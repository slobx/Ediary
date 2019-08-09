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
public class PupilDTO {

	
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
	@NotNull(message = "JMBG must be provided.")
	@Pattern(regexp="^[0-9]{13}$")
	private String jmbg;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	@JsonView(View.Public.class)
	private List<SchoolClassDTO> schoolClassDTO;
	
//	@JsonIgnore
//	@JsonBackReference("pupilsclasses")
//	private List<PupilsInClass> schoolClasses;
	
	
	@JsonView(View.Public.class)
//	@JsonManagedReference("parentpupils")
	private ParentDTO parent;
	
	@JsonView(View.Admin.class)
	private UserDTO pupilUser;
	
	
	
	


	public UserDTO getPupilUser() {
		return pupilUser;
	}

	public void setPupilUser(UserDTO pupilUser) {
		this.pupilUser = pupilUser;
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

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

//	public List<PupilsInClass> getSchoolClasses() {
//		return schoolClasses;
//	}
//
//	public void setSchoolClasses(List<PupilsInClass> schoolClasses) {
//		this.schoolClasses = schoolClasses;
//	}


	public ParentDTO getParent() {
		return parent;
	}

	public void setParent(ParentDTO parent) {
		this.parent = parent;
	}

	public List<SchoolClassDTO> getSchoolClassDTO() {
		return schoolClassDTO;
	}

	public void setSchoolClassDTO(List<SchoolClassDTO> schoolClassDTO) {
		this.schoolClassDTO = schoolClassDTO;
	}

	public PupilDTO() {
		super();
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			ParentDTO parent) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			ParentDTO parent, List<SchoolClassDTO> schoolClassDTO) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
		this.schoolClassDTO = schoolClassDTO;
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			ParentDTO parent, UserDTO pupilUser) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
		this.pupilUser = pupilUser;
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
	}



	public PupilDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			ParentDTO parent) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
	}

	public PupilDTO(
			@NotNull(message = "Name must be provided.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotNull(message = "Surname must be provided.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public PupilDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
	}

	public PupilDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			List<SchoolClassDTO> schoolClassDTO, ParentDTO parent) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.schoolClassDTO = schoolClassDTO;
		this.parent = parent;
	}

	public PupilDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.code = code;
	}

	public PupilDTO(Long id,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 2, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			@NotBlank(message = "Surname must be provided.") @Pattern(regexp = "^\\S*$", message = "Surname must not contain white space.") @Size(min = 2, max = 30, message = "Surname must be between {min} and {max} characters long.") String surname,
			@NotNull(message = "JMBG must be provided.") @Pattern(regexp = "^[0-9]{13}$") String jmbg,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			ParentDTO parent, UserDTO pupilUser) {
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
		this.pupilUser = pupilUser;
	}

	

	

	


	
	
	
	
	
}
