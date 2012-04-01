package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.BusRouteStopDAO;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;

@Service
@Transactional
public class BusRouteStopService extends ABaseService<BusRouteStop> {

	@Autowired
	private BusRouteStopDAO busRouteStopDAO;

	@Override
	public ABaseDAO<BusRouteStop> getAbstractBasicDAO() {
		return busRouteStopDAO;
	}

}
