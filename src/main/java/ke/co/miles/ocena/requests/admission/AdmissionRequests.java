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
        logger.log(Level.INFO, "Entered the method for adding an admission record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("1-001");
        } else if (details.getAdmission() == null || details.getAdmission().trim().length() == 0) {
            logger.log(Level.INFO, "The admission is null");
            throw new InvalidArgumentException("1-002");
        } else if (details.getAdmission().trim().length() > 60) {
            logger.log(Level.INFO, "The admission is longer than 60 characters");
            throw new InvalidArgumentException("1-003");
        }

        //Checking if the admission is a duplicate
        logger.log(Level.INFO, "Checking if the admission is a duplicate");
        q = em.createNamedQuery("Admission.findByAdmission");
        q.setParameter("admission", details.getAdmission());
        try {
            admission = (Admission) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Admission is available for use");
            admission = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("1-002");
        }
        if (admission != null) {
            logger.log(Level.SEVERE, "Admission is already in use");
            throw new InvalidArgumentException("1-005");
        }

        //Creating a container to hold admission record
        logger.log(Level.INFO, "Creating a container to hold admission record");
        admission = new Admission();
        admission.setActive(details.getActive());
        admission.setAdmission(details.getAdmission());

        //Adding an admission record to the database
        logger.log(Level.INFO, "Adding an admission record to the database");
        try {
            em.persist(admission);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("1-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return admission.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<AdmissionDetails> retrieveAdmissions() throws InvalidStateException {
        //Method for retrieving admission records from the database
        logger.log(Level.INFO, "Entered the method for retrieving admission records from the database");

        //Retrieving admission records from the database
        logger.log(Level.INFO, "Retrieving admission records from the database");
        q = em.createNamedQuery("Admission.findAll");
        List<Admission> admissions = new ArrayList<>();
        try {
            admissions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new InvalidStateException("1-002");
        }

        //Returning the details list of admission records
        logger.log(Level.INFO, "Returning the details list of admission records");
        return convertAdmissionsToAdmissionDetailsList(admissions);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editAdmission(AdmissionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an admission record in the database
        logger.log(Level.INFO, "Entered the method for editing an admission record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("1-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The admission's unique identifier is null");
            throw new InvalidArgumentException("1-006");
        } else if (details.getAdmission() == null || details.getAdmission().trim().length() == 0) {
            logger.log(Level.INFO, "The admission is null");
            throw new InvalidArgumentException("1-002");
        } else if (details.getAdmission().trim().length() > 60) {
            logger.log(Level.INFO, "The admission is longer than 60 characters");
            throw new InvalidArgumentException("1-003");
        }

        //Checking if the admission is a duplicate
        logger.log(Level.INFO, "Checking if the admission is a duplicate");
        q = em.createNamedQuery("Admission.findByAdmission");
        q.setParameter("admission", details.getAdmission());
        try {
            admission = (Admission) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Admission is available for use");
            admission = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("1-002");
        }
        if (admission != null) {
            if (!(admission.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Admission is already in use");
                throw new InvalidArgumentException("1-005");
            }
        }

        //Creating a container to hold admission record
        logger.log(Level.INFO, "Creating a container to hold admission record");
        admission = em.find(Admission.class, details.getId());
        admission.setId(details.getId());
        admission.setActive(details.getActive());
        admission.setAdmission(details.getAdmission());

        //Editing an admission record in the database
        logger.log(Level.INFO, "Editing an admission record in the database");
        try {
            em.merge(admission);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("1-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeAdmission(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an admission record from the database
        logger.log(Level.INFO, "Entered the method for removing an admission record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("1-006");
        }

        //Removing an admission record from the database
        logger.log(Level.INFO, "Removing an admission record from the database");
        admission = em.find(Admission.class, id);
        try {
            em.remove(admission);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("1-004");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<AdmissionDetails> convertAdmissionsToAdmissionDetailsList(List<Admission> admissions) {
        //Entered method for converting admissions list to admission details list
        logger.log(Level.FINE, "Entered method for converting admissions list to admission details list");

        //Convert list of admissions to admission details list
        logger.log(Level.FINE, "Convert list of admissions to admission details list");
        List<AdmissionDetails> details = new ArrayList<>();
        for (Admission a : admissions) {
            details.add(convertAdmissionToAdmissionDetails(a));
        }

        //Returning converted admission details list
        logger.log(Level.FINE, "Returning converted admission details list");
        return details;
    }

    @Override
    public AdmissionDetails convertAdmissionToAdmissionDetails(Admission admission) {
        //Entered method for converting admission to admission details
        logger.log(Level.FINE, "Entered method for converting admissions to admission details");

        //Convert list of admission to admission details
        logger.log(Level.FINE, "Convert list of admission to admission details");
        AdmissionDetails details = new AdmissionDetails();
        details.setActive(admission.getActive());
        details.setAdmission(admission.getAdmission());
        details.setId(admission.getId());
        details.setVersion(admission.getVersion());

        //Returning converted admission details
        logger.log(Level.FINE, "Returning converted admission details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(AdmissionRequests.class.getSimpleName());
}
