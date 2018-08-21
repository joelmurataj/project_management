package com.project.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.project.dto.UserDto;
import com.project.bean.UserBean;

/**
 * Servlet Filter implementation class UserFilter
 */
@WebFilter(filterName = "/UserFilter", urlPatterns = { "/*" })
public class UserFilter implements Filter {

	public UserFilter() {

	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		UserBean userBean = (session != null) ? (UserBean) session
				.getAttribute("userBean") : null;
		UserDto userDto = (userBean != null) ? userBean.getUserDto() : null;
		String currentPath = ((HttpServletRequest) request).getRequestURL()
				.toString();

		if (userDto != null) {
			if (userDto.getRoliId()==1) {
				if ((currentPath.contains("login") || currentPath
						.contains("error.xhtml")) && !allowed(currentPath))
					res.sendRedirect("home.xhtml");

			} else if (userDto.getRoliId()==2) {
				if ((currentPath.contains("login") || currentPath
						.contains("error.xhtml")) && !allowed(currentPath))
					res.sendRedirect("home.xhtml");
				else if (currentPath.contains("admin")
						&& !allowed(currentPath))
					res.sendError(403);

			} else {
				chain.doFilter(request, response);
			}
			chain.doFilter(request, res);

		} else {
			if (!currentPath.contains("login") && !allowed(currentPath))

			{
				res.sendRedirect("/project/login.xhtml");
			} else {
				chain.doFilter(request, response);
			}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	private boolean allowed(String path) {
		return path.contains("javax.faces.resource") || path.contains(".png")
				|| path.contains("resources");
	}

}
