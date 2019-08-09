package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;

public interface ProfessorService {

	Iterable<Professor> getAll();
	Optional<Professor> findById(Long id);
	Professor addNew(Professor newProfessor);
	Professor update(Long id, Professor newProfessor);
	Professor delete(Long id);
	 ProfessorSubject addNewPS(Professor newProfessor, Subject newSubject);
	// List<Subject> findSubjectsByProfessor(Professor professor);
	 List<Subject> findSubjectByProff(Long id);
	 //ProfessorSubject fingConectionProfSubject(Long idP, Long idS);
	 Optional<ProfessorSubject> findByProfessorSubject(Professor professor, Subject subject);
	 boolean ifExistsConectonProfessorSubject(Professor professor, Subject subject);
	 boolean ifExists(String code);
	 ProfessorSubject deletePS(Long id);
	 List<ProfessorSubject> findPSByProfessor(Professor professor);
	 Professor findbyUser(String username);
}
