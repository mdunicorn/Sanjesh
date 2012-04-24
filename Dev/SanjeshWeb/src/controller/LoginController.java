package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * 
 * @author Muhammad
 */
@ManagedBean
@ViewScoped
public class LoginController {

	@ManagedProperty(value = "#{loginBean}")
	private LoginBean loginBean;

	private String userName;
	private String password;

	private String returnUrl;

	public void setLoginBean(LoginBean lb) {
		this.loginBean = lb;
	}

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

	public void setReturnUrl(String url) {
		this.returnUrl = url;
	}
	
	public String getReturnUrl(){
		return returnUrl;
	}

	public String doLogin() {
		return loginBean.doLogin(userName, password, returnUrl);
	}
}
