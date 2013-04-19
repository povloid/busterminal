package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.BusTempliteMasterService;
import pk.home.libs.combine.web.jsf.flow.AWFWizart;

/**
 * Контрол для формы удаления ячейки
 * 
 * @author povloid
 *
 */
public class DelFromCellWFWizControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6011782358806237532L;

	private Long selectedSeatId;	// id отрабатываемой ячейки
	private short select;			// отрабатываемая ячека
	
	/**
	 * Сервис управления шаблоном автобуса
	 * 
	 * @return
	 */
	public BusTempliteMasterService service() {
		return (BusTempliteMasterService) findBean("busTempliteMasterService");
	}

	/* (non-Javadoc)
	 * @see pk.home.libs.combine.web.jsf.flow.AWFWizart#nextImpl()
	 */
	@Override
	protected void nextImpl() throws Exception {
		if (select == 0)
			throw new Exception("Выберите вариант!");

	}
	
	/**
	 * Обнулить координаты ячейки 
	 * 
	 * @throws Exception
	 */
	public void setNotMarked() throws Exception {
		Seat seat = service().getSeatService().find(selectedSeatId);
		seat.setSx((short) 0);
		seat.setSy((short) 0);
		service().getSeatService().merge(seat);
	}
	
	// get's and set's -------------------------------------------------------------------------------------------------
	
	public short getSelect() {
		return select;
	}

	public void setSelect(short select) {
		this.select = select;
	}

	public Long getSelectedSeatId() {
		return selectedSeatId;
	}

	public void setSelectedSeatId(Long selectedSeatId) {
		this.selectedSeatId = selectedSeatId;
	}

}
