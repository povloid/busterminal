package pk.home.busterminal.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.UniqueConstraint;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "public", name = "schemas", uniqueConstraints = @UniqueConstraint(columnNames = {
		"bus_id", "keyName" }))
@NamedQueries({
		@NamedQuery(name = "Schema.findAll", query = "select a from Schema a order by a.id"),
		@NamedQuery(name = "Schema.findByPrimaryKey", query = "select a from Schema a where a.id = ?1") })
public class Schema implements Serializable {

	@Version
	private int version;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4574079630536468670L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Bus bus;

	@OneToMany(mappedBy = "schema")
	private Set<Seat> seats;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	private String keyName;

	@Length(max = 1000)
	@Column(length = 1000)
	private String description;

	private Short xSize;
	private Short ySize;
	

	public Short getxSize() {
		return xSize;
	}

	public void setxSize(Short xSize) {
		this.xSize = xSize;
	}

	public Short getySize() {
		return ySize;
	}

	public void setySize(Short ySize) {
		this.ySize = ySize;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Seat> getSeats() {
		return seats;
	}

	public void setSeats(Set<Seat> seats) {
		this.seats = seats;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
		if (!(object instanceof Schema)) {
			return false;
		}
		Schema other = (Schema) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Schema[ id=" + id + " ]";
	}

}
