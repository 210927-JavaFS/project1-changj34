package com.revature;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.controllers.Controller;
import com.revature.controllers.ReimbursementController;
import com.revature.controllers.UserController;

import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;

public class App {

	private static Javalin app;
	public static Logger log = LoggerFactory.getLogger(App.class);
	
	public static void main(String[] args) {
		app = Javalin.create((config) -> {
			config.addStaticFiles("/static", Location.CLASSPATH);
		});
		
		configure(new ReimbursementController(), new UserController());
		log.info("Starting ERS app");
		
		app.start(8081);

	}

	private static void configure(Controller... controllers) {
		for(Controller c:controllers) {
			c.addRoutes(app);
		}
	}
	
}
