package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.repositories.MarkRepository;

@Service
public class MarkServiceImpl implements MarkService{

	
	@Autowired
	private MarkRepository markRepo;
	
	public Iterable<Mark> getAllMarks() {
		return markRepo.findAll();
	}

	public Optional<Mark> findById(Long id) {
		return markRepo.findById(id);
	}

	public Mark addNewMark(Mark newMark) {

		return markRepo.save(newMark);
	}
	
	public Mark updateMark(Long id, Mark newMark) {

		if (newMark == null || !markRepo.findById(id).isPresent()) {
			return null;
		}

		Mark temp = markRepo.findById(id).get();

		temp.setDate(newMark.getDate());
		temp.setValue(newMark.getValue());
		temp.setProfessor(newMark.getProfessor());
		temp.setPupil(newMark.getPupil());

		return markRepo.save(temp);
	}

	public Mark deleteMark(Long id) {

		if (!markRepo.findById(id).isPresent()) {
			return null;
		}
		Mark temp = markRepo.findById(id).get();
		markRepo.deleteById(id);
		return temp;
	}
	
	public List<Mark> findByPupilInClass(PupilsInClass pc){
		return markRepo.findByPupil(pc);
	}

	public List<Mark> findByPupilAndSubject(PupilsInClass pc, ProfessorSubjectClass psc){
		return markRepo.findByPupilAndProfessor(pc, psc);
	}
	
	public List<Mark> findByClassAndSubject( ProfessorSubjectClass psc){
		return markRepo.findByProfessor(psc);
	}
	
	public List<Mark> findMarksByActivity(Activity activity){
		return markRepo.findByActivity(activity);
	}
	
	public List<Mark> findMarksByPupilAndProfessorAndActivity(PupilsInClass pc, ProfessorSubjectClass psc,Activity activity){
		return markRepo.findByPupilAndProfessorAndActivity(pc, psc, activity);
	}
}
