package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.SortOrder;

import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.service.CustomerService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;
import pk.home.libs.combine.web.jsf.flow.model.WFLazyDataModel;

public class CustomerSelectOneWFControl extends AWFBasicControl implements
		Serializable {

	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6986222423369826350L;

	private Customer selected;

	private WFLazyDataModel<Customer> model = new WFLazyDataModel<Customer>() {

		/**
		 * 
		 */
		private static final long serialVersionUID = 4970282896915525138L;

		@Override
		protected int count() throws Exception {
			return (int) getCustomerService().count();
		}

		@Override
		protected List<Customer> aload(int first, int pageSize,
				String sortField, SortOrder sortOrder,
				Map<String, String> filters) throws Exception {

			System.out.println(sortField + " - " + sortOrder);

			for (String s : filters.keySet()) {
				System.out.println(s + " - " + filters.get(s));
			}

			return getCustomerService().getAllEntities(first, pageSize);
		}

		@Override
		public Customer getRowData(String rowKey) {
			for (Customer rd : getDataList()) {
				if (rd.getId() == Long.parseLong(rowKey))
					return rd;
			}
			return null;
		}

		@Override
		public Object getRowKey(Customer object) {
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

	/**
	 * Добавить нового пользователя
	 * 
	 * @return
	 */
	public String addNewCustomer() {
		return "addNewCustomer";
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public Customer getSelected() {
		return selected;
	}

	public void setSelected(Customer selected) {
		this.selected = selected;
	}

	public WFLazyDataModel<Customer> getModel() {
		return model;
	}

	public void setModel(WFLazyDataModel<Customer> model) {
		this.model = model;
	}

	public void setSelectedId(Long id) {
		try {
			this.selected = getCustomerService().find(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}
	}

	public Long getSelectedId() {
		if (selected != null)
			return selected.getId();
		else
			return null;
	}

}
