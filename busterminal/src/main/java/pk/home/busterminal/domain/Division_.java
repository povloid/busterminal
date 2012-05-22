package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: Division
 * Division - отделение
 *
 */
@StaticMetamodel(Division.class)
public class Division_ {
	public static volatile SingularAttribute<Division, Long> id;
	public static volatile SingularAttribute<Division, String> keyName;
	public static volatile SingularAttribute<Division, String> description;
}
