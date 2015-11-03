/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.admission;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.Admission;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AdmissionDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface AdmissionRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addAdmission(AdmissionDetails details) throws InvalidArgumentException;

    /**
     * @return @throws InvalidStateException
     */
    public List<AdmissionDetails> retrieveAdmissions() throws InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editAdmission(AdmissionDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeAdmission(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param admission
     * @return
     */
    public AdmissionDetails convertAdmissionToAdmissionDetails(Admission admission);
}
