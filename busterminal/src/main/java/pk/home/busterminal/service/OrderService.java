package pk.home.busterminal.service;

import java.util.Date;
import java.util.List;

import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusRouteStopDAO;
import pk.home.busterminal.dao.OrderDAO;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Order;
import pk.home.busterminal.domain.OrderType;

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

	// Операции

	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Order createTicketSaleOrder(Order o) throws Exception {
		if (o.getOrderType() != OrderType.TICKET_SALE) {
			throw new Exception("Не TICKET_SALE тип!");
		}

		// Время присваивает текущее сам сервисный уровень !!!
		o.setId(null);
		o.setOpTime(new Date());

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

}
