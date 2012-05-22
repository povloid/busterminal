package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Balance
 * Balance - баланс
 *
 */
@StaticMetamodel(Balance.class)
public class Balance_ {
	public static volatile SingularAttribute<Balance, Long> id;
	public static volatile SingularAttribute<Balance, String> keyName;
	public static volatile SingularAttribute<Balance, String> description;
}
