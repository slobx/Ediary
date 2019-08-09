package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.PupilsInClass;

public interface MarkService {

	 Mark deleteMark(Long id);
	 Mark updateMark(Long id, Mark newMark);
	 Mark addNewMark(Mark newMark);
	 Optional<Mark> findById(Long id);
	 Iterable<Mark> getAllMarks();
	 List<Mark> findByPupilInClass(PupilsInClass pc);
	 List<Mark> findByPupilAndSubject(PupilsInClass pc, ProfessorSubjectClass psc);
	 List<Mark> findByClassAndSubject( ProfessorSubjectClass psc);
	 List<Mark> findMarksByActivity(Activity activity);
	 List<Mark> findMarksByPupilAndProfessorAndActivity(PupilsInClass pc, ProfessorSubjectClass psc,Activity activity);
	 
	 
}
