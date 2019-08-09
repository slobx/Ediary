package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.repositories.ProfessorSubjectRepository;
import com.iktpreobuka.final_project.repositories.SubjectRepository;

@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepo;
	@Autowired
	private ProfessorSubjectRepository psRepo;

	public Iterable<Subject> getAllSubject() {
		return subjectRepo.findAll();
	}

	public Optional<Subject> findById(Long id) {
		return subjectRepo.findById(id);
	}

	public Subject addNewSubject(Subject newSubject) {

		return subjectRepo.save(newSubject);
	}

	public Subject updateSubject(Long id, Subject newSubject) {

		if (newSubject == null || !subjectRepo.findById(id).isPresent()) {
			return null;
		}

		Subject temp = subjectRepo.findById(id).get();

		temp.setCode(newSubject.getCode());
		temp.setName(newSubject.getName());
		temp.setVersion(newSubject.getVersion());

		return subjectRepo.save(temp);
	}

	public Subject deleteSubject(Long id) {

		if (!subjectRepo.findById(id).isPresent()) {
			return null;
		}
		Subject temp = subjectRepo.findById(id).get();
		subjectRepo.deleteById(id);
		return temp;
	}

	public boolean ifExists(String code) {

		if (subjectRepo.findByCode(code) != null) {
			return true;
		} else
			return false;

	}

	public boolean ifExistsName(String name) {

		if (subjectRepo.findByName(name) != null) {
			return true;
		} else
			return false;

	}

	public List<ProfessorSubject> findPSBySubject(Subject subject) {

		return psRepo.findBySubject(subject);
	}
}
