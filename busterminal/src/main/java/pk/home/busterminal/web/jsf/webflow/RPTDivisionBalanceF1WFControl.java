package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import pk.home.busterminal.domain.Division;
import pk.home.busterminal.service.DivisionService;
import pk.home.libs.combine.web.jsf.flow.AWFBasicControl;

public class RPTDivisionBalanceF1WFControl extends AWFBasicControl implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3786051198921606764L;

	private final DateFormat dateFormatFileNameTime = new SimpleDateFormat(
			"dd-MM-yyyy_HH_mm_ss", new Locale("ru"));

	private Long divisionId;
	private Division division;

	private Date bDate, eDate;

	private String purl, filename;

	public DivisionService getDivisionService() {
		return (DivisionService) findBean("divisionService");
	}

	public void setDivisionId(Long id) throws Exception {
		this.division = getDivisionService().find(id);
	}

	/**
	 * Сформировать отчет
	 * 
	 * @return
	 * @throws Exception
	 */
	public String makeReport() throws Exception {
		try {
			if (division == null)
				throw new Exception("Не указано подразделение");

			if (bDate == null)
				throw new Exception("Не указана начальная дата");

			if (eDate == null)
				throw new Exception("Не указана конечная дата");

			if (bDate.compareTo(eDate) > 0)
				throw new Exception("Начальная дата позже конечной");

			purl = "/" + division.getId() + "/" + bDate.getTime() + "/"
					+ eDate.getTime() + "/";

			filename = "division_balance_f1_" + division.getId() + "_"
					+ dateFormatFileNameTime.format(new Date());

			return "makeReport";

		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: ", e
							.getMessage()));

			purl = null;

			throw new Exception(e);
		}
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

	public String getPurl() {
		return purl;
	}

	public void setPurl(String purl) {
		this.purl = purl;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}
