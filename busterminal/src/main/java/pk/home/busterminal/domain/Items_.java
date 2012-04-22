package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Items.class)
public abstract class Items_ {

	public static volatile SingularAttribute<Items, Long> id;
	public static volatile SingularAttribute<Items, Order> order;
	public static volatile SingularAttribute<Items, Seat> seat;
	public static volatile SingularAttribute<Items, String> description;
	public static volatile SingularAttribute<Items, BusRouteStop> brst1;
	public static volatile SingularAttribute<Items, BusRouteStop> brst2;
	public static volatile SingularAttribute<Items, Race> race;

}

