package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.Seat_;
import pk.home.busterminal.service.SeatService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Seat
 * Seat - посадочное место
 */
public class SeatViewWFControl extends AWFBaseLazyLoadTableView<Seat> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}

	@Override
	protected void aInit() throws Exception {
		
		SingularAttribute<Seat, ?> orderByAttribute = Seat_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Seat_.id;
		} else if (csortField != null && csortField.equals("num")) {
			orderByAttribute = Seat_.num;
		}

		dataModel = getSeatService().getAllEntities((page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {		
		return getSeatService().count();
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
