package pk.home.busterminal.web.jsf.security;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.service.security.UserAccountService;

@ManagedBean(name = "terminalCurrentUser")
@SessionScoped
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

}
