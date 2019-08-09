package com.iktpreobuka.final_project.entities;



import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;



@MappedSuperclass
public abstract class Person {

	
	@Id
	@GeneratedValue
	public Long id;
	@Column
	public String name;
	@Column
	public String surname;
	@Column(unique=true)
	public String code;
	@Version
	@ColumnDefault("0")
	public Integer version;
	
	
	
	
	
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
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
	public Person() {
		super();
	}
	
	
	
}
