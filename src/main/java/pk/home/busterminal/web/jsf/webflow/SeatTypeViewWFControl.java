package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.domain.SeatType_;
import pk.home.busterminal.service.SeatTypeService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: SeatType
 * SeatType - тип места
 * 
 * @author povloid
 *
 */
public class SeatTypeViewWFControl extends AWFBaseLazyLoadTableView<SeatType> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис типов посадочных мест
	 * 
	 * @return
	 */
	public SeatTypeService getSeatTypeService() {
		return (SeatTypeService) findBean("seatTypeService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<SeatType, ?> orderByAttribute = SeatType_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = SeatType_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = SeatType_.keyName;
		}

		dataModel = getSeatTypeService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {		
		return getSeatTypeService().count();
	}
	
	
	/**
	 * Добавть
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