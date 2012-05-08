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
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Items_;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.service.OrderService;
import pk.home.busterminal.testbase.BaseTest;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test DAO class for entity class: Items Items - запись ордера
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class,
		TransactionalTestExecutionListener.class })
@Transactional
@ContextConfiguration(locations = { "file:./src/main/resources/applicationContext.xml" })
public class TestItemsDAO extends BaseTest {

	@Autowired
	private OrderService orderService;

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private ItemsDAO dataStore;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(ItemsDAO dataStore) {
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

	@Transactional
	public Order createNewOrder() throws Exception {

		createTestEntitys();

		Order order = new Order();
		order.setOrderType(OrderType.TICKET_SALE);
		order.setOpTime(new Date());

		order.setRace(race);
		order.setBusRouteStopA(busRouteStop11);
		order.setBusRouteStopB(busRouteStop15);

		order.setSeat(seat1);

		order.setUserAccount(userAccount);
		order.setCustomer(customer1);

		order.setActualPrice(new BigDecimal(1000));

		order = orderService.persist(order);

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

		long count = dataStore.count();

		Order order = createNewOrder();

		List<Items> list = dataStore.getAllEntities();

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == count + order.getItems().size());
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

		long count = dataStore.count();

		Order order = createNewOrder();

		List<Items> list = dataStore.getAllEntities(Items_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == count + order.getItems().size());
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

		dataStore.count();

		createNewOrder();

		List<Items> list = dataStore.getAllEntities(1, 1);

		assertTrue(list != null);
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
		dataStore.count();

		createNewOrder();

		List<Items> list = dataStore.getAllEntities(1, 1, Items_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
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
		long count = dataStore.count();

		Order order = createNewOrder();

		List<Items> list = dataStore.getAllEntities(true, 1, 1, Items_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);
		assertTrue(list.size() == count + order.getItems().size());

		list = dataStore.getAllEntities(false, 1, 1, Items_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
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

		dataStore.count();

		Order order = createNewOrder();

		Items items = order.getItems().get(1);

		long id = items.getId();

		Items items2 = dataStore.find(id);

		assertEquals(items, items2);
		assertTrue(items.getId() == items2.getId());

	}

	/**
	 * Test method for {@link pk.home.libs.combine.dao.ABaseDAO#count()}.
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void testCount() throws Exception {
		long count1 = dataStore.count();

		Order order = createNewOrder();

		long count2 = dataStore.count();

		assertTrue(count2 > 0);
		assertTrue(count2 > count1);
		assertTrue(count2 == count1 + order.getItems().size());
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
		Order order = createNewOrder();

		Items items1 = order.getItems().get(1);
		Items items2 = dataStore.find(order.getItems().get(1).getId());

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());
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
		Order order = createNewOrder();

		Items items1 = order.getItems().get(1);
		Items items2 = dataStore.find(order.getItems().get(1).getId());

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());
		assertEquals(items1.getBrst1().getId(), items1.getBrst1().getId());

		items2.setBrst1(busRouteStop11);
		items2 = dataStore.refresh(items2);

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());
		assertEquals(items1.getBrst1().getId(), items1.getBrst1().getId());

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
		Order order = createNewOrder();

		Items items1 = order.getItems().get(1);
		Items items2 = dataStore.find(order.getItems().get(1).getId());

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());
		assertEquals(items1.getBrst1().getId(), items1.getBrst1().getId());

		items2.setBrst1(busRouteStop11);
		items2 = dataStore.merge(items2);

		items1 = dataStore.find(order.getItems().get(1).getId());

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());
		assertTrue(items1.getBrst1().getId() == items1.getBrst1().getId());
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
		long count = dataStore.count();

		Order order = createNewOrder();

		long count1 = dataStore.count();

		assertTrue(count1 > count);

		Items items1 = order.getItems().get(1);
		Items items2 = dataStore.find(order.getItems().get(1).getId());

		assertEquals(items1, items2);
		assertTrue(items1.getId() == items2.getId());

		dataStore.remove(items1);

		items2 = dataStore.find(order.getItems().get(1).getId());

		assertTrue(items2 == null);

	}

}
