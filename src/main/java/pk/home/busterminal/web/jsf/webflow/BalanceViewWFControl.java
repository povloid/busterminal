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
 * 
 * @author povloid
 */
public class BalanceViewWFControl extends AWFBaseLazyLoadTableView<Balance>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Division division;	// Отделение
	
	/**
	 * Сервис работы с балансом
	 * @return
	 */
	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	/**
	 * Сервис работы с отделением
	 * 
	 * @return
	 */
	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	/**
	 * Сервис работы с ордером
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	
	/**
	 * Получить баланс отделения
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
	

	/**
	 * Найти отделение 
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findDivision(Long id) throws Exception {
		this.division = getDivisionService().find(id);
	}
	
	
	/**
	 * Получить сумму баланса
	 * 
	 * @return
	 * @throws Exception
	 */
	public Number getBalanceSumm() throws Exception {
		try {
			return getBalanceService().getBalance(division).doubleValue()
					- getOrderService().findOrdersDivisionBalance(division)
							.doubleValue();
		} catch (NullPointerException ex) {
			return 0;
		}
	}
	

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Balance, ?> orderByAttribute = Balance_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Balance_.id;
		}

		dataModel = getBalanceService().getAllEntities(division,
				(page - 1) * rows, rows, orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		return getBalanceService().count(division);
	}

	/**
	 * ДОбавить
	 * @return
	 */
	public String add() {
		return "add";
	}

	
	/**
	 * Редактировать
	 * 
	 * @return
	 */
	public String edit() {
		return "edit";
	}

	
	/**
	 * Удалить
	 * 
	 * @return
	 */
	public String del() {
		return "del";
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

}
