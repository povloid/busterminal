package pk.home.busterminal.domain;

import java.util.Date;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Race.class)
public abstract class Race_ {

	public static volatile SingularAttribute<Race, Long> id;
	public static volatile SingularAttribute<Race, Bus> bus;
	public static volatile SingularAttribute<Race, Date> dTime;
	public static volatile SingularAttribute<Race, String> keyName;
	public static volatile SingularAttribute<Race, String> description;
	public static volatile SingularAttribute<Race, BusRoute> busRoute;
	public static volatile SingularAttribute<Race, Integer> version;

}

