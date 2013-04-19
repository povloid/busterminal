package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.libs.combine.web.jsf.flow.AWFWizart;

/**
 * Контроллер для мастера добавления места в ячейку
 * 
 * @author povloid
 *
 */
public class AddToCellWFWizControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586242585286422108L;

	/**
	 * Сервис
	 * 
	 * @return
	 */
	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#nextImpl()
	 */
	@Override
	protected void nextImpl() throws Exception {
		if (select == 0)
			throw new Exception("Выберите вариант!");

	}

	private short select;

	private Long selectedSchemaId; 	// Идентификатор текущей схемы
	private Short x;				// Координата X
	private Short y;				// Координата Y

	private Schema selectedSchema;		// Текущая выбранная схема
	private Long selectedSeatId;		// Идентификатор текущего выбранного места
	private Seat selectedSeat;			// Текущее выбранное место
	
	private SeatDataModel noMarkedSeats; // Модель данных

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#init0()
	 */
	@Override
	protected void init0() throws Exception {
		selectedSchema = service().getSchemaService().find(selectedSchemaId);
		noMarkedSeats = new SeatDataModel(service().getSeatService()
				.findNotMarkedSeats(selectedSchema));
	}


	/**
	 * Добавить выбранное неотмесенное место в схему
	 * 
	 * @throws Exception
	 */
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
	
	public short getSelect() {
		return select;
	}

	public void setSelect(short select) {
		this.select = select;
	}


}
