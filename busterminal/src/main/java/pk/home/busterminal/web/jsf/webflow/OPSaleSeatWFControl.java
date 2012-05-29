package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.CustomerService;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.service.RaceService;
import pk.home.busterminal.service.SeatService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;

/**
 * Продажа билета
 * 
 * @author povloid
 * 
 */
public class OPSaleSeatWFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6802892297395121858L;

	// SERVICES
	// -------------------------------------------------------------------------------------------

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}

	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	// Переменные
	private Race race;
	private Seat seat;
	private Order order;

	// actions
	// -------------------------------------------------------------------------------------------

	/**
	 * Поиск рейса
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findRace(long id) throws Exception {
		this.race = getRaceService().find(id);
		if (this.race == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error: Операция продажи не возможна! ",
							"Рейса по коду " + id + " не найдено."));
		}
	}

	/**
	 * Поиск места
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findSeat(long id) throws Exception {
		this.seat = getSeatService().find(id);

		if (this.seat == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: Операция продажи не возможна! ",
									"Продаваемого места по коду " + id
											+ " не найдено."));
		}
	}

	/**
	 * Инициализация
	 */
	@Override
	protected void init0() throws Exception {

	}

	/**
	 * Установить выбранного клиента
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setCustomer(long id) throws Exception {
		this.order.setCustomer(getCustomerService().find(id));
	}

	/**
	 * Продажа места
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saleSeat() throws Exception {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			throw new Exception(e);
		}

		return "saleSeat";
	}

	/**
	 * Создание продажного ордера
	 * 
	 * @throws Exception
	 */
	public void createSaleOrder() throws Exception {
		this.order = new Order();
		this.order.setOrderType(OrderType.TICKET_SALE);
		this.order.setSeat(seat);
		this.order.setRace(race);
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Seat getSeat() {
		return seat;
	}

	public void setSeat(Seat seat) {
		this.seat = seat;
	}

}
