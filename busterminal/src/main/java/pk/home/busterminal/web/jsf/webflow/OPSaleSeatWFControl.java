package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
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

	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	public BusRouteStopService getBusRouteStopService() {
		return (BusRouteStopService) findBean("busRouteStopService");
	}

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
		initStops();
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
	 * Выбор участка пути
	 */

	private List<BusRouteStop> stops1;
	private List<BusRouteStop> stops2;

	/**
	 * Инициализация первого списка остановок
	 * 
	 * @throws Exception
	 */
	public void initStops() throws Exception {

		if (stops1 == null) {
			stops1 = getBusRouteService().findWithLazy(
					race.getBusRoute().getId()).getBusRouteStops();
		}

		System.out.println(order.getBusRouteStopA());

		stops2 = new ArrayList<BusRouteStop>();
		for (BusRouteStop brs : stops1) {
			if (order.getBusRouteStopA() == null
					|| brs.getOrId() > order.getBusRouteStopA().getOrId()) {
				stops2.add(brs);
			}
		}

		order.setBusRouteStopB(null);

	}

	public long getBusRouteStopAId() {
		if (order.getBusRouteStopA() != null)
			return order.getBusRouteStopA().getId();
		else
			return 0;
	}

	public long getBusRouteStopBId() {
		if (order.getBusRouteStopB() != null)
			return order.getBusRouteStopB().getId();
		else
			return 0;
	}

	public void setBusRouteStopAId(long id) {
		try {
			this.order.setBusRouteStopA(getBusRouteStopService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBusRouteStopBId(long id) {
		try {
			this.order.setBusRouteStopB(getBusRouteStopService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// -----------------------------------------------------------------------------------------------------------------

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
		this.order.setActualPrice(this.seat.getPrice());
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

	public List<BusRouteStop> getStops1() {
		return stops1;
	}

	public void setStops1(List<BusRouteStop> stops1) {
		this.stops1 = stops1;
	}

	public List<BusRouteStop> getStops2() {
		return stops2;
	}

	public void setStops2(List<BusRouteStop> stops2) {
		this.stops2 = stops2;
	}

}
