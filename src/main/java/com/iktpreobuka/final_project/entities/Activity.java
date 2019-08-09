package com.iktpreobuka.final_project.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.ColumnDefault;


@Entity
@Table (name="activity")
public class Activity {

	@Id
	@GeneratedValue
	private Long id;
	
	
	@Column(unique = true)
	private String name;
	
	
	@Column(unique = true)
	private String code;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	
	@OneToMany(mappedBy = "activity", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<Mark> marks = new ArrayList<>();

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

	public Activity() {
		super();
	}

	public Activity(String name, String code) {
		super();
		this.name = name;
		this.code = code;
	}

	
	
	
	
	
}
