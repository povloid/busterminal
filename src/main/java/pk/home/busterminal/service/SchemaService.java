package pk.home.busterminal.service;

import java.util.HashSet;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pk.home.busterminal.dao.SchemaDAO;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Schema_;
import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.dao.ABaseDAO.SortOrderType;
import pk.home.libs.combine.service.ABaseService;

/**
 * Service class for entity class: Schema Schema - схема мест расположения
 */
@Service
@Transactional
public class SchemaService extends ABaseService<Schema> {

	@Autowired
	private SchemaDAO schemaDAO;

	@Autowired
	private SeatService seatService;

	@Override
	public ABaseDAO<Schema> getAbstractBasicDAO() {
		return schemaDAO;
	}

	@Transactional(readOnly = true)
	public Schema findAllLazy(Object key) throws Exception {
		Schema s = super.find(key);
		s.getSeats().size();
		return s;
	}

	@Transactional(readOnly = true)
	public List<Schema> getAllEntities(Bus bus, int firstResult,
			int maxResults, SingularAttribute<Schema, ?> orderByAttribute,
			SortOrderType sortOrder) throws Exception {

		CriteriaBuilder cb = schemaDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Schema> cq = cb.createQuery(Schema.class);
		Root<Schema> t = cq.from(Schema.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Schema_.bus), bus));

		return schemaDAO.getAllEntities(firstResult, maxResults,
				orderByAttribute, sortOrder, cb, cq, t);
	}

	@Transactional(readOnly = true)
	public List<Schema> getAllEntities(Bus bus) throws Exception {

		CriteriaBuilder cb = schemaDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Schema> cq = cb.createQuery(Schema.class);
		Root<Schema> t = cq.from(Schema.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Schema_.bus), bus));

		return schemaDAO.getAllEntities(Schema_.keyName, SortOrderType.ASC, cb,
				cq, t);
	}

	@Transactional(readOnly = true)
	public long count(Bus bus) throws Exception {

		CriteriaBuilder cb = schemaDAO.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> cq = schemaDAO.getEntityManager()
				.getCriteriaBuilder().createQuery();
		Root<Schema> rt = cq.from(Schema.class);

		cq.where(cb.equal(rt.get(Schema_.bus), bus));

		cq.select(schemaDAO.getEntityManager().getCriteriaBuilder().count(rt));

		return (Long) schemaDAO.getSinleResult(cq);
	}

	/**
	 * Создать копию схемы
	 * 
	 * @param chhema
	 * @return
	 * @throws Exception
	 */
	@ExceptionHandler(Exception.class)
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public Schema createSchemaCopy(Schema schema) throws Exception {
		Schema schemaCopy = new Schema();

		schemaCopy.setBus(schema.getBus());
		schemaCopy.setKeyName(schema.getKeyName());
		schemaCopy.setDescription(schema.getDescription());
		schemaCopy.setxSize(schema.getxSize());
		schemaCopy.setySize(schema.getySize());

		schemaCopy.setSeats(new HashSet<Seat>());

		for (Seat seat : schema.getSeats()) {
			Seat seatCopy = seatService.createSeatCopy(seat);
			seatCopy.setSchema(schemaCopy);
			schemaCopy.getSeats().add(seatCopy);
			//System.out.println(">>>>>>>>" + seat.getNum() + " ----> " + seatCopy.getNum());
		}

		return schemaCopy;
	}

}
