package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table (name="pupil")

public class Pupil extends Person{

	
	@Column(unique = true)
	private String jmbg;
	
	
	
	@OneToMany(mappedBy = "pupil", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<PupilsInClass> schoolClasses = new ArrayList<>();
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent")
	private Parent parent;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user_id;
	
	
	
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public Parent getParent() {
		return parent;
	}
	public void setParent(Parent parent) {
		this.parent = parent;
	}
	
	public String getJmbg() {
		return jmbg;
	}
	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}
	
	public List<PupilsInClass> getSchoolClasses() {
		return schoolClasses;
	}
	public void setSchoolClasses(List<PupilsInClass> schoolClasses) {
		this.schoolClasses = schoolClasses;
	}
	public Pupil() {
		super();
	}
	public Pupil(String name, String surname, String jmbg, String code, Parent parent) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
	}
	public Pupil(String name, String surname,String jmbg, String code, Parent parent, User user_id) {
		super();
		this.name = name;
		this.surname = surname;
		this.jmbg = jmbg;
		this.code = code;
		this.parent = parent;
		this.user_id = user_id;
	}
	
	
	
	
	
}
