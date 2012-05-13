package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Race
 * Race - рейс
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
	
	
	
	

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
