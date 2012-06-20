package pk.home.busterminal.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.BusRouteDAO;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRouteStop;
import pk.home.busterminal.domain.BusRoute_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: BusRoute BusRoute - Маршрут
 */
@Service
@Transactional
public class BusRouteService extends ABaseService<BusRoute> {

	@Autowired
	private BusRouteDAO busRouteDAO;

	@Override
	public ABaseDAO<BusRoute> getAbstractBasicDAO() {
		return busRouteDAO;
	}

	/**
	 * Загрузка вместе с коллесциями
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public BusRoute findWithLazy(Object key) throws Exception {
		BusRoute busRoute = super.find(key);

		if (busRoute.getBusRouteStops() != null) {
			busRoute.getBusRouteStops().size();
		}

		return busRoute;
	}

	/**
	 * Найти и обновить
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public BusRoute findAndRefresh(Object key) throws Exception {
		BusRoute busRoute = super.find(key);
		busRoute = super.refresh(busRoute);
		if (busRoute.getBusRouteStops() != null) {
			busRoute.getBusRouteStops().size();
		}

		return busRoute;

	}

	/**
	 * Содержит ли данную остановку
	 * 
	 * @param busRoute
	 * @param busRouteStop
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public boolean isContain(BusRoute busRoute, BusRouteStop busRouteStop)
			throws Exception {
		busRoute = findWithLazy(busRoute.getId());

		for (BusRouteStop brs : busRoute.getBusRouteStops()) {
			if (brs != null && brs.equals(busRouteStop)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Получить путь - список остановок от начальной до конечной
	 * 
	 * @param busRoute
	 * @param brstStart
	 * @param brstFinish
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<BusRouteStop> getPath(BusRoute busRoute,
			BusRouteStop brstStart, BusRouteStop brstFinish) throws Exception {

		List<BusRouteStop> path = new ArrayList<BusRouteStop>();

		busRoute = findWithLazy(busRoute.getId());

		path.add(brstStart);

		boolean add = false;
		for (BusRouteStop brs : busRoute.getBusRouteStops()) {

			if (brs.equals(brstStart)) {
				add = true;
				continue;
			}

			if (brs.equals(brstFinish)) {
				add = false;
				break;
			}

			if (add) {
				path.add(brs);
			}

		}

		path.add(brstFinish);

		return path;
	}

	/**
	 * Выборка
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param sortOrderType
	 * @param sortField
	 * @param id
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<BusRoute> select(int firstResult, int maxResults,
			SortOrderType sortOrderType, String sortField, Long id,
			String keyName) throws Exception {

		CriteriaBuilder cb = busRouteDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<BusRoute> cq = cb.createQuery(BusRoute.class);
		Root<BusRoute> t = cq.from(BusRoute.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(BusRoute_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(BusRoute_.keyName), keyName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		SingularAttribute<BusRoute, ?> orderByAttribute = null;
		// Сортировка
		if (sortField != null)
			if (sortField.equals("id")) {
				orderByAttribute = BusRoute_.id;
			} else if (sortField.equals("keyName")) {
				orderByAttribute = BusRoute_.keyName;
			}

		return busRouteDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrderType, cb, cq, t);

	}

	/**
	 * Выборка сколько всего строк
	 * 
	 * @param id
	 * @param keyName
	 * @return
	 * @throws Exception
	 */
	public long selectCount(Long id, String keyName) throws Exception {

		CriteriaBuilder cb = busRouteDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<BusRoute> t = cq.from(BusRoute.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(BusRoute_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(BusRoute_.keyName), keyName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return busRouteDAO.count(t, cq);

	}

}
