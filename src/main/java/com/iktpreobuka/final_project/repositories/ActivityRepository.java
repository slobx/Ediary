package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {

	Activity findByName(String name);
	Activity findByCode(String code);
}
