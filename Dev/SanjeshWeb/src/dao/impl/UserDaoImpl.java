package dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import model.User;
import dao.UserDao;

@Stateless
public class UserDaoImpl extends DaoImplBase<User> implements UserDao {
	
	@SuppressWarnings("unchecked")
	public User findByUserName(String userName) {
		List<User> list = em.createQuery("from User where userName=?")
				.setParameter(1, userName).getResultList();
		if(list.size() == 0)
			return null;
		return list.get(0);
	}
}
