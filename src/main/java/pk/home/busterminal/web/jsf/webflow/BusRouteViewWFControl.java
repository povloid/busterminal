package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRoute_;
import pk.home.busterminal.service.BusRouteService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: BusRoute
 * BusRoute - Маршрут
 * 
 * @author povloid
 *
 */
public class BusRouteViewWFControl extends AWFBaseLazyLoadTableView<BusRoute> implements
		Serializable {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4306147261469800543L;

	
	/**
	 * Сервис управления маршрутами
	 * 
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<BusRoute, ?> orderByAttribute = BusRoute_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = BusRoute_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = BusRoute_.keyName;
		}

		dataModel = getBusRouteService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {		
		return getBusRouteService().count();
	}
	
	
	/**
	 * Добавление 
	 * 
	 * @return
	 */
	public String add(){
		return "add";
	}
	
	/**
	 * Редактирование
	 * 
	 * @return
	 */
	public String edit(){
		return "edit";
	}
	
	/**
	 * Удаление
	 * 
	 * @return
	 */
	public String del(){
		return "del";
	}
	
}
