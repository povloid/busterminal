package pk.home.busterminal.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Bus.class)
public abstract class Bus_ {

	public static volatile SingularAttribute<Bus, Long> id;
	public static volatile SetAttribute<Bus, Schema> schemas;
	public static volatile SingularAttribute<Bus, String> keyName;
	public static volatile SingularAttribute<Bus, String> description;
	public static volatile SingularAttribute<Bus, String> gosNum;
	public static volatile SingularAttribute<Bus, BssType> bssType;
	public static volatile SingularAttribute<Bus, Integer> version;

}

