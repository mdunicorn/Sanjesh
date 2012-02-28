package dao.impl;

/**
 *
 * @author Abbas
 */
import dao.SanjeshAgentDao;
import dao.UserDao;
import model.SanjeshAgent;
import javax.ejb.Stateless;
import javax.inject.Inject;


@Stateless
public class SanjeshAgentDaoImpl extends DaoImplBase<SanjeshAgent> implements SanjeshAgentDao {
   
	@Inject
	UserDao userDao;
	
	@Override
	public SanjeshAgent newEntity(){
		SanjeshAgent sa = super.newEntity();
		sa.setUser(userDao.newEntity());
		return sa;
	}
	
	@Override
	public void save(SanjeshAgent sa){
		sa.getUser().setFullName(sa.getFullName());
		userDao.save(sa.getUser());
		super.save(sa);
	}
	
}
