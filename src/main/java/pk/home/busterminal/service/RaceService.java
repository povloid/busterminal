package pk.home.busterminal.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.RaceDAO;
import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.BusRoute;
import pk.home.busterminal.domain.BusRoute_;
import pk.home.busterminal.domain.Items;
import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Race_;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Race Race - рейс 
 * 
 * @author povloid
 *
 */
@Service
@Transactional
public class RaceService extends ABaseService<Race> {

	private static final Logger LOG = Logger.getLogger(ABaseService.class);
	
	@Autowired
	private RaceDAO raceDAO;
	
	@Autowired
	private SeatService seatService;

	@Autowired
	private BusService busService;

	@Autowired
	private ItemsService itemsService;

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#getAbstractBasicDAO()
	 */
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

		if (race.getId() != null)
			checkBusWorkCopy(race, busTemplite);
		// ....

		Bus busWorkCopy = busService.createWorkCopyFromTemplite(busTemplite);

		race.setBus(busWorkCopy);

		if (race.getId() != null)
			check(race);

		return race;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#persist(java.lang.Object)
	 */
	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Race persist(Race o) throws Exception {
		o = super.persist(o);
		o.getBus().setRace(o);
		busService.merge(o.getBus()); // Так как он уже существует
		return o;
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#merge(java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.service.ABaseService#remove(java.lang.Object)
	 */
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

	/**
	 * Один день
	 * 
	 */
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

	//------------------------------------------------------------------------------------
	
	/**
	 * Выборка
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param sortOrderType
	 * @param sortField
	 * @param id
	 * @param keyName
	 * @param enableFilterDTime
	 * @param dTime
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<Race> select(int firstResult, int maxResults,
			SortOrderType sortOrderType, String sortField, Long id,
			String keyName, boolean enableFilterDTime, Date dTime) throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Race> cq = cb.createQuery(Race.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Race_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Race_.busRoute).get(BusRoute_.keyName),
					keyName + "%"));
		}
		
		if (enableFilterDTime && dTime != null)
			criteria.add(cb.between(t.get(Race_.dTime), dTime,
					new Date(dTime.getTime() + 1000 * 60 * 60 * 24 ) ));
			

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		SingularAttribute<Race, ?> orderByAttribute = null;
		// Сортировка
		if (sortField != null)
			if (sortField.equals("id")) {
				orderByAttribute = Race_.id;
			} else if (sortField.equals("busRoute.keyName")) {
				
				orderByAttribute = null;
				
				if (sortOrderType == SortOrderType.ASC)
					cq.orderBy(cb.asc(t.get(Race_.busRoute).get(
							BusRoute_.keyName)));
				else if (sortOrderType == SortOrderType.DESC)
					cq.orderBy(cb.desc(t.get(Race_.busRoute).get(
							BusRoute_.keyName)));
			}

		return raceDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrderType, cb, cq, t);

	}

	/**
	 * Выборка количество строк
	 * 
	 * @param id
	 * @param keyName
	 * @param enableFilterDTime
	 * @param dTime
	 * @return
	 * @throws Exception
	 */
	public long selectCount(Long id, String keyName, boolean enableFilterDTime, Date dTime) throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (id != null) {
			criteria.add(cb.equal(t.get(Race_.id), id));
		}

		if (keyName != null) {
			criteria.add(cb.like(t.get(Race_.busRoute).get(BusRoute_.keyName),
					keyName + "%"));
		}
		
		if (enableFilterDTime && dTime != null)
			criteria.add(cb.between(t.get(Race_.dTime), dTime,
					new Date(dTime.getTime() + 1000 * 60 * 60 * 24 ) ));

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return raceDAO.count(t, cq);

	}
	
