package com.revature.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.models.Reimbursement;
import com.revature.services.ReimbursementService;

import io.javalin.Javalin;
import io.javalin.http.Handler;

public class ReimbursementController implements Controller{

	private ReimbursementService reimbursementService = new ReimbursementService();	
	public static Logger log = LoggerFactory.getLogger(ReimbursementController.class);
	
	public Handler getAllReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			log.info("Successfully retrieved all reimbursements");
			List<Reimbursement> list = reimbursementService.getAllReimbursements();
			
			ctx.json(list);
			ctx.status(200);
		} else {
			log.warn("Failed to get reimbursements");
			ctx.status(401);
		}
	};
	
	public Handler getReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			try {
				log.info("Getting specific reimbursement");
				String idString = ctx.pathParam("reimbursement");
				int id = Integer.parseInt(idString);
				Reimbursement reimbursement = reimbursementService.getReimbursement(id);
				ctx.json(reimbursement);
				ctx.status(200);
			} catch (NumberFormatException e) {
				log.warn("Not a number");
				e.printStackTrace();
				ctx.status(400);
			}
		} else {
			log.warn("Reimbursement retrieval failed");
			ctx.status(401);
		}
	};
	
	
	public Handler addReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
			if (reimbursementService.addReimbursement(reimbursement)) {
				log.info("Added reimbursement request");
				ctx.status(201);
			} else {
				log.warn("Could not add reimbursement");
				ctx.status(400);
			}
		} else {
			ctx.status(401);
		}
	};
	
	public Handler updateReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			Reimbursement reimbursement = ctx.bodyAsClass(Reimbursement.class);
			if (reimbursementService.updateReimbursement(reimbursement)) {
				log.info("Reimbursement updated");
				ctx.status(200);
			} else {
				log.warn("Failed to update reimbursement");
				ctx.status(400);
			}
		} else {
			ctx.status(401);
		}
	};
	
	public Handler deleteReimbursement = (ctx) -> {
		if (ctx.req.getSession(false) != null) {
			String id = ctx.pathParam("reimbursement");
			try {
				int num = Integer.parseInt(id);
				if (reimbursementService.deleteReimbursement(num)) {
					ctx.status(200);
				} else {
					ctx.status(400);
				}
			} catch(NumberFormatException e) {
				e.printStackTrace();
				ctx.status(400);
			}
		} else {
			ctx.status(401);
		}
	};
	
	@Override
	public void addRoutes(Javalin app) {
		app.get("/reimbursements", this.getAllReimbursement);
		app.get("/reimbursements/:reimbursement", this.getReimbursement);
		app.post("/reimbursements", this.addReimbursement);
		app.put("/reimbursements", this.updateReimbursement);
		app.delete("/reimbursements/:reimbursement", this.deleteReimbursement);
	}

}
