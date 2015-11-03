/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.faculty;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface FacultyRequestsLocal {

    /**
     *
     * @param facultyDetails
     * @param emailContact
     * @param phoneContact
     * @param postalContact
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addFaculty(FacultyDetails facultyDetails, EmailContactDetails emailContact, PhoneContactDetails phoneContact, PostalContactDetails postalContact) throws InvalidArgumentException;

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<FacultyDetails> retrieveFaculties() throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param collegeId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<FacultyDetails> retrieveFaculties(Integer collegeId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public FacultyDetails retrieveFaculty(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param facultyDetails
     * @param emailContact
     * @param phoneContact
     * @param postalContact
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editFaculty(FacultyDetails facultyDetails, EmailContactDetails emailContact, PhoneContactDetails phoneContact, PostalContactDetails postalContact) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeFaculty(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param faculty
     * @return
     */
    public FacultyDetails convertFacultyToFacultyDetails(Faculty faculty);

}
