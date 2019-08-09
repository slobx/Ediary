package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.repositories.ProfessorSubjectClassRepository;
import com.iktpreobuka.final_project.repositories.PupilRepository;
import com.iktpreobuka.final_project.repositories.PupilsInClassRepository;
import com.iktpreobuka.final_project.repositories.SchoolClassRepository;

@Service
public class SchoolClassServiceImpl implements SchoolClassService{

	@Autowired
	private SchoolClassRepository schoolClassRepo;
	
	@Autowired
	private PupilsInClassRepository pupilsInClassRepo;
	
	@Autowired
	private PupilRepository pupilRepo;
	
	
	@Autowired
	private ProfessorSubjectClassRepository professorSubjectClassRepo;
	
	
	
	
	@PersistenceContext
	private EntityManager em;
	
	public Iterable<SchoolClass> getAll() {
		return schoolClassRepo.findAll();
	}

	
	public Optional<SchoolClass> findById(Long id) {
		
		return schoolClassRepo.findById(id);
	}

	
	public SchoolClass addNew(SchoolClass newSchoolClass) {
		
		
		return schoolClassRepo.save(newSchoolClass);
	}

	
	public SchoolClass update(Long id, SchoolClass newSchoolClass) {

		if (newSchoolClass == null || !schoolClassRepo.findById(id).isPresent()) {
			return null;
		}

		SchoolClass temp = schoolClassRepo.findById(id).get();

		temp.setCode(newSchoolClass.getCode());
		temp.setGrade(newSchoolClass.getGrade());
		temp.setVersion(newSchoolClass.getVersion());
		temp.setSemestar(newSchoolClass.getSemestar());
		temp.setName(newSchoolClass.getName());
		
		
		

		return schoolClassRepo.save(temp);
	}

	
	public SchoolClass delete(Long id) {
		
		if (!schoolClassRepo.findById(id).isPresent()) {
			return null;
		}
		SchoolClass temp = schoolClassRepo.findById(id).get();
		schoolClassRepo.deleteById(id);
		return temp;
	}
	
	public PupilsInClass addNewPC(Long idSC, Long idP) {
	
	SchoolClass tempSC = schoolClassRepo.findById(idSC).get();
	Pupil tempP = pupilRepo.findById(idP).get();
		
		PupilsInClass entity = new PupilsInClass(tempSC,tempP);
		return pupilsInClassRepo.save(entity);
	}


	

	@SuppressWarnings("unchecked")
	
	public List<SchoolClass> findClassesByPupils(Long id) {
		
     String str = "select sc from SchoolClass sc right join fetch sc.pupils p right join fetch p.pupil pu where pu.id = :id";
		
		Query query = em.createQuery(str);
		query.setParameter("id", id);
		
		
		return query.getResultList();
		
	}

	
	public ProfessorSubjectClass addSubjectToClass(ProfessorSubjectClass professorSubjectClass) {
		
		
		return professorSubjectClassRepo.save(professorSubjectClass);
	}

	@SuppressWarnings("static-access")
	public boolean ifExistsConectonSchoolClassPupil(SchoolClass sc, Pupil pupil) {
		Optional<PupilsInClass> pupilClass = pupilsInClassRepo.findByPupilAndSchoolClass(pupil, sc);
		if( pupilClass.isPresent()) {
			return true;
		}else return false;
	}

	@SuppressWarnings("static-access")
	public boolean ifExistsConectonProfessorSubjectClass(ProfessorSubject professorSubject, SchoolClass sc) {
		Optional<ProfessorSubjectClass> psc = professorSubjectClassRepo.findByProfessorSubjectAndSchoolClass(professorSubject, sc);
		if( psc.isPresent()) {
			return true;
		}else return false;
	}
	
	public Optional<ProfessorSubjectClass> findByProfessorSubjectClass(ProfessorSubject professorSubject, SchoolClass sc) {
		return professorSubjectClassRepo.findByProfessorSubjectAndSchoolClass(professorSubject, sc);
	}
	
	public ProfessorSubjectClass deletePSC(Long id) {
		
		if (!professorSubjectClassRepo.findById(id).isPresent()) {
			return null;
		}
		ProfessorSubjectClass temp = professorSubjectClassRepo.findById(id).get();
		professorSubjectClassRepo.deleteById(id);
		return temp;
	}
	
	public Optional<PupilsInClass> findPupilsInClass(SchoolClass sc, Pupil pupil){
		return pupilsInClassRepo.findByPupilAndSchoolClass(pupil, sc);
	}
	public PupilsInClass deletePupilsInClass(Long id) {
		
		if (!pupilsInClassRepo.findById(id).isPresent()) {
			return null;
		}
		PupilsInClass temp = pupilsInClassRepo.findById(id).get();
		pupilsInClassRepo.deleteById(id);
		return temp;
	}
	public List<PupilsInClass> findConectionByPupil(Pupil pupil){
		return pupilsInClassRepo.findByPupil(pupil);
	}
	public List<PupilsInClass> findConectionBySchoolClass(SchoolClass schoolClass){
		return pupilsInClassRepo.findBySchoolClass(schoolClass);
	}
	public List<SchoolClass> findBySemestar(Semestar semestar){
		return schoolClassRepo.findBySemestar(semestar);
	}
	
	public List<ProfessorSubjectClass> findConectionPSC(ProfessorSubject professorSubject){
		return professorSubjectClassRepo.findByProfessorSubject(professorSubject);
	}
	
	
//	public SchoolClass findClassByPupilandSemestar(Long id, Semestar semestar) {
//		
//     String str = "select sc from SchoolClass sc right join fetch sc.pupils p right join fetch p.pupil pu "
//     		+ "left join fetch sc.semetar s where pu.id = :id "
//     		+ "and s.id = :semestar";
//		
//		Query query = em.createQuery(str);
//		query.setParameter("id", id);
//		query.setParameter("semestar", semestar);
//		
//		
//		return (SchoolClass) query.getSingleResult();
//		
//	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolClass> findClassByProfessor(Long id) {
		
     String str = "select sc from SchoolClass sc left join fetch sc.professors_subjects psc left join fetch psc.professorSubject ps "
     		+ "left join fetch ps.professor p where p.id = :id ";
		
		Query query = em.createQuery(str);
		query.setParameter("id", id);
		
		
		
		return query.getResultList();
		
	}
	
	@SuppressWarnings("unchecked")
	public List<SchoolClass> findClassByProfessorAndSubject(Long idP, Long idS) {
		
     String str = "select sc from SchoolClass sc left join fetch sc.professors_subjects psc left join fetch psc.professorSubject ps "
     		+ "left join fetch ps.professor p left join fetch ps.subject s where p.id = :idP and s.id = :idS";
		
		Query query = em.createQuery(str);
		query.setParameter("idP", idP);
		query.setParameter("idS", idS);
		
		
		
		return query.getResultList();
		
	}
	
	public SchoolClass findClassByPupilandSemestar(Long id, Semestar semestar) {
		
	     String str = "select sc from SchoolClass sc right join fetch sc.pupils p right join fetch p.pupil pu where pu.id = :id "
	     		+ "and sc.semestar = :semestar";
			
			Query query = em.createQuery(str);
			query.setParameter("id", id);
			query.setParameter("semestar", semestar);
			
			
			return (SchoolClass) query.getSingleResult();
			
		}
	
	public boolean ifExistsCode(String code) {

		if (schoolClassRepo.findByCode(code) != null) {
			return true;
		} else
			return false;

	}
	
}
