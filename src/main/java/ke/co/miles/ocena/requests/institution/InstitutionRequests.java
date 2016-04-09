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
        LOGGER.log(Level.INFO, "Entered the method for adding an institution record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_012_01");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The institution name is null");
            throw new InvalidArgumentException("error_012_02");
        } else if (details.getName().trim().length() > 120) {
            LOGGER.log(Level.INFO, "The institution name is longer than 120 characters");
            throw new InvalidArgumentException("error_012_03");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The institution abbreviation is null");
            throw new InvalidArgumentException("error_012_04");
        } else if (details.getAbbreviation().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The institution abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_012_05");
        } else if (details.getCountry() == null) {
            LOGGER.log(Level.INFO, "The country in which the institution is set up is null");
            throw new InvalidArgumentException("error_012_06");
        }

        //Checking if the institution is a duplicate
        LOGGER.log(Level.INFO, "Checking if the institution name is a duplicate");
        q = em.createNamedQuery("Institution.findByName");
        q.setParameter("name", details.getName());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Institution name is available for use");
            institution = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (institution != null) {
            LOGGER.log(Level.SEVERE, "Institution name is already in use");
            throw new InvalidArgumentException("error_012_07");
        }

        //Checking if the institution abbreviation is a duplicate
        LOGGER.log(Level.INFO, "Checking if the institution abbreviation is a duplicate");
        q = em.createNamedQuery("Institution.findByAbbreviation");
        q.setParameter("institution abbreviation", details.getAbbreviation());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Institution is available for use");
            institution = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (institution != null) {
            LOGGER.log(Level.SEVERE, "Institution institution abbreviation is already in use");
            throw new InvalidArgumentException("error_012_08");
        }

        //Creating a container to hold institution record
        LOGGER.log(Level.INFO, "Creating a container to hold institution record");
        institution = new Institution();
        institution.setName(details.getName());
        institution.setActive(details.getActive());
        institution.setAbbreviation(details.getAbbreviation());
        institution.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Adding an institution record to the database
        LOGGER.log(Level.INFO, "Adding an institution record to the database");
        try {
            em.persist(institution);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return institution.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(List<InstitutionDetails> institutions) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of institution and colleges in it
        LOGGER.log(Level.INFO, "Entered method for retrieving a map of institution and colleges in it");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the institution list details passed in");
        if (institutions == null) {
            LOGGER.log(Level.INFO, "The institution list is null");
            throw new InvalidArgumentException("error_012_09");
        }

        //Retrieve the colleges in the institutions and put them in a map
        LOGGER.log(Level.INFO, "Retrieving the colleges in the institutions and putting them in a map");
        Map<InstitutionDetails, List<CollegeDetails>> institutionCollegesMap = new HashMap<>();
        for (InstitutionDetails i : institutions) {
            try {
                institutionCollegesMap.put(i, collegeService.retrieveColleges(i.getId()));
            } catch (InvalidArgumentException | InvalidStateException ex) {
                Logger.getLogger(CollegeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //Return the map of colleges in the institutions
        LOGGER.log(Level.INFO, "Returning the map of colleges in the institutions");
        return institutionCollegesMap;
    }

    @Override
    public Map<InstitutionDetails, List<CollegeDetails>> retrieveInstitutionColleges(InstitutionDetails institution) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of institution and colleges in it
        LOGGER.log(Level.INFO, "Entered method for retrieving a map of institution and colleges in it");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the institution list details passed in");
        if (institution == null) {
            LOGGER.log(Level.INFO, "The institution is null");
            throw new InvalidArgumentException("error_012_01");
        } else if (institution.getId() == null) {
            LOGGER.log(Level.INFO, "The unique identifier of the institution is null");
            throw new InvalidArgumentException("error_012_10");
        }

        //Retrieve the colleges in the institutions and put them in a map
        LOGGER.log(Level.INFO, "Retrieving the colleges in the institutions and putting them in a map");
        Map<InstitutionDetails, List<CollegeDetails>> institutionCollegesMap = new HashMap<>();
        institutionCollegesMap.put(institution, collegeService.retrieveColleges(institution.getId()));

        //Return the map of colleges in the institutions
        LOGGER.log(Level.INFO, "Returning the map of colleges in the institutions");
        return institutionCollegesMap;
    }

    @Override
    public InstitutionDetails retrieveInstitution(PersonDetails person) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving institution record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the person details passed in");
        if (person == null) {
            LOGGER.log(Level.INFO, "The person is null");
            throw new InvalidArgumentException("error_012_04");
        } else if (person.getId() == null) {
            LOGGER.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_001_13");
        }

        //Finding the faculty member
        LOGGER.log(Level.INFO, "Finding the faculty member");
        q = em.createNamedQuery("FacultyMember.findByPersonId");
        q.setParameter("personId", person.getId());
        facultyMember = new FacultyMember();
        try {
            facultyMember = (FacultyMember) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Obtaining the respective institution
        LOGGER.log(Level.INFO, "Obtaining the respective institution");
        institution = facultyMember.getFaculty().getCollege().getInstitution();

        //Returning the details list of institution records
        LOGGER.log(Level.INFO, "Returning the details list of institution records");
        return convertInstitutionToInstitutionDetails(institution);
    }

    @Override
    public List<InstitutionDetails> retrieveInstitutions(PersonDetails person) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving institution record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the person details passed in");
        if (person == null) {
            LOGGER.log(Level.INFO, "The person is null");
            throw new InvalidArgumentException("error_012_04");
        } else if (person.getId() == null) {
            LOGGER.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_001_13");
        }

        //Finding the faculty member
        LOGGER.log(Level.INFO, "Finding the faculty member");
        q = em.createNamedQuery("FacultyMember.findByPersonId");
        q.setParameter("personId", person.getId());
        List<FacultyMember> facultyMembers;
        try {
            facultyMembers = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_012_02");
        }

        //Obtaining the respective institutions
        LOGGER.log(Level.INFO, "Obtaining the respective institutions");
        List<Institution> institutions = new ArrayList<>();
        for (FacultyMember s : facultyMembers) {
            institutions.add(s.getFaculty().getCollege().getInstitution());
        }

        //Returning the details list of institution records
        LOGGER.log(Level.INFO, "Returning the details list of institution records");
        return convertInstitutionsToInstitutionDetailsList(institutions);
    }

    @Override
    public InstitutionDetails retrieveInstitution(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving institution records from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The institution's unique identifier is null");
            throw new InvalidArgumentException("error_012_10");
        }

        //Retrieving the institution record from the database
        LOGGER.log(Level.INFO, "Retrieving the institution record from the database");
        q = em.createNamedQuery("Institution.findById");
        q.setParameter("id", id);
        try {
            institution = (Institution) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the institution record
        LOGGER.log(Level.INFO, "Returning the details of the institution record");
        return convertInstitutionToInstitutionDetails(institution);
    }

    @Override
    public InstitutionDetails retrieveInstitution() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving institution records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving institution records from the database");

        //Retrieving the institution record from the database
        LOGGER.log(Level.INFO, "Retrieving the institution record from the database");
        q = em.createNamedQuery("Institution.findAll");
        List<Institution> institutions = new ArrayList<>();
        institution = new Institution();
        try {
            institutions = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        for (Institution i : institutions) {
            if (i.getActive()) {
                institution = i;
                break;
            }
        }

        //Returning the details of the institution record
        LOGGER.log(Level.INFO, "Returning the details of the institution record");
        return convertInstitutionToInstitutionDetails(institution);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editInstitution(InstitutionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an institution record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing an institution record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_012_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The institution's unique identifier is null");
            throw new InvalidArgumentException("error_012_10");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The institution name is null");
            throw new InvalidArgumentException("error_012_02");
        } else if (details.getName().trim().length() > 120) {
            LOGGER.log(Level.INFO, "The institution name is longer than 120 characters");
            throw new InvalidArgumentException("error_012_03");
        } else if (details.getAbbreviation() == null || details.getAbbreviation().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The institution abbreviation is null");
            throw new InvalidArgumentException("error_012_04");
        } else if (details.getAbbreviation().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The institution abbreviation is longer than 20 characters");
            throw new InvalidArgumentException("error_012_05");
        } else if (details.getCountry() == null) {
            LOGGER.log(Level.INFO, "The country in which the institution is set up is null");
            throw new InvalidArgumentException("error_012_06");
        }
        //Checking if the institution is a duplicate
        LOGGER.log(Level.INFO, "Checking if the institution name is a duplicate");
        q = em.createNamedQuery("Institution.findByName");
        q.setParameter("name", details.getName());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Institution name is available for use");
            institution = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (institution != null) {
            if (!institution.getId().equals(details.getId())) {
                LOGGER.log(Level.SEVERE, "Institution name is already in use");
                throw new InvalidArgumentException("error_012_07");
            }
        }

        //Checking if the institution abbreviation is a duplicate
        LOGGER.log(Level.INFO, "Checking if the institution abbreviation is a duplicate");
        q = em.createNamedQuery("Institution.findByAbbreviation");
        q.setParameter("institution abbreviation", details.getAbbreviation());
        try {
            institution = (Institution) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Institution is available for use");
            institution = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (institution != null) {
            if (!institution.getId().equals(details.getId())) {
                LOGGER.log(Level.SEVERE, "Institution institution abbreviation is already in use");
                throw new InvalidArgumentException("error_012_08");
            }
        }

        //Creating a container to hold institution record
        LOGGER.log(Level.INFO, "Creating a container to hold institution record");
        institution = em.find(Institution.class, details.getId());
        institution.setId(details.getId());
        institution.setName(details.getName());
        institution.setActive(details.getActive());
        institution.setAbbreviation(details.getAbbreviation());
        institution.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Editing an institution record in the database
        LOGGER.log(Level.INFO, "Editing an institution record in the database");
        try {
            em.merge(institution);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeInstitution(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an institution record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing an institution record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_012_10");
        }

        //Removing an institution record from the database
        LOGGER.log(Level.INFO, "Removing an institution record from the database");
        institution = em.find(Institution.class, id);
        try {
            em.remove(institution);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<InstitutionDetails> convertInstitutionsToInstitutionDetailsList(List<Institution> institutions) {
        //Entered method for converting institutions list to institution details list
        LOGGER.log(Level.FINE, "Entered method for converting institutions list to institution details list");

        //Convert list of institutions to institution details list
        LOGGER.log(Level.FINE, "Convert list of institutions to institution details list");
        List<InstitutionDetails> details = new ArrayList<>();
        for (Institution a : institutions) {
            details.add(convertInstitutionToInstitutionDetails(a));
        }

        //Returning converted institution details list
        LOGGER.log(Level.FINE, "Returning converted institution details list");
        return details;
    }

    private InstitutionDetails convertInstitutionToInstitutionDetails(Institution institution) {
        //Entered method for converting institution to institution details
        LOGGER.log(Level.FINE, "Entered method for converting institutions to institution details");

        //Convert list of institution to institution details
        LOGGER.log(Level.FINE, "Convert list of institution to institution details");
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
            LOGGER.log(Level.FINE, "An error occurred during conversion of institution to details");
        }

        //Returning converted institution details
        LOGGER.log(Level.FINE, "Returning converted institution details");
        return details;
    }
//</editor-fold>
    
    private static final Logger LOGGER = Logger.getLogger(InstitutionRequests.class.getSimpleName());

}
