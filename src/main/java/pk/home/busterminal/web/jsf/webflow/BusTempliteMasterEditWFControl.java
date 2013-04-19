package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 * Контрол для работы с шаблоном автобуса
 * 
 * @author povloid
 *
 */
public class BusTempliteMasterEditWFControl extends AWFControl<Bus, Long>
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3189205660540524184L;

	private Long selectedSchemeId;	// id выбранной остановки
	private Schema selectedScheme;	// выбранная остановка
	
	/**
	 * Сервис управления шаблонами
	 * 
	 * @return
	 */
	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#findEdited(java.lang.Object)
	 */
	@Override
	public Bus findEdited(Long id) throws Exception {
		return service().getBusService().findWithLazy(id);
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#newEdited()
	 */
	@Override
	public Bus newEdited() throws Exception {
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmAddImpl()
	 */
	@Override
	protected void confirmAddImpl() throws Exception {
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmEditImpl()
	 */
	@Override
	protected void confirmEditImpl() throws Exception {
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#confirmDelImpl()
	 */
	@Override
	protected void confirmDelImpl() throws Exception {
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFControl#init0()
	 */
	@Override
	protected void init0() throws Exception {
		System.out.println(">>>init()");

		handleSchemaChange();
	}


	/**
	 * Выбор схемы
	 * 
	 * @throws Exception
	 */
	public void handleSchemaChange() throws Exception {
		if (selectedSchemeId != null) {

			boolean selected = false;
			for (Schema s : edited.getSchemas()) {
				if (s.getId().equals(selectedSchemeId)) {
					selectedScheme = s;
					selected = true;
					break;
				}
			}

			if (!selected) {
				selectedScheme = null;
				selectedSchemeId = null;
				edited = service().getBusService().findWithLazy(edited.getId());
			}
		}
	}

	/**
	 * Добавить новую схему
	 * 
	 * @return
	 */
	public String addNewSchema() {
		return "addNewSchema";
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
	 * Удалить текущую схему
	 * 
	 * @return
	 */
	public String delSelectedSchema() {
		return "delSelectedSchema";
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

	// CELL OPERATIONS
	// ---------------------------------------------------------------------------------
	private Cell selectedCell;	// Выбранная ячейка

	/**
	 * Добавить в ячейку
	 * 
	 * @return
	 */
	public String addToCell() {
		return "addToCell";
	}

	/**
	 * Редактировать в ячейке
	 * 
	 * @return
	 */
	public String editInCell() {
		return "editInCell";
	}

	/**
	 * Удалить в ячейке
	 * 
	 * @return
	 */
	public String delFromCell() {
		return "delFromCell";
	}

	private BigDecimal basePrice;

	/**
	 * Проставить цену согласно мастер проценту
	 * 
	 * @return
	 */
	public String calcAndSetPrice() {
		try {
			edited.setBasePrice(basePrice);
			edited = service().getBusService().mergeBusAndCalcAndSetPrice(
					edited);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return "calcAndSetPrice";
	}

	private boolean discount;		// Скидка
	private int discountPotsent;	// Скидочный процент 

	/**
	 * Мастер группового проставления сведений по скидкам
	 * 
	 * @return
	 */
	public String setAllSeatsDiscount() {
		try {
			service().getBusService().setAllSeatsDiscount(edited, discount,
					discountPotsent);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return "setAllSeatsDiscount";
	}

	/**
	 * Заблокировать все
	 * 
	 * @return
	 */
	public String setAllSeatsBlock() {

		try {
			service().getBusService().setAllSeatsBlock(edited, true);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return "setAllSeatsBlock";
	}

	/**
	 * Разблокировать все
	 * 
	 * @return
	 */
	public String setAllSeatsUnBlock() {

		try {
			service().getBusService().setAllSeatsBlock(edited, false);
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));
		}

		return "setAllSeatsUnBlock";
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

	public BigDecimal getBasePrice() {
		return basePrice;
	}

	public void setBasePrice(BigDecimal basePrice) {
		this.basePrice = basePrice;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public int getDiscountPotsent() {
		return discountPotsent;
	}

	public void setDiscountPotsent(int discountPotsent) {
		this.discountPotsent = discountPotsent;
	}
}
