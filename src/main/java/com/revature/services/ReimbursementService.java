package com.revature.services;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;
import com.revature.repos.ReimbursementDAO;
import com.revature.repos.ReimbursementDAOImpl;

public class ReimbursementService {

	private ReimbursementDAO reimbursementDao = new ReimbursementDAOImpl();
	private UserService userService = new UserService();
	
	public List<Reimbursement> getAllReimbursements() {
		return reimbursementDao.findAllReimbursements();
	}
	
	public Reimbursement getReimbursement(int id) {
		Reimbursement reimbursement = reimbursementDao.findByID(id);
		if (reimbursement != null) {
			return reimbursement;
		} else {
			return new Reimbursement();
		}
	}
	
	
	public boolean addReimbursement(Reimbursement reimbursement) {
		User user = userService.getByUsername(reimbursement.getAuthor().getUsername());
		reimbursement.setAuthor(user);
		return reimbursementDao.addReimbursement(reimbursement);
	}
	
	public boolean updateReimbursement(Reimbursement reimbursement) {
		User author = userService.getByUsername(reimbursement.getAuthor().getUsername());
		reimbursement.setAuthor(author);
		if (reimbursement.getResolver() != null) {
			User resolver = userService.getByUsername(reimbursement.getResolver().getUsername());
			reimbursement.setResolver(resolver);
		}
		return reimbursementDao.updateReimbursement(reimbursement);
	}

	public boolean deleteReimbursement(int id) {
		Reimbursement reimbursement = getReimbursement(id);
		return reimbursementDao.deleteReimbursement(reimbursement);
	}
	
}
