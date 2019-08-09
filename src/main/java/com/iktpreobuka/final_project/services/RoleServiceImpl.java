package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleRepository roleRepo;
	
	public Role findByName(String name) {
		
		return roleRepo.findByName(name);
	}
	
	public boolean ifExists(String name) {
		
		if(roleRepo.findByName(name) != null) {
			return true;
		}else return false;
		
		
	}
	
	
	public Optional<Role> findById(Long id) {
		return roleRepo.findById(id);
	}
	
	
	
	public Iterable<Role> getAllUsers() {
		return roleRepo.findAll();
	}

	public Role addNewRole(Role newRole) {

		
		return roleRepo.save(newRole);
	}
	

	public Role deleteRole(Long id) {

		if (!roleRepo.findById(id).isPresent()) {
			return null;
		}
		Role temp = roleRepo.findById(id).get();
		roleRepo.deleteById(id);
		return temp;
	}

	public Role updateRole(Long id, Role newRole) {

		if (newRole == null || !roleRepo.findById(id).isPresent()) {
			return null;
		}

		Role temp = roleRepo.findById(id).get();

		
		temp.setName(newRole.getName());
		

		return roleRepo.save(temp);
	}
}
