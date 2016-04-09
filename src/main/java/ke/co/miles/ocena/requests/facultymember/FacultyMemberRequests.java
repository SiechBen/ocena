/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.facultymember;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.entities.FacultyMemberRole;
import ke.co.miles.ocena.entities.Person;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.FacultyMemberRoleDetail;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class FacultyMemberRequests extends EntityRequests implements FacultyMemberRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addFacultyMember(FacultyMemberDetails details) throws InvalidArgumentException {
        //Method for adding a faculty member record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding a faculty member record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_028_01");
        } else if (details.getFacultyMemberRole() == null) {
            LOGGER.log(Level.INFO, "The faculty member role is null");
            throw new InvalidArgumentException("error_028_02");
        } else if (details.getPerson() == null) {
            LOGGER.log(Level.INFO, "The person making up the faculty member is null");
            throw new InvalidArgumentException("error_028_03");
        } else if (details.getFaculty() == null && details.getDepartment() == null) {
            LOGGER.log(Level.INFO, "The faculty and department are null");
            throw new InvalidArgumentException("error_028_04");
        } else if (details.getFacultyMemberRole().equals(FacultyMemberRoleDetail.STUDENT)) {
            if (details.getAdmissionYear() == null) {
                LOGGER.log(Level.INFO, "The admission year is null");
                throw new InvalidArgumentException("error_028_05");
            }
        }

        //Creating a container to hold faculty member record
        LOGGER.log(Level.INFO, "Creating a container to hold faculty member record");
        facultyMember = new FacultyMember();
        facultyMember.setActive(details.getActive());
        facultyMember.setAdmissionYear(details.getAdmissionYear());
        facultyMember.setPerson(em.find(Person.class, details.getPerson().getId()));
        try {
            facultyMember.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
        }
        try {
            facultyMember.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
        }
        facultyMember.setFacultyMemberRole(em.find(FacultyMemberRole.class, details.getFacultyMemberRole().getId()));

        //Adding a faculty member record to the database
        LOGGER.log(Level.INFO, "Adding a faculty member record to the database");
        try {
            em.persist(facultyMember);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return facultyMember.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<FacultyMemberDetails> retrieveNonStudentFacultyMembers(Object object) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving faculty member records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving faculty member records from the database");

        //Checking validity of object details
        LOGGER.log(Level.INFO, "Checking validity of the object passed in");
        if (object == null) {
            LOGGER.log(Level.INFO, "The faculty or department is null");
            throw new InvalidArgumentException("error_028_04");
        }

        //Identify the object
        LOGGER.log(Level.INFO, "Identifying the object");
        if (object instanceof FacultyDetails) {
            facultyDetails = (FacultyDetails) object;
            departmentDetails = null;
            LOGGER.log(Level.INFO, "The object is an instance of Faculty");
        } else if (object instanceof DepartmentDetails) {
            departmentDetails = (DepartmentDetails) object;
            facultyDetails = null;
            LOGGER.log(Level.INFO, "The object is an instance of Department");
        }

        //Retrieving faculty member records from the database
        LOGGER.log(Level.INFO, "Retrieving faculty member records from the database");
        List<FacultyMember> facultyMembers = new ArrayList<>();
        if (facultyDetails != null) {
            q = em.createNamedQuery("FacultyMember.findNonStudentsByFacultyId");
            q.setParameter("facultyMemberRoleId", FacultyMemberRoleDetail.STUDENT.getId());
            q.setParameter("facultyId", facultyDetails.getId());
            try {
                facultyMembers = q.getResultList();
            } catch (EJBException e) {
                LOGGER.log(Level.INFO, "An error occurred while retrieving the faculty member records");
            }
        } else if (departmentDetails != null) {
            q = em.createNamedQuery("FacultyMember.findNonStudentsByDepartmentId");
            q.setParameter("facultyMemberRoleId", FacultyMemberRoleDetail.STUDENT.getId());
            q.setParameter("departmentId", departmentDetails.getId());
            try {
                facultyMembers = q.getResultList();
            } catch (EJBException e) {
                LOGGER.log(Level.INFO, "An error occurred while retrieving the faculty member records");
            }
        }

        //Returning the faculty members
        LOGGER.log(Level.INFO, "Returning the faculty members");
        return convertFacultyMembersToFacultyMemberDetailsList(facultyMembers);

    }

    @Override
    public FacultyMemberDetails retrieveFacultyMemberByPerson(Integer personId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a faculty member record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving a faculty member record from the database");

        //Check that the person's unique identifier passed in are not null
        LOGGER.log(Level.INFO, "Checking that the person's unique identifier passed in are not null");
        if (personId == null) {
            LOGGER.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_028_06");
        }

        //Retrieve faculty member record from the database
        LOGGER.log(Level.INFO, "Retrieving faculty member record from the database");
        q = em.createNamedQuery("FacultyMember.findByPersonId");
        q.setParameter("personId", personId);
        try {
            facultyMember = (FacultyMember) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }

        //Return the faculty member record
        LOGGER.log(Level.INFO, "Returning the faculty member record");
        return convertFacultyMemberToFacultyMemberDetails(facultyMember);
    }

    @Override
    public FacultyMemberDetails retrieveSpecificFacultyMember(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a faculty member record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving a faculty member record from the database");

        //Check that the unique identifier passed in are not null
        LOGGER.log(Level.INFO, "Checking that the unique identifier passed in are not null");
        if (id == null) {
            LOGGER.log(Level.INFO, "The faculty member's unique identifier is null");
            throw new InvalidArgumentException("error_028_07");
        }

        //Retrieve faculty member record from the database
        LOGGER.log(Level.INFO, "Retrieving faculty member record from the database");
        q = em.createNamedQuery("FacultyMember.findById");
        q.setParameter("id", id);
        try {
            facultyMember = (FacultyMember) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Return the faculty member record
        LOGGER.log(Level.INFO, "Returning the faculty member record");
        return convertFacultyMemberToFacultyMemberDetails(facultyMember);
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editFacultyMember(FacultyMemberDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a faculty member record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a faculty member record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_028_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The faculty member's unique identifier is null");
            throw new InvalidArgumentException("error_028_07");
        } else if (details.getFacultyMemberRole() == null) {
            LOGGER.log(Level.INFO, "The faculty member role is null");
            throw new InvalidArgumentException("error_028_02");
        } else if (details.getPerson() == null) {
            LOGGER.log(Level.INFO, "The person making up the faculty member is null");
            throw new InvalidArgumentException("error_028_03");
        } else if (details.getFaculty() == null && details.getDepartment() == null) {
            LOGGER.log(Level.INFO, "The faculty and department are null");
            throw new InvalidArgumentException("error_028_04");
        } else if (details.getFacultyMemberRole().equals(FacultyMemberRoleDetail.STUDENT)) {
            if (details.getAdmissionYear() == null) {
                LOGGER.log(Level.INFO, "The admission year is null");
                throw new InvalidArgumentException("error_028_05");
            }
        }

        //Creating a container to hold faculty member record
        LOGGER.log(Level.INFO, "Creating a container to hold faculty member record");
        facultyMember = em.find(FacultyMember.class, details.getId());
        facultyMember.setId(details.getId());
        facultyMember.setActive(details.getActive());
        facultyMember.setAdmissionYear(details.getAdmissionYear());
        facultyMember.setPerson(em.find(Person.class, details.getPerson().getId()));
        try {
            facultyMember.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
            facultyMember.setFaculty(null);
        }
        try {
            facultyMember.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
            facultyMember.setDepartment(null);
        }
        facultyMember.setFacultyMemberRole(em.find(FacultyMemberRole.class, details.getFacultyMemberRole().getId()));

        //Editing a faculty member record in the database
        LOGGER.log(Level.INFO, "Editing a faculty member record in the database");
        try {
            em.merge(facultyMember);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeFacultyMember(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a faculty member record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a faculty member record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_028_07");
        }

        //Removing a faculty member record from the database
        LOGGER.log(Level.INFO, "Removing a faculty member record from the database");
        facultyMember = em.find(FacultyMember.class, id);
        try {
            em.remove(facultyMember);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<FacultyMemberDetails> convertFacultyMembersToFacultyMemberDetailsList(List<FacultyMember> facultyMembers) {
        //Entered method for converting faculty members list to faculty member details list
        LOGGER.log(Level.FINE, "Entered method for converting faculty members list to faculty member details list");

        //Convert list of faculty members to faculty member details list
        LOGGER.log(Level.FINE, "Convert list of faculty members to faculty member details list");
        List<FacultyMemberDetails> details = new ArrayList<>();
        for (FacultyMember a : facultyMembers) {
            details.add(convertFacultyMemberToFacultyMemberDetails(a));
        }

        //Returning converted faculty member details list
        LOGGER.log(Level.FINE, "Returning converted faculty member details list");
        return details;
    }

    @Override
    public FacultyMemberDetails convertFacultyMemberToFacultyMemberDetails(FacultyMember facultyMember) {
        //Entered method for converting facultyMember to facultyMember details
        LOGGER.log(Level.FINE, "Entered method for converting faculty members to faculty member details");

        //Convert list of facultyMember to facultyMember details
        LOGGER.log(Level.FINE, "Convert list of faculty member to faculty member details");
        facultyDetails = new FacultyDetails();
        try {
            facultyDetails.setId(facultyMember.getFaculty().getId());
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "The faculty member does not belong to a faculty");
            facultyDetails = null;
        }

        departmentDetails = new DepartmentDetails();
        try {
            departmentDetails.setId(facultyMember.getDepartment().getId());
        } catch (Exception e) {
            LOGGER.log(Level.FINE, "The faculty member does not belong to a department");
            departmentDetails = null;
        }

        personDetails = new PersonDetails();
        personDetails.setId(facultyMember.getPerson().getId());
        personDetails.setFirstName(facultyMember.getPerson().getFirstName());
        personDetails.setLastName(facultyMember.getPerson().getLastName());
        personDetails.setReferenceNumber(facultyMember.getPerson().getReferenceNumber());

        facultyMemberRoleDetail = FacultyMemberRoleDetail.getFacultyMemberRoleDetail(facultyMember.getFacultyMemberRole().getId());

        facultyMemberDetails = new FacultyMemberDetails();
        facultyMemberDetails.setPerson(personDetails);
        facultyMemberDetails.setFaculty(facultyDetails);
        facultyMemberDetails.setId(facultyMember.getId());
        facultyMemberDetails.setDepartment(departmentDetails);
        facultyMemberDetails.setActive(facultyMember.getActive());
        facultyMemberDetails.setVersion(facultyMember.getVersion());
        facultyMemberDetails.setAdmissionYear(facultyMember.getAdmissionYear());
        facultyMemberDetails.setFacultyMemberRole(facultyMemberRoleDetail);

        //Returning converted facultyMember details
        LOGGER.log(Level.FINE, "Returning converted faculty member details");
        return facultyMemberDetails;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(FacultyMemberRequests.class.getSimpleName());

}
