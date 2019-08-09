package com.iktpreobuka.final_project.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;

public interface ProfessorSubjectRepository extends CrudRepository<ProfessorSubject, Long> {

	List<ProfessorSubject>findByProfessor(Professor professor);
	Optional<ProfessorSubject> findByProfessorAndSubject(Professor professor, Subject subject);
	List<ProfessorSubject>findBySubject(Subject subject);
}
