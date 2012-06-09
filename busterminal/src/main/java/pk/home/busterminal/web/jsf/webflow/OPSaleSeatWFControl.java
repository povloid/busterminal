package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.SlideEndEvent;

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
import pk.home.busterminal.web.jsf.security.TerminalCurrentUser;
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

	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
	}

	// Переменные
	private Race race;
	private Seat seat;
	private Order order;

	// Интерфейсные переменные
	private int percent;

	// actions
	// -------------------------------------------------------------------------------------------

	private final static BigDecimal ROUND_VALUE = new BigDecimal(10);

	public void onSlideEnd(SlideEndEvent event) {
		// FacesMessage msg = new FacesMessage("Slide Ended", "Value: " +
		// event.getValue());
		// FacesContext.getCurrentInstance().addMessage(null, msg);
		percent = event.getValue();

		BigDecimal bd = seat.getPrice().add(
				(seat.getPrice()).divide(new BigDecimal(100), 2,
						BigDecimal.ROUND_DOWN).multiply(
						new BigDecimal(-percent)));
		bd.setScale(20, BigDecimal.ROUND_UP);

		bd = bd.divide(ROUND_VALUE, 0, BigDecimal.ROUND_DOWN).multiply(
				ROUND_VALUE);

		order.setActualPrice(bd);
	}

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
		boolean cleanB = false;
		for (BusRouteStop brs : stops1) {
			if (order.getBusRouteStopA() == null
					|| brs.getOrId() > order.getBusRouteStopA().getOrId()) {
				stops2.add(brs);

				if (!cleanB) {
					cleanB = order.getBusRouteStopB() != null
							&& order.getBusRouteStopB().getId() == brs.getId();
				}
			}

		}

		if (cleanB)
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
	 * На диалог подтверждения операции - функция служит для проверки формы
	 * 
	 * @return
	 * @throws Exception
	 */
	public String qSaleSeat() throws Exception {

		if (order.getCustomer() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"Не указан клиент!"));
			throw new Exception("Не указан клиент");
		}

		if (order.getBusRouteStopA() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"Не указана остановка отправления!"));
			throw new Exception("Не указана остановка отправления!");
		}

		if (order.getBusRouteStopB() == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"Не указана остановка прибытия!"));
			throw new Exception("Не указана остановка прибытия!");
		}

		return "qsale-seat";
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
		this.order.setActualPrice(this.seat.getPrice());
		this.order.setUserAccount(getTerminalCurrentUser().getUserAccount());

	}

	/**
	 * Выполнить операцию продажи билета
	 * 
	 * @return
	 * @throws Exception
	 */
	public String executeSaleOp() throws Exception {
		try {

			getOrderService().createTicketSaleOrder(order);

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Операция проведена успешно, создан ордер №"
									+ order.getId(), "Было продано место №"
									+ order.getSeat().getNum() + " на рейс № "
									+ order.getRace().getId()));

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			throw new Exception(e);
		}

		return "success";
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

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}
}
