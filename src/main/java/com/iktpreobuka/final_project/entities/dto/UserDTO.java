package com.iktpreobuka.final_project.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class UserDTO {

	//@JsonIgnore
	@JsonView(View.Admin.class)
	private Long id;
	
	@JsonView(View.Public.class)
	@NotNull(message = "Email must be provided.")
	@Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", 
	message="Email is not valid.")
	private String email;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Password must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Password must not contain white space.")
	@Size(min=5, max=10, message = "Password must be between {min} and {max} characters long.")
	private String password;
	
	
	@JsonView(View.Admin.class)
	private String confirmPassword;
	
	@JsonView(View.Public.class)
	//obicno se stavlja da ima veliko slovo i neki karakter "^[A-Z]+[a-z]+[0-9-\\+]+$"
	@NotBlank(message = "Username must be provided.")
	@Size(min=5, max=15, message = "Username must be between {min} and {max} characters long.")
	@Pattern(regexp = "^\\S*$", message = "Username must not contain white space.")
	private String username;
	
	//@JsonIgnore
	@JsonView(View.Admin.class)
	private RoleDTO role;
	
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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



	

	public RoleDTO getRole() {
		return role;
	}

	public void setRole(RoleDTO role) {
		this.role = role;
	}

	public UserDTO() {
		super();
	}

	public UserDTO(Long id,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username) {
		this.id = id;
		this.username = username;
	}

	public UserDTO(
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Password must be provided.") @Pattern(regexp = "^\\S*$", message = "Password must not contain white space.") @Size(min = 5, max = 10, message = "Password must be between {min} and {max} characters long.") String password,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username,
			RoleDTO role) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public UserDTO(
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username,
			RoleDTO role) {
		super();
		this.email = email;
		this.username = username;
		this.role = role;
	}

	public UserDTO(
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username) {
		this.email = email;
		this.username = username;
	}

	public UserDTO(Long id,
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username,
			RoleDTO role) {
		this.id = id;
		this.email = email;
		this.username = username;
		this.role = role;
	}

	public UserDTO(Long id,
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Password must be provided.") @Pattern(regexp = "^\\S*$", message = "Password must not contain white space.") @Size(min = 5, max = 10, message = "Password must be between {min} and {max} characters long.") String password,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username,
			RoleDTO role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.username = username;
		this.role = role;
	}

	public UserDTO(Long id,
			@NotNull(message = "Email must be provided.") @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Email is not valid.") String email,
			@NotBlank(message = "Password must be provided.") @Pattern(regexp = "^\\S*$", message = "Password must not contain white space.") @Size(min = 5, max = 10, message = "Password must be between {min} and {max} characters long.") String password,
			String confirmPassword,
			@NotBlank(message = "Username must be provided.") @Size(min = 5, max = 15, message = "Username must be between {min} and {max} characters long.") @Pattern(regexp = "^\\S*$", message = "Username must not contain white space.") String username,
			RoleDTO role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.username = username;
		this.role = role;
	}

	
	
	
}
