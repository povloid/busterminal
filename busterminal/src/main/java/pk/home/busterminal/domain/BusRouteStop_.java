package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusRouteStop.class)
public abstract class BusRouteStop_ {

	public static volatile SingularAttribute<BusRouteStop, Long> id;
	public static volatile SingularAttribute<BusRouteStop, BusStop> busStop;
	public static volatile SingularAttribute<BusRouteStop, String> description;
	public static volatile SingularAttribute<BusRouteStop, BusRoute> busRoute;

}

