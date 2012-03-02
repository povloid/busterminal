package pk.home.busterminal.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "public", name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = {
		"bus_id", "num" , "schema_id"}))
@NamedQueries({
		@NamedQuery(name = "Seat.findAll", query = "select a from Seat a order by a.id"),
		@NamedQuery(name = "Seat.findByPrimaryKey", query = "select a from Seat a where a.id = ?1") })
public class Seat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(nullable = false)
	private Bus bus;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Schema schema;
	
	@NotNull
	@Column(nullable = false)
	private Short num;
	

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Length(max = 1000)
	@Column(length = 1000)
	private String description;


	private Short sx;
	private Short sy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public Short getNum() {
		return num;
	}

	public void setNum(Short num) {
		this.num = num;
	}

	public Short getSx() {
		return sx;
	}

	public void setSx(Short sx) {
		this.sx = sx;
	}

	public Short getSy() {
		return sy;
	}

	public void setSy(Short sy) {
		this.sy = sy;
	}
	
	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
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
		if (!(object instanceof Seat)) {
			return false;
		}
		Seat other = (Seat) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Seat[ id=" + id + " ]";
	}

}
