/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.coursesofinstance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.CourseOfInstance;
import ke.co.miles.ocena.entities.CourseOfSession;
import ke.co.miles.ocena.entities.EvaluationInstance;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class CourseOfInstanceRequests extends EntityRequests implements CourseOfInstanceRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public CourseOfInstanceDetails addCourseOfInstance(CourseOfInstanceDetails details) throws InvalidArgumentException {
        //Method for adding a course-of-instance record to the database
        logger.log(Level.INFO, "Entered the method for adding a course-of-instance record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_020_01");
        } else if (details.getCourseOfSession() == null) {
            logger.log(Level.INFO, "The course of session is null");
            throw new InvalidArgumentException("error_020_02");
        } else if (details.getEvaluationInstance() == null) {
            logger.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_020_03");
        }

        //Checking if the course of instance is a duplicate
        logger.log(Level.INFO, "Checking if the course of instance is a duplicate");
        q = em.createNamedQuery("CourseOfInstance.findByCourseOfSessionIdAndEvaluationInstanceId");
        q.setParameter("courseOfSessionId", details.getCourseOfSession().getId());
        q.setParameter("evaluationInstanceId", details.getEvaluationInstance().getId());
        try {
            courseOfInstance = (CourseOfInstance) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The course of instance is not set yet");
            courseOfInstance = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (courseOfInstance != null) {
            logger.log(Level.INFO, "The course of instance is already set");
            throw new InvalidArgumentException("error_020_04");
        }

        //Creating a container to hold the courses of instance record
        logger.log(Level.INFO, "Creating a container to hold the courses of instance record");
        courseOfInstance = new CourseOfInstance();
        courseOfInstance.setActive(details.getActive());
        courseOfInstance.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        courseOfInstance.setEvaluationInstance(em.find(EvaluationInstance.class, details.getEvaluationInstance().getId()));

        //Adding a course-of-instance record to the database
        logger.log(Level.INFO, "Adding a course-of-instance record to the database");
        try {
            em.persist(courseOfInstance);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return convertCourseOfInstanceToCourseOfInstanceDetails(courseOfInstance);

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<CourseOfInstanceDetails> retrieveCoursesOfInstance(Integer evaluationInstanceId) throws InvalidArgumentException, InvalidStateException {
        //Method retrieving the list of course-of-instance details
        logger.log(Level.INFO, "Entered the method for retrieving the list of course-of-instance details");

        //Check validity of the evaluation session details passed in
        logger.log(Level.INFO, "Check validity of the evaluation session details passed in");
        if (evaluationInstanceId == null) {
            logger.log(Level.INFO, "The evaluation instance unique identifier is null");
            throw new InvalidArgumentException("error_020_06");
        }

        //Retrieve the list of course-of-instance details
        logger.log(Level.INFO, "Retrieving the list of course-of-instance details");
        q = em.createNamedQuery("CourseOfInstance.findByEvaluationInstanceId");
        q.setParameter("evaluationInstanceId", evaluationInstanceId);
        List<CourseOfInstance> listOfCourseOfInstance = new ArrayList<>();
        try {
            listOfCourseOfInstance = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the list of course-of-instance details
        logger.log(Level.INFO, "Returning the list of course-of-instance details");
        return convertCourseOfInstancesToCourseOfInstanceDetailsList(listOfCourseOfInstance);
    }

    @Override
    public CourseOfInstanceDetails retrieveCourseOfInstance(EvaluationInstanceDetails evaluationInstanceDetails, CourseOfSessionDetails courseOfSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method retrieving the list of course-of-instance details
        logger.log(Level.INFO, "Entered the method for retrieving the list of course-of-instance details");

        //Check validity of the evaluation session details passed in
        logger.log(Level.INFO, "Check validity of the evaluation session details passed in");
        if (courseOfSessionDetails == null) {
            logger.log(Level.INFO, "The course of session is null");
            throw new InvalidArgumentException("error_020_02");
        } else if (courseOfSessionDetails.getId() == null) {
            logger.log(Level.INFO, "The course of session unique identifier is null");
            throw new InvalidArgumentException("error_020_05");
        } else if (evaluationInstanceDetails == null) {
            logger.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_020_03");
        } else if (evaluationInstanceDetails.getId() == null) {
            logger.log(Level.INFO, "The evaluation instance unique identifier is null");
            throw new InvalidArgumentException("error_020_06");
        }

        //Retrieve the list of course-of-instance details
        logger.log(Level.INFO, "Retrieving the list of course-of-instance details");
        q = em.createNamedQuery("CourseOfInstance.findByEvaluationInstanceIdAndCourseOfSessionId");
        q.setParameter("evaluationInstanceId", evaluationInstanceDetails.getId());
        q.setParameter("courseOfSessionId", courseOfSessionDetails.getId());
        courseOfInstance = new CourseOfInstance();
        try {
            courseOfInstance = (CourseOfInstance) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.SEVERE, "The course of session was not evaluated upon by this student");
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", ex);
            throw new EJBException("error_000_01");
        }

        //Return the list of course-of-instance details
        logger.log(Level.INFO, "Returning the list of course-of-instance details");
        return convertCourseOfInstanceToCourseOfInstanceDetails(courseOfInstance);
    }

    @Override
    public CourseOfInstanceDetails retrieveCourseOfInstance(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method retrieving a course of instance
        logger.log(Level.INFO, "Entered a course of instance");

        //Check validity of the evaluation session details passed in
        logger.log(Level.INFO, "Check validity of the evaluation session details passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the course of instance is null");
            throw new InvalidArgumentException("error_020_07");
        }

        //Retrieve the list of course-of-instance details
        logger.log(Level.INFO, "Retrieving the list of course-of-instance details");
        q = em.createNamedQuery("CourseOfInstance.findById");
        q.setParameter("id", id);
        courseOfInstance = new CourseOfInstance();
        try {
            courseOfInstance = (CourseOfInstance) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the course-of-instance details
        logger.log(Level.INFO, "Returning the course-of-instance details");
        return convertCourseOfInstanceToCourseOfInstanceDetails(courseOfInstance);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editCourseOfInstance(CourseOfInstanceDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a course-of-instance record in the database
        logger.log(Level.INFO, "Entered the method for editing a course-of-instance record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_020_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The course of instance unique identifier is null");
            throw new InvalidArgumentException("error_020_07");
        } else if (details.getCourseOfSession() == null) {
            logger.log(Level.INFO, "The course of session is null");
            throw new InvalidArgumentException("error_020_02");
        } else if (details.getEvaluationInstance() == null) {
            logger.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_020_03");
        }

        //Checking if the course of instance is a duplicate
        logger.log(Level.INFO, "Checking if the course of instance is a duplicate");
        q = em.createNamedQuery("CourseOfInstance.findByCourseOfSessionIdAndEvaluationInstanceId");
        q.setParameter("courseOfSessionId", details.getCourseOfSession().getId());
        q.setParameter("evaluationInstanceId", details.getEvaluationInstance().getId());
        try {
            courseOfInstance = (CourseOfInstance) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The course of instance is not set yet");
            courseOfInstance = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (courseOfInstance != null) {
            if (!courseOfInstance.getId().equals(details.getId())) {
                logger.log(Level.INFO, "The course of instance is already set");
                throw new InvalidArgumentException("error_020_04");
            }
        }

        //Creating a container to hold the courses of instance record
        logger.log(Level.INFO, "Creating a container to hold the courses of instance record");
        courseOfInstance = em.find(CourseOfInstance.class, details.getId());
        courseOfInstance.setId(details.getId());
        courseOfInstance.setActive(details.getActive());
        courseOfInstance.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        courseOfInstance.setEvaluationInstance(em.find(EvaluationInstance.class, details.getEvaluationInstance().getId()));

        //Editing a course-of-instance record in the database
        logger.log(Level.INFO, "Editing a course-of-instance record in the database");
        try {
            em.merge(courseOfInstance);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeCourseOfInstance(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a course-of-instance record from the database
        logger.log(Level.INFO, "Entered the method for removing a course-of-instance record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the course of instance is null");
            throw new InvalidArgumentException("error_020_07");
        }

        //Removing a course-of-instance record from the database
        logger.log(Level.INFO, "Removing a course-of-instance record from the database");
        courseOfInstance = em.find(CourseOfInstance.class, id);
        try {
            em.remove(courseOfInstance);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<CourseOfInstanceDetails> convertCourseOfInstancesToCourseOfInstanceDetailsList(List<CourseOfInstance> listOfCourseOfInstance) {
        //Entered method for converting listOfCourseOfInstance list to course-of-instance details list
        logger.log(Level.FINE, "Entered method for converting listOfCourseOfInstance list to course-of-instance details list");

        //Convert list  course-of-instance  to course-of-instance details list
        logger.log(Level.FINE, "Convert list  course-of-instance  to course-of-instance details list");
        List<CourseOfInstanceDetails> details = new ArrayList<>();
        for (CourseOfInstance d : listOfCourseOfInstance) {
            details.add(convertCourseOfInstanceToCourseOfInstanceDetails(d));
        }

        //Returning converted course-of-instance details list
        logger.log(Level.FINE, "Returning converted course-of-instance details list");
        return details;
    }

    private CourseOfInstanceDetails convertCourseOfInstanceToCourseOfInstanceDetails(CourseOfInstance courseOfInstance) {
        //Entered method for converting course-of-instance to course-of-instance details
        logger.log(Level.FINE, "Entered method for converting listOfCourseOfInstance to course-of-instance details");

        //Convert list of course-of-instance to course-of-instance details
        logger.log(Level.FINE, "Convert list of course-of-instance to course-of-instance details");

        courseOfSessionDetails = new CourseOfSessionDetails();
        courseOfSessionDetails.setId(courseOfInstance.getCourseOfSession().getId());

        evaluationInstanceDetails = new EvaluationInstanceDetails();
        evaluationInstanceDetails.setId(courseOfInstance.getEvaluationInstance().getId());

        CourseOfInstanceDetails details = new CourseOfInstanceDetails();
        details.setEvaluationInstance(evaluationInstanceDetails);
        details.setCourseOfSession(courseOfSessionDetails);
        details.setVersion(courseOfInstance.getVersion());
        details.setActive(courseOfInstance.getActive());
        details.setId(courseOfInstance.getId());

        //Returning converted course-of-instance details
        logger.log(Level.FINE, "Returning converted course-of-instance details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(CourseOfInstanceRequests.class.getSimpleName());

}
