package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.service.DocumentTypeService;
import pk.home.busterminal.service.DriverService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Driver Driver - водитель
 * 
 * @author povloid
 *
 */
public class DriverEditWFControl extends AWFControl<Driver, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Driver findEdited(Long id) throws Exception {
		return getDriverService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Driver newEdited() throws Exception {
		return new Driver();
	}

	/**
	 * Сервис управления водителями
	 * 
	 * @return
	 */
	public DriverService getDriverService() {
		return (DriverService) findBean("driverService");
	}

	/**
	 * Сервис управления типами документов
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
		edited = getDriverService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getDriverService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getDriverService().remove(edited);
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
	 * @throws Exception
	 */
	private void initDocumentTypes() throws Exception {
		documentTypes = getDocumentTypeService().getAllEntities();
	}

	/**
	 * Получить id типа документа
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
	 * Установить тип документа по id
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
