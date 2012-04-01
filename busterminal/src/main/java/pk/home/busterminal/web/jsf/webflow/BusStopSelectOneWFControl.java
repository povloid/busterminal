package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

public class BusStopSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	private BusStop selected;

	private WFLazyDataModel<BusStop> model = new WFLazyDataModel<BusStop>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4970282896915525138L;

		@Override
		protected int count() throws Exception {
			return (int) getBusStopService().count();
		}

		@Override
		protected List<BusStop> aload(int first, int pageSize,
				String sortField, SortOrder sortOrder,
				Map<String, String> filters) throws Exception {
			return getBusStopService().getAllEntities(first, pageSize);
		}

		@Override
		public BusStop getRowData(String rowKey) {
			for (BusStop rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

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
