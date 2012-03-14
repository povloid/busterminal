package pk.home.busterminal.web.jsf.webflow;

import java.io.Serializable;

import pk.home.libs.combine.web.jsf.flow.AWFWizart;

public class AddToCellWFWizControl extends AWFWizart implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1586242585286422108L;

	
	@Override
	protected void nextImpl() throws Exception {
		if(select == 0)
			throw new Exception("Выберите вариант!");
	}

	private short select;

	public short getSelect() {
		return select;
	}

	public void setSelect(short select) {
		this.select = select;
	}

}
