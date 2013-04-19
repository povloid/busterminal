package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

/**
 * Контрол для выбора одной остановки
 * 
 * 
 * @author povloid
 *
 */
public class BusStopSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * Выбор остановки
	 * 
	 * @return
	 */
	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	/**
	 * Выбранная остановка
	 */
	private BusStop selected;

	/**
	 * Ленивая модель для таблици остановок
	 */
	private WFLazyDataModel<BusStop> model = new WFLazyDataModel<BusStop>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4970282896915525138L;

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

			return (int) getBusStopService().selectCount(id,
					filters.get("keyName"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#aload(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
		 */
		@Override
		protected List<BusStop> aload(int first, int pageSize,
				String sortField, SortOrder sortOrder,
				Map<String, String> filters) throws Exception {
			SortOrderType sortOrderType = SortOrderType.ASC;
			if (sortOrder == SortOrder.DESCENDING) {
				sortOrderType = SortOrderType.DESC;
			}

			Long id = null;
			try {
				id = Long.parseLong(filters.get("id"));
			} catch (Exception e) {
			}

			return getBusStopService().select(first, pageSize, sortOrderType,
					sortField, id, filters.get("keyName"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowData(java.lang.String)
		 */
		@Override
		public BusStop getRowData(String rowKey) {
			for (BusStop rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(BusStop object) {
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
							"Остановка не выбрана"));
			throw new Exception("Остановка не выбрана");
		}

		return "select";
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public BusStop getSelected() {
		return selected;
	}

	public void setSelected(BusStop selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<BusStop> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<BusStop> model) {
		this.model = model;
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
