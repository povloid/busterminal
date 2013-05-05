/**
 * 
 */
package pk.home.busterminal.web.filters;

import java.io.*;
import java.util.Date;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

/**
 * Фильтр для указани срока кэширования запрашиваемых ресурсов
 * 
 * @author kopychenko
 */
public final class CacheFilter implements Filter {

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {

	}

	private static final int AGE = 60 * 60 * 24 * 7;

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
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

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	public void destroy() {
	}
}