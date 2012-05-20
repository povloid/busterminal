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
import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.domain.security.UserAccount;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusRouteStopService;
import pk.home.busterminal.service.BusService;
import pk.home.busterminal.service.BusStopService;
import pk.home.busterminal.service.CustomerService;
import pk.home.busterminal.service.RaceService;
import pk.home.busterminal.service.SchemaService;
import pk.home.busterminal.service.SeatService;
import pk.home.busterminal.service.SeatTypeService;
import pk.home.busterminal.service.security.UserAccountService;

@Transactional
public class BaseTest {

	@Autowired
	protected BusService busService;

	@Autowired
	protected BusRouteService busRouteService;

	@Autowired
	protected BusRouteStopService busRouteStopService;

	@Autowired
	protected BusStopService busStopService;

	@Autowired
	protected UserAccountService userAccountService;

	@Autowired
	protected PasswordEncoder passwordEncoder;

	@Autowired
	protected CustomerService customerService;

	@Autowired
	protected RaceService raceService;

	@Autowired
	protected SchemaService schemaService;

	@Autowired
	protected SeatService seatService;
	
	@Autowired
	protected SeatTypeService seatTypeService;

	// Переменные
	// -------------------------------------------------------------------------------
	protected SeatType seatType;
	
	protected Bus busWork1, busWork2, busTemplite;
	protected Schema schema1, schema2, schema3, schema4;
	protected Seat seat1, seat2, seat3, seat4, seat5, seat6, seat7, seat8;

	protected BusRoute busRoute1;
	protected BusStop busStop11, busStop12, busStop13, busStop14, busStop15,
			busStop16;
	protected BusRouteStop busRouteStop11, busRouteStop12, busRouteStop13,
			busRouteStop14, busRouteStop15, busRouteStop16;

	protected BusRoute busRoute2;
	protected BusStop busStop21, busStop22, busStop23;
	protected BusRouteStop busRouteStop21, busRouteStop22, busRouteStop23;

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
		
		seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		// 1 -------------------------------------------------------
		schema1 = new Schema();
		schema1.setKeyName("Тестовая схема 1");
		schema1.setBus(busTemplite);
		schema1.setxSize((short) 1);
		schema1.setySize((short) 2);
		schema1 = schemaService.persist(schema1);

		seat1 = new Seat();
		seat1.setNum((short) 1);
		seat1.setSx((short) 1);
		seat1.setSy((short) 1);
		seat1.setSchema(schema1);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		seat2 = new Seat();
		seat2.setNum((short) 2);
		seat2.setSx((short) 1);
		seat2.setSy((short) 2);
		seat2.setSchema(schema1);
		seat2.setSeatType(seatType);
		seat2 = seatService.persist(seat2);

		// 2 -------------------------------

		schema2 = new Schema();
		schema2.setKeyName("Тестовая схема 2");
		schema2.setBus(busTemplite);
		schema2.setxSize((short) 4);
		schema2.setySize((short) 2);
		schema2 = schemaService.persist(schema2);

		seat8 = new Seat();
		seat8.setNum((short) 8);
		seat8.setSx((short) 1);
		seat8.setSy((short) 1);
		seat8.setSchema(schema2);
		seat8.setSeatType(seatType);
		seat8 = seatService.persist(seat8);

		// ..
		schema1 = schemaService.refresh(schema1);
		schema2 = schemaService.refresh(schema2);
		busTemplite = busService.refresh(busTemplite);

		// Создаем копии для постановки на рейс ------------------
		// 1
		busWork1 = busService.createWorkCopyFromTemplite(busTemplite);

		// 2
		busWork2 = busService.createWorkCopyFromTemplite(busTemplite);

		// --------------------------------------------------------

		/*
		 * busWork2 = new Bus(); busWork2.setKeyName("Тестовый автобус 2");
		 * busWork2.setGosNum("TEST NUM 2"); busWork2.setBssType(BssType.WORK);
		 * busWork2.setTemplite(busTemplite); busWork2 =
		 * busService.persist(busWork2);
		 */

		// Создаем маршрут 1 ---------------------------------------
		busRoute1 = new BusRoute();
		busRoute1.setKeyName("TEST BUS ROUTE 1");
		busRoute1 = busRouteService.persist(busRoute1);
		busRoute1 = busRouteService.refresh(busRoute1);

		// Создаем остановки 1 -------------------------------------
		busStop11 = new BusStop();
		busStop11.setKeyName("ТЕСТ ОСТАНОВКА 11");
		busStop11 = busStopService.persist(busStop11);

		busStop12 = new BusStop();
		busStop12.setKeyName("ТЕСТ ОСТАНОВКА 12");
		busStop12 = busStopService.persist(busStop12);

		busStop13 = new BusStop();
		busStop13.setKeyName("ТЕСТ ОСТАНОВКА 13");
		busStop13 = busStopService.persist(busStop13);

