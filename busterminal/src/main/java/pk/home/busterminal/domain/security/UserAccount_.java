package pk.home.busterminal.domain.security;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserAccount.class)
public abstract class UserAccount_ {

	public static volatile SingularAttribute<UserAccount, String> pfone2;
	public static volatile SingularAttribute<UserAccount, Boolean> enabled;
	public static volatile SingularAttribute<UserAccount, String> pfone3;
	public static volatile SingularAttribute<UserAccount, String> pfone1;
	public static volatile SingularAttribute<UserAccount, String> pfone4;
	public static volatile SingularAttribute<UserAccount, String> nName;
	public static volatile SingularAttribute<UserAccount, String> password;
	public static volatile SingularAttribute<UserAccount, String> fName;
	public static volatile SetAttribute<UserAccount, UserAuthority> userAuthorities;
	public static volatile SingularAttribute<UserAccount, Long> id;
	public static volatile SingularAttribute<UserAccount, String> username;
	public static volatile SingularAttribute<UserAccount, String> email;
	public static volatile SingularAttribute<UserAccount, String> description;
	public static volatile SingularAttribute<UserAccount, String> mName;
	public static volatile SetAttribute<UserAccount, UserGroup> groups;
	public static volatile SingularAttribute<UserAccount, String> salt;

}

