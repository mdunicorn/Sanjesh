package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

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

    public String doLogin() {
    	
    	User user = userDao.findByUserName(userName);
    	if( user != null && user.getPassword().equals(password)){
            currentUser = user;
            userName = null;
            password = null;
            return "home?faces-redirect=true";
        }
    	
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("نام کاربری یا رمز عبور صحیح نمیباشد."));
        
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
