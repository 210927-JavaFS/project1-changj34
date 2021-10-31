package com.revature.services;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.models.UserDTO;


public class LoginService {
	
	private UserService userService = new UserService();
	public static Logger log = LoggerFactory.getLogger(LoginService.class);
	
	public boolean login(UserDTO userDTO) {
		User user = userService.getByUsername(userDTO.username);
		
		if (user != null && userDTO.password.hashCode() == user.getPassword()) {
			log.info("Login successful");
			return true;
		}
		log.warn("Incorrect credentials. Login failed.");
		return false;
	}
}
