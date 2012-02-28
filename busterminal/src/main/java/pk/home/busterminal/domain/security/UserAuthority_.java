package pk.home.busterminal.domain.security;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserAuthority.class)
public abstract class UserAuthority_ {

	public static volatile SingularAttribute<UserAuthority, Long> id;
	public static volatile SingularAttribute<UserAuthority, String> authority;
	public static volatile SingularAttribute<UserAuthority, String> description;

}

