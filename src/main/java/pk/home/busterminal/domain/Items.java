package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Entity class: Items Items - запись ордера
 * 
 */
@Entity
@Table(schema = "public", name = "items", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "seat_id", "race_id", "brst1_id" }),
		@UniqueConstraint(columnNames = { "seat_id", "race_id", "brst2_id" }),
		@UniqueConstraint(columnNames = { "seat_id", "race_id", "brst1_id",
				"brst2_id" }) },
		indexes = {
		@Index(name="items_idx1", columnList="order_id"),
		@Index(name="items_idx2", columnList="race_id"),
		@Index(name="items_idx3", columnList="seat_id")})
@NamedQueries({
		@NamedQuery(name = "items.findAll", query = "select a from Items a order by a.id"),
		@NamedQuery(name = "items.findByPrimaryKey", query = "select a from Items a where a.id = ?1") })
public class Items implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// -----------------------------------------------------------------------------------------------

	@ManyToOne
	//@Index(name = "items_idx1")
	@JoinColumn(name = "order_id", nullable = false)
	@NotNull
	private Order order;

	@ManyToOne
	//@Index(name = "items_idx2")
	@JoinColumn(name = "race_id", nullable = false)
	@NotNull
	private Race race;

	@ManyToOne
	//@Index(name = "items_idx3")
	@JoinColumn(name = "seat_id", nullable = false)
	@NotNull
	private Seat seat;

	@ManyToOne
	@JoinColumn(name = "brst1_id", nullable = false)
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	private BusRouteStop brst1;

	@ManyToOne
	@JoinColumn(name = "brst2_id", nullable = false)
	// Проверка содержания данной остановки в списке становок маршрута размещена
	// в сервисном уровне
	@NotNull
	private BusRouteStop brst2;

	/**
	 * Проверка на уровне записи
	 * 
	 * @throws Exception
	 */
	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if (brst1 != null && brst2 != null && brst1.equals(brst2))
			throw new Exception("Точки отрезков совпадать не могут");

		if (order != null && !order.getSeat().equals(seat))
			throw new Exception(
					"Место в записи не соответствует месту в ордере");

		if (order != null && !order.getRace().equals(race))
			throw new Exception("Рейс в записи не соответствует рейсу в ордере");
	}

	// -----------------------------------------------------------------------------------------------

	public Items() {
		super();
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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

	public BusRouteStop getBrst1() {
		return brst1;
	}

	public void setBrst1(BusRouteStop brst1) {
		this.brst1 = brst1;
	}

	public BusRouteStop getBrst2() {
		return brst2;
	}

	public void setBrst2(BusRouteStop brst2) {
		this.brst2 = brst2;
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
		if (!(object instanceof Items)) {
			return false;
		}
		Items other = (Items) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Items[ id=" + id + " ]";
	}

}
