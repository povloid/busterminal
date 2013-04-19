package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Driver;


/**
 * DAO class for entity class: Driver
 * Driver - водитель
 */
@Repository
@Transactional
public class DriverDAO extends ABaseDAO<Driver> {

	@Override
	protected Class<Driver> getTClass() {
		return Driver.class;
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
	public Object getPrimaryKey(Driver o) {
		return o.getId();
	}

}
