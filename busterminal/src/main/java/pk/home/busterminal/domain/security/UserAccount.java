package pk.home.busterminal.domain.security;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_ACCOUNT")
@NamedQueries({
		@NamedQuery(name = "UserAccount.findAll", query = "select a from UserAccount a order by a.id"),
		@NamedQuery(name = "UserAccount.findByPrimaryKey", query = "select a from UserAccount a where a.id = ?1"),
		@NamedQuery(name = "UserAccount.findByUsername", query = "select a from UserAccount a where a.username = ?1") })
public class UserAccount implements UserDetails, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5540548849637113620L;

	@Column(unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String username;

	@Column(length = 50, nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean enabled = true;

	private String description;

	@Column(length = 25, nullable = false)
	private String salt = "testSalt"; // todo: Тестовые данные не для Production

	@Email
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();

	@ManyToMany
	private Set<UserGroup> groups = new HashSet<UserGroup>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(Set<UserGroup> groups) {
		this.groups = groups;
	}

	public Set<UserAuthority> getUserAuthorities() {
		return userAuthorities;
	}

	public void setUserAuthorities(Set<UserAuthority> userAuthorities) {
		this.userAuthorities = userAuthorities;
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
		if (!(object instanceof UserAccount)) {
			return false;
		}
		UserAccount other = (UserAccount) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.voyager.domain.security.UserAccount[ id=" + id + " ]";
	}

	// ////// Spring Security UserDetails methods implementation

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return new HashSet<GrantedAuthority>(userAuthorities);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}
}