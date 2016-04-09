/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationinstance;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.EvaluationInstance;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EvaluationInstanceRequests extends EntityRequests implements EvaluationInstanceRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public EvaluationInstanceDetails addEvaluationInstance(EvaluationInstanceDetails details) throws InvalidArgumentException {
        //Method for adding an evaluation instance record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding an evaluation instance record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_026_01");
        } else if (details.getAnonymousIdentity() == null || details.getAnonymousIdentity().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The anonymous identity is null");
            throw new InvalidArgumentException("error_026_02");
        } else if (details.getAnonymousIdentity().trim().length() > 120) {
            LOGGER.log(Level.INFO, "The anonymous identity is longer than 120 characters");
            throw new InvalidArgumentException("error_026_03");
        } else if (details.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_026_04");
        }

        //Check that the evaluation instance is unique to an evaluation session
        LOGGER.log(Level.INFO, "Checking that the evaluation instance is unique to an evaluation session");
        q = em.createNamedQuery("EvaluationInstance.findByAnonymousIdentityAndEvaluationSessionId");
        q.setParameter("anonymousIdentity", details.getAnonymousIdentity());
        q.setParameter("evaluationSessionId", details.getEvaluationSession().getId());
        try {
            evaluationInstance = (EvaluationInstance) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Evaluation instance is available for use");
            evaluationInstance = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (evaluationInstance != null) {
            LOGGER.log(Level.SEVERE, "Evaluation instance is already in use");
            return convertEvaluationInstanceToEvaluationInstanceDetails(evaluationInstance);
        }

        //Creating a container to hold evaluation instance record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluation instance record");
        evaluationInstance = new EvaluationInstance();
        evaluationInstance.setActive(details.getActive());
        evaluationInstance.setAnonymousIdentity(details.getAnonymousIdentity());
        evaluationInstance.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Adding an evaluation instance record to the database
        LOGGER.log(Level.INFO, "Adding an evaluation instance record to the database");
        try {
            em.persist(evaluationInstance);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return convertEvaluationInstanceToEvaluationInstanceDetails(evaluationInstance);

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<EvaluationInstanceDetails> retrieveEvaluationInstances(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving evaluation instance record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation instance record from the database");

        //Check validity of the evaluation session details
        LOGGER.log(Level.INFO, "Checking validity of the evaluation session details passed in");
        if (evaluationSessionDetails == null) {
            LOGGER.log(Level.INFO, "The evaluation session details are null");
            throw new InvalidArgumentException("error_026_04");
        } else if (evaluationSessionDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The evaluation session unique identifier is null");
            throw new InvalidArgumentException("error_026_05");
        }

        //Finding the evaluation instance
        LOGGER.log(Level.INFO, "Finding the evaluation instance");
        q = em.createNamedQuery("EvaluationInstance.findByEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        List<EvaluationInstance> evaluationInstances;
        try {
            evaluationInstances = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of evaluation instance records
        LOGGER.log(Level.INFO, "Returning the details list of evaluation instance records");
        return convertEvaluationInstancesToEvaluationInstanceDetailsList(evaluationInstances);
    }

    @Override
    public EvaluationInstance retrieveEvaluationInstance(EvaluationInstanceDetails evaluationInstanceDetails) throws InvalidArgumentException {
        //Method for retrieving evaluation instance record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation instance record from the database");

        //Check validity of the evaluation session details
        LOGGER.log(Level.INFO, "Checking validity of the evaluation session details passed in");
        if (evaluationInstanceDetails == null) {
            LOGGER.log(Level.INFO, "The evaluation instance details are null");
            throw new InvalidArgumentException("error_026_01");
        } else if (evaluationInstanceDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluation instancce is null");
            throw new InvalidArgumentException("error_026_06");
        }

        //Finding the evaluation instance
        LOGGER.log(Level.INFO, "Finding the evaluation instance");
        q = em.createNamedQuery("EvaluationInstance.findById");
        q.setParameter("id", evaluationInstanceDetails.getId());
        evaluationInstance = new EvaluationInstance();
        try {
            evaluationInstance = (EvaluationInstance) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the evaluation instance
        LOGGER.log(Level.INFO, "Returning the evaluation instance");
        return evaluationInstance;
    }

    @Override
    public EvaluationInstanceDetails retrieveEvaluationInstance(Integer evaluationInstanceId) throws InvalidArgumentException {
        //Method for retrieving evaluation instance record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving evaluation instance record from the database");

        //Check validity of the evaluation session details
        LOGGER.log(Level.INFO, "Checking validity of the evaluation session details passed in");
        if (evaluationInstanceId == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluation instance is null");
            throw new InvalidArgumentException("error_026_06");
        }

        //Finding the evaluation instance
        LOGGER.log(Level.INFO, "Finding the evaluation instance");
        q = em.createNamedQuery("EvaluationInstance.findById");
        q.setParameter("id", evaluationInstanceId);
        evaluationInstance = new EvaluationInstance();
        try {
            evaluationInstance = (EvaluationInstance) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the evaluation instance
        LOGGER.log(Level.INFO, "Returning the evaluation instance");
        return convertEvaluationInstanceToEvaluationInstanceDetails(evaluationInstance);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editEvaluationInstance(EvaluationInstanceDetails evaluationInstanceDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an evaluation instance record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing an evaluation instance record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (evaluationInstanceDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_026_01");
        } else if (evaluationInstanceDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluation instance's is null");
            throw new InvalidArgumentException("error_026_09");
        } else if (evaluationInstanceDetails.getAnonymousIdentity() == null || evaluationInstanceDetails.getAnonymousIdentity().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The anonymous identity is null");
            throw new InvalidArgumentException("error_026_02");
        } else if (evaluationInstanceDetails.getAnonymousIdentity().trim().length() > 120) {
            LOGGER.log(Level.INFO, "The anonymous identity is longer than 120 characters");
            throw new InvalidArgumentException("error_026_03");
        } else if (evaluationInstanceDetails.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_026_04");
        }

        //Check that the evaluation instance is unique to an evaluation session
        LOGGER.log(Level.INFO, "Checking that the evaluation instance is unique to an evaluation session");
        q = em.createNamedQuery("EvaluationInstance.findByAnonymousIdentityAndEvaluationSessionId");
        q.setParameter("anonymousIdentity", evaluationInstanceDetails.getAnonymousIdentity());
        q.setParameter("evaluationSessionId", evaluationInstanceDetails.getEvaluationSession().getId());
        try {
            evaluationInstance = (EvaluationInstance) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "EvaluationInstance is available for use");
            evaluationInstance = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (evaluationInstance != null) {
            if (!(evaluationInstance.getId().equals(evaluationInstanceDetails.getId()))) {
                LOGGER.log(Level.SEVERE, "Evaluation instance is already in use");
                throw new InvalidArgumentException("error_026_07");
            }
        }

        //Creating a container to hold evaluation instance record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluation instance record");
        evaluationInstance = new EvaluationInstance();
        evaluationInstance.setId(evaluationInstanceDetails.getId());
        evaluationInstance.setActive(evaluationInstanceDetails.getActive());
        evaluationInstance.setAnonymousIdentity(evaluationInstanceDetails.getAnonymousIdentity());
        evaluationInstance.setEvaluationSession(em.find(EvaluationSession.class, evaluationInstanceDetails.getEvaluationSession().getId()));

        //Editing an evaluation instance record in the database
        LOGGER.log(Level.INFO, "Editing an evaluation instance record in the database");
        try {
            em.merge(evaluationInstance);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeEvaluationInstance(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an evaluation instance record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing an evaluation instance record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_026_06");
        }

        //Removing an evaluation instance record from the database
        LOGGER.log(Level.INFO, "Removing an evaluation instance record from the database");
        evaluationInstance = em.find(EvaluationInstance.class, id);
        try {
            em.remove(evaluationInstance);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<EvaluationInstanceDetails> convertEvaluationInstancesToEvaluationInstanceDetailsList(List<EvaluationInstance> evaluationInstances) {
        //Entered method for converting evaluation instances list to evaluation instance details list
        LOGGER.log(Level.FINE, "Entered method for converting evaluation instances list to evaluation instance details list");

        //Convert list of evaluation instances to evaluation instance details list
        LOGGER.log(Level.FINE, "Convert list of evaluation instances to evaluation instance details list");
        List<EvaluationInstanceDetails> details = new ArrayList<>();
        for (EvaluationInstance e : evaluationInstances) {
            details.add(convertEvaluationInstanceToEvaluationInstanceDetails(e));
        }

        //Returning converted evaluation instance details list
        LOGGER.log(Level.FINE, "Returning converted evaluation instance details list");
        return details;
    }

    private EvaluationInstanceDetails convertEvaluationInstanceToEvaluationInstanceDetails(EvaluationInstance evaluationInstance) {
        //Entered method for converting evaluation instance to evaluation instance details
        LOGGER.log(Level.FINE, "Entered method for converting evaluation instances to evaluation instance details");

        //Convert list of evaluation instance to evaluation instance details
        LOGGER.log(Level.FINE, "Convert list of evaluation instance to evaluation instance details");
        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(evaluationInstance.getEvaluationSession().getId());

        EvaluationInstanceDetails details = new EvaluationInstanceDetails();
        details.setId(evaluationInstance.getId());
        details.setActive(evaluationInstance.getActive());
        details.setVersion(evaluationInstance.getVersion());
        details.setEvaluationSession(evaluationSessionDetails);
        details.setAnonymousIdentity(evaluationInstance.getAnonymousIdentity());

        //Returning converted evaluation instance details
        LOGGER.log(Level.FINE, "Returning converted evaluation instance details");
        return details;

    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(EvaluationInstanceRequests.class.getSimpleName());

}
