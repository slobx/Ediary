package com.iktpreobuka.final_project.controllers;

import java.time.LocalDate;
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
import com.iktpreobuka.final_project.entities.Activity;
import com.iktpreobuka.final_project.entities.Mark;
import com.iktpreobuka.final_project.entities.Parent;
import com.iktpreobuka.final_project.entities.Professor;
import com.iktpreobuka.final_project.entities.ProfessorSubject;
import com.iktpreobuka.final_project.entities.ProfessorSubjectClass;
import com.iktpreobuka.final_project.entities.Pupil;
import com.iktpreobuka.final_project.entities.PupilsInClass;
import com.iktpreobuka.final_project.entities.SchoolClass;
import com.iktpreobuka.final_project.entities.Semestar;
import com.iktpreobuka.final_project.entities.Subject;
import com.iktpreobuka.final_project.entities.dto.ActivityDTO;
import com.iktpreobuka.final_project.entities.dto.MarkDTO;
import com.iktpreobuka.final_project.entities.dto.MarksForMenyDTO;
import com.iktpreobuka.final_project.entities.dto.MarksProfessorDTO;
import com.iktpreobuka.final_project.entities.dto.ProfessorDTO;
import com.iktpreobuka.final_project.entities.dto.PupilDTO;
import com.iktpreobuka.final_project.entities.dto.PupilMarkDTO;
import com.iktpreobuka.final_project.entities.dto.SchoolClassDTO;
import com.iktpreobuka.final_project.entities.dto.SubjectDTO;
import com.iktpreobuka.final_project.models.EmailObject;
import com.iktpreobuka.final_project.services.ActivityService;
import com.iktpreobuka.final_project.services.EmailService;
import com.iktpreobuka.final_project.services.MarkService;
import com.iktpreobuka.final_project.services.ParentService;
import com.iktpreobuka.final_project.services.ProfessorService;
import com.iktpreobuka.final_project.services.PupilService;
import com.iktpreobuka.final_project.services.SchoolClassService;
import com.iktpreobuka.final_project.services.SemestarService;
import com.iktpreobuka.final_project.services.SubjectService;
import com.iktpreobuka.final_project.util.View;

@RestController
@RequestMapping(path = "/project/mark")
public class MarkController {

	private final Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MarkService markService;

	@Autowired
	private SchoolClassService scService;

	@Autowired
	private PupilService pupilService;

	@Autowired
	private ParentService parentService;

	@Autowired
	private ProfessorService professorService;
	@Autowired
	private SubjectService subjectService;

	@Autowired
	private ActivityService activityService;

	@Autowired
	private SemestarService semestarService;

	@Autowired
	private EmailService emailService;


	private String createErrorMessage(BindingResult result) {
		return result.getAllErrors().stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(" "));
	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<?> findByMarkId(@PathVariable Long id) {

		try {
			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {

				Activity activity = mark.get().getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
						professor.getCode());

				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());

				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());

				MarkDTO markDTO = new MarkDTO(mark.get().getId(), professorDTO, pupilDTO, subjectDTO, scDTO, acDTO,
						mark.get().getValue(), mark.get().getDate());
				logger.info("You successfuly listed mark. ");
				return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing mark with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Mark not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllMarksPrivate() {
		try {
			List<MarkDTO> list = new ArrayList<>();
			for (Mark mark : markService.getAllMarks()) {

				Activity activity = mark.getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getId(), activity.getName(), activity.getCode());

				Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(),
						professor.getSurname(), professor.getCode());

				Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getId(), subject.getName(), subject.getCode());

