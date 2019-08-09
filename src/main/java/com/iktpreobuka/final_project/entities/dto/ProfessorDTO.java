package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })

public class ProfessorDTO {

	//@JsonIgnore
	@JsonView(View.Private.class)
	private Long id;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Surname must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.")
	@Size(min=2, max=30, message = "Surname must be between {min} and {max} characters long.")
	private String surname;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
		
	@JsonView(View.Public.class)
	private List<SubjectDTO> subjects;
	
	@JsonView(View.Private.class)
	private UserDTO professorUser;
	
	

	public UserDTO getProfessorUser() {
		return professorUser;
	}

	public void setProfessorUser(UserDTO professorUser) {
		this.professorUser = professorUser;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public ProfessorDTO() {
		super();
	}

	public List<SubjectDTO> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<SubjectDTO> subjects) {
		this.subjects = subjects;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO professorUser) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.professorUser = professorUser;
	}

	public ProfessorDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SubjectDTO> subjects) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.subjects = subjects;
	}

	public ProfessorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SubjectDTO> subjects) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.subjects = subjects;
	}

	public ProfessorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			UserDTO professorUser) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.professorUser = professorUser;
	}

	
	public ProfessorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public ProfessorDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SubjectDTO> subjects, UserDTO professorUser) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.subjects = subjects;
		this.professorUser = professorUser;
	}
	
	
	
	
}
