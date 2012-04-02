package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.RaceDAO;
import pk.home.busterminal.domain.Race;

/**
 * Service class for entity class: Race
 * Race - рейс
 */
@Service
@Transactional
public class RaceService extends ABaseService<Race> {

	@Autowired
	private RaceDAO raceDAO;

	@Override
	public ABaseDAO<Race> getAbstractBasicDAO() {
		return raceDAO;
	}

}
