package pk.home.busterminal.web.jsf.security;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 * 
 * Код позаимствован: 
 * User: Dmitry Leontyev
 * Date: 12.12.10
 * Time: 21:45
 * 
 * доработан:
 * @author povloid
 */
@Scope("request")
@Controller("authController")
public final class AuthController {
	
	
	
	private static final Logger LOG = Logger.getLogger(AuthController.class);

    
    private String user;
    private String password;
    private boolean remember_me;
    
    
    /**
     * Обрабатывает запрос на аутентификацию.
     *
     * @return
     * @throws IOException
     * @throws ServletException
     */
	public String doLogin() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_check");
        
        String sss = ((ServletRequest) context.getRequest()).getParameter("_spring_security_remember_me");
        LOG.info("**** doLogin() **** => " + sss);
        
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
               
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    
    }
    

    /**
     * Выполняет завершение сеанса работы пользователя с системой.
     *
     * @return
     * @throws java.io.IOException
     * @throws javax.servlet.ServletException
     */
    public String doLogout() throws IOException, ServletException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        RequestDispatcher dispatcher = ((ServletRequest) context.getRequest())
                .getRequestDispatcher("/j_spring_security_logout");
        dispatcher.forward((ServletRequest) context.getRequest(), (ServletResponse) context.getResponse());
        FacesContext.getCurrentInstance().responseComplete();
        return null;
    }

    // get's and set's -----------------------------------------------------------------------------------------------------

	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isRemember_me() {
		return remember_me;
	}


	public void setRemember_me(boolean remember_me) {
		this.remember_me = remember_me;
	}    
    
}