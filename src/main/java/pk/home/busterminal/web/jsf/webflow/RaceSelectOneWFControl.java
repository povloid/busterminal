package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

/**
 * Контрол выбора одного реса
 * 
 * @author povloid
 *
 */
public class RaceSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Race selected;	// Выбранный рейс

	/**
	 *	Ленивая модель для таблици ресов 
	 */
	private WFLazyDataModel<Race> model = new WFLazyDataModel<Race>() {

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

			return (int) getRaceService().selectCount(id,
					filters.get("busRoute.keyName"));
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#aload(int, int, java.lang.String, org.primefaces.model.SortOrder, java.util.Map)
		 */
		@Override
		protected List<Race> aload(int first, int pageSize, String sortField,
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

			System.out.println("*");
			
			List<Race> l = getRaceService().select(first, pageSize, sortOrderType,
					sortField, id, filters.get("busRoute.keyName"));
			System.out.println(l.size());
			
			return l;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowData(java.lang.String)
		 */
		@Override
		public Race getRowData(String rowKey) {
			for (Race rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel#getRowKey(java.lang.Object)
		 */
		@Override
		public Object getRowKey(Race object) {
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

	public Race getSelected() {
		return selected;
	}

	public void setSelected(Race selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<Race> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<Race> model) {
		this.model = model;
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
