package pk.home.busterminal.service.security;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.security.UserAuthorityDAO;
import pk.home.busterminal.domain.security.UserAuthority;
import pk.home.busterminal.domain.security.UserAuthoritys;

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
}
