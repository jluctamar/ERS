package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dao.ReimbursementDAO;
import com.revature.dao.ReimbursementDAOImpl;
import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.servlets.LoadViewServlet;

//Service class to access the DAO methods 
public class ReimbursementService {

	Logger log = LogManager.getLogger(ReimbursementService.class);
	
	private ReimbursementDAO reimbDAO = new ReimbursementDAOImpl();

	
	public void createReimbursement(double amount, int author, int type, String description) {
		 reimbDAO.createReimbursement(amount, description, type, author);
	}
	
	public void updateReimbursement(Employee mng, int reimbID, int status) {
		
		
		//Condition: User must be a manager; record must exist
		if((mng.getUserRoleID() == 2) && (reimbDAO.checkSingleRecord(reimbID))){		
			reimbDAO.updateReimbursement(reimbID, status, mng.getEmployeeID());
			log.info("Update Successfull");
		}else {
			log.info("Update Unsuccessful");
		}
	}
	
	
/*	//depending on the users role return ALL relevant records
	public List<Reimbursement> recordSelector(Employee user) {
				
		//depending on the users role return ALL relevant records(views to be organized in JavaScript)
		if(user.getUserRoleID()==2) {
			return mngGetAll();	
		}else {
			return empGetAll(user.getEmployeeID());
		}
		
	}
	*/
	
	
	

	public List<Reimbursement> mngGetAll(){
		return reimbDAO.mngGetAll();
	}
	public List<Reimbursement> empGetAll(int userID){
		return reimbDAO.empGetAll(userID);
	}
	public List<Reimbursement> mngGetApproved(){
		return reimbDAO.mngGetApproved();
	}
	public List<Reimbursement> empGetApproved(int userID){
		return reimbDAO.empGetApproved(userID);
	}
	
	public List<Reimbursement> mngGetDeclined(){
		return reimbDAO.mngGetDeclined();
	}
	public List<Reimbursement> empGetDeclined(int userID){
		return reimbDAO.empGetDeclined(userID);
	}
	public List<Reimbursement> mngGetPending(){
		return reimbDAO.mngGetPending();
	}
	public List<Reimbursement> empGetPending(int userID){
		return reimbDAO.empGetPending(userID);
	}
	

}
