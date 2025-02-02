package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.models.Employee;
import com.revature.models.Reimbursement;
import com.revature.services.EmployeeServices;
import com.revature.util.ConnectionFactory;

public class ReimbursementDAOImpl implements ReimbursementDAO {
	
	Logger log = LogManager.getLogger(ReimbursementDAOImpl.class);
	
	@Override
	public void createReimbursement(double amount, String description, int type, int author ) {
		log.info("Spinning up the method to create a reimbursement record");
			
		//establish a connection to the database (auto commit:off)
		try(Connection conn = ConnectionFactory.getInstance().getConnection();){
			conn.setAutoCommit(false);
			
					
		//prepared statement -> create the SQL INSERT statement to insert a new record into the table
		String sql = "INSERT INTO REIMBURSEMENT (ReImb_Amount, ReImb_Author, ReImb_Type_ID,  ReImb_Description ) VALUES (?,?,?,?)";
		
		//prepare the SQL call
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		//set the values > double amt,  Employee author , int type< 
		pstmt.setDouble(1, amount);
		pstmt.setInt(2, author);
		pstmt.setInt(3, type);
		pstmt.setString(4, description);
		
		
		//execute the query 
		ResultSet rs = pstmt.executeQuery();

		//commit changes	
		conn.commit();
		}catch(SQLException sql) {
			log.error("Inside Create function");
			sql.printStackTrace();
		}

		
	}

	@Override
	public void updateReimbursement(int id, int status, int resolverID) {
		log.info("Spinning up the method to update a reimbursement record");
		//Financial manager will be able to update the reimbursement table status column
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {
			conn.setAutoCommit(false);
			
			// prepared statement -> create the SQL INSERT statement to insert a new record
			// into the table
			String sql = "UPDATE REIMBURSEMENT SET ReImb_Status_ID = ?, REIMB_RESOLVER = ? WHERE ReImb_ID = ?";                       
			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values 
			pstmt.setInt(1, status);
			pstmt.setInt(2, resolverID);
			pstmt.setInt(3, id);	
			
			// execute the query
			ResultSet rs = pstmt.executeQuery();
			
			//commit changes
			conn.commit();
		} catch (SQLException sql) {
			log.error("Inside Update Function");
			sql.printStackTrace();
		}
	}