		busStop14 = new BusStop();
		busStop14.setKeyName("ТЕСТ ОСТАНОВКА 14");
		busStop14 = busStopService.persist(busStop14);

		busStop15 = new BusStop();
		busStop15.setKeyName("ТЕСТ ОСТАНОВКА 15");
		busStop15 = busStopService.persist(busStop15);

		busStop16 = new BusStop();
		busStop16.setKeyName("ТЕСТ ОСТАНОВКА 16");
		busStop16 = busStopService.persist(busStop16);

		// Создаем траекторию для маршрута 1 -----------------------
		busRouteStop11 = new BusRouteStop();
		busRouteStop11.setBusStop(busStop11);
		busRouteStop11.setBusRoute(busRoute1);
		busRouteStop11.setOrId(0);
		busRouteStop11 = busRouteStopService.persist(busRouteStop11);
		busRoute1.getBusRouteStops().add(busRouteStop11);

		busRouteStop12 = new BusRouteStop();
		busRouteStop12.setBusStop(busStop12);
		busRouteStop12.setBusRoute(busRoute1);
		busRouteStop12.setOrId(1);
		busRouteStop12 = busRouteStopService.persist(busRouteStop12);
		busRoute1.getBusRouteStops().add(busRouteStop12);

		busRouteStop13 = new BusRouteStop();
		busRouteStop13.setBusStop(busStop13);
		busRouteStop13.setBusRoute(busRoute1);
		busRouteStop13.setOrId(2);
		busRouteStop13 = busRouteStopService.persist(busRouteStop13);
		busRoute1.getBusRouteStops().add(busRouteStop13);

		busRouteStop14 = new BusRouteStop();
		busRouteStop14.setBusStop(busStop14);
		busRouteStop14.setBusRoute(busRoute1);
		busRouteStop14.setOrId(3);
		busRouteStop14 = busRouteStopService.persist(busRouteStop14);
		busRoute1.getBusRouteStops().add(busRouteStop14);

		busRouteStop15 = new BusRouteStop();
		busRouteStop15.setBusStop(busStop15);
		busRouteStop15.setBusRoute(busRoute1);
		busRouteStop15.setOrId(4);
		busRouteStop15 = busRouteStopService.persist(busRouteStop15);
		busRoute1.getBusRouteStops().add(busRouteStop15);

		busRouteStop16 = new BusRouteStop();
		busRouteStop16.setBusStop(busStop16);
		busRouteStop16.setBusRoute(busRoute1);
		busRouteStop16.setOrId(5);
		busRouteStop16 = busRouteStopService.persist(busRouteStop16);
		busRoute1.getBusRouteStops().add(busRouteStop16);

		// ---
		busRoute1 = busRouteService.refresh(busRoute1);
		// System.out.println(">>>" + busRoute.getBusRouteStops().size());

		// Создаем маршрут ---------------------------------------
		busRoute2 = new BusRoute();
		busRoute2.setKeyName("TEST BUS ROUTE 2");
		busRoute2 = busRouteService.persist(busRoute2);
		busRoute2 = busRouteService.refresh(busRoute2);

		// Создаем остановки -------------------------------------
		busStop21 = new BusStop();
		busStop21.setKeyName("ТЕСТ ОСТАНОВКА 21");
		busStop21 = busStopService.persist(busStop21);

		busStop22 = new BusStop();
		busStop22.setKeyName("ТЕСТ ОСТАНОВКА 22");
		busStop22 = busStopService.persist(busStop22);

		busStop23 = new BusStop();
		busStop23.setKeyName("ТЕСТ ОСТАНОВКА 23");
		busStop23 = busStopService.persist(busStop23);

		// Создаем траекторию для маршрута -----------------------
		busRouteStop21 = new BusRouteStop();
		busRouteStop21.setBusStop(busStop21);
		busRouteStop21.setBusRoute(busRoute2);
		busRouteStop21.setOrId(0);
		busRouteStop21 = busRouteStopService.persist(busRouteStop21);
		busRoute2.getBusRouteStops().add(busRouteStop21);

		busRouteStop22 = new BusRouteStop();
		busRouteStop22.setBusStop(busStop12);
		busRouteStop22.setBusRoute(busRoute2);
		busRouteStop22.setOrId(1);
		busRouteStop22 = busRouteStopService.persist(busRouteStop22);
		busRoute2.getBusRouteStops().add(busRouteStop22);

		busRouteStop23 = new BusRouteStop();
		busRouteStop23.setBusStop(busStop23);
		busRouteStop23.setBusRoute(busRoute2);
		busRouteStop23.setOrId(2);
		busRouteStop23 = busRouteStopService.persist(busRouteStop23);
		busRoute2.getBusRouteStops().add(busRouteStop23);

		// ---
		busRoute2 = busRouteService.refresh(busRoute2);
		// System.out.println(">>>" + busRoute.getBusRouteStops().size());

		// Создаем рейс -------------------------------------------

		race = new Race();
		race.setBus(busWork1);
		race.setBusRoute(busRoute1);
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
