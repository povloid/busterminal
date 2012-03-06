package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.service.BusService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

public class BusEditWFControl extends AWFControl<Bus, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157216820946820481L;

	@Override
	public Bus findEdited(Long id) throws Exception {
		return getBusService().find(id);
	}

	@Override
	public Bus newEdited() throws Exception {
		return new Bus();
	}

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBusService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBusService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	public void init() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
