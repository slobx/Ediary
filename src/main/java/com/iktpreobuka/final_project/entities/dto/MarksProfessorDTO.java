package com.iktpreobuka.final_project.entities.dto;


import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class MarksProfessorDTO {

	@JsonView(View.Public.class)
	private ProfessorDTO professor;
	
	@JsonView(View.Public.class)
	private PupilMarkDTO pupil;
	
	@JsonView(View.Public.class)
	private SubjectDTO subject;
	@JsonView(View.Public.class)
	private SchoolClassDTO schoolClass;
	

	public ProfessorDTO getProfessor() {
		return professor;
	}

	public void setProfessor(ProfessorDTO professor) {
		this.professor = professor;
	}



	
	

	public PupilMarkDTO getPupil() {
		return pupil;
	}

	public void setPupil(PupilMarkDTO pupil) {
		this.pupil = pupil;
	}

	public SchoolClassDTO getSchoolClass() {
		return schoolClass;
	}

	public void setSchoolClass(SchoolClassDTO schoolClass) {
		this.schoolClass = schoolClass;
	}

	public SubjectDTO getSubject() {
		return subject;
	}

	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}

	public MarksProfessorDTO() {
		super();
	}

	public MarksProfessorDTO(ProfessorDTO professor, PupilMarkDTO pupil, SubjectDTO subject,
			SchoolClassDTO schoolClass) {
		super();
		this.professor = professor;
		this.pupil = pupil;
		this.subject = subject;
		this.schoolClass = schoolClass;
	}

	

	
	
	
	
}
