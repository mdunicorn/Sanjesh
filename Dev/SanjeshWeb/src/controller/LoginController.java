package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import core.Utils;

import dao.UserDao;

import model.User;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@SessionScoped
public class LoginController {

	@Inject
	private UserDao userDao;
	
    private String userName;
    private String password;
    private User currentUser;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String doLogin() {
    	return doLogin(null);
    }
    
    public String doLogin(String returnUrl) {
    	
    	User user = userDao.findByUserName(userName);
    	if( user != null && user.getPassword().equals(password)){
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
