package pk.home.busterminal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.OrderDAO;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Order;

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

	@Override
	public ABaseDAO<Order> getAbstractBasicDAO() {
		return orderDAO;
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Order persist(Order o) throws Exception {
		check(o);

		List<BusRouteStop> list = busRouteService.getPath(o.getRace()
				.getBusRoute(), o.getBusRouteStopA(), o.getBusRouteStopB());

		o = super.persist(o);

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

		return super.refresh(o);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Order merge(Order o) throws Exception {
		check(o);
		return super.merge(o);
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

		object = find(object.getId());

		if (object.getItems().size() > 0) {
			for (Items item : object.getItems()) {
				itemsService.remove(item);
			}
		}

		super.remove(object);
	}

}
