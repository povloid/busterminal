package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFRPTWFControl;

public class RPTDriverForm1WFControl extends AWFRPTWFControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Race race;
	private Long raceId;

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	public void setRaceId(Long id) throws Exception {
		this.raceId = id;
		this.race = getRaceService().find(id);
	}

	@Override
	protected MakeResult _makeReport() throws Exception {

		if (race == null)
			throw new Exception("Не указан рейс");

		return new MakeResult(race.getId() + ""

		, "driver_form1_race" + race.getId() + "_"
				+ dateFormatFileNameTime.format(new Date()));
	}

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public Long getRaceId() {
		return raceId;
	}

}
