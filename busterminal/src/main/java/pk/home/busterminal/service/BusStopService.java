package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusStopDAO;
import pk.home.busterminal.domain.BusStop;

/**
 * Service class for entity class: BusStop
 * BusStop - Остановка
 */
@Service
@Transactional
public class BusStopService extends ABaseService<BusStop> {

	@Autowired
	private BusStopDAO busStopDAO;

	@Override
	public ABaseDAO<BusStop> getAbstractBasicDAO() {
		return busStopDAO;
	}

}
