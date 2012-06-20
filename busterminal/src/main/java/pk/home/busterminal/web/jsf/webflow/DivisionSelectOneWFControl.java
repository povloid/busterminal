package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.DivisionService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

public class DivisionSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	private Division selected;

	private WFLazyDataModel<Division> model = new WFLazyDataModel<Division>() {

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

			return (int) getDivisionService().selectCount(id,
					filters.get("keyName"));
		}

		@Override
		protected List<Division> aload(int first, int pageSize,
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

			return getDivisionService().select(first, pageSize, sortOrderType,
					sortField, id, filters.get("keyName"));
		}

		@Override
		public Division getRowData(String rowKey) {
			for (Division rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		@Override
		public Object getRowKey(Division object) {
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

	public Division getSelected() {
		return selected;
	}

	public void setSelected(Division selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<Division> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<Division> model) {
		this.model = model;
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
