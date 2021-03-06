/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluatedquestionanswer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.CourseOfInstance;
import ke.co.miles.ocena.entities.EvaluatedQuestionAnswer;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionAnswerDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EvaluatedQuestionAnswerRequests extends EntityRequests implements EvaluatedQuestionAnswerRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addEvaluatedQuestionAnswer(EvaluatedQuestionAnswerDetails evaluatedQuestionAnswerDetails) throws InvalidArgumentException {
        //Method for adding a evaluated question answer record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding a evaluated question answer record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (evaluatedQuestionAnswerDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_025_01");
        } else if (evaluatedQuestionAnswerDetails.getComment1() != null) {
            if (evaluatedQuestionAnswerDetails.getComment1().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The first comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment1(evaluatedQuestionAnswerDetails.getComment1().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getComment2() != null) {
            if (evaluatedQuestionAnswerDetails.getComment2().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The second comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment2(evaluatedQuestionAnswerDetails.getComment2().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getComment3() != null) {
            if (evaluatedQuestionAnswerDetails.getComment3().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The third comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment3(evaluatedQuestionAnswerDetails.getComment3().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getReasoning() != null) {
            if (evaluatedQuestionAnswerDetails.getReasoning().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The reasoning is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setReasoning(evaluatedQuestionAnswerDetails.getReasoning().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getEvaluatedQuestion() == null) {
            LOGGER.log(Level.INFO, "The evaluated question for which the answer belongs is null");
            throw new InvalidArgumentException("error_025_02");
        } else if (evaluatedQuestionAnswerDetails.getEvaluationInstance() == null) {
            LOGGER.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_025_03");
        } else if (evaluatedQuestionAnswerDetails.getCourseOfInstance() == null) {
            LOGGER.log(Level.INFO, "The course of instance is null");
            throw new InvalidArgumentException("error_025_04");
        }

        ///Prevent duplicate evaluated question answer creation
        LOGGER.log(Level.INFO, "Checking against duplicate evaluated question answer creation");
        q = em.createNamedQuery("EvaluatedQuestionAnswer.findByEvaluationInstanceIdAndEvaluatedQuestionIdAndCourseOfInstanceId");
        q.setParameter("evaluationInstanceId", evaluatedQuestionAnswerDetails.getEvaluationInstance().getId());
        q.setParameter("evaluatedQuestionId", evaluatedQuestionAnswerDetails.getEvaluatedQuestion().getId());
        q.setParameter("courseOfInstanceId", evaluatedQuestionAnswerDetails.getCourseOfInstance().getId());

        evaluatedQuestionAnswer = new EvaluatedQuestionAnswer();
        try {
            evaluatedQuestionAnswer = (EvaluatedQuestionAnswer) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "The evaluated question answer has no duplicate");
            evaluatedQuestionAnswer = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //If adding would be creating a duplicate
        if (evaluatedQuestionAnswer != null) {
            LOGGER.log(Level.SEVERE, "The evaluated question answer if added would be a duplicate");
            throw new InvalidArgumentException("error_025_05");
        }

        //Creating a container to hold evaluated question answer record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluated question answer record");
        evaluatedQuestionAnswer = new EvaluatedQuestionAnswer();
        evaluatedQuestionAnswer.setActive(evaluatedQuestionAnswerDetails.getActive());
        evaluatedQuestionAnswer.setRating(evaluatedQuestionAnswerDetails.getRating());
        evaluatedQuestionAnswer.setComment1(evaluatedQuestionAnswerDetails.getComment1());
        evaluatedQuestionAnswer.setComment2(evaluatedQuestionAnswerDetails.getComment2());
        evaluatedQuestionAnswer.setComment3(evaluatedQuestionAnswerDetails.getComment3());
        evaluatedQuestionAnswer.setReasoning(evaluatedQuestionAnswerDetails.getReasoning());
        evaluatedQuestionAnswer.setCourseOfInstance(em.find(CourseOfInstance.class, evaluatedQuestionAnswerDetails.getCourseOfInstance().getId()));
        evaluatedQuestionAnswer.setEvaluatedQuestion(evaluatedQuestionService.retrieveEvaluatedQuestion(evaluatedQuestionAnswerDetails.getEvaluatedQuestion(),
                evaluatedQuestionAnswerDetails.getEvaluationInstance().getEvaluationSession()));
        evaluatedQuestionAnswer.setEvaluationInstance(evaluationInstanceService.retrieveEvaluationInstance(evaluatedQuestionAnswerDetails.getEvaluationInstance()));

        //Adding a evaluated question answer record to the database
        LOGGER.log(Level.INFO, "Adding an evaluated question answer record to the database");
        try {
            em.persist(evaluatedQuestionAnswer);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return evaluatedQuestionAnswer.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public EvaluatedQuestionAnswerDetails retrieveEvaluatedQuestionAnswer(EvaluationInstanceDetails currentEvaluationInstance, EvaluatedQuestionDetails currentEvaluatedQuestion, CourseOfInstanceDetails currentCourseOfInstance) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a specified evaluated question answer
        LOGGER.log(Level.INFO, "Entered the method for retrieving a specified evaluated question answer");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (currentEvaluatedQuestion == null) {
            LOGGER.log(Level.INFO, "The evaluated question is null");
            throw new InvalidArgumentException("error_025_02");
        } else if (currentEvaluatedQuestion.getId() == null) {
            LOGGER.log(Level.INFO, "The evaluated question's unique identifier is null");
            throw new InvalidArgumentException("error_025_07");
        } else if (currentEvaluationInstance == null) {
            LOGGER.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_025_03");
        } else if (currentEvaluationInstance.getId() == null) {
            LOGGER.log(Level.INFO, "The evaluation instance's unique identifier is null");
            throw new InvalidArgumentException("error_025_08");
        } else if (currentCourseOfInstance == null) {
            LOGGER.log(Level.INFO, "The current course of instance is null");
            throw new InvalidArgumentException("error_025_04");
        } else if (currentCourseOfInstance.getId() == null) {
            LOGGER.log(Level.INFO, "The current course of instance's unique identifier is null");
            throw new InvalidArgumentException("error_025_09");
        }

        //Retrieving the evaluated question answer record from the database
        LOGGER.log(Level.INFO, "Retrieving the evaluated question answer record from the database");
        q = em.createNamedQuery("EvaluatedQuestionAnswer.findByEvaluationInstanceIdAndEvaluatedQuestionIdAndCourseOfInstanceId");
        q.setParameter("evaluationInstanceId", currentEvaluationInstance.getId());
        q.setParameter("evaluatedQuestionId", currentEvaluatedQuestion.getId());
        q.setParameter("courseOfInstanceId", currentCourseOfInstance.getId());

        evaluatedQuestionAnswer = new EvaluatedQuestionAnswer();
        try {
            evaluatedQuestionAnswer = (EvaluatedQuestionAnswer) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "The evaluated question answer was not recorded");
            return null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the evaluated question answer record
        LOGGER.log(Level.INFO, "Returning the details of the evaluated question answer record");
        return convertEvaluatedQuestionAnswerToEvaluatedQuestionAnswerDetails(evaluatedQuestionAnswer);

    }

    @Override
    public List<EvaluatedQuestionAnswerDetails> retrieveEvaluatedQuestionAnswers(EvaluatedQuestionDetails evaluatedQuestion, EvaluationSessionDetails evaluationSession, CourseOfSessionDetails courseOfSession) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving all entered comments and reasons for a particular course of instance
        LOGGER.log(Level.INFO, "Entered the method for retrieving all entered comments and reasons for a particular course of instance");

        //Validate the arguments
        LOGGER.log(Level.INFO, "Validating the arguments");
        if (evaluatedQuestion == null) {
            LOGGER.log(Level.INFO, "Evaluated question is null");
            throw new InvalidArgumentException("error_025_02");
        } else if (evaluatedQuestion.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluated question is nul");
            throw new InvalidArgumentException("error_025_07");
        } else if (evaluationSession == null) {
            LOGGER.log(Level.INFO, "Evaluation session is null");
            throw new InvalidArgumentException("error_025_10");
        } else if (evaluationSession.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluation session is null");
            throw new InvalidArgumentException("error_025_11");
        } else if (courseOfSession == null) {
            LOGGER.log(Level.INFO, "Course of sesison is null");
            throw new InvalidArgumentException("error_025_12");
        } else if (courseOfSession.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the course of sesison is null");
            throw new InvalidArgumentException("error_025_13");
        }

        //Retrieve all the evaluation instances of the sesion
        LOGGER.log(Level.INFO, "Retrieving all the evaluation instances of the sesion");
        List<EvaluationInstanceDetails> evaluationInstances = new ArrayList<>();
        try {
            evaluationInstances = evaluationInstanceService.retrieveEvaluationInstances(evaluationSession);
        } catch (InvalidArgumentException | InvalidStateException e) {
            LOGGER.log(Level.INFO, "An error occurred during retrieval");
        }

        //Retrieve the course of instance for each evaluation instance
        LOGGER.log(Level.INFO, "Retrieve the course of instance for each evaluation instance");
        List<CourseOfInstance> coursesOfInstance = new ArrayList<>();
        q = em.createNamedQuery("CourseOfInstance.findByEvaluationInstanceIdAndCourseOfSessionId");
        q.setParameter("courseOfSessionId", courseOfSession.getId());
        for (EvaluationInstanceDetails ei : evaluationInstances) {
            q.setParameter("evaluationInstanceId", ei.getId());
            try {
                coursesOfInstance.add((CourseOfInstance) q.getSingleResult());
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "An error occurred during retrieval");
            }
        }

        //Retrieve evaluated question answers for all courses of instance
        LOGGER.log(Level.INFO, "Retrieving evaluated question answers for all courses of instance");
        List<EvaluatedQuestionAnswer> evaluatedQuestionAnswers = new ArrayList<>();
        q = em.createNamedQuery("EvaluatedQuestionAnswer.findByEvaluatedQuestionIdAndCourseOfInstanceId");
        q.setParameter("evaluatedQuestionId", evaluatedQuestion.getId());
        for (CourseOfInstance coi : coursesOfInstance) {
            q.setParameter("courseOfInstanceId", coi.getId());
            try {
                evaluatedQuestionAnswers.add((EvaluatedQuestionAnswer) q.getSingleResult());
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "An error occurred during retrieval");
            }
        }

        //Return the evaluated question answers
        LOGGER.log(Level.INFO, "Returning the evaluated question answers");
        return convertFacultiesToEvaluatedQuestionAnswerDetailsList(evaluatedQuestionAnswers);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">

    @Override
    public void editEvaluatedQuestionAnswer(EvaluatedQuestionAnswerDetails evaluatedQuestionAnswerDetails, CourseOfInstanceDetails courseOfInstanceDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a evaluated question answer record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a evaluated question answer record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (evaluatedQuestionAnswerDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_025_01");
        } else if (evaluatedQuestionAnswerDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluated question answer is null");
            throw new InvalidArgumentException("error_025_14");
        } else if (evaluatedQuestionAnswerDetails.getComment1() != null) {
            if (evaluatedQuestionAnswerDetails.getComment1().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The first comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment1(evaluatedQuestionAnswerDetails.getComment1().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getComment2() != null) {
            if (evaluatedQuestionAnswerDetails.getComment2().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The second comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment2(evaluatedQuestionAnswerDetails.getComment2().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getComment3() != null) {
            if (evaluatedQuestionAnswerDetails.getComment3().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The third comment is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setComment3(evaluatedQuestionAnswerDetails.getComment3().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getReasoning() != null) {
            if (evaluatedQuestionAnswerDetails.getReasoning().trim().length() > 300) {
                LOGGER.log(Level.INFO, "The reasoning is longer than 300 characters");
                evaluatedQuestionAnswerDetails.setReasoning(evaluatedQuestionAnswerDetails.getReasoning().substring(0, 300));
            }
        } else if (evaluatedQuestionAnswerDetails.getEvaluatedQuestion() == null) {
            LOGGER.log(Level.INFO, "The evaluated question for which the answer belongs is null");
            throw new InvalidArgumentException("error_025_02");
        } else if (evaluatedQuestionAnswerDetails.getEvaluationInstance() == null) {
            LOGGER.log(Level.INFO, "The evaluation instance is null");
            throw new InvalidArgumentException("error_025_03");
        } else if (courseOfInstanceDetails == null) {
            LOGGER.log(Level.INFO, "The course of instance is null");
            throw new InvalidArgumentException("error_025_04");
        }

        //Creating a container to hold evaluated question answer record
        LOGGER.log(Level.INFO, "Creating a container to hold evaluated question answer record");
        evaluatedQuestionAnswer = em.find(EvaluatedQuestionAnswer.class, evaluatedQuestionAnswerDetails.getId());
        evaluatedQuestionAnswer.setActive(evaluatedQuestionAnswerDetails.getActive());
        evaluatedQuestionAnswer.setRating(evaluatedQuestionAnswerDetails.getRating());
        evaluatedQuestionAnswer.setComment1(evaluatedQuestionAnswerDetails.getComment1());
        evaluatedQuestionAnswer.setComment2(evaluatedQuestionAnswerDetails.getComment2());
        evaluatedQuestionAnswer.setComment3(evaluatedQuestionAnswerDetails.getComment3());
        evaluatedQuestionAnswer.setReasoning(evaluatedQuestionAnswerDetails.getReasoning());
        evaluatedQuestionAnswer.setCourseOfInstance(em.find(CourseOfInstance.class, courseOfInstanceDetails.getId()));
        evaluatedQuestionAnswer.setEvaluatedQuestion(evaluatedQuestionService.retrieveEvaluatedQuestion(evaluatedQuestionAnswerDetails.getEvaluatedQuestion(),
                evaluatedQuestionAnswerDetails.getEvaluationInstance().getEvaluationSession()));
        evaluatedQuestionAnswer.setEvaluationInstance(evaluationInstanceService.retrieveEvaluationInstance(evaluatedQuestionAnswerDetails.getEvaluationInstance()));

        //Editing a evaluated question answer record in the database
        LOGGER.log(Level.INFO, "Editing a evaluated question answer record in the database");
        try {
            em.merge(evaluatedQuestionAnswer);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_025_03");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeEvaluatedQuestionAnswer(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a evaluated question answer record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a evaluated question answer record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the evaluated question answer is null");
            throw new InvalidArgumentException("error_025_14");
        }

        //Removing a evaluated question answer record from the database
        LOGGER.log(Level.INFO, "Removing a evaluated question answer record from the database");
        evaluatedQuestionAnswer = em.find(EvaluatedQuestionAnswer.class, id);
        try {
            em.remove(evaluatedQuestionAnswer);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<EvaluatedQuestionAnswerDetails> convertFacultiesToEvaluatedQuestionAnswerDetailsList(List<EvaluatedQuestionAnswer> evaluatedQuestionAnswers) {
        //Entered method for converting faculties list to faculty details list
        LOGGER.log(Level.FINE, "Entered method for converting faculties list to faculty details list");

        //Convert list of faculties to faculty details list
        LOGGER.log(Level.FINE, "Convert list of faculties to faculty details list");
        List<EvaluatedQuestionAnswerDetails> details = new ArrayList<>();
        for (EvaluatedQuestionAnswer eqa : evaluatedQuestionAnswers) {
            details.add(convertEvaluatedQuestionAnswerToEvaluatedQuestionAnswerDetails(eqa));
        }

        //Returning converted faculty details list
        LOGGER.log(Level.FINE, "Returning converted faculty details list");
        return details;
    }

    private EvaluatedQuestionAnswerDetails convertEvaluatedQuestionAnswerToEvaluatedQuestionAnswerDetails(EvaluatedQuestionAnswer evaluatedQuestionAnswer) {
        //Entered method for converting faculty to faculty details
        LOGGER.log(Level.FINE, "Entered method for converting faculties to faculty details");

        //Convert list of faculty to faculty details
        LOGGER.log(Level.FINE, "Convert list of faculty to faculty details");

        evaluatedQuestionDetails = new EvaluatedQuestionDetails();
        evaluatedQuestionDetails.setId(evaluatedQuestionAnswer.getEvaluatedQuestion().getId());

        evaluationInstanceDetails = new EvaluationInstanceDetails();
        evaluationInstanceDetails.setId(evaluatedQuestionAnswer.getEvaluationInstance().getId());

        courseOfInstanceDetails = new CourseOfInstanceDetails();
        courseOfInstanceDetails.setId(evaluatedQuestionAnswer.getCourseOfInstance().getId());

        EvaluatedQuestionAnswerDetails details = new EvaluatedQuestionAnswerDetails();
        details.setId(evaluatedQuestionAnswer.getId());
        details.setRating(evaluatedQuestionAnswer.getRating());
        details.setCourseOfInstance(courseOfInstanceDetails);
        details.setEvaluatedQuestion(evaluatedQuestionDetails);
        details.setActive(evaluatedQuestionAnswer.getActive());
        details.setEvaluationInstance(evaluationInstanceDetails);
        details.setVersion(evaluatedQuestionAnswer.getVersion());
        details.setComment1(evaluatedQuestionAnswer.getComment1());
        details.setComment2(evaluatedQuestionAnswer.getComment2());
        details.setComment3(evaluatedQuestionAnswer.getComment3());
        details.setReasoning(evaluatedQuestionAnswer.getReasoning());

        //Returning converted faculty details
        LOGGER.log(Level.FINE, "Returning converted faculty details");
        return details;
    }
    //</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(EvaluatedQuestionAnswerRequests.class.getSimpleName());

}
