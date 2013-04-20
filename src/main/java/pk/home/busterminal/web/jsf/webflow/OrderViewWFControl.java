package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.Order_;
import pk.home.busterminal.service.OrderService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Order
 * Order - ордер - операция
 */
public class OrderViewWFControl extends AWFBaseLazyLoadTableView<Order> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления ордерами
	 * 
	 * @return
	 */
	public OrderService getOrderService() {
		return (OrderService) findBean("orderService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Order, ?> orderByAttribute = Order_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Order_.id;
		}

		dataModel = getOrderService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {		
		return getOrderService().count();
	}
	
	
	/**
	 * Добавить
	 * @return
	 */
	public String add(){
		return "add";
	}
	
	/**
	 * Редактировать
	 * @return
	 */
	public String edit(){
		return "edit";
	}
	
	/**
	 * Удалить
	 * @return
	 */
	public String del(){
		return "del";
	}
	
}
