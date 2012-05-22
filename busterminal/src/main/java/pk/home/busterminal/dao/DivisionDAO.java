package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Division;


/**
 * DAO class for entity class: Division
 * Division - отделение
 */
@Repository
@Transactional
public class DivisionDAO extends ABaseDAO<Division> {

	@Override
	protected Class<Division> getTClass() {
		return Division.class;
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
	public Object getPrimaryKey(Division o) {
		return o.getId();
	}

}
