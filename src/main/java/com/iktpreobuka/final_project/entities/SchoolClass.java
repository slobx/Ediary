package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

import com.iktpreobuka.final_project.enumerations.EGrade;

@Entity
@Table (name="schoolClass")

public class SchoolClass {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique=true)
	private String code;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	@Column
	private EGrade grade;
	
	@Column
	private String name;
	
	
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "semestar")
	private Semestar semestar;
	
	
	@OneToMany(mappedBy = "schoolClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilsInClass> pupils = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "schoolClass", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<ProfessorSubjectClass> professors_subjects = new ArrayList<>();
	
	
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
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public EGrade getGrade() {
		return grade;
	}
	public void setGrade(EGrade grade) {
		this.grade = grade;
	}
	public Semestar getSemestar() {
		return semestar;
	}
	public void setSemestar(Semestar semestar) {
		this.semestar = semestar;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public SchoolClass() {
		super();
	}

	public List<PupilsInClass> getPupils() {
		return pupils;
	}
	public void setPupils(List<PupilsInClass> pupils) {
		this.pupils = pupils;
	}
	public List<ProfessorSubjectClass> getProfessors_subjects() {
		return professors_subjects;
	}
	public void setProfessors_subjects(List<ProfessorSubjectClass> professors_subjects) {
		this.professors_subjects = professors_subjects;
	}
	
	
	public SchoolClass(String code, EGrade grade, Semestar semestar) {
		super();
		this.code = code;
		this.grade = grade;
		this.semestar = semestar;
	}
	public SchoolClass(String code, EGrade grade, String name, Semestar semestar) {
		super();
		this.code = code;
		this.grade = grade;
		this.name = name;
		this.semestar = semestar;
	}

	
	
	
	
}
