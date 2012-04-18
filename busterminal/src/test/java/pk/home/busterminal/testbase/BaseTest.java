package pk.home.busterminal.testbase;

import java.util.Date;

import org.hibernate.annotations.Synchronize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.BusStopService;

@Transactional
public class BaseTest {

	@Autowired
	private BusService busService;

	@Autowired
	private BusRouteService busRouteService;

	@Autowired
	private BusRouteStopService busRouteStopService;

	@Autowired
	private BusStopService busStopService;

	
	// Переменные
	
	protected Bus busWork1, busWork2, busTemplite;
	protected BusRoute busRoute;

	protected BusStop busStop1, busStop2, busStop3;

	protected BusRouteStop busRouteStop1, busRouteStop2, busRouteStop3;

	@Transactional
	public void createTestEntitys() throws Exception {

		busTemplite = new Bus();
		busTemplite.setKeyName("Тестовый автобус 2");
		busTemplite.setGosNum("TEST NUM 2");
		busTemplite.setBssType(BssType.TEMPLITE);
		busTemplite = busService.persist(busTemplite);

		busWork1 = new Bus();
		busWork1.setKeyName("Тестовый автобус 1");
		busWork1.setGosNum("TEST NUM 1");
		busWork1.setBssType(BssType.WORK);
		busWork1.setTemplite(busTemplite);
		busWork1 = busService.persist(busWork1);

		busWork2 = new Bus();
		busWork2.setKeyName("Тестовый автобус 2");
		busWork2.setGosNum("TEST NUM 2");
		busWork2.setBssType(BssType.WORK);
		busWork2.setTemplite(busTemplite);
		busWork2 = busService.persist(busWork2);

		busRoute = new BusRoute();
		busRoute.setKeyName("TEST BUS ROUTE");
		busRoute = busRouteService.persist(busRoute);
		busRoute = busRouteService.refresh(busRoute);

		busStop1 = new BusStop();
		busStop1.setKeyName("ТЕСТ ОСТАНОВКА 1");
		busStop1 = busStopService.persist(busStop1);

		busStop2 = new BusStop();
		busStop2.setKeyName("ТЕСТ ОСТАНОВКА 2");
		busStop2 = busStopService.persist(busStop2);

		busStop3 = new BusStop();
		busStop3.setKeyName("ТЕСТ ОСТАНОВКА 3");
		busStop3 = busStopService.persist(busStop3);

		busRouteStop1 = new BusRouteStop();
		busRouteStop1.setBusStop(busStop1);
		busRouteStop1.setBusRoute(busRoute);
		busRouteStop1.setOrId(1);
		busRouteStop1 = busRouteStopService.persist(busRouteStop1);
		busRoute.getBusRouteStops().add(busRouteStop1);

		busRouteStop2 = new BusRouteStop();
		busRouteStop2.setBusStop(busStop2);
		busRouteStop2.setBusRoute(busRoute);
		busRouteStop2.setOrId(2);
		busRouteStop2 = busRouteStopService.persist(busRouteStop2);
		busRoute.getBusRouteStops().add(busRouteStop2);

		busRouteStop3 = new BusRouteStop();
		busRouteStop3.setBusStop(busStop3);
		busRouteStop3.setBusRoute(busRoute);
		busRouteStop3.setOrId(3);
		busRouteStop3 = busRouteStopService.persist(busRouteStop3);
		busRoute.getBusRouteStops().add(busRouteStop3);
	}

	
	// Работа со временем ----------------------------------------------------------------------------------------
	
	private long time = (new Date()).getTime();
	
	/**
	 * Породить уникальное время;
	 * @return
	 */
	protected Date createUniqueDate(){
		return new Date(++time);
	}
	
	
	
	
	
	
}
