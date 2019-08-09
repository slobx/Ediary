package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Long> {

	Subject findByCode(String code);
	Subject findByName(String name);
}
