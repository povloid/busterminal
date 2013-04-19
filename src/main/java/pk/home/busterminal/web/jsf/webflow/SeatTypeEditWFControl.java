package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.service.SeatTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: SeatType SeatType - тип места
 */
public class SeatTypeEditWFControl extends AWFControl<SeatType, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public SeatType findEdited(Long id) throws Exception {
		return getSeatTypeService().find(id);
	}

	@Override
	public SeatType newEdited() throws Exception {
		return new SeatType();
	}

	public SeatTypeService getSeatTypeService() {
		return (SeatTypeService) findBean("seatTypeService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSeatTypeService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSeatTypeService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getSeatTypeService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	

}
