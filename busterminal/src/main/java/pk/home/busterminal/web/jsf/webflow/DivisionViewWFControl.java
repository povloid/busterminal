package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.domain.Division_;
import pk.home.busterminal.service.BalanceService;
import pk.home.busterminal.service.DivisionService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Division Division - отделение
 */
public class DivisionViewWFControl extends AWFBaseLazyLoadTableView<Division>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Division, ?> orderByAttribute = Division_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Division_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Division_.keyName;
		}

		dataModel = getDivisionService().getAllEntities((page - 1) * rows,
				rows, orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return getDivisionService().count();
	}

	public Number getBalance(Division division) throws Exception {
		return getBalanceService().getBalance(division);
	}

	public String add() {
		return "add";
	}

	public String edit() {
		return "edit";
	}

	public String del() {
		return "del";
	}

}
