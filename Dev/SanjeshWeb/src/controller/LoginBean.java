package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import core.Utils;

import dao.UserDao;

import model.User;

@ManagedBean
@SessionScoped
public class LoginBean {

	@Inject
	private UserDao userDao;
	
	private User currentUser;

	public LoginBean() {
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
            return "/login.xhtml?faces-redirect=true";
        }
        return null;
    }
}
