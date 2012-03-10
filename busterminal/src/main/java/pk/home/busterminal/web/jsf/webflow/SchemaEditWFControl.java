package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.service.SchemaService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Schema
 * Schema - схема мест расположения
 */
public class SchemaEditWFControl extends AWFControl<Schema, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Schema findEdited(Long id) throws Exception {
		return getSchemaService().find(id);
	}

	@Override
	public Schema newEdited() throws Exception {
		return new Schema();
	}

	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSchemaService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSchemaService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSchemaService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	public void init() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
