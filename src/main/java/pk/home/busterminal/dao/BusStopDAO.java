package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.BusStop;


/**
 * DAO class for entity class: BusStop
 * BusStop - Остановка
 */
@Repository
@Transactional
public class BusStopDAO extends ABaseDAO<BusStop> {

	@Override
	protected Class<BusStop> getTClass() {
		return BusStop.class;
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
	public Object getPrimaryKey(BusStop o) {
		return o.getId();
	}

}
