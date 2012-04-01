package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusRoute BusRoute - Маршрут
 */
public class BusRouteEditWFControl extends AWFControl<BusRoute, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public BusRoute findEdited(Long id) throws Exception {
		return getBusRouteService().findWithLazy(id);
	}

	@Override
	public BusRoute newEdited() throws Exception {
		return new BusRoute();
	}

	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	public BusRouteStopService getBusRouteStopService() {
		return (BusRouteStopService) findBean("busRouteStopService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBusRouteService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusRouteService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBusRouteService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// actions
	/**
	 * Выбор остановки
	 * 
	 * @return
	 */
	public String selectBusStop() {
		return "selectBusStop";
	}

	/**
	 * Добавить остановку в список
	 * @param id
	 */
	public void addBusStop(Long id) {
		try {
			BusStop busStop = getBusStopService().find(id);
			BusRouteStop busRouteStop = new BusRouteStop();
			busRouteStop.setBusRoute(edited);
			busRouteStop.setBusStop(busStop);
			busRouteStop.setOrId(edited.getBusRouteStops().size());

			getBusRouteStopService().persist(busRouteStop);

			edited = getBusRouteService().findAndRefresh(edited.getId());

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
