package pk.home.busterminal.web.jsf.security;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView;
import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.domain.security.UserAccount_;
import pk.home.busterminal.service.security.UserAccountService;

@Scope("session")
@Component("userAccountViewControl")
public class UserAccountViewControl extends ABaseLazyLoadTableView<UserAccount>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8914379362921153006L;

	@Autowired
	private UserAccountService userAccountService;

	@Override
	protected void aInit() throws Exception {
		SingularAttribute<UserAccount, ?> orderByAttribute = UserAccount_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = UserAccount_.id;
		} else if (csortField != null && csortField.equals("username")) {
			orderByAttribute = UserAccount_.username;
		} else if (csortField != null && csortField.equals("division")) {
			orderByAttribute = UserAccount_.division;
		}

		dataModel = userAccountService.getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return userAccountService.count();
	}

}
