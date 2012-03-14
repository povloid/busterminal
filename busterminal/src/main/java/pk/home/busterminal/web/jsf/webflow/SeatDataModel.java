package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.FacesException;
import javax.faces.model.DataModel;
import javax.faces.model.DataModelEvent;
import javax.faces.model.DataModelListener;

import org.primefaces.model.SelectableDataModel;

import pk.home.busterminal.domain.Seat;

public class SeatDataModel extends DataModel<Seat> implements
		SelectableDataModel<Seat>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8990908095419549776L;

	public SeatDataModel(List<Seat> list) {
		this.list = new ArrayList<Seat>(list);
	}

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

	// ------------------------------------------------------ Instance Variables

	// The current row index (zero relative)
	private int index = -1;

	// The list we are wrapping
	private List<Seat> list;

	// -------------------------------------------------------------- Properties

	/**
	 * <p>
	 * Return <code>true</code> if there is <code>wrappedData</code> available,
	 * and the current value of <code>rowIndex</code> is greater than or equal
	 * to zero, and less than the size of the list. Otherwise, return
	 * <code>false</code>.
	 * </p>
	 * 
	 * @throws FacesException
	 *             if an error occurs getting the row availability
	 */
	public boolean isRowAvailable() {

		if (list == null) {
			return (false);
		} else if ((index >= 0) && (index < list.size())) {
			return (true);
		} else {
			return (false);
		}

	}

	/**
	 * <p>
	 * If there is <code>wrappedData</code> available, return the length of the
	 * list. If no <code>wrappedData</code> is available, return -1.
	 * </p>
	 * 
	 * @throws FacesException
	 *             if an error occurs getting the row count
	 */
	public int getRowCount() {

		if (list == null) {
			return (-1);
		}
		return (list.size());

	}

	/**
	 * <p>
	 * If row data is available, return the array element at the index specified
	 * by <code>rowIndex</code>. If no wrapped data is available, return
	 * <code>null</code>.
	 * </p>
	 * 
	 * @throws FacesException
	 *             if an error occurs getting the row data
	 * @throws IllegalArgumentException
	 *             if now row data is available at the currently specified row
	 *             index
	 */
	public Seat getRowData() {

		if (list == null) {
			return (null);
		} else if (!isRowAvailable()) {
			try {
				throw new Exception("No Row Available !!!");
			} catch (Exception e) {
				e.printStackTrace();
				return (null);
			}
		} else {
			return ((Seat) list.get(index));
		}

	}

	/**
	 * @throws FacesException
	 *             {@inheritDoc}
	 */
	public int getRowIndex() {

		return (index);

	}

	/**
	 * @throws FacesException
	 *             {@inheritDoc}
	 * @throws IllegalArgumentException
	 *             {@inheritDoc}
	 */
	public void setRowIndex(int rowIndex) {

		if (rowIndex < -1) {
			throw new IllegalArgumentException();
		}
		int old = index;
		index = rowIndex;
		if (list == null) {
			return;
		}
		DataModelListener[] listeners = getDataModelListeners();
		if ((old != index) && (listeners != null)) {
			Object rowData = null;
			if (isRowAvailable()) {
				rowData = getRowData();
			}
			DataModelEvent event = new DataModelEvent(this, index, rowData);
			int n = listeners.length;
			for (int i = 0; i < n; i++) {
				if (null != listeners[i]) {
					listeners[i].rowSelected(event);
				}
			}
		}

	}

	public Object getWrappedData() {

		return (this.list);

	}

	/**
	 * @throws ClassCastException
	 *             if <code>data</code> is non-<code>null</code> and is not a
	 *             <code>List</code>
	 */
	@SuppressWarnings("unchecked")
	public void setWrappedData(Object data) {

		if (data == null) {
			list = null;
			setRowIndex(-1);
		} else {
			list =  (List<Seat>) data;
			index = -1;
			setRowIndex(0);
		}

	}

}