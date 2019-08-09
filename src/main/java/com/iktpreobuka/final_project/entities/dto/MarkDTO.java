package com.iktpreobuka.final_project.entities.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.enumerations.EMarkValue;
import com.iktpreobuka.final_project.util.View;

@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class MarkDTO {

	@JsonView(View.Private.class)
	private Long id;
	
	@JsonView(View.Public.class)
	private ProfessorDTO professor;
	
	@JsonView(View.Public.class)
	private PupilDTO pupil;
	
	@JsonView(View.Public.class)
	private SubjectDTO subject;
	
	@JsonView(View.Public.class)
	private SchoolClassDTO schoolClass;
	
	@JsonView(View.Public.class)
	private ActivityDTO activity;
	
	
	@JsonView(View.Public.class)
	@NotNull(message = "Value must be provided.")
	private EMarkValue value;
	
	@JsonView(View.Public.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone="Europe/Belgrade")
	private LocalDate date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ProfessorDTO getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}

	public PupilDTO getPupil() {
		return pupil;
	}

	public void setPupil(PupilDTO pupil) {
		this.pupil = pupil;
	}

	public SchoolClassDTO getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClassDTO schoolClass) {
		this.schoolClass = schoolClass;
	}

	public ActivityDTO getActivity() {
		return activity;
	}

	public void setActivity(ActivityDTO activity) {
		this.activity = activity;
	}

	public EMarkValue getValue() {
		return value;
	}

	public void setValue(EMarkValue value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public MarkDTO() {
		super();
	}

	public MarkDTO(@NotNull(message = "Professor must be provided.") ProfessorDTO professor,
			@NotNull(message = "Pupil must be provided.") PupilDTO pupil,
			@NotNull(message = "Subject must be provided.") SubjectDTO subject,
			@NotNull(message = "SchoolClass must be provided.") SchoolClassDTO schoolClass,
			@NotNull(message = "Activity must be provided.") ActivityDTO activity,
			@NotNull(message = "Value must be provided.") EMarkValue value,
			@NotNull(message = "Date must be provided.") LocalDate date) {
		super();
		this.professor = professor;
		this.pupil = pupil;
		this.subject = subject;
		this.schoolClass = schoolClass;
		this.activity = activity;
		this.value = value;
		this.date = date;
	}
	

	public MarkDTO(Long id, ProfessorDTO professor, PupilDTO pupil, SubjectDTO subject, SchoolClassDTO schoolClass,
			ActivityDTO activity, @NotNull(message = "Value must be provided.") EMarkValue value, LocalDate date) {
		this.id = id;
		this.professor = professor;
		this.pupil = pupil;
		this.subject = subject;
		this.schoolClass = schoolClass;
		this.activity = activity;
		this.value = value;
		this.date = date;
	}

	public MarkDTO(@NotNull(message = "Activity must be provided.") ActivityDTO activity,
			@NotNull(message = "Value must be provided.") EMarkValue value) {
		super();
		this.activity = activity;
		this.value = value;
	}
	


	public MarkDTO(Long id, ActivityDTO activity, @NotNull(message = "Value must be provided.") EMarkValue value,
			LocalDate date) {
		this.id = id;
		this.activity = activity;
		this.value = value;
		this.date = date;
	}

	public MarkDTO(ActivityDTO activity, @NotNull(message = "Value must be provided.") EMarkValue value,
			LocalDate date) {
		super();
		this.activity = activity;
		this.value = value;
		this.date = date;
	}

	public MarkDTO(ProfessorDTO professor, SubjectDTO subject, SchoolClassDTO schoolClass, ActivityDTO activity,
			@NotNull(message = "Value must be provided.") EMarkValue value, LocalDate date) {
		super();
		this.professor = professor;
		this.subject = subject;
		this.schoolClass = schoolClass;
		this.activity = activity;
		this.value = value;
		this.date = date;
	}

	public MarkDTO(Long id, ProfessorDTO professor, SubjectDTO subject, SchoolClassDTO schoolClass,
			ActivityDTO activity, @NotNull(message = "Value must be provided.") EMarkValue value, LocalDate date) {
		this.id = id;
		this.professor = professor;
		this.subject = subject;
		this.schoolClass = schoolClass;
		this.activity = activity;
		this.value = value;
		this.date = date;
	}

	

	
	
	
	
}
