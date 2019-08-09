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
import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.services.ActivityService;
import com.iktpreobuka.final_project.services.MarkService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/activity")
public class ActivityController {

	
	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ActivityService activityService;
	
	@Autowired
	private MarkService markService;
	

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}
	
	
	//@Secured({"ROLE_PUPIL", "ROLE_PARENT"})
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public ResponseEntity<?> getAllActivitiesPublic() {
		
		 
		
		
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getId(),activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all activities. ");
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all activities. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		

	}
	
	@Secured("ROLE_PROFESSOR")
	//@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllActivitiesPrivate() {
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getId(),activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all activities. ");
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all activities. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong when listing all activities. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllActivitiesAdmin() {
		try {
			List<ActivityDTO> list = new ArrayList<>();
			for (Activity activity : activityService.getAllActivities()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.getId(),activity.getName(), activity.getCode());
				list.add(activityDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all activities. ");
				return new ResponseEntity<Iterable<ActivityDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all activities. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Activities"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong when listing all activities. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	//@Secured({"ROLE_ADMIN", "ROLE_PROFESSOR"})
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByActivityId(@PathVariable Long id) {

		try {
			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {
				ActivityDTO activityDTO = new ActivityDTO(activity.get().getId(),activity.get().getName(), activity.get().getCode());
				logger.info("You successfuly listed activity. ");
				return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing activity by given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewActivity(@Valid @RequestBody ActivityDTO newActivity, BindingResult result) {
		try{
			if (result.hasErrors()) {
		
			logger.error("Something went wrong in posting new activity. Check input values.");
			return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
		} 
	} catch (Exception e) {
		logger.error("Something went wrong. ");
		return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
		if(activityService.ifExists(newActivity.getCode(), newActivity.getName())) {
			logger.error("Code or name for activity is already in use. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code or name for activity is already in use."), HttpStatus.BAD_REQUEST);

		}else {

		Activity newActivityEntity = new Activity(newActivity.getName(), newActivity.getCode());

		Activity savedActivity = activityService.addNewActivity(newActivityEntity);
		
		ActivityDTO activityDTO = new ActivityDTO(savedActivity.getId(),savedActivity.getName(),savedActivity.getCode());

		logger.info("You successfuly posted activity. " + activityDTO.getName() + " " + activityDTO.getCode());
		return new ResponseEntity<>(activityDTO, HttpStatus.OK);
		}
		
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateActivity(@Valid @RequestBody ActivityDTO newActivity,@PathVariable Long id, 
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in posting new activity. Check input values.");
					return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
				} 

			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {
				if(!activity.get().getName().equals(newActivity.getName())) {
					if(activityService.ifExistsName(newActivity.getName())) {
						logger.error("Name for activity is already in use. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Name for activity is already in use."), HttpStatus.BAD_REQUEST);

					}else {
						activity.get().setName(newActivity.getName());
					}
					
				}if(!activity.get().getCode().equals(newActivity.getCode())){
					if(activityService.ifExistsCode(newActivity.getCode())) {
						logger.error("Code for activity is already in use. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for activity is already in use."), HttpStatus.BAD_REQUEST);

					}else {
						activity.get().setCode(newActivity.getCode());
					}
				}
				activityService.updateActivity(id, activity.get());
				ActivityDTO activityDTO = new ActivityDTO(activity.get().getId(),activity.get().getName(),activity.get().getCode());
				logger.info("You successfuly updated activity. " + activityDTO.getName() + " " + activityDTO.getCode());
				return new ResponseEntity<>(activityDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when update new activity by given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByActivityId(@PathVariable Long id) {

		try {
			Optional<Activity> activity = activityService.findById(id);
			if (activity.isPresent()) {

				List<Mark> marks = markService.findMarksByActivity(activity.get());
				if(marks.size() != 0) {
					logger.error("You can not delete activity when there are marks that uses that activity. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "You can not delete activity when there are marks that uses that activity."), HttpStatus.BAD_REQUEST);

				}else {
				ActivityDTO activityDTO = new ActivityDTO(activity.get().getId(),activity.get().getName(), activity.get().getCode());
				activityService.deleteActivity(id);
				logger.info("You successfuly deleted activity. " + activityDTO.getName() + " " + activityDTO.getCode());
				return new ResponseEntity<ActivityDTO>(activityDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting activity by given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Activity not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
