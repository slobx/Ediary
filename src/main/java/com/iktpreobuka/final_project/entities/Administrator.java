package com.iktpreobuka.final_project.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="admin")
public class Administrator extends Person{

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user_id;

	public User getUser_id() {
		return user_id;
	}

	public void setUser_id(User user_id) {
		this.user_id = user_id;
	}

	public Administrator(String name, String surname, String code,User user_id) {
		this.name = name;
		this.surname = surname;
		this.code = code;
		this.user_id = user_id;
	}

	public Administrator() {
	}

	
	
	
	
}
