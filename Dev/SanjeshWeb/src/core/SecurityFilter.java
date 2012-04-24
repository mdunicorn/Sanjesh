package core;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.LoginBean;

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
			"/userprofile.xhtml",
		};
	
	private static Map<String, List<SecurityItem>> pageSecurityItems;
	
	static{
		pageSecurityItems = new HashMap<String, List<SecurityItem>>();
		pageSecurityItems.put("/Course.xhtml",
			Arrays.asList(SecurityItems.Course));

		pageSecurityItems.put("/Designer.xhtml",
			Arrays.asList(SecurityItems.Designer));
		
		pageSecurityItems.put("/EducationField.xhtml",
			Arrays.asList(SecurityItems.EducationField));

		pageSecurityItems.put("/EducationGroup.xhtml",
			Arrays.asList(SecurityItems.EducationGroup));

		pageSecurityItems.put("/Grade.xhtml",
			Arrays.asList(SecurityItems.Grade));

		pageSecurityItems.put("/Question.xhtml",
			Arrays.asList(SecurityItems.Question));
		
		pageSecurityItems.put("/SanjeshAgent.xhtml",
			Arrays.asList(SecurityItems.SanjeshAgent));
		
		pageSecurityItems.put("/Topic.xhtml",
			Arrays.asList(SecurityItems.Topic));
		
		pageSecurityItems.put("/University.xhtml",
			Arrays.asList(SecurityItems.University));
		
		pageSecurityItems.put("/UniversityAgent.xhtml",
			Arrays.asList(SecurityItems.UniversityAgent));		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse res = (HttpServletResponse)response;
		
		String contextPath = req.getContextPath();
		
		String path = req.getRequestURI().substring(contextPath.length());
		
		
		LoginBean loginBean = (LoginBean)req.getSession().getAttribute("loginBean");
		boolean isLoggedIn = loginBean != null && loginBean.isLoggedIn();
		
		if (isLoggedIn && "/".equals(path)) {
			res.sendRedirect(contextPath + "/home.xhtml");
		}

		for (String s : pageSecurityItems.keySet()) {
			if (s.equalsIgnoreCase(path)) {
				
				if(!isLoggedIn){
					res.sendRedirect(req.getContextPath() + "/notloggedin.xhtml?returnUrl=" + path);
					return;
				}

				for( SecurityItem si : pageSecurityItems.get(s)){
					if( !SecurityService.hasPermission(loginBean, si) ){
						res.sendRedirect(contextPath + "/accessdenied.xhtml");
						return;
					}
				}
			}
		}
		
		for (String s : securedPages) {
			if (s.equalsIgnoreCase(path)) {
				if (!isLoggedIn) {
					res.sendRedirect(req.getContextPath() + "/notloggedin.xhtml?returnUrl=" + path);
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
