package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;



@Entity
@Table (name="professor",uniqueConstraints = {@UniqueConstraint(columnNames = "code")})

public class Professor extends Person{

	

	
	
	@OneToMany(mappedBy = "professor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProfessorSubject> subjects = new ArrayList<>();
	

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user_id;
	
	
	public User getUser_id() {
		return user_id;
	}
	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}
	public List<ProfessorSubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<ProfessorSubject> subjects) {
		this.subjects = subjects;
	}
	
	public Professor() {
		super();
	}
	public Professor(String name, String surname, String code, User user_id) {
		super();
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.user_id = user_id;
	}
	
	
	
}
