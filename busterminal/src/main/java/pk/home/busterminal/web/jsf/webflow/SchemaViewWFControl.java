package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Schema_;
import pk.home.busterminal.service.SchemaService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Schema
 * Schema - схема мест расположения
 */
public class SchemaViewWFControl extends AWFBaseLazyLoadTableView<Schema> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Schema, ?> orderByAttribute = Schema_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Schema_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Schema_.keyName;
		} else if (csortField != null && csortField.equals("Bus")) {
			orderByAttribute = Schema_.bus;
		}

		dataModel = getSchemaService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getSchemaService().count();
	}
	
	
	public String add(){
		return "add";
	}
	
	public String edit(){
		return "edit";
	}
	
	public String del(){
		return "del";
	}
	
}
