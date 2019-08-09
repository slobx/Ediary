package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class SubjectDTO {

	@JsonView(View.Private.class)
	private Long id;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=2, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;

	@JsonIgnore
	private List<ProfessorDTO> professors;
	
	@JsonView(View.Private.class)
	private List<SchoolClassDTO> schoolClasses;
	
	
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<ProfessorDTO> getProfessors() {
		return professors;
	}

	public void setProfessors(List<ProfessorDTO> professors) {
		this.professors = professors;
	}

	public List<SchoolClassDTO> getSchoolClasses() {
		return schoolClasses;
	}

	public void setSchoolClasses(List<SchoolClassDTO> schoolClasses) {
		this.schoolClasses = schoolClasses;
	}

	public SubjectDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.code = code;
	}

	public SubjectDTO() {
		super();
	}

	public SubjectDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		this.id = id;
		this.name = name;
		this.code = code;
	}

	public SubjectDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SchoolClassDTO> schoolClasses) {
		this.id = id;
		this.name = name;
		this.code = code;
		this.schoolClasses = schoolClasses;
	}
	
	
}
