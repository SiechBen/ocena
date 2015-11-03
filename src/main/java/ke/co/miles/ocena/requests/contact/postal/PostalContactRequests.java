/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.postal;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Contact;
import ke.co.miles.ocena.entities.Country;
import ke.co.miles.ocena.entities.PostalContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class PostalContactRequests extends EntityRequests implements PostalContactRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public PostalContact addPostalContact(PostalContactDetails details) throws InvalidArgumentException {
        //Method for adding a postal contact record to the database
        logger.log(Level.INFO, "Entered the method for adding a postal contact record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("11-001");
        } else if (details.getBoxNumber() != null) {
            if (details.getBoxNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The box number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getPostalCode() != null) {
            if (details.getPostalCode().trim().length() > 20) {
                logger.log(Level.INFO, "The fixed number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getTown() != null) {
            if (details.getTown().trim().length() > 20) {
                logger.log(Level.INFO, "The town is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact to which the postal contact belongs is null");
            throw new InvalidArgumentException("11-004");
        } else if (details.getCountry() == null) {
            logger.log(Level.INFO, "The country to which the postal contact belongs is null");
            throw new InvalidArgumentException("11-004");
        }

        //Creating a container to hold postal contact record
        logger.log(Level.INFO, "Creating a container to hold postal contact record");
        postalContact = new PostalContact();
        postalContact.setActive(details.getActive());
        postalContact.setTown(details.getTown());
        postalContact.setBoxNumber(details.getBoxNumber());
        postalContact.setPostalCode(details.getPostalCode());
        postalContact.setContact(em.find(Contact.class, details.getContact().getId()));
        postalContact.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Adding a postal contact record to the database
        logger.log(Level.INFO, "Adding a postal contact record to the database");
        try {
            em.persist(postalContact);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("11-001");
        }

        //Returning the new record added
        logger.log(Level.INFO, "Returning the new record added");
        return postalContact;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<PostalContactDetails> retrievePostalContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving postal contact records from the database
        logger.log(Level.INFO, "Entered the method for retrieving postal contact records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact which offers the postal contact is null");
            throw new InvalidArgumentException("11-004");
        }

        //Retrieving postal contact records from the database
        logger.log(Level.INFO, "Retrieving postal contact records from the database");
        q = em.createNamedQuery("PostalContact.findByContactId");
        q.setParameter("contactId", contactId);
        List<PostalContact> postalContacts = new ArrayList<>();
        try {
            postalContacts = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }

        //Returning the details list of postal contact records
        logger.log(Level.INFO, "Returning the details list of postal contact records");
        return convertPostalContactsToPostalContactDetailsList(postalContacts);
    }

    @Override
    public PostalContactDetails retrievePostalContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a postal contact record
        logger.log(Level.INFO, "Entered the method for retrieving postal contact record");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact is null");
            throw new InvalidArgumentException("11-004");
        }

        //Retrieving postal contact records from the database
        logger.log(Level.INFO, "Retrieving the postal contact record from the database");
        q = em.createNamedQuery("PostalContact.findByContactId");
        q.setParameter("contactId", contactId);
        postalContact = new PostalContact();
        try {
            postalContact = (PostalContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }

        //Returning the postal contact record
        logger.log(Level.INFO, "Returning the postal contact record");
        return convertPostalContactToPostalContactDetails(postalContact);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editPostalContact(PostalContactDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a postal contact record in the database
        logger.log(Level.INFO, "Entered the method for editing a postal contact record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("11-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The postal contact's unique identifier is null");
            throw new InvalidArgumentException("11-006");
        } else if (details.getBoxNumber() != null) {
            if (details.getBoxNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The box number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getPostalCode() != null) {
            if (details.getPostalCode().trim().length() > 20) {
                logger.log(Level.INFO, "The fixed number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getTown() != null) {
            if (details.getTown().trim().length() > 20) {
                logger.log(Level.INFO, "The town is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact to which the postal contact belongs is null");
            throw new InvalidArgumentException("11-004");
        } else if (details.getCountry() == null) {
            logger.log(Level.INFO, "The country to which the postal contact belongs is null");
            throw new InvalidArgumentException("11-004");
        }

        //Creating a container to hold postal contact record
        logger.log(Level.INFO, "Creating a container to hold postal contact record");
        postalContact = em.find(PostalContact.class, details.getId());
        postalContact.setId(details.getId());
        postalContact.setTown(details.getTown());
        postalContact.setActive(details.getActive());
        postalContact.setBoxNumber(details.getBoxNumber());
        postalContact.setPostalCode(details.getPostalCode());
        postalContact.setContact(em.find(Contact.class, details.getContact().getId()));
        postalContact.setCountry(em.find(Country.class, details.getCountry().getId()));

        //Editing a postal contact record in the database
        logger.log(Level.INFO, "Editing a postal contact record in the database");
        try {
            em.merge(postalContact);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("11-003");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">

    @Override
    public void removePostalContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a postal contact record from the database
        logger.log(Level.INFO, "Entered the method for removing a postal contact record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("11-006");
        }

        //Get the postal contact record to be removed
        logger.log(Level.INFO, "Getting the postal contact record to be removed");
        q = em.createNamedQuery("PostalContact.findByContactId");
        q.setParameter("contactId", contactId);
        try {
            postalContact = (PostalContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during postal record retrieval", e);
        }
        
        //Removing a postal contact record from the database
        logger.log(Level.INFO, "Removing a postal contact record from the database");
        try {
            em.remove(postalContact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("11-004");

        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<PostalContactDetails> convertPostalContactsToPostalContactDetailsList(List<PostalContact> postalContacts) {
        //Entered method for converting postal contacts list to postal contact details list
        logger.log(Level.FINE, "Entered method for converting postal contacts list to postal contact details list");

        //Convert list of postal contacts to postal contact details list
        logger.log(Level.FINE, "Convert list of postal contacts to postal contact details list");
        List<PostalContactDetails> details = new ArrayList<>();
        for (PostalContact a : postalContacts) {
            details.add(convertPostalContactToPostalContactDetails(a));
        }

        //Returning converted postal contact details list
        logger.log(Level.FINE, "Returning converted postal contact details list");
        return details;
    }

    private PostalContactDetails convertPostalContactToPostalContactDetails(PostalContact postalContact) {
        //Entered method for converting postalContact to postalContact details
        logger.log(Level.FINE, "Entered method for converting postal contacts to postal contact details");

        //Convert list of postalContact to postalContact details
        logger.log(Level.FINE, "Convert list of postal contact to postal contact details");
        contactDetails = new ContactDetails();
        contactDetails.setId(postalContact.getContact().getId());

        countryDetails = new CountryDetails();
        countryDetails.setId(postalContact.getCountry().getId());

        PostalContactDetails details = new PostalContactDetails();
        details.setPostalCode(postalContact.getPostalCode());
        details.setBoxNumber(postalContact.getBoxNumber());
        details.setVersion(postalContact.getVersion());
        details.setActive(postalContact.getActive());
        details.setTown(postalContact.getTown());
        details.setId(postalContact.getId());
        details.setContact(contactDetails);
        details.setCountry(countryDetails);

        //Returning converted postalContact details
        logger.log(Level.FINE, "Returning converted postal contact details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(PostalContactRequests.class.getSimpleName());
}
