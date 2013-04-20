package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.DivisionService;
import pk.home.libs.combine.web.jsf.flow.AWFRPTWFControl;

/**
 * Отчет - баоланс
 * 
 * @author povloid
 *
 */
public class RPTDivisionBalanceF1WFControl extends AWFRPTWFControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long divisionId;	// id отделения
	private Division division;	// Отделение

	private Date bDate, eDate;	// промежуток времени

	/**
	 * Сервис подразделений
	 * @return
	 */
	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	/**
	 * Поиск отделения по id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void setDivisionId(Long id) throws Exception {
		this.division = getDivisionService().find(id);
	}

	/**
	 * Сформировать отчет
	 * 
	 * @return
	 * @throws Exception
	 */
	public MakeResult _makeReport() throws Exception {

		if (division == null)
			throw new Exception("Не указано подразделение");

		if (bDate == null)
			throw new Exception("Не указана начальная дата");

		if (eDate == null)
			throw new Exception("Не указана конечная дата");

		if (bDate.compareTo(eDate) > 0)
			throw new Exception("Начальная дата позже конечной");

		return new MakeResult(division.getId() + "/" + bDate.getTime()
				+ "/" + eDate.getTime() 

		, "division_balance_f1_" + division.getId() + "_"
				+ dateFormatFileNameTime.format(new Date()));
	}

	// get's and set's
	// ---------------------------------------------------------------------------------------------

	public Division getDivision() {
		return division;
	}

	public void setDivision(Division division) {
		this.division = division;
	}

	public Date getbDate() {
		return bDate;
	}

	public void setbDate(Date bDate) {
		this.bDate = bDate;
	}

	public Date geteDate() {
		return eDate;
	}

	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}

	public Long getDivisionId() {
		return divisionId;
	}

}
