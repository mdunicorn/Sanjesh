package dao.impl;

/**
 *
 * @author Abbas
 */
import dao.DesignerDao;
import dao.RoleDao;
import dao.UserDao;
import model.Designer;
import model.User;

import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import model.RegisterState;


@Stateless
public class DesignerDaoImpl extends DaoImplBase<Designer> implements DesignerDao {

	@Inject
	UserDao userDao;

	@Inject
	RoleDao roleDao;
	
	
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Designer newEntity() {
		Designer d = super.newEntity();
		d.setUser(userDao.newEntity());
		return d;
	}

    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(Designer d) {
        if( d.getState() == RegisterState.NONE)
            d.setState(RegisterState.REGISTERED);
        
		User user = d.getUser();
		user.setFullName(d.getFullName());
		
		//if( new user )
		{
			user.getRoles().add(roleDao.getDesignerRole());
		}

        // The save (merge) operation will cascade to user and roles.
        super.save(d);
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public Designer findByUser(int userId) {
		List<Designer> list =
				em.createQuery("select u from Designer d where d.user.id=?1", Designer.class).
				setParameter(1, userId).getResultList();
		if( list.size() == 0)
			return null;
		return list.get(0);
	}


    @SuppressWarnings("unchecked")
	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Designer> findByState(RegisterState state){
        return em.createQuery("from Designer where state=:s").
                setParameter("s", state).getResultList();
    }

    
}
