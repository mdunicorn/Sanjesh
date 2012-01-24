package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import model.User;

/**
 *
 * @author Muhammad
 */
@ManagedBean
@SessionScoped
public class LoginController {

    private String userName;
    private String password;
    private boolean loggedIn;
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
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public String doLogin() {
        if ("admin".equals(userName.toLowerCase()) && "admin".equals(password)) {
            loggedIn = true;
            User u = new User();
            u.setUserName("admin");
            u.setPassword("admin");
            u.setFullName("سرپرست");

            currentUser = u;

            return "home?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("نام کاربری یا رمز عبور صحیح نمیباشد."));
        return null;
    }

    public String doLogout() {
        if (loggedIn) {
            loggedIn = false;
            currentUser = null;
            return "/login.xhtml?faces-redirect=true";
        }
        return null;
    }
}
