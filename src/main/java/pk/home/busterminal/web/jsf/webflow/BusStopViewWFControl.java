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
 * 
 * @author povloid
 *
 */
public class BusStopViewWFControl extends AWFBaseLazyLoadTableView<BusStop> implements
		Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления остановками
	 * 
	 * @return
	 */
	public BusStopService getBusStopService() {
		return (BusStopService) findBean("busStopService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
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

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {		
		return getBusStopService().count();
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
