package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity class: Race Race - рейс
 * 
 */
@Entity
@Table(schema = "public", name = "Race")
@NamedQueries({
		@NamedQuery(name = "Race.findAll", query = "select a from Race a order by a.id"),
		@NamedQuery(name = "Race.findByPrimaryKey", query = "select a from Race a where a.id = ?1") })
public class Race implements Serializable {

	@Version
	private int version;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;

	// -------------------------------

	@Temporal(TemporalType.TIMESTAMP)
	@Column(unique = true, nullable = false)
	private Date dTime;

	@ManyToOne
	@JoinColumn(nullable = false)
	private BusRoute busRoute;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Bus bus;

	private Boolean block;

	/**
	 * Проверка
	 * 
	 * @throws Exception
	 */
	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if (bus != null && bus.getBssType() != BssType.WORK) {
			throw new Exception("В рейсе должен быть рабочий вариант шаблона");
		}
	}

	public Race() {
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

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Date getdTime() {
		return dTime;
	}

	public void setdTime(Date dTime) {
		this.dTime = dTime;
	}

	public BusRoute getBusRoute() {
		return busRoute;
	}

	public void setBusRoute(BusRoute busRoute) {
		this.busRoute = busRoute;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Boolean getBlock() {
		return block;
	}

	public void setBlock(Boolean block) {
		this.block = block;
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
		if (!(object instanceof Race)) {
			return false;
		}
		Race other = (Race) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Race[ id=" + id + " ]";
	}

}
