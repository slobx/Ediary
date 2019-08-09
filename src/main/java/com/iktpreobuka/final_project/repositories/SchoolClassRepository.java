package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;

public interface SchoolClassRepository extends CrudRepository<SchoolClass, Long> {

	List<SchoolClass> findBySemestar(Semestar semestar);
	SchoolClass findByCode(String code);
	
}
