package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Administrator;

public interface AdministratorRepository extends CrudRepository<Administrator, Long> {
 
	Administrator findByCode(String code);
}
