package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.domain.Division_;
import pk.home.busterminal.service.BalanceService;
import pk.home.busterminal.service.DivisionService;
import pk.home.busterminal.service.OrderService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Division Division - отделение
 * 
 * @author povloid
 *
 */
public class DivisionViewWFControl extends AWFBaseLazyLoadTableView<Division>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления отделениями
	 * 
	 * @return
	 */
	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	/**
	 * Сервис управления ордерами
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/**
	 * Сервис управления балансом
	 * 
	 * @return
	 */
	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
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

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		return getDivisionService().count();
	}

	/**
	 * Получить баланс отделения
	 * 
	 * @param division
	 * @return
	 * @throws Exception
	 */
	public Number getBalance(Division division) throws Exception {
		return getBalanceService().getBalance(division);
	}

	/**
	 * Получить балансный ордер
	 * 
	 * @param division
	 * @return
	 * @throws Exception
	 */
	public Number getOrderBalance(Division division) throws Exception {
		return getOrderService().findOrdersDivisionBalance(division);
	}

	/**
	 * Сумма баланса
	 * 
	 * @param division
	 * @return
	 * @throws Exception
	 */
	public Number getBalanceSumm(Division division) throws Exception {
		try {
			return getBalanceService().getBalance(division).doubleValue()
					- getOrderService().findOrdersDivisionBalance(division)
							.doubleValue();
		} catch (NullPointerException ex) {
			return 0;
		}
	}

	/**
	 * Добавить
	 * 
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

}
