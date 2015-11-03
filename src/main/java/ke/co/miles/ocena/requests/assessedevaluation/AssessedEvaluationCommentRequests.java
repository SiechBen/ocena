/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.assessedevaluation;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.AssessedEvaluation;
import ke.co.miles.ocena.entities.AssessedEvaluationComment;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.AssessedEvaluationCommentDetails;

/**
 * w
 *
 * @author Ben Siech
 */
@Stateless
public class AssessedEvaluationCommentRequests extends EntityRequests implements AssessedEvaluationCommentRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addAssessedEvaluationComment(AssessedEvaluationCommentDetails details) throws InvalidArgumentException {
        //Method for adding an assessed evaluation comment record to the database
        logger.log(Level.INFO, "Entered the method for adding an assessed evaluation comment record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("13-001");
        } else if (details.getComment() != null || details.getComment().trim().length() != 0) {
            if (details.getComment().trim().length() > 200) {
                logger.log(Level.INFO, "The assessed evaluation comment is longer than 200 characters");
                throw new InvalidArgumentException("13-003");
            }
        } else if (details.getAssessedEvaluation() == null) {
            logger.log(Level.INFO, "The assessed evaluation to which the assessed evaluation comment belongs is null");
            throw new InvalidArgumentException("13-006");
        }

        //Creating a container to hold assessed evaluation comment record
        logger.log(Level.INFO, "Creating a container to hold assessed evaluation comment record");
        assessedEvaluationComment = new AssessedEvaluationComment();
        assessedEvaluationComment.setComment(details.getComment());
        assessedEvaluationComment.setActive(details.getActive());
        assessedEvaluationComment.setAssessedEvaluation(em.find(AssessedEvaluation.class, details.getAssessedEvaluation().getId()));

        //Adding an assessed evaluation comment record to the database
        logger.log(Level.INFO, "Adding an assessed evaluation comment record to the database");
        try {
            em.persist(assessedEvaluationComment);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("0-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return assessedEvaluationComment.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<AssessedEvaluationCommentDetails> retrieveAssessedEvaluationComment(AssessedEvaluationDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation comment record from the database
        logger.log(Level.INFO, "Entered the method for retrieving assessed evaluation comment record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the assessed evaluation details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The assessed evaluation is null");
            throw new InvalidArgumentException("13-004");
        }

        //Finding the assessed evaluation comments
        logger.log(Level.INFO, "Finding the assessed evaluation comments");
        q = em.createNamedQuery("AssessedEvaluationComment.findByAssessedEvaluationId");
        q.setParameter("assessedEvaluationId", details.getId());
        List<AssessedEvaluationComment> assessedEvaluationComments = new ArrayList<>();
        try {
            assessedEvaluationComments = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("13-002");
        }

        //Returning the details list of assessed evaluation comment records
        logger.log(Level.INFO, "Returning the details list of assessed evaluation comment records");
        return convertAssessedEvaluationCommentsToAssessedEvaluationCommentDetailsList(assessedEvaluationComments);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editAssessedEvaluationComment(AssessedEvaluationCommentDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an assessed evaluation comment record in the database
        logger.log(Level.INFO, "Entered the method for editing an assessed evaluation comment record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("13-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The assessed evaluation comment's unique identifier is null");
            throw new InvalidArgumentException("13-009");
        } else if (details.getComment() != null || details.getComment().trim().length() != 0) {
            if (details.getComment().trim().length() > 200) {
                logger.log(Level.INFO, "The assessed evaluation comment is longer than 200 characters");
                throw new InvalidArgumentException("13-003");
            }
        } else if (details.getAssessedEvaluation() == null) {
            logger.log(Level.INFO, "The assessed evaluation to which the assessed evaluation comment belongs is null");
            throw new InvalidArgumentException("13-006");
        }

        //Creating a container to hold assessed evaluation comment record
        logger.log(Level.INFO, "Creating a container to hold assessed evaluation comment record");
        assessedEvaluationComment = new AssessedEvaluationComment();
        assessedEvaluationComment.setId(details.getId());
        assessedEvaluationComment.setComment(details.getComment());
        assessedEvaluationComment.setActive(details.getActive());
        assessedEvaluationComment.setAssessedEvaluation(em.find(AssessedEvaluation.class, details.getAssessedEvaluation().getId()));

        //Editing an assessed evaluation comment record in the database
        logger.log(Level.INFO, "Editing an assessed evaluation comment record in the database");
        try {
            em.merge(assessedEvaluationComment);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("0-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeAssessedEvaluationComment(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an assessed evaluation comment record from the database
        logger.log(Level.INFO, "Entered the method for removing an assessed evaluation comment record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("13-009");
        }

        //Removing an assessed evaluation comment record from the database
        logger.log(Level.INFO, "Removing an assessed evaluation comment record from the database");
        assessedEvaluationComment = em.find(AssessedEvaluationComment.class, id);
        try {
            em.remove(assessedEvaluationComment);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("0-004");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<AssessedEvaluationCommentDetails> convertAssessedEvaluationCommentsToAssessedEvaluationCommentDetailsList(List<AssessedEvaluationComment> assessedEvaluationComments) {
        //Entered method for converting assessed evaluation comments list to assessedEvaluationComment details list
        logger.log(Level.FINE, "Entered method for converting assessed evaluation comments list to assessed evaluation comment details list");

        //Convert list of assessed evaluation comments to assessedEvaluationComment details list
        logger.log(Level.FINE, "Convert list of assessed evaluation comments to assessed evaluation comment details list");
        List<AssessedEvaluationCommentDetails> details = new ArrayList<>();
        for (AssessedEvaluationComment a : assessedEvaluationComments) {
            details.add(convertAssessedEvaluationCommentToAssessedEvaluationCommentDetails(a));
        }

        //Returning converted assessedEvaluationComment details list
        logger.log(Level.FINE, "Returning converted assessed evaluation comment details list");
        return details;
    }

    private AssessedEvaluationCommentDetails convertAssessedEvaluationCommentToAssessedEvaluationCommentDetails(AssessedEvaluationComment assessedEvaluationComment) {
        //Entered method for converting assessedEvaluationComment to assessedEvaluationComment details
        logger.log(Level.FINE, "Entered method for converting assessed evaluation comments to assessed evaluation comment details");

        //Convert list of assessedEvaluationComment to assessedEvaluationComment details
        logger.log(Level.FINE, "Convert list of assessed evaluation comment to assessed evaluation comment details");
        assessedEvaluationDetails = new AssessedEvaluationDetails();
        AssessedEvaluationCommentDetails details = new AssessedEvaluationCommentDetails();

        try {
            assessedEvaluationDetails.setId(assessedEvaluationComment.getAssessedEvaluation().getId());

            details.setVersion(assessedEvaluationComment.getVersion());
            details.setComment(assessedEvaluationComment.getComment());
            details.setActive(assessedEvaluationComment.getActive());
            details.setAssessedEvaluation(assessedEvaluationDetails);
            details.setId(assessedEvaluationComment.getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "An error occurred during conversion of assessed evaluation comment to details");
        }

        //Returning converted assessedEvaluationComment details
        logger.log(Level.FINE, "Returning converted assessed evaluation comment details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(AssessedEvaluationCommentRequests.class.getSimpleName());

}
