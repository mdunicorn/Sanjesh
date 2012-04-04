package dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import model.User;
import dao.UserDao;

@Stateless
public class UserDaoImpl extends DaoImplBase<User> implements UserDao {
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public User findByUserName(String userName) {
		List<User> list = findByUserNameList(userName);
		if(list.size() == 0)
			return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<User> findByUserNameList(String userName){
		return em.createQuery("from User where userName=?")
				.setParameter(1, userName).getResultList();
	}
	
	@Override
	public boolean validateSave(User u, List<String> messages) {
		boolean isValid = super.validateSave(u, messages);
		if (!isValid)
			return false;

		List<User> l = findByUserNameList(u.getUserName());
		if (l.size() > 1 || (l.size() == 1 && l.get(0).getId() != u.getId())) {
			messages.add("نام کاربری وارد شده تکراری است. لطفاً نام دیگری وارد کنید.");
			return false;
		}

		return true;
	}
}
