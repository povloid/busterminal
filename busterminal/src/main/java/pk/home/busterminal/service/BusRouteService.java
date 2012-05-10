package pk.home.busterminal.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusRouteDAO;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;

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

	/**
	 * Содержит ли данную остановку
	 * 
	 * @param busRoute
	 * @param busRouteStop
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public boolean isContain(BusRoute busRoute, BusRouteStop busRouteStop)
			throws Exception {
		busRoute = findWithLazy(busRoute.getId());

		for (BusRouteStop brs : busRoute.getBusRouteStops()) {
			if (brs != null && brs.equals(busRouteStop)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Получить путь - список остановок от начальной до конечной
	 * 
	 * @param busRoute
	 * @param brstStart
	 * @param brstFinish
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<BusRouteStop> getPath(BusRoute busRoute,
			BusRouteStop brstStart, BusRouteStop brstFinish) throws Exception {

		List<BusRouteStop> path = new ArrayList<BusRouteStop>();

		busRoute = findWithLazy(busRoute.getId());

		path.add(brstStart);

		boolean add = false;
		for (BusRouteStop brs : busRoute.getBusRouteStops()) {

			if (brs.equals(brstStart)) {
				add = true;
				continue;
			}

			if (brs.equals(brstFinish)) {
				add = false;
				break;
			}

			if (add) {
				path.add(brs);
			}

		}

		path.add(brstFinish);

		return path;
	}

}
