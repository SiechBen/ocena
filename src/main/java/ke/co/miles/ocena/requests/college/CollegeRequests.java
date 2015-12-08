/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.college;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.College;
import ke.co.miles.ocena.entities.Institution;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class CollegeRequests extends EntityRequests implements CollegeRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addCollege(CollegeDetails details) throws InvalidArgumentException {
        //Method for adding a college record to the database
        logger.log(Level.INFO, "Entered the method for adding a college record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_011_01");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The college name is null");
            throw new InvalidArgumentException("error_011_02");
        } else if (details.getName().trim().length() > 300) {
            logger.log(Level.INFO, "The college name is longer than 300 characters");
            throw new InvalidArgumentException("error_011_03");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The college abbreviation is null");
            throw new InvalidArgumentException("error_011_04");
        } else if (details.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The college abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_011_05");
        } else if (details.getInstitution() == null) {
            logger.log(Level.INFO, "The institution to which the college belongs is null");
            throw new InvalidArgumentException("error_011_06");
        }

        //Checking if the college name is a duplicate
        logger.log(Level.INFO, "Checking if the college name is a duplicate");
        q = em.createNamedQuery("College.findByName");
        q.setParameter("name", details.getName());
        try {
            college = (College) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "College name is available for use");
            college = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_011_02");
        }
        if (college != null) {
            logger.log(Level.SEVERE, "College name is already in use");
            throw new InvalidArgumentException("error_011_07");
        }

        //Checking if the college abbreviation is a duplicate
        logger.log(Level.INFO, "Checking if the college abbreviation is a duplicate");
        q = em.createNamedQuery("College.findByAbbreviation");
        q.setParameter("abbreviation", details.getAbbreviation());
        try {
            college = (College) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "College abbreviation is available for use");
            college = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (college != null) {
            logger.log(Level.SEVERE, "College abbreviation is already in use");
            throw new InvalidArgumentException("error_011_08");
        }

        //Creating a container to hold college record
        logger.log(Level.INFO, "Creating a container to hold college record");
        college = new College();
        college.setName(details.getName());
        college.setActive(details.getActive());
        college.setAbbreviation(details.getAbbreviation());
        college.setInstitution(em.find(Institution.class, details.getInstitution().getId()));

        //Adding a college record to the database
        logger.log(Level.INFO, "Adding a college record to the database");
        try {
            em.persist(college);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return college.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public Map<CollegeDetails, List<FacultyDetails>> retrieveCollegeFaculties(Integer institutionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of college and faculties in it
        logger.log(Level.INFO, "Entered method for retrieving a map of college and faculties in it");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution's unique identifier passed in");
        if (institutionId == null) {
            logger.log(Level.INFO, "The institution's unique identifier is null");
            throw new InvalidArgumentException("error_012_0");
        }

        //Retrieve the colleges in an assessedEvaluationComment
        logger.log(Level.INFO, "Retrieving the colleges in an institution");
        List<CollegeDetails> colleges = retrieveColleges(institutionId);

        //Retrieve the faculties in the colleges and put them in a map
        logger.log(Level.INFO, "Retrieving the faculties in the colleges and putting them in a map");
        Map<CollegeDetails, List<FacultyDetails>> collegeFacultiesMap = new HashMap<>();
        for (CollegeDetails c : colleges) {
            collegeFacultiesMap.put(c, facultyservice.retrieveFaculties(c.getId()));
        }

        //Return the map of faculties in the colleges
        logger.log(Level.INFO, "Returning the map of faculties in the colleges");
        return collegeFacultiesMap;
    }

    @Override
    public List<CollegeDetails> retrieveColleges(Integer institutionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving records of colleges in an assessedEvaluationComment from the database
        logger.log(Level.INFO, "Entered the method for retrieving records of colleges in an institution from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution unique identifier passed in");
        if (institutionId == null) {
            logger.log(Level.INFO, "The institution to which the college belongs is null");
            throw new InvalidArgumentException("error_011_06");
        }

        //Retrieving college records from the database
        logger.log(Level.INFO, "Retrieving college records from the database");
        q = em.createNamedQuery("College.findByInstitutionId");
        q.setParameter("institutionId", institutionId);
        List<College> colleges = new ArrayList<>();
        try {
            colleges = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of college records
        logger.log(Level.INFO, "Returning the details list of college records");
        return convertCollegesToCollegeDetailsList(colleges);
    }
    
    @Override
    public CollegeDetails retrieveCollege(Integer collegeId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a college record from the database
        logger.log(Level.INFO, "Entered the method for retrieving a college record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the college unique identifier passed in");
        if (collegeId == null) {
            logger.log(Level.INFO, "The college unique identifier is null");
            throw new InvalidArgumentException("error_011_09");
        }

        //Retrieving college records from the database
        logger.log(Level.INFO, "Retrieving the college record from the database");
        q = em.createNamedQuery("College.findById");
        q.setParameter("id", collegeId);
         college = new College();
        try {
            college = (College) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details of the college record
        logger.log(Level.INFO, "Returning the details of the college record");
        return convertCollegeToCollegeDetails(college);
    }

    @Override
    public List<CollegeDetails> retrieveColleges() throws InvalidStateException {
        //Method for retrieving records of all colleges from the database
        logger.log(Level.INFO, "Entered the method for retrieving records of all colleges from the database");

        //Retrieving college records from the database
        logger.log(Level.INFO, "Retrieving college records from the database");
        q = em.createNamedQuery("College.findAll");
        List<College> colleges = new ArrayList<>();
        try {
            colleges = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of college records
        logger.log(Level.INFO, "Returning the details list of college records");
        return convertCollegesToCollegeDetailsList(colleges);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editCollege(CollegeDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a college record in the database
        logger.log(Level.INFO, "Entered the method for editing a college record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_011_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The college's unique identifier is null");
            throw new InvalidArgumentException("error_011_09");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The college name is null");
            throw new InvalidArgumentException("error_011_02");
        } else if (details.getName().trim().length() > 300) {
            logger.log(Level.INFO, "The college name is longer than 300 characters");
            throw new InvalidArgumentException("error_011_03");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The college abbreviation is null");
            throw new InvalidArgumentException("error_011_04");
        } else if (details.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The college abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_011_05");
        } else if (details.getInstitution() == null) {
            logger.log(Level.INFO, "The institution to which the college belongs is null");
            throw new InvalidArgumentException("error_011_06");
        }

        //Checking if the college name is a duplicate
        logger.log(Level.INFO, "Checking if the college name is a duplicate");
        q = em.createNamedQuery("College.findByName");
        q.setParameter("name", details.getName());
        try {
            college = (College) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "College name is available for use");
            college = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (college != null) {
            if (!college.getId().equals(details.getId())) {
                logger.log(Level.SEVERE, "College name is already in use");
                throw new InvalidArgumentException("error_011_07");
            }
        }
        
        //Checking if the college abbreviation is a duplicate
        logger.log(Level.INFO, "Checking if the college abbreviation is a duplicate");
        q = em.createNamedQuery("College.findByAbbreviation");
        q.setParameter("abbreviation", details.getAbbreviation());
        try {
            college = (College) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "College name is available for use");
            college = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_011_02");
        }
        if (college != null) {
            if (!college.getId().equals(details.getId())) {
                logger.log(Level.SEVERE, "College abbreviation is already in use");
                throw new InvalidArgumentException("error_011_08");
            }
        }

        //Creating a container to hold college record
        logger.log(Level.INFO, "Creating a container to hold college record");
        college = em.find(College.class, details.getId());
        college.setName(details.getName());
        college.setId(details.getId());
        college.setActive(details.getActive());
        college.setAbbreviation(details.getAbbreviation());
        college.setInstitution(em.find(Institution.class, details.getInstitution().getId()));

        //Editing a college record in the database
        logger.log(Level.INFO, "Editing a college record in the database");
        try {
            em.merge(college);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeCollege(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a college record from the database
        logger.log(Level.INFO, "Entered the method for removing a college record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The college's unique identifier is null");
            throw new InvalidArgumentException("error_011_09");
        }

        //Removing a college record from the database
        logger.log(Level.INFO, "Removing a college record from the database");
        college = em.find(College.class, id);
        try {
            em.remove(college);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<CollegeDetails> convertCollegesToCollegeDetailsList(List<College> colleges) {
        //Entered method for converting colleges list to college details list
        logger.log(Level.FINE, "Entered method for converting colleges list to college details list");

        //Convert list of colleges to college details list
        logger.log(Level.FINE, "Convert list of colleges to college details list");
        List<CollegeDetails> details = new ArrayList<>();
        for (College a : colleges) {
            details.add(convertCollegeToCollegeDetails(a));
        }

        //Returning converted college details list
        logger.log(Level.FINE, "Returning converted college details list");
        return details;
    }

    private CollegeDetails convertCollegeToCollegeDetails(College college) {
        //Entered method for converting college to college details
        logger.log(Level.FINE, "Entered method for converting colleges to college details");

        //Convert list of college to college details
        logger.log(Level.FINE, "Convert list of college to college details");
        institutionDetails = new InstitutionDetails();
        institutionDetails.setId(college.getInstitution().getId());

        CollegeDetails details = new CollegeDetails();
        details.setActive(college.getActive());
        details.setName(college.getName());
        details.setId(college.getId());
        details.setAbbreviation(college.getAbbreviation());
        details.setInstitution(institutionDetails);
        details.setVersion(college.getVersion());

        //Returning converted college details
        logger.log(Level.FINE, "Returning converted college details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(CollegeRequests.class.getSimpleName());
}
