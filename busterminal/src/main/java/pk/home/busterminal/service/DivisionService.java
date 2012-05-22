package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.DivisionDAO;
import pk.home.busterminal.domain.Division;

/**
 * Service class for entity class: Division
 * Division - отделение
 */
@Service
@Transactional
public class DivisionService extends ABaseService<Division> {

	@Autowired
	private DivisionDAO divisionDAO;

	@Override
	public ABaseDAO<Division> getAbstractBasicDAO() {
		return divisionDAO;
	}

}
