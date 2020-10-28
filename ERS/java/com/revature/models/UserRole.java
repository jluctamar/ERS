package com.revature.models;

//POJO representing the User roles table
public class UserRole {

	
	private int userRoleID;
	private String userRole;
	
	//Constructors
	public UserRole() {}
	
	public UserRole(int id ,String stat) {
		
		this.userRoleID= id;
		this.userRole = stat;
	}

	
	
	//GETTERS IN SETTERS
	public int getUserRoleID() {
		return userRoleID;
	}

	public void setUserRoleID(int userRoleID) {
		this.userRoleID = userRoleID;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userRole == null) ? 0 : userRole.hashCode());
		result = prime * result + userRoleID;
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
		UserRole other = (UserRole) obj;
		if (userRole == null) {
			if (other.userRole != null)
				return false;
		} else if (!userRole.equals(other.userRole))
			return false;
		if (userRoleID != other.userRoleID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserRole [userRoleID=" + userRoleID + ", userRole=" + userRole + "]";
	}
	
	
	
	
	

}
