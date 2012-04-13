package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Order
 * Order - ордер - операция
 *
 */
@StaticMetamodel(Order.class)
public class Order_ {
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, String> keyName;
	public static volatile SingularAttribute<Order, String> description;
}
