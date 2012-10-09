package controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import core.Utils;

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
	private String securityString;
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

	public String getSecurityString() {
        return securityString;
    }

    public void setSecurityString(String securityString) {
        this.securityString = securityString;
    }

    public void setReturnUrl(String url) {
		this.returnUrl = url;
	}
	
	public String getReturnUrl(){
		return returnUrl;
	}

	public String doLogin() {
	    String kaptchaExpected = (String) FacesContext.getCurrentInstance().getExternalContext().
	        getSessionMap().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
	    if (securityString == null || !securityString.equals(kaptchaExpected)) {
	        Utils.addFacesErrorMessage("کد امنیتی وارد شده صحیح نمیباشد.");
	        return null;
	    }
		return loginBean.doLogin(userName, password, returnUrl);
	}
}
