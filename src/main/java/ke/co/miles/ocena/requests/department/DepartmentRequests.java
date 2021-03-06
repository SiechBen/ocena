/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.department;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class DepartmentRequests extends EntityRequests implements DepartmentRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addDepartment(DepartmentDetails departmentDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException {
        //Method for adding a department record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding a department record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (departmentDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_022_01");
        } else if (departmentDetails.getName() == null || departmentDetails.getName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The department name is null");
            throw new InvalidArgumentException("error_022_02");
        } else if (departmentDetails.getName().trim().length() > 300) {
            LOGGER.log(Level.INFO, "The department name is longer than 300 characters");
            throw new InvalidArgumentException("error_022_03");
        } else if (departmentDetails.getAbbreviation() == null || departmentDetails.getAbbreviation().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The abbreviation is null");
            throw new InvalidArgumentException("error_022_04");
        } else if (departmentDetails.getAbbreviation().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_022_05");
        } else if (departmentDetails.getFaculty() == null) {
            LOGGER.log(Level.INFO, "The faculty that the department lies under is null");
            throw new InvalidArgumentException("error_022_06");
        }

        //Checking if the department name is a duplicate
        LOGGER.log(Level.INFO, "Checking if the department name is a duplicate");
        q = em.createNamedQuery("Department.findByName");
        q.setParameter("name", departmentDetails.getName());
        try {
            department = (Department) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Department name is available for use");
            department = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (department != null) {
            LOGGER.log(Level.SEVERE, "Department name is already in use");
            throw new InvalidArgumentException("error_022_07");
        }

        //Checking if the abbreviation is a duplicate
        LOGGER.log(Level.INFO, "Checking if the abbreviation is a duplicate");
        q = em.createNamedQuery("Department.findByAbbreviation");
        q.setParameter("abbreviation", departmentDetails.getAbbreviation());
        try {
            department = (Department) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The abbreviation is available for use");
            department = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (department != null) {
            LOGGER.log(Level.SEVERE, "Abbreviation is already in use");
            throw new InvalidArgumentException("error_022_08");
        }

        //Creating a container to hold department record
        LOGGER.log(Level.INFO, "Creating a container to hold department record");
        department = new Department();
        department.setName(departmentDetails.getName());
        department.setActive(departmentDetails.getActive());
        department.setAbbreviation(departmentDetails.getAbbreviation());
        department.setFaculty(em.find(Faculty.class, departmentDetails.getFaculty().getId()));
        department.setContact(contactService.addContact(emailContactDetails, phoneContactDetails, postalContactDetails));

        //Adding a department record to the database
        LOGGER.log(Level.INFO, "Adding a department record to the database");
        try {
            em.persist(department);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return department.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<DepartmentDetails> retrieveDepartments(Integer facultyId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving department records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving department records from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the faculty unique identifier passed in");
        if (facultyId == null) {
            LOGGER.log(Level.INFO, "The faculty that the department lies under is null");
            throw new InvalidArgumentException("error_022_06");
        }

        //Retrieving department records from the database
        LOGGER.log(Level.INFO, "Retrieving department records from the database");
        q = em.createNamedQuery("Department.findByFacultyId");
        q.setParameter("facultyId", facultyId);
        List<Department> departments = new ArrayList<>();
        try {
            departments = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of department records
        LOGGER.log(Level.INFO, "Returning the details list of department records");
        return convertDepartmentsToDepartmentDetailsList(departments);
    }

    @Override
    public DepartmentDetails retrieveDepartment(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a department record
        LOGGER.log(Level.INFO, "Entered the method for retrieving a department record");

        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the department is null");
            throw new InvalidArgumentException("error_022_09");
        }
        
        //Retrieving the department record from the database
        LOGGER.log(Level.INFO, "Retrieving the department record from the database");
        q = em.createNamedQuery("Department.findById");
        q.setParameter("id", id);
        department = new Department();
        try {
            department = (Department) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No department record retrieved");
            throw new InvalidStateException("error_022_10");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details of the department record
        LOGGER.log(Level.INFO, "Returning the details of the department record");
        return convertDepartmentToDepartmentDetails(department);

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editDepartment(DepartmentDetails departmentDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a department record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a department record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (departmentDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_022_01");
        } else if (departmentDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The department's unique identifier is null");
            throw new InvalidArgumentException("error_022_09");
        } else if (departmentDetails.getName() == null || departmentDetails.getName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The department name is null");
            throw new InvalidArgumentException("error_022_02");
        } else if (departmentDetails.getName().trim().length() > 300) {
            LOGGER.log(Level.INFO, "The department name is longer than 300 characters");
            throw new InvalidArgumentException("error_022_03");
        } else if (departmentDetails.getAbbreviation() == null || departmentDetails.getAbbreviation().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The abbreviation is null");
            throw new InvalidArgumentException("error_022_04");
        } else if (departmentDetails.getAbbreviation().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_022_05");
        } else if (departmentDetails.getFaculty() == null) {
            LOGGER.log(Level.INFO, "The faculty that the department lies under is null");
            throw new InvalidArgumentException("error_022_06");
        }

        //Checking if the department name is a duplicate
        LOGGER.log(Level.INFO, "Checking if the department name is a duplicate");
        q = em.createNamedQuery("Department.findByName");
        q.setParameter("name", departmentDetails.getName());
        try {
            department = (Department) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Department name is available for use");
            department = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_022_07");
        }
        if (department != null) {
            if (!(department.getId().equals(departmentDetails.getId()))) {
                LOGGER.log(Level.SEVERE, "Department name is already in use");
                throw new InvalidArgumentException("error_000_01");
            }
        }

        //Checking if the abbreviation is a duplicate
        LOGGER.log(Level.INFO, "Checking if the abbreviation is a duplicate");
        q = em.createNamedQuery("Department.findByAbbreviation");
        q.setParameter("abbreviation", departmentDetails.getAbbreviation());
        try {
            department = (Department) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The abbreviation is available for use");
            department = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_022_08");
        }
        if (department != null) {
            if (!(department.getId().equals(departmentDetails.getId()))) {
                LOGGER.log(Level.SEVERE, "Abbreviation is already in use");
                throw new InvalidArgumentException("error_000_01");
            }
        }

        //Creating a container to hold department record
        LOGGER.log(Level.INFO, "Creating a container to hold department record");
        department = em.find(Department.class, departmentDetails.getId());
        department.setId(departmentDetails.getId());
        department.setName(departmentDetails.getName());
        department.setActive(departmentDetails.getActive());
        department.setAbbreviation(departmentDetails.getAbbreviation());
        department.setFaculty(em.find(Faculty.class, departmentDetails.getFaculty().getId()));
        if (department.getContact() != null) {
            contactDetails = new ContactDetails();
            contactDetails.setId(departmentDetails.getContact().getId());
            contactDetails.setActive(departmentDetails.getContact().getActive());
            department.setContact(contactService.editContact(contactDetails, emailContactDetails, phoneContactDetails, postalContactDetails));
        }

        //Editing a department record in the database
        LOGGER.log(Level.INFO, "Editing a department record in the database");
        try {
            em.merge(department);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeDepartment(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a department record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a department record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the department is null");
            throw new InvalidArgumentException("error_022_09");
        }

        //Get the department record to be removed
        LOGGER.log(Level.INFO, "Getting the department record to be removed");
        department = em.find(Department.class, id);
        int contactId = department.getContact().getId();

        //Removing a department record from the database
        LOGGER.log(Level.INFO, "Removing a department record from the database");
        try {
            em.remove(department);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("0-004");
        }

        //Remove the department's contact record
        LOGGER.log(Level.INFO, "Removing the department's contact record");
        contactService.removeContact(contactId);

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<DepartmentDetails> convertDepartmentsToDepartmentDetailsList(List<Department> departments) {
        //Entered method for converting departments list to department details list
        LOGGER.log(Level.FINE, "Entered method for converting departments list to department details list");

        //Convert list of departments to department details list
        LOGGER.log(Level.FINE, "Convert list of departments to department details list");
        List<DepartmentDetails> details = new ArrayList<>();
        for (Department a : departments) {
            details.add(convertDepartmentToDepartmentDetails(a));
        }

        //Returning converted department details list
        LOGGER.log(Level.FINE, "Returning converted department details list");
        return details;
    }

    @Override
    public DepartmentDetails convertDepartmentToDepartmentDetails(Department department) {
        //Entered method for converting department to department details
        LOGGER.log(Level.FINE, "Entered method for converting departments to department details");

        //Convert list of department to department details
        LOGGER.log(Level.FINE, "Convert list of department to department details");
        contactDetails = new ContactDetails();
        facultyDetails = new FacultyDetails();
        departmentDetails = new DepartmentDetails();

        try {
            contactDetails.setId(department.getContact().getId());
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred during conversion of department contact to details", e);
        }
        try {
            facultyDetails.setId(department.getFaculty().getId());
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred during conversion of department faculty to details", e);
        }

        departmentDetails.setId(department.getId());
        departmentDetails.setName(department.getName());
        departmentDetails.setFaculty(facultyDetails);
        departmentDetails.setContact(contactDetails);
        departmentDetails.setActive(department.getActive());
        departmentDetails.setVersion(department.getVersion());
        departmentDetails.setAbbreviation(department.getAbbreviation());

        //Returning converted department details
        LOGGER.log(Level.FINE, "Returning converted department details");
        return departmentDetails;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(DepartmentRequests.class.getSimpleName());

}
