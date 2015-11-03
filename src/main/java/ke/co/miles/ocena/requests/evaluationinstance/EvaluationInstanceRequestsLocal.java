/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationinstance;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.EvaluationInstance;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface EvaluationInstanceRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public EvaluationInstanceDetails addEvaluationInstance(EvaluationInstanceDetails details) throws InvalidArgumentException;

    /**
     *
     * @param evaluationSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<EvaluationInstanceDetails> retrieveEvaluationInstances(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editEvaluationInstance(EvaluationInstanceDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeEvaluationInstance(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationInstanceId
     * @return
     * @throws InvalidArgumentException
     */
    public EvaluationInstanceDetails retrieveEvaluationInstance(Integer evaluationInstanceId) throws InvalidArgumentException;

    /**
     *
     * @param evaluationInstanceDetails
     * @return
     * @throws InvalidArgumentException
     */
    public EvaluationInstance retrieveEvaluationInstance(EvaluationInstanceDetails evaluationInstanceDetails) throws InvalidArgumentException;
    
}
