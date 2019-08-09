package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.enumerations.EGrade;
import com.iktpreobuka.final_project.util.View;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class SchoolClassDTO {

	@JsonView(View.Private.class)
	private Long id;
	
	@JsonView(View.Admin.class)
	@NotBlank(message = "Code must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Code must not contain white space.")
	@Size(min=1, max=30, message = "Code must be between {min} and {max} characters long.")
	private String code;
	
	
	@JsonView(View.Public.class)
	@NotNull(message = "Grade must be provided.")
	private EGrade grade;
	
	@JsonView(View.Public.class)
	private SemestarDTO semestarDTO;
	
	@JsonView(View.Public.class)
	@NotBlank(message = "Name must be provided.")
	@Pattern(regexp = "^\\S*$", message = "Name must not contain white space.")
	@Size(min=1, max=30, message = "Name must be between {min} and {max} characters long.")
	private String name;
	
	@JsonView(View.Private.class)
	private List<PupilDTO> pupilsAttendingClass;




	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EGrade getGrade() {
		return grade;
	}

	public void setGrade(EGrade grade) {
		this.grade = grade;
	}

	
	public SemestarDTO getSemestarDTO() {
		return semestarDTO;
	}

	public void setSemestarDTO(SemestarDTO semestarDTO) {
		this.semestarDTO = semestarDTO;
	}

	
	public List<PupilDTO> getPupilsAttendingClass() {
		return pupilsAttendingClass;
	}

	public void setPupilsAttendingClass(List<PupilDTO> pupilsAttendingClass) {
		this.pupilsAttendingClass = pupilsAttendingClass;
	}

	public SchoolClassDTO() {
		super();
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO,
			List<PupilDTO> pupilsAttendingClass) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.pupilsAttendingClass = pupilsAttendingClass;
	}
	

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO, String name,
			List<PupilDTO> pupilsAttendingClass) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.name = name;
		this.pupilsAttendingClass = pupilsAttendingClass;
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade) {
		super();
		this.code = code;
		this.grade = grade;
	}
	
	

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, String name) {
		super();
		this.code = code;
		this.grade = grade;
		this.name = name;
	}

	public SchoolClassDTO(
			@NotNull(message = "Code must be provided.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO, String name) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.name = name;
	}

	public SchoolClassDTO(Long id,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 1, max = 30, message = "Name must be between {min} and {max} characters long.") String name) {
		this.id = id;
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.name = name;
	}

	public SchoolClassDTO(Long id,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade, SemestarDTO semestarDTO,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 1, max = 30, message = "Name must be between {min} and {max} characters long.") String name,
			List<PupilDTO> pupilsAttendingClass) {
		this.id = id;
		this.code = code;
		this.grade = grade;
		this.semestarDTO = semestarDTO;
		this.name = name;
		this.pupilsAttendingClass = pupilsAttendingClass;
	}

	public SchoolClassDTO(Long id,
			@NotBlank(message = "Code must be provided.") @Pattern(regexp = "^\\S*$", message = "Code must not contain white space.") @Size(min = 1, max = 30, message = "Code must be between {min} and {max} characters long.") String code,
			@NotNull(message = "Grade must be provided.") EGrade grade,
			@NotBlank(message = "Name must be provided.") @Pattern(regexp = "^\\S*$", message = "Name must not contain white space.") @Size(min = 1, max = 30, message = "Name must be between {min} and {max} characters long.") String name) {
		this.id = id;
		this.code = code;
		this.grade = grade;
		this.name = name;
	}


	




	
	
	
}
