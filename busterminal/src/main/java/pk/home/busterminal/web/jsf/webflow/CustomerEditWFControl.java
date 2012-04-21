package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.service.CustomerService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Customer
 * Customer - клиент
 */
public class CustomerEditWFControl extends AWFControl<Customer, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Customer findEdited(Long id) throws Exception {
		return getCustomerService().find(id);
	}

	@Override
	public Customer newEdited() throws Exception {
		return new Customer();
	}

	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getCustomerService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getCustomerService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getCustomerService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
