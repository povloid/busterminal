package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, Long> id;
	public static volatile SingularAttribute<Customer, String> keyName;
	public static volatile SingularAttribute<Customer, String> description;

}
