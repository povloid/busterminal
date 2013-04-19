package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: BusRoute BusRoute - Маршрут
 * 
 * 
 * 
 * @author povloid
 *
 */
public class BusRouteEditWFControl extends AWFControl<BusRoute, Long> implements
		Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 6198696373641859442L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public BusRoute findEdited(Long id) throws Exception {
		return getBusRouteService().findWithLazy(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public BusRoute newEdited() throws Exception {
		return new BusRoute();
	}

	/**
	 * Сервис управления маршрутами автобусов
	 * 
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	/**
	 * Сервис управления остановками
	 * 
	 * @return
	 */
	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
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
		edited = getBusRouteService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusRouteService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getBusRouteService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
	}

	// actions
	/**
	 * Выбор остановки
	 * 
	 * @return
	 */
	public String selectBusStop() {
		return "selectBusStop";
	}

	/**
	 * Редактировать
	 * 
	 * @return
	 */
	public String busRouteStopEdit() {
		return "busRouteStopEdit";
	}

	private BusRouteStop selectedBusRouteStop;

	/**
	 * Добавить остановку в список остановок маршрута
	 * 
	 * @param id
	 */
	public void addBusStop(Long id) {

		if (id == null)
			return;

		try {
			BusStop busStop = getBusStopService().find(id);
			BusRouteStop busRouteStop = new BusRouteStop();
			busRouteStop.setBusRoute(edited);
			busRouteStop.setBusStop(busStop);
			busRouteStop.setOrId(edited.getBusRouteStops().size());

			getBusRouteStopService().persist(busRouteStop);

			edited = getBusRouteService().findAndRefresh(edited.getId());

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	/**
	 * Удалить остановку из маршрута
	 */
	public void delBusStop() {
		try {
			getBusRouteStopService().remove(selectedBusRouteStop);

			edited = getBusRouteService().findAndRefresh(edited.getId());

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------
	public BusRouteStop getSelectedBusRouteStop() {
		return selectedBusRouteStop;
	}

	public void setSelectedBusRouteStop(BusRouteStop selectedBusRouteStop) {
		this.selectedBusRouteStop = selectedBusRouteStop;
	}

}
