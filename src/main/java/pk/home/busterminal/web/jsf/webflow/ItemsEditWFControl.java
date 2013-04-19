package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Items;
import pk.home.busterminal.service.ItemsService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

/**
 * JSF edit control class for entity class: Items
 * Items - запись ордера
 */
public class ItemsEditWFControl extends AWFControl<Items, Long> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Items findEdited(Long id) throws Exception {
		return getItemsService().find(id);
	}

	@Override
	public Items newEdited() throws Exception {
		return new Items();
	}

	public ItemsService getItemsService() {
		return (ItemsService) findBean("itemsService");
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		edited = getItemsService().persist(edited);
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		edited = getItemsService().merge(edited);
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		getItemsService().remove(edited);
	}

	// init
	// ----------------------------------------------------------------------------------------------
	protected void init0() throws Exception {
	}

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

}
