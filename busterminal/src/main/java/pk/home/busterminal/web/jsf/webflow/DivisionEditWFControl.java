package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.DivisionService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Division
 * Division - отделение
 */
public class DivisionEditWFControl extends AWFControl<Division, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Division findEdited(Long id) throws Exception {
		return getDivisionService().find(id);
	}

	@Override
	public Division newEdited() throws Exception {
		return new Division();
	}

	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getDivisionService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getDivisionService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getDivisionService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
