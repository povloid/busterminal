/**
 * 
 */
package pk.home.busterminal.dao;

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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * @author povloid
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
@ActiveProfiles({"Dev"})
public class TestBusDAO {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private BusDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusDAO dataStore) {
		this.dataStore = dataStore;
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

		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			index++;
		}

		List<Bus> list = dataStore.getAllEntities();

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
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			index++;
		}

		List<Bus> list = dataStore.getAllEntities(Bus_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Bus bus : list) {
			assertTrue(lastId < bus.getId());
			lastId = bus.getId();
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
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			// index++;
		}

		List<Bus> list = dataStore.getAllEntities(10, 10);

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
		// long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			// index++;
		}

		List<Bus> list = dataStore.getAllEntities(10, 10, Bus_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Bus bus : list) {
			assertTrue(lastId < bus.getId());
			lastId = bus.getId();
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
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			index++;
		}

		// all - FALSE
		List<Bus> list = dataStore.getAllEntities(false, 10, 10, Bus_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Bus bus : list) {
			assertTrue(lastId < bus.getId());
			lastId = bus.getId();
		}

		// all - TRUE
		list = dataStore.getAllEntities(true, 10, 10, Bus_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Bus bus : list) {
			assertTrue(lastId < bus.getId());
			lastId = bus.getId();
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

		Bus bus1 = new Bus();
		bus1.setBssType(BssType.TEMPLITE);
		bus1.setKeyName("key " + 999);
		bus1.setGosNum("H 999 TRZ");
		bus1 = dataStore.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = dataStore.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			index++;
		}

		long count = dataStore.count();

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
		Bus bus1 = new Bus();
		bus1.setBssType(BssType.TEMPLITE);
		bus1.setKeyName("key " + 999);
		bus1.setGosNum("H 999 TRZ");
		bus1 = dataStore.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = dataStore.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());
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
		Bus bus1 = new Bus();
		bus1.setBssType(BssType.TEMPLITE);
		bus1.setKeyName("key " + 999);
		bus1.setGosNum("H 999 TRZ");
		bus1 = dataStore.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = dataStore.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		bus2.setKeyName("key 65535");
		bus2 = dataStore.refresh(bus2);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

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
		Bus bus1 = new Bus();
		bus1.setBssType(BssType.TEMPLITE);
		bus1.setKeyName("key " + 999);
		bus1.setGosNum("H 999 TRZ");
		bus1 = dataStore.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = dataStore.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		bus2.setKeyName("key 65535");
		bus2 = dataStore.merge(bus2);

		bus1 = dataStore.refresh(bus1);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());
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
		Bus bus1 = new Bus();
		bus1.setBssType(BssType.TEMPLITE);
		bus1.setKeyName("key " + 999);
		bus1.setGosNum("H 999 TRZ");
		bus1 = dataStore.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = dataStore.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		dataStore.remove(bus1);

		Bus bus3 = dataStore.find(id);
		assertTrue(bus3 == null);

	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = dataStore.count();
		for (int i = 200; i < 210; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			dataStore.persist(bus1);
			index++;
		}

		List<Bus> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

}
