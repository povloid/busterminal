package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.BalanceType;
import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.BalanceService;
import pk.home.busterminal.service.DivisionService;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.web.jsf.security.TerminalCurrentUser;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Balance Balance - баланс
 */
public class BalanceEditWFControl extends AWFControl<Balance, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
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
	public Balance findEdited(Long id) throws Exception {
		return getBalanceService().find(id);
	}

	@Override
	public Balance newEdited() throws Exception {
		Balance balance = new Balance();
		balance.setUserAccount(getTerminalCurrentUser().getUserAccount());
		balance.setDivision(division);

		return balance;
	}

	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setBalanceType(BalanceType.valueOf(type));

		edited = getBalanceService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited.setBalanceType(BalanceType.valueOf(type));

		edited = getBalanceService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBalanceService().remove(edited);
	}

	private String type = "PLUS";

	public Number getOrderBalance() throws Exception {
		return getOrderService().findOrdersDivisionBalance(division);
	}

	public Number getBalance() throws Exception {
		return getBalanceService().getBalance(division);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
