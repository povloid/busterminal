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

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Schema_;
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
public class TestSchemaService {

	private BusService busService;
	
	
	
	@Autowired
	public void setBusDAO(BusService busService) {
		this.busService = busService;
	}

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private SchemaService schemaService;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(SchemaService schemaService) {
		this.schemaService = schemaService;
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		
		long index = schemaService.count();
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setBus(bus);
			schema1.setKeyName("key " + i);
			schemaService.persist(schema1);
			index++;
		}

		List<Schema> list = schemaService.getAllEntities();

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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		
		long index = schemaService.count();
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setKeyName("key " + i);
			schema1.setBus(bus);
			schemaService.persist(schema1);
			index++;
		}

		List<Schema> list = schemaService.getAllEntities(Schema_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Schema schema : list) {
			assertTrue(lastId < schema.getId());
			lastId = schema.getId();
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		// int index = 0;
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setKeyName("key " + i);
			schema1.setBus(bus);
			schemaService.persist(schema1);
			// index++;
		}

		List<Schema> list = schemaService.getAllEntities(10, 10);

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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		// long index = dataStore.count();
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setKeyName("key " + i);
			schema1.setBus(bus);
			schemaService.persist(schema1);
			// index++;
		}

		List<Schema> list = schemaService.getAllEntities(10, 10, Schema_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Schema schema : list) {
			assertTrue(lastId < schema.getId());
			lastId = schema.getId();
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		
		long index = schemaService.count();
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setKeyName("key " + i);
			schema1.setBus(bus);
			schemaService.persist(schema1);
			index++;
		}

		// all - FALSE
		List<Schema> list = schemaService.getAllEntities(false, 10, 10, Schema_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Schema schema : list) {
			assertTrue(lastId < schema.getId());
			lastId = schema.getId();
		}

		// all - TRUE
		list = schemaService.getAllEntities(true, 10, 10, Schema_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Schema schema : list) {
			assertTrue(lastId < schema.getId());
			lastId = schema.getId();
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		Schema schema1 = new Schema();
		schema1.setKeyName("key " + 999);
		schema1.setBus(bus);
		schema1 = schemaService.persist(schema1);

		long id = schema1.getId();

		Schema schema2 = schemaService.find(id);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());

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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		long index = schemaService.count();
		for (int i = 0; i < 100; i++) {
			Schema schema1 = new Schema();
			schema1.setKeyName("key " + i);
			schema1.setBus(bus);
			schemaService.persist(schema1);
			index++;
		}

		long count = schemaService.count();

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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		
		Schema schema1 = new Schema();
		schema1.setKeyName("key " + 999);
		schema1.setBus(bus);
		schema1 = schemaService.persist(schema1);

		long id = schema1.getId();

		Schema schema2 = schemaService.find(id);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		Schema schema1 = new Schema();
		schema1.setKeyName("key " + 999);
		schema1.setBus(bus);
		schema1 = schemaService.persist(schema1);

		long id = schema1.getId();

		Schema schema2 = schemaService.find(id);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());

		schema2.setKeyName("key 65535");
		schema2 = schemaService.refresh(schema2);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());

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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		Schema schema1 = new Schema();
		schema1.setKeyName("key " + 999);
		schema1.setBus(bus);
		schema1 = schemaService.persist(schema1);

		long id = schema1.getId();

		Schema schema2 = schemaService.find(id);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());

		schema2.setKeyName("key 65535");
		schema2 = schemaService.merge(schema2);

		schema1 = schemaService.refresh(schema1);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());
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
		bus.setKeyName("#Test Bus");
		bus.setGosNum("#Test num");
		bus = busService.persist(bus);
		
		
		Schema schema1 = new Schema();
		schema1.setKeyName("key " + 999);
		schema1.setBus(bus);
		schema1 = schemaService.persist(schema1);

		long id = schema1.getId();

		Schema schema2 = schemaService.find(id);

		assertEquals(schema1, schema2);
		assertTrue(schema1.getId() == schema2.getId());
		assertEquals(schema1.getKeyName(), schema2.getKeyName());

		schemaService.remove(schema1);

		Schema schema3 = schemaService.find(id);
		assertTrue(schema3 == null);

	}

}