package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.service.BusRouteService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusRoute
 * BusRoute - Маршрут
 */
public class BusRouteEditWFControl extends AWFControl<BusRoute, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public BusRoute findEdited(Long id) throws Exception {
		return getBusRouteService().find(id);
	}

	@Override
	public BusRoute newEdited() throws Exception {
		return new BusRoute();
	}

	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
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

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
