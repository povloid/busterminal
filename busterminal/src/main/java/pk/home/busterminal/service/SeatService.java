package pk.home.busterminal.service;

import java.util.List;

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
	private SeatDAO seatDAO;

	@Override
	public ABaseDAO<Seat> getAbstractBasicDAO() {
		return seatDAO;
	}

	public List<Seat> getAllEntities(int firstResult, int maxResults,
			SingularAttribute<Seat, ?> orderByAttribute,
			SortOrderType sortOrder, Bus bus, Schema schema) throws Exception {

		if (orderByAttribute == Seat_.id && sortOrder == SortOrderType.ASC) {
			return seatDAO.executeQueryByName("Seat.findByBusAndSchema.id.asc", firstResult, maxResults, bus, schema);
		} else if (orderByAttribute == Seat_.id && sortOrder == SortOrderType.DESC) {
			return seatDAO.executeQueryByName("Seat.findByBusAndSchema.id.desc", firstResult, maxResults, bus, schema);
		} else if (orderByAttribute == Seat_.num && sortOrder == SortOrderType.ASC) {
			return seatDAO.executeQueryByName("Seat.findByBusAndSchema.num.asc", firstResult, maxResults, bus, schema);
		} else if (orderByAttribute == Seat_.num && sortOrder == SortOrderType.DESC) {
			return seatDAO.executeQueryByName("Seat.findByBusAndSchema.num.desc", firstResult, maxResults, bus, schema);
		} else {
			return super.getAllEntities(firstResult, maxResults,
					orderByAttribute, sortOrder);
		}
	}

	public long count(Bus bus, Schema schema) throws Exception {
		return (Long) seatDAO.executeQueryByNameSingleResultO("Seat.findByBusAndSchema.count", bus, schema);
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

	@Transactional
	public void check(Seat o) throws Exception {
		Bus b = busService.findWithLazy(o.getSchema().getBus().getId());
		
		for(Schema sh: b.getSchemas()){
			for(Seat st: sh.getSeats()){
				if(!o.equals(st) && o.getNum().equals(st.getNum())){
					throw new Exception("Номера мест в пределах одного автобуса должны бвть уникальными!");
				}
			}
		}
	}
	
	
	
	
	
}
