package com.revature.models;

//POJO representing the reimbursement type table
public class ReimbursementType {
	
	
	private int typeID;
	private String type;
	
	
	//Constructors
	public ReimbursementType() {}
	
	public ReimbursementType(int id ,String stat) {
		
		this.typeID= id;
		this.type = stat;
	}

	
	
	
	
	
	//GETTERS AND SETTERS
	public int getID() {
		return typeID;
	}

	public void setID(int iD) {
		typeID = iD;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + typeID;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		ReimbursementType other = (ReimbursementType) obj;
		if (typeID != other.typeID)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReimbursementType [ID=" + typeID + ", type=" + type + "]";
	}

	
	
	
	
	
	
	
	
	
	
	
}
