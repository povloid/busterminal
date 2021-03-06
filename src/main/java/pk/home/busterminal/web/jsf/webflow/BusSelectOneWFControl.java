package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.service.BusService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

/**
 * Контрол для выбора одного автобуса
 * 
 * @author povloid
 *
 */
public class BusSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	
	/**
	 * Сервис управления автобусами
	 * 
	 * @return
	 */
	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Выбранный автобус
	 */
	private Bus selected;

	/**
	 * Ленивая модель для отображения списка автобусов
	 */
	private WFLazyDataModel<Bus> model = new WFLazyDataModel<Bus>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#count(java.util.Map)
		 */
		@Override
		protected int count(Map<String, String> filters) throws Exception {
			Long id = null;
			try {
				id = Long.parseLong(filters.get("id"));
			} catch (Exception e) {
			}

			return (int) getBusService().selectCount(BssType.TEMPLITE, id,
					filters.get("keyName"), filters.get("gosNum"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#aload(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
		 */
		@Override
		protected List<Bus> aload(int first, int pageSize, String sortField,
				SortOrder sortOrder, Map<String, String> filters)
				throws Exception {
			SortOrderType sortOrderType = SortOrderType.ASC;
			if (sortOrder == SortOrder.DESCENDING) {
				sortOrderType = SortOrderType.DESC;
			}

			Long id = null;
			try {
				id = Long.parseLong(filters.get("id"));
			} catch (Exception e) {
			}

			return getBusService().select(first, pageSize, sortOrderType,
					sortField, BssType.TEMPLITE, id, filters.get("keyName"),
					filters.get("gosNum"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowData(java.lang.String)
		 */
		@Override
		public Bus getRowData(String rowKey) {
			for (Bus rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(Bus object) {
			return object.getId();
		}
	};

	// actions
	// -----------------------------------------------------------------------------------------

	/**
	 * Выбор остановки
	 * 
	 * @return
	 * @throws Exception
	 */
	public String select() throws Exception {

		if (selected == null) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ",
							"Элемент небыл выбран"));
			throw new Exception("Элемент небыл выбран");
		}

		return "select";
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public Bus getSelected() {
		return selected;
	}

	public void setSelected(Bus selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<Bus> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<Bus> model) {
		this.model = model;
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
