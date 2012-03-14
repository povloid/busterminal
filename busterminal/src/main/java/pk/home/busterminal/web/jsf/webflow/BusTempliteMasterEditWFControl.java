package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.commons.lang.NotImplementedException;

import pk.home.busterminal.domain.Bus;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.libs.combine.web.jsf.flow.AWFControl;

public class BusTempliteMasterEditWFControl extends AWFControl<Bus, Long>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189205660540524184L;

	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	@Override
	public Bus findEdited(Long id) throws Exception {
		return service().getBusService().findWithLazy(id);
	}

	@Override
	public Bus newEdited() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	protected void confirmAddImpl() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	protected void confirmEditImpl() throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	protected void confirmDelImpl() throws Exception {
		throw new NotImplementedException();
	}

	@Override
	protected void init0() throws Exception {
		System.out.println("1111111");
		handleSchemaChange();
	}

	private Long selectedSchemeId;
	private Schema selectedScheme;

	/**
	 * Выбор схемы
	 */
	public void handleSchemaChange() {
		if (selectedSchemeId != null)
			for (Schema s : edited.getSchemas()) {
				if (s.getId().equals(selectedSchemeId)) {
					selectedScheme = s;
					break;
				}
			}
	}

	/**
	 * Редактировать текущую схему
	 * 
	 * @return
	 */
	public String editSelectedSchema() {
		return "editSelectedSchema";
	}

	/**
	 * Формирование коллекций для отрисовки схемы
	 * 
	 * @return
	 */
	public List<List<Cell>> getCells() {

		List<List<Cell>> ylist = new ArrayList<List<Cell>>();

		try {
			if (selectedScheme != null) {

				selectedScheme = service().getSchemaService().findAllLazy(
						selectedScheme.getId());

				Cell pCell = null;
				for (short y = 1; y < selectedScheme.getySize() + 1; ++y) {
					List<Cell> xList = new ArrayList<Cell>();
					for (short x = 1; x < selectedScheme.getxSize() + 1; ++x) {

						Seat seat = null;
						for (Seat s : selectedScheme.getSeats()) {
							if (s.getSx() != null && s.getSy() != null
									&& s.getSx() == x && s.getSy() == y) {
								seat = s;
								break;
							}
						}

						Cell cell = new Cell(pCell, seat, x, y);
						xList.add(cell);
						// ----------
						pCell = cell;
					}
					// --------------
					ylist.add(xList);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ylist;
	}

	// CELL OPERATIONS ---------------------------------------------------------------------------------
	
	
	private Cell selectedCell;
	
	/**
	 * Добавить в ячейку
	 * @return
	 */
	public String addToCell() {
		return "addToCell";
	}
	
	
	// get's and set's
	// -------------------------------------------------------------------------------------------------
	public Long getSelectedSchemeId() {
		return selectedSchemeId;
	}

	public void setSelectedSchemeId(Long selectedSchemeId) {
		this.selectedSchemeId = selectedSchemeId;
	}

	public Schema getSelectedScheme() {
		return selectedScheme;
	}

	public void setSelectedScheme(Schema selectedScheme) {
		this.selectedScheme = selectedScheme;
	}

	public Cell getSelectedCell() {
		return selectedCell;
	}

	public void setSelectedCell(Cell selectedCell) {
		this.selectedCell = selectedCell;
	}

	
	
}
