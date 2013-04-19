package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.libs.combine.web.jsf.flow.AWFWizart;

public class AddToCellWFWizControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586242585286422108L;

	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	@Override
	protected void nextImpl() throws Exception {
		if (select == 0)
			throw new Exception("Выберите вариант!");

	}

	private short select;

	public short getSelect() {
		return select;
	}

	public void setSelect(short select) {
		this.select = select;
	}

	private Long selectedSchemaId;
	private Short x;
	private Short y;

	private Schema selectedSchema;
	private Long selectedSeatId;
	private Seat selectedSeat;

	@Override
	protected void init0() throws Exception {
		selectedSchema = service().getSchemaService().find(selectedSchemaId);
		noMarkedSeats = new SeatDataModel(service().getSeatService()
				.findNotMarkedSeats(selectedSchema));
	}

	private SeatDataModel noMarkedSeats;

	public void addSelectNotMarkedSeatToCell() throws Exception {
		try {

			if (selectedSeat == null)
				throw new Exception("Выберите место!");

			selectedSeat.setSx(x);
			selectedSeat.setSy(y);
			service().getSeatService().merge(selectedSeat);

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));

			throw new Exception(e);
		}
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public Schema getSelectedSchema() {
		return selectedSchema;
	}

	public void setSelectedSchema(Schema selectedSchema) {
		this.selectedSchema = selectedSchema;
	}

	public SeatDataModel getNoMarkedSeats() {
		return noMarkedSeats;
	}

	public void setNoMarkedSeats(SeatDataModel noMarkedSeats) {
		this.noMarkedSeats = noMarkedSeats;
	}

	public Long getSelectedSchemaId() {
		return selectedSchemaId;
	}

	public void setSelectedSchemaId(Long selectedSchemaId) {
		this.selectedSchemaId = selectedSchemaId;
	}

	public Short getX() {
		return x;
	}

	public void setX(Short x) {
		this.x = x;
	}

	public Short getY() {
		return y;
	}

	public void setY(Short y) {
		this.y = y;
	}

	public Long getSelectedSeatId() {
		return selectedSeatId;
	}

	public void setSelectedSeatId(Long selectedSeatId) {
		this.selectedSeatId = selectedSeatId;
	}

	public Seat getSelectedSeat() {
		return selectedSeat;
	}

	public void setSelectedSeat(Seat selectedSeat) {
		this.selectedSeat = selectedSeat;
	}

}
