package pk.home.busterminal.web.jsf;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "report")
@RequestScoped
public class Report {

	private String report;
	private String purl;
	private String filename;

	public String getReport() {
		return report;
	}

	public void setReport(String report) {
		this.report = report;
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
