package pk.home.busterminal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusRouteStop_;
import pk.home.busterminal.domain.BusStop;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestBusRouteStopService {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private BusRouteService busRouteStopService;
	private BusRouteStopService busRouteStopStopService;
	private BusStopService busStopService;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusRouteService busRouteStopStopService) {
		this.busRouteStopService = busRouteStopStopService;
	}

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusRouteStopService busRouteStopStopService) {
		this.busRouteStopStopService = busRouteStopStopService;
	}

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusStopService busStopService) {
		this.busStopService = busStopService;
	}

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
	public void test1() throws Exception {

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test 1");
		busStop = busStopService.persist(busStop);

	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntities() throws Exception {

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		long index = busRouteStopStopService.count();
		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
			index++;
		}

		List<BusRouteStop> list = busRouteStopStopService.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesSingularAttributeOfTQSortOrderType()
			throws Exception {
		long index = busRouteStopStopService.count();

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
			index++;
		}

		List<BusRouteStop> list = busRouteStopStopService.getAllEntities(
				BusRouteStop_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (BusRouteStop busRouteStop : list) {
			assertTrue(lastId < busRouteStop.getId());
			lastId = busRouteStop.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(int, int)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesIntInt() throws Exception {

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		// int index = 0;
		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
		}

		List<BusRouteStop> list = busRouteStopStopService
				.getAllEntities(10, 10);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesIntIntSingularAttributeOfTQSortOrderType()
			throws Exception {
		// long index = busRouteStopStopService.count();

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
		}

		List<BusRouteStop> list = busRouteStopStopService.getAllEntities(10,
				10, BusRouteStop_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (BusRouteStop busRouteStop : list) {
			assertTrue(lastId < busRouteStop.getId());
			lastId = busRouteStop.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities(boolean, int, int, javax.persistence.metamodel.SingularAttribute, pk.home.libs.combine.dao.ABaseDAO.SortOrderType)}
	 * .
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntitiesBooleanIntIntSingularAttributeOfTQSortOrderType()
			throws Exception {
		long index = busRouteStopStopService.count();

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
			index++;
		}

		// all - FALSE
		List<BusRouteStop> list = busRouteStopStopService.getAllEntities(false,
				10, 10, BusRouteStop_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (BusRouteStop busRouteStop : list) {
			assertTrue(lastId < busRouteStop.getId());
			lastId = busRouteStop.getId();
		}

		// all - TRUE
		list = busRouteStopStopService.getAllEntities(true, 10, 10,
				BusRouteStop_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (BusRouteStop busRouteStop : list) {
			assertTrue(lastId < busRouteStop.getId());
			lastId = busRouteStop.getId();
		}
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#find(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testFind() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		long index = busRouteStopStopService.count();

		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
			index++;
		}

		long count = busRouteStopStopService.count();

		assertTrue(count == index);
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#persist(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testPersist() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#refresh(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testRefresh() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

		busRouteStop2 = busRouteStopStopService.refresh(busRouteStop2);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#merge(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testMerge() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

		busRouteStop2 = busRouteStopStopService.merge(busRouteStop2);

		busRouteStop = busRouteStopStopService.refresh(busRouteStop);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());
	}

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#remove(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testRemove() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

		busRouteStopStopService.remove(busRouteStop);

		BusRouteStop busRouteStop3 = busRouteStopStopService.find(id);
		assertTrue(busRouteStop3 == null);

	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		long index = busRouteStopStopService.count();
		for (int i = 0; i < 100; i++) {
			BusRouteStop busRouteStop = new BusRouteStop();

			busRouteStop.setBusRoute(busRoute);

			BusStop busStop = new BusStop();
			busStop.setKeyName("test " + i);
			busStop = busStopService.persist(busStop);
			busRouteStop.setOrId(i);
			busRouteStop.setBusStop(busStop);

			busRouteStopStopService.persist(busRouteStop);
			index++;
		}

		List<BusRouteStop> list = busRouteStopStopService.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

	
	
	
	// Тесты на логику -------------------------------------------------------------------------------------------------
	
	
	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#merge(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testMerge1() throws Exception {
		BusRoute busRoute = new BusRoute();
		busRoute.setKeyName("test 1");
		busRoute = busRouteStopService.persist(busRoute);

		BusStop busStop = new BusStop();
		busStop.setKeyName("test " + 1);
		busStop = busStopService.persist(busStop);

		BusRouteStop busRouteStop = new BusRouteStop();
		busRouteStop.setBusRoute(busRoute);
		busRouteStop.setBusStop(busStop);
		busRouteStop.setOrId(1);
		busRouteStop = busRouteStopStopService.persist(busRouteStop);

		long id = busRouteStop.getId();

		BusRouteStop busRouteStop2 = busRouteStopStopService.find(id);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());

		
					
		
		
		busRouteStop2 = busRouteStopStopService.merge(busRouteStop2);
		busRouteStop = busRouteStopStopService.refresh(busRouteStop);

		assertEquals(busRouteStop, busRouteStop2);
		assertTrue(busRouteStop.getId() == busRouteStop2.getId());
	}
	
	
}
