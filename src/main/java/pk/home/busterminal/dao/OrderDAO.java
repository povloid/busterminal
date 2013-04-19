package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Order;

/**
 * DAO class for entity class: Order Order - ордер - операция
 * 
 * @author povloid
 *
 */
@Repository
@Transactional
public class OrderDAO extends ABaseDAO<Order> {

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getTClass()
	 */
	@Override
	protected Class<Order> getTClass() {
		return Order.class;
	}

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
	 * @see pk.home.libs.combine.dao.ABaseDAO#getPrimaryKey(java.lang.Object)
	 */
	@Override
	public Object getPrimaryKey(Order o) {
		return o.getId();
	}

}
