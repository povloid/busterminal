package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Seat.class)
public abstract class Seat_ {

	public static volatile SingularAttribute<Seat, Long> id;
	public static volatile SingularAttribute<Seat, Schema> schema;
	public static volatile SingularAttribute<Seat, Short> num;
	public static volatile SingularAttribute<Seat, String> description;
	public static volatile SingularAttribute<Seat, Short> sy;
	public static volatile SingularAttribute<Seat, Short> sx;
	public static volatile SingularAttribute<Seat, Integer> version;

}

