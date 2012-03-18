package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusRouteDAO;
import pk.home.busterminal.domain.BusRoute;

/**
 * Service class for entity class: BusRoute
 * BusRoute - Маршрут
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

}
