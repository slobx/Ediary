package com.iktpreobuka.final_project.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;

import com.iktpreobuka.final_project.enumerations.EMarkValue;

@Entity
@Table (name="mark")
public class Mark {

	@Id
	@GeneratedValue
	private Long id;
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pupil")
	private PupilsInClass pupil;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "professor")
	private ProfessorSubjectClass professor;
	
	
	private EMarkValue value;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	private LocalDate date;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "activity")
	private Activity activity;
	
	
	
	
	public PupilsInClass getPupil() {
		return pupil;
	}
	public ProfessorSubjectClass getProfessor() {
		return professor;
	}
	public void setProfessor(ProfessorSubjectClass professor) {
		this.professor = professor;
	}
	public void setPupil(PupilsInClass pupil) {
		this.pupil = pupil;
	}
	public EMarkValue getValue() {
		return value;
	}
	public void setValue(EMarkValue value) {
		this.value = value;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public Mark() {
		super();
	}
	public Mark(PupilsInClass pupil, ProfessorSubjectClass professor, EMarkValue value, LocalDate date,
			Activity activity) {
		super();
		this.pupil = pupil;
		this.professor = professor;
		this.value = value;
		this.date = date;
		this.activity = activity;
	}
	
	
	
	
}