	@Override
	public List<Reimbursement> mngGetAll() {
		log.info("Spinning up the method to get all reimbursements(mng)");
		List<Reimbursement> records = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			//Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM REIMBURSEMENT ORDER BY ReImb_ID ASC";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);   
		
			//loop through each row, assigning each table field to is corresponding object field
			while(rs.next()) {
				Reimbursement rec = new Reimbursement();
				rec.setReimbID(rs.getInt(1));
				rec.setAmount(rs.getDouble(2));
				rec.setSubmitted(rs.getTimestamp(3));
				rec.setResolved(rs.getTimestamp(4));
				rec.setDescription(rs.getString(5));
				//Receipt(would go here) not included
				rec.setAuthor(rs.getInt(7));
				rec.setResolver(rs.getInt(8));
				rec.setStatusID(rs.getInt(9));
				rec.setTypeID(rs.getInt(10));
				
				//appended populated record to the list of records to be returned
				records.add(rec);
				
			}

		} catch (SQLException sql) {
			log.error("Inside mngGetAll function");
			sql.printStackTrace();
		}
		return records;
	}
	

	@Override
	public List<Reimbursement> empGetAll(int userID) {
		log.info("Spinning up the method to get all reimbursements(emp)");
		
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		//System.out.println("Spinning up the method to get all reimbursements");

		// establish a connection to the database (auto commit:off)
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// prepared statement -> create the SQL INSERT statement to insert a new record
			// into the table
			String sql = "SELECT * FROM REIMBURSEMENT WHERE REIMB_AUTHOR = ? ORDER BY ReImb_ID ASC";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);

			// execute the query
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReimbID(rs.getInt("REIMB_ID"));
				r.setAmount(rs.getInt("REIMB_AMOUNT"));
				r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
				r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
				r.setDescription(rs.getString("REIMB_DESCRIPTION"));
				r.setAuthor(rs.getInt("REIMB_AUTHOR"));
				r.setResolver(rs.getInt("REIMB_RESOLVER"));
				r.setStatusID(rs.getInt("REIMB_STATUS_ID"));
				r.setTypeID(rs.getInt("REIMB_TYPE_ID"));
				reimbursements.add(r);
			}

		} catch (SQLException sql) {
			log.error("Inside empGetAll Function ");
			sql.printStackTrace();
		}

		return reimbursements;
	}
	
	
	@Override
	public List<Reimbursement> mngGetApproved() {
		
		log.info("Spinning up the method to get all Approved reimbursements(mng)");
		List<Reimbursement> records = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			//Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM REIMBURSEMENT WHERE ReImb_Status_ID = 3";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);   
		
			//loop through each row, assigning each table field to is corresponding object field
			while(rs.next()) {
				Reimbursement rec = new Reimbursement();
				rec.setReimbID(rs.getInt(1));
				rec.setAmount(rs.getDouble(2));
				rec.setSubmitted(rs.getTimestamp(3));
				rec.setResolved(rs.getTimestamp(4));
				rec.setDescription(rs.getString(5));
				//Receipt(would go here) not included
				rec.setAuthor(rs.getInt(7));
				rec.setResolver(rs.getInt(8));
				rec.setStatusID(rs.getInt(9));
				rec.setTypeID(rs.getInt(10));
				
				//appended populated record to the list of records to be returned
				records.add(rec);
				
			}

		} catch (SQLException sql) {
			log.error("Inside mngGetApproved Function");
			sql.printStackTrace();
		}
		return records;
	}

	
	@Override
	public List<Reimbursement> empGetApproved(int userID) {
		// create a new object ()
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		log.info("Spinning up the method to get all Approveed reimbursements(emp)");

		// establish a connection to the database (auto commit:off)
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// prepared statement -> create the SQL INSERT statement to insert a new record
			// into the table
			String sql = "SELECT * FROM REIMBURSEMENT WHERE (REIMB_STATUS_ID = 3 AND AND REIMB_AUTHOR = ?)";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);

			// execute the query
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReimbID(rs.getInt("REIMB_ID"));
				r.setAmount(rs.getInt("REIMB_AMOUNT"));
				r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
				r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
				r.setDescription(rs.getString("REIMB_DESCRIPTION"));
				r.setAuthor(rs.getInt("REIMB_AUTHOR"));
				r.setResolver(rs.getInt("REIMB_RESOLVER"));
				r.setStatusID(rs.getInt("REIMB_STATUS_ID"));
				r.setTypeID(rs.getInt("REIMB_TYPE_ID"));
				reimbursements.add(r);
			}

		} catch (SQLException sql) {
			log.error("Inside empGetApproved Function");
			sql.printStackTrace();
		}

		return reimbursements;

	}
	
	@Override
	public List<Reimbursement> mngGetDeclined() {
		log.info("Spinning up the method to get all declined reimbursements(mng)");
		
		List<Reimbursement> records = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			//Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM REIMBURSEMENT WHERE ReImb_Status_ID = 2 ";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);   
		
			//loop through each row, assigning each table field to is corresponding object field
			while(rs.next()) {
				Reimbursement rec = new Reimbursement();
				rec.setReimbID(rs.getInt(1));
				rec.setAmount(rs.getDouble(2));
				rec.setSubmitted(rs.getTimestamp(3));
				rec.setResolved(rs.getTimestamp(4));
				rec.setDescription(rs.getString(5));
				//Receipt(would go here) not included
				rec.setAuthor(rs.getInt(7));
				rec.setResolver(rs.getInt(8));
				rec.setStatusID(rs.getInt(9));
				rec.setTypeID(rs.getInt(10));
				
				//appended populated record to the list of records to be returned
				records.add(rec);
				
			}

		} catch (SQLException sql) {
			log.error("Inside mngGetDeclined Function");
			sql.printStackTrace();
		}
		return records;
	}

	@Override
 	public List<Reimbursement> empGetDeclined(int userID) {
		// create a new object ()
		List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
		log.info("Spinning up the method to get all Declined Reimbursements(emp)");

		// establish a connection to the database (auto commit:off)
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// prepared statement -> create the SQL INSERT statement to insert a new record
			// into the table
			String sql = "SELECT * FROM REIMBURSEMENT WHERE (REIMB_STATUS_ID = 2 AND REIMB_AUTHOR = ?)";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, userID);

			// execute the query
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Reimbursement r = new Reimbursement();

				r.setReimbID(rs.getInt("REIMB_ID"));
				r.setAmount(rs.getInt("REIMB_AMOUNT"));
				r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
				r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
				r.setDescription(rs.getString("REIMB_DESCRIPTION"));
				r.setAuthor(rs.getInt("REIMB_AUTHOR"));
				r.setResolver(rs.getInt("REIMB_RESOLVER"));
				r.setStatusID(rs.getInt("REIMB_STATUS_ID"));
				r.setTypeID(rs.getInt("REIMB_TYPE_ID"));
				reimbursements.add(r);
			}

		} catch (SQLException sql) {
			log.error("Inside empGetDeclined Function");
			sql.printStackTrace();
		}

		return reimbursements;
	}
	
	@Override
	public List<Reimbursement> mngGetPending() {
		log.info("Spinning up the method to get all Pending reimbursements(mng)");

	
		List<Reimbursement> records = new ArrayList<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			//Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM REIMBURSEMENT WHERE ReImb_Status_ID = 1";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);   
		
			//loop through each row, assigning each table field to is corresponding object field
			while(rs.next()) {
				Reimbursement rec = new Reimbursement();
				rec.setReimbID(rs.getInt(1));
				rec.setAmount(rs.getDouble(2));
				rec.setSubmitted(rs.getTimestamp(3));
				rec.setResolved(rs.getTimestamp(4));
				rec.setDescription(rs.getString(5));
				//Receipt(would go here) not included
				rec.setAuthor(rs.getInt(7));
				rec.setResolver(rs.getInt(8));
				rec.setStatusID(rs.getInt(9));
				rec.setTypeID(rs.getInt(10));
				
				//appended populated record to the list of records to be returned
				records.add(rec);
				
			}

		} catch (SQLException sql) {
			log.error("Inside mngGetPending Function");
			sql.printStackTrace();
		}
		return records;
	}

	
	
	@Override
	public List<Reimbursement> empGetPending(int userID) {
		// create a new object ()
				List<Reimbursement> reimbursements = new ArrayList<Reimbursement>();
				log.info("Spinning up the method to get all Pending reimbursements(emp)");
				

				// establish a connection to the database (auto commit:off)
				try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

					// prepared statement -> create the SQL INSERT statement to insert a new record
					// into the table
					String sql = "SELECT * FROM REIMBURSEMENT WHERE (REIMB_STATUS_ID = 1 AND REIMB_AUTHOR = ?)";

					// prepare the SQL call
					PreparedStatement pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, userID);

					// execute the query
					ResultSet rs = pstmt.executeQuery();
					while (rs.next()) {
						Reimbursement r = new Reimbursement();

						r.setReimbID(rs.getInt("REIMB_ID"));
						r.setAmount(rs.getInt("REIMB_AMOUNT"));
						r.setSubmitted(rs.getTimestamp("REIMB_SUBMITTED"));
						r.setResolved(rs.getTimestamp("REIMB_RESOLVED"));
						r.setDescription(rs.getString("REIMB_DESCRIPTION"));
						r.setAuthor(rs.getInt("REIMB_AUTHOR"));
						r.setResolver(rs.getInt("REIMB_RESOLVER"));
						r.setStatusID(rs.getInt("REIMB_STATUS_ID"));
						r.setTypeID(rs.getInt("REIMB_TYPE_ID"));
						reimbursements.add(r);
					}

				} catch (SQLException sql) {
					log.error("Inside empGetPending Function");
					sql.printStackTrace();
				}

				return reimbursements;
	}

	@Override
	public boolean checkSingleRecord(int id) {
		log.info("Checking the existance of desired record...");
		// check to see if their exists a record that corresponds with the inputed ID
		try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

			// Construct & execute the corresponding SELECT statement
			String sql = "SELECT * FROM REIMBURSEMENT WHERE REIMB_ID = ?";

			// prepare the SQL call
			PreparedStatement pstmt = conn.prepareStatement(sql);

			// set the values
			pstmt.setInt(1, id);

			ResultSet rs = pstmt.executeQuery();

			// check to see credentials match with the database
			int count = 0;
			while (rs.next()) {
				++count;
			}
			if (count == 1) {
				log.info("Reimbursement Record Exists");
				return true;
			} else {
				log.warn("Reimbursement Record Not Found");
				return false;
			}
		} catch (SQLException sql) {
			log.error("Inside checkSingleRecord function");
			sql.printStackTrace();
		}
		return false;
	}
	
}
