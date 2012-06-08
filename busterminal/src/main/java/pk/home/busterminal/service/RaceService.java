package pk.home.busterminal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.RaceDAO;
import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Race Race - рейс
 */
@Service
@Transactional
public class RaceService extends ABaseService<Race> {

	@Autowired
	private RaceDAO raceDAO;

	@Autowired
	private BusService busService;

	@Autowired
	private ItemsService itemsService;

	@Override
	public ABaseDAO<Race> getAbstractBasicDAO() {
		return raceDAO;
	}

	/**
	 * Создать копию шаблона и прицепить его к данному рейсу
	 * 
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Race setBusWorkCopy(Race race, Bus busTemplite) throws Exception {

		// Первичные проверки
		if (busTemplite == null || busTemplite.getId() == null
				|| busTemplite.getId() == 0)
			throw new Exception(
					"Bus == null or Bus.id == null or Bus.id == 0 !");
		if (busTemplite.getBssType() != BssType.TEMPLITE)
			throw new Exception("Автобус должен иметь тип TEMPLITE");
		// Проверка в items

		checkBusWorkCopy(race, busTemplite);
		// ....

		Bus busWorkCopy = busService.createWorkCopyFromTemplite(busTemplite);

		race.setBus(busWorkCopy);

		check(race);

		return race;
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Race persist(Race o) throws Exception {
		o = super.persist(o);
		o.getBus().setRace(o);
		busService.merge(o.getBus()); // Так как он уже существует
		return o;
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Race merge(Race o) throws Exception {

		// Проверка в items
		check(o);

		o = super.merge(o);
		o.getBus().setRace(o);
		busService.merge(o.getBus()); // Так как он уже существует
		return o;
	}

	/**
	 * Проверка при установке рабочей копии, вынесена туда чтобы не захламлять
	 * базу пустыми копиями
	 * 
	 * @param race
	 * @param busTemplite
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void checkBusWorkCopy(Race race, Bus busTemplite) throws Exception {
		List<Items> items = itemsService.findAllItemsForRace(race);
		List<Long> bitems = new ArrayList<Long>();

		for (Items i : items) {
			if (!i.getRace().getBus().equals(busTemplite)
					&& !bitems.contains(i.getOrder().getId())) {
				bitems.add(i.getOrder().getId());
			}
		}

		if (bitems.size() > 0) {
			String es = "Сменить схему невозможно, по данному рейсу имеются "
					+ "привязвнные к старому автобусу следующие невозвращенные ордера: ";
			for (Long i : bitems)
				es += " №" + i + " ";

			throw new Exception(es);

		}
	}

	/**
	 * Проверка при обновлении
	 * 
	 * @param race
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public void check(Race race) throws Exception {
		{
			List<Items> items = itemsService.findAllItemsForRace(race);
			List<Long> bitems = new ArrayList<Long>();

			for (Items i : items) {
				if (!i.getRace().getBus().equals(race.getBus())
						&& !bitems.contains(i.getOrder().getId())) {
					bitems.add(i.getOrder().getId());
				}
			}

			if (bitems.size() > 0) {
				String es = "Сменить схему невозможно, по данному рейсу имеются "
						+ "привязвнные к старому автобусу следующие невозвращенные ордера: ";
				for (Long i : bitems)
					es += " №" + i + " ";

				throw new Exception(es);

			}
		}

		{
			List<Items> items = itemsService.findAllItemsForRace(race);
			List<Long> bitems = new ArrayList<Long>();

			for (Items i : items) {
				if (!i.getBrst1().getBusRoute().equals(race.getBusRoute())
						&& !bitems.contains(i.getOrder().getId())) {
					bitems.add(i.getOrder().getId());
				}
			}

			if (bitems.size() > 0) {
				String es = "Сменить маршрут невозможно, по данному рейсу имеются "
						+ "привязвнные к старому маршруту следующие невозвращенные ордера: ";
				for (Long i : bitems)
					es += " №" + i + " ";

				throw new Exception(es);

			}
		}
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void remove(Race race) throws Exception {
		// race = find(race.getId());

		Bus bus = race.getBus();

		/*
		 * List<Race> list = getAllEntities(); for(Race r: list){
		 * if(bus.equals(r.getBus())){ System.out.println("1>>>>>>>>>>>>>>>>" +
		 * r); } }
		 */

		bus.setRace(null);
		bus = busService.merge(bus);

		super.remove(race);

		/*
		 * list = getAllEntities(); for(Race r: list){
		 * if(bus.equals(r.getBus())){ System.out.println("2>>>>>>>>>>>>>>>>" +
		 * r); } }
		 */

		busService.remove(bus);

	}

	public static final int DAY = 1000 * 60 * 60 * 24;

	/**
	 * поиск рейсов
	 * 
	 * @param busRoute
	 * @param allDates
	 * @param date
	 * @param firstResult
	 * @param maxResults
	 * @param orderByAttribute
	 * @param sortOrder
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public List<Race> selectRaces(BusRoute busRoute, boolean allDates,
			Date date, int firstResult, int maxResults,
			SingularAttribute<Race, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Race> cq = cb.createQuery(Race.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Race_.busRoute), busRoute));

		if (allDates) {
			cq.where(cb.equal(t.get(Race_.busRoute), busRoute));
		} else {
			cq.where(
					cb.between(t.get(Race_.dTime), date,
							new Date(date.getTime() + DAY)),
					cb.equal(t.get(Race_.busRoute), busRoute));
		}

		return raceDAO.getAllEntities(false, firstResult, maxResults,
				orderByAttribute, sortOrder, cb, cq, t);
	}

	/**
	 * Получить полное количество
	 * 
	 * @param busRoute
	 * @param allDates
	 * @param date
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public long selectRacesCount(BusRoute busRoute, boolean allDates, Date date)
			throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery();
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		cq.select(cb.count(t));

		if (allDates) {
			cq.where(cb.equal(t.get(Race_.busRoute), busRoute));
		} else {
			cq.where(
					cb.between(t.get(Race_.dTime), date,
							new Date(date.getTime() + DAY)),
					cb.equal(t.get(Race_.busRoute), busRoute));
		}

		Query q = raceDAO.getEntityManager().createQuery(cq);
		return ((Long) q.getSingleResult()).longValue();
	}

}
