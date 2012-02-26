package dao;


import model.User;

public interface UserDao extends DaoBase<User> {
	
	public User findByUserName(String userName);
}
