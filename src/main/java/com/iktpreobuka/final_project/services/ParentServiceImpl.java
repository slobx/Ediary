package com.iktpreobuka.final_project.services;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.repositories.ParentRepository;

@Service
public class ParentServiceImpl implements ParentService {

	@Autowired
	private ParentRepository parentRepo;

	@PersistenceContext
	private EntityManager em;

	public Iterable<Parent> getAllParents() {
		return parentRepo.findAll();
	}

	public Optional<Parent> findById(Long id) {
		return parentRepo.findById(id);
	}

	public Parent addNewParent(Parent newParent) {

		return parentRepo.save(newParent);
	}

	public Parent updateParent(Long id, Parent newParent) {

		if (newParent == null || !parentRepo.findById(id).isPresent()) {
			return null;
		}

		Parent temp = parentRepo.findById(id).get();

		temp.setName(newParent.getName());
		temp.setSurname(newParent.getSurname());
		temp.setCode(newParent.getCode());
		temp.setVersion(newParent.getVersion());
		temp.setUser_id(newParent.getUser_id());

		return parentRepo.save(temp);
	}

	public Parent deleteParent(Long id) {

		if (!parentRepo.findById(id).isPresent()) {
			return null;
		}
		Parent temp = parentRepo.findById(id).get();
		parentRepo.deleteById(id);
		return temp;
	}

	public boolean ifExists(String code) {

		if (parentRepo.findByCode(code) != null) {
			return true;
		} else
			return false;

	}

	public Parent findByCode(String code) {

		return parentRepo.findByCode(code);
	}

	public Parent findbyUser(String username) {

		String str = "select p from Parent p left join fetch p.user_id u where u.username = :username";

		Query query = em.createQuery(str);
		query.setParameter("username", username);

		return (Parent) query.getSingleResult();
	}

}
