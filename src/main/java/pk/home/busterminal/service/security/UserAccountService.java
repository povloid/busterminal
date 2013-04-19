package pk.home.busterminal.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.security.UserAccountDAO;
import pk.home.busterminal.domain.security.UserAccount;



@Service
@Transactional
public class UserAccountService extends ABaseService<UserAccount> {

	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Override
	public ABaseDAO<UserAccount> getAbstractBasicDAO() {
		return userAccountDAO;
	}
	
	//@Transactional(propagation = Propagation.REQUIRED ,readOnly = true)
	//public UserAccount findUserAccountByUserName(String username) 
	
	

}
