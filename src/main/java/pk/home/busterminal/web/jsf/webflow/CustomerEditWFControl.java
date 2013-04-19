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
 * 
 * @author povloid
 *
 */
public class CustomerEditWFControl extends AWFControl<Customer, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Customer findEdited(Long id) throws Exception {
		return getCustomerService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Customer newEdited() throws Exception {
		return new Customer();
	}

	/**
	 * Сервис управления клиентами
	 * 
	 * @return
	 */
	public CustomerService getCustomerService() {
		return (CustomerService) findBean("customerService");
	}

	/**
	 * Сервис управления типами доккументов
	 * 
	 * @return
	 */
	public DocumentTypeService getDocumentTypeService() {
		return (DocumentTypeService) findBean("documentTypeService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getCustomerService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getCustomerService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getCustomerService().remove(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
		initDocumentTypes();
	}

	// DocumentTypes -----------------------

	
	/**
	 * Список типов документов
	 */
	private List<DocumentType> documentTypes;

	/**
	 * Инициализация типов документов
	 * 
	 * @throws Exception
	 */
	private void initDocumentTypes() throws Exception {
		documentTypes = getDocumentTypeService().getAllEntities();
	}

	/**
	 * Получить id типа документов текущей записи 
	 * 
	 * @return
	 */
	public long getDocumentTypeId() {
		if (edited != null && edited.getDocupentType() != null)
			return edited.getDocupentType().getId();
		else
			return 0;
	}

	/**
	 * Установить тип документа текущей записи по id 
	 * 
	 * @param id
	 */
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
