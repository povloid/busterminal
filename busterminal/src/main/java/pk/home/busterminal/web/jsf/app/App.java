/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pk.home.busterminal.web.jsf.app;

import java.io.Serializable;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 * @author traveler
 */
@ManagedBean(name = "app")
@RequestScoped
public class App implements Serializable {

	private static final long serialVersionUID = 2167179189163857373L;

	public Date getDate() {
		return new Date();
	}

	public void setDate(Date date) {

	}

	private static int sbindex = 1;

	private int bindex;

	@PostConstruct
	void init() {
		++sbindex;
		if (sbindex > 10)
			sbindex = 1;
		bindex = sbindex;
	}

	public int getBindex() {
		return bindex;
	}

	public void setBindex(int bindex) {
		this.bindex = bindex;
	}

	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
