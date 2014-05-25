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
@Table(schema = "public", name = "Race",
indexes = {
	@Index(name="race_idx0", columnList="dTime"),
	@Index(name="race_idx1", columnList="busRoute_id"),
	@Index(name="race_idx2", columnList="bus_id")})
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
	//@Index(name = "race_idx0")
	private Date dTime;

	@ManyToOne
	@JoinColumn(nullable = false)
	//@Index(name = "race_idx1")
	private BusRoute busRoute;

	@ManyToOne
	@JoinColumn(nullable = false)
	//@Index(name = "race_idx3")
	private Bus bus;

	private Boolean block;
	
	private Boolean autoUnblocked;
	private Integer autoUnblockedBefore;
	
	private Boolean autoBlocked;
	private Integer autoBlockedBefore;
	
	
	/**
	 * Gets the auto unblocked before time.
	 *
	 * @return the auto unblocked before time
	 */
	public Date getAutoUnblockedBeforeTime() {
		if (autoUnblocked != null && autoUnblocked
				&& autoUnblockedBefore != null)
			return new Date(dTime.getTime()
					- (1000L * 60L * 60L * autoUnblockedBefore));
		else
			return null;
	}
	
	/**
	 * Gets the auto blocked before time.
	 *
	 * @return the auto blocked before time
	 */
	public Date getAutoBlockedBeforeTime() {
		if (autoBlocked != null && autoBlocked
				&& autoBlockedBefore != null)
			return new Date(dTime.getTime()
					- (1000L * 60L * 60L * autoBlockedBefore));
		else
			return null;
	}

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
	
	public Boolean getAutoUnblocked() {
		return autoUnblocked;
	}

	public void setAutoUnblocked(Boolean autoUnblocked) {
		this.autoUnblocked = autoUnblocked;
	}

	public Integer getAutoUnblockedBefore() {
		return autoUnblockedBefore;
	}

	public void setAutoUnblockedBefore(Integer autoUnblockedBefore) {
		this.autoUnblockedBefore = autoUnblockedBefore;
	}
	
	public Boolean getAutoBlocked() {
		return autoBlocked;
	}

	public void setAutoBlocked(Boolean autoBlocked) {
		this.autoBlocked = autoBlocked;
	}

	public Integer getAutoBlockedBefore() {
		return autoBlockedBefore;
	}

	public void setAutoBlockedBefore(Integer autoBlockedBefore) {
		this.autoBlockedBefore = autoBlockedBefore;
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
