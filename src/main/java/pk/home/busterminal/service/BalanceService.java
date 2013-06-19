package pk.home.busterminal.service;

import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.BalanceDAO;
import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.Balance_;
import pk.home.busterminal.domain.Division;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Balance Balance - баланс
 */
@Service
@Transactional
public class BalanceService extends ABaseService<Balance> {

	@Autowired
	private BalanceDAO balanceDAO;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
	@Override
	public ABaseDAO<Balance> getAbstractBasicDAO() {
		return balanceDAO;
	}

	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#persist(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Balance persist(Balance o) throws Exception {

		o.setOpTime(new Date()); // Задаем свое время

		o.check();

		return super.persist(o);
	}

	public Balance merge(Balance o) throws Exception {
		throw new NotSupportedException("Операция не поддерживается");
		// return super.merge(o);
	}

	/**
	 * баланс за все время по всему
	 * 
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Number getBalanceForAllTime() throws Exception {
		Number n = (Number) balanceDAO
				.executeQueryByNameSingleResultO("Balance.getAllBalance");

		return n != null ? n : 0;
	}

	/**
	 * Баланс подразделения
	 * 
	 * @param division
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Number getBalance(Division division) throws Exception {
		Number n = (Number) balanceDAO.executeQueryByNameSingleResultO(
				"Balance.getAllBalanceForDivision", division);

		return n != null ? n : 0;
	}

	
	
	/**
	 * Получить все записи
	 * 
	 * @param division
	 * @param firstResult
	 * @param maxResults
	 * @param orderByAttribute
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Balance> getAllEntities(Division division, int firstResult,
			int maxResults, SingularAttribute<Balance, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {

		CriteriaBuilder cb = balanceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Balance> cq = cb.createQuery(Balance.class);
		Root<Balance> t = cq.from(Balance.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Balance_.division), division));

		return balanceDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrder, cb, cq, t);
	}

	/**
	 * Получить общее число записей
	 * 
	 * @param division
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public long count(Division division) throws Exception {

		CriteriaBuilder cb = balanceDAO.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> cq = balanceDAO.getEntityManager()
				.getCriteriaBuilder().createQuery();
		Root<Balance> rt = cq.from(Balance.class);

		cq.where(cb.equal(rt.get(Balance_.division), division));

		cq.select(balanceDAO.getEntityManager().getCriteriaBuilder().count(rt));

		return (Long) balanceDAO.findAdvancedObj(cq);
	}

}
