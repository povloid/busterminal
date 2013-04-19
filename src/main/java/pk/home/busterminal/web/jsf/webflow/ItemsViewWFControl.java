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
 * 
 * @author povloid
 *
 */
public class ItemsViewWFControl extends AWFBaseLazyLoadTableView<Items> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления записями ордера (продаваемыми отрезками пути)
	 * 
	 * @return
	 */
	public ItemsService getItemsService() {
		return (ItemsService) findBean("itemsService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Items, ?> orderByAttribute = Items_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Items_.id;
		}

		dataModel = getItemsService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {		
		return getItemsService().count();
	}
	
	
	/**
	 * Добавить
	 * 
	 * @return
	 */
	public String add(){
		return "add";
	}
	
	/**
	 * Редактировать
	 * 
	 * @return
	 */
	public String edit(){
		return "edit";
	}
	
	/**
	 * Удалить
	 * 
	 * @return
	 */
	public String del(){
		return "del";
	}
}
