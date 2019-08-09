package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Administrator;

public interface AdministratorService {


	Iterable<Administrator> getAllAdministrators();
	Optional<Administrator> findById(Long id);
	Administrator addNewAdministrator(Administrator newAdministrator);
	Administrator updateAdministrator(Long id, Administrator newAdministrator);
	Administrator deleteAdministrator(Long id);
	Administrator findByCode(String code);
	 boolean ifExists(String code);
	 Administrator findbyUser(String username);
	 
}
