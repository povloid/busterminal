package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Race Race - рейс
 * 
 * @author povloid
 *
 */
public class RaceEditWFControl extends AWFControl<Race, Long> implements
		Serializable {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Race findEdited(Long id) throws Exception {
		return getRaceService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Race newEdited() throws Exception {
		return new Race();
	}

	/**
	 * Сервис управления рейсами
	 * 
	 * @return
	 */
	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	/**
	 * Сервис управления автобусами
	 * 
	 * @return
	 */
	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/**
	 * Сервис управления маршрутами
	 * 
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getRaceService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getRaceService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getRaceService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
	}

	// actions
	// -------------------------------------------------------------------------------------------------

	/**
	 * Выбор автобуса
	 * 
	 * @return
	 */
	public String selectBus() {
		return "selectBus";
	}

	/**
	 * Указать автобус
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setBus(long id) throws Exception {
		try {
			Bus templite = getBusService().find(id);
			edited = getRaceService().setBusWorkCopy(edited, templite);

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
			throw new Exception(e);
		}
	}

	// ...

	/**
	 * Выбрать автобусный маршрут
	 * 
	 * @return
	 */
	public String selectBusRoute() {
		return "selectBusRoute";
	}

	/**
	 * Указать автобусный маршрут
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setBusRoute(long id) throws Exception {
		edited.setBusRoute(getBusRouteService().find(id));
	}

	// ...

	// Edit work bus

	/**
	 * Перейти в редактор шаблонов
	 * 
	 * @return
	 */
	public String busTempliteMasterEdit() {
		return "busTempliteMaster";
	}

	// ..

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
