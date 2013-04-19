package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.Balance_;
import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.BalanceService;
import pk.home.busterminal.service.DivisionService;
import pk.home.busterminal.service.OrderService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Balance Balance - баланс
 */
public class BalanceViewWFControl extends AWFBaseLazyLoadTableView<Balance>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	private Division division;

	public void findDivision(Long id) throws Exception {
		this.division = getDivisionService().find(id);
	}

	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Balance, ?> orderByAttribute = Balance_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Balance_.id;
		}

		dataModel = getBalanceService().getAllEntities(division,
				(page - 1) * rows, rows, orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return getBalanceService().count(division);
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

	public Number getOrderBalance() throws Exception {
		return getOrderService().findOrdersDivisionBalance(division);
	}

	public Number getBalance() throws Exception {
		return getBalanceService().getBalance(division);
	}

	public Number getBalanceSumm() throws Exception {
		try {
			return getBalanceService().getBalance(division).doubleValue()
					- getOrderService().findOrdersDivisionBalance(division)
							.doubleValue();
		} catch (NullPointerException ex) {
			return 0;
		}
	}

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

}
