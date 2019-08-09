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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.PasswordDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.RoleService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/user")
public class UserController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllUsersAdmin() {
		try {
			List<UserDTO> list = new ArrayList<>();
			for (User user : userService.getAllUsers()) {

				Role role = user.getRole();
				RoleDTO roleDTO = new RoleDTO(role.getName());
				UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getUsername(), roleDTO);
				list.add(userDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all users. ");
				return new ResponseEntity<Iterable<UserDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all users. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Users"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	@ResponseBody
	public Object currentUserName(Authentication authentication) {
		return authentication.getName();
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByUserId(@PathVariable Long id, Authentication authentication) {

		try {

			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {
				Role role = user.get().getRole();
				RoleDTO roleDTO = new RoleDTO(role.getName());

				UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getEmail(), user.get().getPassword(),
						user.get().getPassword(), user.get().getUsername(), roleDTO);
				logger.info("You successfuly listed user with given id. ");
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing user with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST) // , consumes = "application/json"
	public ResponseEntity<?> addNewUser(@Valid @RequestBody UserDTO newUser, BindingResult result) {
		try {
			if (result.hasErrors()) {

				logger.error("Something went wrong in posting new user. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (userService.ifExists(newUser.getUsername())) {
			logger.error("Username for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is present"),
					HttpStatus.BAD_REQUEST);
		}
		if (userService.ifExistsEmail(newUser.getEmail())) {
			logger.error("Email for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is present"), HttpStatus.BAD_REQUEST);

		}
		if (newUser.getRole().getName().isEmpty()) {
			logger.error("Name for role must be provided. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Name for role must be provided."),
					HttpStatus.BAD_REQUEST);
		}

		// RoleDTO roleDTO = newUser.getRole();

		if (roleService.ifExists(newUser.getRole().getName())) {

			Role role = roleService.findByName(newUser.getRole().getName());

			User newuserE = new User(newUser.getEmail(), newUser.getPassword(), newUser.getUsername(), role);

			userService.addNewUserWithoutRole(newuserE);

		} else {

//			String str= "ROLE_"+newUser.getRole().getName().toUpperCase();
//			Role role = new Role(str);
//			roleService.addNewRole(role);
//			User newuserEntity = new User(newUser.getEmail(), newUser.getPassword(), newUser.getUsername(),role);
//			userService.addNewUserWithoutRole(newuserEntity);
			logger.error("Role must be set already for this operation to succeed. ");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Role must be set already for this operation to succeed."),
					HttpStatus.BAD_REQUEST);

		}
		logger.info("You successfuly posted user. ");
		return new ResponseEntity<>(newUser, HttpStatus.OK);

	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO newUser, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating user. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}

			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {

				if (!user.get().getUsername().equals(newUser.getUsername())) {
					if (userService.ifExists(newUser.getUsername())) {
						logger.error("Username for user is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is already present"),
								HttpStatus.BAD_REQUEST);
					}
					user.get().setUsername(newUser.getUsername());
				}
				if (!user.get().getEmail().equals(newUser.getEmail())) {
					if (userService.ifExistsEmail(newUser.getEmail())) {
						logger.error("Email for user is already present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is already present"),
								HttpStatus.BAD_REQUEST);
					}
					user.get().setEmail(newUser.getEmail());
				}

				if (!roleService.ifExists(newUser.getRole().getName())) {
					logger.error("Role must be already present. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "Role must be already present"),
							HttpStatus.BAD_REQUEST);

				}
				Role role = roleService.findByName(newUser.getRole().getName());
				user.get().setRole(role);
				userService.updateUser(id, user.get());
				logger.info("You successfuly updated user. ");
				return new ResponseEntity<>(newUser, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating user with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "passwordChange/{id}")
	public ResponseEntity<?> updateUserPassword(@Valid @RequestBody PasswordDTO newUser, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {

				if (!user.get().getPassword().equals(newUser.getOldPassword())) {
					logger.error("Old password does not match. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "Old password does not match."),
							HttpStatus.BAD_REQUEST);

				}
				if (!newUser.getNewPassword().equals(newUser.getNewConfirmPassword())) {
					logger.error("Password and confirmation of password does not match. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1, "Password and confirmation of password does not match."),
							HttpStatus.BAD_REQUEST);

				}

				user.get().setPassword(newUser.getNewPassword());
				userService.updateUser(id, user.get());
				RoleDTO roleDTO = new RoleDTO(user.get().getRole().getName());
				UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getUsername(), roleDTO);
				logger.info("You successfuly changed password for user. ");
				return new ResponseEntity<>(userDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when changing password for user with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByUserId(@PathVariable Long id) {

		try {
			Optional<User> user = userService.findById(id);
			if (user.isPresent()) {

				RoleDTO roleDTO = new RoleDTO(user.get().getRole().getName());
				UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getPassword(), user.get().getUsername(),
						roleDTO);
				userService.deleteUser(id);
				logger.info("You successfuly deleted user. ");
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when deleting user with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "User not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idUser}/role/{idRole}")
	public ResponseEntity<?> matchUserWithRole(@PathVariable Long idUser, @PathVariable Long idRole) {

		try {
			Optional<User> user = userService.findById(idUser);
			Optional<Role> role = roleService.findById(idRole);
			if (user.isPresent() && role.isPresent()) {

				user.get().setRole(role.get());
				userService.updateUser(idUser, user.get());

				RoleDTO roleDTO = new RoleDTO(role.get().getName());

				UserDTO userDTO = new UserDTO(user.get().getEmail(), user.get().getPassword(), user.get().getUsername(),
						roleDTO);
				logger.info("You successfuly conected user whith role. ");
				return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when conecting user with role for given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "User or role not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
