package pk.home.busterminal.domain;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusRouteStop.class)
public abstract class BusRouteStop_ {

	public static volatile SingularAttribute<BusRouteStop, Long> id;
	public static volatile SingularAttribute<BusRouteStop, BusStop> busStop;
	public static volatile SingularAttribute<BusRouteStop, String> description;
	public static volatile SingularAttribute<BusRouteStop, BusRoute> busRoute;
	public static volatile SingularAttribute<BusRouteStop, Integer> addDay;
	public static volatile SingularAttribute<BusRouteStop, BusRouteStop> pBRStop;
	public static volatile SingularAttribute<BusRouteStop, Integer> orId;
	public static volatile SingularAttribute<BusRouteStop, BusRouteStop> nBRStop;
	public static volatile SingularAttribute<BusRouteStop, Date> addTime;

}

