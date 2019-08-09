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
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.MarkService;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/pupil")
public class PupilController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
	private PupilService pupilService;

	
	@Autowired
	private ParentService parentService;
	@Autowired
	private SchoolClassService scService;
	@Autowired
	private MarkService markService;
	
	@Autowired
	private UserService userService;
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
//	
//	@Secured("ROLE_ADMIN")
//	@JsonView(View.Admin.class)
//	@RequestMapping(method = RequestMethod.GET, value = "/private")
//	public ResponseEntity<?> getAllPupilsPrivate() {
//		try {
//			List<PupilDTO> list = new ArrayList<>();
//			for (Pupil pupil : pupilService.getAll()) {
//				Parent pt = pupil.getParent();
//				ParentDTO ptDTO = new ParentDTO(pt.getId(),pt.getName(),pt.getSurname(),pt.getCode());
//				
//				PupilDTO pupilDTO = new PupilDTO(pupil.getId(),pupil.getName(),pupil.getSurname(),pupil.getJmbg(),pupil.getCode(),
//						ptDTO);
//				list.add(pupilDTO);
//			}
//			if (list.size() != 0) {
//				return new ResponseEntity<Iterable<PupilDTO>>(list, HttpStatus.OK);
//			}
//			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Pupils"),
//					HttpStatus.BAD_REQUEST);
//		} catch (Exception e) {
//			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
//					HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllPupilsAdmin() {
		try {
			List<PupilDTO> list = new ArrayList<>();
			for (Pupil pupil : pupilService.getAll()) {
				Parent pt = pupil.getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getId(),pt.getName(),pt.getSurname(),pt.getCode());
				UserDTO userDTO = new UserDTO(pupil.getUser_id().getId(), pupil.getUser_id().getUsername());
				PupilDTO pupilDTO = new PupilDTO(pupil.getId(),pupil.getName(),pupil.getSurname(),pupil.getJmbg(),pupil.getCode(),
						ptDTO,userDTO);
				list.add(pupilDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all pupils. ");
				return new ResponseEntity<Iterable<PupilDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all pupils. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Pupils"),
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
	public ResponseEntity<?> findByPupilId(@PathVariable Long id) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {
				Parent pt = pupil.get().getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getId(),pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getId(),pupil.get().getName(),pupil.get().getSurname(),
						pupil.get().getJmbg(),pupil.get().getCode(),ptDTO);
				logger.info("You successfuly listed pupil. ");
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing pupil with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_PUPIL")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/loged")
	public ResponseEntity<?> findByPupil(Authentication authentication) {

		try {
			
			Pupil pupil = pupilService.findbyUser(authentication.getName());
				Parent pt = pupil.getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getId(),pt.getName(),pt.getSurname(),pt.getCode());
				

				List<SchoolClassDTO> list = new ArrayList<>();
				for (SchoolClass sc : scService.findClassesByPupils(pupil.getId())) {

					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getId(),sm.getName(), sm.getValue(),  sm.getStartDate(), 
							sm.getEndDate(),sm.getCode(),sm.isActive());

					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getId(),sc.getCode(), sc.getGrade(), smDTO, sc.getName());

					list.add(schoolClassDTO);
				}
				
				PupilDTO pupilDTO = new PupilDTO(pupil.getId(),pupil.getName(),pupil.getSurname(),
						pupil.getJmbg(),pupil.getCode(),list,ptDTO);
				logger.info("You successfuly listed pupil. ");
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewPupil(@Valid @RequestBody PupilDTO newPupil, BindingResult result) {
		try{
			if (result.hasErrors()) {
		
			logger.error("Something went wrong in posting new pupil. Check input values.");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		}
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		if(pupilService.ifExists(newPupil.getCode())) {
			logger.error("Code for pupil is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for pupil is present"), HttpStatus.BAD_REQUEST);
		}if(pupilService.ifExistsJMBG(newPupil.getJmbg())) {
			logger.error("Jmbg for pupil is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Jmbg for pupil is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExists(newPupil.getPupilUser().getUsername())) {
			logger.error("Username for user pupil is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Username for user pupil is present"), HttpStatus.BAD_REQUEST);
		}if(userService.ifExistsEmail(newPupil.getPupilUser().getEmail())) {
			logger.error("Email for user pupil is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Email for user pupil is present"), HttpStatus.BAD_REQUEST);
		}
		
		
		ParentDTO parentDTO = newPupil.getParent();
		
		if(parentService.ifExists(parentDTO.getCode())) {
			Parent parent = parentService.findByCode(parentDTO.getCode());
			User pupilUser = new User (newPupil.getPupilUser().getEmail(),newPupil.getPupilUser().getPassword(),newPupil.getPupilUser().getUsername());
			User thisUser = userService.addNewUser(pupilUser, "ROLE_PUPIL");
			Pupil newPupilEntity = new Pupil(newPupil.getName(),newPupil.getSurname(),newPupil.getJmbg(),newPupil.getCode(),
					parent,thisUser);

			pupilService.addNew(newPupilEntity);
			ParentDTO ptDTO = new ParentDTO(parent.getId(),parent.getName(),parent.getSurname(),parent.getCode());
			PupilDTO pupilDTO = new PupilDTO(newPupilEntity.getId(),newPupilEntity.getName(),newPupilEntity.getSurname(),
					newPupilEntity.getJmbg(),newPupilEntity.getCode(),ptDTO);
			logger.info("You successfuly posted pupil. ");
			return new ResponseEntity<>(pupilDTO, HttpStatus.OK);
		}else {
			if(userService.ifExists(newPupil.getParent().getParentUser().getUsername())) {
				logger.error("Username for user parent is present. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Username for user for parent is present"), HttpStatus.BAD_REQUEST);
			}if(userService.ifExistsEmail(newPupil.getParent().getParentUser().getEmail())) {
				logger.error("Username for user parent is present. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Email for user for parent is present"), HttpStatus.BAD_REQUEST);
			}
			User parentUser = new User(newPupil.getParent().getParentUser().getEmail(),newPupil.getParent().getParentUser().getPassword(),
					newPupil.getParent().getParentUser().getUsername());
			
			User thisUserParent = userService.addNewUser(parentUser, "ROLE_PARENT");
			
			Parent parent = new Parent(parentDTO.getName(),parentDTO.getSurname(),parentDTO.getCode(),thisUserParent);
			parentService.addNewParent(parent);
			User pupilUser = new User (newPupil.getPupilUser().getEmail(),newPupil.getPupilUser().getPassword(),newPupil.getPupilUser().getUsername());
			User thisUser = userService.addNewUser(pupilUser, "ROLE_PUPIL");
			
			Pupil newPupilE = new Pupil(newPupil.getName(),newPupil.getSurname(),newPupil.getJmbg(),newPupil.getCode(),
					parent,thisUser);
			
			pupilService.addNew(newPupilE);
			
			PupilDTO pupilDTO = new PupilDTO(newPupilE.getId(),newPupilE.getName(),newPupilE.getSurname(),
					newPupilE.getJmbg(),newPupilE.getCode(),parentDTO);

			logger.info("You successfuly posted pupil. ");
			return new ResponseEntity<>(pupilDTO, HttpStatus.OK);
		}
		
	}
	
	
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updatePupil(@Valid @RequestBody PupilDTO newPupil,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating new pupil. Check input values.");
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				}

			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {
				if(!pupil.get().getCode().equals(newPupil.getCode())) {
					if(pupilService.ifExists(newPupil.getCode())) {
						logger.error("Code for pupil is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for pupil is present"), HttpStatus.BAD_REQUEST);
					}else {
						pupil.get().setCode(newPupil.getCode());
					}
				}
				
				pupil.get().setName(newPupil.getName());
				pupil.get().setSurname(newPupil.getSurname());
				ParentDTO parentDTO = newPupil.getParent();
				
				if(parentService.ifExists(parentDTO.getCode())) {
					Parent parent = parentService.findByCode(parentDTO.getCode());
					pupil.get().setParent(parent);
					
				}else {
					logger.error("Parent not present. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
				}
				
				pupilService.update(id, pupil.get());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getId(),pupil.get().getName(),pupil.get().getSurname(),
						pupil.get().getJmbg(),pupil.get().getCode(),parentDTO);

				logger.info("You successfuly updated pupil. ");
				return new ResponseEntity<>(pupilDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating pupil with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByPupilId(@PathVariable Long id) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			if (pupil.isPresent()) {
				
				List<PupilsInClass> pupilInClass = scService.findConectionByPupil(pupil.get());
				if(pupilInClass.size() !=0) {
					logger.error("You can not delete pupil when there are school classes conected to him/her. ");
							return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete pupil when there are school classes conected to him/her."), HttpStatus.BAD_REQUEST);

				}else {
				Parent pt = pupil.get().getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(),pupil.get().getSurname(),pupil.get().getJmbg(),
						pupil.get().getCode(), ptDTO);
				pupilService.delete(id);
				logger.info("You successfuly deleted pupil. ");
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting pupil with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/class/{idSC}")
	public ResponseEntity<?> deleteByPupilInClass(@PathVariable Long id, @PathVariable Long idSC) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			Optional<SchoolClass> sc = scService.findById(idSC);
			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc.get(), pupil.get());
			if(pc.isPresent()) {
				List<Mark> marks = markService.findByPupilInClass(pc.get());
				if(marks.size() !=0) {
					logger.error("You can not delete pupil and school class conection when there are marks. ");
							return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete pupil and school class conection when there are marks."), HttpStatus.BAD_REQUEST);

				}else {
					
				Parent pt = pupil.get().getParent();
				ParentDTO ptDTO = new ParentDTO(pt.getName(),pt.getSurname(),pt.getCode());
				
				PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(),pupil.get().getSurname(),pupil.get().getJmbg(),
						pupil.get().getCode(), ptDTO);
				scService.delete(pc.get().getId());
				logger.info("You successfuly deleted conection between pupil and school class. ");
				return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting conection pupil and school class with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil or school class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/class/{id}")
	public ResponseEntity<?> getAllPupilsbyclass(@PathVariable Long id) {
		try {
			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {

				List<PupilDTO> list = new ArrayList<>();
				for (Pupil pupil : pupilService.findPupilsByClass(id)) {
					
					PupilDTO pupilDTO = new PupilDTO(pupil.getId(),pupil.getName(),pupil.getSurname(),pupil.getCode());
					list.add(pupilDTO);
				}

				if (list.size() != 0) {
					logger.info("You successfuly listed school classes for pupil. ");

					return new ResponseEntity<Iterable<PupilDTO>>(list, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when listing pupils for school class with given id. ");

			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all pupils"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
}
