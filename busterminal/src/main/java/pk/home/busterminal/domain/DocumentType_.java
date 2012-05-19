package pk.home.busterminal.domain;


import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

/**
 * Static metamodel for entity class: DocumentType
 * DocumentType - тип документа
 *
 */
@StaticMetamodel(DocumentType.class)
public class DocumentType_ {
	public static volatile SingularAttribute<DocumentType, Long> id;
	public static volatile SingularAttribute<DocumentType, String> keyName;
	public static volatile SingularAttribute<DocumentType, String> description;
}
