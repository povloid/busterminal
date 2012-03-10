package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.SeatService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Seat
 * Seat - посадочное место
 */
public class SeatEditWFControl extends AWFControl<Seat, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Seat findEdited(Long id) throws Exception {
		return getSeatService().find(id);
	}

	@Override
	public Seat newEdited() throws Exception {
		return new Seat();
	}

	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSeatService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSeatService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSeatService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	public void init() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
