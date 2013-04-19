package pk.home.busterminal.dao;

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
import pk.home.busterminal.domain.SeatType;
import pk.home.busterminal.domain.SeatType_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test DAO class for entity class: SeatType
 * SeatType - тип места
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestSeatTypeDAO {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private SeatTypeDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(SeatTypeDAO dataStore) {
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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			index++;
		}

		List<SeatType> list = dataStore.getAllEntities();

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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			index++;
		}

		List<SeatType> list = dataStore.getAllEntities(SeatType_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (SeatType seatType : list) {
			assertTrue(lastId < seatType.getId());
			lastId = seatType.getId();
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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			// index++;
		}

		List<SeatType> list = dataStore.getAllEntities(10, 10);

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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			// index++;
		}

		List<SeatType> list = dataStore.getAllEntities(10, 10, SeatType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (SeatType seatType : list) {
			assertTrue(lastId < seatType.getId());
			lastId = seatType.getId();
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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			index++;
		}

		// all - FALSE
		List<SeatType> list = dataStore.getAllEntities(false, 10, 10, SeatType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (SeatType seatType : list) {
			assertTrue(lastId < seatType.getId());
			lastId = seatType.getId();
		}

		// all - TRUE
		list = dataStore.getAllEntities(true, 10, 10, SeatType_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (SeatType seatType : list) {
			assertTrue(lastId < seatType.getId());
			lastId = seatType.getId();
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

		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = dataStore.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = dataStore.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
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
		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = dataStore.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = dataStore.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());
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
		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = dataStore.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = dataStore.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		seatType2.setKeyName("key 65535");
		seatType2 = dataStore.refresh(seatType2);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

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
		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = dataStore.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = dataStore.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		seatType2.setKeyName("key 65535");
		seatType2 = dataStore.merge(seatType2);

		seatType = dataStore.refresh(seatType);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());
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
		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = dataStore.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = dataStore.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		dataStore.remove(seatType);

		SeatType seatType3 = dataStore.find(id);
		assertTrue(seatType3 == null);

	}
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = dataStore.count();
		for (int i = 200; i < 210; i++) {
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			dataStore.persist(seatType);
			index++;
		}

		List<SeatType> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}
	

}
