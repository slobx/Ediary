package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.repositories.RoleRepository;
import com.iktpreobuka.final_project.repositories.UserRepository;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	
	
	public Iterable<User> getAllUsers() {
		return userRepo.findAll();
	}

	public Optional<User> findById(Long id) {
		return userRepo.findById(id);
	}

	public User addNewUser(User newUser, String name) {

		
		newUser.setRole(roleRepo.findByName(name));
		
		return userRepo.save(newUser);
	}
	
	public User addNewUserWithoutRole(User newUser) {

		
		return userRepo.save(newUser);
	}
	public User updateUser(Long id, User newUser) {

		if (newUser == null || !userRepo.findById(id).isPresent()) {
			return null;
		}

		User temp = userRepo.findById(id).get();

		temp.setEmail(newUser.getEmail());
		temp.setPassword(newUser.getPassword());
		temp.setUsername(newUser.getUsername());
		temp.setRole(newUser.getRole());

		return userRepo.save(temp);
	}

	public User deleteUser(Long id) {

		if (!userRepo.findById(id).isPresent()) {
			return null;
		}
		User temp = userRepo.findById(id).get();
		userRepo.deleteById(id);
		return temp;
	}

	public boolean ifExists(String username) {
		
		if(userRepo.findByUsername(username) != null) {
			return true;
		}else return false;
		
		
	}
public boolean ifExistsEmail(String email) {
		
		if(userRepo.findByEmail(email) != null) {
			return true;
		}else return false;
		
		
	}
	
	public List<User> findByRole(Role role) {
		return userRepo.findByRole(role); 
	}
	
	public User findByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
