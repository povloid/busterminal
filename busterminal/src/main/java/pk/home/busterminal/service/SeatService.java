package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pk.home.libs.combine.dao.ABaseDAO;
import pk.home.libs.combine.service.ABaseService;
import pk.home.busterminal.dao.SeatDAO;
import pk.home.busterminal.domain.Seat;

/**
 * Service class for entity class: Seat
 * Seat - посадочное место
 */
@Service
@Transactional
public class SeatService extends ABaseService<Seat> {

	@Autowired
	private SeatDAO seatDAO;

	@Override
	public ABaseDAO<Seat> getAbstractBasicDAO() {
		return seatDAO;
	}

}
