package pk.home.busterminal.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.Schema;
import pk.home.libs.combine.dao.ABaseDAO;


@Repository
@Transactional
public class SchemaDAO extends ABaseDAO<Schema>{
	
	@PersistenceContext(unitName = "")
	private EntityManager entityManager;

	@Override
	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	protected Class<Schema> getTClass() {
		return Schema.class;
	}

	@Override
	public Object getPrimaryKey(Schema o) {
		return o.getId();
	}

}