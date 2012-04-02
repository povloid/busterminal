package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Race
 * Race - рейс
 *
 */
@StaticMetamodel(Race.class)
public class Race_ {
	public static volatile SingularAttribute<Race, Long> id;
	public static volatile SingularAttribute<Race, String> keyName;
	public static volatile SingularAttribute<Race, String> description;
}
