package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.domain.Customer_;
import pk.home.busterminal.service.CustomerService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Customer Customer - клиент
 * 
 * @author povloid
 *
 */
public class CustomerViewWFControl extends AWFBaseLazyLoadTableView<Customer>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления клиентами
	 * 
	 * @return
	 */
	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Customer, ?> orderByAttribute = Customer_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Customer_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Customer_.keyName;
		} else if (csortField != null && csortField.equals("fName")) {
			orderByAttribute = Customer_.fName;
		} else if (csortField != null && csortField.equals("nName")) {
			orderByAttribute = Customer_.nName;
		} else if (csortField != null && csortField.equals("mName")) {
			orderByAttribute = Customer_.mName;
		}

		dataModel = getCustomerService().getAllEntities((page - 1) * rows,
				rows, orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		return getCustomerService().count();
	}

	/**
	 * ДОбавить
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
