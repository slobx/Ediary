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




@Entity
@Table (name="parent")

public class Parent extends Person{

	
	
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Pupil> parent_pupils = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user_id;
	
	
	

	

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public List<Pupil> getParent_pupils() {
		return parent_pupils;
	}

	public void setParent_pupils(List<Pupil> parent_pupils) {
		this.parent_pupils = parent_pupils;
	}

	public Parent() {
		super();
	}

	public Parent(String name, String surname, String code) {
		super();
		this.name = name;
		this.surname = surname;
		//this.user.setEmail(email);
		this.code = code;
	}

	public Parent(String name, String surname, String code,User user_id) {
		super();
		this.name = name;
		this.surname = surname;
		//this.user.setEmail(email);
		this.code = code;
		this.user_id = user_id;
	}
	
	
	
	
}
