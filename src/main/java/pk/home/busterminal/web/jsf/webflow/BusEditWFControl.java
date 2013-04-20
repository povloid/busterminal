package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.DriverService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * Контрол редактирования автобуса
 * 
 * @author povloid
 *
 */
public class BusEditWFControl extends AWFControl<Bus, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Тип записи
	private BssType bssType = BssType.TEMPLITE;

	/**
	 * Установить тип по строчному значению
	 * 
	 * @param s
	 * @throws Exception
	 */
	public void setBssType(String s) throws Exception {
		if (s != null && s.trim().length() > 0) {
			bssType = BssType.valueOf(s);
		}
	}

	// ---------------------------------------------------------------------------------------------

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Bus findEdited(Long id) throws Exception {
		return getBusService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Bus newEdited() throws Exception {
		return new Bus();
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
	 * Сервис управления водителями
	 * 
	 * @return
	 */
	public DriverService getDriverService() {
		return (DriverService) findBean("driverService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setBssType(bssType);
		edited = getBusService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getBusService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
		initDrivers();
	}

	// DocumentTypes -----------------------


	
	/**
	 * Список водителей 
	 */
	private List<Driver> drivers;

	/**
	 * Инициализация списка водителей
	 * 
	 * @throws Exception
	 */
	private void initDrivers() throws Exception {
		drivers = getDriverService().getAllEntities();
	}

	/**
	 * Получить id водителя 1
	 * 
	 * @return
	 */
	public long getDriver1Id() {
		if (edited != null && edited.getDriver1() != null)
			return edited.getDriver1().getId();
		else
			return 0;
	}

	/**
	 * Установить id водителя 1
	 * 
	 * @param id
	 */
	public void setDriver1Id(long id) {
		try {
			this.edited.setDriver1(getDriverService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Получить id водителя 2
	 * 
	 * @return
	 */
	public long getDriver2Id() {
		if (edited != null && edited.getDriver2() != null)
			return edited.getDriver2().getId();
		else
			return 0;
	}

	/**
	 * Установить id водителя 2
	 * 
	 * @param id
	 */
	public void setDriver2Id(long id) {
		try {
			this.edited.setDriver2(getDriverService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public BssType getBssType() {
		return bssType;
	}

	public void setBssType(BssType bssType) {
		this.bssType = bssType;
	}

	public List<Driver> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<Driver> drivers) {
		this.drivers = drivers;
	}

}
