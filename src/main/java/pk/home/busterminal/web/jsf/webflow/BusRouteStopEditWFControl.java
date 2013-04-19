package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusRouteStop
 * BusRouteStop - остановки маршрута
 */
public class BusRouteStopEditWFControl extends AWFControl<BusRouteStop, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public BusRouteStop findEdited(Long id) throws Exception {
		return getBusRouteStopService().find(id);
	}

	@Override
	public BusRouteStop newEdited() throws Exception {
		return new BusRouteStop();
	}

	public BusRouteStopService getBusRouteStopService() {
		return (BusRouteStopService) findBean("busRouteStopService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBusRouteStopService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusRouteStopService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBusRouteStopService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
