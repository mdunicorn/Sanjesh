package model.auditing;

import org.hibernate.envers.RevisionListener;

import controller.LoginController;
import core.Utils;

public class RevisionChangeListener implements RevisionListener{
	
	@Override
	public void newRevision(Object revEntity) {
		RevInfo re = (RevInfo)revEntity;
		LoginController loginController = Utils.findBean("loginController"); 
		re.setUserId(loginController.getCurrentUser().getId());
	}
}
