package pk.home.busterminal.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Date;
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
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Order_;
import pk.home.busterminal.testbase.BaseTest;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test DAO class for entity class: Order Order - ордер - операция
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestOrderDAO extends BaseTest {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private OrderDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(OrderDAO dataStore) {
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
	 * Инициализация
	 */
	@Override
	@Transactional
	public void createTestEntitys() throws Exception {
		super.createTestEntitys();
	}

	@Transactional
	public Order createNewOrder() throws Exception {
		Order order = new Order();
		order.setOrderType(OrderType.TICKET_SALE);
		order.setOpTime(new Date());

		order.setRace(race);
		order.setBusRouteStopA(busRouteStop11);
		order.setBusRouteStopB(busRouteStop16);

		order.setSeat(busWork1.getSchemas().iterator().next().getSeats()
				.iterator().next());

		order.setUserAccount(userAccount);
		order.setCustomer(customer1);

		order.setActualPrice(new BigDecimal(1000));

		order = dataStore.persist(order);
		return order;
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

		long oldcout = dataStore.count();

		createNewOrder();

		List<Order> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == oldcout + 1);

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

		long oldcout = dataStore.count();

		createNewOrder();

		List<Order> list = dataStore.getAllEntities(Order_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == oldcout + 1);
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

		createNewOrder();

		List<Order> list = dataStore.getAllEntities(0, 1);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 1);
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

		createNewOrder();

		List<Order> list = dataStore.getAllEntities(0, 1, Order_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == 1);
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

		createNewOrder();

		List<Order> list = dataStore.getAllEntities(true, 0, 1, Order_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);

		list = dataStore.getAllEntities(false, 0, 1, Order_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);

		assertTrue(list.size() == 1);

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

		Order order1 = createNewOrder();

		long id = order1.getId();

		Order order2 = dataStore.find(id);

		assertEquals(order1, order2);
		assertTrue(order1.getId() == order2.getId());
		assertEquals(order1.getOpTime().getTime(), order1.getOpTime().getTime());

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

		createTestEntitys();

		createNewOrder();

		long count = dataStore.count();

		assertTrue(count == index + 1);

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

		Order order1 = createNewOrder();

		long id = order1.getId();

		Order order2 = dataStore.find(id);

		assertEquals(order1, order2);
		assertTrue(order1.getId() == order2.getId());
		assertEquals(order1.getOpTime().getTime(), order1.getOpTime().getTime());
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

		Order order = createNewOrder();

		long id = order.getId();

		Order order2 = dataStore.find(id);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOrderType(), order2.getOrderType());

		order2.setOrderType(OrderType.TICKET_RETURN);
		order2 = dataStore.refresh(order2);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOrderType(), order2.getOrderType());
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

		Order order = createNewOrder();

		long id = order.getId();

		Order order2 = dataStore.find(id);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOrderType(), order2.getOrderType());

		order2.setOrderType(OrderType.TICKET_RETURN);
		order2 = dataStore.merge(order2);

		order = dataStore.refresh(order);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOrderType(), order2.getOrderType());
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

		Order order = createNewOrder();

		long id = order.getId();

		Order order2 = dataStore.find(id);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOpTime().getTime(), order.getOpTime().getTime());

		dataStore.remove(order);

		Order order3 = dataStore.find(id);
		assertTrue(order3 == null);

	}

}
