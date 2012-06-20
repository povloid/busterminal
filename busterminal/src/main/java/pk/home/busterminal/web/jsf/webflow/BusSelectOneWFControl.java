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

public class BusSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	private Bus selected;

	private WFLazyDataModel<Bus> model = new WFLazyDataModel<Bus>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4970282896915525138L;

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

		@Override
		public Bus getRowData(String rowKey) {
			for (Bus rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

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
