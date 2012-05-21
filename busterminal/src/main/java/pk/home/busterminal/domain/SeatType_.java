package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SeatType.class)
public abstract class SeatType_ {

	public static volatile SingularAttribute<SeatType, Long> id;
	public static volatile SingularAttribute<SeatType, String> keyName;
	public static volatile SingularAttribute<SeatType, String> description;
	public static volatile SingularAttribute<SeatType, String> seatIcon;
	public static volatile SingularAttribute<SeatType, Boolean> sold;

}

