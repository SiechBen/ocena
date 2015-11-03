/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.college;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface CollegeRequestsLocal {

    /**
     *
     * @param details the details of a college record
     * @return the unique identifier of the new college record made
     * @throws InvalidArgumentException when the college details have invalid or incorrectly
     * specified arguments
     */
    public Integer addCollege(CollegeDetails details) throws InvalidArgumentException;

    /**
     *
     * @param institutionId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<CollegeDetails> retrieveColleges(Integer institutionId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editCollege(CollegeDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeCollege(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param institutionId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<CollegeDetails, List<FacultyDetails>> retrieveCollegeFaculties(Integer institutionId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @return @throws InvalidStateException
     */
    public List<CollegeDetails> retrieveColleges() throws InvalidStateException;

    /**
     *
     * @param collegeId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public CollegeDetails retrieveCollege(Integer collegeId) throws InvalidArgumentException, InvalidStateException;

}
