package pk.home.busterminal.service;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.ItemsDAO;
import pk.home.busterminal.domain.BusRouteStop_;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Items_;
import pk.home.busterminal.domain.Order_;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Seat_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Items Items - запись ордера
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class ItemsService extends ABaseService<Items> {

	@Autowired
	private ItemsDAO itemsDAO;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
	@Override
	public ABaseDAO<Items> getAbstractBasicDAO() {
		return itemsDAO;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#persist(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Items persist(Items o) throws Exception {
		check(o);
		return super.persist(o);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#merge(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Items merge(Items o) throws Exception {
		check(o);
		return super.merge(o);
	}

	/**
	 * Проверка уровня сервиса
	 * 
	 * @param o
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void check(Items o) throws Exception {
		o.check();
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#remove(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void remove(Items object) throws Exception {
		super.remove(object);
	}

	// Запросы
	// *****************************************************************************************************************

	/**
	 * Найти все Записи, связанные с данным рейсом
	 * 
	 * @param race
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Items> findAllItemsForRace(Race race) throws Exception {
		CriteriaBuilder cb = itemsDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Items> cq = cb.createQuery(Items.class);
		Root<Items> t = cq.from(Items.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Items_.race), race));
		cq.orderBy(cb.asc(t.get(Items_.id)));

		return itemsDAO.getAllEntities(cb, cq, t);
	}

	/**
	 * Получить упрощенную матрицу проданныз отрезков по данному рейсу
	 * 
	 * @param race
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Object[]> raceItemsMatrix(Race race) throws Exception {

		CriteriaBuilder cb = itemsDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);
		Root<Items> t = cq.from(Items.class);

		cq.multiselect(t.get(Items_.id), t.get(Items_.order).get(Order_.id), t
				.get(Items_.seat).get(Seat_.id),
				t.get(Items_.brst1).get(BusRouteStop_.id), t.get(Items_.brst2)
						.get(BusRouteStop_.id));
		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Items_.race), race));
		cq.orderBy(cb.asc(t.get(Items_.id)));

		TypedQuery<Object[]> q = itemsDAO.getEntityManager().createQuery(cq);
		return q.getResultList();
	}

}
