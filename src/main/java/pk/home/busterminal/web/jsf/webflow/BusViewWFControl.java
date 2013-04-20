package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.persistence.metamodel.SingularAttribute;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.busterminal.service.BusService;
import pk.home.libs.combine.web.jsf.flow.AWFBaseLazyLoadTableView;

/**
 * Контрол для списка автобусов
 * 
 * @author povloid
 *
 */
public class BusViewWFControl extends AWFBaseLazyLoadTableView<Bus> implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Сервис управления автобусами
	 * 
	 * @return
	 */
	public BusService getBusService() {
		return (BusService) findBean("busService");
	}

	/**
	 * Тип автобуса
	 */
	private BssType bssType = BssType.TEMPLITE;
	
	/**
	 * Установка типа автобуса из строкового значения
	 * 
	 * @param s
	 * @throws Exception
	 */
	public void setBssType(String s) throws Exception
	{
		if(s != null && s.trim().length() > 0){
			bssType = BssType.valueOf(s);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#aInit()
	 */
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

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.ABaseLazyLoadTableView#initAllRowsCount()
	 */
	@Override
	protected long initAllRowsCount() throws Exception {
		return getBusService().count(bssType);
	}

	/**
	 * Добавить 
	 * 
	 * @return
	 */
	public String add() {
		return "add";
	}

	/**
	 * Редактировать 
	 * 
	 * @return
	 */
	public String edit() {
		return "edit";
	}

	/**
	 * Удалить
	 * 
	 * @return
	 */
	public String del() {
		return "del";
	}

	/**
	 * Перейти на шаблон
	 * 
	 * @return
	 */
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
