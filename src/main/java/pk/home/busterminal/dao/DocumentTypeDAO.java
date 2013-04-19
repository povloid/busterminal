package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.DocumentType;


/**
 * DAO class for entity class: DocumentType
 * DocumentType - тип документа
 */
@Repository
@Transactional
public class DocumentTypeDAO extends ABaseDAO<DocumentType> {

	@Override
	protected Class<DocumentType> getTClass() {
		return DocumentType.class;
	}

	/**
	 * EntityManager injected by Spring for persistence unit
	 * 
	 */
	@PersistenceContext(unitName = "")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Object getPrimaryKey(DocumentType o) {
		return o.getId();
	}

}
