package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Driver.class)
public abstract class Driver_ {

	public static volatile SingularAttribute<Driver, Long> id;
	public static volatile SingularAttribute<Driver, DocumentType> docupentType;
	public static volatile SingularAttribute<Driver, String> keyName;
	public static volatile SingularAttribute<Driver, String> description;
	public static volatile SingularAttribute<Driver, String> nName;
	public static volatile SingularAttribute<Driver, String> mName;
	public static volatile SingularAttribute<Driver, String> fName;

}

