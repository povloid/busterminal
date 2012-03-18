package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: BusStop
 * BusStop - Остановка
 *
 */
@StaticMetamodel(BusStop.class)
public class BusStop_ {
	public static volatile SingularAttribute<BusStop, Long> id;
	public static volatile SingularAttribute<BusStop, String> keyName;
	public static volatile SingularAttribute<BusStop, String> description;
}
