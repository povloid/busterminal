package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.SchemaDAO;
import pk.home.busterminal.domain.Schema;

/**
 * Service class for entity class: Schema
 * Schema - схема мест расположения
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
	
	
	
	

}
