package com.iktpreobuka.final_project.repositories;


import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Semestar;

public interface SemestarRepository extends CrudRepository<Semestar, Long> {

	//Semestar findByCode(String code);
	Semestar findByCode(String code);
	Semestar findByActive(boolean active);
	
	
}
