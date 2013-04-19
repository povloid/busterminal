package pk.home.busterminal.domain;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DocumentType.class)
public abstract class DocumentType_ {

	public static volatile SingularAttribute<DocumentType, Long> id;
	public static volatile SingularAttribute<DocumentType, String> keyName;
	public static volatile SingularAttribute<DocumentType, String> description;

}

