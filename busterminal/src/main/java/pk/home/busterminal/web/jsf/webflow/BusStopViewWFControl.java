package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.domain.BusStop_;
import pk.home.busterminal.service.BusStopService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: BusStop
 * BusStop - Остановка
 */
public class BusStopViewWFControl extends AWFBaseLazyLoadTableView<BusStop> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<BusStop, ?> orderByAttribute = BusStop_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = BusStop_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = BusStop_.keyName;
		}

		dataModel = getBusStopService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getBusStopService().count();
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
