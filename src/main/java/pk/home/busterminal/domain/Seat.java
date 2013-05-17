package pk.home.busterminal.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import pk.home.busterminal.domain.security.UserAccount;

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
		@NamedQuery(name = "Seat.findNoMarkedBySchema.count", query = "select count(a) from Seat a join a.schema s where a.schema = ?1 and (a.sx is null or a.sy is null or a.sx = 0 or a.sy = 0 or a.sx > s.xSize or a.sy > s.ySize) "),

		@NamedQuery(name = "Seat.findNumInSchema.min", query = "select min(a.num) from Seat a where a.schema = ?1"),
		@NamedQuery(name = "Seat.findNumInSchema.max", query = "select max(a.num) from Seat a where a.schema = ?1")

})
public class Seat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Version
	private int version;

	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if (seatType != null && seatType.getSold() != null
				&& seatType.getSold() && num < 0)
			throw new Exception(
					"У продаваемых мест номер должен быть положительным!");

		if (seatType != null && seatType.getSold() != null
				&& !seatType.getSold() && num > 0)
			throw new Exception(
					"У НЕ продаваемых мест номер должен быть отрицательным!");
		
		if (block != null && block) {
			if (blockDescription == null || blockDescription.trim().length() == 0)
				throw new Exception("Не указана причина блокировки!");

			if (blocker == null)
				throw new Exception(
						"Не указан пользователь, осуществивший блокирование!");
			
			blockDate = new Date();
		} else {
			blockDescription = null;
			blocker = null;
			blockDate = null;
		}
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

	// Мастер процент
	private Integer masterProcent;

	// Цена
	private BigDecimal price;

	private Boolean discount;
	private Integer discountPotsent;

	// Тип места
	@NotNull
	@ManyToOne
	@JoinColumn(nullable = false)
	private SeatType seatType;

	// Блокировка места	
	private Boolean block;
	
	@Length(max = 1000)
	@Column(length = 1000)
	private String blockDescription;
	
	@ManyToOne
	private UserAccount blocker;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date blockDate;

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

	public Integer getMasterProcent() {
		return masterProcent;
	}

	public void setMasterProcent(Integer masterProcent) {
		this.masterProcent = masterProcent;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public SeatType getSeatType() {
		return seatType;
	}

	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}

	public Boolean getDiscount() {
		return discount;
	}

	public void setDiscount(Boolean discount) {
		this.discount = discount;
	}

	public Integer getDiscountPotsent() {
		return discountPotsent;
	}

	public void setDiscountPotsent(Integer discountPotsent) {
		this.discountPotsent = discountPotsent;
	}

	public Boolean getBlock() {
		return block;
	}

	public void setBlock(Boolean block) {
		this.block = block;
	}

	public String getBlockDescription() {
		return blockDescription;
	}

	public void setBlockDescription(String blockDescription) {
		this.blockDescription = blockDescription;
	}

	public UserAccount getBlocker() {
		return blocker;
	}

	public void setBlocker(UserAccount blocker) {
		this.blocker = blocker;
	}

	public Date getBlockDate() {
		return blockDate;
	}

	public void setBlockDate(Date blockDate) {
		this.blockDate = blockDate;
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
