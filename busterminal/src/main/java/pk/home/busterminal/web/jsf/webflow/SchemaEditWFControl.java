package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.SchemaService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Schema Schema - схема мест
 * расположения
 */
public class SchemaEditWFControl extends AWFControl<Schema, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Schema findEdited(Long id) throws Exception {
		return getSchemaService().find(id);
	}

	@Override
	public Schema newEdited() throws Exception {
		return new Schema();
	}

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSchemaService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSchemaService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSchemaService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	public void init() throws Exception {
		initBuses();
	}

	// ----------------------------------------------------------------------------------------------------------------

	private List<Bus> buses;

	private void initBuses() {
		try {
			buses = getBusService().getAllEntities();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public long getBusId() {

		if (this.edited != null && this.edited.getBus() != null)
			return this.edited.getBus().getId();
		else
			return 0;
	}

	public void setBusId(long id) {
		try {
			this.edited.setBus(getBusService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public List<Bus> getBuses() {
		return buses;
	}

	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

}
