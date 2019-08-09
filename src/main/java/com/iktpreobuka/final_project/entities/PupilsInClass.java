package com.iktpreobuka.final_project.entities;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;





@Entity
@Table (name="pupilsInClass")
public class PupilsInClass {

	@Id
	@GeneratedValue
	private Long id;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "schoolClass")
	private SchoolClass schoolClass;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "pupil")
	private Pupil pupil;
	
	
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Mark> marks = new ArrayList<>();
	
	
	
	public SchoolClass getSchoolClass() {
		return schoolClass;
	}
	public void setSchoolClass(SchoolClass schoolClass) {
		this.schoolClass = schoolClass;
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
	public Pupil getPupil() {
		return pupil;
	}
	public void setPupil(Pupil pupil) {
		this.pupil = pupil;
	}
	public PupilsInClass() {
		super();
	}
	public PupilsInClass(SchoolClass schoolClass, Pupil pupil) {
		super();
		this.schoolClass = schoolClass;
		this.pupil = pupil;
	}
	
	
	
}
