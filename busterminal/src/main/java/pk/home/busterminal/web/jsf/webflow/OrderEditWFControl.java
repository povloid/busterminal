package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.service.OrderService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Order
 * Order - ордер - операция
 */
public class OrderEditWFControl extends AWFControl<Order, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Order findEdited(Long id) throws Exception {
		return getOrderService().find(id);
	}

	@Override
	public Order newEdited() throws Exception {
		return new Order();
	}

	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getOrderService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getOrderService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getOrderService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
