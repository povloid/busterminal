package pk.home.busterminal.service;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.NotSupportedException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.BalanceDAO;
import pk.home.busterminal.domain.Balance;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.Seat_;

/**
 * Service class for entity class: Balance Balance - баланс
 */
@Service
@Transactional
public class BalanceService extends ABaseService<Balance> {

	@Autowired
	private BalanceDAO balanceDAO;

	@Override
	public ABaseDAO<Balance> getAbstractBasicDAO() {
		return balanceDAO;
	}

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
//	@Transactional(readOnly = true)
//	public BigDecimal getBalanceForAllTime() throws Exception {
//		CriteriaBuilder cb = balanceDAO.getEntityManager().getCriteriaBuilder();
//
//		CriteriaQuery<Balance> cq = cb.createQuery(Balance.class);
//		Root<Balance> t = cq.from(Balance.class);
//
//		
//		// parent param ---------------------------------------
//		// /cq.where(cb.equal(t.get(Seat_.schema), schema));
//
//		// return seatDAO.e(Seat_.num, SortOrderType.ASC, cb, cq, t);
//		
//		
//		
//	}

}
