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
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/parent")
public class ParentController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ParentService parentService;

	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PupilService pupilService;

	
	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllParentsAdmin() {
		try {
			List<ParentDTO> list = new ArrayList<>();
			for (Parent parent : parentService.getAllParents()) {
				ParentDTO parentDTO = new ParentDTO(parent.getId(),parent.getName(),parent.getSurname(),parent.getCode());
				list.add(parentDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all parents. ");
				return new ResponseEntity<Iterable<ParentDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all parents. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Parents"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				ParentDTO parentDTO = new ParentDTO(parent.get().getId(),parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
				logger.info("You successfuly listed parent. ");
				return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing parent with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured("ROLE_PARENT")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/loged")
	public ResponseEntity<?> findByParent(Authentication authentication) {

		try {
			Parent parent = parentService.findbyUser(authentication.getName());
			
//			List<PupilDTO> list = new ArrayList<>();
//			for (Pupil pupil : pupilService.findPupilsByParent(parent)) {
//
//				
//				PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(),pupil.getJmbg(),pupil.getCode());
//
//				list.add(pupilDTO);
//			}
				ParentDTO parentDTO = new ParentDTO(parent.getId(),parent.getName(),parent.getSurname(),
						parent.getCode());
				logger.info("You successfuly listed parent. ");
				return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewParent(@Valid @RequestBody ParentDTO newParent, BindingResult result) {
		try{
			if (result.hasErrors()) {
		
				logger.error("Something went wrong in posting new parent. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	
		if(parentService.ifExists(newParent.getCode())) {
			logger.error("Code for parent is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for parent is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExists(newParent.getParentUser().getUsername())) {
			logger.error("Username for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExistsEmail(newParent.getParentUser().getEmail())) {
			logger.error("Email for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is present"), HttpStatus.BAD_REQUEST);

		}
		User parentUser = new User(newParent.getParentUser().getEmail(),newParent.getParentUser().getPassword(),
				newParent.getParentUser().getUsername());
		
		User thisUser = userService.addNewUser(parentUser, "ROLE_PARENT");
		
		Parent newParentEntity = new Parent(newParent.getName(),newParent.getSurname(),
				newParent.getCode(),thisUser );
		Parent savedParent = parentService.addNewParent(newParentEntity);
		
		RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
		UserDTO userDTO = new UserDTO(savedParent.getUser_id().getEmail(),savedParent.getUser_id().getUsername(),roleDTO);
		ParentDTO parentDTO = new ParentDTO(savedParent.getId(),savedParent.getName(),savedParent.getSurname(),
				savedParent.getCode(),userDTO);
		
		logger.info("You successfuly posted parent. ");
		return new ResponseEntity<>(parentDTO, HttpStatus.OK);
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateParent(@Valid @RequestBody ParentDTO newParent,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating new parent. Check input values.");
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				}

			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				if(!parent.get().getCode().equals(newParent.getCode())) {
					if(parentService.ifExists(newParent.getCode())) {
						logger.error("Code for parent is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for parent is present"), HttpStatus.BAD_REQUEST);
					}else {
						parent.get().setCode(newParent.getCode());
					}
				}
				parent.get().setName(newParent.getName());
				parent.get().setSurname(newParent.getSurname());
				

				parentService.updateParent(id, parent.get());

				ParentDTO parentDTO = new ParentDTO(parent.get().getId(),parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
				logger.info("You successfuly updated parent. ");
				return new ResponseEntity<>(parentDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating parent with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByParentId(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {
				List<Pupil>pupils = pupilService.findPupilsByParent(parent.get());
				if(pupils.size() !=0) {
					logger.error("You can not delete parent when there are pupils in this school. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete parent when there are pupils in this school."), HttpStatus.BAD_REQUEST);

				}else {

					ParentDTO parentDTO = new ParentDTO(parent.get().getName(),parent.get().getSurname(),
						parent.get().getCode());
					userService.deleteUser(parent.get().getUser_id().getId());
					parentService.deleteParent(id);
					logger.info("You successfuly deleted parent. ");
					return new ResponseEntity<ParentDTO>(parentDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting parent with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}
