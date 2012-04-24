package core;

import java.util.Set;

import model.Role;
import model.User;
import controller.LoginBean;

public class SecurityService {

//	public static boolean isLoggedIn() {
//		LoginBean lc = getLoginBean();
//		if (lc == null)
//			return false;
//		return lc.isLoggedIn();
//	}

	 public static LoginBean getLoginBean() {
		 LoginBean lc = (LoginBean) Utils.findBean("loginBean");
		 if( lc == null )
			 throw new RuntimeException("Login controller could not be retrieved.");
		 return lc;
	 }

	// public static User getCurrentUser() {
	// LoginBean lc = getLoginBean();
	// if (lc == null)
	// return null;
	// return lc.getCurrentUser();
	// }

	public static boolean hasPermission(User user, SecurityItem securityItem) {
		if (user == null)
			return false;
		if (user.isAdmin())
			return true;
		Set<Role> roles = user.getRoles();
		//SecurityItem root = securityItem.findRoot();
		String key = securityItem.getFullKey();
		for (Role r : roles) {
			java.util.List<String> keys;
			if ((keys = SecurityItems.RoleAccessKeys.get(r.getId())) != null && keys.contains(key)) {
				return true;
			}
		}
		return false;
	}

	public static boolean hasPermission(LoginBean loginBean, SecurityItem securityItem) {
		return hasPermission(loginBean.getCurrentUser(), securityItem);
	}
	
	public static boolean hasPermission(SecurityItem securityItem){
		return hasPermission(getLoginBean(), securityItem);
	}
}
