/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.institution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.controllers.CollegeController;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Country;
import ke.co.miles.ocena.entities.Institution;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class InstitutionRequests extends EntityRequests implements InstitutionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addInstitution(InstitutionDetails details) throws InvalidArgumentException {
        //Method for adding an institution record to the database
        logger.log(Level.INFO, "Entered the method for adding an institution record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("13-001");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The institution name is null");
            throw new InvalidArgumentException("13-002");
        } else if (details.getName().trim().length() > 60) {
            logger.log(Level.INFO, "The institution name is longer than 60 characters");
            throw new InvalidArgumentException("13-003");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The abbreviation is null");
            throw new InvalidArgumentException("13-004");
        } else if (details.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("13-005");
        } else if (details.getCountry() == null) {
            logger.log(Level.INFO, "The country in which the institution is set up is null");
            throw new InvalidArgumentException("13-006");
        }

        //Checking if the institution is a duplicate
        logger.log(Level.INFO, "Checking if the institution name is a duplicate");
        q = em.createNamedQuery("Institution.findByName");
        q.setParameter("name", details.getName());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Institution name is available for use");
            institution = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (institution != null) {
            logger.log(Level.SEVERE, "Institution name is already in use");
            throw new InvalidArgumentException("13-007");
        }

        //Checking if the abbreviation is a duplicate
        logger.log(Level.INFO, "Checking if the abbreviation is a duplicate");
        q = em.createNamedQuery("Institution.findByAbbreviation");
        q.setParameter("abbreviation", details.getAbbreviation());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Institution is available for use");
            institution = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (institution != null) {
            logger.log(Level.SEVERE, "Abbreviation is already in use");
            throw new InvalidArgumentException("13-008");
        }

        //Creating a container to hold institution record
        logger.log(Level.INFO, "Creating a container to hold institution record");
        institution = new Institution();
        institution.setName(details.getName());
        institution.setActive(details.getActive());
        institution.setAbbreviation(details.getAbbreviation());
        institution.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Adding an institution record to the database
        logger.log(Level.INFO, "Adding an institution record to the database");
        try {
            em.persist(institution);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("0-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return institution.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(List<InstitutionDetails> institutions) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of institution and colleges in it
        logger.log(Level.INFO, "Entered method for retrieving a map of institution and colleges in it");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution list details passed in");
        if (institutions == null) {
            logger.log(Level.INFO, "The institution list is null");
            throw new InvalidArgumentException("13-004");
        }

        //Retrieve the colleges in the institutions and put them in a map
        logger.log(Level.INFO, "Retrieving the colleges in the institutions and putting them in a map");
        Map<InstitutionDetails, List<CollegeDetails>> institutionCollegesMap = new HashMap<>();
        for (InstitutionDetails i : institutions) {
            try {
                institutionCollegesMap.put(i, collegeService.retrieveColleges(i.getId()));
            } catch (InvalidArgumentException | InvalidStateException ex) {
                Logger.getLogger(CollegeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Return the map of colleges in the institutions
        logger.log(Level.INFO, "Returning the map of colleges in the institutions");
        return institutionCollegesMap;
    }

    @Override
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(InstitutionDetails institution) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of institution and colleges in it
        logger.log(Level.INFO, "Entered method for retrieving a map of institution and colleges in it");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution list details passed in");
        if (institution == null) {
            logger.log(Level.INFO, "The institution list is null");
            throw new InvalidArgumentException("13-004");
        }

        //Retrieve the colleges in the institutions and put them in a map
        logger.log(Level.INFO, "Retrieving the colleges in the institutions and putting them in a map");
        Map<InstitutionDetails, List<CollegeDetails>> institutionCollegesMap = new HashMap<>();
        institutionCollegesMap.put(institution, collegeService.retrieveColleges(institution.getId()));

        //Return the map of colleges in the institutions
        logger.log(Level.INFO, "Returning the map of colleges in the institutions");
        return institutionCollegesMap;
    }

    @Override
    public InstitutionDetails retrieveInstitution(PersonDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution record from the database
        logger.log(Level.INFO, "Entered the method for retrieving institution record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the person details passed in");
        if (person == null) {
            logger.log(Level.INFO, "The person is null");
            throw new InvalidArgumentException("13-004");
        }

        //Finding the faculty member
        logger.log(Level.INFO, "Finding the faculty member");
        q = em.createNamedQuery("FacultyMember.findByPersonId");
        q.setParameter("personId", details.getId());
        facultyMember = new FacultyMember();
        try {
            facultyMember = (FacultyMember) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("13-002");
        }

        //Obtaining the respective institution
        logger.log(Level.INFO, "Obtaining the respective institution");
        institution = facultyMember.getFaculty().getCollege().getInstitution();

        //Returning the details list of institution records
        logger.log(Level.INFO, "Returning the details list of institution records");
        return convertInstitutionToInstitutionDetails(institution);
    }

    @Override
    public List<InstitutionDetails> retrieveInstitutions(PersonDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution record from the database
        logger.log(Level.INFO, "Entered the method for retrieving institution record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the person details passed in");
        if (person == null) {
            logger.log(Level.INFO, "The person is null");
            throw new InvalidArgumentException("13-004");
        }

        //Finding the faculty member
        logger.log(Level.INFO, "Finding the faculty member");
        q = em.createNamedQuery("FacultyMember.findByPersonId");
        q.setParameter("personId", details.getId());
        List<FacultyMember> facultyMembers;
        try {
            facultyMembers = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("13-002");
        }

        //Obtaining the respective institutions
        logger.log(Level.INFO, "Obtaining the respective institutions");
        List<Institution> institutions = new ArrayList<>();
        for (FacultyMember s : facultyMembers) {
            institutions.add(s.getFaculty().getCollege().getInstitution());
        }

        //Returning the details list of institution records
        logger.log(Level.INFO, "Returning the details list of institution records");
        return convertInstitutionsToInstitutionDetailsList(institutions);
    }

    @Override
    public InstitutionDetails retrieveInstitution(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution records from the database
        logger.log(Level.INFO, "Entered the method for retrieving institution records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The institution's unique identifier is null");
            throw new InvalidArgumentException("13-004");
        }

        //Retrieving the institution record from the database
        logger.log(Level.INFO, "Retrieving the institution record from the database");
        q = em.createNamedQuery("Institution.findById");
        q.setParameter("id", id);
        try {
            institution = (Institution) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }

        //Returning the details of the institution record
        logger.log(Level.INFO, "Returning the details of the institution record");
        return convertInstitutionToInstitutionDetails(institution);
    }

    @Override
    public InstitutionDetails retrieveInstitution() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution records from the database
        logger.log(Level.INFO, "Entered the method for retrieving institution records from the database");

        //Retrieving the institution record from the database
        logger.log(Level.INFO, "Retrieving the institution record from the database");
        q = em.createNamedQuery("Institution.findAll");
        List<Institution> institutions = new ArrayList<>();
        institution = new Institution();
        try {
            institutions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        for (Institution i : institutions) {
            if (i.getActive()) {
                institution = i;
                break;
            }
        }

        //Returning the details of the institution record
        logger.log(Level.INFO, "Returning the details of the institution record");
        return convertInstitutionToInstitutionDetails(institution);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editInstitution(InstitutionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an institution record in the database
        logger.log(Level.INFO, "Entered the method for editing an institution record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("13-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The institution's unique identifier is null");
            throw new InvalidArgumentException("13-009");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The institution name is null");
            throw new InvalidArgumentException("13-002");
        } else if (details.getName().trim().length() > 60) {
            logger.log(Level.INFO, "The institution name is longer than 60 characters");
            throw new InvalidArgumentException("13-003");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            logger.log(Level.INFO, "The abbreviation is null");
            throw new InvalidArgumentException("13-004");
        } else if (details.getAbbreviation().trim().length() > 20) {
            logger.log(Level.INFO, "The abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("13-005");
        } else if (details.getCountry() == null) {
            logger.log(Level.INFO, "The country in which the institution is set up is null");
            throw new InvalidArgumentException("13-006");
        }
        //Checking if the institution is a duplicate
        logger.log(Level.INFO, "Checking if the institution name is a duplicate");
        q = em.createNamedQuery("Institution.findByName");
        q.setParameter("name", details.getName());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Institution name is available for use");
            institution = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (institution != null) {
            if (!institution.getId().equals(details.getId())) {
                logger.log(Level.SEVERE, "Institution name is already in use");
                throw new InvalidArgumentException("13-007");
            }
        }

        //Checking if the abbreviation is a duplicate
        logger.log(Level.INFO, "Checking if the abbreviation is a duplicate");
        q = em.createNamedQuery("Institution.findByAbbreviation");
        q.setParameter("abbreviation", details.getAbbreviation());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Institution is available for use");
            institution = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("0-002");
        }
        if (institution != null) {
            if (!institution.getId().equals(details.getId())) {
                logger.log(Level.SEVERE, "Abbreviation is already in use");
                throw new InvalidArgumentException("13-008");
            }
        }

        //Creating a container to hold institution record
        logger.log(Level.INFO, "Creating a container to hold institution record");
        institution = em.find(Institution.class, details.getId());
        institution.setId(details.getId());
        institution.setName(details.getName());
        institution.setActive(details.getActive());
        institution.setAbbreviation(details.getAbbreviation());
        institution.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Editing an institution record in the database
        logger.log(Level.INFO, "Editing an institution record in the database");
        try {
            em.merge(institution);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("0-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeInstitution(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an institution record from the database
        logger.log(Level.INFO, "Entered the method for removing an institution record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("13-009");
        }

        //Removing an institution record from the database
        logger.log(Level.INFO, "Removing an institution record from the database");
        institution = em.find(Institution.class, id);
        try {
            em.remove(institution);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("0-004");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<InstitutionDetails> convertInstitutionsToInstitutionDetailsList(List<Institution> institutions) {
        //Entered method for converting institutions list to institution details list
        logger.log(Level.FINE, "Entered method for converting institutions list to institution details list");

        //Convert list of institutions to institution details list
        logger.log(Level.FINE, "Convert list of institutions to institution details list");
        List<InstitutionDetails> details = new ArrayList<>();
        for (Institution a : institutions) {
            details.add(convertInstitutionToInstitutionDetails(a));
        }

        //Returning converted institution details list
        logger.log(Level.FINE, "Returning converted institution details list");
        return details;
    }

    private InstitutionDetails convertInstitutionToInstitutionDetails(Institution institution) {
        //Entered method for converting institution to institution details
        logger.log(Level.FINE, "Entered method for converting institutions to institution details");

        //Convert list of institution to institution details
        logger.log(Level.FINE, "Convert list of institution to institution details");
        countryDetails = new CountryDetails();
        InstitutionDetails details = new InstitutionDetails();

        try {
            countryDetails.setId(institution.getCountry().getId());
            countryDetails.setIso(institution.getCountry().getIso());
            countryDetails.setName(institution.getCountry().getName());
            countryDetails.setNiceName(institution.getCountry().getNiceName());

            details.setAbbreviation(institution.getAbbreviation());
            details.setVersion(institution.getVersion());
            details.setActive(institution.getActive());
            details.setName(institution.getName());
            details.setCountry(countryDetails);
            details.setId(institution.getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "An error occurred during conversion of institution to details");
        }

        //Returning converted institution details
        logger.log(Level.FINE, "Returning converted institution details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(InstitutionRequests.class.getSimpleName());

}
