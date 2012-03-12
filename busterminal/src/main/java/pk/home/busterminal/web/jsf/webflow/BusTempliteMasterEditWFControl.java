package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import org.apache.commons.lang.NotImplementedException;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

public class BusTempliteMasterEditWFControl extends AWFControl<Bus, Long>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189205660540524184L;

	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	@Override
	public Bus findEdited(Long id) throws Exception {
		return service().getBusService().findWithLazy(id);
	}

	@Override
	public Bus newEdited() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		throw new NotImplementedException();
	}

}
