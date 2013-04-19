package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Race;


/**
 * DAO class for entity class: Race
 * Race - рейс
 */
@Repository
@Transactional
public class RaceDAO extends ABaseDAO<Race> {

	@Override
	protected Class<Race> getTClass() {
		return Race.class;
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
	public Object getPrimaryKey(Race o) {
		return o.getId();
	}

}
