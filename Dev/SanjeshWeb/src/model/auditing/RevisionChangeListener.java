package model.auditing;

import org.hibernate.envers.RevisionListener;

import controller.LoginBean;
import core.Utils;

public class RevisionChangeListener implements RevisionListener{
	
	@Override
	public void newRevision(Object revEntity) {
		RevInfo re = (RevInfo)revEntity;
		LoginBean loginBean = Utils.findBean("loginBean"); 
		re.setUserId(loginBean.getCurrentUser().getId());
	}
}
