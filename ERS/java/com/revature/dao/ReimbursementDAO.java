package com.revature.dao;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.Reimbursement;

public interface ReimbursementDAO {

	

	//creates a new reimbursement record 
	public void createReimbursement(double amount, String description, int type, int author);
	public void updateReimbursement(int id, int status, int resolverID); //Validation: ID should be checked to to see if it exists in the table BEFORE function call
	public boolean checkSingleRecord(int id);//Checks the existence of record based on its unique identifier
	List<Reimbursement> mngGetAll();
	List<Reimbursement> empGetAll(int userID);//get all reimbursements that belong to current employee
	List<Reimbursement> mngGetApproved();
	List<Reimbursement> empGetApproved(int userID);//get all reimbursements that belong to current employee that are "Approved"
	List<Reimbursement> mngGetDeclined();
	List<Reimbursement> empGetDeclined(int userID);//Get all reimbursements that belong to the current employee that are "Declined"
	List<Reimbursement> mngGetPending();
	List<Reimbursement> empGetPending(int userID);//Get all reimbursements that belong to the current employee that are "Pending"


	
	
	
	
	
	
}