	//------------------------------------------------------------------------------------
	
	
	/**
	 * Выборка по различным параметрам
	 * 
	 * @param firstResult
	 * @param maxResults
	 * @param orderByAttribute
	 * @param sortOrderType
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<Race> selectF(int firstResult, int maxResults,
			SingularAttribute<Race, ?> orderByAttribute, SortOrderType sortOrderType, 
			Map<String,Object> filters) throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Race> cq = cb.createQuery(Race.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		// MAP ------------------
		
		//if (id != null) {
		//	criteria.add(cb.equal(t.get(Race_.id), id));
		//}

		//if (keyName != null) {
		//	criteria.add(cb.like(t.get(Race_.busRoute).get(BusRoute_.keyName),
		//			keyName + "%"));
		//}

		if(filters != null){
			if(filters.containsKey("dTime"))
				criteria.add(cb.between(t.get(Race_.dTime)
						, (Date) filters.get("dTime")
						, new Date(((Date) filters.get("dTime")).getTime() + 1000 * 60 * 60 * 24 ) ));
		}
		
		
		// ----------------------
		
		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return raceDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrderType, cb, cq, t);

	}

	/**
	 * Выборка по различным параметрам
	 * 
	 * @param filters
	 * @return
	 * @throws Exception
	 */
	public long selectFCount(Map<String,Object> filters) throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Object> cq = cb.createQuery(Object.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		List<Predicate> criteria = new ArrayList<Predicate>();

		// MAP ------------------
		
		if(filters != null){
			if(filters.containsKey("dTime"))
				criteria.add(cb.between(t.get(Race_.dTime)
						, (Date) filters.get("dTime")
						, new Date(((Date) filters.get("dTime")).getTime() + 1000 * 60 * 60 * 24 ) ));
		}
		
		// ----------------------

		cq.where(cb.and(criteria.toArray(new Predicate[0])));

		return raceDAO.count(t, cq);

	}
	
	
	
	/**
	 * Block work.
	 * 
	 * Выполнение блокирования и разблокирования
	 *
	 * @throws Exception the exception
	 */
	@Scheduled(cron="* 0 * * * *")
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED)
	public void autoBlockUnblockWork () throws Exception {
		LOG.info("RUN BLOCK WORK:");
		
		//int h = 1000 * 60 * 60;
		Date currentTime = new Date();
		//long currentTimeAsLong = currentTime.getTime();
		
		
		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Race> cq = cb.createQuery(Race.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		cq.where(cb.greaterThanOrEqualTo(t.get(Race_.dTime), currentTime));
		//cq.where(cb.equal(t.get(Race_.busRoute), busRoute));
	
		Collection<Race> races = raceDAO.getAllEntities(true, 0, 0,
				Race_.id, SortOrderType.ASC, cb, cq, t);
		
		LOG.info(races.size());
		
		for(Race race: races){
			
			Date ubt = race.getAutoUnblockedBeforeTime();
			Date bt = race.getAutoBlockedBeforeTime();
						
			LOG.info(race.getId() + "[" + race.getdTime() + "] " 
			+  " -UBT-> " + ubt + " -BT-> " + bt); 
			
			if (race.getAutoUnblocked() != null 
					&& race.getAutoUnblocked() 
					&& ubt != null 
					&& currentTime.compareTo(ubt) >= 0) {
				LOG.info(">>> Selected race for unblock: " + race);
				
				if(race.getBlock() != null && race.getBlock()){
					race.setBlock(false);
					persist(race);
				}
				
				for(Schema schema :race.getBus().getSchemas())
					for(Seat seat: schema.getSeats())
						if(seat.getCanAutoUnblocked() != null 
								&& seat.getCanAutoUnblocked()
								&& seat.getBlock() != null
								&& seat.getBlock()){
							
							LOG.info(">>> Selected seat for unblock: " + seat.getNum());
							
							seat.setBlock(false);
							seatService.persist(seat);
						}
			}
			
			if (race.getAutoBlocked() != null 
					&& race.getAutoBlocked()
					&& race.getBlock() != null
					&& !race.getBlock() 
					&& bt != null 
					&& currentTime.compareTo(bt) >= 0) {
				LOG.info(">>> Selected race block: " + race);
				
				race.setBlock(true);
				persist(race);		
			}
			
		}		
	}
	
	
	
	
	

}
