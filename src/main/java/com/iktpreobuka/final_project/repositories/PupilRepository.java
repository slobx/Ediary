package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Pupil;

public interface PupilRepository extends CrudRepository<Pupil, Long> {

	List<Pupil> findByParent(Parent parent);
	Pupil findByCode(String code);
	Pupil findByJmbg(String jmbg);
}
