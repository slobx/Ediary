package com.iktpreobuka.final_project.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.repositories.ProfessorRepository;
import com.iktpreobuka.final_project.repositories.ProfessorSubjectRepository;

@Service
public class ProfessorServiceImpl implements ProfessorService{

	@Autowired
	private ProfessorRepository professorRepo;
	
	@Autowired
	private ProfessorSubjectRepository psRepo;
	
	
	@PersistenceContext
	private EntityManager em;
	
	public Iterable<Professor> getAll() {
		return professorRepo.findAll();
	}

	public Optional<Professor> findById(Long id) {
		return professorRepo.findById(id);
	}

	public Professor addNew(Professor newProfessor) {

		return professorRepo.save(newProfessor);
	}
	public Professor update(Long id, Professor newProfessor) {

		if (newProfessor == null || !professorRepo.findById(id).isPresent()) {
			return null;
		}

		Professor temp = professorRepo.findById(id).get();

		temp.setCode(newProfessor.getCode());
		temp.setName(newProfessor.getName());
		temp.setVersion(newProfessor.getVersion());
		temp.setSurname(newProfessor.getSurname());

		return professorRepo.save(temp);
	}

	public Professor delete(Long id) {

		if (!professorRepo.findById(id).isPresent()) {
			return null;
		}
		Professor temp = professorRepo.findById(id).get();
		professorRepo.deleteById(id);
		return temp;
	}
	
	public ProfessorSubject deletePS(Long id) {
		if(!psRepo.findById(id).isPresent()) {
			return null;
		}
		ProfessorSubject temp = psRepo.findById(id).get();
		psRepo.deleteById(id);
		return temp;
	}
	
	public ProfessorSubject addNewPS(Professor newProfessor, Subject newSubject) {
		
		ProfessorSubject ps = new ProfessorSubject(newProfessor,newSubject);

		return psRepo.save(ps);
	}
	
	public List<ProfessorSubject> findPSByProfessor(Professor professor){
		
		return psRepo.findByProfessor(professor);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<Subject> findSubjectByProff(Long id){
		
		String str = "select s from Subject s right join fetch s.professors ps right join fetch ps.professor p where p.id = :id";
		
		Query query = em.createQuery(str);
		query.setParameter("id", id);
		
		
		return query.getResultList();
	}
	
//	public Professor findByUser(Long id) {
//		
//		Optional<User> user = userRepo.findById(id);
//		
//		return professorRepo.findByUser(user.get());
//	}

	//@SuppressWarnings("unchecked")
	public Professor findbyUser(String username){
		
		String str = "select p from Professor p left join fetch p.user_id u where u.username = :username";
		
		Query query = em.createQuery(str);
		query.setParameter("username", username);
		
		
		return (Professor) query.getSingleResult();
	}
	
	public Optional<ProfessorSubject> findByProfessorSubject(Professor professor, Subject subject) {
		return psRepo.findByProfessorAndSubject(professor, subject);
	}

	@SuppressWarnings("static-access")
	public boolean ifExistsConectonProfessorSubject(Professor professor, Subject subject) {
		Optional<ProfessorSubject> professorSubject = psRepo.findByProfessorAndSubject(professor, subject);
		if( professorSubject.isPresent()) {
			return true;
		}else return false;
	}
	
	public boolean ifExists(String code) {
		
		if(professorRepo.findByCode(code) != null) {
			return true;
		}else return false;
		
		
	}
	
}
