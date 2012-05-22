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
import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.Balance_;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test DAO class for entity class: Balance
 * Balance - баланс
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestBalanceDAO {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private BalanceDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(BalanceDAO dataStore) {
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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			index++;
		}

		List<Balance> list = dataStore.getAllEntities();

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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			index++;
		}

		List<Balance> list = dataStore.getAllEntities(Balance_.id, SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		long lastId = 0;
		for (Balance balance : list) {
			assertTrue(lastId < balance.getId());
			lastId = balance.getId();
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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			// index++;
		}

		List<Balance> list = dataStore.getAllEntities(10, 10);

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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			// index++;
		}

		List<Balance> list = dataStore.getAllEntities(10, 10, Balance_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Balance balance : list) {
			assertTrue(lastId < balance.getId());
			lastId = balance.getId();
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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			index++;
		}

		// all - FALSE
		List<Balance> list = dataStore.getAllEntities(false, 10, 10, Balance_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 10);

		long lastId = 0;
		for (Balance balance : list) {
			assertTrue(lastId < balance.getId());
			lastId = balance.getId();
		}

		// all - TRUE
		list = dataStore.getAllEntities(true, 10, 10, Balance_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);

		lastId = 0;
		for (Balance balance : list) {
			assertTrue(lastId < balance.getId());
			lastId = balance.getId();
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

		Balance balance = new Balance();
		balance.setKeyName("key " + 999);
		balance = dataStore.persist(balance);

		long id = balance.getId();

		Balance balance2 = dataStore.find(id);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());

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
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
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
		Balance balance = new Balance();
		balance.setKeyName("key " + 999);
		balance = dataStore.persist(balance);

		long id = balance.getId();

		Balance balance2 = dataStore.find(id);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());
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
		Balance balance = new Balance();
		balance.setKeyName("key " + 999);
		balance = dataStore.persist(balance);

		long id = balance.getId();

		Balance balance2 = dataStore.find(id);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());

		balance2.setKeyName("key 65535");
		balance2 = dataStore.refresh(balance2);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());

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
		Balance balance = new Balance();
		balance.setKeyName("key " + 999);
		balance = dataStore.persist(balance);

		long id = balance.getId();

		Balance balance2 = dataStore.find(id);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());

		balance2.setKeyName("key 65535");
		balance2 = dataStore.merge(balance2);

		balance = dataStore.refresh(balance);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());
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
		Balance balance = new Balance();
		balance.setKeyName("key " + 999);
		balance = dataStore.persist(balance);

		long id = balance.getId();

		Balance balance2 = dataStore.find(id);

		assertEquals(balance, balance2);
		assertTrue(balance.getId() == balance2.getId());
		assertEquals(balance.getKeyName(), balance2.getKeyName());

		dataStore.remove(balance);

		Balance balance3 = dataStore.find(id);
		assertTrue(balance3 == null);

	}
	
	
	
	// -----------------------------------------------------------------------------------------------------------------
	
	@Test
	@Rollback(true)
	public void insertEntities() throws Exception {

		long index = dataStore.count();
		for (int i = 200; i < 210; i++) {
			Balance balance = new Balance();
			balance.setKeyName("key " + i);
			dataStore.persist(balance);
			index++;
		}

		List<Balance> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == index);
	}
	

}
