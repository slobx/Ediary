package com.iktpreobuka.final_project.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.repositories.SemestarRepository;

@Service
public class SemestarServiceImpl implements SemestarService{

	@Autowired
	private SemestarRepository semestarRepo;
	
	
	public Iterable<Semestar> getAll() {
		return semestarRepo.findAll();
	}

	public Optional<Semestar> findById(Long id) {
		return semestarRepo.findById(id);
	}

	public Semestar addNew(Semestar newSemestar) {

		if(newSemestar.getValue().equals(1)) {
			String str = "first" + newSemestar.getStartDate().getYear()+ " / " + newSemestar.getEndDate().getYear();
			newSemestar.setName(str);
			
		}if(newSemestar.getValue().equals(2)) {
			String str = "second" + newSemestar.getStartDate().getYear()+ " / " + newSemestar.getEndDate().getYear();
			newSemestar.setName(str);
			
		}
		return semestarRepo.save(newSemestar);
	}
	
	public Semestar update(Long id, Semestar newSemestar) {

		if (newSemestar == null || !semestarRepo.findById(id).isPresent()) {
			return null;
		}

		Semestar temp = semestarRepo.findById(id).get();

		if(newSemestar.getValue().equals(1)) {
			String str = "first" + newSemestar.getStartDate().getYear()+ " / " + newSemestar.getEndDate().getYear();
			newSemestar.setName(str);
			
		}if(newSemestar.getValue().equals(2)) {
			String str = "second" + newSemestar.getStartDate().getYear()+ " / " + newSemestar.getEndDate().getYear();
			newSemestar.setName(str);
			
		}
		
		temp.setName(newSemestar.getName());
		temp.setCode(newSemestar.getCode());
		temp.setStartDate(newSemestar.getStartDate());
		temp.setEndDate(newSemestar.getEndDate());
		temp.setValue(newSemestar.getValue());
		temp.setVersion(newSemestar.getVersion());
		temp.setActive(newSemestar.isActive());
		
		
		

		return semestarRepo.save(temp);
	}

	public Semestar delete(Long id) {

		if (!semestarRepo.findById(id).isPresent()) {
			return null;
		}
		Semestar temp = semestarRepo.findById(id).get();
		semestarRepo.deleteById(id);
		return temp;
	}
	
	public boolean ifExists(String code) {
		
		if(semestarRepo.findByCode(code) != null) {
			return true;
		}else return false;
		
		
	}
	
	public Semestar findByCode(String code) {
		
		return semestarRepo.findByCode(code);
	}
	
//	public Semestar findByCode(String code) {
//		return semestarRepo.findByCode(code).get();
//		
//	}
	
	public Semestar findIfActive(boolean active) {
		
		return semestarRepo.findByActive(active);
	}
	
	public boolean ifExistsActive(boolean active) {
		if(semestarRepo.findByActive(active) != null) {
			return true;
		}else return false;
		
	}
	
}
