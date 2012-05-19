package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Driver
 * Driver - водитель
 *
 */
@StaticMetamodel(Driver.class)
public class Driver_ {
	public static volatile SingularAttribute<Driver, Long> id;
	public static volatile SingularAttribute<Driver, String> keyName;
	public static volatile SingularAttribute<Driver, String> description;
}
