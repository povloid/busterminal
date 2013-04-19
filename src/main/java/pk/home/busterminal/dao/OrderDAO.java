package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Order;


/**
 * DAO class for entity class: Order
 * Order - ордер - операция
 */
@Repository
@Transactional
public class OrderDAO extends ABaseDAO<Order> {

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

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Object getPrimaryKey(Order o) {
		return o.getId();
	}

}
