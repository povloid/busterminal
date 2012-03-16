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
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "public", name = "seats", uniqueConstraints = @UniqueConstraint(columnNames = {
		"num", "schema_id" }))
@NamedQueries({
		@NamedQuery(name = "Seat.findAll", query = "select a from Seat a order by a.id"),
		@NamedQuery(name = "Seat.findByPrimaryKey", query = "select a from Seat a where a.id = ?1"),

		@NamedQuery(name = "Seat.findByBusAndSchema.id.asc", query = "select a from Seat a where a.schema.bus = ?1 and a.schema = ?2 order by a.id asc"),
		@NamedQuery(name = "Seat.findByBusAndSchema.id.desc", query = "select a from Seat a where a.schema.bus = ?1 and a.schema = ?2 order by a.id desc"),

		@NamedQuery(name = "Seat.findByBusAndSchema.num.asc", query = "select a from Seat a where a.schema.bus = ?1 and a.schema = ?2 order by a.num asc"),
		@NamedQuery(name = "Seat.findByBusAndSchema.num.desc", query = "select a from Seat a where a.schema.bus = ?1 and a.schema = ?2 order by a.num desc"),

		@NamedQuery(name = "Seat.findByBusAndSchema.count", query = "select count(a) from Seat a where a.schema.bus = ?1 and a.schema = ?2"),
		
		@NamedQuery(name = "Seat.findNoMarkedBySchema", query = "select a from Seat a join a.schema s  where a.schema = ?1 and (a.sx is null or a.sy is null or a.sx = 0 or a.sy = 0 or a.sx > s.xSize or a.sy > s.ySize) "),
		@NamedQuery(name = "Seat.findNoMarkedBySchema.count", query = "select count(a) from Seat a join a.schema s where a.schema = ?1 and (a.sx is null or a.sy is null or a.sx = 0 or a.sy = 0 or a.sx > s.xSize or a.sy > s.ySize) ")

})
public class Seat implements Serializable {

	@Version
	private int version;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@PrePersist
	@PreUpdate
	public void check() throws Exception {

	}

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
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
