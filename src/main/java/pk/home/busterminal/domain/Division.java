package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity class: Division Division - отделение
 * 
 */
@Entity
@Table(schema = "public", name = "Division")
@NamedQueries({
		@NamedQuery(name = "Division.findAll", query = "select a from Division a order by a.id"),
		@NamedQuery(name = "Division.findByPrimaryKey", query = "select a from Division a where a.id = ?1") })
public class Division implements Serializable {

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
	private String keyName;

	@Size(max = 20)
	@Column(length = 20)
	private String pfone1;

	@Size(max = 20)
	@Column(length = 20)
	private String pfone2;

	@Size(max = 20)
	@Column(length = 20)
	private String pfone3;

	@Size(max = 20)
	@Column(length = 20)
	private String pfone4;

	@Size(max = 1000)
	@Column(length = 1000)
	private String address;

	private String description;

	public Division() {
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

	public String getPfone1() {
		return pfone1;
	}

	public void setPfone1(String pfone1) {
		this.pfone1 = pfone1;
	}

	public String getPfone2() {
		return pfone2;
	}

	public void setPfone2(String pfone2) {
		this.pfone2 = pfone2;
	}

	public String getPfone3() {
		return pfone3;
	}

	public void setPfone3(String pfone3) {
		this.pfone3 = pfone3;
	}

	public String getPfone4() {
		return pfone4;
	}

	public void setPfone4(String pfone4) {
		this.pfone4 = pfone4;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
		if (!(object instanceof Division)) {
			return false;
		}
		Division other = (Division) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Division[ id=" + id + " ]";
	}

}
