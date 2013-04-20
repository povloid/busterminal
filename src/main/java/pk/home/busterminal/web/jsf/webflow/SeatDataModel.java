package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.web.jsf.flow.model.WFDataModel;

public class SeatDataModel extends WFDataModel<Seat> implements Serializable {

	public SeatDataModel(List<Seat> list) {
		super(list);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	@Override
	public Seat getRowData(String key) {

		for (Seat s : this) {
			if (s.getId() == Long.parseLong(key))
				return s;
		}

		return null;
	}

	@Override
	public Object getRowKey(Seat seat) {
		return seat.getId();
	}


}