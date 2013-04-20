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
 * 
 * @author povloid
 *
 */
public class SchemaViewWFControl extends AWFBaseLazyLoadTableView<Schema>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис схем
	 * 
	 * @return
	 */
	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	/**
	 * Сервис автобусов
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

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		return getSchemaService().count(selectedBus);
	}

	/**
	 * Добавить
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

	/**
	 * Тип автобуса
	 */
	private BssType bssType = BssType.TEMPLITE;

	private List<Bus> buses;	// список автобусов
	private Bus selectedBus;	// Выбранный автобус
	private Long selectedBusId;	// id выбранного автобуса

	/**
	 * Установить тип автобуса по строчному значению
	 * 
	 * @param s
	 * @throws Exception
	 */
	public void setBssType(String s) throws Exception {
		if (s != null && s.trim().length() > 0) {
			bssType = BssType.valueOf(s);
		}
	}


	/**
	 * Установить id выбранного автобуса
	 * 
	 * @param selectedBusId
	 */
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

	/**
	 * Инициализация списка автобусов
	 * 
	 * @throws Exception
	 */
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
		System.out.println(buses.size());
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
