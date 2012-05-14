package pk.home.busterminal.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Index;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(schema = "public", name = "buses")
// , uniqueConstraints = {
// @UniqueConstraint(columnNames = { "bsstype", "keyName" }),
// @UniqueConstraint(columnNames = { "bsstype", "keyName" }) })
@NamedQueries({
		@NamedQuery(name = "Bus.findAll", query = "select a from Bus a order by a.id"),
		@NamedQuery(name = "Bus.findByPrimaryKey", query = "select a from Bus a where a.id = ?1") })
public class Bus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -865838226573198527L;

	@Version
	private int version;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@Index(name = "buses_bsstype_idx")
	private BssType bssType;

	@ManyToOne
	private Bus templite;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(nullable = false)
	@Index(name = "buses_keyname_idx")
	private String keyName;

	@NotNull
	@Column(nullable = false, length = 10)
	@Size(max = 10)
	@Index(name = "buses_gosnum_idx")
	private String gosNum;

	@Length(max = 1000)
	@Column(length = 1000)
	private String description;

	@OneToMany(mappedBy = "bus")
	private Set<Schema> schemas;

	@ManyToOne
	private Race race;

	// check
	// -----------------------------------------------------------------------------------------------------------

	@PrePersist
	@PreUpdate
	public void check() throws Exception {
		if (bssType == null)
			throw new Exception("Тип должен быть указан!");

		if (bssType == BssType.TEMPLITE && templite != null) {
			throw new Exception(
					"TEMPLITE не может иметь родителя в поле templite!");
		} else if (bssType == BssType.WORK && templite == null) {
			throw new Exception("WORK должен иметь родителя в поле templite!");
		}
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public BssType getBssType() {
		return bssType;
	}

	public void setBssType(BssType bssType) {
		this.bssType = bssType;
	}

	public Bus getTemplite() {
		return templite;
	}

	public void setTemplite(Bus templite) {
		this.templite = templite;
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

	public Set<Schema> getSchemas() {
		return schemas;
	}

	public void setSchemes(Set<Schema> schemas) {
		this.schemas = schemas;
	}

	public String getGosNum() {
		return gosNum;
	}

	public void setGosNum(String gosNum) {
		this.gosNum = gosNum;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
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
		if (!(object instanceof Bus)) {
			return false;
		}
		Bus other = (Bus) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Bus[ id=" + id + " ]";
	}

}
