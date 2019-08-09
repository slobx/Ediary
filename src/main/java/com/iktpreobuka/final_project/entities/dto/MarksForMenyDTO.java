package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class MarksForMenyDTO {

	@JsonView(View.Public.class)
	private ProfessorDTO professor;
	
	@JsonView(View.Public.class)
	private List<PupilMarkDTO> pupil;
	
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
	public List<PupilMarkDTO> getPupil() {
		return pupil;
	}
	public void setPupil(List<PupilMarkDTO> pupil) {
		this.pupil = pupil;
	}
	public SubjectDTO getSubject() {
		return subject;
	}
	public void setSubject(SubjectDTO subject) {
		this.subject = subject;
	}
	public SchoolClassDTO getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(SchoolClassDTO schoolClass) {
		this.schoolClass = schoolClass;
	}
	public MarksForMenyDTO(ProfessorDTO professor, List<PupilMarkDTO> pupil, SubjectDTO subject,
			SchoolClassDTO schoolClass) {
		super();
		this.professor = professor;
		this.pupil = pupil;
		this.subject = subject;
		this.schoolClass = schoolClass;
	}
	public MarksForMenyDTO() {
		super();
	}
	
	
	
	
}
