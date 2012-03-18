package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.BusRoute;


/**
 * DAO class for entity class: BusRoute
 * BusRoute - Маршрут
 */
@Repository
@Transactional
public class BusRouteDAO extends ABaseDAO<BusRoute> {

	@Override
	protected Class<BusRoute> getTClass() {
		return BusRoute.class;
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
	public Object getPrimaryKey(BusRoute o) {
		return o.getId();
	}

}
