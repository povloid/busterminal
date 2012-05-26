package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;

/**
 * Sale - продажа билета
 * 
 * @author povloid
 * 
 */
public class OPSaleWFControl extends AWFBasicControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6702785294921753935L;

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	private Race race;

	/**
	 * Поиск рейса
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findRace(long id) throws Exception {
		this.race = getRaceService().find(id);
		System.out.println("RACE " + id + ": " + race);
	}

	// actions
	// -------------------------------------------------------------------------------------------

	@Override
	protected void init0() throws Exception {
		System.out.println("INIT");
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

}
