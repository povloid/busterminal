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
 * 
 * Котрол для бормы редактирования баланса
 * 
 * @author povloid
 *
 */
public class BalanceEditWFControl extends AWFControl<Balance, Long> implements
		Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Division division;	// Текущее отделение
	private String type = "PLUS";
	
	/**
	 * Сервис по текущему пользователю
	 * 
	 * @return
	 */
	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
	}

	/**
	 * Сервис по работе с отделениями
	 * 
	 * @return
	 */
	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	/**
	 * Сервис по работе с ордерами
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/**
	 * Сервис по работе с балансом
	 * 
	 * @return
	 */
	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	/**
	 * Поиск отделения по его id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findDivision(Long id) throws Exception {
		this.division = getDivisionService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Balance findEdited(Long id) throws Exception {
		return getBalanceService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Balance newEdited() throws Exception {
		Balance balance = new Balance();
		balance.setUserAccount(getTerminalCurrentUser().getUserAccount());
		balance.setDivision(division);

		return balance;
	}


	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setBalanceType(BalanceType.valueOf(type));

		edited = getBalanceService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited.setBalanceType(BalanceType.valueOf(type));

		edited = getBalanceService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getBalanceService().remove(edited);
	}


	/**
	 * Получить баланс по ордеру
	 * 
	 * @return
	 * @throws Exception
	 */
	public Number getOrderBalance() throws Exception {
		return getOrderService().findOrdersDivisionBalance(division);
	}

	
	/**
	 * Получить баланс
	 * 
	 * @return
	 * @throws Exception
	 */
	public Number getBalance() throws Exception {
		return getBalanceService().getBalance(division);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
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
