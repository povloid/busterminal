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
import org.springframework.test.context.ActiveProfiles;
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
 * JUnit test service class for entity class: SeatType SeatType - тип места
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
@ActiveProfiles({"Dev"})
public class TestSeatTypeService {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private SeatTypeService service;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(SeatTypeService service) {
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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
			index++;
		}

		List<SeatType> list = service.getAllEntities();

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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
			index++;
		}

		List<SeatType> list = service.getAllEntities(SeatType_.id,
				SortOrderType.ASC);

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
			service.persist(seatType);
			// index++;
		}

		List<SeatType> list = service.getAllEntities(10, 10);

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
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
			// index++;
		}

		List<SeatType> list = service.getAllEntities(10, 10, SeatType_.id,
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
		long index = service.count();
		for (int i = 0; i < 100; i++) {
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
			index++;
		}

		// all - FALSE
		List<SeatType> list = service.getAllEntities(false, 10, 10,
				SeatType_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (SeatType seatType : list) {
			assertTrue(lastId < seatType.getId());
			lastId = seatType.getId();
		}

		// all - TRUE
		list = service.getAllEntities(true, 10, 10, SeatType_.id,
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
		seatType = service.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = service.find(id);

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
		long index = service.count();
		for (int i = 0; i < 100; i++) {
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
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
		SeatType seatType = new SeatType();
		seatType.setKeyName("key " + 999);
		seatType = service.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = service.find(id);

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
		seatType = service.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = service.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		seatType2.setKeyName("key 65535");
		seatType2 = service.refresh(seatType2);

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
		seatType = service.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = service.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		seatType2.setKeyName("key 65535");
		seatType2 = service.merge(seatType2);

		seatType = service.refresh(seatType);

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
		seatType = service.persist(seatType);

		long id = seatType.getId();

		SeatType seatType2 = service.find(id);

		assertEquals(seatType, seatType2);
		assertTrue(seatType.getId() == seatType2.getId());
		assertEquals(seatType.getKeyName(), seatType2.getKeyName());

		service.remove(seatType);

		SeatType seatType3 = service.find(id);
		assertTrue(seatType3 == null);

	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = service.count();
		for (int i = 200; i < 210; i++) {
			SeatType seatType = new SeatType();
			seatType.setKeyName("key " + i);
			service.persist(seatType);
			index++;
		}

		List<SeatType> list = service.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

}
