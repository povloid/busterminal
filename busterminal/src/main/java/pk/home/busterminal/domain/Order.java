package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;

import pk.home.busterminal.domain.security.UserAccount;

/**
 * Entity class: Order Order - ордер - операция
 * 
 */
@Entity
@Table(schema = "public", name = "Order")
@NamedQueries({
		@NamedQuery(name = "Order.findAll", query = "select a from Order a order by a.id"),
		@NamedQuery(name = "Order.findByPrimaryKey", query = "select a from Order a where a.id = ?1") })
public class Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private Order previousOrder; // Предшествующий ордер

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Index(name = "order_idx1")
	private OrderType orderType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false)
	@Index(name = "order_idx2")
	@NotNull
	private Date opTime;

	private String description;

	// -----------------------------------------------------------------------------------------------------------------

	@ManyToOne
	@NotNull
	@JoinColumn(nullable = false)
	private Race race;

	@ManyToOne
	// (1S) ????????
	// Проверка содержания данного места в списке мест выставленного на данном
	// рейсе автобуса при продаже
	// в сервисном уровне
	@NotNull
	@JoinColumn(nullable = false)
	private Seat seat;

	@ManyToOne
	// (2S)
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	@JoinColumn(nullable = false)
	private BusRouteStop busRouteStopA;

	@ManyToOne
	// (3S)
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	@JoinColumn(nullable = false)
	private BusRouteStop busRouteStopB;

	@NotNull
	@Column(nullable = false)
	private BigDecimal actualPrice;

	@OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
	private List<Items> items;

	// -----------------------------------------------------------------------------------------------------------------

	@ManyToOne
	@Index(name = "order_idx3")
	@JoinColumn(nullable = false)
	@NotNull
	private UserAccount userAccount;

	@ManyToOne
	@Index(name = "order_idx4")
	@JoinColumn(nullable = false)
	@NotNull
	private Customer customer;

	// -----------------------------------------------------------------------------------------------------------------

	private final static BigDecimal ROUND_VALUE = new BigDecimal(10);

	/**
	 * Проверка
	 * 
	 * @throws Exception
	 */
	@PrePersist
	@PreUpdate
	public void check() throws Exception {

		if (opTime == null) {
			throw new Exception("Должно быть указано время операции!");
		}

		if (orderType == null) {
			throw new Exception("Не указан тип операции!");
		}

		if (customer == null) {
			throw new Exception("Не указан клиент");
		}

		if (userAccount == null) {
			throw new Exception("Не указан пользователь операции");
		}

		// (1) Проверка по типу ордера --------------------------
		// Продажа
		if (orderType != null && orderType == OrderType.TICKET_SALE
				&& opTime != null
				&& opTime.getTime() > race.getdTime().getTime()) {
			throw new Exception("Нельзя продать билет на старый рейс!");
		}

		if (orderType != null && orderType == OrderType.TICKET_SALE
				&& seat != null && seat.getSeatType().getSold()) {

			if (seat != null && seat.getBlock() != null && seat.getBlock())
				throw new Exception(
						"Место заблокировано, продавать его нельзя!");

			if (seat.getDiscount() != null && seat.getDiscount()
					&& seat.getDiscountPotsent() != null) {
				if (seat.getPrice().compareTo(actualPrice) == -1) {
					throw new Exception(
							"При продаже со скидкой цена продажи не может быть больше максимальной цены места!");
				}

				BigDecimal minPrice = seat
						.getPrice()
						.divide(new BigDecimal(100))
						.multiply(
								new BigDecimal(100 - seat.getDiscountPotsent()));

				minPrice = minPrice.divide(ROUND_VALUE, 0,
						BigDecimal.ROUND_DOWN).multiply(ROUND_VALUE);

				System.out.println(minPrice + "  --  " + actualPrice);

				if (minPrice.compareTo(actualPrice) == 1) {
					throw new Exception(
							"При продаже со скидкой цена продажи не может быть меньше минимальной цены места!");
				}

			} else {
				if (seat.getPrice().compareTo(actualPrice) != 0) {
					throw new Exception(
							"При продаже без скидки цена продажи не может отлицаться от цены места!");
				}
			}
		}

		// Возврат

		if (orderType != null && orderType == OrderType.TICKET_RETURN
				&& previousOrder == null) {
			throw new Exception(
					"Возвратный ордер должен иметь родительский ордер!");
		}

		if (orderType != null && orderType == OrderType.TICKET_RETURN
				&& previousOrder != null) {

			if (seat != null && !seat.equals(previousOrder.getSeat()))
				throw new Exception(
						"Место в возвратном ордере должно совпадать с местом в родительском ордере!");

			if (race != null && !race.equals(previousOrder.getRace()))
				throw new Exception(
						"Рейс в возвратном ордере должно совпадать с рейсом в родительском ордере!");

			if (customer != null
					&& !customer.equals(previousOrder.getCustomer()))
				throw new Exception(
						"Клиент в возвратном ордере должно совпадать с клиентом в родительском ордере!");

			if (busRouteStopA != null
					&& !busRouteStopA.equals(previousOrder.getBusRouteStopA()))
				throw new Exception(
						"Остановка отправления в возвратном ордере должно совпадать с остановкой отправления в родительском ордере!");

			if (busRouteStopB != null
					&& !busRouteStopB.equals(previousOrder.getBusRouteStopB()))
				throw new Exception(
						"Остановка прибытия в возвратном ордере должно совпадать с остановкой прибытия в родительском ордере!");

			if (actualPrice != null && actualPrice.doubleValue() > 0)
				throw new Exception(
						"При возврате цена должна быть отрицательной!");

			if (actualPrice != null
					&& !actualPrice.abs()
							.equals(previousOrder.getActualPrice()))
				throw new Exception(
						"При возврате цена должна совпадать с ценой в продажном ордере!");

		}

		// (2) Проверки по введенному рейсу ---------------------
		// Пусть принадлежность к рейсу всегда указывается
		if (race == null) {
			throw new Exception("Не указан рейс");
		} else if (race.getBus().getBssType() != BssType.WORK) {
			throw new Exception(
					"Автобус продаваемого рейса должен иметь тип WORK");
		}

		// (3) --------------------------------------------------
		if (seat == null) {
			throw new Exception("Не указано место");
		} else if (!seat.getSchema().getBus().equals(race.getBus())) {
			throw new Exception(
					"Указанный автобус в схеме не совпадает с указаным автобусом в рейсе");
		}

		// (4) Сведения по отрезку пути
		if (busRouteStopA == null || busRouteStopB == null) {
			throw new Exception("Не указаны пункты начала и конца пути");
		} else if (busRouteStopA.equals(busRouteStopB)) {
			throw new Exception("Остановки отрезка совпадать не могут");
		} else if (busRouteStopA.getOrId() >= busRouteStopB.getOrId()) {
			throw new Exception(
					"Остановка начала пути не может быть позже остановки конца пути");
		}

		// (5)
		if (seat != null && seat.getNum() < 0)
			throw new Exception(
					"Нельзя продавать места с отрицательными номерами!");

		// (6)
		if (seat != null && !seat.getSeatType().getSold())
			throw new Exception("Нельзя продавать места с непродажными типами!");

		// (7)
		if (race != null && race.getBlock() != null && race.getBlock())
			throw new Exception(
					"Данный рейс заблокирован. Проведение операции невозможно!");

	}

	// Сервисные функции

	// @Transient
	// private Date seatTime;

	/**
	 * Получение времени посадки с вприбавлением по смещению от посадочной
	 * остановки маршрута
	 * 
	 * @return
	 */
	public Date getSeatTime() {

		Calendar cal = Calendar.getInstance();
		cal.setTime(race.getdTime());

		if (busRouteStopA.getAddDay() != null) {
			cal.add(Calendar.DAY_OF_YEAR, busRouteStopA.getAddDay().intValue());
		}

		if (busRouteStopA.getAddTime() != null) {
			Calendar calTime = Calendar.getInstance();
			calTime.setTime(busRouteStopA.getAddTime());

			cal.add(Calendar.HOUR_OF_DAY, calTime.get(Calendar.HOUR_OF_DAY));
			cal.add(Calendar.MINUTE, calTime.get(Calendar.MINUTE));
		}

		return cal.getTime();
	}

	// -----------------------------------------------------------------------------------------------------------------

	public Order() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Order getPreviousOrder() {
		return previousOrder;
	}

	public void setPreviousOrder(Order previousOrder) {
		this.previousOrder = previousOrder;
	}

	public Date getOpTime() {
		return opTime;
	}

	public void setOpTime(Date opTime) {
		this.opTime = opTime;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

	public BusRouteStop getBusRouteStopA() {
		return busRouteStopA;
	}

	public void setBusRouteStopA(BusRouteStop busRouteStopA) {
		this.busRouteStopA = busRouteStopA;
	}

	public BusRouteStop getBusRouteStopB() {
		return busRouteStopB;
	}

	public void setBusRouteStopB(BusRouteStop busRouteStopB) {
		this.busRouteStopB = busRouteStopB;
	}

	public BigDecimal getActualPrice() {
		return actualPrice;
	}

	public void setActualPrice(BigDecimal actualPrice) {
		this.actualPrice = actualPrice;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<Items> getItems() {
		return items;
	}

	public void setItems(List<Items> items) {
		this.items = items;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Order)) {
			return false;
		}
		Order other = (Order) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Order[ id=" + id + " ]";
	}

}
