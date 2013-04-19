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

import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.domain.Seat_;
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
public class TestSeatService {

	private SchemaService schemaService;

	@Autowired
	private SeatTypeService seatTypeService;

	@Autowired
	public void setSchemaDAO(SchemaService schemaService) {
		this.schemaService = schemaService;
	}

	private BusService busService;

	@Autowired
	public void setBusDAO(BusService busService) {
		this.busService = busService;
	}

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private SeatService seatService;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(SeatService seatService) {
		this.seatService = seatService;
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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);

		System.out.println(">>>" + bus.getSchemas());

		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		long index = seatService.count();
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setSchema(schema);
			seat1.setNum((short) i);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			index++;
		}

		List<Seat> list = seatService.getAllEntities();

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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		long index = seatService.count();
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setNum((short) i);
			seat1.setSchema(schema);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			index++;
		}

		List<Seat> list = seatService.getAllEntities(Seat_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Seat seat : list) {
			assertTrue(lastId < seat.getId());
			lastId = seat.getId();
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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		// int index = 0;
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setNum((short) i);
			seat1.setSchema(schema);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			// index++;
		}

		List<Seat> list = seatService.getAllEntities(10, 10);

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
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		// long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setNum((short) i);
			seat1.setSchema(schema);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			// index++;
		}

		List<Seat> list = seatService.getAllEntities(10, 10, Seat_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Seat seat : list) {
			assertTrue(lastId < seat.getId());
			lastId = seat.getId();
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
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		long index = seatService.count();
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setNum((short) i);
			seat1.setSchema(schema);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			index++;
		}

		// all - FALSE
		List<Seat> list = seatService.getAllEntities(false, 10, 10, Seat_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Seat seat : list) {
			assertTrue(lastId < seat.getId());
			lastId = seat.getId();
		}

		// all - TRUE
		list = seatService.getAllEntities(true, 10, 10, Seat_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Seat seat : list) {
			assertTrue(lastId < seat.getId());
			lastId = seat.getId();
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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);

		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);
		
		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		long index = seatService.count();
		for (int i = 0; i < 100; i++) {
			Seat seat1 = new Seat();
			seat1.setNum((short) i);
			seat1.setSchema(schema);
			seat1.setSeatType(seatType);
			seatService.persist(seat1);
			index++;
		}

		long count = seatService.count();

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
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());
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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

		seat2.setNum((short) 200);
		seat2 = seatService.refresh(seat2);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

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

		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

		seat2.setNum((short) 200);
		seat2 = seatService.merge(seat2);

		seat1 = seatService.refresh(seat1);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());
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
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);
		
		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);

		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

		seatService.remove(seat1);

		Seat seat3 = seatService.find(id);
		assertTrue(seat3 == null);

	}

	// LOGICAL CHECK
	// ---------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void testPersistCheck() throws Exception {
		Bus bus = new Bus();
		bus.setBssType(BssType.TEMPLITE);
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);

		SeatType seatType = new SeatType();
		seatType.setKeyName("Тест - Пассажирское");
		seatType.setSold(true);
		seatType = seatTypeService.persist(seatType);
		
		Schema schema = new Schema();
		schema.setKeyName("key " + 999);
		schema.setBus(bus);
		schema = schemaService.persist(schema);

		Schema schema2 = new Schema();
		schema2.setKeyName("key " + 1000);
		schema2.setBus(bus);
		schema2 = schemaService.persist(schema2);

		Seat seat1 = new Seat();
		seat1.setNum((short) 100);
		seat1.setSchema(schema);
		seat1.setSx((short) 1);
		seat1.setSy((short) 1);
		seat1.setSeatType(seatType);
		seat1 = seatService.persist(seat1);

		long id = seat1.getId();

		Seat seat2 = seatService.find(id);

		assertEquals(seat1, seat2);
		assertTrue(seat1.getId() == seat2.getId());
		assertEquals(seat1.getNum(), seat2.getNum());

		// Уникальность при текущей схеме
		try {
			Seat seat3 = new Seat();
			seat3.setNum((short) 100);
			seat3.setSchema(schema);
			seat3.setSeatType(seatType);
			seat3 = seatService.persist(seat3);

			assertTrue(
					"Допущено нарушение уникальности нумерации мест в пределах автобуса",
					false);

		} catch (Exception e) {
			assertTrue(true);
		}

		// Уникальность при текущей схеме
		try {
			Seat seat3 = new Seat();
			seat3.setNum((short) 100);
			seat3.setSchema(schema2);
			seat3.setSeatType(seatType);
			seat3 = seatService.persist(seat3);

			assertTrue(
					"Допущено нарушение уникальности нумерации мест в пределах автобуса",
					false);

		} catch (Exception e) {
			assertTrue(true);
		}

		// Уникальность координат при текущей схеме
		try {
			Seat seat3 = new Seat();
			seat3.setNum((short) 101); // !
			seat3.setSchema(schema);
			seat3.setSeatType(seatType);
			seat3.setSx((short) 1);
			seat3.setSy((short) 1);

			seat3 = seatService.persist(seat3);

			assertTrue(
					"Допущено нарушение уникальности координат мест в пределах схемы",
					false);

		} catch (Exception e) {
			assertTrue(true);
		}

		// Уникальность координат при текущей схеме
		try {
			Seat seat3 = new Seat();
			seat3.setNum((short) 101); // !
			seat3.setSchema(schema2);
			seat3.setSeatType(seatType);
			seat3.setSx((short) 1);
			seat3.setSy((short) 1);

			seat3 = seatService.persist(seat3);

			assertTrue(true);

		} catch (Exception e) {
			assertTrue(
					"Допускаются одниаковые x y в пределах автобуса, так как они надодятся в разных схемах",
					false);
		}

	}

}