package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.libs.combine.dao.ABaseDAO;


@Repository
@Transactional
public class BusRouteStopDAO extends ABaseDAO<BusRouteStop> {

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
	protected Class<BusRouteStop> getTClass() {
		return BusRouteStop.class;
	}

	@Override
	public Object getPrimaryKey(BusRouteStop o) {
		return o.getId();
	}

}
