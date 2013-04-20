package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.List;

import pk.home.busterminal.domain.Seat;
import pk.home.libs.combine.web.jsf.flow.model.WFDataModel;

/**
 * Модель данных для посадочных мест
 * 
 * @author povloid
 *
 */
public class SeatDataModel extends WFDataModel<Seat> implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Конструктор
	 * 
	 * @param list
	 */
	public SeatDataModel(List<Seat> list) {
		super(list);
	}


	/* (non-Javadoc)
	 * @see org.primefaces.model.SelectableDataModel#getRowData(java.lang.String)
	 */
	@Override
	public Seat getRowData(String key) {

		for (Seat s : this) {
			if (s.getId() == Long.parseLong(key))
				return s;
		}

		return null;
	}

	/* (non-Javadoc)
	 * @see org.primefaces.model.SelectableDataModel#getRowKey(java.lang.Object)
	 */
	@Override
	public Object getRowKey(Seat seat) {
		return seat.getId();
	}
}