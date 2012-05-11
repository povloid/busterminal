package pk.home.busterminal.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.BusDAO;
import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Bus_;
import pk.home.busterminal.domain.Schema;
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

	// CRUD
	// ------------------------------------------------------------------------------------------------------------

	/*
	 * (non-Javadoc)
	 * 
	 * @see pk.home.libs.combine.service.ABaseService#persist(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus persist(Bus o) throws Exception {
		check(o, DML.PERSIST);
		return super.persist(o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pk.home.libs.combine.service.ABaseService#merge(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus merge(Bus o) throws Exception {
		check(o, DML.MERGE);
		return super.merge(o);
	}

	private enum DML {
		PERSIST, MERGE
	}

	/**
	 * Проверка логической целостности элемента
	 * 
	 * @param o
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void check(Bus o, DML dml) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();

		// Проверка уникальности шаблонов по полям keyName и gosNum
		if (o.getBssType() == BssType.TEMPLITE) {
			CriteriaQuery<Object> cq = busDAO.getEntityManager()
					.getCriteriaBuilder().createQuery();
			Root<Bus> rt = cq.from(Bus.class);

			if (dml == DML.MERGE) {
				cq.where(cb.and(cb.notEqual(rt.get(Bus_.id), o.getId()), cb
						.equal(rt.get(Bus_.bssType), o.getBssType()), cb.or(
						cb.equal(rt.get(Bus_.keyName), o.getKeyName()),
						cb.equal(rt.get(Bus_.gosNum), o.getGosNum()))));
			} else if (dml == DML.PERSIST) {
				cq.where(cb.and(cb.equal(rt.get(Bus_.bssType), o.getBssType()),
						cb.or(cb.equal(rt.get(Bus_.keyName), o.getKeyName()),
								cb.equal(rt.get(Bus_.gosNum), o.getGosNum()))));
			}

			cq.select(busDAO.getEntityManager().getCriteriaBuilder().count(rt));

			long count = (Long) busDAO.getSinleResult(cq);
			if (count > 0)
				throw new Exception(
						"У шаблонов поля keyName и gosNum должно быть уникальным");
		}

	}

	// SELECT
	// ----------------------------------------------------------------------------------------------------------

	/**
	 * Загрузка вместе с коллесциями
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public Bus findWithLazy(Object key) throws Exception {
		Bus bus = super.find(key);

		if (bus.getSchemas() != null) {
			bus.getSchemas().size();
		}

		return bus;
	}

	/**
	 * Получить порцию элементов со смещением и сортировкой
	 * 
	 * @param bssType
	 * @param firstResult
	 * @param maxResults
	 * @param orderByAttribute
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
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

	/**
	 * Получить список всех элементов
	 * 
	 * @param bssType
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Bus> getAllEntities(BssType bssType) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Bus> cq = cb.createQuery(Bus.class);
		Root<Bus> t = cq.from(Bus.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Bus_.bssType), bssType));

		return busDAO
				.getAllEntities(Bus_.keyName, SortOrderType.ASC, cb, cq, t);
	}

	/**
	 * Получить количество записей
	 * 
	 * @param bssType
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public long count(BssType bssType) throws Exception {

		CriteriaBuilder cb = busDAO.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> cq = busDAO.getEntityManager()
				.getCriteriaBuilder().createQuery();
		Root<Bus> rt = cq.from(Bus.class);

		cq.where(cb.equal(rt.get(Bus_.bssType), bssType));

		cq.select(busDAO.getEntityManager().getCriteriaBuilder().count(rt));

		return (Long) busDAO.getSinleResult(cq);
	}

	/**
	 * Создание рабочей копии шаблона
	 * 
	 * @param templite
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus createWorkBusCopy(Bus templite) throws Exception {

		Bus workCopy = new Bus();

		return workCopy;

	}

	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus createBusCopy(Bus templite) throws Exception {
		Bus copy = new Bus();

		// Копируем простые поля
		copy.setBssType(templite.getBssType());
		copy.setDescription(templite.getDescription());
		copy.setGosNum(templite.getGosNum());
		copy.setKeyName(templite.getKeyName());
		copy.setTemplite(templite.getTemplite());

		// Копируем колекции
		for (Schema schema : templite.getSchemas()) {

			
			
			
		}

		return copy;
	}

}
