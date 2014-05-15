package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



/**
 * Entity class: BusRoute BusRoute - Маршрут
 * 
 */
@Entity
@Table(schema = "public", name = "BusRoutes", 
indexes = {
		@Index(name="busroutes_idx1", columnList="keyName")})
@NamedQueries({
		@NamedQuery(name = "BusRoute.findAll", query = "select a from BusRoute a order by a.id"),
		@NamedQuery(name = "BusRoute.findByPrimaryKey", query = "select a from BusRoute a where a.id = ?1") })
public class BusRoute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(unique = true, nullable = false)
	//@Index(name = "busroutes_idx1")
	private String keyName;

	private String description;

	@OneToMany(mappedBy = "busRoute", fetch = FetchType.LAZY)
	@OrderColumn(name="orId")
	private List<BusRouteStop> busRouteStops;

	public BusRoute() {
		super();
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String keyName) {
		System.out.println(keyName);
		this.keyName = keyName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<BusRouteStop> getBusRouteStops() {
		return busRouteStops;
	}

	public void setBusRouteStops(List<BusRouteStop> busRouteStops) {
		this.busRouteStops = busRouteStops;
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
		if (!(object instanceof BusRoute)) {
			return false;
		}
		BusRoute other = (BusRoute) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.BusRoute[ id=" + id + " ]";
	}

}
