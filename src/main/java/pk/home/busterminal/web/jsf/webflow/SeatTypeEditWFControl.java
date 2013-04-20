package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.service.SeatTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: SeatType SeatType - тип места
 * 
 * @author povloid
 *
 */
public class SeatTypeEditWFControl extends AWFControl<SeatType, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public SeatType findEdited(Long id) throws Exception {
		return getSeatTypeService().find(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public SeatType newEdited() throws Exception {
		return new SeatType();
	}

	/**
	 * Сервис типов посадочных мест
	 * @return
	 */
	public SeatTypeService getSeatTypeService() {
		return (SeatTypeService) findBean("seatTypeService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getSeatTypeService().persist(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getSeatTypeService().merge(edited);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		getSeatTypeService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	protected void init0() throws Exception {
	}

}
