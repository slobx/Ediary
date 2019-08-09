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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Administrator;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.AdministratorDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.AdministratorService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/admin")
public class AdministratorController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdministratorService adminService;

		
	
	@Autowired
	private UserService userService;
	

	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllAdmin() {
		try {
			List<AdministratorDTO> list = new ArrayList<>();
			for (Administrator admin : adminService.getAllAdministrators()) {
				AdministratorDTO adminDTO = new AdministratorDTO(admin.getId(),admin.getName(),admin.getSurname(),admin.getCode());
				list.add(adminDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all administrators. ");
				return new ResponseEntity<Iterable<AdministratorDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all administrators. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Admins"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
		@Secured("ROLE_ADMIN")
		@JsonView(View.Admin.class)
		@RequestMapping(method = RequestMethod.GET, value = "/loged")
		public ResponseEntity<?> findByLog(Authentication authentication) {

			try {
				Administrator admin = adminService.findbyUser(authentication.getName());
			
					RoleDTO roleDTO = new RoleDTO(admin.getUser_id().getRole().getName());
					UserDTO userDTO = new UserDTO(admin.getUser_id().getId(),admin.getUser_id().getEmail(),
							admin.getUser_id().getUsername(),roleDTO);
					AdministratorDTO adminDTO = new AdministratorDTO(admin.getId(),admin.getName(),
							admin.getSurname(),admin.getCode(),userDTO);
					logger.info("You successfuly listed administrator. " + adminDTO.getName() + adminDTO.getSurname());
					return new ResponseEntity<AdministratorDTO>(adminDTO, HttpStatus.OK);
				
				
			} catch (Exception e) {
				logger.error("Something went wrong. ");
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
//		@Secured("ROLE_ADMIN")
//		@JsonView(View.Admin.class)
//		@RequestMapping(method = RequestMethod.GET, value = "/{id}")
//		public ResponseEntity<?> findById(@PathVariable Long id) {
//
//			try {
//				Optional<Administrator> admin = adminService.findById(id);
//				if (admin.isPresent()) {
//					RoleDTO roleDTO = new RoleDTO(admin.get().getUser_id().getRole().getName());
//					UserDTO userDTO = new UserDTO(admin.get().getUser_id().getId(),admin.get().getUser_id().getEmail(),
//							admin.get().getUser_id().getUsername(),roleDTO);
//					AdministratorDTO adminDTO = new AdministratorDTO(admin.get().getId(),admin.get().getName(),
//							admin.get().getSurname(),admin.get().getCode(),userDTO);
//					logger.info("You successfuly listed administrator. " + adminDTO.getName() + adminDTO.getSurname());
//					return new ResponseEntity<AdministratorDTO>(adminDTO, HttpStatus.OK);
//				}
//				logger.error("Something went wrong when listing administrator with given id. ");
//				return new ResponseEntity<RESTError>(new RESTError(1, "Admin not present"), HttpStatus.BAD_REQUEST);
//			} catch (Exception e) {
//				logger.error("Something went wrong. ");
//				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
//						HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		}
	
	
		@Secured("ROLE_ADMIN")
		@JsonView(View.Admin.class)
		@RequestMapping(method = RequestMethod.POST)
		public ResponseEntity<?> addNewAdmin(@Valid @RequestBody AdministratorDTO newAdmin, BindingResult result) {
			try {
			if (result.hasErrors()) {
				logger.warn("Something went wrong in posting new admin. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			
		
			} catch (Exception e) {
				logger.error("Something went wrong. ");
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
			if(adminService.ifExists(newAdmin.getCode())) {
				logger.error("Code for admin is present. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Code for admin is present"), HttpStatus.BAD_REQUEST);
			}if(userService.ifExists(newAdmin.getAdminUser().getUsername())) {
				logger.error("Username for user is present. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is present"), HttpStatus.BAD_REQUEST);
			}if(userService.ifExistsEmail(newAdmin.getAdminUser().getEmail())) {
				logger.error("Email for user is present. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is present"), HttpStatus.BAD_REQUEST);

			}
			User adminUser = new User(newAdmin.getAdminUser().getEmail(),newAdmin.getAdminUser().getPassword(),
					newAdmin.getAdminUser().getUsername());
			
			User thisUser = userService.addNewUser(adminUser, "ROLE_ADMIN");
			
			Administrator newAdminEntity = new Administrator(newAdmin.getName(),newAdmin.getSurname(),
					newAdmin.getCode(),thisUser );
			Administrator savedAdministrator = adminService.addNewAdministrator(newAdminEntity);
			
			RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
			UserDTO userDTO = new UserDTO(savedAdministrator.getUser_id().getId(),savedAdministrator.getUser_id().getEmail(),
					savedAdministrator.getUser_id().getUsername(),roleDTO);
			AdministratorDTO adminDTO = new AdministratorDTO(savedAdministrator.getId(),savedAdministrator.getName(),
					savedAdministrator.getSurname(),savedAdministrator.getCode(),userDTO);
			logger.info("You successfuly posted administrator. " + adminDTO.getName() + adminDTO.getSurname());

			return new ResponseEntity<>(adminDTO, HttpStatus.OK);
		}
	
		@Secured("ROLE_ADMIN")
		@JsonView(View.Admin.class)
		@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
		public ResponseEntity<?> updateParent(@Valid @RequestBody AdministratorDTO newAdmin,@PathVariable Long id, 
				BindingResult result) {

			try {
				if (result.hasErrors()) {
					logger.error("Something went wrong in posting new admin. Check input values.");
						return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
					}

				Optional<Administrator> admin = adminService.findById(id);
				if (admin.isPresent()) {
					if(!admin.get().getCode().equals(newAdmin.getCode())) {
						if(adminService.ifExists(newAdmin.getCode())) {
							logger.error("Code for admin is present. ");
							return new ResponseEntity<RESTError>(new RESTError(1, "Code for admin is present"), HttpStatus.BAD_REQUEST);
						}else {
							admin.get().setCode(newAdmin.getCode());
						}
					}
					admin.get().setName(newAdmin.getName());
					admin.get().setSurname(newAdmin.getSurname());
					

					adminService.updateAdministrator(id, admin.get());

					AdministratorDTO adminDTO = new AdministratorDTO(admin.get().getId(),admin.get().getName(),
							admin.get().getSurname(),admin.get().getCode());
					logger.info("You successfuly updated administrator. " + adminDTO.getName() + adminDTO.getSurname());
					return new ResponseEntity<>(adminDTO, HttpStatus.OK);
				}
				logger.error("Something went wrong when updating administrator with given id. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Admin not present"), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				logger.error("Something went wrong. ");
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	
	
		@Secured("ROLE_ADMIN")
		@JsonView(View.Admin.class)
		@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
		public ResponseEntity<?> deleteById(@PathVariable Long id) {

			try {
				Optional<Administrator> admin = adminService.findById(id);
				if (admin.isPresent()) {
					List<User> user = userService.findByRole(admin.get().getUser_id().getRole());
					if(user.size()<=1) {
						logger.error("You can not delete admin if there is no other admin activ. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete admin if there is no other admin activ."), HttpStatus.BAD_REQUEST);

					}else {
					AdministratorDTO adminDTO = new AdministratorDTO(admin.get().getId(),admin.get().getName(),admin.get().getSurname(),
							admin.get().getCode());
						userService.deleteUser(admin.get().getUser_id().getId());
						adminService.deleteAdministrator(id);
						logger.info("You successfuly deleted administrator. " + adminDTO.getName() + adminDTO.getSurname());
						return new ResponseEntity<AdministratorDTO>(adminDTO, HttpStatus.OK);
					}
				}
				logger.error("Something went wrong when deleting administrator with given id. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Admin not present"), HttpStatus.BAD_REQUEST);
			} catch (Exception e) {
				logger.error("Something went wrong. ");
				return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		
		
}
