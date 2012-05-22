package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.Balance_;
import pk.home.busterminal.service.BalanceService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Balance
 * Balance - баланс
 */
public class BalanceViewWFControl extends AWFBaseLazyLoadTableView<Balance> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Balance, ?> orderByAttribute = Balance_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Balance_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Balance_.keyName;
		}

		dataModel = getBalanceService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getBalanceService().count();
	}
	
	
	public String add(){
		return "add";
	}
	
	public String edit(){
		return "edit";
	}
	
	public String del(){
		return "del";
	}
	
}
