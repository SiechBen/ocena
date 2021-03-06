/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationsession;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Degree;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.exceptions.DuplicateStateException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EvaluationSessionRequests extends EntityRequests implements EvaluationSessionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addEvaluationSession(EvaluationSessionDetails details) throws InvalidArgumentException, DuplicateStateException {
        //Method for adding an evaluation session record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding an evaluation session record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_024_01");
        } else if (details.getStartDate() == null) {
            LOGGER.log(Level.INFO, "The evaluation session start date is null");
            throw new InvalidArgumentException("error_024_02");
        } else if (details.getEndDate() == null) {
            LOGGER.log(Level.INFO, "The evaluation session end date is null");
            throw new InvalidArgumentException("error_024_03");
        } else if (details.getAcademicYear() == null || details.getAcademicYear().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The academic year is null");
            throw new InvalidArgumentException("error_024_04");
        } else if (details.getAcademicYear().trim().length() > 45) {
            LOGGER.log(Level.INFO, "The academic year is longer than 45 characters");
            throw new InvalidArgumentException("error_024_05");
        } else if (details.getDegree() == null) {
            LOGGER.log(Level.INFO, "The degree is null");
            throw new InvalidArgumentException("error_024_06");
        } else if (details.getSemester() == null || details.getSemester().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The semester is null");
            throw new InvalidArgumentException("error_024_07");
        } else if (details.getSemester().trim().length() > 45) {
            LOGGER.log(Level.INFO, "The semester is longer than 45 characters");
            throw new InvalidArgumentException("error_024_08");
        } else if (details.getAdmissionYear() == null) {
            LOGGER.log(Level.INFO, "The admission year is null");
            throw new InvalidArgumentException("error_024_09");
        }

        //Check against any active evaluation session
        LOGGER.log(Level.INFO, "Checking against any active evaluation session");
        q = em.createNamedQuery("EvaluationSession.findActiveByDegreeIdAndAdmissionYear");
        evaluationSession = new EvaluationSession();
        q.setParameter("active", Boolean.TRUE);
        q.setParameter("degreeId", details.getDegree().getId());
        q.setParameter("admissionYear", details.getAdmissionYear());

        try {
            evaluationSession = (EvaluationSession) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No other active evaluation session");
            evaluationSession = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Creating a container to hold evaluation session record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluation session record");
        if (evaluationSession != null) {
            LOGGER.log(Level.SEVERE, "Another active evaluation session exists hence a new one cannot be created");
            throw new DuplicateStateException("18-099");
        }

        evaluationSession = new EvaluationSession();
        evaluationSession.setActive(details.getActive());
        evaluationSession.setEndDate(details.getEndDate());
        evaluationSession.setSemester(details.getSemester());
        evaluationSession.setStartDate(details.getStartDate());
        evaluationSession.setAcademicYear(details.getAcademicYear());
        evaluationSession.setAdmissionYear(details.getAdmissionYear());
        evaluationSession.setDegree(em.find(Degree.class, details.getDegree().getId()));

        //Adding an evaluation session record to the database
        LOGGER.log(Level.INFO, "Adding an evaluation session record to the database");
        try {
            em.persist(evaluationSession);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return evaluationSession.getId();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<EvaluationSessionDetails> retrieveEvaluationSessions(List<DegreeDetails> degrees) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving evaluation sessions from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation sessions from the database");

        //Check validity of the list of degrees 
        LOGGER.log(Level.INFO, "Checking validity of the list of degrees ");
        if (degrees == null) {
            LOGGER.log(Level.INFO, "The degrees are not provided");
            throw new InvalidArgumentException("error_024_11");
        } else if (degrees.isEmpty()) {
            LOGGER.log(Level.INFO, "The degrees are not provided");
            throw new InvalidArgumentException("error_024_11");
        }

        //Retrieving active evaluation session records from the database
        LOGGER.log(Level.INFO, "Retrieving active evaluation session records from the database");
        q = em.createNamedQuery("EvaluationSession.findByDegreeIdWhereActive");
        List<EvaluationSession> evaluationSessions = new ArrayList<>();
        List<EvaluationSession> holder;
        for (DegreeDetails d : degrees) {
            q.setParameter("degreeId", d.getId());
            q.setParameter("active", Boolean.TRUE);
            try {
                holder = q.getResultList();
                for (EvaluationSession es : holder) {
                    evaluationSessions.add(es);
                }
            } catch (NoResultException e) {
                LOGGER.log(Level.SEVERE, "No evaluation session record found");
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                throw new EJBException("error_000_01");
            }
        }

        //Returning the details of evaluation session records
        LOGGER.log(Level.INFO, "Returning the details of evaluation session records");
        return convertEvaluationSessionsToEvaluationSessionDetailsList(evaluationSessions);
    }

    @Override
    public EvaluationSessionDetails retrieveEvaluationSessionByDegree(Integer degreeId, Date admissionYear) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving evaluation sessions from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation sessions from the database");

        //Check validity of the degree unique identifier
        LOGGER.log(Level.INFO, "Checking validity of the degree unique identifier");
        if (degreeId == null) {
            LOGGER.log(Level.INFO, "The degree unique identifier is not provided");
            throw new InvalidArgumentException("error_024_12");
        } else if (admissionYear == null) {
            LOGGER.log(Level.INFO, "The admission year is not provided");
            throw new InvalidArgumentException("error_024_04");
        }

        //Retrieving active evaluation session record from the database
        LOGGER.log(Level.INFO, "Retrieving active evaluation session record from the database");
        q = em.createNamedQuery("EvaluationSession.findActiveByDegreeIdAndAdmissionYear");
        evaluationSession = new EvaluationSession();
        q.setParameter("admissionYear", admissionYear);
        q.setParameter("active", Boolean.TRUE);
        q.setParameter("degreeId", degreeId);

        try {
            evaluationSession = (EvaluationSession) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No evaluation session record found");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of evaluation session record
        LOGGER.log(Level.INFO, "Returning the details of evaluation session record");
        return convertEvaluationSessionToEvaluationSessionDetails(evaluationSession);
    }

    @Override
    public EvaluationSessionDetails retrieveEvaluationSession(Integer evaluationSessionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving evaluation session the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation session from the database");

        //Check validity of the evaluation session unique identifier
        LOGGER.log(Level.INFO, "Checking validity of the evaluation session unique identifier");
        if (evaluationSessionId == null) {
            LOGGER.log(Level.INFO, "The evaluation session unique identifier is null");
            throw new InvalidArgumentException("error_024_10");
        }

        //Retrieving evaluation session record from the database
        LOGGER.log(Level.INFO, "Retrieving evaluation session record from the database");
        q = em.createNamedQuery("EvaluationSession.findById");
        q.setParameter("id", evaluationSessionId);
        evaluationSession = new EvaluationSession();
        try {
            evaluationSession = (EvaluationSession) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_024_02");
        }

        //Returning the details of evaluation session records
        LOGGER.log(Level.INFO, "Returning the details of evaluation session records");
        return convertEvaluationSessionToEvaluationSessionDetails(evaluationSession);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editEvaluationSession(EvaluationSessionDetails details) throws InvalidArgumentException, InvalidStateException, DuplicateStateException {
        //Method for editing an evaluation session record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing an evaluation session record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_024_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The evaluation session's unique identifier is null");
            throw new InvalidArgumentException("error_024_10");
        } else if (details.getStartDate() == null) {
            LOGGER.log(Level.INFO, "The evaluation session start date is null");
            throw new InvalidArgumentException("error_024_02");
        } else if (details.getEndDate() == null) {
            LOGGER.log(Level.INFO, "The evaluation session end date is null");
            throw new InvalidArgumentException("error_024_03");
        } else if (details.getAcademicYear() == null || details.getAcademicYear().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The academic year is null");
            throw new InvalidArgumentException("error_024_04");
        } else if (details.getAcademicYear().trim().length() > 45) {
            LOGGER.log(Level.INFO, "The academic year is longer than 45 characters");
            throw new InvalidArgumentException("error_024_05");
        } else if (details.getDegree() == null) {
            LOGGER.log(Level.INFO, "The degree is null");
            throw new InvalidArgumentException("error_024_06");
        } else if (details.getSemester() == null || details.getSemester().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The semester is null");
            throw new InvalidArgumentException("error_024_07");
        } else if (details.getSemester().trim().length() > 45) {
            LOGGER.log(Level.INFO, "The semester is longer than 45 characters");
            throw new InvalidArgumentException("error_024_08");
        } else if (details.getAdmissionYear() == null) {
            LOGGER.log(Level.INFO, "The admission year is null");
            throw new InvalidArgumentException("error_024_09");
        }

        //Check against any active evaluation session
        LOGGER.log(Level.INFO, "Checking against any active evaluation session");
        q = em.createNamedQuery("EvaluationSession.findActiveByDegreeIdAndAdmissionYear");
        evaluationSession = new EvaluationSession();
        q.setParameter("active", Boolean.TRUE);
        q.setParameter("degreeId", details.getDegree().getId());
        q.setParameter("admissionYear", details.getAdmissionYear());

        try {
            evaluationSession = (EvaluationSession) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No other active evaluation session");
            evaluationSession = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Creating a container to hold evaluation session record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluation session record");
        if (evaluationSession != null) {
            if (!evaluationSession.getId().equals(details.getId())) {
                LOGGER.log(Level.SEVERE, "Another active evaluation session exists hence a new one cannot be created");
                throw new DuplicateStateException("error_024_10");
            }
        }
        evaluationSession = em.find(EvaluationSession.class, details.getId());
        evaluationSession.setActive(details.getActive());
        evaluationSession.setEndDate(details.getEndDate());
        evaluationSession.setSemester(details.getSemester());
        evaluationSession.setStartDate(details.getStartDate());
        evaluationSession.setAcademicYear(details.getAcademicYear());
        evaluationSession.setAdmissionYear(details.getAdmissionYear());
        evaluationSession.setDegree(em.find(Degree.class, details.getDegree().getId()));

        //Editing an evaluation session record in the database
        LOGGER.log(Level.INFO, "Editing an evaluation session record in the database");
        try {
            em.merge(evaluationSession);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeEvaluationSession(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an evaluation session record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing an evaluation session record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_024_10");
        }

        //Removing an evaluation session record from the database
        LOGGER.log(Level.INFO, "Removing an evaluation session record from the database");
        evaluationSession = em.find(EvaluationSession.class, id);
        try {
            em.remove(evaluationSession);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_024_04");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<EvaluationSessionDetails> convertEvaluationSessionsToEvaluationSessionDetailsList(List<EvaluationSession> evaluationSessions) {
        //Entered method for converting questions list to evaluation session details list
        LOGGER.log(Level.FINE, "Entered method for converting questions list to evaluation session details list");

        //Convert list of questions to evaluation session details list
        LOGGER.log(Level.FINE, "Convert list of questions to evaluation session details list");
        List<EvaluationSessionDetails> details = new ArrayList<>();
        for (EvaluationSession e : evaluationSessions) {
            details.add(convertEvaluationSessionToEvaluationSessionDetails(e));
        }

        //Returning converted evaluation session details list
        LOGGER.log(Level.FINE, "Returning converted evaluation session details list");
        return details;
    }

    private EvaluationSessionDetails convertEvaluationSessionToEvaluationSessionDetails(EvaluationSession evaluationSession) {
        //Entered method for converting evaluation session to evaluation session details
        LOGGER.log(Level.FINE, "Entered method for converting questions to evaluation session details");

        //Convert list of evaluation session to evaluation session details
        LOGGER.log(Level.FINE, "Convert list of evaluation session to evaluation session details");

        admissionDetails = new AdmissionDetails();
        admissionDetails.setId(evaluationSession.getDegree().getAdmission().getId());
        admissionDetails.setAdmission(evaluationSession.getDegree().getAdmission().getAdmission());

        degreeDetails = new DegreeDetails();
        try {
            degreeDetails.setId(evaluationSession.getDegree().getId());
            degreeDetails.setName(evaluationSession.getDegree().getName());
            degreeDetails.setAdmission(admissionDetails);
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "This evaluation session does not belong to a degree");
        }

        EvaluationSessionDetails details = new EvaluationSessionDetails();
        details.setDegree(degreeDetails);
        details.setId(evaluationSession.getId());
        details.setActive(evaluationSession.getActive());
        details.setVersion(evaluationSession.getVersion());
        details.setEndDate(evaluationSession.getEndDate());
        details.setSemester(evaluationSession.getSemester());
        details.setStartDate(evaluationSession.getStartDate());
        details.setAcademicYear(evaluationSession.getAcademicYear());
        details.setAdmissionYear(evaluationSession.getAdmissionYear());

        //Returning converted evaluation session details
        LOGGER.log(Level.FINE, "Returning converted evaluation session details");
        return details;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(EvaluationSessionRequests.class.getSimpleName());

}
