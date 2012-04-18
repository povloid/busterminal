package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Items
 * Items - запись ордера
 *
 */
@StaticMetamodel(Items.class)
public class Items_ {
	public static volatile SingularAttribute<Items, Long> id;
	public static volatile SingularAttribute<Items, String> keyName;
	public static volatile SingularAttribute<Items, String> description;
}
