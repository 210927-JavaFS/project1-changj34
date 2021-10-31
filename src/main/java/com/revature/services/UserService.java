package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.repos.UserDAO;
import com.revature.repos.UserDAOImpl;


public class UserService {
	
private UserDAO userDao = new UserDAOImpl();
public static Logger log = LoggerFactory.getLogger(UserService.class);
	
	public List<User> getAllUsers() {
		return userDao.findAllUsers();
	}
	
	
	public boolean addUser(User user) {
		return userDao.addUser(user);
	}
	
	public boolean updateUser(User user) {
		return userDao.updateUser(user);
	}

	public boolean deleteUser(String username) {
		User user = getByUsername(username);
		return userDao.deleteUser(user);
	}
	
	public User getByUsername(String username) {
		User user = userDao.getByUsername(username);
		if (user != null) {
			return user;
		} else {
			log.warn("Failed to get user");
			return new User();
		}
	}
	

}
