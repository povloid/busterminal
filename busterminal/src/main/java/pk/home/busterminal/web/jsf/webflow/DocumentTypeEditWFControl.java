package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.service.DocumentTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: DocumentType
 * DocumentType - тип документа
 */
public class DocumentTypeEditWFControl extends AWFControl<DocumentType, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public DocumentType findEdited(Long id) throws Exception {
		return getDocumentTypeService().find(id);
	}

	@Override
	public DocumentType newEdited() throws Exception {
		return new DocumentType();
	}

	public DocumentTypeService getDocumentTypeService() {
		return (DocumentTypeService) findBean("documentTypeService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getDocumentTypeService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getDocumentTypeService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getDocumentTypeService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
