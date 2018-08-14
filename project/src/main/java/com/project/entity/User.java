package com.project.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.project.entity.Role;

@Entity
@Table(name="user")
public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private int id;
	@Column(name = "username", nullable = false, length = 45)
	private String username;
	@Column(name = "emer", nullable = false, length = 45)
	private String emer;
	@Column(name = "mbiemer", nullable = false, length = 45)
	private String mbiemer;
	@Column(name = "password", nullable = false, length = 500)
	private String password;
	@Column(name = "active", nullable = false)
	private boolean active;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "employee_role", nullable = false)
	private Role role;
	@ManyToOne( fetch = FetchType.LAZY)
	@JoinColumn(name = "managed_by")
	private User managedBy;
	
	public User() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public User getManagedBy() {
		return managedBy;
	}

	public void setManagedBy(User managedBy) {
		this.managedBy = managedBy;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", emer=" + emer + ", mbiemer=" + mbiemer + ", password="
				+ password + ", active=" + active + ", role=" + role + ", managedBy=" + managedBy + "]";
	}
	
}
