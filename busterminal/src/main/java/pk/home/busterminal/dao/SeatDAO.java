package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.dao.ABaseDAO;


@Repository
@Transactional
public class SeatDAO extends ABaseDAO<Seat>{
	
	@PersistenceContext(unitName = "")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	protected Class<Seat> getTClass() {
		return Seat.class;
	}

	@Override
	public Object getPrimaryKey(Seat o) {
		return o.getId();
	}

}