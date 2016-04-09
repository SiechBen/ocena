/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.admission;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Admission;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AdmissionDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class AdmissionRequests extends EntityRequests implements AdmissionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addAdmission(AdmissionDetails details) throws InvalidArgumentException {
        //Method for adding an admission record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding an admission record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_008_01");
        } else if (details.getAdmission() == null || details.getAdmission().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The admission is null");
            throw new InvalidArgumentException("error_008_02");
        } else if (details.getAdmission().trim().length() > 60) {
            LOGGER.log(Level.INFO, "The admission is longer than 60 characters");
            throw new InvalidArgumentException("error_008_03");
        }

        //Checking if the admission is a duplicate
        LOGGER.log(Level.INFO, "Checking if the admission is a duplicate");
        q = em.createNamedQuery("Admission.findByAdmission");
        q.setParameter("admission", details.getAdmission());
        try {
            admission = (Admission) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Admission is available for use");
            admission = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (admission != null) {
            LOGGER.log(Level.SEVERE, "Admission is already in use");
            throw new InvalidArgumentException("error_008_04");
        }

        //Creating a container to hold admission record
        LOGGER.log(Level.INFO, "Creating a container to hold admission record");
        admission = new Admission();
        admission.setActive(details.getActive());
        admission.setAdmission(details.getAdmission());

        //Adding an admission record to the database
        LOGGER.log(Level.INFO, "Adding an admission record to the database");
        try {
            em.persist(admission);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return admission.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<AdmissionDetails> retrieveAdmissions() throws InvalidStateException {
        //Method for retrieving admission records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving admission records from the database");

        //Retrieving admission records from the database
        LOGGER.log(Level.INFO, "Retrieving admission records from the database");
        q = em.createNamedQuery("Admission.findAll");
        List<Admission> admissions = new ArrayList<>();
        try {
            admissions = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("error_000_01");
        }

        //Returning the details list of admission records
        LOGGER.log(Level.INFO, "Returning the details list of admission records");
        return convertAdmissionsToAdmissionDetailsList(admissions);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editAdmission(AdmissionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an admission record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing an admission record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_008_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The admission's unique identifier is null");
            throw new InvalidArgumentException("error_008_05");
        } else if (details.getAdmission() == null || details.getAdmission().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The admission is null");
            throw new InvalidArgumentException("error_008_02");
        } else if (details.getAdmission().trim().length() > 60) {
            LOGGER.log(Level.INFO, "The admission is longer than 60 characters");
            throw new InvalidArgumentException("error_008_03");
        }

        //Checking if the admission is a duplicate
        LOGGER.log(Level.INFO, "Checking if the admission is a duplicate");
        q = em.createNamedQuery("Admission.findByAdmission");
        q.setParameter("admission", details.getAdmission());
        try {
            admission = (Admission) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Admission is available for use");
            admission = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        if (admission != null) {
            if (!(admission.getId().equals(details.getId()))) {
                LOGGER.log(Level.SEVERE, "Admission is already in use");
                throw new InvalidArgumentException("error_008_04");
            }
        }

        //Creating a container to hold admission record
        LOGGER.log(Level.INFO, "Creating a container to hold admission record");
        admission = em.find(Admission.class, details.getId());
        admission.setId(details.getId());
        admission.setActive(details.getActive());
        admission.setAdmission(details.getAdmission());

        //Editing an admission record in the database
        LOGGER.log(Level.INFO, "Editing an admission record in the database");
        try {
            em.merge(admission);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeAdmission(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an admission record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing an admission record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_008_05");
        }

        //Removing an admission record from the database
        LOGGER.log(Level.INFO, "Removing an admission record from the database");
        admission = em.find(Admission.class, id);
        try {
            em.remove(admission);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<AdmissionDetails> convertAdmissionsToAdmissionDetailsList(List<Admission> admissions) {
        //Entered method for converting admissions list to admission details list
        LOGGER.log(Level.FINE, "Entered method for converting admissions list to admission details list");

        //Convert list of admissions to admission details list
        LOGGER.log(Level.FINE, "Convert list of admissions to admission details list");
        List<AdmissionDetails> details = new ArrayList<>();
        for (Admission a : admissions) {
            details.add(convertAdmissionToAdmissionDetails(a));
        }

        //Returning converted admission details list
        LOGGER.log(Level.FINE, "Returning converted admission details list");
        return details;
    }

    @Override
    public AdmissionDetails convertAdmissionToAdmissionDetails(Admission admission) {
        //Entered method for converting admission to admission details
        LOGGER.log(Level.FINE, "Entered method for converting admissions to admission details");

        //Convert list of admission to admission details
        LOGGER.log(Level.FINE, "Convert list of admission to admission details");
        AdmissionDetails details = new AdmissionDetails();
        details.setActive(admission.getActive());
        details.setAdmission(admission.getAdmission());
        details.setId(admission.getId());
        details.setVersion(admission.getVersion());

        //Returning converted admission details
        LOGGER.log(Level.FINE, "Returning converted admission details");
        return details;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(AdmissionRequests.class.getSimpleName());
}
