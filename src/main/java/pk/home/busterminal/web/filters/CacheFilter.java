/**
 * 
 */
package pk.home.busterminal.web.filters;

import java.io.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author kopychenko
 */
public class CacheFilter implements Filter {

	public void init(FilterConfig config) throws ServletException {

	}

	private static final int AGE = 60 * 60 * 24 * 7;

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain next) throws IOException, ServletException {

		// Calendar inOneMonth = Calendar.getInstance();
		// inOneMonth.add(Calendar.MONTH, 1);
		long expiry = new Date().getTime() + AGE * 1000;

		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setDateHeader("Expires", expiry);
		httpResponse.setHeader("Cache-Control", "max-age=" + AGE);

		next.doFilter(request, response);

	}

	public void destroy() {
	}
}