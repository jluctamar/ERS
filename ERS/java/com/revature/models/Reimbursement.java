package com.revature.models;

import java.sql.Timestamp;

//POJO representing the reimbursement table
public class Reimbursement {
	
	private int reimbID;
	private double amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	//private     /*data type for receipt*/     description;
	private int author;
	private int resolver;
	private int statusID;
	private int typeID;
	
	
	public Reimbursement() {}
	
	//called when creating a new record
	public Reimbursement(double amount, String description,  int typeID, int author) {
		super();
		this.amount = amount;
		this.description = description;
		this.author = author;
		this.typeID = typeID;
	}

	
	public Reimbursement(int reimbID, double amount, Timestamp submitted, Timestamp resolved, String description,
			int author, int resolver, int statusID, int typeID) {
		super();
		this.reimbID = reimbID;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.author = author;
		this.resolver = resolver;
		this.statusID = statusID;
		this.typeID = typeID;
	}


	
	
	
	public int getReimbID() {
		return reimbID;
	}

	public void setReimbID(int reimbID) {
		this.reimbID = reimbID;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

	public int getTypeID() {
		return typeID;
	}

	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + author;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + reimbID;
		result = prime * result + ((resolved == null) ? 0 : resolved.hashCode());
		result = prime * result + resolver;
		result = prime * result + statusID;
		result = prime * result + ((submitted == null) ? 0 : submitted.hashCode());
		result = prime * result + typeID;
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
		Reimbursement other = (Reimbursement) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (author != other.author)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (reimbID != other.reimbID)
			return false;
		if (resolved == null) {
			if (other.resolved != null)
				return false;
		} else if (!resolved.equals(other.resolved))
			return false;
		if (resolver != other.resolver)
			return false;
		if (statusID != other.statusID)
			return false;
		if (submitted == null) {
			if (other.submitted != null)
				return false;
		} else if (!submitted.equals(other.submitted))
			return false;
		if (typeID != other.typeID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Reimbursement [reimbID=" + reimbID + ", amount=" + amount + ", submitted=" + submitted + ", resolved="
				+ resolved + ", description=" + description + ", author=" + author + ", resolver=" + resolver
				+ ", statusID=" + statusID + ", typeID=" + typeID + "]";
	}


	
}
