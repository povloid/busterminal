package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * JSF view control class for entity class: Race Race - рейс
 */
public class RaceViewWFControl extends AWFBaseLazyLoadTableView<Race> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Race, ?> orderByAttribute = Race_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Race_.id;
		}

		if (csortField != null && csortField.equals("dTime")) {
			orderByAttribute = Race_.dTime;
		}

		dataModel = getRaceService().getAllEntities(Math.abs(page - 1) * rows, rows,
				orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return getRaceService().count();
	}

	public String add() {
		return "add";
	}

	public String edit() {
		return "edit";
	}

	public String del() {
		return "del";
	}

}
