package com.iktpreobuka.final_project.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.PupilsInClass;

public interface MarkRepository extends CrudRepository<Mark, Long> {

	List<Mark> findByPupil(PupilsInClass pc);
	List<Mark> findByPupilAndProfessor(PupilsInClass pc, ProfessorSubjectClass psc);
	List<Mark> findByProfessor(ProfessorSubjectClass psc);
	List<Mark> findByActivity(Activity activity);
	List<Mark> findByPupilAndProfessorAndActivity(PupilsInClass pc, ProfessorSubjectClass psc, Activity activity);
	
	
	
}
