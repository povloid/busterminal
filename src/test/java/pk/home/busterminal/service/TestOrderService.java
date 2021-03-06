package pk.home.busterminal.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
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
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Order_;
import pk.home.busterminal.testbase.BaseTest;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;

/**
 * JUnit test service class for entity class: Order Order - ордер - операция
 */
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
public class TestOrderService extends BaseTest {

	/**
	 * The DAO being tested, injected by Spring
	 * 
	 */
	private OrderService service;

	@Autowired
	private ItemsService itemsService;

	/**
	 * Method to allow Spring to inject the DAO that will be tested
	 * 
	 */
	@Autowired
	public void setDataStore(OrderService service) {
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
	 * Инициализация
	 */
	@Override
	@Transactional
	public void createTestEntitys() throws Exception {
		super.createTestEntitys();
	}

	@Transactional
	public Order _createNewOrder() throws Exception {
		Order order = new Order();
		order.setOrderType(OrderType.TICKET_SALE);
		// order.setOpTime(new Date());

		order.setRace(race);
		order.setBusRouteStopA(busRouteStop12);
		order.setBusRouteStopB(busRouteStop15);

		// System.out.println(">>>" +
		// busWork1.getSchemas().iterator().next().getSeats().size());

		order.setSeat(busWork1.getSchemas().iterator().next().getSeats()
				.iterator().next());

		order.setUserAccount(userAccount);
		order.setCustomer(customer1);

		order.setActualPrice(new BigDecimal(1000));

		return order;
	}

	@Transactional
	public Order createNewOrder() throws Exception {

		return service.createTicketSaleOrder(_createNewOrder());
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

		long oldcout = service.count();

		createNewOrder();

		List<Order> list = service.getAllEntities();

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

		long oldcout = service.count();

		createNewOrder();

		List<Order> list = service.getAllEntities(Order_.id, SortOrderType.ASC);

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

		List<Order> list = service.getAllEntities(0, 1);

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

		List<Order> list = service.getAllEntities(0, 1, Order_.id,
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

		List<Order> list = service.getAllEntities(true, 0, 1, Order_.id,
				SortOrderType.ASC);

		assertTrue(list != null);
		assertTrue(list.size() > 0);

		list = service
				.getAllEntities(false, 0, 1, Order_.id, SortOrderType.ASC);

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

		Order order2 = service.find(id);

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

		long index = service.count();

		createTestEntitys();

		createNewOrder();

		long count = service.count();

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

		Order order2 = service.find(id);

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

		Order order2 = service.find(id);

		assertEquals(order, order2);
		assertTrue(order.getId() == order2.getId());
		assertEquals(order.getOrderType(), order2.getOrderType());

		order2.setOrderType(OrderType.TICKET_RETURN);
		order2 = service.refresh(order2);

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
	// @Test
	// @Rollback(true)
	// public void testMerge() throws Exception {
	// createTestEntitys();
	//
	// Order order = createNewOrder();
	//
	// long id = order.getId();
	//
	// Order order2 = service.find(id);
	//
	// assertEquals(order, order2);
	// assertTrue(order.getId() == order2.getId());
	// assertEquals(order.getOrderType(), order2.getOrderType());
	//
	// order2.setOpTime(createUniqueDate());
	// order2 = service.merge(order2);
	//
	// order = service.refresh(order);
	//
	// assertEquals(order, order2);
	// assertTrue(order.getId() == order2.getId());
	// assertEquals(order.getOpTime(), order2.getOpTime());
	// }

	/**
	 * Test method for
	 * {@link pk.home.libs.combine.dao.ABaseDAO#remove(java.lang.Object)}.
	 * 
	 * @throws Exception
	 */
	// @Test
	// @Rollback(true)
	// public void testRemove() throws Exception {
	// createTestEntitys();
	//
	// Order order = createNewOrder();
	//
	// long id = order.getId();
	//
	// Order order2 = service.find(id);
	//
	// assertEquals(order, order2);
	// assertTrue(order.getId() == order2.getId());
	// assertEquals(order.getOpTime().getTime(), order.getOpTime().getTime());
	//
	// service.remove(order);
	//
	// Order order3 = service.find(id);
	// assertTrue(order3 == null);
	//
	// }

	/**
	 * Тест ограничений
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void consctraintTest() throws Exception {
		createTestEntitys();

		Order order = createNewOrder();

		try {

			order.setOpTime(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания времени операции", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setOpTime(createUniqueDate());

		try {

			order.setOrderType(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания типа операции", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setOrderType(OrderType.TICKET_SALE);

		try {

			order.setOrderType(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания типа операции", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setOrderType(OrderType.TICKET_SALE);

		try {

			order.setCustomer(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без клинта", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setCustomer(customer1);

		try {

			order.setUserAccount(null);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка без указания пользователя, проводящего операцию",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setUserAccount(userAccount);

		try {

			order.setOrderType(OrderType.TICKET_RETURN);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка возвратного ордера без указания родительского",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setOrderType(OrderType.TICKET_SALE);

		try {

			order.setRace(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания рейса", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setRace(race);

		try {

			order.getRace().getBus().setBssType(BssType.TEMPLITE);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка автобуса продаваемого рейса неимеющего тип TEMPLITE",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.getRace().getBus().setBssType(BssType.WORK);

		try {

			order.setSeat(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания продаваемого места",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setSeat(seat1);

		try {

			order.setSeat(seat8);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка указанного автобуса в схеме не совпадающего с указаным автобусом в рейсе",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setSeat(seat1);

		try {

			order.setBusRouteStopA(null);
			order.setBusRouteStopB(null);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка без указания пункта начала и конца пути",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopA(busRouteStop11);
		order.setBusRouteStopB(busRouteStop12);

		try {

			order.setBusRouteStopA(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания пункта начала пути",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopA(busRouteStop11);

		try {

			order.setBusRouteStopB(null);
			order = service.merge(order);

			assertTrue("Допущена вставка без указания пункта конца пути", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopB(busRouteStop12);

		try {

			order.setBusRouteStopB(busRouteStop11);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка одинаковых пунктов конца и начала пути",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopB(busRouteStop12);

		try {

			order.setBusRouteStopA(busRouteStop12);
			order.setBusRouteStopB(busRouteStop11);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка остановки начала пути которая позже остановки конца пути",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopA(busRouteStop11);
		order.setBusRouteStopB(busRouteStop12);

		// Ценовые проверки

		// Вначале тест без кидки
		try {
			order.setActualPrice(new BigDecimal(1001));
			order = service.merge(order);

			assertTrue("Допущена превышение цены при торговле без скидок",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setActualPrice(new BigDecimal(1000));

		try {
			order.setActualPrice(new BigDecimal(999));
			order = service.merge(order);

			assertTrue("Допущена уменьшение цены при торговле без скидок",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setActualPrice(new BigDecimal(1000));

		// теперь тест со скидкой
		order.getSeat().setDiscount(true);
		order.getSeat().setDiscountPotsent(90);

		try {
			order.setActualPrice(new BigDecimal(1001));
			order = service.merge(order);

			assertTrue("Допущена превышение цены при торговле со скидокой",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setActualPrice(new BigDecimal(1000));

		try {
			order.setActualPrice(new BigDecimal(800));
			order = service.merge(order);

			assertTrue("Допущена уменьшение цены при торговле со скидокой",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setActualPrice(new BigDecimal(1000));

		try {
			order.getSeat().setBlock(true);
			order = service.merge(order);

			assertTrue("Допущена продажа заблокированного места", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.getSeat().setBlock(false);

		try {
			order.getRace().setBlock(true);
			order = service.merge(order);

			assertTrue("Допущена продажа заблокированного рейса", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.getRace().setBlock(false);

		// Сервисные проверки
		// --------------------------------------------------------------------------------

		try {

			order.setBusRouteStopA(busRouteStop21);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка точки начала пути которая не содержится в списке становок маршрута размещена",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopA(busRouteStop11);

		try {

			order.setBusRouteStopB(busRouteStop21);
			order = service.merge(order);

			assertTrue(
					"Допущена вставка точки конца пути которая не содержится в списке становок маршрута размещена",
					false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		order.setBusRouteStopB(busRouteStop12);

	}

	/**
	 * Тест ограничений отрезков
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void otrezkiTest() throws Exception {
		createTestEntitys();

		createNewOrder();

		try {

			createNewOrder();

			assertTrue("Допущено 2 клиента на одно место!", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		try {

			Order o = _createNewOrder();
			o.setBusRouteStopA(busRouteStop11);
			o.setBusRouteStopB(busRouteStop13);

			o = service.createTicketSaleOrder(o);

			assertTrue("Допущено 2 клиента на одно место!", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		try {

			Order o = _createNewOrder();
			o.setBusRouteStopA(busRouteStop13);
			o.setBusRouteStopB(busRouteStop14);

			o = service.createTicketSaleOrder(o);

			assertTrue("Допущено 2 клиента на одно место!", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		try {

			Order o = _createNewOrder();
			o.setBusRouteStopA(busRouteStop13);
			o.setBusRouteStopB(busRouteStop16);

			o = service.createTicketSaleOrder(o);

			assertTrue("Допущено 2 клиента на одно место!", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

	}

	/**
	 * Тест ограничений
	 * 
	 * @throws Exception
	 */
	@Test
	@Rollback(true)
	public void returnTest() throws Exception {
		createTestEntitys();

		long icount1 = itemsService.count();

		Order order = createNewOrder();
		long icountt = itemsService.count();

		assertTrue(icountt > icount1);
		assertTrue(icountt == icount1 + order.getItems().size());

		long count1 = service.count();

		Order retOrder = new Order();
		retOrder.setOrderType(OrderType.TICKET_RETURN);
		retOrder.setPreviousOrder(order);
		retOrder.setUserAccount(userAccount);

		retOrder = service.createTicketReturnOrder(retOrder);

		long count2 = service.count();
		long icount2 = itemsService.count();

		assertTrue(count2 > count1);
		assertTrue(count2 - 1 == count1);
		assertTrue(icount2 == icount1);

		try {

			retOrder.setUserAccount(null);
			retOrder = service.createTicketReturnOrder(retOrder);

			assertTrue("Допущена вставка без указания пользователя", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

		retOrder.setUserAccount(userAccount);

		try {

			retOrder = service.createTicketReturnOrder(retOrder);

			assertTrue("Допущена повторная операция возврата билета", false);

		} catch (Exception e) {
			System.out.println(e);
			assertTrue(true);
		}

	}

}
