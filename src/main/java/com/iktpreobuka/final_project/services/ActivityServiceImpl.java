package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.repositories.ActivityRepository;

@Service
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	private ActivityRepository activityRepo;

	public Iterable<Activity> getAllActivities() {
		return activityRepo.findAll();
	}

	public Optional<Activity> findById(Long id) {
		return activityRepo.findById(id);
	}

	public Activity addNewActivity(Activity newActivity) {

		return activityRepo.save(newActivity);
	}

	public Activity updateActivity(Long id, Activity newActivity) {

		if (newActivity == null || !activityRepo.findById(id).isPresent()) {
			return null;
		}

		Activity temp = activityRepo.findById(id).get();

		temp.setCode(newActivity.getCode());
		temp.setName(newActivity.getName());
		temp.setVersion(newActivity.getVersion());

		return activityRepo.save(temp);
	}

	public Activity deleteActivity(Long id) {

		if (!activityRepo.findById(id).isPresent()) {
			return null;
		}
		Activity temp = activityRepo.findById(id).get();
		activityRepo.deleteById(id);
		return temp;
	}

	public Activity findActivityByName(String name) {
		return activityRepo.findByName(name);
	}

	public boolean ifExists(String code, String name) {

		if (activityRepo.findByCode(code) != null  || activityRepo.findByName(name) != null) {
			return true;
		} else
			return false;

	}

	public boolean ifExistsCode(String code) {

		if (activityRepo.findByCode(code) != null) {
			return true;
		} else
			return false;

	}

	public boolean ifExistsName(String name) {

		if (activityRepo.findByCode(name) != null) {
			return true;
		} else
			return false;

	}
	
	 

}
