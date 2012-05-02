package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
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
	@Column(unique = true, nullable = false)
	@Index(name = "order_idx2")
	@NotNull
	private Date opTime;

	private String description;

	// -----------------------------------------------------------------------------------------------------------------

	@ManyToOne
	@NotNull
	private Race race;

	@ManyToOne
	// Проверка содержания данного места в списке мест выставленного на данном
	// рейсе автобуса
	// в сервисном уровне
	@NotNull
	private Seat seat;

	@ManyToOne
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	private BusRouteStop busRouteStopA;

	@ManyToOne
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	private BusRouteStop busRouteStopB;

	@NotNull
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

	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if(opTime == null){
			throw new Exception(
					"Должно быть указано время операции!");
		}
		
		
		if(orderType == null){
			throw new Exception(
					"Не указан тип операции!");
		}
		
		
		
		// (1) Проверка по типу ордера --------------------------
		if (orderType != null && orderType == OrderType.TICKET_RETURN
				&& previousOrder == null) {
			throw new Exception(
					"Возвратный ордер должен иметь родительский ордер!");
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
