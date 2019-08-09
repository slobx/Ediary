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
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SemestarService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/semestar")
public class SemestarController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SemestarService semestarService;


	@Autowired
	private SchoolClassService schoolClassService;
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSemestarsAdmin() {
		try {
			List<SemestarDTO> list = new ArrayList<>();
			for (Semestar semestar : semestarService.getAll()) {
				SemestarDTO semestarDTO = new SemestarDTO(semestar.getId(),semestar.getName(), semestar.getValue(), 
						semestar.getStartDate(),semestar.getEndDate(),semestar.getCode(),semestar.isActive());
				list.add(semestarDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all semestars. ");
				return new ResponseEntity<Iterable<SemestarDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all semestars. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Semestars"),
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
	public ResponseEntity<?> findBySemestarId(@PathVariable Long id) {

		try {
			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {
				SemestarDTO semestarDTO = new SemestarDTO(semestar.get().getId(),semestar.get().getName(),semestar.get().getValue(),semestar.get().getStartDate(),
						semestar.get().getEndDate(), semestar.get().getCode(),semestar.get().isActive());
				logger.info("You successfuly listed semestar. ");
				return new ResponseEntity<SemestarDTO>(semestarDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing semestar with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewSemestar(@Valid @RequestBody SemestarDTO newSemestar, BindingResult result) {
		try{
			if (result.hasErrors()) {
		
			logger.error("Something went wrong in posting new semestar. Check input values.");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} 
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (semestarService.ifExists(newSemestar.getCode())) {
			logger.error("Code for semestar is already in use. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for semestar is already in use."), HttpStatus.BAD_REQUEST);
		}if(newSemestar.isActive()== true && semestarService.ifExistsActive(true) == true) {
			logger.error("Active semestar is already in use. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Active semestar is already in use."), HttpStatus.BAD_REQUEST);
		}
				Semestar newSemestarEntity = new Semestar(newSemestar.getName(),newSemestar.getValue(),
						newSemestar.getStartDate(),newSemestar.getEndDate(), newSemestar.getCode(),newSemestar.isActive());

				Semestar savedSemestar = semestarService.addNew(newSemestarEntity);

				SemestarDTO semestarDTO = new SemestarDTO(savedSemestar.getId(),savedSemestar.getName(), savedSemestar.getValue(), 
						savedSemestar.getStartDate(),savedSemestar.getEndDate(),savedSemestar.getCode(),savedSemestar.isActive());
				logger.info("You successfuly posted semestar. ");
				return new ResponseEntity<>(semestarDTO, HttpStatus.OK);
		
		
		
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateSemestar(@Valid @RequestBody SemestarDTO newSemestar,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating semestar. Check input values.");
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} 

			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {
				if(!semestar.get().getCode().equals(newSemestar.getCode())) {
					if(semestarService.ifExists(newSemestar.getCode())) {
						logger.error("Code for semestar is already in use. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for semestar is already in use."), HttpStatus.BAD_REQUEST);

					}else {
						semestar.get().setCode(newSemestar.getCode());
					}
				}
			if(newSemestar.isActive()== true && semestarService.ifExistsActive(true) == true) {
				logger.error("Active semestar is already in use. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "Active semestar is already in use."), HttpStatus.BAD_REQUEST);
			}else {
				semestar.get().setActive(newSemestar.isActive());
			}
				semestar.get().setName(newSemestar.getName());
				semestar.get().setValue(newSemestar.getValue());
				semestar.get().setStartDate(newSemestar.getStartDate());
				semestar.get().setEndDate(newSemestar.getEndDate());
				
				

				Semestar savedSemestar = semestarService.update(id, semestar.get());
				SemestarDTO semestarDTO = new SemestarDTO(savedSemestar.getId(),savedSemestar.getName(), savedSemestar.getValue(), 
						savedSemestar.getStartDate(),savedSemestar.getEndDate(),savedSemestar.getCode(),savedSemestar.isActive());
				logger.info("You successfuly updated semestar. ");
				return new ResponseEntity<>(semestarDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating semestar with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySemestarId(@PathVariable Long id) {

		try {
			Optional<Semestar> semestar = semestarService.findById(id);
			if (semestar.isPresent()) {

				List<SchoolClass> schoolClasses = schoolClassService.findBySemestar(semestar.get());
				if(schoolClasses.size() !=0) {
					logger.error("You can not delete semestar when there are school classes conected to it. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete semestar when there are school classes conected to it."), HttpStatus.BAD_REQUEST);

				}else {
					SemestarDTO semestarDTO = new SemestarDTO(semestar.get().getName(),semestar.get().getValue(),semestar.get().getStartDate(),
						semestar.get().getEndDate(),semestar.get().getCode(),semestar.get().isActive());
					semestarService.delete(id);
					logger.info("You successfuly deleted semestar. ");
					return new ResponseEntity<SemestarDTO>(semestarDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting semestar with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
}
