package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.web.jsf.security.TerminalCurrentUser;
import pk.home.libs.combine.web.jsf.flow.AWFWizart;

public class OPReturnWFControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4938481481919621181L;

	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
	}

	@Override
	protected void init0() throws Exception {

	}

	@Override
	protected void closeImpl() throws Exception {

	}

	@Override
	protected void backImpl() throws Exception {

	}

	@Override
	protected void nextImpl() throws Exception {
		if (order == null)
			throw new Exception("Ордер не обозначен");
	}

	private Order order;
	private Order retOrder = new Order();

	private long orderId;

	/**
	 * Поиск ордера
	 * 
	 * @return
	 */
	public String findOrder() {
		try {

			this.order = getOrderService().find(orderId);

			if (this.order == null) {
				FacesContext.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO,
										"INFO: ", "Оперция под номером "
												+ orderId + " не найден"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return "findOrder";
	}

	/**
	 * Возврат
	 * 
	 * @return
	 */
	public void executeReturn() throws Exception {
		try {

			this.retOrder.setUserAccount(getTerminalCurrentUser()
					.getUserAccount());
			this.retOrder.setPreviousOrder(order);
			this.retOrder.setOrderType(OrderType.TICKET_RETURN);

			this.retOrder = getOrderService().createTicketReturnOrder(retOrder);

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			throw new Exception(e);
		}
	}

	// get's and set's
	// -----------------------------------------------------------------------------------------------------------------

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Order getRetOrder() {
		return retOrder;
	}

	public void setRetOrder(Order retOrder) {
		this.retOrder = retOrder;
	}

	public long getOrderId() {
		return orderId;
	}

	public void setOrderId(long orderId) {
		this.orderId = orderId;
	}

}
