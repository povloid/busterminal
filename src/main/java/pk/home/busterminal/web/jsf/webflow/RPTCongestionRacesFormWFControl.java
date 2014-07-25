package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.util.Date;

import pk.home.libs.combine.web.jsf.flow.AWFRPTWFControl;

/**
 * Отчет - список рейсов и их загруженность
 * 
 * @author povloid
 *
 */
public class RPTCongestionRacesFormWFControl extends AWFRPTWFControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Date bDate, eDate;	// промежуток времени

	/**
	 * Сформировать отчет
	 * 
	 * @return
	 * @throws Exception
	 */
	public MakeResult _makeReport() throws Exception {

		if (bDate == null)
			throw new Exception("Не указана начальная дата");

		if (eDate == null)
			throw new Exception("Не указана конечная дата");

		if (bDate.compareTo(eDate) > 0)
			throw new Exception("Начальная дата позже конечной");

		return new MakeResult( bDate.getTime() + "/" + eDate.getTime() 
		, "Congestion_of_races_" + "_"	
				+ dateFormatFileNameTime.format(new Date()));
	}

	// get's and set's
	// ---------------------------------------------------------------------------------------------

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

}
