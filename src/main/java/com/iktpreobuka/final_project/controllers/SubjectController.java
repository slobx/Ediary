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
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/subject")
public class SubjectController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SubjectService subjectService;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@Secured({ "ROLE_PUPIL", "ROLE_PARENT" })
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public ResponseEntity<?> getAllSubjectsPublic() {
		try {
			List<SubjectDTO> list = new ArrayList<>();
			for (Subject subject : subjectService.getAllSubject()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getCode());
				list.add(subjectDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all subjects. ");
				return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all subjects. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Subject"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllSubjectsPrivate() {
		try {
			List<SubjectDTO> list = new ArrayList<>();
			for (Subject subject : subjectService.getAllSubject()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getCode());
				list.add(subjectDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all subjects. ");
				return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all subjects. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Subject"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSubjectsAdmin() {
		try {
			List<SubjectDTO> list = new ArrayList<>();
			for (Subject subject : subjectService.getAllSubject()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getCode());
				list.add(subjectDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all subjects. ");
				return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all subjects. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Subject"),
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
	public ResponseEntity<?> findBySubjectId(@PathVariable Long id) {

		try {
			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {
				SubjectDTO subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getName(),
						subject.get().getCode());
				logger.info("You successfuly listed subject. ");
				return new ResponseEntity<SubjectDTO>(subjectDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing subject with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewSubject(@Valid @RequestBody SubjectDTO newSubject, BindingResult result) {
		try {
			if (result.hasErrors()) {

				logger.error("Something went wrong in posting new subject. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (subjectService.ifExists(newSubject.getCode())) {
			logger.error("Code for subject is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for subject is present"),
					HttpStatus.BAD_REQUEST);
		}
		if (subjectService.ifExistsName(newSubject.getName())) {
			logger.error("Name for subject is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Name for subject is present"),
					HttpStatus.BAD_REQUEST);
		}

		Subject newSubjectEntity = new Subject(newSubject.getName(), newSubject.getCode());

		subjectService.addNewSubject(newSubjectEntity);
		SubjectDTO subjectDTO = new SubjectDTO(newSubjectEntity.getId(), newSubjectEntity.getName(),
				newSubjectEntity.getCode());
		logger.info("You successfuly posted subject. ");
		return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateActivity(@Valid @RequestBody SubjectDTO newSubject, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating subject. Check input values.");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 

			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {
				if (!subject.get().getCode().equals(newSubject.getCode())) {
					if (subjectService.ifExists(newSubject.getCode())) {
						logger.error("Code for subject is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for subject is present"),
								HttpStatus.BAD_REQUEST);
					} else {
						subject.get().setCode(newSubject.getCode());
					}
				}
				if (!subject.get().getName().equals(newSubject.getName())) {
					if (subjectService.ifExistsName(newSubject.getName())) {
						logger.error("Name for subject is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Name for subject is present"),
								HttpStatus.BAD_REQUEST);
					} else {
						subject.get().setName(newSubject.getName());
					}
				}

				subjectService.updateSubject(id, subject.get());
				SubjectDTO subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getName(),
						subject.get().getCode());

				logger.info("You successfuly updated subject. ");
				return new ResponseEntity<>(subjectDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating subject with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySubjectId(@PathVariable Long id) {

		try {
			Optional<Subject> subject = subjectService.findById(id);
			if (subject.isPresent()) {

				List<ProfessorSubject> professors = subjectService.findPSBySubject(subject.get());
				if (professors.size() != 0) {
					logger.error("You can not delete subject when there are professors conected to him/her. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1,
									"You can not delete subject when there are professors conected to him/her."),
							HttpStatus.BAD_REQUEST);

				} else {
					SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
					subjectService.deleteSubject(id);
					logger.info("You successfuly deleted subject. ");
					return new ResponseEntity<SubjectDTO>(subjectDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting subject with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Subject not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
