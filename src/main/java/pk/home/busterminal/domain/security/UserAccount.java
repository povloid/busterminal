package pk.home.busterminal.domain.security;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import pk.home.busterminal.domain.Division;

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
	private static final long serialVersionUID = 1L;

	@Column(unique = true, nullable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false, unique = true)
	private String username;

	@Column(length = 80, nullable = false)
	private String password;

	@Column(nullable = false)
	private boolean enabled = true;

	@NotNull
	@Size(max = 20)
	@Column(length = 20, nullable = false)
	private String fName;

	@NotNull
	@Size(max = 20)
	@Column(length = 20, nullable = false)
	private String nName;

	@NotNull
	@Size(max = 20)
	@Column(length = 20, nullable = false)
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

	@Column(length = 25, nullable = false)
	private String salt = "testSalt"; // todo: Тестовые данные не для Production

	@Email
	private String email;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<UserAuthority> userAuthorities = new HashSet<UserAuthority>();

	@ManyToMany
	private Set<UserGroup> groups = new HashSet<UserGroup>();

	@ManyToOne
	private Division division;

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

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
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

}