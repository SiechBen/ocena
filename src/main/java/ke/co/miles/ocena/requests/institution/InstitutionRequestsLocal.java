/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.institution;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface InstitutionRequestsLocal {

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeInstitution(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editInstitution(InstitutionDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addInstitution(InstitutionDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<InstitutionDetails> retrieveInstitutions(PersonDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public InstitutionDetails retrieveInstitution(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public InstitutionDetails retrieveInstitution(PersonDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param institutions
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(List<InstitutionDetails> institutions) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param institution
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(InstitutionDetails institution) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public InstitutionDetails retrieveInstitution() throws InvalidArgumentException, InvalidStateException;
}
