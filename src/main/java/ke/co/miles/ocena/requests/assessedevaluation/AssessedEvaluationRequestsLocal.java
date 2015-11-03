/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.assessedevaluation;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.AssessedEvaluation;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface AssessedEvaluationRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public AssessedEvaluation addAssessedEvaluation(AssessedEvaluationDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editAssessedEvaluation(AssessedEvaluationDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<QuestionCategoryDetails, List<AssessedEvaluationDetails>> retrieveAssessedEvaluationsByCategory(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluations(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeAssessedEvaluation(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param courseOfSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<AssessedEvaluationDetails> retrieveAssessedEvaluationsByCourse(CourseOfSessionDetails courseOfSessionDetails) throws InvalidArgumentException, InvalidStateException;

}
