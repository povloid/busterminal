package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Schema_;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.SchemaService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Schema Schema - схема мест
 * расположения
 */
public class SchemaViewWFControl extends AWFBaseLazyLoadTableView<Schema>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	@Override
	protected void aInit() throws Exception {
		initBuses();

		SingularAttribute<Schema, ?> orderByAttribute = Schema_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Schema_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Schema_.keyName;
		} else if (csortField != null && csortField.equals("Bus")) {
			orderByAttribute = Schema_.bus;
		}

		dataModel = getSchemaService().getAllEntities(selectedBus,
				(page - 1) * rows, rows, orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return getSchemaService().count(selectedBus);
	}

	public String add() {
		return "add";
	}

	public String edit() {
		return "edit";
	}

	public String del() {
		return "del";
	}

	// -----------------------------------------------------------------------------------------------------------------

	private BssType bssType = BssType.TEMPLITE;

	public void setBssType(String s) throws Exception {
		if (s != null && s.trim().length() > 0) {
			bssType = BssType.valueOf(s);
		}
	}

	private List<Bus> buses;
	private Bus selectedBus;
	private Long selectedBusId;

	public void setSelectedBusId(Long selectedBusId) {
		this.selectedBusId = selectedBusId;

		if (buses == null)
			return;

		if (selectedBusId == 0l) {
			selectedBus = null;
			selectedBusId = null;
			return;
		}

		for (Bus bus : buses) {
			if (bus.getId().equals(selectedBusId)) {
				try {
					if (selectedBus == null || !selectedBus.equals(bus)) {
					}

					selectedBus = getBusService().findWithLazy(bus.getId());

				} catch (Exception e) {
					selectedBus = null;
					selectedBusId = null;
					e.printStackTrace();
				}
				break;
			}
		}
	}

	private void initBuses() throws Exception {
		buses = getBusService().getAllEntities(bssType);

	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public BssType getBssType() {
		return bssType;
	}

	public void setBssType(BssType bssType) {
		this.bssType = bssType;
	}

	public List<Bus> getBuses() {
		return buses;
	}

	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	public Bus getSelectedBus() {
		return selectedBus;
	}

	public void setSelectedBus(Bus selectedBus) {
		this.selectedBus = selectedBus;
	}

	public Long getSelectedBusId() {
		return selectedBusId;
	}

}
