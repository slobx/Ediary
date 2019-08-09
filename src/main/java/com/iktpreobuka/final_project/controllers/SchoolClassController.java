package com.iktpreobuka.final_project.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.dto.ParentDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.services.ProfessorService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SemestarService;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/schoolclass")
public class SchoolClassController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SchoolClassService scService;

	@Autowired
	private PupilService pService;

	@Autowired
	private SemestarService smService;

	@Autowired
	private ProfessorService professorService;
	@Autowired
	private SubjectService subjectService;


	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllSCPrivate() {
		try {
			List<SchoolClassDTO> list = new ArrayList<>();
			for (SchoolClass sc : scService.getAll()) {
				Semestar sm = sc.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
						sm.getEndDate(), sm.getCode(), sm.isActive());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
						sc.getName());
				list.add(schoolClassDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all school classes. ");
				return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all school classes. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/admin")
	public ResponseEntity<?> getAllSCAdmin() {
		try {
			List<SchoolClassDTO> list = new ArrayList<>();
			for (SchoolClass sc : scService.getAll()) {
				Semestar sm = sc.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
						sm.getEndDate(), sm.getCode(), sm.isActive());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
						sc.getName());
				list.add(schoolClassDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all school classes. ");
				return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all school classes. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findBySCId(@PathVariable Long id) {

		try {
			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {
				Semestar sm = sc.get().getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
						sm.getEndDate(), sm.getCode(), sm.isActive());

				List<PupilDTO> pupilsInClass = new ArrayList<>();

				for (Pupil pupil : pService.findPupilsByClass(id)) {
					PupilDTO pupilDTO = new PupilDTO(pupil.getId(), pupil.getName(), pupil.getSurname(),
							pupil.getJmbg(), pupil.getCode());
					pupilsInClass.add(pupilDTO);
				}
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getId(), sc.get().getCode(), sc.get().getGrade(),
						smDTO, sc.get().getName(), pupilsInClass);
				logger.info("You successfuly listed school class " + scDTO.getName());
				return new ResponseEntity<SchoolClassDTO>(scDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing school class with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST) // , consumes = "application/json"
	public ResponseEntity<?> addNewSC(@Valid @RequestBody SchoolClassDTO newSchoolClass, BindingResult result) {
		try {
			if (result.hasErrors()) {

				logger.error("Something went wrong in posting new school class. Check input values. ");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (scService.ifExistsCode(newSchoolClass.getCode())) {
			logger.error("Code for school class is already in use. ");

			return new ResponseEntity<RESTError>(new RESTError(1, "Code for school class is already in use."),
					HttpStatus.BAD_REQUEST);

		}
		if (smService.ifExists(newSchoolClass.getSemestarDTO().getCode())) {

			Semestar semestar = smService.findByCode(newSchoolClass.getSemestarDTO().getCode());
			SemestarDTO smDTO = new SemestarDTO(semestar.getId(), semestar.getName(), semestar.getValue(),
					semestar.getStartDate(), semestar.getEndDate(), semestar.getCode(), semestar.isActive());

			SchoolClass newSCE = new SchoolClass(newSchoolClass.getCode(), newSchoolClass.getGrade(),
					newSchoolClass.getName(), semestar);

			scService.addNew(newSCE);
			SchoolClassDTO schoolClassDTO = new SchoolClassDTO(newSCE.getId(), newSCE.getCode(), newSCE.getGrade(),
					smDTO, newSCE.getName());
			logger.info("You successfuly posted school class " + schoolClassDTO.getName());
			return new ResponseEntity<>(schoolClassDTO, HttpStatus.OK);

		} else {
			logger.error("Semestar not present when adding new school class. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"), HttpStatus.BAD_REQUEST);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateSC(@Valid @RequestBody SchoolClassDTO newSchoolClass, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				logger.error("Something went wrong in updating school class. Check input values. ");
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 

			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {
				if (!sc.get().getCode().equals(newSchoolClass.getCode())) {
					if (scService.ifExistsCode(newSchoolClass.getCode())) {
						logger.error("Code for school class is already in use. ");

						return new ResponseEntity<RESTError>(
								new RESTError(1, "Code for school class is already in use."), HttpStatus.BAD_REQUEST);

					} else {
						sc.get().setCode(newSchoolClass.getCode());
					}
				}
				sc.get().setGrade(newSchoolClass.getGrade());
				sc.get().setName(newSchoolClass.getName());
				SemestarDTO smDTO = newSchoolClass.getSemestarDTO();

				if (smService.ifExists(smDTO.getCode())) {
					Semestar semestar = smService.findByCode(newSchoolClass.getSemestarDTO().getCode());
					sc.get().setSemestar(semestar);
				} else {
					logger.error("Semestar not present when updating new school class. ");
					return new ResponseEntity<RESTError>(new RESTError(1, "Semestar not present"),
							HttpStatus.BAD_REQUEST);
				}

				SchoolClass savedSchoolClass = scService.update(id, sc.get());
				SemestarDTO semestarDTO = new SemestarDTO(savedSchoolClass.getSemestar().getId(),
						savedSchoolClass.getSemestar().getName(), savedSchoolClass.getSemestar().getValue(),
						savedSchoolClass.getSemestar().getStartDate(), savedSchoolClass.getSemestar().getEndDate(),
						savedSchoolClass.getSemestar().getCode(), savedSchoolClass.getSemestar().isActive());
				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(savedSchoolClass.getId(), savedSchoolClass.getCode(),
						savedSchoolClass.getGrade(), semestarDTO, savedSchoolClass.getName());
				logger.info("You successfuly updated school class " + schoolClassDTO.getName());
				return new ResponseEntity<>(schoolClassDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating school class with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteBySCId(@PathVariable Long id) {

		try {
			Optional<SchoolClass> sc = scService.findById(id);
			if (sc.isPresent()) {

				List<PupilsInClass> pupilInClass = scService.findConectionBySchoolClass(sc.get());
				if (pupilInClass.size() != 0) {
					logger.error("You can not delete school class when there are pupils conected to it. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1, "You can not delete school class when there are pupils conected to it."),
							HttpStatus.BAD_REQUEST);

				} else {
					Semestar sm = sc.get().getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getStartDate(), sm.getEndDate(),
							sm.getCode());

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getId(), sc.get().getCode(), sc.get().getGrade(),
							smDTO, sc.get().getName());
					scService.delete(id);
					logger.info("You successfuly deleted school class " + scDTO.getName());
					return new ResponseEntity<SchoolClassDTO>(scDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when deleting school class with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "School class not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/{id}")
	public ResponseEntity<?> getAllSchoolClasesByPupil(@PathVariable Long id) {
		try {
			Optional<Pupil> p = pService.findById(id);
			if (p.isPresent()) {

				List<SchoolClassDTO> list = new ArrayList<>();
				for (SchoolClass sc : scService.findClassesByPupils(id)) {
					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
							sm.getEndDate(), sm.getCode(), sm.isActive());
					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
							sc.getName());
					list.add(schoolClassDTO);
				}

				if (list.size() != 0) {
					logger.info("You successfuly listed school classes for pupil. ");

					return new ResponseEntity<Iterable<SchoolClassDTO>>(list, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when listing school classes for pupil with given id. ");

			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured({ "ROLE_PUPIL", "ROLE_PARENT" })
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupilandclass/{id}")
	public ResponseEntity<?> getAllSchoolClasesByPupilId(@PathVariable Long id) {
		try {
			Optional<Pupil> p = pService.findById(id);
			Parent pt = p.get().getParent();
			ParentDTO ptDTO = new ParentDTO(pt.getId(), pt.getName(), pt.getSurname(), pt.getCode());
			if (p.isPresent()) {

				List<SchoolClassDTO> list = new ArrayList<>();
				for (SchoolClass sc : scService.findClassesByPupils(id)) {

					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
							sm.getEndDate(), sm.getCode(), sm.isActive());

					SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
							sc.getName());

					list.add(schoolClassDTO);
				}

				PupilDTO pupilDTO = new PupilDTO(p.get().getId(), p.get().getName(), p.get().getSurname(),
						p.get().getJmbg(), p.get().getCode(), list, ptDTO);

				if (list.size() != 0) {
					logger.info("You successfuly listed school classes for pupil. ");

					return new ResponseEntity<PupilDTO>(pupilDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when listing school classes for pupil with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all school classes"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idSc}/pupil/{idP}")
	public ResponseEntity<?> conectionProfessorSubject(@PathVariable Long idSc, @PathVariable Long idP) {

		try {
			Optional<SchoolClass> sc = scService.findById(idSc);
			Optional<Pupil> pupil = pService.findById(idP);
			if (sc.isPresent() && pupil.isPresent()
					&& !scService.ifExistsConectonSchoolClassPupil(sc.get(), pupil.get())) {

				scService.addNewPC(sc.get().getId(), pupil.get().getId());
				// professorService.update(id, professor.get());

				List<PupilDTO> list = new ArrayList<>();
				for (Pupil temp : pService.findPupilsByClass(sc.get().getId())) {
					PupilDTO pupilDTO = new PupilDTO(temp.getName(), temp.getSurname(), temp.getJmbg(), temp.getCode());
					list.add(pupilDTO);
				}

				Semestar sm = sc.get().getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getStartDate(), sm.getEndDate(),
						sm.getCode());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade(), smDTO,
						sc.get().getName(), list);
				logger.info("You successfuly added conection between pupil and school class. ");

				return new ResponseEntity<>(schoolClassDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when adding conection pupil and school class with given id. ");

			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil or School class are not present"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idP}/subject/{idS}/class/{idSC}")
	public ResponseEntity<?> conectionProfessorSubjectClass(@PathVariable Long idP, @PathVariable Long idS,
			@PathVariable Long idSC) {

		try {
			Optional<SchoolClass> sc = scService.findById(idSC);
			Optional<Professor> professor = professorService.findById(idP);
			Optional<Subject> subject = subjectService.findById(idS);

			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor.get(),
					subject.get());

			if (sc.isPresent() && professorSubject.isPresent()
					&& !scService.ifExistsConectonProfessorSubjectClass(professorSubject.get(), sc.get())) {

				ProfessorSubjectClass professorSubjectClass = new ProfessorSubjectClass(professorSubject.get(),
						sc.get());
				scService.addSubjectToClass(professorSubjectClass);

				Semestar sm = sc.get().getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getName(), sm.getValue(), sm.getStartDate(), sm.getEndDate(),
						sm.getCode());

				SchoolClassDTO schoolClassDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade(), smDTO,
						sc.get().getName());
				logger.info("You successfuly added conection betweenprofessor, subject and school class. ");

				return new ResponseEntity<>(schoolClassDTO, HttpStatus.OK);
			}
			logger.error(
					"Something went wrong when adding conection between professor, subject and school class with given id. ");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Professor or subject or school class are not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
