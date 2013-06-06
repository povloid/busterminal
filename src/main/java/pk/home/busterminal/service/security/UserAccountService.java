package pk.home.busterminal.service.security;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.security.UserAccountDAO;
import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.domain.security.UserAuthoritys;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;



/**
 * Сервис управления пользователями
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class UserAccountService extends ABaseService<UserAccount> {

	private static final Logger LOG = Logger.getLogger(UserAccountService.class);
	
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
	@Override
	public ABaseDAO<UserAccount> getAbstractBasicDAO() {
		return userAccountDAO;
	}
	
	//@Transactional(propagation = Propagation.REQUIRED ,readOnly = true)
	//public UserAccount findUserAccountByUserName(String username)
	
	
	/**
	 * Проверка на наличие роли у пользователя
	 * @param userAccount
	 * @param userAuthority
	 * @return
	 * @throws Exception
	 */
	public boolean containRole(UserAccount userAccount, UserAuthoritys userAuthoritys) throws Exception {
		
		userAccount = userAccountDAO.find(userAccount.getId());
		
		for(GrantedAuthority ga: userAccount.getAuthorities()){
			LOG.debug(">>>>> containRole: "  + ga.getAuthority() + " - " + userAuthoritys.name());
			
			if((ga.getAuthority().equals(userAuthoritys.name())))
				return true;
		}
		
		return false;
	}
	
	
}
