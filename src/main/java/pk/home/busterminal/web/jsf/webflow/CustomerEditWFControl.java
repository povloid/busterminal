package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.service.CustomerService;
import pk.home.busterminal.service.DocumentTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Customer Customer - клиент
 */
public class CustomerEditWFControl extends AWFControl<Customer, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Customer findEdited(Long id) throws Exception {
		return getCustomerService().find(id);
	}

	@Override
	public Customer newEdited() throws Exception {
		return new Customer();
	}

	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	public DocumentTypeService getDocumentTypeService() {
		return (DocumentTypeService) findBean("documentTypeService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getCustomerService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getCustomerService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getCustomerService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
		initDocumentTypes();
	}

	// DocumentTypes -----------------------

	private List<DocumentType> documentTypes;

	private void initDocumentTypes() throws Exception {
		documentTypes = getDocumentTypeService().getAllEntities();
	}

	public long getDocumentTypeId() {
		if (edited != null && edited.getDocupentType() != null)
			return edited.getDocupentType().getId();
		else
			return 0;
	}

	public void setDocumentTypeId(long id) {
		try {
			this.edited.setDocupentType(getDocumentTypeService().find(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ...

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public List<DocumentType> getDocumentTypes() {
		return documentTypes;
	}

	public void setDocumentTypes(List<DocumentType> documentTypes) {
		this.documentTypes = documentTypes;
	}

}
