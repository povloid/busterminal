package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Customer;


/**
 * DAO class for entity class: Customer
 * Customer - клиент
 */
@Repository
@Transactional
public class CustomerDAO extends ABaseDAO<Customer> {

	@Override
	protected Class<Customer> getTClass() {
		return Customer.class;
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
	public Object getPrimaryKey(Customer o) {
		return o.getId();
	}

}
