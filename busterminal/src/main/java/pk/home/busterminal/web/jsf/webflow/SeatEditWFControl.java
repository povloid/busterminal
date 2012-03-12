package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.SchemaService;
import pk.home.busterminal.service.SeatService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Seat Seat - посадочное место
 */
public class SeatEditWFControl extends AWFControl<Seat, Long> implements
		Serializable {

	private Long schemaId;
	private Schema schema;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}

	public SchemaService getSchemaService() {
		return (SchemaService) findBean("schemaService");
	}

	

	@Override
	public Seat findEdited(Long id) throws Exception {
		return getSeatService().find(id);
	}

	@Override
	public Seat newEdited() throws Exception {
		return new Seat();
	}

	
	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setSchema(schema);
		edited = getSeatService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		//edited.setSchema(schema);
		edited = getSeatService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSeatService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
		schema = getSchemaService().find(schemaId);
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public Long getSchemaId() {
		return schemaId;
	}

	public void setSchemaId(Long schemaId) {
		this.schemaId = schemaId;
	}

	public Schema getSchema() {
		return schema;
	}

	public void setSchema(Schema schema) {
		this.schema = schema;
	}
	
	
	
	

}
