package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Schema;


/**
 * DAO class for entity class: Schema
 * Schema - схема мест расположения
 * 
 * @author povloid
 *
 */
@Repository
@Transactional
public class SchemaDAO extends ABaseDAO<Schema> {

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getTClass()
	 */
	@Override
	protected Class<Schema> getTClass() {
		return Schema.class;
	}

	/**
	 * EntityManager injected by Spring for persistence unit
	 * 
	 */
	@PersistenceContext(unitName = "")
	private EntityManager entityManager;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getEntityManager()
	 */
	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getPrimaryKey(java.lang.Object)
	 */
	@Override
	public Object getPrimaryKey(Schema o) {
		return o.getId();
	}

}
