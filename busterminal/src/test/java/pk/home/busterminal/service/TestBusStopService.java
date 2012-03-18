package pk.home.busterminal.service;

import static org.junit.Assert.*;
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
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.domain.BusStop_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test service class for entity class: BusStop
 * BusStop - Остановка
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestBusStopService {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private BusStopService service;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusStopService service) {
		this.service = service;
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

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#getAllEntities()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testGetAllEntities() throws Exception {

		long index = service.count();
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			index++;
		}

		List<BusStop> list = service.getAllEntities();

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
		long index = service.count();
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			index++;
		}

		List<BusStop> list = service.getAllEntities(BusStop_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (BusStop busStop : list) {
			assertTrue(lastId < busStop.getId());
			lastId = busStop.getId();
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

		// int index = 0;
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			// index++;
		}

		List<BusStop> list = service.getAllEntities(10, 10);

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
		// long index = service.count();
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			// index++;
		}

		List<BusStop> list = service.getAllEntities(10, 10, BusStop_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (BusStop busStop : list) {
			assertTrue(lastId < busStop.getId());
			lastId = busStop.getId();
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
		long index = service.count();
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			index++;
		}

		// all - FALSE
		List<BusStop> list = service.getAllEntities(false, 10, 10, BusStop_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (BusStop busStop : list) {
			assertTrue(lastId < busStop.getId());
			lastId = busStop.getId();
		}

		// all - TRUE
		list = service.getAllEntities(true, 10, 10, BusStop_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (BusStop busStop : list) {
			assertTrue(lastId < busStop.getId());
			lastId = busStop.getId();
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

		BusStop busStop = new BusStop();
		busStop.setKeyName("key " + 999);
		busStop = service.persist(busStop);

		long id = busStop.getId();

		BusStop busStop2 = service.find(id);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		long index = service.count();
		for (int i = 0; i < 100; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			index++;
		}

		long count = service.count();

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
		BusStop busStop = new BusStop();
		busStop.setKeyName("key " + 999);
		busStop = service.persist(busStop);

		long id = busStop.getId();

		BusStop busStop2 = service.find(id);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());
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
		BusStop busStop = new BusStop();
		busStop.setKeyName("key " + 999);
		busStop = service.persist(busStop);

		long id = busStop.getId();

		BusStop busStop2 = service.find(id);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());

		busStop2.setKeyName("key 65535");
		busStop2 = service.refresh(busStop2);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());

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
		BusStop busStop = new BusStop();
		busStop.setKeyName("key " + 999);
		busStop = service.persist(busStop);

		long id = busStop.getId();

		BusStop busStop2 = service.find(id);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());

		busStop2.setKeyName("key 65535");
		busStop2 = service.merge(busStop2);

		busStop = service.refresh(busStop);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());
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
		BusStop busStop = new BusStop();
		busStop.setKeyName("key " + 999);
		busStop = service.persist(busStop);

		long id = busStop.getId();

		BusStop busStop2 = service.find(id);

		assertEquals(busStop, busStop2);
		assertTrue(busStop.getId() == busStop2.getId());
		assertEquals(busStop.getKeyName(), busStop2.getKeyName());

		service.remove(busStop);

		BusStop busStop3 = service.find(id);
		assertTrue(busStop3 == null);

	}
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = service.count();
		for (int i = 200; i < 210; i++) {
			BusStop busStop = new BusStop();
			busStop.setKeyName("key " + i);
			service.persist(busStop);
			index++;
		}

		List<BusStop> list = service.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}
	

}
