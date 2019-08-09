package com.iktpreobuka.final_project.repositories;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Role;





public interface RoleRepository extends CrudRepository<Role, Long> {

	Role findByName(String name);
}
