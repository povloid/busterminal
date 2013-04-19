package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.DocumentType;
import pk.home.busterminal.domain.DocumentType_;
import pk.home.busterminal.service.DocumentTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: DocumentType
 * DocumentType - тип документа
 * 
 * @author povloid
 *
 */
public class DocumentTypeViewWFControl extends AWFBaseLazyLoadTableView<DocumentType> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления типами доркументов
	 * 
	 * @return
	 */
	public DocumentTypeService getDocumentTypeService() {
		return (DocumentTypeService) findBean("documentTypeService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<DocumentType, ?> orderByAttribute = DocumentType_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = DocumentType_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = DocumentType_.keyName;
		}

		dataModel = getDocumentTypeService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getDocumentTypeService().count();
	}
	
	
	/**
	 * Добавить
	 * 
	 * @return
	 */
	public String add(){
		return "add";
	}
	
	/**
	 * Редактировать
	 * 
	 * @return
	 */
	public String edit(){
		return "edit";
	}
	
	/**
	 * Удалить
	 * 
	 * @return
	 */
	public String del(){
		return "del";
	}
	
}
