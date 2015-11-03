/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.studentfeedback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.StudentFeedback;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.StudentFeedbackDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class StudentFeedbackRequests extends EntityRequests implements StudentFeedbackRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addStudentFeedback(StudentFeedbackDetails details) throws InvalidArgumentException {
        //Method for adding a student feedback record to the database
        logger.log(Level.INFO, "Entered the method for adding a student feedback record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_002_01");
        } else if (details.getFeedback() == null || details.getFeedback().trim().length() == 0) {
            logger.log(Level.INFO, "The feedback is null");
            throw new InvalidArgumentException("error_002_02");
        } else if (details.getFeedback().trim().length() > 400) {
            logger.log(Level.INFO, "The feedback is longer than 400 characters");
            throw new InvalidArgumentException("error_002_03");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_002_04");
        } else if (details.getDateCompleted() == null) {
            logger.log(Level.INFO, "The date recorded is null");
            throw new InvalidArgumentException("error_002_05");
        } else if (details.getFacultyMember() == null) {
            logger.log(Level.INFO, "The faculty member is null");
            throw new InvalidArgumentException("error_002_06");
        }

        q = em.createNamedQuery("StudentFeedback.findByEvaluationSessionIdAndFacultyMemberId");
        q.setParameter("evaluationSessionId", details.getEvaluationSession().getId());
        q.setParameter("facultyMemberId", details.getFacultyMember().getId());
        try {
            studentFeedback = (StudentFeedback) q.getSingleResult();
        } catch (NoResultException e) {
            studentFeedback = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during student feedback record retrieval");
            throw new EJBException("error_000_01");
        }

        if (studentFeedback != null) {
            logger.log(Level.INFO, "A student feedback for this faculty member and session already exists");
            throw new InvalidArgumentException("error_002_07");
        }

        //Creating a container to hold student feedback record
        logger.log(Level.INFO, "Creating a container to hold student feedback record");
        studentFeedback = new StudentFeedback();
        studentFeedback.setActive(details.getActive());
        studentFeedback.setFeedback(details.getFeedback());
        studentFeedback.setDateCompleted(details.getDateCompleted());
        studentFeedback.setFacultyMember(em.find(FacultyMember.class, details.getFacultyMember().getId()));
        studentFeedback.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Adding a student feedback record to the database
        logger.log(Level.INFO, "Adding a student feedback record to the database");
        try {
            em.persist(studentFeedback);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return studentFeedback.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<StudentFeedbackDetails> retrieveStudentFeedbacks(FacultyMemberDetails facultyMember) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving student feedback record from the database
        logger.log(Level.INFO, "Entered the method for retrieving student feedback record from the database");

        //Check validity of the faculty member details
        logger.log(Level.INFO, "Checking validity of the faculty member details passed in");
        if (facultyMember == null) {
            logger.log(Level.INFO, "The faculty member details are null");
            throw new InvalidArgumentException("error_002_06");
        } else if (facultyMember.getId() == null) {
            logger.log(Level.INFO, "The faculty member unique identifier is null");
            throw new InvalidArgumentException("error_002_08");
        }

        //Finding the student feedback
        logger.log(Level.INFO, "Finding the student feedback");
        q = em.createNamedQuery("StudentFeedback.findByFacultyMemberId");
        q.setParameter("facultyMemberId", facultyMember.getId());
        List<StudentFeedback> studentFeedbacks;
        try {
            studentFeedbacks = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of student feedback records
        logger.log(Level.INFO, "Returning the details list of student feedback records");
        return convertStudentFeedbacksToStudentFeedbackDetailsList(studentFeedbacks);
    }

    @Override
    public StudentFeedback retrieveStudentFeedback(StudentFeedbackDetails studentFeedbackDetails) throws InvalidArgumentException {
        //Method for retrieving student feedback record from the database
        logger.log(Level.INFO, "Entered the method for retrieving student feedback record from the database");

        //Check validity of the evaluation session details
        logger.log(Level.INFO, "Checking validity of the evaluation session details passed in");
        if (studentFeedbackDetails == null) {
            logger.log(Level.INFO, "The student feedback details are null");
            throw new InvalidArgumentException("error_002_01");
        } else if (studentFeedbackDetails.getId() == null) {
            logger.log(Level.INFO, "The student feedback unique identifier is null");
            throw new InvalidArgumentException("error_002_09");
        }

        //Finding the student feedback
        logger.log(Level.INFO, "Finding the student feedback");
        q = em.createNamedQuery("StudentFeedback.findById");
        q.setParameter("id", studentFeedback.getId());
        studentFeedback = new StudentFeedback();
        try {
            studentFeedback = (StudentFeedback) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the student feedback
        logger.log(Level.INFO, "Returning the student feedback");
        return studentFeedback;
    }

    @Override
    public StudentFeedbackDetails retrieveStudentFeedback(Integer studentFeedbackId) throws InvalidArgumentException {
        //Method for retrieving student feedback record from the database
        logger.log(Level.INFO, "Entered the method for retrieving student feedback record from the database");

        //Check validity of the evaluation session details
        logger.log(Level.INFO, "Checking validity of the evaluation session details passed in");
        if (studentFeedbackId == null) {
            logger.log(Level.INFO, "The student feedback unique identifier is null");
            throw new InvalidArgumentException("error_002_09");
        }

        //Finding the student feedback
        logger.log(Level.INFO, "Finding the student feedback");
        q = em.createNamedQuery("StudentFeedback.findById");
        q.setParameter("id", studentFeedbackId);
        studentFeedback = new StudentFeedback();
        try {
            studentFeedback = (StudentFeedback) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the student feedback
        logger.log(Level.INFO, "Returning the student feedback");
        return convertStudentFeedbackToStudentFeedbackDetails(studentFeedback);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editStudentFeedback(StudentFeedbackDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a student feedback record in the database
        logger.log(Level.INFO, "Entered the method for editing a student feedback record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_002_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The student feedback's unique identifier is null");
            throw new InvalidArgumentException("error_002_09");
        } else if (details.getFeedback() == null || details.getFeedback().trim().length() == 0) {
            logger.log(Level.INFO, "The feedback is null");
            throw new InvalidArgumentException("error_002_02");
        } else if (details.getFeedback().trim().length() > 400) {
            logger.log(Level.INFO, "The feedback is longer than 400 characters");
            throw new InvalidArgumentException("error_002_03");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_002_04");
        } else if (details.getDateCompleted() == null) {
            logger.log(Level.INFO, "The date recorded is null");
            throw new InvalidArgumentException("error_002_05");
        } else if (details.getFacultyMember() == null) {
            logger.log(Level.INFO, "The faculty member is null");
            throw new InvalidArgumentException("error_002_06");
        }

        //Check that the correct feedback is being edited
        logger.log(Level.INFO, "Checking that the correct feedback is being edited");
        q = em.createNamedQuery("StudentFeedback.findByEvaluationSessionIdAndFacultyMemberId");
        q.setParameter("evaluationSessionId", details.getEvaluationSession().getId());
        q.setParameter("facultyMemberId", details.getFacultyMember().getId());
        try {
            studentFeedback = (StudentFeedback) q.getSingleResult();
        } catch (NoResultException e) {
            studentFeedback = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during student feedback record retrieval");
            throw new EJBException("error_000_01");
        }

        if (studentFeedback != null) {
            if (!studentFeedback.getId().equals(details.getId())) {
                logger.log(Level.INFO, "A student feedback for this faculty member and session already exists");
                throw new InvalidArgumentException("error_002_07");
            }
        }

        //Creating a container to hold student feedback record
        logger.log(Level.INFO, "Creating a container to hold student feedback record");
        studentFeedback = new StudentFeedback();
        studentFeedback.setId(details.getId());
        studentFeedback.setActive(details.getActive());
        studentFeedback.setFeedback(details.getFeedback());
        studentFeedback.setDateCompleted(details.getDateCompleted());
        studentFeedback.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));
        studentFeedback.setFacultyMember(em.find(FacultyMember.class, details.getFacultyMember().getId()));

        //Editing a student feedback record in the database
        logger.log(Level.INFO, "Editing a student feedback record in the database");
        try {
            em.merge(studentFeedback);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeStudentFeedback(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a student feedback record from the database
        logger.log(Level.INFO, "Entered the method for removing a student feedback record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_002_09");
        }

        //Removing a student feedback record from the database
        logger.log(Level.INFO, "Removing a student feedback record from the database");
        studentFeedback = em.find(StudentFeedback.class, id);
        try {
            em.remove(studentFeedback);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<StudentFeedbackDetails> convertStudentFeedbacksToStudentFeedbackDetailsList(List<StudentFeedback> studentFeedbacks) {
        //Entered method for converting student feedbacks list to student feedback details list
        logger.log(Level.FINE, "Entered method for converting student feedbacks list to student feedback details list");

        //Convert list of student feedbacks to student feedback details list
        logger.log(Level.FINE, "Convert list of student feedbacks to student feedback details list");
        List<StudentFeedbackDetails> details = new ArrayList<>();
        for (StudentFeedback e : studentFeedbacks) {
            details.add(convertStudentFeedbackToStudentFeedbackDetails(e));
        }

        //Returning converted student feedback details list
        logger.log(Level.FINE, "Returning converted student feedback details list");
        return details;
    }

    private StudentFeedbackDetails convertStudentFeedbackToStudentFeedbackDetails(StudentFeedback studentFeedback) {
        //Entered method for converting student feedback to student feedback details
        logger.log(Level.FINE, "Entered method for converting student feedbacks to student feedback details");

        //Convert list of student feedback to student feedback details
        logger.log(Level.FINE, "Convert list of student feedback to student feedback details");
        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(studentFeedback.getEvaluationSession().getId());

        facultyMemberDetails = new FacultyMemberDetails();
        facultyMemberDetails.setId(studentFeedback.getFacultyMember().getId());

        StudentFeedbackDetails details = new StudentFeedbackDetails();
        details.setId(studentFeedback.getId());
        details.setFacultyMember(facultyMemberDetails);
        details.setActive(studentFeedback.getActive());
        details.setVersion(studentFeedback.getVersion());
        details.setFeedback(studentFeedback.getFeedback());
        details.setEvaluationSession(evaluationSessionDetails);
        details.setDateCompleted(studentFeedback.getDateCompleted());

        //Returning converted student feedback details
        logger.log(Level.FINE, "Returning converted student feedback details");
        return details;

    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(StudentFeedbackRequests.class.getSimpleName());

}
