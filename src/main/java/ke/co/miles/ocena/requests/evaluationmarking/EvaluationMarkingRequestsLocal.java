/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationmarking;

import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface EvaluationMarkingRequestsLocal {

    /**
     *
     * @param evaluationSession
     * @throws InvalidArgumentException
     */
    public void markEvaluation(EvaluationSessionDetails evaluationSession) throws InvalidArgumentException;

}
