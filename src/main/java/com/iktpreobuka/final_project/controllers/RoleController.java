package com.iktpreobuka.final_project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.services.RoleService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/role")
public class RoleController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RoleService roleService;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllRoles() {
		try {
			List<RoleDTO> list = new ArrayList<>();
			for (Role role : roleService.getAllUsers()) {

				RoleDTO roleDTO = new RoleDTO(role.getName());

				list.add(roleDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all roles. ");
				return new ResponseEntity<Iterable<RoleDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all roles. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Roles"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByRoleId(@PathVariable Long id) {

		try {
			Optional<Role> role = roleService.findById(id);
			if (role.isPresent()) {

				RoleDTO roleDTO = new RoleDTO(role.get().getName());

				logger.info("You successfuly listed role " + roleDTO.getName());
				return new ResponseEntity<RoleDTO>(roleDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing role with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Role not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewRole(@Valid @RequestBody RoleDTO newRole, BindingResult result) {
		try {
			if (result.hasErrors()) {

				logger.error("Something went wrong in posting new role. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		Role newRoleEntity = new Role(newRole.getName());

		Role savedRole = roleService.addNewRole(newRoleEntity);
		RoleDTO roleDTO = new RoleDTO(savedRole.getName());

		logger.info("You successfuly posted new role " + roleDTO.getName());
		return new ResponseEntity<>(roleDTO, HttpStatus.OK);
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateRole(@Valid @RequestBody RoleDTO newRole, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating new role. Check input values. ");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 

			Optional<Role> role = roleService.findById(id);
			if (role.isPresent()) {

				role.get().setName(newRole.getName());

				Role updatedRole = roleService.updateRole(id, role.get());
				RoleDTO roleDTO = new RoleDTO(updatedRole.getName());

				logger.info("You successfuly updated new role " + roleDTO.getName());
				return new ResponseEntity<>(roleDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating role with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Role not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByRoleId(@PathVariable Long id) {

		try {
			Optional<Role> role = roleService.findById(id);
			if (role.isPresent()) {

				RoleDTO roleDTO = new RoleDTO(role.get().getName());

				roleService.deleteRole(id);
				logger.info("You successfuly deleted new role " + roleDTO.getName());
				return new ResponseEntity<RoleDTO>(roleDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when deleting role with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Role not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
