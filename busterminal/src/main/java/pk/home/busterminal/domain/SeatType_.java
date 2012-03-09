package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: SeatType
 * SeatType - тип места
 *
 */
@StaticMetamodel(SeatType.class)
public class SeatType_ {
	public static volatile SingularAttribute<SeatType, Long> id;
	public static volatile SingularAttribute<SeatType, String> keyName;
	public static volatile SingularAttribute<SeatType, String> description;
}
