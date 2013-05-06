package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Seat;
import pk.home.busterminal.service.SeatService;
import pk.home.busterminal.web.jsf.security.TerminalCurrentUser;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;

/**
 * Контрол для блокировки посадочного места
 * 
 * @author povloid
 *
 */
public class SeatOPBlockWFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Seat seat;
	
	/**
	 * сервис мест
	 * 
	 * @return
	 */
	public SeatService getSeatService() {
		return (SeatService) findBean("seatService");
	}
	
	
	/**
	 * сервис по текущему пользователю
	 * 
	 * @return
	 */
	public TerminalCurrentUser getTerminalCurrentUser() {
		return (TerminalCurrentUser) findBean("terminalCurrentUser");
	}

	
	/**
	 * Поиск места
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void findSeat(long id) throws Exception {
		this.seat = getSeatService().find(id);

		if (this.seat == null) {
			FacesContext.getCurrentInstance()
					.addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Error: Операция не возможна! ",
									"Места по коду " + id
											+ " не найдено."));
		}
	}
	
	/**
	 * Событие блокировки
	 * 
	 * @return
	 * @throws Exception
	 */
	public String block() throws Exception {
		if(seat.getBlock() != null && seat.getBlock())
			seat.setBlocker(getTerminalCurrentUser().getUserAccount());
		
		try{
		getSeatService().merge(seat);
		} catch (Exception e){
			FacesContext.getCurrentInstance()
			.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error: Операция не возможна! ",
							e.getMessage()));
			throw new Exception(e);
		}
		
		return "";
	}
	
	/**
	 * Событие блокировки и закрытие диалога
	 * 
	 * @return
	 * @throws Exception
	 */
	public String blockAndClose() throws Exception {
		block();
		return "end";
	}
	

	// gets and sets
	// ---------------------------------------------------------------------------------------------------

	public Seat getSeat() {
		return seat;
	}


	public void setSeat(Seat seat) {
		this.seat = seat;
	}
}
