/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.person;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.Person;
import ke.co.miles.ocena.exceptions.AlgorithmException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.exceptions.MilesException;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;
import ke.co.miles.ocena.utilities.UserAccountDetails;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
@Local
public interface PersonRequestsLocal {

    /**
     *
     * @param personDetails
     * @param userAccountDetails
     * @param facultyMemberDetails
     * @param emailContactDetails
     * @param phoneContactDetails
     * @param postalContactDetails
     * @return
     * @throws InvalidArgumentException
     * @throws AlgorithmException
     */
    public Integer addPerson(PersonDetails personDetails, UserAccountDetails userAccountDetails, FacultyMemberDetails facultyMemberDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, AlgorithmException;

    /**
     *
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<PersonDetails> retrievePersons() throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removePerson(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws ke.co.miles.ocena.exceptions.MilesException
     * @throws ke.co.miles.ocena.exceptions.AlgorithmException
     */
    public Map<PersonDetails, UserGroupDetail> retrievePerson(String username, String password) throws MilesException, AlgorithmException;

    /**
     *
     * @param referenceNumber
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public PersonDetails retrievePerson(String referenceNumber) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param personId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public PersonDetails retrievePerson(Integer personId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param person
     * @return
     */
    public PersonDetails convertPersonToPersonDetails(Person person);

    /**
     *
     * @param personDetails
     * @param userAccountDetails
     * @param facultyMemberDetails
     * @param emailContactDetails
     * @param phoneContactDetails
     * @param postalContactDetails
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     * @throws AlgorithmException
     */
    public void editPerson(PersonDetails personDetails, UserAccountDetails userAccountDetails, FacultyMemberDetails facultyMemberDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, InvalidStateException, AlgorithmException;

}
