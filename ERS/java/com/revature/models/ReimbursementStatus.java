package com.revature.models;

//POJO Representing the Reimbursement status table
public class ReimbursementStatus {

	private int statusID;
	private String status;
	
	//Constructors
	public ReimbursementStatus() {}
	
	public ReimbursementStatus(int id ,String stat) {
		
		this.statusID= id;
		this.status = stat;
	}

	
	
	
	
	//GETTERS AND SETTERS
	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + statusID;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReimbursementStatus other = (ReimbursementStatus) obj;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (statusID != other.statusID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursmentStatus [statusID=" + statusID + ", status=" + status + "]";
	}

	
	
	
	
	

	
}
