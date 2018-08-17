package com.project.dto;

import java.io.Serializable;

public class UserDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private int roliId;
	private String emer;
	private String mbiemer;
	private String password;
	private int managedBy;
	private String confirmPassword;
	
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public int getManagedBy() {
		return managedBy;
	}

	public void setManagedBy(int managedBy) {
		this.managedBy = managedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoliId() {
		return roliId;
	}

	public void setRoliId(int roliId) {
		this.roliId = roliId;
	}

	public String getEmer() {
		return emer;
	}

	public void setEmer(String emer) {
		this.emer = emer;
	}

	public String getMbiemer() {
		return mbiemer;
	}

	public void setMbiemer(String mbiemer) {
		this.mbiemer = mbiemer;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((emer == null) ? 0 : emer.hashCode());
		result = prime * result + ((mbiemer == null) ? 0 : mbiemer.hashCode());
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
		UserDto other = (UserDto) obj;
		if (emer == null) {
			if (other.emer != null)
				return false;
		} else if (!emer.equals(other.emer))
			return false;
		if (mbiemer == null) {
			if (other.mbiemer != null)
				return false;
		} else if (!mbiemer.equals(other.mbiemer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserDto [id=" + id + ", username=" + username + ", emer=" + emer + ", mbiemer=" + mbiemer + ", password="
				+ password  + ", role=" + roliId + ", managedBy=" + managedBy + "]";
	}
	
}
