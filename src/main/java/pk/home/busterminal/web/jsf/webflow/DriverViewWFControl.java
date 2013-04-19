package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.domain.Driver_;
import pk.home.busterminal.service.DriverService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Driver
 * Driver - водитель
 * 
 * @author povloid
 *
 */
public class DriverViewWFControl extends AWFBaseLazyLoadTableView<Driver> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления водителями
	 * 
	 * @return
	 */
	public DriverService getDriverService() {
		return (DriverService) findBean("driverService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Driver, ?> orderByAttribute = Driver_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Driver_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Driver_.keyName;
		}

		dataModel = getDriverService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getDriverService().count();
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
