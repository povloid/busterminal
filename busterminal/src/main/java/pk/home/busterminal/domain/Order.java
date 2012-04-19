package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;
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
	private Date opTime;

	private String description;

	// -----------------------------------------------------------------------------------------------------------------

	@ManyToOne
	private Race race;

	@ManyToOne
	// Проверка содержания данного места в списке мест выставленного на данном
	// рейсе автобуса
	// в сервисном уровне
	private Seat seat;

	@ManyToOne
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	private BusStop busStopA;

	@ManyToOne
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	private BusStop busStopB;

	private BigDecimal actualPrice;

	// -----------------------------------------------------------------------------------------------------------------

	@ManyToOne
	@Index(name = "order_idx3")
	@JoinColumn(nullable = false)
	private UserAccount userAccount;

	// -----------------------------------------------------------------------------------------------------------------

	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if (!seat.getSchema().getBus().equals(race.getBus())) {
			throw new Exception(
					"Указанный автобус в схеме не совпадает с указаным автобусом в рейсе");
		}

		if (busStopA.equals(busStopB)) {
			throw new Exception("Остановки отрезка совпадать не могут");
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

	public BusStop getBusStopA() {
		return busStopA;
	}

	public void setBusStopA(BusStop busStopA) {
		this.busStopA = busStopA;
	}

	public BusStop getBusStopB() {
		return busStopB;
	}

	public void setBusStopB(BusStop busStopB) {
		this.busStopB = busStopB;
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
