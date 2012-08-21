package dao.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import dao.ArbiterDao;
import dao.RoleDao;
import dao.UserDao;

import model.Arbiter;
import model.Role;
import model.User;

@Stateless
public class ArbiterDaoImpl extends DaoImplBase<Arbiter> implements ArbiterDao {
    
    @Inject
    UserDao userDao;

    @Inject
    RoleDao roleDao;

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Arbiter newEntity(){
        Arbiter a = super.newEntity();
        a.setUser(userDao.newEntity());
        return a;
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void save(Arbiter a) {
        User user = a.getUser();
        user.setFullName(a.getFullName());
        
        Set<Role> roles = user.getRoles();
        roles.add(roleDao.getArbiterRole());
        
        super.save(a);
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Arbiter findByUser(int userId) {
        List<Arbiter> list =
                em.createQuery("select a from Arbiter a where a.user.id=?1", Arbiter.class).
                setParameter(1, userId).getResultList();
        if( list.size() == 0)
            return null;
        return list.get(0);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<Arbiter> findByEducationGroup (int egroupId) {
        return em.createQuery("select a from Arbiter a where a.educationGroup.id=?1", Arbiter.class).
                setParameter(1, egroupId).getResultList();
    }
}
