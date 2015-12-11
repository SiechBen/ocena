/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluatedquestionanswer;

import java.util.List;
import javax.ejb.Local;
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
@Local
public interface EvaluatedQuestionAnswerRequestsLocal {

    /**
     *
     * @param evaluatedQuestionAnswerDetails
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addEvaluatedQuestionAnswer(EvaluatedQuestionAnswerDetails evaluatedQuestionAnswerDetails) throws InvalidArgumentException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeEvaluatedQuestionAnswer(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluatedQuestionAnswerDetails
     * @param courseOfInstanceDetails
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editEvaluatedQuestionAnswer(EvaluatedQuestionAnswerDetails evaluatedQuestionAnswerDetails, CourseOfInstanceDetails courseOfInstanceDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param currentEvaluationInstance
     * @param currentEvaluatedQuestion
     * @param currentCourseOfInstance
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public EvaluatedQuestionAnswerDetails retrieveEvaluatedQuestionAnswer(EvaluationInstanceDetails currentEvaluationInstance, EvaluatedQuestionDetails currentEvaluatedQuestion, CourseOfInstanceDetails currentCourseOfInstance) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluatedQuestion
     * @param evaluationSession
     * @param c
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<EvaluatedQuestionAnswerDetails> retrieveEvaluatedQuestionAnswers(EvaluatedQuestionDetails evaluatedQuestion, EvaluationSessionDetails evaluationSession, CourseOfSessionDetails c) throws InvalidArgumentException, InvalidStateException;

}
