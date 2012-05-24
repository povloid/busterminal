package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

/**
 * Контрол поиска рейсов
 * 
 * @author povloid
 * 
 */
public class FindRaceWFControl extends AWFBasicControl implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2866582993448987413L;

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	private FindRaceModel findRaceModel1 = new FindRaceModel();
	private FindRaceModel findRaceModel2 = new FindRaceModel();

	private int days;

	// actions
	// ------------------------------------------------------------------------------------

	@Override
	protected void init0() throws Exception {
		System.out.println("INIT");
	}

	/**
	 * Выбрать маршрут 1 - Туда
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setBusRoute1(Long id) throws Exception {
		findRaceModel1.setBusRoute(getBusRouteService().find(id));
	}

	/**
	 * Выбрать маршрут 2 - Обратно
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setBusRoute2(Long id) throws Exception {
		findRaceModel2.setBusRoute(getBusRouteService().find(id));
	}

	// ------------------------------------------------------------------------------------

	/**
	 * Класс - табличная модель поиска маршрута
	 * 
	 * @author povloid
	 * 
	 */
	public class FindRaceModel extends WFLazyDataModel<Race> implements
			Serializable {

		private Date date = new Date();
		private BusRoute busRoute;

		/**
		 * 
		 */
		private static final long serialVersionUID = 4970282896915525138L;

		@Override
		protected int count() throws Exception {
			if (date != null && busRoute != null)
				return (int) getRaceService().selectRacesCount(busRoute, false,
						date);
			else
				return 0;
		}

		@Override
		protected List<Race> aload(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, String> filters)
				throws Exception {
			if (date != null && busRoute != null)
				return getRaceService().selectRaces(busRoute, false, date,
						first, pageSize, Race_.dTime, SortOrderType.ASC);
			else
				return null;
		}

		@Override
		public Race getRowData(String rowKey) {
			for (Race rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		@Override
		public Object getRowKey(Race object) {
			return object.getId();
		}

		// get's and set's
		// ----------------------------------------------------------

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public BusRoute getBusRoute() {
			return busRoute;
		}

		public void setBusRoute(BusRoute busRoute) {
			this.busRoute = busRoute;
		}

	}

	// get's and set's
	// ----------------------------------------------------------

	public FindRaceModel getFindRaceModel1() {
		return findRaceModel1;
	}

	public void setFindRaceModel1(FindRaceModel findRaceModel1) {
		this.findRaceModel1 = findRaceModel1;
	}

	public FindRaceModel getFindRaceModel2() {
		return findRaceModel2;
	}

	public void setFindRaceModel2(FindRaceModel findRaceModel2) {
		this.findRaceModel2 = findRaceModel2;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	};

}
