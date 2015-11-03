/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.assessedevaluation;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationCommentDetails;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface AssessedEvaluationCommentRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addAssessedEvaluationComment(AssessedEvaluationCommentDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<AssessedEvaluationCommentDetails> retrieveAssessedEvaluationComment(AssessedEvaluationDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editAssessedEvaluationComment(AssessedEvaluationCommentDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeAssessedEvaluationComment(Integer id) throws InvalidArgumentException, InvalidStateException;

 
}
