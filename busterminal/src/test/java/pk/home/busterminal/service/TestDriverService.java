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
import pk.home.busterminal.domain.Driver;
import pk.home.busterminal.domain.Driver_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test service class for entity class: Driver
 * Driver - водитель
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestDriverService {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private DriverService service;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(DriverService service) {
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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			index++;
		}

		List<Driver> list = service.getAllEntities();

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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			index++;
		}

		List<Driver> list = service.getAllEntities(Driver_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Driver driver : list) {
			assertTrue(lastId < driver.getId());
			lastId = driver.getId();
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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			// index++;
		}

		List<Driver> list = service.getAllEntities(10, 10);

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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			// index++;
		}

		List<Driver> list = service.getAllEntities(10, 10, Driver_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Driver driver : list) {
			assertTrue(lastId < driver.getId());
			lastId = driver.getId();
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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			index++;
		}

		// all - FALSE
		List<Driver> list = service.getAllEntities(false, 10, 10, Driver_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Driver driver : list) {
			assertTrue(lastId < driver.getId());
			lastId = driver.getId();
		}

		// all - TRUE
		list = service.getAllEntities(true, 10, 10, Driver_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Driver driver : list) {
			assertTrue(lastId < driver.getId());
			lastId = driver.getId();
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

		Driver driver = new Driver();
		driver.setKeyName("key " + 999);
		driver = service.persist(driver);

		long id = driver.getId();

		Driver driver2 = service.find(id);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());

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
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
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
		Driver driver = new Driver();
		driver.setKeyName("key " + 999);
		driver = service.persist(driver);

		long id = driver.getId();

		Driver driver2 = service.find(id);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());
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
		Driver driver = new Driver();
		driver.setKeyName("key " + 999);
		driver = service.persist(driver);

		long id = driver.getId();

		Driver driver2 = service.find(id);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());

		driver2.setKeyName("key 65535");
		driver2 = service.refresh(driver2);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());

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
		Driver driver = new Driver();
		driver.setKeyName("key " + 999);
		driver = service.persist(driver);

		long id = driver.getId();

		Driver driver2 = service.find(id);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());

		driver2.setKeyName("key 65535");
		driver2 = service.merge(driver2);

		driver = service.refresh(driver);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());
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
		Driver driver = new Driver();
		driver.setKeyName("key " + 999);
		driver = service.persist(driver);

		long id = driver.getId();

		Driver driver2 = service.find(id);

		assertEquals(driver, driver2);
		assertTrue(driver.getId() == driver2.getId());
		assertEquals(driver.getKeyName(), driver2.getKeyName());

		service.remove(driver);

		Driver driver3 = service.find(id);
		assertTrue(driver3 == null);

	}
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = service.count();
		for (int i = 200; i < 210; i++) {
			Driver driver = new Driver();
			driver.setKeyName("key " + i);
			service.persist(driver);
			index++;
		}

		List<Driver> list = service.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}
	

}
