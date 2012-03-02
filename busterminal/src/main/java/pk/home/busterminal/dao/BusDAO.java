package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.Bus;
import pk.home.libs.combine.dao.ABaseDAO;

@Repository
@Transactional
public class BusDAO extends ABaseDAO<Bus>{
	
	@PersistenceContext(unitName = "")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	protected Class<Bus> getTClass() {
		return Bus.class;
	}

	@Override
	public Object getPrimaryKey(Bus o) {
		return o.getId();
	}

}
