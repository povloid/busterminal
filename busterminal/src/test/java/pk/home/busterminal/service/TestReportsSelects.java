package pk.home.busterminal.service;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.service.OrderService.FindOrdersOrderByBusRouteStopsResult;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestReportsSelects {

	@Autowired
	private OrderService orderService;

	@Autowired
	private RaceService raceService;

	@Autowired
	private DivisionService divisionService;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Rollback(true)
	public void testFindOrdersOrderByBusRouteStops() throws Exception {
		Race race = raceService.find(1l);

		List<FindOrdersOrderByBusRouteStopsResult> listo = orderService
				.findOrdersOrderByBusRouteStops(race);

		for (FindOrdersOrderByBusRouteStopsResult r : listo) {
			System.out.println(r.getBusRouteStop().getOrId() + " "
					+ r.getBusRouteStop().getBusStop().getKeyName() + " "
					+ r.getgOrder() + " --- " + r.getpOrder());
		}

		System.out.println(listo.size());

	}

	@Test
	@Rollback(true)
	public void testOrdersForDateAndDivision() throws Exception {
		Division div = divisionService.find(2l);

		Date bdate = new Date((new Date()).getTime() - 1000 * 24 * 24 * 60 * 30);

		Date edate = new Date();

		List<Order> list = orderService.findOrdersForDateAndDivision(div,
				bdate, edate);

		System.out.println(list.size());
	}

	@Test
	@Rollback(true)
	public void testFindOrdersForDateAndDivisionO() throws Exception {
		Division div = divisionService.find(2l);

		Date bdate = new Date((new Date()).getTime() - 1000 * 24 * 24 * 60 * 30);

		Date edate = new Date();

		List<Object[]> list = orderService.findOrdersForDateAndDivisionO(div,
				bdate, edate);

		System.out.println(list.size());

		Number n = orderService.findOrdersForDateAndDivisionBalance(div, bdate,
				edate);

		System.out.println(n);

		Number n2 = orderService.findOrdersDivisionBalance(div);

		System.out.println(n2);

	}

}
