package pk.home.busterminal.service.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.security.UserAuthorityDAO;
import pk.home.busterminal.domain.security.UserAuthority;
import pk.home.busterminal.domain.security.UserAuthority_;
import pk.home.busterminal.domain.security.UserAuthoritys;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;

/**
 * Сервис авторизации
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class UserAuthorityService extends ABaseService<UserAuthority> {

	@Autowired
	private UserAuthorityDAO userAuthorityDAO;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
	@Override
	public ABaseDAO<UserAuthority> getAbstractBasicDAO() {
		return userAuthorityDAO;
	}

	/**
	 * Инициализация
	 */
	@PostConstruct
	public void init() {
		try {
			for (UserAuthoritys ua : UserAuthoritys.values()) {
				initRole(ua.name());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Инициализация роли
	 * 
	 * @param role
	 * @throws Exception
	 */
	private void initRole(String role) throws Exception {

		try {
			@SuppressWarnings("unused")
			UserAuthority ua = userAuthorityDAO.executeQueryByNameSingleResult(
					"UserAuthority.findByUsername", role);

		} catch (javax.persistence.NoResultException ex) {
			UserAuthority uat = new UserAuthority();
			uat.setAuthority(role);
			userAuthorityDAO.persist(uat);
		}
	}
	
	@Transactional(readOnly=true)
	public UserAuthority findUserAuthority(UserAuthoritys userAuthoritys) throws Exception {
		
		CriteriaBuilder cb = userAuthorityDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<UserAuthority> cq = cb.createQuery(UserAuthority.class);
		Root<UserAuthority> t = cq.from(UserAuthority.class);

		List<Predicate> criteria = new ArrayList<Predicate>();

		criteria.add(cb.equal(t.get(UserAuthority_.authority), userAuthoritys.name()));
						
		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return userAuthorityDAO.getAllEntities(cb, cq, t).get(0);
	}
	
	
}
