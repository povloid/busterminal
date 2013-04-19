package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.busterminal.service.BusService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

public class BusViewWFControl extends AWFBaseLazyLoadTableView<Bus> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6872276756508162992L;

	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	private BssType bssType = BssType.TEMPLITE;
	
	public void setBssType(String s) throws Exception
	{
		if(s != null && s.trim().length() > 0){
			bssType = BssType.valueOf(s);
		}
	}
	
	
	
	@Override
	protected void aInit() throws Exception {

		SingularAttribute<Bus, ?> orderByAttribute = Bus_.id;
		if (csortField != null && csortField.equals("id")) {
			orderByAttribute = Bus_.id;
		} else if (csortField != null && csortField.equals("keyName")) {
			orderByAttribute = Bus_.keyName;
		}

		dataModel = getBusService().getAllEntities(bssType, (page - 1) * rows,
				rows, orderByAttribute, getSortOrderType());
	}

	@Override
	protected long initAllRowsCount() throws Exception {
		return getBusService().count(bssType);
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

	public String busTempliteMaster() {
		return "busTempliteMaster";
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public BssType getBssType() {
		return bssType;
	}

	public void setBssType(BssType bssType) {
		this.bssType = bssType;
	}

}
