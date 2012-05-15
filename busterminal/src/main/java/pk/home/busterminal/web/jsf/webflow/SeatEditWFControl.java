package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.math.BigDecimal;

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

	private boolean blockXY;

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
		Seat seat = new Seat();
		seat.setMasterProcent(100);
		seat.setPrice(new BigDecimal(0));
		return seat;
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited.setSchema(schema);
		edited = getSeatService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		// edited.setSchema(schema);
		edited = getSeatService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSeatService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
		if (schemaId != null)
			schema = getSchemaService().find(schemaId);
		else if (edited != null && edited.getSchema() != null) {
			schema = edited.getSchema();
		}
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

	public boolean isBlockXY() {
		return blockXY;
	}

	public void setBlockXY(boolean blockXY) {
		this.blockXY = blockXY;
	}

}
