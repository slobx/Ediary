package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;


import com.iktpreobuka.final_project.entities.Parent;

public interface ParentRepository extends CrudRepository<Parent, Long>{

	Parent findByCode(String code);
}
