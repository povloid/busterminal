package pk.home.busterminal.service;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.busterminal.dao.SchemaDAO;
import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Schema_;
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

	public List<Schema> getAllEntities(Bus bus) throws Exception {

		CriteriaBuilder cb = schemaDAO.getEntityManager().getCriteriaBuilder();

		CriteriaQuery<Schema> cq = cb.createQuery(Schema.class);
		Root<Schema> t = cq.from(Schema.class);

		// parent param ---------------------------------------
		cq.where(cb.equal(t.get(Schema_.bus), bus));

		return schemaDAO.getAllEntities(Schema_.keyName, SortOrderType.ASC, cb,
				cq, t);
	}

	public long count(Bus bus) throws Exception {

		CriteriaBuilder cb = schemaDAO.getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Object> cq = schemaDAO.getEntityManager()
				.getCriteriaBuilder().createQuery();
		Root<Schema> rt = cq.from(Schema.class);

		cq.where(cb.equal(rt.get(Schema_.bus), bus));

		cq.select(schemaDAO.getEntityManager().getCriteriaBuilder().count(rt));

		return (Long) schemaDAO.getSinleResult(cq);
	}

}
