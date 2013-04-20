package pk.home.busterminal.domain.security;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USER_AUTHORITY")
@NamedQueries({
	@NamedQuery(name = "UserAuthority.findAll", query = "select a from UserAuthority a order by a.id"),
	@NamedQuery(name = "UserAuthority.findByPrimaryKey", query = "select a from UserAuthority a where a.id = ?1"),
	@NamedQuery(name = "UserAuthority.findByUsername", query = "select a from UserAuthority a where a.authority = ?1") })
public class UserAuthority implements GrantedAuthority, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String authority;

	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getDescription() {
		return description;
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
		if (!(object instanceof UserAuthority)) {
			return false;
		}
		UserAuthority other = (UserAuthority) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.voyager.domain.security.UserAuthority[ id=" + id + " ]";
	}

}