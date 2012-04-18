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
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.busterminal.testbase.BaseTest;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test service class for entity class: Race Race - рейс
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestRaceService extends BaseTest {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private RaceService service;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(RaceService service) {
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

		createTestEntitys();

		long index = service.count();
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);

			service.persist(race);
			index++;
		}

		List<Race> list = service.getAllEntities();

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

		createTestEntitys();

		long index = service.count();
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			service.persist(race);
			index++;
		}

		List<Race> list = service.getAllEntities(Race_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Race race : list) {
			assertTrue(lastId < race.getId());
			lastId = race.getId();
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

		createTestEntitys();

		// int index = 0;
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			race.setdTime(createUniqueDate());
			service.persist(race);
			// index++;
		}

		List<Race> list = service.getAllEntities(10, 10);

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
		createTestEntitys();

		// long index = service.count();
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			service.persist(race);
			// index++;
		}

		List<Race> list = service.getAllEntities(10, 10, Race_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Race race : list) {
			assertTrue(lastId < race.getId());
			lastId = race.getId();
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
		createTestEntitys();

		long index = service.count();
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			service.persist(race);
			index++;
		}

		// all - FALSE
		List<Race> list = service.getAllEntities(false, 10, 10, Race_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Race race : list) {
			assertTrue(lastId < race.getId());
			lastId = race.getId();
		}

		// all - TRUE
		list = service
				.getAllEntities(true, 10, 10, Race_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Race race : list) {
			assertTrue(lastId < race.getId());
			lastId = race.getId();
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
		createTestEntitys();

		Race race = new Race();
		race.setdTime(createUniqueDate());
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race = service.persist(race);

		long id = race.getId();

		Race race2 = service.find(id);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		createTestEntitys();

		long index = service.count();
		for (int i = 0; i < 100; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			service.persist(race);
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
		createTestEntitys();

		Race race = new Race();
		race.setdTime(createUniqueDate());
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race = service.persist(race);

		long id = race.getId();

		Race race2 = service.find(id);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());
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
		createTestEntitys();

		Race race = new Race();
		race.setdTime(createUniqueDate());
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race = service.persist(race);

		long id = race.getId();

		Race race2 = service.find(id);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());

		race.setdTime(createUniqueDate());
		race2 = service.refresh(race2);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());

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
		createTestEntitys();

		Race race = new Race();
		race.setdTime(createUniqueDate());
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race = service.persist(race);

		long id = race.getId();

		Race race2 = service.find(id);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());

		race.setdTime(createUniqueDate());
		race2 = service.merge(race2);

		race = service.refresh(race);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());
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
		createTestEntitys();

		Race race = new Race();
		race.setdTime(createUniqueDate());
		race.setBus(busWork1);
		race.setBusRoute(busRoute);
		race = service.persist(race);

		long id = race.getId();

		Race race2 = service.find(id);

		assertEquals(race, race2);
		assertTrue(race.getId() == race2.getId());
		assertEquals(race.getdTime().getTime(), race.getdTime().getTime());

		service.remove(race);

		Race race3 = service.find(id);
		assertTrue(race3 == null);

	}

	// -----------------------------------------------------------------------------------------------------------------

	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {
		createTestEntitys();

		long index = service.count();
		for (int i = 200; i < 210; i++) {
			Race race = new Race();
			race.setdTime(createUniqueDate());
			race.setBus(busWork1);
			race.setBusRoute(busRoute);
			service.persist(race);
			index++;
		}

		List<Race> list = service.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}

}
