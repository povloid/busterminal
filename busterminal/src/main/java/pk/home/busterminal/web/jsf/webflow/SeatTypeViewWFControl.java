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
 */
public class SeatTypeViewWFControl extends AWFBaseLazyLoadTableView<SeatType> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeatTypeService getSeatTypeService() {
		return (SeatTypeService) findBean("seatTypeService");
	}

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

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getSeatTypeService().count();
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
