/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.assessedevaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.AssessedEvaluation;
import ke.co.miles.ocena.entities.CourseOfSession;
import ke.co.miles.ocena.entities.EvaluatedQuestion;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class AssessedEvaluationRequests extends EntityRequests implements AssessedEvaluationRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public AssessedEvaluation addAssessedEvaluation(AssessedEvaluationDetails details) throws InvalidArgumentException {
        //Method for adding an assessed evaluation record to the database
        logger.log(Level.INFO, "Entered the method for adding an assessed evaluation record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("18-001");
        } else if (details.getEvaluatedQuestion() == null) {
            logger.log(Level.INFO, "The evaluated question is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getPercentageScore() != null) {
            if (details.getPercentageScore().trim().length() > 20) {
                logger.log(Level.INFO, "The percentage score is longer than 20 characters");
                throw new InvalidArgumentException("18-002");
            }
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestionDescription() != null) {
            if (details.getQuestionDescription().trim().length() > 200) {
                logger.log(Level.INFO, "The question description is longer than 200 characters");
                throw new InvalidArgumentException("18-002");
            }
        }

        //Creating a container to hold assessed evaluation record
        logger.log(Level.INFO, "Creating a container to hold assessed evaluation record");
        assessedEvaluation = new AssessedEvaluation();
        assessedEvaluation.setActive(details.getActive());
        if (details.getRating() != null) {
            if (details.getRating().length() >= 8) {
                assessedEvaluation.setRating(details.getRating().substring(0, 8));
            } else {
                assessedEvaluation.setRating(details.getRating());
            }
        } else {
            assessedEvaluation.setRating(details.getRating());
        }
        assessedEvaluation.setPercentageScore(details.getPercentageScore());
        assessedEvaluation.setQuestionDescription(details.getQuestionDescription());
        assessedEvaluation.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        assessedEvaluation.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        assessedEvaluation.setEvaluatedQuestion(em.find(EvaluatedQuestion.class, details.getEvaluatedQuestion().getId()));
        assessedEvaluation.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Adding an assessed evaluation record to the database
        logger.log(Level.INFO, "Adding an assessed evaluation record to the database");
        try {
            em.persist(assessedEvaluation);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("0-001");
        }

        //Returning details of the record created
        logger.log(Level.INFO, "Returning details of the record created");
        return assessedEvaluation;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluations(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation of a faculty from the database
        logger.log(Level.INFO, "Entered the method for retrieving assessed evaluation of a faculty from the database");

        //Retrieving assessed evaluation records from the database
        logger.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        q = em.createNamedQuery("AssessedEvaluation.findByEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        List<AssessedEvaluation> assessedEvaluations = new ArrayList<>();
        try {
            assessedEvaluations = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("18-002");
        }

        //Returning the details list of assessed evaluation records
        logger.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return convertAssessedEvaluationsToAssessedEvaluationDetailsList(assessedEvaluations);
    }

    @Override
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluationsByCourse(CourseOfSessionDetails courseOfSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation from the database
        logger.log(Level.INFO, "Entered the method for retrieving assessed evaluation from the database");

        //Retrieving assessed evaluation records from the database
        logger.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        q = em.createNamedQuery("AssessedEvaluation.findByCourseOfSessionId");
        q.setParameter("courseOfSessionId", courseOfSessionDetails.getId());
        List<AssessedEvaluation> assessedEvaluations = new ArrayList<>();
        try {
            assessedEvaluations = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("18-002");
        }

        //Returning the details list of assessed evaluation records
        logger.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return convertAssessedEvaluationsToAssessedEvaluationDetailsList(assessedEvaluations);
    }

    @Override
    public Map<QuestionCategoryDetails, List<AssessedEvaluationDetails>> retrieveAssessedEvaluationsByCategory(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation of a department from the database
        logger.log(Level.INFO, "Entered the method for retrieving assessed evaluation of a department from the database");

        //Retrieve question categories used in the evaluation session from the database
        logger.log(Level.INFO, "Retrieving question categories used in the evaluation session from the database");
        List<QuestionCategory> questionCategories;
        q = em.createNamedQuery("QuestionCategory.findAll");
        try {
            questionCategories = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while retrieving question categories from the database");
            throw new InvalidStateException("0-002");
        }

        //Retrieving assessed evaluation records from the database
        logger.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        Map<QuestionCategoryDetails, List<AssessedEvaluationDetails>> assessedEvaluationsMap = new HashMap<>();

        q = em.createNamedQuery("AssessedEvaluation.findByEvaluationSessionIdAndQuestionCategoryId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        for (QuestionCategory qc : questionCategories) {
            q.setParameter("questionCategoryId", qc.getId());
            try {
                assessedEvaluationsMap.put(questionCategoryService.convertQuestionCategoryToQuestionCategoryDetails(qc),
                        convertAssessedEvaluationsToAssessedEvaluationDetailsList(q.getResultList()));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
                throw new EJBException("18-002");
            }
        }

        //Returning the details list of assessed evaluation records
        logger.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return assessedEvaluationsMap;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editAssessedEvaluation(AssessedEvaluationDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an assessed evaluation record in the database
        logger.log(Level.INFO, "Entered the method for editing an assessed evaluation record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("18-001");
        } else if (details.getEvaluatedQuestion() == null) {
            logger.log(Level.INFO, "The evaluated question is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getEvaluationSession() == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getPercentageScore() != null) {
            if (details.getPercentageScore().trim().length() > 20) {
                logger.log(Level.INFO, "The percentage score is longer than 20 characters");
                throw new InvalidArgumentException("18-002");
            }
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("18-002");
        } else if (details.getQuestionDescription() != null) {
            if (details.getQuestionDescription().trim().length() > 200) {
                logger.log(Level.INFO, "The question description is longer than 200 characters");
                throw new InvalidArgumentException("18-002");
            }
        }

        //Creating a container to hold assessed evaluation record
        logger.log(Level.INFO, "Creating a container to hold assessed evaluation record");
        assessedEvaluation = em.find(AssessedEvaluation.class, details.getId());
        assessedEvaluation.setId(details.getId());
        assessedEvaluation.setActive(details.getActive());
        if (details.getRating() != null) {
            if (details.getRating().length() >= 8) {
                assessedEvaluation.setRating(details.getRating().substring(0, 8));
            } else {
                assessedEvaluation.setRating(details.getRating());
            }
        } else {
            assessedEvaluation.setRating(details.getRating());
        }
        assessedEvaluation.setPercentageScore(details.getPercentageScore());
        assessedEvaluation.setQuestionDescription(details.getQuestionDescription());
        assessedEvaluation.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        assessedEvaluation.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        assessedEvaluation.setEvaluatedQuestion(em.find(EvaluatedQuestion.class, details.getEvaluatedQuestion().getId()));
        assessedEvaluation.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Editing an assessed evaluation record in the database
        logger.log(Level.INFO, "Editing an assessed evaluation record in the database");
        try {
            em.merge(assessedEvaluation);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("18-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeAssessedEvaluation(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an assessed evaluation record from the database
        logger.log(Level.INFO, "Entered the method for removing an assessed evaluation record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("18-006");
        }

        //Removing an assessed evaluation record from the database
        logger.log(Level.INFO, "Removing an assessed evaluation record from the database");
        assessedEvaluation = em.find(AssessedEvaluation.class, id);
        try {
            em.remove(assessedEvaluation);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("18-004");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<AssessedEvaluationDetails> convertAssessedEvaluationsToAssessedEvaluationDetailsList(List<AssessedEvaluation> assessedEvaluations) {
        //Entered method for converting assessedEvaluations list to assessed evaluation details list
        logger.log(Level.FINE, "Entered method for converting assessedEvaluations list to assessed evaluation details list");

        //Convert list of assessedEvaluations to assessed evaluation details list
        logger.log(Level.FINE, "Convert list of assessedEvaluations to assessed evaluation details list");
        List<AssessedEvaluationDetails> details = new ArrayList<>();
        for (AssessedEvaluation a : assessedEvaluations) {
            details.add(convertAssessedEvaluationToAssessedEvaluationDetails(a));
        }

        //Returning converted assessed evaluation details list
        logger.log(Level.FINE, "Returning converted assessed evaluation details list");
        return details;
    }

    private AssessedEvaluationDetails convertAssessedEvaluationToAssessedEvaluationDetails(AssessedEvaluation assessedEvaluation) {
        //Entered method for converting assessed evaluation to assessed evaluation details
        logger.log(Level.FINE, "Entered method for converting assessedEvaluations to assessed evaluation details");

        //Convert list of assessed evaluation to assessed evaluation details
        logger.log(Level.FINE, "Convert list of assessed evaluation to assessed evaluation details");

        questionCategoryDetails = new QuestionCategoryDetails();
        questionCategoryDetails.setId(assessedEvaluation.getQuestionCategory().getId());

        evaluatedQuestionDetails = new EvaluatedQuestionDetails();
        evaluatedQuestionDetails.setId(assessedEvaluation.getEvaluatedQuestion().getId());
        evaluatedQuestionDetails.setMeansOfAnswering(MeansOfAnsweringDetail.getMeansOfAnswering(assessedEvaluation.getEvaluatedQuestion().getMeansOfAnswering().getId()));

        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(assessedEvaluation.getEvaluationSession().getId());

        AssessedEvaluationDetails details = new AssessedEvaluationDetails();
        details.setId(assessedEvaluation.getId());
        details.setRating(assessedEvaluation.getRating());
        details.setActive(assessedEvaluation.getActive());
        details.setVersion(assessedEvaluation.getVersion());
        details.setQuestionCategory(questionCategoryDetails);
        details.setEvaluatedQuestion(evaluatedQuestionDetails);
        details.setEvaluationSession(evaluationSessionDetails);
        details.setPercentageScore(assessedEvaluation.getPercentageScore());
        details.setQuestionDescription(assessedEvaluation.getQuestionDescription());

        //Returning converted assessed evaluation details
        logger.log(Level.FINE, "Returning converted assessed evaluation details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(AssessedEvaluationRequests.class.getSimpleName());

}
