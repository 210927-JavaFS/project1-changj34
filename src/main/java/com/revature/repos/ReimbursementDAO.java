package com.revature.repos;

import java.util.List;

import com.revature.models.Reimbursement;
import com.revature.models.User;

public interface ReimbursementDAO {

	List<Reimbursement> findAllReimbursements();
	Reimbursement findByID(int id);
	boolean addReimbursement(Reimbursement reimbursement);
	boolean updateReimbursement(Reimbursement reimbursement);
	boolean deleteReimbursement(Reimbursement reimbursement);
	
}
