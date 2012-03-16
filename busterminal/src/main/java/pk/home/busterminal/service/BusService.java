package pk.home.busterminal.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.BusDAO;
import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

@Service
@Transactional
public class BusService extends ABaseService<Bus> {

	@Autowired
	private BusDAO busDAO;

	@Override
	public ABaseDAO<Bus> getAbstractBasicDAO() {
		return busDAO;
	}

	@Transactional(readOnly = true)
	public Bus findWithLazy(Object key) throws Exception {
		Bus bus = super.find(key);

		if (bus.getSchemas() != null) {
			bus.getSchemas().size();
		}

		return bus;
	}

	public List<Bus> getAllEntities(BssType bssType, int firstResult,
			int maxResults, SingularAttribute<Bus, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Bus> cq = cb.createQuery(Bus.class);
		Root<Bus> t = cq.from(Bus.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Bus_.bssType), bssType));

		return busDAO.getAllEntities(firstResult, maxResults, orderByAttribute,
				sortOrder, cb, cq, t);
	}
	
	
	public List<Bus> getAllEntities(BssType bssType) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Bus> cq = cb.createQuery(Bus.class);
		Root<Bus> t = cq.from(Bus.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Bus_.bssType), bssType));

		return busDAO.getAllEntities(Bus_.keyName,
				SortOrderType.ASC, cb, cq, t);
	}
	

	public long count(BssType bssType) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> cq = busDAO.getEntityManager()
				.getCriteriaBuilder().createQuery();
		Root<Bus> rt = cq.from(Bus.class);

		cq.where(cb.equal(rt.get(Bus_.bssType), bssType));

		cq.select(busDAO.getEntityManager().getCriteriaBuilder().count(rt));

		return (Long) busDAO.getSinleResult(cq);
	}

}
