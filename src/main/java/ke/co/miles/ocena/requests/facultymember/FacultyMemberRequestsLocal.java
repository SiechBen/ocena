/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.facultymember;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;

/**
 *
 * @author Ben Siech
 */
@Local

public interface FacultyMemberRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addFacultyMember(FacultyMemberDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editFacultyMember(FacultyMemberDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param personId
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public FacultyMemberDetails retrieveFacultyMemberByPerson(Integer personId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public FacultyMemberDetails retrieveSpecificFacultyMember(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param personId
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeFacultyMember(Integer personId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param object
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<FacultyMemberDetails> retrieveNonStudentFacultyMembers(Object object) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param facultyMember
     * @return
     */
    public FacultyMemberDetails convertFacultyMemberToFacultyMemberDetails(FacultyMember facultyMember);

}
