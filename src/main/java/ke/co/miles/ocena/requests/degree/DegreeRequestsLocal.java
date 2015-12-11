/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.degree;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.Degree;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface DegreeRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addDegree(DegreeDetails details) throws InvalidArgumentException;

    /**
     *
     * @param admissionId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<DegreeDetails> retrieveDegrees(Integer admissionId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editDegree(DegreeDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeDegree(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param facultyId
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public List<DegreeDetails> retrieveFacultyDegrees(Integer facultyId) throws InvalidArgumentException;

    /**
     *
     * @param faculty
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<AdmissionDetails, List<DegreeDetails>> retrieveDegreesOfFacultyByAdmission(FacultyDetails faculty) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param departmentId
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public List<DegreeDetails> retrieveDepartmentDegrees(Integer departmentId) throws InvalidArgumentException;

    /**
     *
     * @param department
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public Map<AdmissionDetails, List<DegreeDetails>> retrieveDegreesOfDepartmentByAdmission(DepartmentDetails department) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param degree
     * @return
     */
    public DegreeDetails convertDegreeToDegreeDetails(Degree degree);

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public DegreeDetails retrieveDegree(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param object
     * @param admissionId
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     * @throws ke.co.miles.ocena.exceptions.InvalidStateException
     */
    public List<DegreeDetails> retrieveDegreesOfFacultyOrDepartmentAndAdmission(Object object, Integer admissionId) throws InvalidArgumentException, InvalidStateException;

}
