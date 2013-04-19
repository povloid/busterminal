package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.service.SchemaService;
import pk.home.busterminal.service.SeatService;
import pk.home.busterminal.service.SeatTypeService;
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

	public SeatTypeService getSeatTypeService() {
		return (SeatTypeService) findBean("seatTypeService");
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
		seat.setDiscountPotsent(0);
		return seat;
	}

	@Override
	protected void confirmAddImpl() throws Exception {
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
		if (schemaId != null) {
			schema = getSchemaService().find(schemaId);
			edited.setSchema(schema);
		} else if (edited != null && edited.getSchema() != null) {
			schema = edited.getSchema();
		}

		initSeatTypes();
	}

	// ------

	private List<SeatType> seatTypes;

	private void initSeatTypes() throws Exception {
		seatTypes = getSeatTypeService().getAllEntities();
	}

	public long getSeatTypeId() {
		if (edited != null && edited.getSeatType() != null)
			return edited.getSeatType().getId();
		else
			return 0;
	}

	public void setSeatTypeId(long seatTypeId) {
		try {
			this.edited.setSeatType(getSeatTypeService().find(seatTypeId));
			if (!edited.getSeatType().getSold()) {
				edited.setNum(getSeatService().createDecoratorMinNum(
						this.edited));
			} else if (edited.getSeatType().getSold()
					&& (this.edited.getNum() == null || this.edited.getNum() < 0)) {
				edited.setNum(getSeatService().createDecoratorMaxNum(edited));
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public List<SeatType> getSeatTypes() {
		return seatTypes;
	}

	public void setSeatTypes(List<SeatType> seatTypes) {
		this.seatTypes = seatTypes;
	}

}
