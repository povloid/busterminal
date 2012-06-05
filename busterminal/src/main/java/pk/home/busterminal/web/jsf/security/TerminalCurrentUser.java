package pk.home.busterminal.web.jsf.security;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.service.security.UserAccountService;

@Scope("session")
@Component("terminalCurrentUser")
public class TerminalCurrentUser implements Serializable {

	@Autowired
	private UserAccountService userAccountService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3589616731821852097L;

	private UserAccount userAccount;

	public UserAccount getUserAccount() {

		if (this.userAccount == null) {
			Object principal = SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal();
			this.userAccount = (UserAccount) principal;
		}

		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public UserAccountService getUserAccountService() {
		return userAccountService;
	}

	public void setUserAccountService(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

}
