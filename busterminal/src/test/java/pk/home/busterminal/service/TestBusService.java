/**
 * 
 */
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

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.testbase.BaseTest;
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
public class TestBusService extends BaseTest {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private BusService busService;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BusService busService) {
		this.busService = busService;
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

		long index = busService.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			busService.persist(bus1);
			index++;
		}

		List<Bus> list = busService.getAllEntities();

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
		long index = busService.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			busService.persist(bus1);
			index++;
		}

		List<Bus> list = busService.getAllEntities(Bus_.id, SortOrderType.ASC);

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
			busService.persist(bus1);
			// index++;
		}

		List<Bus> list = busService.getAllEntities(10, 10);

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
			busService.persist(bus1);
			// index++;
		}

		List<Bus> list = busService.getAllEntities(10, 10, Bus_.id,
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
		long index = busService.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			busService.persist(bus1);
			index++;
		}

		// all - FALSE
		List<Bus> list = busService.getAllEntities(false, 10, 10, Bus_.id,
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
		list = busService.getAllEntities(true, 10, 10, Bus_.id,
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
		bus1 = busService.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = busService.find(id);

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
		long index = busService.count();
		for (int i = 0; i < 100; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			busService.persist(bus1);
			index++;
		}

		long count = busService.count();

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
		bus1 = busService.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = busService.find(id);

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
		bus1 = busService.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = busService.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		bus2.setKeyName("key 65535");
		bus2 = busService.refresh(bus2);

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
		bus1 = busService.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = busService.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		bus2.setKeyName("key 65535");
		bus2 = busService.merge(bus2);

		bus1 = busService.refresh(bus1);

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
		bus1 = busService.persist(bus1);

		long id = bus1.getId();

		Bus bus2 = busService.find(id);

		assertEquals(bus1, bus2);
		assertTrue(bus1.getId() == bus2.getId());
		assertEquals(bus1.getKeyName(), bus2.getKeyName());

		busService.remove(bus1);

		Bus bus3 = busService.find(id);
		assertTrue(bus3 == null);

	}

	// bus logical tests
	// -----------------------------------------------------------------------------------------------
	@Test
	@Rollback(true)
	public void insertBssTypeNull() {
		try {
			Bus bus1 = new Bus();
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			assertTrue(
					"Проверка не прошла, допущено сохранение без указания типа",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

	}

	@Test
	@Rollback(true)
	public void insertTempliteEntities() {
		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			Bus bus2 = new Bus();
			bus2.setBssType(BssType.TEMPLITE);
			bus2.setKeyName("key 5");
			bus2.setGosNum("H 5555 TRZ");
			bus2.setTemplite(bus1);

			busService.persist(bus2);

			assertTrue(
					"Проверка не прошла, допущено сохранение шаблона с родительским элементом",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

	}

	@Test
	@Rollback(true)
	public void insertTempliteCheckUniqueKeyNme() {
		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			Bus bus2 = new Bus();
			bus2.setBssType(BssType.TEMPLITE);
			bus2.setKeyName("key 4");
			bus2.setGosNum("H 5555 TRZ");

			busService.persist(bus2);

			assertTrue(
					"Проверка не прошла, допущено нарушение уникальности шаблона по полю keyName",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			Bus bus2 = new Bus();
			bus2.setBssType(BssType.TEMPLITE);
			bus2.setKeyName("key 5");
			bus2.setGosNum("H 5555 TRZ");

			busService.persist(bus2);

			assertTrue(true);

			bus2.setKeyName("key 3");
			busService.merge(bus2);

			assertTrue(true);

			bus2.setKeyName("key 4");
			busService.merge(bus2);

			assertTrue(
					"Проверка не прошла, допущено нарушение уникальности шаблона по полю keyName",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

	}

	@Test
	@Rollback(true)
	public void insertTempliteCheckUniquegGosNum() {
		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			Bus bus2 = new Bus();
			bus2.setBssType(BssType.TEMPLITE);
			bus2.setKeyName("key 5");
			bus2.setGosNum("H 4444 TRZ");

			busService.persist(bus2);

			assertTrue(
					"Проверка не прошла, допущено нарушение уникальности шаблона по полю gosNum",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			Bus bus2 = new Bus();
			bus2.setBssType(BssType.TEMPLITE);
			bus2.setKeyName("key 5");
			bus2.setGosNum("H 5555 TRZ");

			busService.persist(bus2);

			assertTrue(true);

			bus2.setGosNum("H 2222 TRZ");
			busService.merge(bus2);

			assertTrue(true);

			bus2.setGosNum("H 4444 TRZ");
			busService.merge(bus2);

			assertTrue(
					"Проверка не прошла, допущено нарушение уникальности шаблона по полю gosNum",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

	}

	// -----------------------------------------------------------------------------------------------------------------
	@Test
	@Rollback(true)
	public void insertWorkEntities() {
		try {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.WORK);
			bus1.setKeyName("key 4");
			bus1.setGosNum("H 4444 TRZ");
			busService.persist(bus1);

			assertTrue(
					"Проверка не прошла, допущено сохранение рабочей версии без родительского элемента",
					false);

		} catch (Exception e) {
			e.printStackTrace();

			assertTrue("Проверка сработала правильно", true);
		}

	}

	@Test
	@Rollback(true)
	public void createBusCopy() throws Exception {
		// Создаем шаблон ----------------------------------------
		busTemplite = new Bus();
		busTemplite.setKeyName("Тестовый автобус 2");
		busTemplite.setGosNum("TEST NUM 2");
		busTemplite.setBssType(BssType.TEMPLITE);
		busTemplite = busService.persist(busTemplite);

		// -------------------------------------------------------
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
		seat1 = seatService.persist(seat1);

		seat2 = new Seat();
		seat2.setNum((short) 2);
		seat2.setSx((short) 1);
		seat2.setSy((short) 2);
		seat2.setSchema(schema1);
		seat2 = seatService.persist(seat2);

		schema1 = schemaService.refresh(schema1);

		busTemplite = busService.refresh(busTemplite);

		Bus busCopy = busService.createBusCopy(busTemplite);

		assertEquals(busTemplite.getKeyName(), busCopy.getKeyName());
		assertEquals(busTemplite.getDescription(), busCopy.getDescription());
		assertEquals(busTemplite.getGosNum(), busCopy.getGosNum());
		assertEquals(busTemplite.getBssType(), busCopy.getBssType());
		assertFalse(busTemplite.getId() == busCopy.getId());

		assertTrue(busTemplite.getSchemas().size() == busCopy.getSchemas()
				.size());

		Schema[] aa = busTemplite.getSchemas().toArray(new Schema[0]);
		Schema[] ba = busCopy.getSchemas().toArray(new Schema[0]);

		for (int i = 0; i < busTemplite.getSchemas().size(); i++) {
			Schema a = aa[i];
			Schema b = ba[i];

			assertEquals(a.getKeyName(), b.getKeyName());
			assertEquals(a.getDescription(), b.getDescription());
			assertEquals(a.getxSize(), b.getxSize());
			assertEquals(a.getySize(), b.getySize());
			assertTrue(a.getSeats().size() == b.getSeats().size());

			assertFalse(a.getId() == b.getId());

			Seat[] asa = a.getSeats().toArray(new Seat[0]);
			Seat[] bsa = b.getSeats().toArray(new Seat[0]);

			for (int j = 0; j < busTemplite.getSchemas().size(); j++) {
				Seat as = asa[j];
				Seat bs = bsa[j];

				assertEquals(as.getDescription(), bs.getDescription());
				assertEquals(as.getNum(), bs.getNum());
				assertEquals(as.getSx(), bs.getSx());
				assertEquals(as.getSy(), bs.getSy());

				assertFalse(as.getId() == bs.getId());
			}
		}
		
		
		

	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = busService.count();
		for (int i = 200; i < 210; i++) {
			Bus bus1 = new Bus();
			bus1.setBssType(BssType.TEMPLITE);
			bus1.setKeyName("key " + i);
			bus1.setGosNum("H" + i + "TRZ");
			busService.persist(bus1);
			index++;
		}

		List<Bus> list = busService.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

}