				Pupil pupil = mark.getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getId(), pupil.getName(), pupil.getSurname(), pupil.getCode());

				SchoolClass sc = mark.getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getId(), sc.getCode(), sc.getGrade(), sc.getName());

				MarkDTO markDTO = new MarkDTO(mark.getId(), professorDTO, pupilDTO, subjectDTO, scDTO, acDTO,
						mark.getValue(), mark.getDate());

				list.add(markDTO);
			}
			if (list.size() != 0) {
				logger.info("You successfuly listed all marks. ");
				return new ResponseEntity<Iterable<MarkDTO>>(list, HttpStatus.OK);
			}
			logger.error("Something went wrong when listing all marks. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Failed to list all marks"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addNewMark(@Valid @RequestBody MarkDTO newMark, BindingResult result) {
		try {
			if (result.hasErrors()) {

				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

		try {
			Professor professor = professorService.findbyUser(newMark.getProfessor().getProfessorUser().getUsername());
			// Optional<Professor> professor =
			// professorService.findById(newMark.getProfessor().getId());
			Optional<Subject> subject = subjectService.findById(newMark.getSubject().getId());
			Optional<SchoolClass> sc = scService.findById(newMark.getSchoolClass().getId());
			Optional<Pupil> pupil = pupilService.findById(newMark.getPupil().getId());
			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor,
					subject.get());
			Optional<ProfessorSubjectClass> professorSubjectClass = scService
					.findByProfessorSubjectClass(professorSubject.get(), sc.get());
			Optional<PupilsInClass> pupilsInClass = scService.findPupilsInClass(sc.get(), pupil.get());
			Activity activity = activityService.findActivityByName(newMark.getActivity().getName());
			LocalDate date = LocalDate.now();

			if (subject.isPresent() && sc.isPresent() && pupil.isPresent() && professorSubject.isPresent()
					&& professorSubjectClass.isPresent() && pupilsInClass.isPresent()) {
				Activity activityFinal = activityService.findActivityByName("final");
				if ((markService.findMarksByPupilAndProfessorAndActivity(pupilsInClass.get(),
						professorSubjectClass.get(), activityFinal)).size() != 0) {
					logger.error("You can not add marks when there is final mark that is set. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1, "You can not add marks when there is final mark that is set."),
							HttpStatus.BAD_REQUEST);

				}

				Mark newMarkEntity = new Mark(pupilsInClass.get(), professorSubjectClass.get(), newMark.getValue(),
						date, activity);
				markService.addNewMark(newMarkEntity);
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
						professor.getCode());

				SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());

				PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
						pupil.get().getCode());

				SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade());

				MarkDTO markDTO = new MarkDTO(professorDTO, pupilDTO, subjectDTO, scDTO, acDTO, newMark.getValue(),
						date);

				EmailObject object = new EmailObject();
				object.setTo(pupil.get().getParent().getUser_id().getEmail());
				object.setSubject(
						"Obavestenje o oceni Vaseg deteta " + pupil.get().getName() + " " + pupil.get().getSurname());
				String text = "Vase dete " + pupil.get().getName() + " " + pupil.get().getSurname()
						+ " je dobilo ocenu " + newMark.getValue() + " iz predmeta " + subject.get().getName()
						+ " ocenio profesor " + professor.getName() + " " + professor.getSurname()
						+ " . Za dalje informacije mozete kontaktirati mail skole .";

				object.setText(text);

				if (object == null || object.getTo() == null || object.getText() == null) {
					return null;
				}

				emailService.sendSimpleMessage(object);

				logger.info("You successfuly posted mark. ");

				return new ResponseEntity<>(markDTO, HttpStatus.OK);
			}
			logger.error("Some entities missing and adding new mark is not possible. ");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Some entities missing and adding new mark is not possible."),
					HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured " + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}")
	public ResponseEntity<?> updateMark(@Valid @RequestBody MarkDTO newMark, @PathVariable Long id,
			BindingResult result) {

		try {
			if (result.hasErrors()) {
				return new ResponseEntity<>(createErrorMessage(result), HttpStatus.BAD_REQUEST);
			} 

			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {
				ProfessorSubjectClass professorSubjectClass = mark.get().getProfessor();
				PupilsInClass pupilInClass = mark.get().getPupil();
				Activity activityFinal = activityService.findActivityByName("final");
				if ((markService.findMarksByPupilAndProfessorAndActivity(pupilInClass, professorSubjectClass,
						activityFinal)).size() != 0) {
					logger.error("You can not change marks when there is final mark that is set. ");
					return new ResponseEntity<RESTError>(
							new RESTError(1, "You can not change marks when there is final mark that is set."),
							HttpStatus.BAD_REQUEST);

				}
				mark.get().setValue(newMark.getValue());
				Activity activity = activityService.findActivityByName(newMark.getActivity().getName());
				mark.get().setActivity(activity);

				markService.updateMark(id, mark.get());

				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
						professor.getCode());

				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());

				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());

				MarkDTO markDTO = new MarkDTO(professorDTO, pupilDTO, subjectDTO, scDTO, acDTO, mark.get().getValue(),
						mark.get().getDate());

				logger.info("You successfuly updated mark. ");
				return new ResponseEntity<>(markDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when updating mark with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Mark not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Long id) {

		try {
			Optional<Mark> mark = markService.findById(id);
			if (mark.isPresent()) {

				Activity activity = mark.get().getActivity();
				ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

				Professor professor = mark.get().getProfessor().getProfessorSubject().getProfessor();
				ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
						professor.getCode());

				Subject subject = mark.get().getProfessor().getProfessorSubject().getSubject();
				SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

				Pupil pupil = mark.get().getPupil().getPupil();
				PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());

				SchoolClass sc = mark.get().getPupil().getSchoolClass();
				SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade());

				MarkDTO markDTO = new MarkDTO(professorDTO, pupilDTO, subjectDTO, scDTO, acDTO, mark.get().getValue(),
						mark.get().getDate());

				markService.deleteMark(id);
				logger.info("You successfuly deleted mark. ");
				return new ResponseEntity<MarkDTO>(markDTO, HttpStatus.OK);
			}
			logger.error("Something went wrong when deleting mark with given id. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Mark not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PUPIL")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/{id}")
	public ResponseEntity<?> findMarksByPupilId(@PathVariable Long id) {

		try {
			Optional<Pupil> pupil = pupilService.findById(id);
			Semestar semestar = semestarService.findIfActive(true);
			SchoolClass sc = scService.findClassByPupilandSemestar(id, semestar);

			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
			if (pupil.isPresent() && pc.isPresent()) {

				List<MarkDTO> marks = new ArrayList<>();
				for (Mark mark : markService.findByPupilInClass(pc.get())) {

					Activity activity = mark.getActivity();
					ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

					Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
					ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
							professor.getCode());

					Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
					SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

					MarkDTO markDTO = new MarkDTO(professorDTO, subjectDTO, scDTO, acDTO, mark.getValue(),
							mark.getDate());

					marks.add(markDTO);
				}

				if (marks.size() != 0) {

					PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
							pupil.get().getCode());
					PupilMarkDTO pupilsMarks = new PupilMarkDTO(pupilDTO, marks);
					logger.info("You successfuly listed all marks for pupil. " + pupilDTO.getName()
							+ pupilDTO.getSurname());
					return new ResponseEntity<PupilMarkDTO>(pupilsMarks, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong while listing all marks and pupil with given id is not present. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Pupil not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PUPIL")
	// @JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/loged/{username}")
	public ResponseEntity<?> findMarksByPupilLoged(@PathVariable String username) {

		try {
			Pupil pupil = pupilService.findbyUser(username);
			Semestar semestar = semestarService.findIfActive(true);
			SchoolClass sc = scService.findClassByPupilandSemestar(pupil.getId(), semestar);

			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil);
			if (pc.isPresent()) {

				List<MarkDTO> marks = new ArrayList<>();
				for (Mark mark : markService.findByPupilInClass(pc.get())) {

					Activity activity = mark.getActivity();
					ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

					Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
					ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
							professor.getCode());

					Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
					SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

					MarkDTO markDTO = new MarkDTO(mark.getId(), professorDTO, subjectDTO, scDTO, acDTO, mark.getValue(),
							mark.getDate());

					marks.add(markDTO);
				}

				if (marks.size() != 0) {

					PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());
					PupilMarkDTO pupilsMarks = new PupilMarkDTO(pupilDTO, marks);
					logger.info("You successfuly listed all marks for pupil. " + pupilDTO.getName()
							+ pupilDTO.getSurname());
					return new ResponseEntity<PupilMarkDTO>(pupilsMarks, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong while listing all marks and pupil. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Marks not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PUPIL")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/pupil/logedd")
	public ResponseEntity<?> findMarksByPupilLogedd(Authentication authentication) {

		try {
			Pupil pupil = pupilService.findbyUser(authentication.getName());
			Semestar semestar = semestarService.findIfActive(true);
			SchoolClass sc = scService.findClassByPupilandSemestar(pupil.getId(), semestar);

			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil);
			if (pc.isPresent()) {

				List<MarkDTO> marks = new ArrayList<>();
				for (Mark mark : markService.findByPupilInClass(pc.get())) {

					Activity activity = mark.getActivity();
					ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

					Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
					ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
							professor.getCode());

					Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
					SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

					SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

					MarkDTO markDTO = new MarkDTO(mark.getId(), professorDTO, subjectDTO, scDTO, acDTO, mark.getValue(),
							mark.getDate());

					marks.add(markDTO);
				}

				if (marks.size() != 0) {

					PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());
					PupilMarkDTO pupilsMarks = new PupilMarkDTO(pupilDTO, marks);
					logger.info("You successfuly listed all marks for pupil. " + pupilDTO.getName()
							+ pupilDTO.getSurname());
					return new ResponseEntity<PupilMarkDTO>(pupilsMarks, HttpStatus.OK);
				}
			}
			logger.error("Something went wrong while listing all marks and pupil. ");
			return new ResponseEntity<RESTError>(new RESTError(1, "Marks not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/professor/{idPr}/subject/{idS}/pupil/{idPu}")
	public ResponseEntity<?> findMarksByProfessorAndPupilId(@PathVariable Long idPr, @PathVariable Long idS,
			@PathVariable Long idPu) {

		try {
			Optional<Pupil> pupil = pupilService.findById(idPu);
			Optional<Subject> subject = subjectService.findById(idS);
			Optional<Professor> professor = professorService.findById(idPr);
			Semestar semestar = semestarService.findIfActive(true);
			SchoolClass sc = scService.findClassByPupilandSemestar(idPu, semestar);

			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor.get(),
					subject.get());
			Optional<ProfessorSubjectClass> professorSubjectClass = scService
					.findByProfessorSubjectClass(professorSubject.get(), sc);

			ProfessorDTO professorDTO = new ProfessorDTO(professor.get().getName(), professor.get().getSurname(),
					professor.get().getCode());
			SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
			SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

			if (pc.isPresent() && professorSubjectClass.isPresent()) {

				List<MarkDTO> marks = new ArrayList<>();
				for (Mark mark : markService.findByPupilAndSubject(pc.get(), professorSubjectClass.get())) {

					Activity activity = mark.getActivity();
					ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
					MarkDTO markDTO = new MarkDTO(acDTO, mark.getValue(), mark.getDate());

					marks.add(markDTO);
				}

				if (marks.size() != 0) {

					PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
							pupil.get().getCode());

					PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);

					MarksProfessorDTO marksProfessorDTO = new MarksProfessorDTO(professorDTO, pupilMarkDTO, subjectDTO,
							scDTO);
					logger.info("You successfuly listed marks for pupil " + pupilDTO.getName() + " "
							+ pupilDTO.getSurname() + " for subject " + subjectDTO.getName());
					return new ResponseEntity<MarksProfessorDTO>(marksProfessorDTO, HttpStatus.OK);
				}
				logger.error("Pupil does not have marks for this subject. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Pupil does not have marks for this subject. "),
						HttpStatus.BAD_REQUEST);
			}
			logger.error("Some entities missing and lisitng marks is not possible.");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Some entities missing and lisitng marks is not possible."),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/subject/{idS}/pupil/{idPu}")
	public ResponseEntity<?> findMarksByProfessorLogedForPupil(Authentication authentication, @PathVariable Long idS,
			@PathVariable Long idPu) {

		try {
			Optional<Pupil> pupil = pupilService.findById(idPu);
			Optional<Subject> subject = subjectService.findById(idS);
			Professor professor = professorService.findbyUser(authentication.getName());
			Semestar semestar = semestarService.findIfActive(true);
			SchoolClass sc = scService.findClassByPupilandSemestar(idPu, semestar);

			Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor,
					subject.get());
			Optional<ProfessorSubjectClass> professorSubjectClass = scService
					.findByProfessorSubjectClass(professorSubject.get(), sc);

			ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
					professor.getCode());
			SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
			SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

			if (pc.isPresent() && professorSubjectClass.isPresent()) {

				List<MarkDTO> marks = new ArrayList<>();
				for (Mark mark : markService.findByPupilAndSubject(pc.get(), professorSubjectClass.get())) {

					Activity activity = mark.getActivity();
					ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
					MarkDTO markDTO = new MarkDTO(acDTO, mark.getValue(), mark.getDate());

					marks.add(markDTO);
				}

				if (marks.size() != 0) {

					PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
							pupil.get().getCode());

					PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);

					MarksProfessorDTO marksProfessorDTO = new MarksProfessorDTO(professorDTO, pupilMarkDTO, subjectDTO,
							scDTO);
					logger.info("You successfuly listed marks for pupil " + pupilDTO.getName() + " "
							+ pupilDTO.getSurname() + " for subject " + subjectDTO.getName());
					return new ResponseEntity<MarksProfessorDTO>(marksProfessorDTO, HttpStatus.OK);
				}
				logger.error("Pupil does not have marks for this subject. ");
				return new ResponseEntity<RESTError>(new RESTError(1, "Pupil does not have marks for this subject. "),
						HttpStatus.BAD_REQUEST);
			}
			logger.error("Some entities missing and lisitng marks is not possible.");
			return new ResponseEntity<RESTError>(
					new RESTError(1, "Some entities missing and lisitng marks is not possible."),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured({ "ROLE_ADMIN", "ROLE_PROFESSOR" })
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/professor/{username}/subject/{idS}/class/{idSc}")
	public ResponseEntity<?> findMarksByProfessorAndClass(@PathVariable String username, @PathVariable Long idS,
			@PathVariable Long idSc) {

		try {
			Optional<Subject> subject = subjectService.findById(idS);
			Professor professor = professorService.findbyUser(username);
			// Optional<Professor> professor = professorService.findById(idPr);
			Optional<SchoolClass> sc = scService.findById(idSc);

			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor,
					subject.get());
			Optional<ProfessorSubjectClass> professorSubjectClass = scService
					.findByProfessorSubjectClass(professorSubject.get(), sc.get());

			ProfessorDTO professorDTO = new ProfessorDTO(professor.getId(), professor.getName(), professor.getSurname(),
					professor.getCode());
			SubjectDTO subjectDTO = new SubjectDTO(subject.get().getId(), subject.get().getName(),
					subject.get().getCode());
			SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getId(), sc.get().getCode(), sc.get().getGrade(),
					sc.get().getName());

			if (professorSubjectClass.isPresent()) {

				List<PupilMarkDTO> pupils = new ArrayList<>();
				for (Pupil pupil : pupilService.findPupilsByClass(idSc)) {
					Optional<PupilsInClass> pc = scService.findPupilsInClass(sc.get(), pupil);
					PupilDTO pupilDTO = new PupilDTO(pupil.getId(), pupil.getName(), pupil.getSurname(),
							pupil.getCode());
					List<MarkDTO> marks = new ArrayList<>();
					for (Mark mark : markService.findByPupilAndSubject(pc.get(), professorSubjectClass.get())) {

						Activity activity = mark.getActivity();
						ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
						MarkDTO markDTO = new MarkDTO(mark.getId(), acDTO, mark.getValue(), mark.getDate());
						marks.add(markDTO);
					}

					PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);
					pupils.add(pupilMarkDTO);
				}

				MarksForMenyDTO marksDTO = new MarksForMenyDTO(professorDTO, pupils, subjectDTO, scDTO);
				logger.info("You successfuly listed marks for school class " + scDTO.getName() + " for subject "
						+ subjectDTO.getName());
				return new ResponseEntity<MarksForMenyDTO>(marksDTO, HttpStatus.OK);
			}
			logger.error(
					"Something went wrong. There is no conection between Professor and Subject and School Class that you are trying to get.");
			return new ResponseEntity<RESTError>(
					new RESTError(1,
							"Something went wrong. There is no conection between "
									+ "Professor and Subject and School Class that you are trying to get."),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PROFESSOR")
	@JsonView(View.Private.class)
	@RequestMapping(method = RequestMethod.GET, value = "/subject/{idS}/class/{idSc}")
	public ResponseEntity<?> findMarksByProfessorLogedForClass(Authentication authentication, @PathVariable Long idS,
			@PathVariable Long idSc) {

		try {
			Optional<Subject> subject = subjectService.findById(idS);
			Professor professor = professorService.findbyUser(authentication.getName());
			Optional<SchoolClass> sc = scService.findById(idSc);

			Optional<ProfessorSubject> professorSubject = professorService.findByProfessorSubject(professor,
					subject.get());
			Optional<ProfessorSubjectClass> professorSubjectClass = scService
					.findByProfessorSubjectClass(professorSubject.get(), sc.get());

			ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
					professor.getCode());
			SubjectDTO subjectDTO = new SubjectDTO(subject.get().getName(), subject.get().getCode());
			SchoolClassDTO scDTO = new SchoolClassDTO(sc.get().getCode(), sc.get().getGrade(), sc.get().getName());

			if (professorSubjectClass.isPresent()) {

				List<PupilMarkDTO> pupils = new ArrayList<>();
				for (Pupil pupil : pupilService.findPupilsByClass(idSc)) {
					Optional<PupilsInClass> pc = scService.findPupilsInClass(sc.get(), pupil);
					PupilDTO pupilDTO = new PupilDTO(pupil.getName(), pupil.getSurname(), pupil.getCode());
					List<MarkDTO> marks = new ArrayList<>();
					for (Mark mark : markService.findByPupilAndSubject(pc.get(), professorSubjectClass.get())) {

						Activity activity = mark.getActivity();
						ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());
						MarkDTO markDTO = new MarkDTO(acDTO, mark.getValue(), mark.getDate());
						marks.add(markDTO);
					}

					PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);
					pupils.add(pupilMarkDTO);
				}

				MarksForMenyDTO marksDTO = new MarksForMenyDTO(professorDTO, pupils, subjectDTO, scDTO);
				logger.info("You successfuly listed marks for school class " + scDTO.getName() + " for subject "
						+ subjectDTO.getName());
				return new ResponseEntity<MarksForMenyDTO>(marksDTO, HttpStatus.OK);
			}
			logger.error(
					"Something went wrong. There is no conection between Professor and Subject and School Class that you are trying to get.");
			return new ResponseEntity<RESTError>(
					new RESTError(1,
							"Something went wrong. There is no conection between "
									+ "Professor and Subject and School Class that you are trying to get."),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_ADMIN")
	@JsonView(View.Admin.class)
	@RequestMapping(method = RequestMethod.GET, value = "/parent/{id}")
	public ResponseEntity<?> findMarksByParent(@PathVariable Long id) {

		try {
			Optional<Parent> parent = parentService.findById(id);
			if (parent.isPresent()) {

				List<PupilMarkDTO> pupilsMarks = new ArrayList<>();

				for (Pupil pupils : parent.get().getParent_pupils()) {
					try {
						Optional<Pupil> pupil = pupilService.findById(pupils.getId());
						Semestar semestar = semestarService.findIfActive(true);
						SchoolClass sc = scService.findClassByPupilandSemestar(pupils.getId(), semestar);

						Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
						PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
								pupil.get().getCode());

						if (pupil.isPresent() && pc.isPresent()) {
							List<MarkDTO> marks = new ArrayList<>();

							for (Mark mark : markService.findByPupilInClass(pc.get())) {

								Activity activity = mark.getActivity();
								ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

								Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
								ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(),
										professor.getSurname(), professor.getCode());

								Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
								SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

								SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

								MarkDTO markDTO = new MarkDTO(professorDTO, subjectDTO, scDTO, acDTO, mark.getValue(),
										mark.getDate());
								marks.add(markDTO);
							}
							PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);
							pupilsMarks.add(pupilMarkDTO);
						}
					} catch (Exception e) {
						logger.error("Something went wrong. ");
						return new ResponseEntity<RESTError>(new RESTError(2, "Exception  :" + e.getMessage()),
								HttpStatus.INTERNAL_SERVER_ERROR);
					}
				}
				logger.info("You successfuly listed marks for pupil. ");
				return new ResponseEntity<Iterable<PupilMarkDTO>>(pupilsMarks, HttpStatus.OK);
			}
			logger.error("Parent with given id is missing and lisitng marks is not possible.");
			return new ResponseEntity<RESTError>(new RESTError(1, "Parent not present"), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PARENT")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/parent/loged")
	public ResponseEntity<?> findMarksByParentLoged(Authentication authentication) {

		try {
			Parent parent = parentService.findbyUser(authentication.getName());

			List<PupilMarkDTO> pupilsMarks = new ArrayList<>();

			for (Pupil pupils : parent.getParent_pupils()) {
				try {
					Optional<Pupil> pupil = pupilService.findById(pupils.getId());
					Semestar semestar = semestarService.findIfActive(true);
					SchoolClass sc = scService.findClassByPupilandSemestar(pupils.getId(), semestar);

					Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
					PupilDTO pupilDTO = new PupilDTO(pupil.get().getName(), pupil.get().getSurname(),
							pupil.get().getCode());

					if (pupil.isPresent() && pc.isPresent()) {
						List<MarkDTO> marks = new ArrayList<>();

						for (Mark mark : markService.findByPupilInClass(pc.get())) {

							Activity activity = mark.getActivity();
							ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

							Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
							ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
									professor.getCode());

							Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
							SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

							SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

							MarkDTO markDTO = new MarkDTO(professorDTO, subjectDTO, scDTO, acDTO, mark.getValue(),
									mark.getDate());
							marks.add(markDTO);
						}
						PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);
						pupilsMarks.add(pupilMarkDTO);
					}
				} catch (Exception e) {
					logger.error("Something went wrong. ");
					return new ResponseEntity<RESTError>(new RESTError(2, "Exception  :" + e.getMessage()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			logger.info("You successfuly listed marks for pupil. ");
			return new ResponseEntity<Iterable<PupilMarkDTO>>(pupilsMarks, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	
	@Secured("ROLE_PARENT")
	@JsonView(View.Public.class)
	@RequestMapping(method = RequestMethod.GET, value = "/parent/loged/{username}")
	public ResponseEntity<?> findMarksByParentLogedd(@PathVariable String username) {

		try {
			Parent parent = parentService.findbyUser(username);

			List<PupilMarkDTO> pupilsMarks = new ArrayList<>();

			for (Pupil pupils : parent.getParent_pupils()) {
				try {
					Optional<Pupil> pupil = pupilService.findById(pupils.getId());
					Semestar semestar = semestarService.findIfActive(true);
					SchoolClass sc = scService.findClassByPupilandSemestar(pupils.getId(), semestar);

					Optional<PupilsInClass> pc = scService.findPupilsInClass(sc, pupil.get());
					PupilDTO pupilDTO = new PupilDTO(pupil.get().getId(), pupil.get().getName(),
							pupil.get().getSurname(), pupil.get().getCode());

					if (pupil.isPresent() && pc.isPresent()) {
						List<MarkDTO> marks = new ArrayList<>();

						for (Mark mark : markService.findByPupilInClass(pc.get())) {

							Activity activity = mark.getActivity();
							ActivityDTO acDTO = new ActivityDTO(activity.getName(), activity.getCode());

							Professor professor = mark.getProfessor().getProfessorSubject().getProfessor();
							ProfessorDTO professorDTO = new ProfessorDTO(professor.getName(), professor.getSurname(),
									professor.getCode());

							Subject subject = mark.getProfessor().getProfessorSubject().getSubject();
							SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode());

							SchoolClassDTO scDTO = new SchoolClassDTO(sc.getCode(), sc.getGrade(), sc.getName());

							MarkDTO markDTO = new MarkDTO(mark.getId(), professorDTO, subjectDTO, scDTO, acDTO,
									mark.getValue(), mark.getDate());
							marks.add(markDTO);
						}
						PupilMarkDTO pupilMarkDTO = new PupilMarkDTO(pupilDTO, marks);
						pupilsMarks.add(pupilMarkDTO);
					}
				} catch (Exception e) {
					logger.error("Something went wrong. ");
					return new ResponseEntity<RESTError>(new RESTError(2, "Exception  :" + e.getMessage()),
							HttpStatus.INTERNAL_SERVER_ERROR);
				}
			}
			logger.info("You successfuly listed marks for pupil. ");
			return new ResponseEntity<Iterable<PupilMarkDTO>>(pupilsMarks, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Something went wrong. ");
			return new ResponseEntity<RESTError>(new RESTError(2, "Exception occured :" + e.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
