/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluatedquestion;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.EvaluatedQuestion;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.entities.MeansOfAnswering;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.entities.RatingType;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EvaluatedQuestionRequests extends EntityRequests implements EvaluatedQuestionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addEvaluatedQuestion(EvaluatedQuestionDetails details) throws InvalidArgumentException {
        //Method for adding a evaluated question record to the database
        logger.log(Level.INFO, "Entered the method for adding a evaluated question record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("18-001");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestion() == null || details.getQuestion().trim().length() == 0) {
            logger.log(Level.INFO, "The question is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestion().trim().length() > 200) {
            logger.log(Level.INFO, "The question is longer than 200 characters");
            throw new InvalidArgumentException("18-002");
        } else if (details.getMeansOfAnswering() == null) {
            logger.log(Level.INFO, "The means of answering is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The category in which the question falls is null");
            throw new InvalidArgumentException("18-002");
        }

        //Creating a container to hold evaluated question record
        logger.log(Level.INFO, "Creating a container to hold evaluated question record");
        evaluatedQuestion = new EvaluatedQuestion();
        evaluatedQuestion.setActive(details.getActive());
        evaluatedQuestion.setQuestion(details.getQuestion());
        evaluatedQuestion.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));
        evaluatedQuestion.setMeansOfAnswering(em.find(MeansOfAnswering.class, details.getMeansOfAnswering().getId()));
        evaluatedQuestion.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        try {
            evaluatedQuestion.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Rating type is null - evaluated question is not answered by rating");
        }

        //Adding a evaluated question record to the database
        logger.log(Level.INFO, "Adding a evaluated question record to the database");
        try {
            em.persist(evaluatedQuestion);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("0-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return evaluatedQuestion.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<EvaluatedQuestionDetails> retrieveEvaluatedQuestions(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving question of a session from the database
        logger.log(Level.INFO, "Entered the method for retrieving question of a session from the database");

        //Check the validity of the evaluation session details passed in
        logger.log(Level.INFO, "Checking the validity of the evaluation session details passed in");
        if (evaluationSessionDetails == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("18-009");
        } else if (evaluationSessionDetails.getId() == null) {
            logger.log(Level.INFO, "The evaluation session's unique identifier is null");
            throw new InvalidArgumentException("18-009");
        }

        //Retrieving evaluated question records from the database
        logger.log(Level.INFO, "Retrieving evaluated question records from the database");
        q = em.createNamedQuery("EvaluatedQuestion.findByEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        List<EvaluatedQuestion> evaluatedQuestions = new ArrayList<>();
        try {
            evaluatedQuestions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("18-002");
        }

        //Returning the details list of evaluated question records
        logger.log(Level.INFO, "Returning the details list of evaluated question records");
        return convertEvaluatedQuestionsToEvaluatedQuestionDetailsList(evaluatedQuestions);
    }

    @Override
    public EvaluatedQuestion retrieveEvaluatedQuestion(EvaluatedQuestionDetails evaluatedQuestionDetails, EvaluationSessionDetails evaluationSessionDetails) {
        //Method for retrieving evaluated question from the database
        logger.log(Level.INFO, "Entered the method for retrieving question from the database");

        //Retrieving evaluated question records from the database
        logger.log(Level.INFO, "Retrieving evaluated question records from the database");
        q = em.createNamedQuery("EvaluatedQuestion.findByQuestionAndEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        q.setParameter("specificQuestion", evaluatedQuestionDetails.getQuestion());
        evaluatedQuestion = new EvaluatedQuestion();
        try {
            evaluatedQuestion = (EvaluatedQuestion) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("18-002");
        }

        //Returning the details list of evaluated question records
        logger.log(Level.INFO, "Returning the details list of evaluated question records");
        return evaluatedQuestion;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editEvaluatedQuestion(EvaluatedQuestionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a evaluated question record in the database
        logger.log(Level.INFO, "Entered the method for editing a evaluated question record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("18-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The question's unique identifier is null");
            throw new InvalidArgumentException("18-008");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestion() == null || details.getQuestion().trim().length() == 0) {
            logger.log(Level.INFO, "The question is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestion().trim().length() > 200) {
            logger.log(Level.INFO, "The question is longer than 200 characters");
            throw new InvalidArgumentException("18-002");
        } else if (details.getMeansOfAnswering() == null) {
            logger.log(Level.INFO, "The means of answering is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The category in which the question falls is null");
            throw new InvalidArgumentException("18-002");
        }

        //Creating a container to hold evaluated question record
        logger.log(Level.INFO, "Creating a container to hold evaluated question record");
        evaluatedQuestion = new EvaluatedQuestion();
        evaluatedQuestion.setId(details.getId());
        evaluatedQuestion.setActive(details.getActive());
        evaluatedQuestion.setQuestion(details.getQuestion());
        evaluatedQuestion.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));
        evaluatedQuestion.setMeansOfAnswering(em.find(MeansOfAnswering.class, details.getMeansOfAnswering().getId()));
        evaluatedQuestion.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        try {
            evaluatedQuestion.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Rating type is null - evaluated question is not answered by rating");
        }

        //Editing a evaluated question record in the database
        logger.log(Level.INFO, "Editing a evaluated question record in the database");
        try {
            em.merge(evaluatedQuestion);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("18-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeEvaluatedQuestion(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a evaluated question record from the database
        logger.log(Level.INFO, "Entered the method for removing a evaluated question record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("18-006");
        }

        //Removing a evaluated question record from the database
        logger.log(Level.INFO, "Removing a evaluated question record from the database");
        evaluatedQuestion = em.find(EvaluatedQuestion.class, id);
        try {
            em.remove(evaluatedQuestion);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("18-004");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<EvaluatedQuestionDetails> convertEvaluatedQuestionsToEvaluatedQuestionDetailsList(List<EvaluatedQuestion> evaluatedQuestions) {
        //Entered method for converting questions list to question details list
        logger.log(Level.FINE, "Entered method for converting questions list to question details list");

        //Convert list of questions to question details list
        logger.log(Level.FINE, "Convert list of questions to question details list");
        List<EvaluatedQuestionDetails> details = new ArrayList<>();
        for (EvaluatedQuestion a : evaluatedQuestions) {
            details.add(convertEvaluatedQuestionToEvaluatedQuestionDetails(a));
        }

        //Returning converted question details list
        logger.log(Level.FINE, "Returning converted question details list");
        return details;
    }

    private EvaluatedQuestionDetails convertEvaluatedQuestionToEvaluatedQuestionDetails(EvaluatedQuestion evaluatedQuestion) {
        //Entered method for converting question to question details
        logger.log(Level.FINE, "Entered method for converting questions to question details");

        //Convert list of question to question details
        logger.log(Level.FINE, "Convert list of question to question details");

        questionCategoryDetails = new QuestionCategoryDetails();
        questionCategoryDetails.setId(evaluatedQuestion.getQuestionCategory().getId());

        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(evaluatedQuestion.getEvaluationSession().getId());

        EvaluatedQuestionDetails details = new EvaluatedQuestionDetails();
        details.setId(evaluatedQuestion.getId());
        details.setActive(evaluatedQuestion.getActive());
        details.setVersion(evaluatedQuestion.getVersion());
        details.setQuestion(evaluatedQuestion.getQuestion());
        details.setQuestionCategory(questionCategoryDetails);
        details.setEvaluationSession(evaluationSessionDetails);
        details.setMeansOfAnswering(MeansOfAnsweringDetail.getMeansOfAnswering(evaluatedQuestion.getMeansOfAnswering().getId()));
        try {
            details.setRatingType(RatingTypeDetail.getRatingType(evaluatedQuestion.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "The rating type is null");
        }

        //Returning converted question details
        logger.log(Level.FINE, "Returning converted question details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(EvaluatedQuestionRequests.class.getSimpleName());

}
