package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusRouteStop_;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: BusRouteStop
 * BusRouteStop - остановки маршрута
 */
public class BusRouteStopViewWFControl extends AWFBaseLazyLoadTableView<BusRouteStop> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusRouteStopService getBusRouteStopService() {
		return (BusRouteStopService) findBean("busRouteStopService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<BusRouteStop, ?> orderByAttribute = BusRouteStop_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = BusRouteStop_.id;
		} else if (csortField != null && csortField.equals("orId")) {
			orderByAttribute = BusRouteStop_.orId;
		}

		dataModel = getBusRouteStopService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getBusRouteStopService().count();
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
