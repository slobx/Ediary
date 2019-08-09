package com.iktpreobuka.final_project.entities.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

@JsonView(View.Admin.class)
public class AdministratorDTO {

	
	private Long id;
	
	
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	
	@NotBlank(message = "Surname must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.")
	@Size(min=2, max=30, message = "Surame must be between {min} and {max} characters long.")
	private String surname;
	
	
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	

	private UserDTO adminUser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public UserDTO getAdminUser() {
		return adminUser;
	}

	
	public void setAdminUser(UserDTO adminUser) {
		this.adminUser = adminUser;
	}

	public AdministratorDTO() {
	}

	public AdministratorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO adminUser) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.adminUser = adminUser;
	}

	public AdministratorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
	}
	
	
}
