package core;

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

import controller.LoginController;

@WebFilter("/*")
public class SecurityFilter implements Filter {
	
    public SecurityFilter() {
    }

	public void destroy() {
	}

	private static String[] securedPages = new String[]
		{
			"/Course.xhtml",
			"/Designer.xhtml",
			"/DesignerRegistration.xhtml",
			"/EducationField.xhtml",
			"/EducationGroup.xhtml",
			"/Grade.xhtml",
			"/Home.xhtml",
			"/Question.xhtml",
			"/SanjeshAgent.xhtml",
			"/Topic.xhtml",
			"/University.xhtml",
			"/UniversityAgent.xhtml",
		};
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String path = req.getRequestURI().substring(req.getContextPath().length());
		
		
		for( String s : securedPages){
			if( s.equalsIgnoreCase(path)){
				
				LoginController loginController = (LoginController)req.getSession().getAttribute("loginController");
				if(loginController == null){
					res.sendRedirect(req.getContextPath() + "/SessionExpired.jsp");
					return;
				}

				if(!loginController.isLoggedIn()){
					res.sendRedirect(req.getContextPath() + "/login.xhtml?returnUrl=" + path);
					return;
				}
				break;
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
