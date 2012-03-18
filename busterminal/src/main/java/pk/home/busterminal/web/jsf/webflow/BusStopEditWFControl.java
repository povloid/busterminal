package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusStop
 * BusStop - Остановка
 */
public class BusStopEditWFControl extends AWFControl<BusStop, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public BusStop findEdited(Long id) throws Exception {
		return getBusStopService().find(id);
	}

	@Override
	public BusStop newEdited() throws Exception {
		return new BusStop();
	}

	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBusStopService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusStopService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBusStopService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
