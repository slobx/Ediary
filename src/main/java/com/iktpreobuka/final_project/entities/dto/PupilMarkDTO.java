package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class PupilMarkDTO {


	@JsonView(View.Public.class)
	private PupilDTO pupil;
	
	@JsonView(View.Public.class)
	private List<MarkDTO> marks;



	public PupilDTO getPupil() {
		return pupil;
	}

	public void setPupil(PupilDTO pupil) {
		this.pupil = pupil;
	}

	public List<MarkDTO> getMarks() {
		return marks;
	}

	public void setMarks(List<MarkDTO> marks) {
		this.marks = marks;
	}

	public PupilMarkDTO() {
		super();
	}

	public PupilMarkDTO(PupilDTO pupil, List<MarkDTO> marks) {
		super();
		this.pupil = pupil;
		this.marks = marks;
	}

	
	
	
	
	
	
	
	
}
