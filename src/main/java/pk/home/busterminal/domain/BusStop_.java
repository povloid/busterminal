package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(BusStop.class)
public abstract class BusStop_ {

	public static volatile SingularAttribute<BusStop, Long> id;
	public static volatile SingularAttribute<BusStop, String> keyName;
	public static volatile SingularAttribute<BusStop, String> description;

}

