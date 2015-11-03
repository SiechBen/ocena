/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.email;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Contact;
import ke.co.miles.ocena.entities.EmailContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EmailContactRequests extends EntityRequests implements EmailContactRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public EmailContact addEmailContact(EmailContactDetails details) throws InvalidArgumentException {
        //Method for adding an email contact record to the database
        logger.log(Level.INFO, "Entered the method for adding an email contact record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("10-001");
        } else if (details.getEmailAddress() != null) {
            if (details.getEmailAddress().trim().length() > 65) {
                logger.log(Level.INFO, "The email address is longer than 65 characters");
                throw new InvalidArgumentException("10-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact which offers the email contact is null");
            throw new InvalidArgumentException("10-004");
        }

        //Checking if the email contact is a duplicate
        logger.log(Level.INFO, "Checking if the email address is a duplicate");
        q = em.createNamedQuery("EmailContact.findByEmailAddress");
        q.setParameter("emailAddress", details.getEmailAddress());
        try {
            emailContact = (EmailContact) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Email address is available for use");
            emailContact = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("10-002");
        }
        if (emailContact != null) {
            logger.log(Level.SEVERE, "Email address is already in use");
            throw new InvalidArgumentException("10-005");
        }

        //Creating a container to hold email contact record
        logger.log(Level.INFO, "Creating a container to hold email contact record");
        emailContact = new EmailContact();
        emailContact.setActive(details.getActive());
        emailContact.setEmailAddress(details.getEmailAddress());
        emailContact.setContact(em.find(Contact.class, details.getContact().getId()));

        //Adding an email contact record to the database
        logger.log(Level.INFO, "Adding an email contact record to the database");
        try {
            em.persist(emailContact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("10-001");
        }

        //Returning the new record added
        logger.log(Level.INFO, "Returning the new record added");
        return emailContact;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<EmailContactDetails> retrieveEmailContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving email contact records from the database
        logger.log(Level.INFO, "Entered the method for retrieving email contact records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact which offers the email contact is null");
            throw new InvalidArgumentException("10-004");
        }

        //Retrieving email contact records from the database
        logger.log(Level.INFO, "Retrieving email contact records from the database");
        q = em.createNamedQuery("EmailContact.findByContactId");
        q.setParameter("contactId", contactId);
        List<EmailContact> emailContacts = new ArrayList<>();
        try {
            emailContacts = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("10-002");
        }

        //Returning the details list of email contact records
        logger.log(Level.INFO, "Returning the details list of email contact records");
        return convertEmailContactsToEmailContactDetailsList(emailContacts);
    }

    @Override
    public EmailContactDetails retrieveEmailContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving an email contact record
        logger.log(Level.INFO, "Entered the method for retrieving an email contact record");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact is null");
            throw new InvalidArgumentException("10-004");
        }

        //Retrieving email contact records from the database
        logger.log(Level.INFO, "Retrieving the email contact record from the database");
        q = em.createNamedQuery("EmailContact.findByContactId");
        q.setParameter("contactId", contactId);
        try {
            emailContact = (EmailContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("10-002");
        }

        //Returning the details list of email contact records
        logger.log(Level.INFO, "Returning the email contact record");
        return convertEmailContactToEmailContactDetails(emailContact);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editEmailContact(EmailContactDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an email contact record in the database
        logger.log(Level.INFO, "Entered the method for editing an email contact record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("10-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The email contact's unique identifier is null");
            throw new InvalidArgumentException("10-006");
        } else if (details.getEmailAddress() != null) {
            if (details.getEmailAddress().trim().length() > 65) {
                logger.log(Level.INFO, "The email address is longer than 65 characters");
                throw new InvalidArgumentException("10-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact which offers the email contact is null");
            throw new InvalidArgumentException("10-004");
        }

        //Checking if the email contact is a duplicate
        logger.log(Level.INFO, "Checking if the email address is a duplicate");
        q = em.createNamedQuery("EmailContact.findByEmailAddress");
        q.setParameter("emailAddress", details.getEmailAddress());
        try {
            emailContact = (EmailContact) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Email address is available for use");
            emailContact = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("10-002");
        }
        if (emailContact != null) {
            if (!(emailContact.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Email address is already in use");
                throw new InvalidArgumentException("10-005");
            }
        }

        //Creating a container to hold email contact record
        logger.log(Level.INFO, "Creating a container to hold email contact record");
        emailContact = em.find(EmailContact.class, details.getId());
        emailContact.setId(details.getId());
        emailContact.setEmailAddress(details.getEmailAddress());
        emailContact.setActive(details.getActive());
        emailContact.setContact(em.find(Contact.class, details.getContact().getId()));

        //Editing an email contact record in the database
        logger.log(Level.INFO, "Editing an email contact record in the database");
        try {
            em.merge(emailContact);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("10-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeEmailContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an email contact record from the database
        logger.log(Level.INFO, "Entered the method for removing an email contact record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("10-006");
        }

        //Get the email contact record to be removed
        logger.log(Level.INFO, "Getting the email contact record to be removed");
        q = em.createNamedQuery("EmailContact.findByContactId");
        q.setParameter("contactId", contactId);
        try {
            emailContact = (EmailContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during phone record retrieval", e);
        }

        //Remove an email contact record from the database
        logger.log(Level.INFO, "Removing an email contact record from the database");
        try {
            em.remove(emailContact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("10-004");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<EmailContactDetails> convertEmailContactsToEmailContactDetailsList(List<EmailContact> emailContacts) {
        //Entered method for converting email contacts list to email contact details list
        logger.log(Level.FINE, "Entered method for converting email contacts list to email contact details list");

        //Convert list of email contacts to email contact details list
        logger.log(Level.FINE, "Convert list of email contacts to email contact details list");
        List<EmailContactDetails> details = new ArrayList<>();
        for (EmailContact a : emailContacts) {
            details.add(convertEmailContactToEmailContactDetails(a));
        }

        //Returning converted email contact details list
        logger.log(Level.FINE, "Returning converted email contact details list");
        return details;
    }

    private EmailContactDetails convertEmailContactToEmailContactDetails(EmailContact emailContact) {
        //Entered method for converting emailContact to emailContact details
        logger.log(Level.FINE, "Entered method for converting email contacts to email contact details");

        //Convert list of emailContact to emailContact details
        logger.log(Level.FINE, "Convert list of email contact to email contact details");
        contactDetails = new ContactDetails();
        contactDetails.setId(emailContact.getContact().getId());

        EmailContactDetails details = new EmailContactDetails();
        details.setEmailAddress(emailContact.getEmailAddress());
        details.setVersion(emailContact.getVersion());
        details.setActive(emailContact.getActive());
        details.setId(emailContact.getId());
        details.setContact(contactDetails);

        //Returning converted emailContact details
        logger.log(Level.FINE, "Returning converted email contact details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(EmailContactRequests.class.getSimpleName());
}
