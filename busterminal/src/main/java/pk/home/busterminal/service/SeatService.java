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

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.SeatDAO;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.domain.Seat_;

/**
 * Service class for entity class: Seat Seat - посадочное место
 */
@Service
@Transactional
public class SeatService extends ABaseService<Seat> {

	@Autowired
	private BusService busService;

	@Autowired
	private SchemaService schemaService;

	@Autowired
	private SeatDAO seatDAO;

	@Override
	public ABaseDAO<Seat> getAbstractBasicDAO() {
		return seatDAO;
	}

	public List<Seat> getAllEntities(int firstResult, int maxResults,
			SingularAttribute<Seat, ?> orderByAttribute,
			SortOrderType sortOrder, Bus bus, Schema schema) throws Exception {

		if (orderByAttribute == Seat_.id && sortOrder == SortOrderType.ASC) {
			return seatDAO.executeQueryByName("Seat.findByBusAndSchema.id.asc",
					firstResult, maxResults, bus, schema);
		} else if (orderByAttribute == Seat_.id
				&& sortOrder == SortOrderType.DESC) {
			return seatDAO.executeQueryByName(
					"Seat.findByBusAndSchema.id.desc", firstResult, maxResults,
					bus, schema);
		} else if (orderByAttribute == Seat_.num
				&& sortOrder == SortOrderType.ASC) {
			return seatDAO.executeQueryByName(
					"Seat.findByBusAndSchema.num.asc", firstResult, maxResults,
					bus, schema);
		} else if (orderByAttribute == Seat_.num
				&& sortOrder == SortOrderType.DESC) {
			return seatDAO.executeQueryByName(
					"Seat.findByBusAndSchema.num.desc", firstResult,
					maxResults, bus, schema);
		} else {
			return super.getAllEntities(firstResult, maxResults,
					orderByAttribute, sortOrder);
		}
	}

	public long count(Bus bus, Schema schema) throws Exception {
		return (Long) seatDAO.executeQueryByNameSingleResultO(
				"Seat.findByBusAndSchema.count", bus, schema);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Seat persist(Seat o) throws Exception {
		check(o);
		return super.persist(o);
	}

	@Override
	@ExceptionHandler(Exception.class)
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public Seat merge(Seat o) throws Exception {
		check(o);
		return super.merge(o);
	}

	/**
	 * Проверка логической уникальности сохраняемого места
	 * 
	 * @param o
	 * @throws Exception
	 */
	@Transactional
	public void check(Seat o) throws Exception {

		Bus b = busService.find(o.getSchema().getBus().getId());

		for (Schema sh : schemaService.getAllEntities(b)) {
			schemaService.refresh(sh);

			for (Seat st : getAllEntities(sh)) {

				if (!o.equals(st)) {
					if (o.getNum() != null && st.getNum() != null
							&& o.getNum().equals(st.getNum())) {
						throw new Exception(
								"Номера мест в пределах одного автобуса должны бвть уникальными!");
					}

					if (o.getSx() != null && o.getSx() > 0 && o.getSy() != null
							&& o.getSy() > 0

							&& o.getSchema().equals(st.getSchema())

							&& o.getSx().equals(st.getSx())
							&& o.getSy().equals(st.getSy())) {
						throw new Exception("Координаты места в схеме x="
								+ o.getSx() + " и y=" + o.getSy()
								+ " уже заняты местом №" + st.getNum());
					}
				}
			}
		}
	}

	/**
	 * Получить список мест схемы
	 * 
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<Seat> getAllEntities(Schema schema) throws Exception {

		CriteriaBuilder cb = seatDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Seat> cq = cb.createQuery(Seat.class);
		Root<Seat> t = cq.from(Seat.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Seat_.schema), schema));

		return seatDAO.getAllEntities(Seat_.num, SortOrderType.ASC, cb, cq, t);
	}

	/**
	 * Find not marked seats list by scheme
	 * 
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public List<Seat> findNotMarkedSeats(Schema schema) throws Exception {
		return seatDAO.executeQueryByName("Seat.findNoMarkedBySchema", schema);
	}

	/**
	 * Find not marked seats count by scheme
	 * 
	 * @param schema
	 * @return
	 * @throws Exception
	 */
	public int findNotMarkedSeatsCount(Schema schema) throws Exception {
		return (Integer) seatDAO.executeQueryByNameSingleResultO(
				"Seat.findNoMarkedBySchema.count", schema);
	}

	/**
	 * Создать копию
	 * 
	 * @param seat
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Seat createSeatCopy(Seat seat) throws Exception {
		Seat seatCopy = new Seat();

		seatCopy.setDescription(seat.getDescription());
		seatCopy.setNum(seat.getNum());
		seatCopy.setSx(seat.getSx());
		seatCopy.setSy(seat.getSy());
		seatCopy.setSchema(seat.getSchema());
		seatCopy.setSeatType(seat.getSeatType());
		seatCopy.setMasterProcent(seat.getMasterProcent());
		seatCopy.setPrice(seat.getPrice());

		return seatCopy;
	}

	/**
	 * Получить минимальный автоматический номер для декораций
	 * 
	 * @param seat
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public short createDecoratorMinNum(Seat seat) throws Exception {

		short min = (Short) seatDAO.executeQueryByNameSingleResultO(
				"Seat.findNumInSchema.min", seat.getSchema());

		if (min >= 0)
			min = (short) 0;

		return --min;
	}

	/**
	 * Получить максимальный автоматический номер для декораций
	 * 
	 * @param seat
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public short createDecoratorMaxNum(Seat seat) throws Exception {

		short max = (Short) seatDAO.executeQueryByNameSingleResultO(
				"Seat.findNumInSchema.max", seat.getSchema());

		if (max < 0)
			max = (short) 0;

		return ++max;
	}

}
