package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.DriverService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

public class BusEditWFControl extends AWFControl<Bus, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4157216820946820481L;

	private BssType bssType = BssType.TEMPLITE;

	public void setBssType(String s) throws Exception {
		if (s != null && s.trim().length() > 0) {
			bssType = BssType.valueOf(s);
		}
	}

	// ---------------------------------------------------------------------------------------------

	@Override
	public Bus findEdited(Long id) throws Exception {
		return getBusService().find(id);
	}

	@Override
	public Bus newEdited() throws Exception {
		return new Bus();
	}

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	public DriverService getDriverService() {
		return (DriverService) findBean("driverService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setBssType(bssType);
		edited = getBusService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBusService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBusService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
		initDrivers();
	}

	// DocumentTypes -----------------------

	private List<Driver> drivers;

	private void initDrivers() throws Exception {
		drivers = getDriverService().getAllEntities();
	}

	public long getDriver1Id() {
		if (edited != null && edited.getDriver1() != null)
			return edited.getDriver1().getId();
		else
			return 0;
	}

	public void setDriver1Id(long id) {
		try {
			this.edited.setDriver1(getDriverService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getDriver2Id() {
		if (edited != null && edited.getDriver2() != null)
			return edited.getDriver2().getId();
		else
			return 0;
	}

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
