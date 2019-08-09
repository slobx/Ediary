package com.iktpreobuka.final_project.entities.dto;



import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class ParentDTO {

	
	//@JsonIgnore
	@JsonView(View.Admin.class)
	private Long id;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Surname must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.")
	@Size(min=2, max=30, message = "Surame must be between {min} and {max} characters long.")
	private String surname;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	@JsonView(View.Admin.class)
	private UserDTO parentUser;
	
	//@JsonIgnore
	@JsonView(View.Public.class)
	@JsonBackReference("parentpupils")	
	private List<PupilDTO> parent_pupils;



	public UserDTO getParentUser() {
		return parentUser;
	}


	public void setParentUser(UserDTO parentUser) {
		this.parentUser = parentUser;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


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


	







	public List<PupilDTO> getParent_pupils() {
		return parent_pupils;
	}


	public void setParent_pupils(List<PupilDTO> parent_pupils) {
		this.parent_pupils = parent_pupils;
	}


	public ParentDTO(String name, String surname, String code) {
		super();
		this.name = name;
		this.surname = surname;
		//this.email = email;
		this.code = code;
	}


	
//
//	public ParentDTO(
//			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
//			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
//			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
//			UserDTO parentUser) {
//		super();
//		this.name = name;
//		this.surname = surname;
//		this.code = code;
//		this.parentUser = parentUser;
//	}

	

	public ParentDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
	}


	public ParentDTO(
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO parentUser) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.parentUser = parentUser;
	}


	public ParentDTO() {
		super();
	}


	public ParentDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO parentUser) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.parentUser = parentUser;
	}


	public ParentDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surame must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<PupilDTO> parent_pupils) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.parent_pupils = parent_pupils;
	}


	
	
	
	
	
}
