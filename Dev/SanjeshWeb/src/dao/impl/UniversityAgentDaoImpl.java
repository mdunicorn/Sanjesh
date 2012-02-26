package dao.impl;

/**
 *
 * @author Abbas
 */
import dao.UniversityAgentDao;
import dao.UserDao;
import model.UniversityAgent;
import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class UniversityAgentDaoImpl extends DaoImplBase<UniversityAgent> implements UniversityAgentDao {
   
	@Inject
	UserDao userDao;
	
	@Override
	public UniversityAgent newEntity(){
		UniversityAgent ua = super.newEntity();
		ua.setUser(userDao.newEntity());
		return ua;
	}
    
}
