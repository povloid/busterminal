package pk.home.busterminal.domain;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity class: Driver Driver - водитель
 * 
 */
@Entity
@Table(schema = "public", name = "Driver")
@NamedQueries({
		@NamedQuery(name = "Driver.findAll", query = "select a from Driver a order by a.id"),
		@NamedQuery(name = "Driver.findByPrimaryKey", query = "select a from Driver a where a.id = ?1") })
public class Driver implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	private DocumentType docupentType;

	@NotNull
	@Column(unique = true, nullable = false)
	private String keyName;

	@NotNull
	@Size(max = 20)
	@Column(length = 20, nullable = false)
	private String fName;

	@Size(max = 20)
	@Column(length = 20, nullable = false)
	private String nName;

	@Size(max = 20)
	@Column(length = 20)
	private String mName;

	// Телефоны

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

	private String description;

	public Driver() {
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

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getnName() {
		return nName;
	}

	public void setnName(String nName) {
		this.nName = nName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public DocumentType getDocupentType() {
		return docupentType;
	}

	public void setDocupentType(DocumentType docupentType) {
		this.docupentType = docupentType;
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

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof Driver)) {
			return false;
		}
		Driver other = (Driver) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.busterminal.domain.Driver[ id=" + id + " ]";
	}

}
