package dao.impl;

/**
 *
 * @author Abbas
 */

import dao.RoleDao;
import dao.UniversityAgentDao;
import dao.UserDao;
import model.UniversityAgent;
import model.User;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
public class UniversityAgentDaoImpl extends DaoImplBase<UniversityAgent>
		implements UniversityAgentDao {

	@Inject
	UserDao userDao;

	@Inject
	RoleDao roleDao;

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public UniversityAgent newEntity() {
		UniversityAgent ua = super.newEntity();
		ua.setUser(userDao.newEntity());
		return ua;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void save(UniversityAgent ua) {
		User user = ua.getUser();
		user.setFullName(ua.getFullName());
		
//		int id = user.getId();
//		if (id == 0 || userDao.findById(id) == null) // this is a new UniversityAgent with new User assigned
		{
			user.getRoles().add(roleDao.getUniversityAgentRole());
		}

		userDao.save(ua.getUser());
		super.save(ua);
	}

}
