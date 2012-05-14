package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Race Race - рейс
 */
public class RaceEditWFControl extends AWFControl<Race, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Race findEdited(Long id) throws Exception {
		return getRaceService().find(id);
	}

	@Override
	public Race newEdited() throws Exception {
		return new Race();
	}

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getRaceService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getRaceService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getRaceService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// actions
	// -------------------------------------------------------------------------------------------------

	// Bus
	public String selectBus() {
		return "selectBus";
	}

	public void setBus(long id) throws Exception {
		Bus templite = getBusService().find(id);
		edited = getRaceService().setBusWorkCopy(edited, templite);
	}

	// ...

	// BusRoute
	public String selectBusRoute() {
		return "selectBusRoute";
	}

	public void setBusRoute(long id) throws Exception {
		edited.setBusRoute(getBusRouteService().find(id));
	}
	// ...

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
