package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.Items;


/**
 * DAO class for entity class: Items
 * Items - запись ордера
 */
@Repository
@Transactional
public class ItemsDAO extends ABaseDAO<Items> {

	@Override
	protected Class<Items> getTClass() {
		return Items.class;
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
	public Object getPrimaryKey(Items o) {
		return o.getId();
	}

}
