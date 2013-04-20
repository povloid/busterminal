package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.web.jsf.security.TerminalCurrentUser;
import pk.home.libs.combine.web.jsf.flow.AWFWizart;

/**
 * Контрол для операции возврата билета
 * 
 * @author povloid
 *
 */
public class OPReturnWFControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Order order;	// Ордер покупки билета
	private Order retOrder = new Order();	// Возвратный ордер
	
	private long orderId;	// id ордера покупки билета

	/**
	 * сервис управления ордерами
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/**
	 * Сервис текущего пользователя
	 * 
	 * @return
	 */
	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#init0()
	 */
	@Override
	protected void init0() throws Exception {

	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#closeImpl()
	 */
	@Override
	protected void closeImpl() throws Exception {

	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#backImpl()
	 */
	@Override
	protected void backImpl() throws Exception {

	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#nextImpl()
	 */
	@Override
	protected void nextImpl() throws Exception {
		if (order == null)
			throw new Exception("Ордер не обозначен");
	}


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
