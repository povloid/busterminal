package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Balance;


/**
 * DAO class for entity class: Balance
 * Balance - баланс
 */
@Repository
@Transactional
public class BalanceDAO extends ABaseDAO<Balance> {

	@Override
	protected Class<Balance> getTClass() {
		return Balance.class;
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
	public Object getPrimaryKey(Balance o) {
		return o.getId();
	}

}
