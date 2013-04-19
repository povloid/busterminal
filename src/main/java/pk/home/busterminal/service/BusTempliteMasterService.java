package pk.home.busterminal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BusTempliteMasterService {

	@Autowired
	private BusService busService;

	@Autowired
	private SchemaService schemaService;

	@Autowired
	private SeatService seatService;

	public BusService getBusService() {
		return busService;
	}

	// get's and set's
	// -------------------------------------------------------------------------------------------------

	public void setBusService(BusService busService) {
		this.busService = busService;
	}

	public SchemaService getSchemaService() {
		return schemaService;
	}

	public void setSchemaService(SchemaService schemaService) {
		this.schemaService = schemaService;
	}

	public SeatService getSeatService() {
		return seatService;
	}

	public void setSeatService(SeatService seatService) {
		this.seatService = seatService;
	}

}
