package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.service.BusRouteService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

/**
 * Контрол выбора одного маршрута
 * 
 * @author povloid
 *
 */
public class BusRouteSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * Сервис управления маршрутами
	 * 
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	private BusRoute selected; // выбранный маршрут

	
	/**
	 *	Ленивая модель для таблици маршрутов 
	 */
	private WFLazyDataModel<BusRoute> model = new WFLazyDataModel<BusRoute>() {

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

			return (int) getBusRouteService().selectCount(id,
					filters.get("keyName"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#aload(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
		 */
		@Override
		protected List<BusRoute> aload(int first, int pageSize,
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

			return getBusRouteService().select(first, pageSize, sortOrderType,
					sortField, id, filters.get("keyName"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowData(java.lang.String)
		 */
		@Override
		public BusRoute getRowData(String rowKey) {
			for (BusRoute rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(BusRoute object) {
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

	public BusRoute getSelected() {
		return selected;
	}

	public void setSelected(BusRoute selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<BusRoute> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<BusRoute> model) {
		this.model = model;
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
