package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.primefaces.event.DateSelectEvent;
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
	private static final long serialVersionUID = 1L;
	
	private final static int DAY = 1000 * 60 * 60 * 24;	// Один день
	
	
	private FindRaceModel findRaceModel1 = new FindRaceModel();	// Модель для таблици рейсов слева
	private FindRaceModel findRaceModel2 = new FindRaceModel();	// Модель для таблици рейсов справа
	
	private Race selected1;	// выбранный рейс в левой половине
	private Race selected2;	// выбранный рейс в правой половине
	
	private int days = 0;	// Разница в сутках между рейсом "туда" и "обратно"

	/**
	 * Сервис управления рейсами
	 * @return
	 */
	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	/**
	 * Сервис управления остановками
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}


	// actions
	// ------------------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFBasicControl#init0()
	 */
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


	/**
	 * Смена даты 1
	 * 
	 * @param event
	 */
	public void handleDateSelect1(DateSelectEvent event) {
		System.out.println("handleDateSelect1");

		Date eDate = event.getDate();

		findRaceModel2.setDate(new Date(eDate.getTime() + (days) * DAY));
		
		System.out.println(new Date(eDate.getTime() + (days) * DAY));
	}

	/**
	 * Смена даты 2
	 * 
	 * @param event
	 */
	public void handleDateSelect2(DateSelectEvent event) {
		System.out.println("handleDateSelect2");

		Date eDate = event.getDate();

		System.out
				.println(eDate.getTime() - findRaceModel1.getDate().getTime());

		days = (int) ((eDate.getTime() - findRaceModel1.getDate().getTime()) / DAY);
		System.out.println(days);
	}

	/**
	 * Установка разности дней спинером
	 */
	public void handleSpinnerSelect() {
		System.out.println("handleSpinnerSelect");
		findRaceModel2.setDate(new Date(findRaceModel1.getDate().getTime()
				+ (days) * DAY));
		// System.out.println(new Date(findRaceModel1.getDate().getTime() + days
		// * DAY));
	}

	/**
	 * Прибавить день на 1
	 */
	public void incDay() {
		++days;
		handleSpinnerSelect();
	}

	/**
	 * Уменьшить день на 1
	 */
	public void decDay() {
		if (days > 0)
			--days;

		handleSpinnerSelect();
	}

	// ------------------------------------------------------------------------------------

	/**
	 * Класс - табличная модель поиска маршрута
	 * 
	 * @author povloid
	 * 
	 */
	/**
	 * @author povloid
	 *
	 */
	public class FindRaceModel extends WFLazyDataModel<Race> implements
			Serializable {

		private Date date;			// дата для запроса
		private BusRoute busRoute;	// выбранный рейс

		{
			java.util.Calendar cal = GregorianCalendar.getInstance();
			// Получаем полночь
			cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
			cal.set(java.util.Calendar.MINUTE, 0);
			cal.set(java.util.Calendar.SECOND, 0);
			cal.set(java.util.Calendar.MILLISECOND, 0);
			date = cal.getTime();
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#count(java.util.Map)
		 */
		@Override
		protected int count(Map<String, String> filters) throws Exception {
			if (date != null && busRoute != null)
				return (int) getRaceService().selectRacesCount(busRoute, false,
						date);
			else
				return 0;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#aload(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
		 */
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

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowData(java.lang.String)
		 */
		@Override
		public Race getRowData(String rowKey) {
			if (getDataList() != null)
				for (Race rd : getDataList()) {
					if (rd.getId() == Long.parseLong(rowKey))
						return rd;
				}
			return null;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(Race object) {

			return object != null ? object.getId() : null;
		}

		// get's and set's
		// ----------------------------------------------------------
		public Date getDate() {
			return date;
		}

		//@Override
		//public void setRowIndex(int rowIndex) {
		//	super.setRowIndex(rowIndex == 0 ? -1 : rowIndex);
		//}

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
	}

	public Race getSelected1() {
		return selected1;
	}

	public void setSelected1(Race selected1) {
		this.selected1 = selected1;
	}

	public Race getSelected2() {
		return selected2;
	}

	public void setSelected2(Race selected2) {
		this.selected2 = selected2;
	}

}
