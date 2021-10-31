package com.revature.repos;

import java.util.List;

import com.revature.models.User;

public interface UserDAO {

	List<User> findAllUsers();
	boolean addUser(User user);
	boolean updateUser(User user);
	boolean deleteUser(User user);
	User getByUsername(String username);
	
}
