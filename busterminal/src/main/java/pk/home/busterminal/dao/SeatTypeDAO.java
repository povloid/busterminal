package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.SeatType;


/**
 * DAO class for entity class: SeatType
 * SeatType - тип места
 */
@Repository
@Transactional
public class SeatTypeDAO extends ABaseDAO<SeatType> {

	@Override
	protected Class<SeatType> getTClass() {
		return SeatType.class;
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
	public Object getPrimaryKey(SeatType o) {
		return o.getId();
	}

}
