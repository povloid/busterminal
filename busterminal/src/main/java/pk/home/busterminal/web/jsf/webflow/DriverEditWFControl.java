package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.service.DriverService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Driver
 * Driver - водитель
 */
public class DriverEditWFControl extends AWFControl<Driver, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Driver findEdited(Long id) throws Exception {
		return getDriverService().find(id);
	}

	@Override
	public Driver newEdited() throws Exception {
		return new Driver();
	}

	public DriverService getDriverService() {
		return (DriverService) findBean("driverService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getDriverService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getDriverService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getDriverService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
