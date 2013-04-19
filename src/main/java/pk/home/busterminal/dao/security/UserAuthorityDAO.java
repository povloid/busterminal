package pk.home.busterminal.dao.security;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.busterminal.domain.security.UserAuthority;


/**
 * Уровень данных для механизмов авторизации
 * 
 * @author povloid
 *
 */
@Repository
@Transactional
public class UserAuthorityDAO extends ABaseDAO<UserAuthority>{

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
	 * @see pk.home.libs.combine.dao.ABaseDAO#getTClass()
	 */
	@Override
	protected Class<UserAuthority> getTClass() {
		return UserAuthority.class;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.dao.ABaseDAO#getPrimaryKey(java.lang.Object)
	 */
	@Override
	public Object getPrimaryKey(UserAuthority o) {
		return o.getId();
	}

}
