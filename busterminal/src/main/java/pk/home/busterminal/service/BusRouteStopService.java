package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BusRouteStop persist(BusRouteStop o) throws Exception {
		check(o);
		return super.persist(o);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BusRouteStop merge(BusRouteStop o) throws Exception {
		check(o);
		return super.merge(o);
	}
	
	@Transactional(readOnly = true)
	public void check(BusRouteStop o) throws Exception {
		BusRouteStop old = find(o.getId());
		
		if(old != null && old.isProtectionFieldUpdated(o))
			throw new Exception("Менять значения полей pBRStop и nBRStop нельзя");
	}

	
	
	
	
}
