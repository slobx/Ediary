package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.User;

public interface UserRepository extends CrudRepository<User, Long>{

	User findByUsername(String username);
	User findByEmail(String email);
	List<User> findByRole(Role role);
}
