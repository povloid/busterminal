package pk.home.busterminal.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BusStopDAO;
import pk.home.busterminal.domain.BusStop;
import pk.home.busterminal.domain.BusStop_;

/**
 * Service class for entity class: BusStop BusStop - Остановка
 */
@Service
@Transactional
public class BusStopService extends ABaseService<BusStop> {

	@Autowired
	private BusStopDAO busStopDAO;

	@Override
	public ABaseDAO<BusStop> getAbstractBasicDAO() {
		return busStopDAO;
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
	public List<BusStop> select(int firstResult, int maxResults,
			SortOrderType sortOrderType, String sortField, Long id,
			String keyName) throws Exception {

		CriteriaBuilder cb = busStopDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<BusStop> cq = cb.createQuery(BusStop.class);
		Root<BusStop> t = cq.from(BusStop.class);

		// parent param ---------------------------------------
		if (id != null) {
			cq.where(cb.equal(t.get(BusStop_.id), id));
		}

		if (keyName != null) {
			cq.where(cb.like(t.get(BusStop_.keyName), keyName + "%"));
		}

		SingularAttribute<BusStop, ?> orderByAttribute = null;
		// Сортировка
		if (sortField != null)
			if (sortField.equals("id")) {
				orderByAttribute = BusStop_.id;
			} else if (sortField.equals("keyName")) {
				orderByAttribute = BusStop_.keyName;
			}

		return busStopDAO.getAllEntities(firstResult, maxResults,
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

		CriteriaBuilder cb = busStopDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<BusStop> t = cq.from(BusStop.class);

		// parent param ---------------------------------------
		if (id != null) {
			cq.where(cb.equal(t.get(BusStop_.id), id));
		}

		if (keyName != null) {
			cq.where(cb.like(t.get(BusStop_.keyName), keyName + "%"));
		}

		return busStopDAO.count(t, cq);

	}

}
