package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFRPTWFControl;

/**
 * Отчет для водителя - список пассажиров
 * 
 * @author povloid
 *
 */
public class RPTDriverForm1WFControl extends AWFRPTWFControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Race race;	// Рейс
	private Long raceId;// id реса

	/**
	 * Сервис рейсов
	 * 
	 * @return
	 */
	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	/**
	 * Сервис ордеров
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/**
	 * Найти рейс по id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setRaceId(Long id) throws Exception {
		this.raceId = id;
		this.race = getRaceService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFRPTWFControl#_makeReport()
	 */
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
