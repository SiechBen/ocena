/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.assessedevaluation;

import java.text.DecimalFormat;
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
        LOGGER.log(Level.INFO, "Entered the method for adding an assessed evaluation record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_010_01");
        } else if (details.getEvaluatedQuestion() == null) {
            LOGGER.log(Level.INFO, "The evaluated question is null");
            throw new InvalidArgumentException("error_010_02");
        } else if (details.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_010_03");
        } else if (details.getPercentageScore() != null) {
            if (details.getPercentageScore().trim().length() > 20) {
                LOGGER.log(Level.INFO, "The percentage score is longer than 20 characters");
                throw new InvalidArgumentException("error_010_04");
            }
        } else if (details.getQuestionCategory() == null) {
            LOGGER.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("error_010_05");
        } else if (details.getQuestionDescription() != null) {
            if (details.getQuestionDescription().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The question description is longer than 300 characters");
                throw new InvalidArgumentException("error_010_06");
            }
        }

        //Creating a container to hold assessed evaluation record
        LOGGER.log(Level.INFO, "Creating a container to hold assessed evaluation record");
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
        if (details.getStandardDeviation() != null) {
            assessedEvaluation.setStandardDeviation(Double.parseDouble(formatter.format(details.getStandardDeviation())));
        } else {
            assessedEvaluation.setStandardDeviation(details.getStandardDeviation());
        }
        assessedEvaluation.setPercentageScore(details.getPercentageScore());
        assessedEvaluation.setQuestionDescription(details.getQuestionDescription());
        assessedEvaluation.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        assessedEvaluation.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        assessedEvaluation.setEvaluatedQuestion(em.find(EvaluatedQuestion.class, details.getEvaluatedQuestion().getId()));
        assessedEvaluation.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Adding an assessed evaluation record to the database
        LOGGER.log(Level.INFO, "Adding an assessed evaluation record to the database");
        try {
            em.persist(assessedEvaluation);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning details of the record created
        LOGGER.log(Level.INFO, "Returning details of the record created");
        return assessedEvaluation;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluations(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation of a faculty from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving assessed evaluation of a faculty from the database");

        //Retrieving assessed evaluation records from the database
        LOGGER.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        q = em.createNamedQuery("AssessedEvaluation.findByEvaluationSessionId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        List<AssessedEvaluation> assessedEvaluations = new ArrayList<>();
        try {
            assessedEvaluations = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of assessed evaluation records
        LOGGER.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return convertAssessedEvaluationsToAssessedEvaluationDetailsList(assessedEvaluations);
    }

    @Override
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluationsByCourse(CourseOfSessionDetails courseOfSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving assessed evaluation from the database");

        //Retrieving assessed evaluation records from the database
        LOGGER.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        q = em.createNamedQuery("AssessedEvaluation.findByCourseOfSessionId");
        q.setParameter("courseOfSessionId", courseOfSessionDetails.getId());
        List<AssessedEvaluation> assessedEvaluations = new ArrayList<>();
        try {
            assessedEvaluations = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of assessed evaluation records
        LOGGER.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return convertAssessedEvaluationsToAssessedEvaluationDetailsList(assessedEvaluations);
    }

    @Override
    public Map<QuestionCategoryDetails, List<AssessedEvaluationDetails>> retrieveAssessedEvaluationsByCategory(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving assessed evaluation of a department from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving assessed evaluation of a department from the database");

        //Retrieve question categories used in the evaluation session from the database
        LOGGER.log(Level.INFO, "Retrieving question categories used in the evaluation session from the database");
        List<QuestionCategory> questionCategories;
        q = em.createNamedQuery("QuestionCategory.findAll");
        try {
            questionCategories = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred while retrieving question categories from the database", e);
            throw new InvalidStateException("error_000_01");
        }

        //Retrieving assessed evaluation records from the database
        LOGGER.log(Level.INFO, "Retrieving assessed evaluation records from the database");
        Map<QuestionCategoryDetails, List<AssessedEvaluationDetails>> assessedEvaluationsMap = new HashMap<>();

        q = em.createNamedQuery("AssessedEvaluation.findByEvaluationSessionIdAndQuestionCategoryId");
        q.setParameter("evaluationSessionId", evaluationSessionDetails.getId());
        for (QuestionCategory qc : questionCategories) {
            q.setParameter("questionCategoryId", qc.getId());
            try {
                assessedEvaluationsMap.put(questionCategoryService.convertQuestionCategoryToQuestionCategoryDetails(qc),
                        convertAssessedEvaluationsToAssessedEvaluationDetailsList(q.getResultList()));
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                throw new EJBException("error_000_01");
            }
        }

        //Returning the details list of assessed evaluation records
        LOGGER.log(Level.INFO, "Returning the details list of assessed evaluation records");
        return assessedEvaluationsMap;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editAssessedEvaluation(AssessedEvaluationDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an assessed evaluation record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing an assessed evaluation record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_010_01");
        } else if (details.getEvaluatedQuestion() == null) {
            LOGGER.log(Level.INFO, "The evaluated question is null");
            throw new InvalidArgumentException("error_010_02");
        } else if (details.getEvaluationSession() == null) {
            LOGGER.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_010_03");
        } else if (details.getPercentageScore() != null) {
            if (details.getPercentageScore().trim().length() > 20) {
                LOGGER.log(Level.INFO, "The percentage score is longer than 20 characters");
                throw new InvalidArgumentException("error_010_04");
            }
        } else if (details.getQuestionCategory() == null) {
            LOGGER.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("error_010_05");
        } else if (details.getQuestionDescription() != null) {
            if (details.getQuestionDescription().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The question description is longer than 300 characters");
                throw new InvalidArgumentException("error_010_06");
            }
        }

        //Creating a container to hold assessed evaluation record
        LOGGER.log(Level.INFO, "Creating a container to hold assessed evaluation record");
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
        if (details.getStandardDeviation() != null) {
            assessedEvaluation.setStandardDeviation(Double.parseDouble(formatter.format(details.getStandardDeviation())));
        } else {
            assessedEvaluation.setStandardDeviation(details.getStandardDeviation());
        }
        assessedEvaluation.setPercentageScore(details.getPercentageScore());
        assessedEvaluation.setQuestionDescription(details.getQuestionDescription());
        assessedEvaluation.setCourseOfSession(em.find(CourseOfSession.class, details.getCourseOfSession().getId()));
        assessedEvaluation.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        assessedEvaluation.setEvaluatedQuestion(em.find(EvaluatedQuestion.class, details.getEvaluatedQuestion().getId()));
        assessedEvaluation.setEvaluationSession(em.find(EvaluationSession.class, details.getEvaluationSession().getId()));

        //Editing an assessed evaluation record in the database
        LOGGER.log(Level.INFO, "Editing an assessed evaluation record in the database");
        try {
            em.merge(assessedEvaluation);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeAssessedEvaluation(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an assessed evaluation record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing an assessed evaluation record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_010_07");
        }

        //Removing an assessed evaluation record from the database
        LOGGER.log(Level.INFO, "Removing an assessed evaluation record from the database");
        assessedEvaluation = em.find(AssessedEvaluation.class, id);
        try {
            em.remove(assessedEvaluation);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<AssessedEvaluationDetails> convertAssessedEvaluationsToAssessedEvaluationDetailsList(List<AssessedEvaluation> assessedEvaluations) {
        //Entered method for converting assessedEvaluations list to assessed evaluation details list
        LOGGER.log(Level.FINE, "Entered method for converting assessedEvaluations list to assessed evaluation details list");

        //Convert list of assessedEvaluations to assessed evaluation details list
        LOGGER.log(Level.FINE, "Convert list of assessedEvaluations to assessed evaluation details list");
        List<AssessedEvaluationDetails> details = new ArrayList<>();
        for (AssessedEvaluation a : assessedEvaluations) {
            details.add(convertAssessedEvaluationToAssessedEvaluationDetails(a));
        }

        //Returning converted assessed evaluation details list
        LOGGER.log(Level.FINE, "Returning converted assessed evaluation details list");
        return details;
    }

    private AssessedEvaluationDetails convertAssessedEvaluationToAssessedEvaluationDetails(AssessedEvaluation assessedEvaluation) {
        //Entered method for converting assessed evaluation to assessed evaluation details
        LOGGER.log(Level.FINE, "Entered method for converting assessedEvaluations to assessed evaluation details");

        //Convert list of assessed evaluation to assessed evaluation details
        LOGGER.log(Level.FINE, "Convert list of assessed evaluation to assessed evaluation details");

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
        details.setStandardDeviation(assessedEvaluation.getStandardDeviation());
        details.setQuestionDescription(assessedEvaluation.getQuestionDescription());

        //Returning converted assessed evaluation details
        LOGGER.log(Level.FINE, "Returning converted assessed evaluation details");
        return details;
    }
//</editor-fold>

    private final DecimalFormat formatter = new DecimalFormat("00.##");
    private static final Logger LOGGER = Logger.getLogger(AssessedEvaluationRequests.class.getSimpleName());

}
