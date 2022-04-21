package br.com.cerc.holerite.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity
public class ApplicationUser {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotEmpty(message = "The field 'name' cannot be empty")
	private String username;
	@NotEmpty(message = "The field 'password' cannot be empty")
	private String password;
	@NotEmpty(message = "The field 'role' cannot be empty")
	private String role;
	
	public ApplicationUser() {
		
	}
	
	public ApplicationUser(ApplicationUser applicationUser) {
		this.username = applicationUser.username;
		this.password = applicationUser.password;
	}
	
	public ApplicationUser(Long id, String username, String password, String role) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public static class Builder {
		private Long id;
		private String username;
		private String password;
		private String role;
		
		public Builder id(Long id) {
			this.id = id;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}
		
		public Builder password(String password) {
			this.password = password;
			return this;
		}
		
		public Builder role(String role) {
			this.role = role;
			return this;
		}
		
		public ApplicationUser build() {
			return new ApplicationUser(id, username, password, role);
		}
	}
	
}