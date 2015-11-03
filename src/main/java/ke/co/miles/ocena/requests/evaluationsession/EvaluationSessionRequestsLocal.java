/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationsession;

import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.DuplicateStateException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface EvaluationSessionRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     * @throws ke.co.miles.ocena.exceptions.DuplicateStateException
     */
    public Integer addEvaluationSession(EvaluationSessionDetails details) throws InvalidArgumentException, DuplicateStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     * @throws ke.co.miles.ocena.exceptions.DuplicateStateException
     */
    public void editEvaluationSession(EvaluationSessionDetails details) throws InvalidArgumentException, InvalidStateException, DuplicateStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeEvaluationSession(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationSessionId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public EvaluationSessionDetails retrieveEvaluationSession(Integer evaluationSessionId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param degrees
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<EvaluationSessionDetails> retrieveEvaluationSessions(List<DegreeDetails> degrees) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param degreeId
     * @param admissionYear
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public EvaluationSessionDetails retrieveEvaluationSessionByDegree(Integer degreeId, Date admissionYear) throws InvalidArgumentException, InvalidStateException;

}
