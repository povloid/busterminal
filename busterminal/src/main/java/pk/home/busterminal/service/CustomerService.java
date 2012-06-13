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

import pk.home.busterminal.dao.CustomerDAO;
import pk.home.busterminal.domain.Customer;
import pk.home.busterminal.domain.Customer_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Customer Customer - клиент
 */
@Service
@Transactional
public class CustomerService extends ABaseService<Customer> {

	@Autowired
	private CustomerDAO customerDAO;

	@Override
	public ABaseDAO<Customer> getAbstractBasicDAO() {
		return customerDAO;
	}

	/**
	 * Выборка
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param sortOrderType
	 * @param sortField
	 * @param keyName
	 * @param fName
	 * @param nName
	 * @param mName
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<Customer> select(int firstResult, int maxResults,
			SortOrderType sortOrderType, String sortField, Long id,
			String keyName, String fName, String nName, String mName)
			throws Exception {

		CriteriaBuilder cb = customerDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
		Root<Customer> t = cq.from(Customer.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Customer_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Customer_.keyName), keyName + "%"));
		}

		if (fName != null) {
			criteria.add(cb.like(t.get(Customer_.fName), fName + "%"));
		}

		if (nName != null) {
			criteria.add(cb.like(t.get(Customer_.nName), nName + "%"));
		}

		if (mName != null) {
			criteria.add(cb.like(t.get(Customer_.mName), mName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		SingularAttribute<Customer, ?> orderByAttribute = null;
		// Сортировка
		if (sortField != null)
			if (sortField.equals("id")) {
				orderByAttribute = Customer_.id;
			} else if (sortField.equals("keyName")) {
				orderByAttribute = Customer_.keyName;
			} else if (sortField.equals("fName")) {
				orderByAttribute = Customer_.fName;
			} else if (sortField.equals("nName")) {
				orderByAttribute = Customer_.nName;
			} else if (sortField.equals("mName")) {
				orderByAttribute = Customer_.mName;
			}

		return customerDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrderType, cb, cq, t);

	}

	/**
	 * Общее количество
	 * 
	 * @param keyName
	 * @param fName
	 * @param nName
	 * @param mName
	 * @return
	 * @throws Exception
	 */
	public long selectCount(Long id, String keyName, String fName,
			String nName, String mName) throws Exception {

		CriteriaBuilder cb = customerDAO.getEntityManager()
				.getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<Customer> t = cq.from(Customer.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Customer_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Customer_.keyName), keyName + "%"));
		}

		if (fName != null) {
			criteria.add(cb.like(t.get(Customer_.fName), fName + "%"));
		}

		if (nName != null) {
			criteria.add(cb.like(t.get(Customer_.nName), nName + "%"));
		}

		if (mName != null) {
			criteria.add(cb.like(t.get(Customer_.mName), mName + "%"));
		}

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return customerDAO.count(t, cq);

	}

}
