/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.course;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Course;
import ke.co.miles.ocena.entities.Degree;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class CourseRequests extends EntityRequests implements CourseRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addCourse(CourseDetails details) throws InvalidArgumentException {
        //Method for adding a course record to the database
        logger.log(Level.INFO, "Entered the method for adding a course record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("7-001");
        } else if (details.getTitle() == null || details.getTitle().trim().length() == 0) {
            logger.log(Level.INFO, "The course title is null");
            throw new InvalidArgumentException("7-002");
        } else if (details.getTitle().trim().length() > 120) {
            logger.log(Level.INFO, "The course title is longer than 120 characters");
            throw new InvalidArgumentException("7-003");
        } else if (details.getCode() == null || details.getCode().trim().length() == 0) {
            logger.log(Level.INFO, "The code is null");
            throw new InvalidArgumentException("7-002");
        } else if (details.getCode().trim().length() > 20) {
            logger.log(Level.INFO, "The code is longer than 20 characters");
            throw new InvalidArgumentException("7-003");
        } else if (details.getDegree() == null) {
            logger.log(Level.INFO, "The degree which offers the course is null");
            throw new InvalidArgumentException("7-004");
        }

        //Checking if the course title is a duplicate
        logger.log(Level.INFO, "Checking if the course title is a duplicate");
        q = em.createNamedQuery("Course.findByTitle");
        q.setParameter("title", details.getTitle());
        try {
            course = (Course) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Course is available for use");
            course = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("7-002");
        }
        if (course != null) {
            logger.log(Level.SEVERE, "Course title is already in use");
            throw new InvalidArgumentException("7-005");
        }

        //Checking if the code is a duplicate
        logger.log(Level.INFO, "Checking if the code is a duplicate");
        q = em.createNamedQuery("Course.findByCode");
        q.setParameter("code", details.getCode());
        try {
            course = (Course) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Course is available for use");
            course = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("7-002");
        }
        if (course != null) {
            logger.log(Level.SEVERE, "Code is already in use");
            throw new InvalidArgumentException("7-005");
        }

        //Creating a container to hold course record
        logger.log(Level.INFO, "Creating a container to hold course record");
        course = new Course();
        course.setTitle(details.getTitle());
        course.setActive(details.getActive());
        course.setCode(details.getCode());
        course.setDegree(em.find(Degree.class, details.getDegree().getId()));

        //Adding a course record to the database
        logger.log(Level.INFO, "Adding a course record to the database");
        try {
            em.persist(course);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("7-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return course.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<CourseDetails> retrieveCoursesOfDegree(Integer degreeId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving course records from the database
        logger.log(Level.INFO, "Entered the method for retrieving course records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the degree unique identifier passed in");
        if (degreeId == null) {
            logger.log(Level.INFO, "The degree which offers the course is null");
            throw new InvalidArgumentException("7-004");
        }

        //Retrieving course records from the database
        logger.log(Level.INFO, "Retrieving course records from the database");
        q = em.createNamedQuery("Course.findByDegreeId");
        q.setParameter("degreeId", degreeId);
        List<Course> courses = new ArrayList<>();
        try {
            courses = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("7-002");
        }

        //Returning the details list of course records
        logger.log(Level.INFO, "Returning the details list of course records");
        return convertCoursesToCourseDetailsList(courses);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editCourse(CourseDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a course record in the database
        logger.log(Level.INFO, "Entered the method for editing a course record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("7-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The course's unique identifier is null");
            throw new InvalidArgumentException("7-006");
        } else if (details.getTitle() == null || details.getTitle().trim().length() == 0) {
            logger.log(Level.INFO, "The course title is null");
            throw new InvalidArgumentException("7-002");
        } else if (details.getTitle().trim().length() > 120) {
            logger.log(Level.INFO, "The course title is longer than 120 characters");
            throw new InvalidArgumentException("7-003");
        } else if (details.getCode() == null || details.getCode().trim().length() == 0) {
            logger.log(Level.INFO, "The code is null");
            throw new InvalidArgumentException("7-002");
        } else if (details.getCode().trim().length() > 20) {
            logger.log(Level.INFO, "The code is longer than 20 characters");
            throw new InvalidArgumentException("7-003");
        } else if (details.getDegree() == null) {
            logger.log(Level.INFO, "The degree which offers the course is null");
            throw new InvalidArgumentException("7-004");
        }

        //Checking if the course title is a duplicate
        logger.log(Level.INFO, "Checking if the course title is a duplicate");
        q = em.createNamedQuery("Course.findByTitle");
        q.setParameter("title", details.getTitle());
        try {
            course = (Course) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Course title is available for use");
            course = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("7-002");
        }
        if (course != null) {
            if (!(course.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Course title is already in use");
                throw new InvalidArgumentException("7-005");
            }
        }

        //Checking if the code is a duplicate
        logger.log(Level.INFO, "Checking if the code is a duplicate");
        q = em.createNamedQuery("Course.findByCode");
        q.setParameter("code", details.getCode());
        try {
            course = (Course) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Course code is available for use");
            course = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("7-002");
        }
        if (course != null) {
            if (!(course.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Course code is already in use");
                throw new InvalidArgumentException("7-005");
            }
        }

        //Creating a container to hold course record
        logger.log(Level.INFO, "Creating a container to hold course record");
        course = em.find(Course.class, details.getId());
        course.setId(details.getId());
        course.setTitle(details.getTitle());
        course.setActive(details.getActive());
        course.setCode(details.getCode());
        course.setDegree(em.find(Degree.class, details.getDegree().getId()));

        //Editing a course record in the database
        logger.log(Level.INFO, "Editing a course record in the database");
        try {
            em.merge(course);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("7-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeCourse(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a course record from the database
        logger.log(Level.INFO, "Entered the method for removing a course record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("7-006");
        }

        //Removing a course record from the database
        logger.log(Level.INFO, "Removing a course record from the database");
        course = em.find(Course.class, id);
        try {
            em.remove(course);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("7-004");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<CourseDetails> convertCoursesToCourseDetailsList(List<Course> courses) {
        //Entered method for converting courses list to course details list
        logger.log(Level.FINE, "Entered method for converting courses list to course details list");

        //Convert list of courses to course details list
        logger.log(Level.FINE, "Convert list of courses to course details list");
        List<CourseDetails> details = new ArrayList<>();
        for (Course a : courses) {
            details.add(convertCourseToCourseDetails(a));
        }

        //Returning converted course details list
        logger.log(Level.FINE, "Returning converted course details list");
        return details;
    }

    private CourseDetails convertCourseToCourseDetails(Course course) {
        //Entered method for converting course to course details
        logger.log(Level.FINE, "Entered method for converting courses to course details");

        //Convert list of course to course details
        logger.log(Level.FINE, "Convert list of course to course details");
        degreeDetails = new DegreeDetails();
        degreeDetails.setId(course.getDegree().getId());

        CourseDetails details = new CourseDetails();
        details.setActive(course.getActive());
        details.setTitle(course.getTitle());
        details.setId(course.getId());
        details.setCode(course.getCode());
        details.setDegree(degreeDetails);
        details.setVersion(course.getVersion());

        //Returning converted course details
        logger.log(Level.FINE, "Returning converted course details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(CourseRequests.class.getSimpleName());

}
