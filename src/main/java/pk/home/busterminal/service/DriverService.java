package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.DriverDAO;
import pk.home.busterminal.domain.Driver;

/**
 * Service class for entity class: Driver
 * Driver - водитель
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class DriverService extends ABaseService<Driver> {

	@Autowired
	private DriverDAO driverDAO;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
	@Override
	public ABaseDAO<Driver> getAbstractBasicDAO() {
		return driverDAO;
	}

}
