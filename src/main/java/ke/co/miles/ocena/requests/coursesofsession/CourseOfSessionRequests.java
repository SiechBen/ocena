/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.coursesofsession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Course;
import ke.co.miles.ocena.entities.CourseOfSession;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.entities.Person;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class CourseOfSessionRequests extends EntityRequests implements CourseOfSessionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addCourseOfSession(CourseOfSessionDetails details) throws InvalidArgumentException {
        //Method for adding a course-of-session record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding a course-of-session record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_021_01");
        } else if (details.getCourse() == null) {
            LOGGER.log(Level.INFO, "The course is null");
            throw new InvalidArgumentException("error_021_02");
        } else if (details.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_021_03");
        } else if (details.getFacultyMember() == null) {
            LOGGER.log(Level.INFO, "The faculty member is null");
            throw new InvalidArgumentException("error_021_04");
        }

        courseOfSession = new CourseOfSession();
        q = em.createNamedQuery("CourseOfSession.findByCourseIdAndEvaluationSessionId");
        q.setParameter("courseId", details.getCourse().getId());
        q.setParameter("evaluationSessionId", details.getEvaluationSession().getId());
        try {
            courseOfSession = (CourseOfSession) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The course of session is unique");
            courseOfSession = null;
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred while checking if the course of sesison provided is unique to the evaluation session");
            throw new EJBException("error_000_01");
        }

        if (courseOfSession != null) {
            LOGGER.log(Level.INFO, "The course of session is already set");
            throw new InvalidArgumentException("error_021_05");
        }

        //Creating a container to hold the courses of session record
        LOGGER.log(Level.INFO, "Creating a container to hold the courses of session record");
        courseOfSession = new CourseOfSession();
        courseOfSession.setActive(details.getActive());
        courseOfSession.setCourse(em.find(Course.class, details.getCourse().getId()));
        courseOfSession.setFacultyMember(em.find(FacultyMember.class, details.getFacultyMember().getId()));
        courseOfSession.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Adding a course-of-session record to the database
        LOGGER.log(Level.INFO, "Adding a course-of-session record to the database");
        try {
            em.persist(courseOfSession);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return courseOfSession.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<CourseOfSessionDetails> retrieveCoursesOfSession(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method retrieving the list of course-of-session details
        LOGGER.log(Level.INFO, "Entered the method for retrieving the list of course-of-session details");

        //Check validity of the evaluation session details passed in
        LOGGER.log(Level.INFO, "Check validity of the evaluation session details passed in");
        if (evaluationSessionDetails == null) {
            LOGGER.log(Level.INFO, "The evaluation session to which the course of session belongs is null");
            throw new InvalidArgumentException("error_021_06");
        }

        //Retrieve the list of course-of-session details
        LOGGER.log(Level.INFO, "Retrieving the list of course-of-session details");
        q = em.createNamedQuery("CourseOfSession.findByEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        List<CourseOfSession> listOfCourseOfSession = new ArrayList<>();
        try {
            listOfCourseOfSession = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the list of course-of-session details
        LOGGER.log(Level.INFO, "Returning the list of course-of-session details");
        return convertCourseOfSessionsToCourseOfSessionDetailsList(listOfCourseOfSession);
    }

    @Override
    public Map<CourseOfSessionDetails, PersonDetails> retrievePersonByCourseOfSession(List<CourseOfSessionDetails> coursesOfSession) throws InvalidArgumentException {
        //Method retrieving map of person by course
        LOGGER.log(Level.INFO, "Entered the method for retrieving the map of person by course");

        //Check validity of the courses of session passed in
        LOGGER.log(Level.INFO, "Check validity of the courses of session passed in");
        if (coursesOfSession.isEmpty()) {
            LOGGER.log(Level.INFO, "The courses of session are not provided");
        }

        //Retrieve the matching people and populate the map
        LOGGER.log(Level.INFO, "Retrieving the people matching and populating the map");
        q = em.createNamedQuery("FacultyMember.findById");
        Query q2 = em.createNamedQuery("Person.findById");
        Map<CourseOfSessionDetails, PersonDetails> personByCourseOfSessionMap = new HashMap<>();
        for (CourseOfSessionDetails c : coursesOfSession) {
            try {
                q.setParameter("id", c.getFacultyMember().getId());
                facultyMember = (FacultyMember) q.getSingleResult();
                q2.setParameter("id", facultyMember.getPerson().getId());
                personByCourseOfSessionMap.put(c, personService.convertPersonToPersonDetails((Person) q2.getSingleResult()));
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                throw new EJBException("error_000_01");
            }

        }

        //Return the map
        LOGGER.log(Level.INFO, "Returning the map");
        return personByCourseOfSessionMap;

    }

    @Override
    public Map<FacultyMemberDetails, PersonDetails> retrievePersonByFacultyMember(List<FacultyMemberDetails> facultyMembers) throws InvalidArgumentException {
        //Method retrieving map of faculty members
        LOGGER.log(Level.INFO, "Entered the method for retrieving the map of faculty members");

        //Check validity of the faculty members passed in
        LOGGER.log(Level.INFO, "Check validity of the faculty members passed in");
        if (facultyMembers.isEmpty()) {
            LOGGER.log(Level.INFO, "The faculty members are not provided");
            throw new InvalidArgumentException("error_021_07");
        }

        //Retrieve the matching people and populate the map
        LOGGER.log(Level.INFO, "Retrieving the people matching and populating the map");
        q = em.createNamedQuery("Person.findById");
        Map<FacultyMemberDetails, PersonDetails> personByFacultyMemberMap = new HashMap<>();
        for (FacultyMemberDetails f : facultyMembers) {
            try {
                q.setParameter("id", f.getPerson().getId());
                personByFacultyMemberMap.put(f, personService.convertPersonToPersonDetails((Person) q.getSingleResult()));
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                throw new EJBException("error_000_01");
            }
        }

        //Return the map
        LOGGER.log(Level.INFO, "Returning the map");
        return personByFacultyMemberMap;

    }

    @Override
    public CourseOfSessionDetails retrieveCourseOfSession(Integer id) throws InvalidArgumentException {
        //Method for retrieving course-of-session details
        LOGGER.log(Level.INFO, "Entered the method for retrieving course-of-session details");

        //Check validity of the unique identifier passed in
        LOGGER.log(Level.INFO, "Check validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_021_08");
        }

        //Retrieve the course-of-session
        LOGGER.log(Level.INFO, "Retrieving the course-of-session");
        q = em.createNamedQuery("CourseOfSession.findById");
        q.setParameter("id", id);
        courseOfSession = new CourseOfSession();
        try {
            courseOfSession = (CourseOfSession) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the list of course-of-session details
        LOGGER.log(Level.INFO, "Returning the list of course-of-session details");
        return convertCourseOfSessionToCourseOfSessionDetails(courseOfSession);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editCourseOfSession(CourseOfSessionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a course-of-session record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a course-of-session record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_021_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The course-of-session's unique identifier is null");
            throw new InvalidArgumentException("error_021_08");
        } else if (details.getCourse() == null) {
            LOGGER.log(Level.INFO, "The course is null");
            throw new InvalidArgumentException("error_021_02");
        } else if (details.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_021_03");
        } else if (details.getFacultyMember() == null) {
            LOGGER.log(Level.INFO, "The faculty member is null");
            throw new InvalidArgumentException("error_021_04");
        }

        //Creating a container to hold the courses of session record
        LOGGER.log(Level.INFO, "Creating a container to hold the courses of session record");
        courseOfSession = new CourseOfSession();
        courseOfSession.setId(details.getId());
        courseOfSession.setActive(details.getActive());
        courseOfSession.setCourse(em.find(Course.class, details.getCourse().getId()));
        courseOfSession.setFacultyMember(em.find(FacultyMember.class, details.getFacultyMember().getId()));
        courseOfSession.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Editing a course-of-session record in the database
        LOGGER.log(Level.INFO, "Editing a course-of-session record in the database");
        try {
            em.merge(courseOfSession);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeCourseOfSession(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a course-of-session record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a course-of-session record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_021_08");
        }

        //Removing a course-of-session record from the database
        LOGGER.log(Level.INFO, "Removing a course-of-session record from the database");
        courseOfSession = em.find(CourseOfSession.class, id);
        try {
            em.remove(courseOfSession);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<CourseOfSessionDetails> convertCourseOfSessionsToCourseOfSessionDetailsList(List<CourseOfSession> listOfCourseOfSession) {
        //Entered method for converting listOfCourseOfSession list to course-of-session details list
        LOGGER.log(Level.FINE, "Entered method for converting listOfCourseOfSession list to course-of-session details list");

        //Convert list  course-of-session  to course-of-session details list
        LOGGER.log(Level.FINE, "Convert list  course-of-session  to course-of-session details list");
        List<CourseOfSessionDetails> details = new ArrayList<>();
        for (CourseOfSession d : listOfCourseOfSession) {
            details.add(convertCourseOfSessionToCourseOfSessionDetails(d));
        }

        //Returning converted course-of-session details list
        LOGGER.log(Level.FINE, "Returning converted course-of-session details list");
        return details;
    }

    private CourseOfSessionDetails convertCourseOfSessionToCourseOfSessionDetails(CourseOfSession courseOfSession) {
        //Entered method for converting course-of-session to course-of-session details
        LOGGER.log(Level.FINE, "Entered method for converting listOfCourseOfSession to course-of-session details");

        //Convert list of course-of-session to course-of-session details
        LOGGER.log(Level.FINE, "Convert list of course-of-session to course-of-session details");

        personDetails = new PersonDetails();
        personDetails.setId(Integer.SIZE);
        personDetails.setReferenceNumber(courseOfSession.getFacultyMember().getPerson().getReferenceNumber());
        personDetails.setFirstName(courseOfSession.getFacultyMember().getPerson().getFirstName());
        personDetails.setLastName(courseOfSession.getFacultyMember().getPerson().getLastName());

        facultyMemberDetails = new FacultyMemberDetails();
        facultyMemberDetails.setId(courseOfSession.getFacultyMember().getId());
        facultyMemberDetails.setPerson(personDetails);

        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(courseOfSession.getEvaluationSession().getId());
        evaluationSessionDetails.setAdmissionYear(courseOfSession.getEvaluationSession().getAdmissionYear());

        courseDetails = new CourseDetails();
        courseDetails.setId(courseOfSession.getCourse().getId());
        courseDetails.setCode(courseOfSession.getCourse().getCode());
        courseDetails.setTitle(courseOfSession.getCourse().getTitle());

        CourseOfSessionDetails details = new CourseOfSessionDetails();
        details.setEvaluationSession(evaluationSessionDetails);
        details.setVersion(courseOfSession.getVersion());
        details.setFacultyMember(facultyMemberDetails);
        details.setActive(courseOfSession.getActive());
        details.setId(courseOfSession.getId());
        details.setCourse(courseDetails);

        //Returning converted course-of-session details
        LOGGER.log(Level.FINE, "Returning converted course-of-session details");
        return details;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(CourseOfSessionRequests.class.getSimpleName());

}
