package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: BusRoute
 * BusRoute - Маршрут
 *
 */
@StaticMetamodel(BusRoute.class)
public class BusRoute_ {
	public static volatile SingularAttribute<BusRoute, Long> id;
	public static volatile SingularAttribute<BusRoute, String> keyName;
	public static volatile SingularAttribute<BusRoute, String> description;
}
