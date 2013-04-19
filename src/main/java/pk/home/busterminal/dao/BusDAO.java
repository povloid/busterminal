package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.Bus;
import pk.home.libs.combine.dao.ABaseDAO;

/**
 * Персистентный слой для автобусов
 * 
 * @author povloid
 *
 */
@Repository
@Transactional
public class BusDAO extends ABaseDAO<Bus>{
	
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
	 * @see pk.home.libs.combine.dao.ABaseDAO#getTClass()
	 */
	@Override
	protected Class<Bus> getTClass() {
		return Bus.class;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getPrimaryKey(java.lang.Object)
	 */
	@Override
	public Object getPrimaryKey(Bus o) {
		return o.getId();
	}

}
