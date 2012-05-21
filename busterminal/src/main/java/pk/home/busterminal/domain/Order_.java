package pk.home.busterminal.domain;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import pk.home.busterminal.domain.security.UserAccount;

@StaticMetamodel(Order.class)
public abstract class Order_ {

	public static volatile SingularAttribute<Order, BusRouteStop> busRouteStopA;
	public static volatile SingularAttribute<Order, Date> opTime;
	public static volatile SingularAttribute<Order, BusRouteStop> busRouteStopB;
	public static volatile SingularAttribute<Order, OrderType> orderType;
	public static volatile SingularAttribute<Order, Customer> customer;
	public static volatile SingularAttribute<Order, Race> race;
	public static volatile SingularAttribute<Order, Long> id;
	public static volatile SingularAttribute<Order, BigDecimal> actualPrice;
	public static volatile SingularAttribute<Order, Seat> seat;
	public static volatile ListAttribute<Order, Items> items;
	public static volatile SingularAttribute<Order, String> description;
	public static volatile SingularAttribute<Order, Order> previousOrder;
	public static volatile SingularAttribute<Order, UserAccount> userAccount;

}

