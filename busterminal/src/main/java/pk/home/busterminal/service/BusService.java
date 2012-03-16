package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.BusDAO;
import pk.home.busterminal.domain.Bus;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;

@Service
@Transactional
public class BusService extends ABaseService<Bus> {

	@Autowired
	private BusDAO busDAO;

	@Override
	public ABaseDAO<Bus> getAbstractBasicDAO() {
		return busDAO;
	}

	@Transactional(readOnly = true)
	public Bus findWithLazy(Object key) throws Exception {
		Bus bus = super.find(key);

		if (bus.getSchemas() != null) {
			bus.getSchemas().size();
		}

		return bus;
	}

}
