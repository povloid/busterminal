package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.service.OrderService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Order
 * 
 * Order - ордер - операция
 * 
 * @author povloid
 *
 */
public class OrderEditWFControl extends AWFControl<Order, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Order findEdited(Long id) throws Exception {
		return getOrderService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Order newEdited() throws Exception {
		return new Order();
	}

	/**
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getOrderService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getOrderService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getOrderService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
