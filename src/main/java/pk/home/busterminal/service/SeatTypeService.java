package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.SeatTypeDAO;
import pk.home.busterminal.domain.SeatType;

/**
 * Service class for entity class: SeatType
 * SeatType - тип места
 */
@Service
@Transactional
public class SeatTypeService extends ABaseService<SeatType> {

	@Autowired
	private SeatTypeDAO seatTypeDAO;

	@Override
	public ABaseDAO<SeatType> getAbstractBasicDAO() {
		return seatTypeDAO;
	}

}
