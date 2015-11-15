/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.faculty;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.College;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class FacultyRequests extends EntityRequests implements FacultyRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addFaculty(FacultyDetails facultyDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException {
        //Method for adding a faculty record to the database
        logger.log(Level.INFO, "Entered the method for adding a faculty record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (facultyDetails == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("19-001");
        } else if (facultyDetails.getName() == null || facultyDetails.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getName().trim().length() > 300) {
            logger.log(Level.INFO, "The rating is longer than 300 characters");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getAbbreviation() == null || facultyDetails.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The rating is longer than 20 characters");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getCollege() == null) {
            logger.log(Level.INFO, "The college the faculty is under is null");
            throw new InvalidArgumentException("19-002");
        }

        //Checking if the faculty name is unique
        logger.log(Level.INFO, "Checking if the faculty name is unique");
        q = em.createNamedQuery("Faculty.findByName");
        q.setParameter("name", facultyDetails.getName());
        try {
            faculty = (Faculty) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Faculty name is available for use");
            faculty = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (faculty != null) {
            if (faculty.getId().equals(facultyDetails.getId())) {
                logger.log(Level.INFO, "Faculty name exists already in the database for this faculty");
                throw new EJBException("19-003");
            }
        }

        //Checking if the faculty abbreviation is unique
        logger.log(Level.INFO, "Checking if the faculty abbreviation is unique");
        q = em.createNamedQuery("Faculty.findByAbbreviation");
        q.setParameter("abbreviation", facultyDetails.getAbbreviation());
        try {
            faculty = (Faculty) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Faculty abbreviation is available for use");
            faculty = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (faculty != null) {
            if (faculty.getId().equals(facultyDetails.getId())) {
                logger.log(Level.INFO, "Faculty abbreviation exists already in the database for this faculty");
                throw new EJBException("19-003");
            }
        }

        //Creating a container to hold faculty record
        logger.log(Level.INFO, "Creating a container to hold faculty record");
        faculty = new Faculty();
        faculty.setActive(facultyDetails.getActive());
        faculty.setName(facultyDetails.getName());
        faculty.setAbbreviation(facultyDetails.getAbbreviation());
        faculty.setCollege(em.find(College.class, facultyDetails.getCollege().getId()));
        faculty.setContact(contactService.addContact(emailContactDetails, phoneContactDetails, postalContactDetails));

        //Adding a faculty record to the database
        logger.log(Level.INFO, "Adding a faculty record to the database");
        try {
            em.persist(faculty);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("0-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return faculty.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<FacultyDetails> retrieveFaculties() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving faculty records from the database
        logger.log(Level.INFO, "Entered the method for retrieving faculty records from the database");

        //Retrieving faculty records from the database
        logger.log(Level.INFO, "Retrieving faculty records from the database");
        q = em.createNamedQuery("Faculty.findAll");
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of faculty records
        logger.log(Level.INFO, "Returning the details list of faculty records");
        return convertFacultiesToFacultyDetailsList(faculties);
    }

    @Override
    public List<FacultyDetails> retrieveFaculties(Integer collegeId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving records of faculties in a college from the database
        logger.log(Level.INFO, "Entered the method for retrieving records of faculties in a college from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the college unique identifier passed in");
        if (collegeId == null) {
            logger.log(Level.INFO, "The college in which the faculty belongs is null");
            throw new InvalidArgumentException("7-004");
        }

        //Retrieving faculty records from the database
        logger.log(Level.INFO, "Retrieving faculty records from the database");
        q = em.createNamedQuery("Faculty.findByCollegeId");
        q.setParameter("collegeId", collegeId);
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of faculty records
        logger.log(Level.INFO, "Returning the details list of faculty records");
        return convertFacultiesToFacultyDetailsList(faculties);
    }

    @Override
    public FacultyDetails retrieveFaculty(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a faculty record
        logger.log(Level.INFO, "Entered the method for retrieving a faculty record");

        //Checking validity of the unique identifier
        logger.log(Level.INFO, "Checking validity of the faculty unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the faculty is null");
            throw new InvalidArgumentException("7-004");
        }

        //Retrieving the faculty record from the database
        logger.log(Level.INFO, "Retrieving the faculty record from the database");
        q = em.createNamedQuery("Faculty.findById");
        q.setParameter("id", id);
        faculty = new Faculty();
        try {
            faculty = (Faculty) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details of the faculty record
        logger.log(Level.INFO, "Returning the details of the faculty record");
        return convertFacultyToFacultyDetails(faculty);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editFaculty(FacultyDetails facultyDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a faculty record in the database
        logger.log(Level.INFO, "Entered the method for editing a faculty record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (facultyDetails == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("19-001");
        } else if (facultyDetails.getId() == null) {
            logger.log(Level.INFO, "The faculty's unique identifier is null");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getName() == null || facultyDetails.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getName().trim().length() > 300) {
            logger.log(Level.INFO, "The rating is longer than 300 characters");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getAbbreviation() == null || facultyDetails.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The rating is longer than 20 characters");
            throw new InvalidArgumentException("19-002");
        } else if (facultyDetails.getCollege() == null) {
            logger.log(Level.INFO, "The college the faculty is under is null");
            throw new InvalidArgumentException("19-002");
        }

        //Checking if the faculty name is unique
        logger.log(Level.INFO, "Checking if the faculty name is unique");
        q = em.createNamedQuery("Faculty.findByName");
        q.setParameter("name", facultyDetails.getName());
        try {
            faculty = (Faculty) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Faculty name is available for use");
            faculty = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (faculty != null) {
            if (!(faculty.getId().equals(facultyDetails.getId()))) {
                logger.log(Level.INFO, "Faculty name exists already in the database for this faculty");
                throw new EJBException("19-003");
            }
        }

        //Checking if the faculty abbreviation is unique
        logger.log(Level.INFO, "Checking if the faculty abbreviation is unique");
        q = em.createNamedQuery("Faculty.findByAbbreviation");
        q.setParameter("abbreviation", facultyDetails.getAbbreviation());
        try {
            faculty = (Faculty) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Faculty abbreviation is available for use");
            faculty = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (faculty != null) {
            if (!(faculty.getId().equals(facultyDetails.getId()))) {
                logger.log(Level.INFO, "Faculty abbreviation exists already in the database for this faculty");
                throw new EJBException("19-003");
            }
        }

        //Creating a container to hold faculty record
        logger.log(Level.INFO, "Creating a container to hold faculty record");
        faculty = em.find(Faculty.class, facultyDetails.getId());
        faculty.setActive(facultyDetails.getActive());
        faculty.setId(facultyDetails.getId());
        faculty.setName(facultyDetails.getName());
        faculty.setAbbreviation(facultyDetails.getAbbreviation());
        faculty.setCollege(em.find(College.class, facultyDetails.getCollege().getId()));
        if (facultyDetails.getContact() != null) {
            contactDetails = new ContactDetails();
            contactDetails.setId(facultyDetails.getContact().getId());
            contactDetails.setActive(facultyDetails.getContact().getActive());
            faculty.setContact(contactService.editContact(contactDetails, emailContactDetails, phoneContactDetails, postalContactDetails));
        }

        //Editing a faculty record in the database
        logger.log(Level.INFO, "Editing a faculty record in the database");
        try {
            em.merge(faculty);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("19-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeFaculty(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a faculty record from the database
        logger.log(Level.INFO, "Entered the method for removing a faculty record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("19-006");
        }

        //Get the faculty record to be removed
        logger.log(Level.INFO, "Getting the faculty record to be removed");
        faculty = em.find(Faculty.class, id);
        int contactId = faculty.getContact().getId();

        //Removing a faculty record from the database
        logger.log(Level.INFO, "Removing a faculty record from the database");
        try {
            em.remove(faculty);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("0-004");
        }

        //Remove the faculty's contact record
        logger.log(Level.INFO, "Removing the faculty's contact record");
        contactService.removeContact(contactId);

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<FacultyDetails> convertFacultiesToFacultyDetailsList(List<Faculty> faculties) {
        //Entered method for converting faculties list to faculty details list
        logger.log(Level.FINE, "Entered method for converting faculties list to faculty details list");

        //Convert list of faculties to faculty details list
        logger.log(Level.FINE, "Convert list of faculties to faculty details list");
        List<FacultyDetails> details = new ArrayList<>();
        for (Faculty a : faculties) {
            details.add(convertFacultyToFacultyDetails(a));
        }

        //Returning converted faculty details list
        logger.log(Level.FINE, "Returning converted faculty details list");
        return details;
    }

    @Override
    public FacultyDetails convertFacultyToFacultyDetails(Faculty faculty) {
        //Entered method for converting faculty to faculty details
        logger.log(Level.FINE, "Entered method for converting faculties to faculty details");

        //Convert list of faculty to faculty details
        logger.log(Level.FINE, "Convert list of faculty to faculty details");
        contactDetails = new ContactDetails();
        collegeDetails = new CollegeDetails();
        FacultyDetails details = new FacultyDetails();

        try {
            contactDetails.setId(faculty.getContact().getId());
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during conversion of faculty contact to details", e);
        }
        try {
            collegeDetails.setId(faculty.getCollege().getId());
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during conversion of faculty college to details", e);
        }

        details.setId(faculty.getId());
        details.setName(faculty.getName());
        details.setCollege(collegeDetails);
        details.setContact(contactDetails);
        details.setActive(faculty.getActive());
        details.setVersion(faculty.getVersion());
        details.setAbbreviation(faculty.getAbbreviation());

        //Returning converted faculty details
        logger.log(Level.FINE, "Returning converted faculty details");
        return details;
    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(FacultyRequests.class.getSimpleName());

}
