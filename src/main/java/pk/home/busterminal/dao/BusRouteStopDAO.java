package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.libs.combine.dao.ABaseDAO;


/**
 * Слой сохранения для остановок на маршруте
 * 
 * @author povloid
 *
 */
@Repository
@Transactional
public class BusRouteStopDAO extends ABaseDAO<BusRouteStop> {

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
	 * @see pk.home.libs.combine.dao.ABaseDAO#getTClass()
	 */
	@Override
	protected Class<BusRouteStop> getTClass() {
		return BusRouteStop.class;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getPrimaryKey(java.lang.Object)
	 */
	@Override
	public Object getPrimaryKey(BusRouteStop o) {
		return o.getId();
	}

}
