package pk.home.busterminal.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, BusStop> busStopB;
	public static volatile SingularAttribute<Order, BigDecimal> actualPrice;
	public static volatile SingularAttribute<Order, String> keyName;
	public static volatile SingularAttribute<Order, Seat> seat;
	public static volatile SingularAttribute<Order, String> description;
	public static volatile SingularAttribute<Order, Order> previousOrder;
	public static volatile SingularAttribute<Order, BusStop> busStopA;
	public static volatile SingularAttribute<Order, Race> race;
	public static volatile SingularAttribute<Order, Date> сTime;

}

