package pk.home.busterminal.domain.security;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER_GROUP")
public class UserGroup implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String groupName;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "GROUP_AUTHORITY")
    private Set<UserAuthority> authorities = new HashSet<UserAuthority>();

    @ManyToMany(mappedBy = "groups", cascade = CascadeType.ALL)
    private Set<UserAccount> accounts = new HashSet<UserAccount>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<UserAuthority> authorities) {
        this.authorities = authorities;
    }

    public Set<UserAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<UserAccount> accounts) {
        this.accounts = accounts;
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
		if (!(object instanceof UserGroup)) {
			return false;
		}
		UserGroup other = (UserGroup) object;
		if ((this.id == null && other.id != null)
				|| (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "pk.home.vta.domain.security.UserGroup[ id=" + id + " ]";
	}
    
    
}
