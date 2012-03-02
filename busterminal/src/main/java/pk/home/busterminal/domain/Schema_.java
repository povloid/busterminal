package pk.home.busterminal.domain;

import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Schema.class)
public abstract class Schema_ {

	public static volatile SingularAttribute<Schema, Long> id;
	public static volatile SingularAttribute<Schema, String> keyName;
	public static volatile SingularAttribute<Schema, Bus> bus;
	public static volatile SingularAttribute<Schema, String> description;
	public static volatile SetAttribute<Schema, Seat> seats;

}

