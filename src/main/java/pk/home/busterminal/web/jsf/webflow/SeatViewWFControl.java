package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.Seat_;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.SeatService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Seat Seat - посадочное место
 * 
 * @author povloid
 *
 */
public class SeatViewWFControl extends AWFBaseLazyLoadTableView<Seat> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * сервис мест
	 * 
	 * @return
	 */
	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}

	/**
	 * сервис автобусов
	 * 
	 * @return
	 */
	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		initBuses();

		SingularAttribute<Seat, ?> orderByAttribute = Seat_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Seat_.id;
		} else if (csortField != null && csortField.equals("num")) {
			orderByAttribute = Seat_.num;
		}

		dataModel = getSeatService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType(), selectedBus,
				selectedSchema);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		// System.out.println(">>>>>>>" + selectedBus);
		// System.out.println(">>>>>>>" + selectedSchema);
		return getSeatService().count(selectedBus, selectedSchema);
	}

	/**
	 * Добавть
	 * @return
	 */
	public String add() {
		return "add";
	}

	/**
	 * Редактировать
	 * @return
	 */
	public String edit() {
		return "edit";
	}

	/**
	 * Удалить
	 * @return
	 */
	public String del() {
		return "del";
	}

	// -----------------------------------------------------------------------------------------------------------------

	private BssType bssType = BssType.TEMPLITE;

	public void setBssType(String s) throws Exception {
		System.out.println("s = " + s);
		
		if (s != null && s.trim().length() > 0) {
			bssType = BssType.valueOf(s);
		}
	}

	private List<Bus> buses;
	private Bus selectedBus;
	private Schema selectedSchema;

	private Long selectedBusId;
	private Long selectedSchemaId;

	private void initBuses() throws Exception {
		System.out.println("bssType = " + bssType);
		
		buses = getBusService().getAllEntities(bssType);

	}

	public void setSelectedBusId(Long selectedBusId) {
		this.selectedBusId = selectedBusId;

		if (buses == null)
			return;

		if (selectedBusId == 0l) {
			selectedBus = null;
			selectedBusId = null;

			selectedSchema = null;
			selectedSchemaId = null;
			return;
		}

		for (Bus bus : buses) {
			if (bus.getId().equals(selectedBusId)) {
				try {
					if (selectedBus == null || !selectedBus.equals(bus)) {
						selectedSchema = null;
						selectedSchemaId = null;
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

	public void setSelectedSchemaId(Long selectedSchemaId) {
		this.selectedSchemaId = selectedSchemaId;

		if (selectedBus == null)
			return;

		for (Schema s : selectedBus.getSchemas()) {
			if (s.getId().equals(selectedSchemaId)) {
				selectedSchema = s;
				break;
			}
		}
	}

	// get and set
	// -----------------------------------------------------------------------------------------------------

	public List<Bus> getBuses() {
		System.out.println(buses.size());
		return buses;
	}

	public Long getSelectedBusId() {
		return selectedBusId;
	}

	public Long getSelectedSchemaId() {
		return selectedSchemaId;
	}

	public void setBuses(List<Bus> buses) {
		this.buses = buses;
	}

	public Bus getSelectedBus() {
		return selectedBus;
	}

	public void setSelectedBus(Bus selectedBus) {
		System.out.println(selectedBus);
		this.selectedBus = selectedBus;
	}

	public Schema getSelectedSchema() {
		return selectedSchema;
	}

	public void setSelectedSchema(Schema selectedSchema) {
		this.selectedSchema = selectedSchema;
	}

	public BssType getBssType() {
		return bssType;
	}

	public void setBssType(BssType bssType) {
		this.bssType = bssType;
	}
}