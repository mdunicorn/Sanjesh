package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import core.Utils;

import dao.ArbiterDao;
import dao.DesignerDao;
import dao.SanjeshAgentDao;
import dao.UniversityAgentDao;
import dao.UserDao;

import model.Person;
import model.User;

@ManagedBean
@SessionScoped
public class LoginBean {

	@Inject
	private UserDao userDao;
    @Inject
    private SanjeshAgentDao sanjeshAgentDao;
    @Inject
    private UniversityAgentDao universityAgentDao;
    @Inject
    private DesignerDao designerDao;
    @Inject
    private ArbiterDao arbiterDao;
	
	private User currentUser;

	public LoginBean() {
	}
	
	public static LoginBean getInstance() {
        LoginBean lc = (LoginBean) Utils.findBean("loginBean");
        if( lc == null )
            throw new RuntimeException("Login bean could not be retrieved.");
        return lc;
	}
	
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }
    
    public User reloadCurrentUser(){
    	currentUser = userDao.refresh(currentUser);
    	return currentUser;
    }
    
    public String doLogin(String userName, String password, String returnUrl) {
    	userDao.clear();
    	User user = userDao.findByUserName(userName);
    	if( user != null && user.getPassword().equals(password)){
    	    
    	    if (!user.isActive()) {
    	        Utils.addFacesErrorMessage("کاربر شما غیر فعال میباشد. برای اطلاعات بیشتر با مدیر سیستم تماس بگیرید.");
    	        return null;
    	    }
    	    
            currentUser = user;
            userName = null;
            password = null;
            
            if( StringUtils.isNotEmpty(returnUrl))
            	return returnUrl + "?faces-redirect=true";
            else
            	return "home?faces-redirect=true";
        }
    	
    	Utils.addFacesErrorMessage("نام کاربری یا رمز عبور صحیح نمیباشد.");
        
        return null;
    }

    public String doLogout() {
        if (currentUser != null) {
            currentUser = null;
            ((HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false))
                    .invalidate();
            return "/login.xhtml?faces-redirect=true";
        }
        return null;
    }

    public Person getRelatedPerson() {
        Person p = sanjeshAgentDao.findByUser(currentUser.getId());
        if (p == null)
            p = universityAgentDao.findByUser(currentUser.getId());
        if (p == null)
            p = designerDao.findByUser(currentUser.getId());
        if (p == null)
            p = arbiterDao.findByUser(currentUser.getId());
        return p;
    }

}
