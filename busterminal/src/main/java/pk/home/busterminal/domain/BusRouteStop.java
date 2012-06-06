package pk.home.busterminal.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

@Entity
@Table(schema = "public", name = "bus_routes_stops", uniqueConstraints = {
// @UniqueConstraint(columnNames = { "busroute_id", "busstop_id" }), --> Потому
// что надо чтобы были возможны круговые маршруты и петли
// Подразумевается мультиграф с петлями
@UniqueConstraint(columnNames = { "busroute_id", "orId" }) })
public class BusRouteStop implements Serializable {

	/**
	 * 
	 * 
	 */
	private static final long serialVersionUID = -2201675064555128789L;

	@ManyToOne
	@JoinColumn(nullable = false, name = "busroute_id")
	@Index(name = "bus_routes_stops_idx1")
	private BusRoute busRoute;

	@ManyToOne
	@JoinColumn(nullable = false, name = "busstop_id")
	@Index(name = "bus_routes_stops_idx1")
	private BusStop busStop;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	// logic

	@Column(name = "orId")
	private int orId;

	@ManyToOne
	private BusRouteStop pBRStop;

	@ManyToOne
	private BusRouteStop nBRStop;

	private Integer addDay;
	private Date addTime;

	/**
	 * Проверка были ли изменения в защищенных полях
	 * 
	 * @param busRouteStop
	 * @return
	 * @throws Exception
	 */
	public boolean isProtectionFieldUpdated(BusRouteStop busRouteStop)
			throws Exception {

		return // инвертированное условие номального состояния
		!
		// Первое поле
		(((this.pBRStop == null && busRouteStop.pBRStop == null) || (this.pBRStop != null
				&& busRouteStop.pBRStop != null && this.pBRStop
					.equals(busRouteStop.pBRStop)))
		// и
		&&
		// Второе поле
		((this.nBRStop == null && busRouteStop.nBRStop == null) || (this.nBRStop != null
				&& busRouteStop.nBRStop != null && this.nBRStop
					.equals(busRouteStop.nBRStop))));
	}

	/**
	 * Проверка логической правильности
	 * 
	 * @throws Exception
	 */
	@PreUpdate
	@PrePersist
	public void check() throws Exception {

		// Данная проверька была перенесена на уровень сервиса
		// так как сервисный уровено должен помечать сразу группу без проверок
		// через DAO уровен
		// if ((pBRStop == null && pBRStop == null)
		// || (pBRStop == null || pBRStop.orId < orId)
		// && (nBRStop == null || nBRStop.orId > orId)
		// && (pBRStop != null && nBRStop != null
		// && !pBRStop.equals(nBRStop) && !pBRStop.equals(this) && !nBRStop
		// .equals(this))) {
		// // Выполнение данного условия считается нормальным
		// // System.out.println(">>> OK");
		// } else {
		// throw new Exception("Нарушение упорядоченности");
		// }
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BusRoute getBusRoute() {
		return busRoute;
	}

	public void setBusRoute(BusRoute busRoute) {
		this.busRoute = busRoute;
	}

	public BusStop getBusStop() {
		return busStop;
	}

	public void setBusStop(BusStop busStop) {
		this.busStop = busStop;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getOrId() {
		return orId;
	}

	public void setOrId(int orId) {
		this.orId = orId;
	}

	public BusRouteStop getpBRStop() {
		return pBRStop;
	}

	public void setpBRStop(BusRouteStop pBRStop) {
		this.pBRStop = pBRStop;
	}

	public BusRouteStop getnBRStop() {
		return nBRStop;
	}

	public void setnBRStop(BusRouteStop nBRStop) {
		this.nBRStop = nBRStop;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Integer getAddDay() {
		return addDay;
	}

	public void setAddDay(Integer addDay) {
		this.addDay = addDay;
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
		if (!(object instanceof BusRouteStop)) {
			return false;
		}
		BusRouteStop other = (BusRouteStop) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.BusRouteStop[ id=" + id + " ]";
	}

}
