package pk.home.busterminal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Index;

@Entity
@Table(schema = "public", name = "bus_routes_stops", uniqueConstraints = @UniqueConstraint(columnNames = {
		"busroute_id", "busstop_id" }))
public class BusRouteStop implements Serializable {

	/**
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
