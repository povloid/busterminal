package pk.home.busterminal.testbase;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.BusStopService;
import pk.home.busterminal.service.CustomerService;
import pk.home.busterminal.service.RaceService;
import pk.home.busterminal.service.SchemaService;
import pk.home.busterminal.service.SeatService;
import pk.home.busterminal.service.security.UserAccountService;

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

	@Autowired
	private UserAccountService userAccountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private RaceService raceService;

	@Autowired
	private SchemaService schemaService;

	@Autowired
	private SeatService seatService;

	// Переменные
	// -------------------------------------------------------------------------------
	protected Bus busWork1, busWork2, busTemplite;
	protected Schema schema1, schema2, schema3, schema4;
	protected Seat seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8;

	protected BusRoute busRoute;
	protected BusStop busStop1, busStop2, busStop3;
	protected BusRouteStop busRouteStop1, busRouteStop2, busRouteStop3;
	protected Race race;

	protected Customer customer1, customer2;
	
	protected UserAccount userAccount;

	// ------------------------------------------------------------------------------------------

	@Transactional
	public void createTestEntitys() throws Exception {

		// Создаем шаблон ----------------------------------------
		busTemplite = new Bus();
		busTemplite.setKeyName("Тестовый автобус 2");
		busTemplite.setGosNum("TEST NUM 2");
		busTemplite.setBssType(BssType.TEMPLITE);
		busTemplite = busService.persist(busTemplite);

		// Создаем копии для постановки на рейс ------------------
		// 1
		busWork1 = new Bus();
		busWork1.setKeyName("Тестовый автобус 1");
		busWork1.setGosNum("TEST NUM 1");
		busWork1.setBssType(BssType.WORK);
		busWork1.setTemplite(busTemplite);
		busWork1 = busService.persist(busWork1);
		
		// 2
		busWork2 = new Bus();
		busWork2.setKeyName("Тестовый автобус 2");
		busWork2.setGosNum("TEST NUM 2");
		busWork2.setBssType(BssType.WORK);
		busWork2.setTemplite(busTemplite);
		busWork2 = busService.persist(busWork2);

		// -------------------------------------------------------
		schema1 = new Schema();
		schema1.setKeyName("Тестовая схема 1");
		schema1.setBus(busWork1);
		schema1.setxSize((short) 1);
		schema1.setySize((short) 2);
		schema1 = schemaService.persist(schema1);
		
		schema2 = new Schema();
		schema2.setKeyName("Тестовая схема 2");
		schema2.setBus(busWork2);
		schema2.setxSize((short) 4);
		schema2.setySize((short) 2);
		schema2 = schemaService.persist(schema2);
		

		seat1 = new Seat();
		seat1.setNum((short) 1);
		seat1.setSx((short) 1);
		seat1.setSy((short) 1);
		seat1.setSchema(schema1);
		seat1 = seatService.persist(seat1);

		seat2 = new Seat();
		seat2.setNum((short) 2);
		seat2.setSx((short) 1);
		seat2.setSy((short) 2);
		seat2.setSchema(schema1);
		seat2 = seatService.persist(seat2);
		
		// 2 -------------------------------
		
		seat8 = new Seat();
		seat8.setNum((short) 8);
		seat8.setSx((short) 1);
		seat8.setSy((short) 1);
		seat8.setSchema(schema2);
		seat8 = seatService.persist(seat8);
		
		//..
		schema1 = schemaService.refresh(schema1);
		schema2 = schemaService.refresh(schema2);
		
		busWork1 = busService.refresh(busWork1);
		busWork2 = busService.refresh(busWork2);
		

		// --------------------------------------------------------

/*		busWork2 = new Bus();
		busWork2.setKeyName("Тестовый автобус 2");
		busWork2.setGosNum("TEST NUM 2");
		busWork2.setBssType(BssType.WORK);
		busWork2.setTemplite(busTemplite);
		busWork2 = busService.persist(busWork2);*/

		// Создаем маршрут ---------------------------------------
		busRoute = new BusRoute();
		busRoute.setKeyName("TEST BUS ROUTE");
		busRoute = busRouteService.persist(busRoute);
		busRoute = busRouteService.refresh(busRoute);
		

		// Создаем остановки -------------------------------------
		busStop1 = new BusStop();
		busStop1.setKeyName("ТЕСТ ОСТАНОВКА 1");
		busStop1 = busStopService.persist(busStop1);

		busStop2 = new BusStop();
		busStop2.setKeyName("ТЕСТ ОСТАНОВКА 2");
		busStop2 = busStopService.persist(busStop2);

		busStop3 = new BusStop();
		busStop3.setKeyName("ТЕСТ ОСТАНОВКА 3");
		busStop3 = busStopService.persist(busStop3);

		// Создаем траекторию для маршрута -----------------------
		
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
		
		// ---
		busRoute = busRouteService.refresh(busRoute);
		
		// Создаем рейс -------------------------------------------

		race = new Race();
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race.setdTime(createUniqueDate());
		race = raceService.persist(race);

		// Создаем пользователя -----------------------------------
		userAccount = new UserAccount();
		userAccount.setUsername("testuser1");
		userAccount.setPassword(passwordEncoder
				.encodePassword("password", null));

		userAccount = userAccountService.persist(userAccount);

		// Создаем клиента -----------------------------------------
		customer1 = new Customer();
		customer1.setKeyName("Дядя Вася");
		customer1 = customerService.persist(customer1);

		customer2 = new Customer();
		customer2.setKeyName("Дядя Петя");
		customer2 = customerService.persist(customer2);
		

	}

	// Работа со временем
	// ----------------------------------------------------------------------------------------

	private long time = (new Date()).getTime();

	/**
	 * Породить уникальное время;
	 * 
	 * @return
	 */
	protected Date createUniqueDate() {
		return new Date(++time);
	}

}
