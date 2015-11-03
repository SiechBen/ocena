/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.department;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface DepartmentRequestsLocal {

    /**
     *
     * @param departmentDetails
     * @param emailContact
     * @param phoneContact
     * @param postalContact
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addDepartment(DepartmentDetails departmentDetails, EmailContactDetails emailContact, PhoneContactDetails phoneContact, PostalContactDetails postalContact) throws InvalidArgumentException;

    /**
     *
     * @param facultyId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<DepartmentDetails> retrieveDepartments(Integer facultyId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public DepartmentDetails retrieveDepartment(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param departmentDetails
     * @param emailContact
     * @param phoneContact
     * @param postalContact
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editDepartment(DepartmentDetails departmentDetails, EmailContactDetails emailContact, PhoneContactDetails phoneContact, PostalContactDetails postalContact) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeDepartment(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param department
     * @return
     */
    public DepartmentDetails convertDepartmentToDepartmentDetails(Department department);

}
