package pk.home.busterminal.service;

import java.util.Date;
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

import pk.home.busterminal.dao.RaceDAO;
import pk.home.busterminal.domain.BssType;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.BusRoute;
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
		// ....

		Bus busWorkCopy = busService.createWorkCopyFromTemplite(busTemplite);

		race.setBus(busWorkCopy);

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
		o = super.merge(o);
		o.getBus().setRace(o);
		busService.merge(o.getBus()); // Так как он уже существует
		return o;
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
	public List<Race> selectRaces(BusRoute busRoute, boolean allDates,
			Date date, int firstResult, int maxResults,
			SingularAttribute<Race, ?> orderByAttribute, SortOrderType sortOrder)
			throws Exception {

		CriteriaBuilder cb = raceDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Race> cq = cb.createQuery(Race.class);
		Root<Race> t = cq.from(Race.class);

		// parent param ---------------------------------------
		if (!allDates) {
			cq.where(cb.between(t.get(Race_.dTime), date,
					new Date(date.getTime() + 1000 * 60 * 60 * 24)));
		}

		return raceDAO.getAllEntities(false, firstResult, maxResults,
				orderByAttribute, sortOrder, cb, cq, t);
	}

}
