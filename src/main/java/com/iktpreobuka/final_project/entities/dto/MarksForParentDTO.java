package com.iktpreobuka.final_project.entities.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.util.View;

public class MarksForParentDTO {

	@JsonView(View.Public.class)
	private List<PupilMarkDTO> pupil;

	public List<PupilMarkDTO> getPupil() {
		return pupil;
	}

	public void setPupil(List<PupilMarkDTO> pupil) {
		this.pupil = pupil;
	}

	public MarksForParentDTO(List<PupilMarkDTO> pupil) {
		super();
		this.pupil = pupil;
	}
	


	
	
	
	
}
