package dao.impl;

/**
 *
 * @author Abbas
 */

import java.util.Set;

import dao.RoleDao;
import dao.SanjeshAgentDao;
import dao.UserDao;
import model.Role;
import model.SanjeshAgent;
import model.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;


@Stateless
public class SanjeshAgentDaoImpl extends DaoImplBase<SanjeshAgent> implements SanjeshAgentDao {
   
	@Inject
	UserDao userDao;

	@Inject
	RoleDao roleDao;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public SanjeshAgent newEntity(){
		SanjeshAgent sa = super.newEntity();
		sa.setUser(userDao.newEntity());
		return sa;
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(SanjeshAgent sa){
		User user = sa.getUser();
		user.setFullName(sa.getFullName());
//		int id = user.getId();
//		if (id == 0 || userDao.findById(id) == null) // this is a new UniversityAgent with new User assigned
		{
			Set<Role> roles = user.getRoles();
			
			for( Role r : roles)
				System.out.println(r.getName());
			
			if (sa.isArbiterExpert())
				roles.add(roleDao.getArbiterExpertRole());
			else
				roles.remove(roleDao.getArbiterExpertRole());

			if (sa.isDataExpert())
				roles.add(roleDao.getDataExpertRole());
			else
				roles.remove(roleDao.getDataExpertRole());

			if (sa.isDesignerExpert())
				roles.add(roleDao.getDesignerExpertRole());
			else
				roles.remove(roleDao.getDesignerExpertRole());
			
			if (sa.isQuestionExpert())
				roles.add(roleDao.getQuestionExpertRole());
			else
				roles.remove(roleDao.getQuestionExpertRole());
		}
		
		userDao.save(user);
		super.save(sa);
	}
	
}
