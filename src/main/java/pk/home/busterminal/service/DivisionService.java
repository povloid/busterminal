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

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.DivisionDAO;
import pk.home.busterminal.domain.Division;
import pk.home.busterminal.domain.Division_;

/**
 * Service class for entity class: Division Division - отделение
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class DivisionService extends ABaseService<Division> {

	@Autowired
	private DivisionDAO divisionDAO;

	@Override
	public ABaseDAO<Division> getAbstractBasicDAO() {
		return divisionDAO;
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
	public List<Division> select(int firstResult, int maxResults,
			SortOrderType sortOrderType, String sortField, Long id,
			String keyName) throws Exception {

		CriteriaBuilder cb = divisionDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<Division> cq = cb.createQuery(Division.class);
		Root<Division> t = cq.from(Division.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Division_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Division_.keyName), keyName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		SingularAttribute<Division, ?> orderByAttribute = null;
		// Сортировка
		if (sortField != null)
			if (sortField.equals("id")) {
				orderByAttribute = Division_.id;
			} else if (sortField.equals("keyName")) {
				orderByAttribute = Division_.keyName;
			}

		return divisionDAO.getAllEntities(firstResult, maxResults,
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

		CriteriaBuilder cb = divisionDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<Division> t = cq.from(Division.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Division_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Division_.keyName), keyName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return divisionDAO.count(t, cq);

	}

}
