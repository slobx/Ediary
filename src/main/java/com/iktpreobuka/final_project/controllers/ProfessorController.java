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
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.final_project.controllers.util.RESTError;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.Role;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.User;
import com.iktpreobuka.final_project.entities.dto.ProfessorDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.RoleDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SemestarDTO;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.entities.dto.UserDTO;
import com.iktpreobuka.final_project.services.MarkService;
import com.iktpreobuka.final_project.services.ProfessorService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.services.UserService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/professor")
public class ProfessorController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProfessorService professorService;

	@Autowired
	private UserService userService;

	@Autowired
	private PupilService pupilService;

	@Autowired
	private MarkService markService;

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SchoolClassService scService;

	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@Secured({ "ROLE_PUPIL", "ROLE_PARENT" })
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/public")
	public ResponseEntity<?> getAllProfessorsPublic() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {

				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}

				ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(),
						professor.getSurname(), professor.getCode(), listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all professors. ");
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all professors. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/private")
	public ResponseEntity<?> getAllProfessorsPrivate() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {

				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(),
						professor.getSurname(), professor.getCode(), listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all professors. ");
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all professors. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
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
	public ResponseEntity<?> getAllProfessorsAdmin() {
		try {
			List<ProfessorDTO> list = new ArrayList<>();
			for (Professor professor : professorService.getAll()) {

				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(),
						professor.getSurname(), professor.getCode(), listSubjectDTO);
				list.add(professorDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all professors. ");
				return new ResponseEntity<Iterable<ProfessorDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all professors. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all Professors"),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByProfessorId(@PathVariable Long id) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {
				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(), temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
						professor.get().getSurname(), professor.get().getCode(), listSubjectDTO);
				logger.info("You successfuly listed professor. ");
				return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing professor with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/loged")
	public ResponseEntity<?> findByProfessorLoged(Authentication authentication) {

		try {
			User user = userService.findByUsername(authentication.getName());

			Role role = user.getRole();
			RoleDTO roleDTO = new RoleDTO(role.getName());

			UserDTO userDTO = new UserDTO(user.getId(), user.getEmail(), user.getUsername(), roleDTO);
			Professor professor = professorService.findbyUser(authentication.getName());

			List<SubjectDTO> listSubjectDTO = new ArrayList<>();
			for (Subject temp : professorService.findSubjectByProff(professor.getId())) {
				SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode());
				listSubjectDTO.add(subjectDTO);
			}
			ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(), professor.getSurname(),
					professor.getCode(), listSubjectDTO, userDTO);
			logger.info("You successfuly listed professor. ");
			return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/classesBySubject")
	public ResponseEntity<?> findClassesByProfessorLogedAndSubject(Authentication authentication) {

		try {

			Professor professor = professorService.findbyUser(authentication.getName());

			List<SubjectDTO> list = new ArrayList<>();
			List<SchoolClassDTO> listscDTO = new ArrayList<>();
			for (Subject temp : professorService.findSubjectByProff(professor.getId())) {

				for (SchoolClass sc : scService.findClassByProfessorAndSubject(professor.getId(), temp.getId())) {
					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
							sm.getEndDate(), sm.getCode(), sm.isActive());
					List<PupilDTO> pupilsAttendingClass = new ArrayList<>();
					for (Pupil pupil : pupilService.findPupilsByClass(sc.getId())) {

						PupilDTO pupilDTO = new PupilDTO(pupil.getId(), pupil.getName(), pupil.getSurname(),
								pupil.getCode());
						pupilsAttendingClass.add(pupilDTO);
					}

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
							sc.getName(), pupilsAttendingClass);
					listscDTO.add(scDTO);
				}
				SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode(), listscDTO);
				list.add(subjectDTO);
			}

			logger.info("You successfuly listed classes for professor and subject. ");
			return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/classesBySubject/{username}")
	public ResponseEntity<?> findClassesByProfessorAndSubject(@PathVariable String username) {

		try {

			Professor professor = professorService.findbyUser(username);

			List<SubjectDTO> list = new ArrayList<>();
			List<SchoolClassDTO> listscDTO = new ArrayList<>();
			for (Subject temp : professorService.findSubjectByProff(professor.getId())) {

				for (SchoolClass sc : scService.findClassByProfessorAndSubject(professor.getId(), temp.getId())) {
					Semestar sm = sc.getSemestar();
					SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
							sm.getEndDate(), sm.getCode(), sm.isActive());
					List<PupilDTO> pupilsAttendingClass = new ArrayList<>();
					for (Pupil pupil : pupilService.findPupilsByClass(sc.getId())) {

						PupilDTO pupilDTO = new PupilDTO(pupil.getId(), pupil.getName(), pupil.getSurname(),
								pupil.getCode());
						pupilsAttendingClass.add(pupilDTO);
					}

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), smDTO,
							sc.getName(), pupilsAttendingClass);
					listscDTO.add(scDTO);
				}
				SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode(), listscDTO);
				list.add(subjectDTO);
			}

			logger.info("You successfuly listed classes for professor and subject. ");
			return new ResponseEntity<Iterable<SubjectDTO>>(list, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/loged/classes/{username}")
	public ResponseEntity<?> findClassesByProfessor(@PathVariable String username) {

		try {

			Professor professor = professorService.findbyUser(username);

			List<SchoolClassDTO> listscDTO = new ArrayList<>();
			for (SchoolClass temp : scService.findClassByProfessor(professor.getId())) {
				Semestar sm = temp.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
						sm.getEndDate(), sm.getCode(), sm.isActive());

				SchoolClassDTO scDTO = new SchoolClassDTO(temp.getId(), temp.getCode(), temp.getGrade(), smDTO,
						temp.getName());
				listscDTO.add(scDTO);
			}

			logger.info("You successfuly listed school classes by professor. ");
			return new ResponseEntity<Iterable<SchoolClassDTO>>(listscDTO, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/loged/classes")
	public ResponseEntity<?> findClassesByProfessorLoged(Authentication authentication) {

		try {

			Professor professor = professorService.findbyUser(authentication.getName());

			List<SchoolClassDTO> listscDTO = new ArrayList<>();
			for (SchoolClass temp : scService.findClassByProfessor(professor.getId())) {
				Semestar sm = temp.getSemestar();
				SemestarDTO smDTO = new SemestarDTO(sm.getId(), sm.getName(), sm.getValue(), sm.getStartDate(),
						sm.getEndDate(), sm.getCode(), sm.isActive());

				SchoolClassDTO scDTO = new SchoolClassDTO(temp.getId(), temp.getCode(), temp.getGrade(), smDTO,
						temp.getName());
				listscDTO.add(scDTO);
			}

			logger.info("You successfuly listed school classes by professor. ");
			return new ResponseEntity<Iterable<SchoolClassDTO>>(listscDTO, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewProfessor(@Valid @RequestBody ProfessorDTO newProfessor, BindingResult result) {
		try {
			if (result.hasErrors()) {

				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
//			else {
//			professorValidator.validate(newProfessor, result);
//		}
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (professorService.ifExists(newProfessor.getCode())) {
			logger.error("Code for professor is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Code for professor is present"),
					HttpStatus.BAD_REQUEST);
		}
		if (userService.ifExists(newProfessor.getProfessorUser().getUsername())) {
			logger.error("Username for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Username for user is present"),
					HttpStatus.BAD_REQUEST);
		}
		if (userService.ifExistsEmail(newProfessor.getProfessorUser().getEmail())) {
			logger.error("Email for user is present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Email for user is present"), HttpStatus.BAD_REQUEST);

		}
		User professorUser = new User(newProfessor.getProfessorUser().getEmail(),
				newProfessor.getProfessorUser().getPassword(), newProfessor.getProfessorUser().getUsername());

		User thisUser = userService.addNewUser(professorUser, "ROLE_PROFESSOR");

		Professor newProfessorEntity = new Professor(newProfessor.getName(), newProfessor.getSurname(),
				newProfessor.getCode(), thisUser);

		Professor savedProfessor = professorService.addNew(newProfessorEntity);
		RoleDTO roleDTO = new RoleDTO(thisUser.getRole().getName());
		UserDTO userDTO = new UserDTO(savedProfessor.getUser_id().getEmail(), savedProfessor.getUser_id().getUsername(),
				roleDTO);
		ProfessorDTO professorDTO = new ProfessorDTO(savedProfessor.getId(), savedProfessor.getName(),
				savedProfessor.getSurname(), savedProfessor.getCode(), userDTO);

		logger.info("You successfuly posted professor. ");
		return new ResponseEntity<>(professorDTO, HttpStatus.OK);
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateProfessor(@Valid @RequestBody ProfessorDTO newProfessor, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}

			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {
				if (!professor.get().getCode().equals(newProfessor.getCode())) {
					if (professorService.ifExists(newProfessor.getCode())) {
						logger.error("Code for professor is present. ");
						return new ResponseEntity<RESTError>(new RESTError(1, "Code for professor is present"),
								HttpStatus.BAD_REQUEST);
					} else {
						professor.get().setCode(newProfessor.getCode());
					}
				}
				professor.get().setName(newProfessor.getName());
				professor.get().setSurname(newProfessor.getSurname());

				professorService.update(id, professor.get());

				List<SubjectDTO> listSubjectDTO = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getName(), temp.getCode());
					listSubjectDTO.add(subjectDTO);
				}
				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
						professor.get().getSurname(), professor.get().getCode(), listSubjectDTO);
				logger.info("You successfuly updated professor. ");
				return new ResponseEntity<>(professorDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating professor with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteByProfessorId(@PathVariable Long id) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			if (professor.isPresent()) {

				List<ProfessorSubject> subjects = professorService.findPSByProfessor(professor.get());
				if (subjects.size() != 0) {
					return new ResponseEntity<RESTError>(
							new RESTError(1,
									"You can not delete professor when there are subjects conected to him/her."),
							HttpStatus.BAD_REQUEST);

				} else {
					ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
							professor.get().getSurname(), professor.get().getCode());
					userService.deleteUser(professor.get().getUser_id().getId());
					professorService.delete(id);
					logger.info("You successfuly deleted professor. ");
					return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong when updating professor with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/subject/{idS}")
	public ResponseEntity<?> deleteConectionPS(@PathVariable Long id, @PathVariable Long idS) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			Optional<Subject> subject = subjectService.findById(idS);
			if (professor.isPresent() && subject.isPresent()) {
				Optional<ProfessorSubject> ps = professorService.findByProfessorSubject(professor.get(), subject.get());
				List<ProfessorSubjectClass> psc = scService.findConectionPSC(ps.get());
				if (psc.size() != 0) {
					logger.error(
							"You can not remove professor and subject because there is conection to school class. ");
					return new ResponseEntity<RESTError>(new RESTError(1,
							"You can not remove professor and subject because there is conection to school class."),
							HttpStatus.BAD_REQUEST);

				} else {
					professorService.deletePS(ps.get().getId());
					ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
							professor.get().getSurname(), professor.get().getCode());
					logger.info("You successfuly deleted conection between professor and subject. ");
					return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
				}

			}
			logger.error("Something went wrong when deleting conection professor and subject with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{idP}/subject/{idS}")
	public ResponseEntity<?> conectionProfessorSubject(@PathVariable Long idP, @PathVariable Long idS) {

		try {
			Optional<Professor> professor = professorService.findById(idP);
			Optional<Subject> subject = subjectService.findById(idS);
			if (professor.isPresent() && subject.isPresent()
					&& !professorService.ifExistsConectonProfessorSubject(professor.get(), subject.get())) {

				professorService.addNewPS(professor.get(), subject.get());
				// professorService.update(id, professor.get());

				List<SubjectDTO> list = new ArrayList<>();
				for (Subject temp : professorService.findSubjectByProff(professor.get().getId())) {
					SubjectDTO subjectDTO = new SubjectDTO(temp.getId(), temp.getName(), temp.getCode());
					list.add(subjectDTO);
				}

				ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
						professor.get().getSurname(), professor.get().getCode(), list);
				logger.info("You successfuly added conection between professor and subject. ");
				return new ResponseEntity<>(professorDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when adding conection professor and subject with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Professor not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}/subject/{idS}/class/{idSC}")
	public ResponseEntity<?> deleteConectionPSC(@PathVariable Long id, @PathVariable Long idS,
			@PathVariable Long idSC) {

		try {
			Optional<Professor> professor = professorService.findById(id);
			Optional<Subject> subject = subjectService.findById(idS);
			Optional<SchoolClass> sc = scService.findById(idSC);
			Optional<ProfessorSubject> ps = professorService.findByProfessorSubject(professor.get(), subject.get());
			Optional<ProfessorSubjectClass> psc = scService.findByProfessorSubjectClass(ps.get(), sc.get());
			if (psc.isPresent()) {
				List<Mark> marks = markService.findByClassAndSubject(psc.get());
				if (marks.size() != 0) {
					logger.error(
							"You can not remove conection beetwen professor,subject and school class because there are marks in that conection. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1, "You can not remove conection beetwen professor,subject and school class"
									+ " because there are marks in that conection."),
							HttpStatus.BAD_REQUEST);

				} else {
					scService.deletePSC(psc.get().getId());
					ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getId(), professor.get().getName(),
							professor.get().getSurname(), professor.get().getCode());
					logger.info("You successfuly deleted conection between professor and subject and school class. ");
					return new ResponseEntity<ProfessorDTO>(professorDTO, HttpStatus.OK);
				}
			}
			logger.error("Conection beetwen professor and subject and school class not present. ");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Conection beetwen professor and subject and school class not present"),
					HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
