package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * Entity class: BusStop
 * BusStop - Остановка
 *
 */
@Entity
@Table(schema = "public", name = "BusStops", 
indexes = {
		@Index(name="busstops_idx1", columnList="keyName")})
@NamedQueries({
	@NamedQuery(name = "BusStop.findAll", query = "select a from BusStop a order by a.id"),
	@NamedQuery(name = "BusStop.findByPrimaryKey", query = "select a from BusStop a where a.id = ?1")})
public class BusStop implements Serializable {

	   
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
    @Column(unique=true, nullable = false)
	//@Index(name = "busstops_idx1")
	private String keyName;
	
	
	private String description;
	

	public BusStop() {
		super();
	}   
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
	
	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof BusStop)) {
			return false;
		}
		BusStop other = (BusStop) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.BusStop[ id=" + id + " ]";
	}
   
}
