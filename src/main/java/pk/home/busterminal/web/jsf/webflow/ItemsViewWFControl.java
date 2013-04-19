package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Items_;
import pk.home.busterminal.service.ItemsService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Items
 * Items - запись ордера
 */
public class ItemsViewWFControl extends AWFBaseLazyLoadTableView<Items> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ItemsService getItemsService() {
		return (ItemsService) findBean("itemsService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Items, ?> orderByAttribute = Items_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Items_.id;
		}

		dataModel = getItemsService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getItemsService().count();
	}
	
	
	public String add(){
		return "add";
	}
	
	public String edit(){
		return "edit";
	}
	
	public String del(){
		return "del";
	}
	
}
