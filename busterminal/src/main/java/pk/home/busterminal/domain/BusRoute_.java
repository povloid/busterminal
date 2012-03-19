package pk.home.busterminal.domain;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusRoute.class)
public abstract class BusRoute_ {

	public static volatile SingularAttribute<BusRoute, Long> id;
	public static volatile ListAttribute<BusRoute, BusRouteStop> busRouteStops;
	public static volatile SingularAttribute<BusRoute, String> keyName;
	public static volatile SingularAttribute<BusRoute, String> description;

}

