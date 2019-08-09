package com.iktpreobuka.final_project.services;

import java.util.Optional;

import com.iktpreobuka.final_project.entities.Parent;

public interface ParentService {

	Iterable<Parent> getAllParents();
	Optional<Parent> findById(Long id);
	Parent addNewParent(Parent newParent);
	 Parent updateParent(Long id, Parent newParent);
	 Parent deleteParent(Long id);
	 
	 boolean ifExists(String code);
	 Parent findByCode(String code);
	 Parent findbyUser(String username);
	 
	
}
