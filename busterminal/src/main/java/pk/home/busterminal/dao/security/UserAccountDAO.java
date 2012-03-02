package pk.home.busterminal.dao.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.security.UserAccount;



@Repository
@Transactional
public class UserAccountDAO extends ABaseDAO<UserAccount>{

	
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
	protected Class<UserAccount> getTClass() {
		return UserAccount.class;
	}

	@Override
	public Object getPrimaryKey(UserAccount o) {
		return o.getId();
	}

}
