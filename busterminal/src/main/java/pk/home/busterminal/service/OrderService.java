package pk.home.busterminal.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.BusRouteStopDAO;
import pk.home.busterminal.dao.OrderDAO;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusRouteStop_;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Items_;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;
import pk.home.busterminal.domain.Order_;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.busterminal.domain.Seat_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Order Order - ордер - операция
 */
@Service
@Transactional
public class OrderService extends ABaseService<Order> {

	@Autowired
	private OrderDAO orderDAO;

	@Autowired
	private BusRouteService busRouteService;

	@Autowired
	private ItemsService itemsService;

	@Autowired
	private BusRouteStopDAO busRouteStopDAO;

	@Override
	public ABaseDAO<Order> getAbstractBasicDAO() {
		return orderDAO;
	}

	@Override
	public Order persist(Order o) throws Exception {
		throw new NotSupportedException();
	}

	@Override
	public Order merge(Order o) throws Exception {
		throw new NotSupportedException();
	}

	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.SUPPORTS)
	public void check(Order o) throws Exception {
		o.check();

		if (!busRouteService.isContain(o.getRace().getBusRoute(),
				o.getBusRouteStopA())) {
			throw new Exception(
					"Указанное начало пути не содержится в данном маршруте!");
		}

		if (!busRouteService.isContain(o.getRace().getBusRoute(),
				o.getBusRouteStopB())) {
			throw new Exception(
					"Указаннай конец пути не содержится в данном маршруте!");
		}

	}

	@Override
	public void remove(Order object) throws Exception {

		// object = find(object.getId());
		//
		// if (object.getItems().size() > 0) {
		// for (Items item : object.getItems()) {
		// itemsService.remove(item);
		// }
		// }
		//
		// super.remove(object);
		throw new NotSupportedException();
	}

	// Запросы
	// *****************************************************************************************************************

	/**
	 * Найти все ордера, связанные с данным рейсом
	 * 
	 * @param race
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Order> findAllOrdersForRace(Race race) throws Exception {
		CriteriaBuilder cb = orderDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> t = cq.from(Order.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Order_.race), race));

		return orderDAO.getAllEntities(cb, cq, t);
	}

	// Операции
	// *****************************************************************************************************************

	/**
	 * Создать продажный ордер
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Order createTicketSaleOrder(Order o) throws Exception {
		if (o.getOrderType() != OrderType.TICKET_SALE) {
			throw new Exception("Не TICKET_SALE тип!");
		}

		// Время присваивает текущее сам сервисный уровень !!!
		o.setId(null);
		o.setOpTime(new Date());
		o.setOrderType(OrderType.TICKET_SALE);

		check(o);

		List<BusRouteStop> list = busRouteService.getPath(o.getRace()
				.getBusRoute(), o.getBusRouteStopA(), o.getBusRouteStopB());

		// for (BusRouteStop ibrs : list) {
		// System.out.print(">>>>>>>" + ibrs);
		// System.out.print(" - " + ibrs.getOrId());
		//
		// if (ibrs.getpBRStop() != null)
		// System.out.print(" - " + ibrs.getpBRStop().getOrId());
		// else
		// System.out.print(" - " + "null");
		//
		// if (ibrs.getnBRStop() != null)
		// System.out.println(" - " + ibrs.getnBRStop().getOrId());
		// else
		// System.out.println(" - " + "null");
		// }

		o = super.persist(o);

		// Это надо делать только при продаже

		BusRouteStop brsLast = o.getBusRouteStopA();
		for (BusRouteStop brs : list) {
			if (!brs.equals(brsLast)) {
				Items item = new Items();

				item.setOrder(o);
				item.setRace(o.getRace());
				item.setSeat(o.getSeat());
				item.setBrst1(brsLast);
				item.setBrst2(brs);

				item = itemsService.persist(item);
			}

			brsLast = brs;

		}

		BusRouteStop ibrsLast = list.get(0);
		for (BusRouteStop ibrs : list) {
			if (!ibrs.equals(ibrsLast)) {
				ibrs.setpBRStop(ibrsLast);
				ibrsLast.setnBRStop(ibrs);
			}

			ibrsLast = ibrs;
		}

		for (BusRouteStop ibrs : list) {
			System.out.print(">>>>>>>" + ibrs);
			System.out.print(" - " + ibrs.getOrId());

			if (ibrs.getpBRStop() != null)
				System.out.print(" - " + ibrs.getpBRStop().getOrId());
			else
				System.out.print(" - " + "null");

			if (ibrs.getnBRStop() != null)
				System.out.println(" - " + ibrs.getnBRStop().getOrId());
			else
				System.out.println(" - " + "null");
		}

		for (BusRouteStop brs : list) {
			brs = busRouteStopDAO.merge(brs);
		}

		return super.refresh(o);
	}

	/**
	 * Создать возвратный ордер
	 * 
	 * Данная процедура удаляет Items родительского ордера и пишит свой,
	 * подчиненный ордер
	 * 
	 * @param o
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Order createTicketReturnOrder(Order o) throws Exception {

		if (o.getOrderType() != OrderType.TICKET_RETURN) {
			throw new Exception("Не TICKET_RETURN тип!");
		}

		if (o.getPreviousOrder() == null) {
			throw new Exception("Не указан возврантный ордер!");
		}

		Order order = find(o.getPreviousOrder().getId());

		// Время присваивает текущее сам сервисный уровень !!!
		o.setId(null);
		o.setOpTime(new Date());

		o.setOrderType(OrderType.TICKET_RETURN);
		o.setSeat(order.getSeat());
		o.setRace(order.getRace());
		o.setBusRouteStopA(order.getBusRouteStopA());
		o.setBusRouteStopB(order.getBusRouteStopB());
		o.setCustomer(order.getCustomer());
		o.setActualPrice(order.getActualPrice().multiply(new BigDecimal(-1)));

		check(o);

		o = super.persist(o);

		for (Items items : order.getItems()) {
			itemsService.remove(items);
		}

		return o;
	}

	// reports
	// -----------------------------------------------------------------------------------------------------------------

	/**
	 * Поиск ордеров
	 * 
	 * @param race
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Order> findOrdersBySeatNum(Race race) throws Exception {

		CriteriaBuilder cb = orderDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Order> t = cq.from(Order.class);

		Subquery<Long> sq = cq.subquery(Long.class);
		Root<Items> it = sq.from(Items.class);

		sq.select(it.get(Items_.order).get(Order_.id)).distinct(true)
				.where(cb.equal(it.get(Items_.race), race));

		cq.where(cb.equal(t.get(Order_.race), race), cb.equal(
				t.get(Order_.orderType), OrderType.TICKET_SALE),
				cb.in(t.get(Order_.id)).value(sq));
		cq.orderBy(cb.asc(t.get(Order_.seat).get(Seat_.num)));

		return orderDAO.getAllEntities(cb, cq, t);
	}

	public class FindOrdersOrderByBusRouteStopsResult {

		BusRouteStop BusRouteStop;

		private String type;
		private String typeCaption;

		Order gOrder;
		Order pOrder;

		public FindOrdersOrderByBusRouteStopsResult(
				pk.home.busterminal.domain.BusRouteStop busRouteStop,
				String type, String typeCaption) {
			super();
			BusRouteStop = busRouteStop;
			this.type = type;
			this.typeCaption = typeCaption;
		}

		public Order getgOrder() {
			return gOrder;
		}

		public void setgOrder(Order gOrder) {
			this.gOrder = gOrder;
		}

		public Order getpOrder() {
			return pOrder;
		}

		public void setpOrder(Order pOrder) {
			this.pOrder = pOrder;
		}

		public BusRouteStop getBusRouteStop() {
			return BusRouteStop;
		}

		public void setBusRouteStop(BusRouteStop busRouteStop) {
			BusRouteStop = busRouteStop;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getTypeCaption() {
			return typeCaption;
		}

		public void setTypeCaption(String typeCaption) {
			this.typeCaption = typeCaption;
		}

	}

	/**
	 * Поиск ордеров
	 * 
	 * @param race
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<FindOrdersOrderByBusRouteStopsResult> findOrdersOrderByBusRouteStops(
			Race race) throws Exception {

		CriteriaBuilder cb = orderDAO.getEntityManager().getCriteriaBuilder();

		List<BusRouteStop> brsList = null;

		{
			CriteriaQuery<BusRouteStop> cq = cb.createQuery(BusRouteStop.class);
			Root<BusRouteStop> t = cq.from(BusRouteStop.class);
			// Join<BusRouteStop, Order> p = t.join("busRouteStopA",
			// JoinType.LEFT);
			//
			// cq.multiselect(t, p);
			cq.where(cb.equal(t.get(BusRouteStop_.busRoute), race.getBusRoute()));
			cq.orderBy(cb.asc(t.get(BusRouteStop_.orId)));

			TypedQuery<BusRouteStop> q = orderDAO.getEntityManager()
					.createQuery(cq);

			brsList = q.getResultList();
		}

		List<Order> orders = null;

		{
			CriteriaQuery<Order> cq = cb.createQuery(Order.class);
			Root<Order> t = cq.from(Order.class);

			Subquery<Long> sq = cq.subquery(Long.class);
			Root<Items> it = sq.from(Items.class);

			sq.select(it.get(Items_.order).get(Order_.id)).distinct(true)
					.where(cb.equal(it.get(Items_.race), race));

			cq.where(cb.equal(t.get(Order_.race), race),
					cb.equal(t.get(Order_.orderType), OrderType.TICKET_SALE),
					cb.in(t.get(Order_.id)).value(sq));
			cq.orderBy(cb.asc(t.get(Order_.seat).get(Seat_.num)));

			orders = orderDAO.getAllEntities(cb, cq, t);
		}

		List<FindOrdersOrderByBusRouteStopsResult> rlist = new ArrayList<OrderService.FindOrdersOrderByBusRouteStopsResult>();

		for (BusRouteStop brs : brsList) {
			for (Order o : orders) {
				if (o.getBusRouteStopA().equals(brs)) {
					FindOrdersOrderByBusRouteStopsResult foobb = new FindOrdersOrderByBusRouteStopsResult(
							brs, "get", "Посадка");
					foobb.setgOrder(o);
					rlist.add(foobb);
				}
			}

			for (Order o : orders) {
				if (o.getBusRouteStopB().equals(brs)) {
					FindOrdersOrderByBusRouteStopsResult foobb = new FindOrdersOrderByBusRouteStopsResult(
							brs, "get", "Высадка");
					foobb.setpOrder(o);
					rlist.add(foobb);
				}
			}
		}

		return rlist;
	}

}
