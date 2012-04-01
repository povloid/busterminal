package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusRouteDAO;
import pk.home.busterminal.domain.BusRoute;

/**
 * Service class for entity class: BusRoute BusRoute - Маршрут
 */
@Service
@Transactional
public class BusRouteService extends ABaseService<BusRoute> {

	@Autowired
	private BusRouteDAO busRouteDAO;

	@Override
	public ABaseDAO<BusRoute> getAbstractBasicDAO() {
		return busRouteDAO;
	}

	/**
	 * Загрузка вместе с коллесциями
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public BusRoute findWithLazy(Object key) throws Exception {
		BusRoute busRoute = super.find(key);

		if (busRoute.getBusRouteStops() != null) {
			busRoute.getBusRouteStops().size();
		}

		return busRoute;
	}

	/**
	 * Найти и обновить
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BusRoute findAndRefresh(Object key) throws Exception {
		BusRoute busRoute = super.find(key);
		busRoute = super.refresh(busRoute);
		if (busRoute.getBusRouteStops() != null) {
			busRoute.getBusRouteStops().size();
		}

		return busRoute;

	}

}
