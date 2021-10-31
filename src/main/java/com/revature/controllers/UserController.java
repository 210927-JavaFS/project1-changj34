package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.services.LoginService;
import com.revature.services.UserService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class UserController implements Controller{

	private UserService userService = new UserService();	
	private LoginService loginService = new LoginService();
	public static Logger log = LoggerFactory.getLogger(UserController.class);
	
	public Handler getAllUser = (ctx) -> {
		List<User> list = userService.getAllUsers();
		log.info("In getAllUsers");
		
		ctx.json(list);
		ctx.status(200);
	};
	
	public Handler loginAttempt = (ctx) -> {
		UserDTO userDTO = ctx.bodyAsClass(UserDTO.class);
		if (loginService.login(userDTO)) {
			ctx.req.getSession();
			log.info("In LoginAttempt");
			ctx.status(200);
		} else {
			ctx.req.getSession().invalidate();
			log.warn("Invalidating session");
			ctx.status(401);
		}
	};
	
	
	public Handler getUser = (ctx) -> {
		log.info("In getUser");
		String username = ctx.pathParam("user");
		User user = userService.getByUsername(username);
		ctx.json(user);
		ctx.status(200);
	};
	
	public Handler addUser = (ctx) -> {
		User user = ctx.bodyAsClass(User.class);
		if (userService.addUser(user)) {
			log.info("Adding user success");
			ctx.status(201);
		} else {
			log.warn("Failed to add user");
			ctx.status(400);
		}
	};
	
	public Handler updateUser = (ctx) -> {
		User user = ctx.bodyAsClass(User.class);
		if (userService.updateUser(user)) {
			log.info("Update user success");
			ctx.status(200);
		} else {
			log.warn("Update user failed");
			ctx.status(400);
		}
	};
	
	public Handler deleteUser = (ctx) -> {
		String username = ctx.pathParam("user");
		
		if (userService.deleteUser(username)) {
			ctx.status(200);
			log.info("User deleted");
		} else {
			ctx.status(400);
			log.warn("Failed to delete user");
		}
	};
	
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("/users", this.getAllUser);
		app.get("/users/:user", this.getUser);
		app.post("/users", this.addUser);
		app.put("/users", this.updateUser);
		app.delete("/users/:user", this.deleteUser);
		app.post("/login", this.loginAttempt);
	}
}
