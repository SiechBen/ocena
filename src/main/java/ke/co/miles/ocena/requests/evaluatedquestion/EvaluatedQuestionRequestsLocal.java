/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluatedquestion;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.EvaluatedQuestion;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface EvaluatedQuestionRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addEvaluatedQuestion(EvaluatedQuestionDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editEvaluatedQuestion(EvaluatedQuestionDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeEvaluatedQuestion(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<EvaluatedQuestionDetails> retrieveEvaluatedQuestions(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluatedQuestion
     * @param evaluationSession
     * @return
     */
    public EvaluatedQuestion retrieveEvaluatedQuestion(EvaluatedQuestionDetails evaluatedQuestion, EvaluationSessionDetails evaluationSession);

}
