package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusRouteStop
 * BusRouteStop - остановки маршрута
 * 
 * @author povloid
 *
 */
public class BusRouteStopEditWFControl extends AWFControl<BusRouteStop, Long> implements
		Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public BusRouteStop findEdited(Long id) throws Exception {
		return getBusRouteStopService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public BusRouteStop newEdited() throws Exception {
		return new BusRouteStop();
	}

	/**
	 * Сервис управления остановками на маршрутах
	 * 
	 * @return
	 */
	public BusRouteStopService getBusRouteStopService() {
		return (BusRouteStopService) findBean("busRouteStopService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBusRouteStopService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusRouteStopService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getBusRouteStopService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
