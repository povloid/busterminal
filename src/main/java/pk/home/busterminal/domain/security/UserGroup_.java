package pk.home.busterminal.domain.security;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserGroup.class)
public abstract class UserGroup_ {

	public static volatile SingularAttribute<UserGroup, Long> id;
	public static volatile SingularAttribute<UserGroup, String> groupName;
	public static volatile SetAttribute<UserGroup, UserAccount> accounts;
	public static volatile SetAttribute<UserGroup, UserAuthority> authorities;

}

