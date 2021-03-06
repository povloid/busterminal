package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pk.home.busterminal.domain.Race;
import pk.home.busterminal.domain.Schema;
import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusRouteService;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.busterminal.service.ItemsService;
import pk.home.busterminal.service.RaceService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;

/**
 * Sale - продажа билета
 * 
 * @author povloid
 * 
 */
public class OPSaleWFControl extends AWFBasicControl implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Race race;	// Рейс

	// SERVICES
	// -------------------------------------------------------------------------------------------

	/**
	 * Сервис продаваемых/проданных отрезков пути
	 * 
	 * @return
	 */
	public ItemsService getItemsService() {
		return (ItemsService) findBean("itemsService");
	}

	/**
	 * Сервис рейсов
	 * 
	 * @return
	 */
	public RaceService getRaceService() {
		return (RaceService) findBean("raceService");
	}

	/**
	 * Сервис маршрутов
	 * 
	 * @return
	 */
	public BusRouteService getBusRouteService() {
		return (BusRouteService) findBean("busRouteService");
	}

	/**
	 * Сервис шаблонов/схем 
	 * 
	 * @return
	 */
	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	/**
	 * Поиск рейса
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findRace(long id) throws Exception {
		this.race = getRaceService().find(id);
		race.setBus(service().getBusService().findWithLazy(
				race.getBus().getId()));

		System.out.println("RACE " + id + ": " + race);
	}
	
	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFBasicControl#init0()
	 */
	@Override
	protected void init0() throws Exception {
		System.out.println("INIT");
		handleSchemaChange();
	}

	// actions
	// -------------------------------------------------------------------------------------------


	/**
	 * Продать место
	 * 
	 * @return
	 */
	public String saleSeat() {
		return "saleSeat";
	}

	/**
	 * Блокировать ячейку
	 * 
	 * @return
	 */
	public String blockCell() {
		return "blockCell";
	}
	
	// Schemas
	// -------------------------------------------------------------------------------------------

	private Long selectedSchemeId;	// Выбранной схемы/плана
	private Schema selectedScheme;	// Выбранная схема/план	

	/**
	 * Выбор схемы
	 * 
	 * @throws Exception
	 */
	public void handleSchemaChange() throws Exception {
		if (selectedSchemeId != null) {

			boolean selected = false;
			for (Schema s : race.getBus().getSchemas()) {
				if (s.getId().equals(selectedSchemeId)) {
					selectedScheme = s;
					selected = true;
					break;
				}
			}

			if (!selected) {
				selectedScheme = null;
				selectedSchemeId = null;
				race.setBus(service().getBusService().findWithLazy(
						race.getBus().getId()));
			}
		}
	}

	// CELL OPERATIONS
	// ---------------------------------------------------------------------------------
	private Cell selectedCell;

	/**
	 * Формирование коллекций для отрисовки схемы
	 * 
	 * @return
	 */
	public List<List<Cell>> getCells() {

		List<List<Cell>> ylist = new ArrayList<List<Cell>>();

		try {

			System.out.println("01");
			
			race.setBusRoute(getBusRouteService().findWithLazy(
					race.getBusRoute().getId()));
			
			System.out.println("02");

			List<Object[]> ilist = getItemsService().raceItemsMatrix(race);
			
			//List<Items> ilist = new ArrayList<Items>();
			
			System.out.println("03");

			if (selectedScheme != null) {

				selectedScheme = service().getSchemaService().findAllLazy(
						selectedScheme.getId());

				Cell pCell = null;
				for (short y = 1; y < selectedScheme.getySize() + 1; ++y) {
					List<Cell> xList = new ArrayList<Cell>();
					for (short x = 1; x < selectedScheme.getxSize() + 1; ++x) {

						Seat seat = null;
						for (Seat s : selectedScheme.getSeats()) {
							//System.out.println("111");
							
							if (s.getSx() != null && s.getSy() != null
									&& s.getSx() == x && s.getSy() == y) {
								seat = s;
								break;
							}
						}

						Cell cell = new Cell(pCell, seat, x, y);
						
						
						// Эта функция создает полосу прогресса, а также пожкрашивает проданные места
						cell.buidProgress(race, ilist, cell);

						// Для проверки, все сделает красным
						// /cell.setOpType(Cell.OP_TYPE.SALE);

						// // Для отладки
						// /for (ProgressPoint pp : cell.getProgress()) {
						// / LOG.debug(pp.getV());
						// /}
						// /System.out.println();

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

	// get's and set's
	// -------------------------------------------------------------------------------------------

	public Race getRace() {
		return race;
	}

	public void setRace(Race race) {
		this.race = race;
	}

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
