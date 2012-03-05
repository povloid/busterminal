package pk.home.busterminal.web.jsf.webflow.security;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DualListModel;
import org.springframework.security.authentication.encoding.PasswordEncoder;

import pk.home.libs.combine.web.jsf.flow.AWFControl;
import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.domain.security.UserAuthority;
import pk.home.busterminal.service.security.UserAccountService;
import pk.home.busterminal.service.security.UserAuthorityService;

public class UserAccountWFControl extends AWFControl<UserAccount, Long>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157216820946820481L;

	private String password;

	@Override
	public UserAccount findEdited(Long id) throws Exception {
		return getUserAccountService().find(id);
	}

	@Override
	public UserAccount newEdited() throws Exception {
		return new UserAccount();
	}

	public UserAccountService getUserAccountService() {
		return (UserAccountService) findBean("userAccountService");
	}

	public UserAuthorityService getUserAuthorityService() {
		return (UserAuthorityService) findBean("userAuthorityService");
	}

	public PasswordEncoder getPasswordEncoder() {
		return (PasswordEncoder) findBean("passwordEncoder");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		

		if (password != null && password.length() > 0) {
			edited.setPassword(getPasswordEncoder().encodePassword(password,
					null));
		} else {
			throw new Exception("Password requrired!!!");
		}

		edited = getUserAccountService().persist(edited);
		
		populateEditedresortTypes();
		
		edited = getUserAccountService().merge(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		populateEditedresortTypes();

		if (password != null && password.length() > 0) {
			edited.setPassword(getPasswordEncoder().encodePassword(password,
					null));
		}

		edited = getUserAccountService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		edited.getAuthorities().clear();
		edited = getUserAccountService().merge(edited);
		getUserAccountService().remove(edited);
	}

	
	// init
	// ----------------------------------------------------------------------------------------------
	public void init() throws Exception {
		populateResortTypes();
	}

	// UserAuthority
	// ----------------------------------------------------------------------------------------------------

	private DualListModel<String> roles;

	private Map<String, Long> rolesMap = new HashMap<String, Long>();

	private void populateResortTypes() {
		rolesMap.clear();

		List<String> source = new ArrayList<String>();
		List<String> target = new ArrayList<String>();

		try {
			for (UserAuthority rt : getUserAuthorityService().getAllEntities()) {
				source.add(rt.getAuthority());
				rolesMap.put(rt.getAuthority(), rt.getId());
			}

			for (UserAuthority rt : edited.getUserAuthorities()) {
				target.add(rt.getAuthority());
				rolesMap.put(rt.getAuthority(), rt.getId());
			}

			source.removeAll(target);

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.roles = new DualListModel<String>(source, target);
	}

	private void populateEditedresortTypes() throws Exception {
		edited.getUserAuthorities().clear();

		for (String s : roles.getTarget()) {
			long id = rolesMap.get(s);
			UserAuthority rt = getUserAuthorityService().find(id);
			edited.getUserAuthorities().add(rt);
		}
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public DualListModel<String> getRoles() {
		return roles;
	}

	public void setRoles(DualListModel<String> roles) {
		this.roles = roles;
	}
	
	
	
	

}
