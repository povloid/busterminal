package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.SchemaService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Schema Schema - схема мест
 * расположения
 * 
 * @author povloid
 *
 */
public class SchemaEditWFControl extends AWFControl<Schema, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Bus bus;	// Автобус

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Schema findEdited(Long id) throws Exception {
		return getSchemaService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Schema newEdited() throws Exception {
		Schema schema = new Schema();
		schema.setBus(bus);
		return schema;
	}

	/**
	 * Сервис автобусов
	 * 
	 * @return
	 */
	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/**
	 * Сервис схем
	 * 
	 * @return
	 */
	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSchemaService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSchemaService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getSchemaService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
		// initBuses();
	}

	// ----------------------------------------------------------------------------------------------------------------

	// private List<Bus> buses;
	//
	// private void initBuses() {
	// try {
	// buses = getBusService().getAllEntities();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	//
	// public long getBusId() {
	//
	// if (this.edited != null && this.edited.getBus() != null)
	// return this.edited.getBus().getId();
	// else
	// return 0;
	// }
	//
	// public void setBusId(long id) {
	// try {
	// this.edited.setBus(getBusService().find(id));
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }


	/**
	 * Установить автобус по id
	 * 
	 * @param busId
	 * @throws Exception
	 */
	public void setSelectedBus(Long busId) throws Exception {
		bus = getBusService().find(busId);
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	// public List<Bus> getBuses() {
	// return buses;
	// }
	//
	// public void setBuses(List<Bus> buses) {
	// this.buses = buses;
	// }

	public Bus getBus() {
		return bus;
	}

	public void setBus(Bus bus) {
		this.bus = bus;
	}

}
