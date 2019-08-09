package com.iktpreobuka.final_project.entities;

import java.time.LocalDate;
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
@Table (name="semestar")

public class Semestar {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Integer value;
	
	@Column
	private LocalDate startDate;
	
	@Column
	private LocalDate endDate;
	
	@Column(unique=true)
	private String code;
	
	@Version
	@ColumnDefault("0")
	private Integer version;
	
	@Column
	private boolean active;
	
	
	
	@OneToMany(mappedBy = "semestar", cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private List<SchoolClass> classes = new ArrayList<>();
	
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<SchoolClass> getClasses() {
		return classes;
	}
	public void setClasses(List<SchoolClass> classes) {
		this.classes = classes;
	}
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
	
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Semestar() {
		super();
	}
	public Semestar(String name, Integer value, String code) {
		super();
		this.name = name;
		this.value = value;
		this.code = code;
	}
	public Semestar(String name, Integer value, LocalDate startDate, LocalDate endDate, String code) {
		super();
		this.name = name;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
	}
	public Semestar(String name, Integer value, LocalDate startDate, LocalDate endDate, String code, boolean active) {
		super();
		this.name = name;
		this.value = value;
		this.startDate = startDate;
		this.endDate = endDate;
		this.code = code;
		this.active = active;
	}
	
	
	
	
}
