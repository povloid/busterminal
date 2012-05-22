package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.service.BalanceService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Balance
 * Balance - баланс
 */
public class BalanceEditWFControl extends AWFControl<Balance, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Balance findEdited(Long id) throws Exception {
		return getBalanceService().find(id);
	}

	@Override
	public Balance newEdited() throws Exception {
		return new Balance();
	}

	public BalanceService getBalanceService() {
		return (BalanceService) findBean("balanceService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getBalanceService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getBalanceService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getBalanceService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
