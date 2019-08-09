package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;

public interface SchoolClassService {


	Iterable<SchoolClass> getAll();
	Optional<SchoolClass> findById(Long id);
	SchoolClass addNew(SchoolClass newSchoolClass);
	SchoolClass update(Long id, SchoolClass newSchoolClass);
	SchoolClass delete(Long id);
	PupilsInClass addNewPC(Long idSC, Long idP);
	List<SchoolClass> findClassesByPupils(Long id);
	 ProfessorSubjectClass addSubjectToClass(ProfessorSubjectClass professorSubjectClass);
	 boolean ifExistsConectonSchoolClassPupil(SchoolClass sc, Pupil pupil);
	 boolean ifExistsConectonProfessorSubjectClass(ProfessorSubject professorSubject, SchoolClass sc);
	 Optional<ProfessorSubjectClass> findByProfessorSubjectClass(ProfessorSubject professorSubject, SchoolClass sc);
	 Optional<PupilsInClass> findPupilsInClass(SchoolClass sc, Pupil pupil);
	 List<SchoolClass> findBySemestar(Semestar semestar);
	 SchoolClass findClassByPupilandSemestar(Long id, Semestar semestar);
	 List<ProfessorSubjectClass> findConectionPSC(ProfessorSubject professorSubject);
	 ProfessorSubjectClass deletePSC(Long id);
	 List<PupilsInClass> findConectionByPupil(Pupil pupil);
	 PupilsInClass deletePupilsInClass(Long id);
	 boolean ifExistsCode(String code);
	 List<PupilsInClass> findConectionBySchoolClass(SchoolClass schoolClass);
	 List<SchoolClass> findClassByProfessor(Long id);
	 List<SchoolClass> findClassByProfessorAndSubject(Long idP, Long idS);
}
