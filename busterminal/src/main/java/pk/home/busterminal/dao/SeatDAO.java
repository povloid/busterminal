package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Seat;


/**
 * DAO class for entity class: Seat
 * Seat - посадочное место
 */
@Repository
@Transactional
public class SeatDAO extends ABaseDAO<Seat> {

	@Override
	protected Class<Seat> getTClass() {
		return Seat.class;
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
	public Object getPrimaryKey(Seat o) {
		return o.getId();
	}

}
