package pk.home.busterminal.service;

import java.util.HashSet;
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
import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

@Service
@Transactional
public class BusService extends ABaseService<Bus> {

	@Autowired
	private BusDAO busDAO;

	@Autowired
	private SchemaService schemaService;

	@Autowired
	private SeatService seatService;

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

	/**
	 * Удаление - рекурсивное
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void remove(Bus object) throws Exception {
		if (object.getSchemas() != null)
			for (Schema schema : object.getSchemas()) {
				if (schema.getSeats() != null)
					for (Seat seat : schema.getSeats()) {
						seatService.remove(seat);
					}
				schemaService.remove(schema);
			}
		super.remove(object);
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

	/**
	 * Создание копии автобуса
	 * 
	 * @param templite
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Bus createBusCopy(Bus templite) throws Exception {
		templite = findWithLazy(templite.getId()); // !

		Bus busCopy = new Bus();

		// Копируем простые поля
		busCopy.setBssType(templite.getBssType());
		busCopy.setDescription(templite.getDescription());
		busCopy.setGosNum(templite.getGosNum());
		busCopy.setKeyName(templite.getKeyName());
		busCopy.setTemplite(templite.getTemplite());

		busCopy.setSchemes(new HashSet<Schema>());

		// Копируем колекции
		for (Schema schema : templite.getSchemas()) {
			Schema schemaCopy = schemaService.createSchemaCopy(schema);
			schemaCopy.setBus(busCopy);
			busCopy.getSchemas().add(schemaCopy);
		}

		return busCopy;
	}

	/**
	 * Рекурсивное сохранение копии
	 * 
	 * @param copy
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus persistCopy(Bus copy) throws Exception {

		copy = busDAO.persist(copy);

		for (Schema schema : copy.getSchemas()) {
			schema = schemaService.persist(schema);

			for (Seat seat : schema.getSeats()) {
				seat.setSchema(schema);
				seat = seatService.persist(seat);
			}

			schema = schemaService.refresh(schema);
		}

		return copy;
	}

	/**
	 * Создание рабочей копии
	 * 
	 * @param copy
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Bus createWorkCopyFromTemplite(Bus templite) throws Exception {

		if (templite.getBssType() != BssType.TEMPLITE) {
			throw new Exception(
					"Создать рабочую копию можно только из шаблонной");
		}

		Bus work = createBusCopy(templite);

		work.setTemplite(templite);
		work.setBssType(BssType.WORK);

		return persistCopy(work);
	}

}
