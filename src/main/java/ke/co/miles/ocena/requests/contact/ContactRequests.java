/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Contact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class ContactRequests extends EntityRequests implements ContactRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Contact addContact(EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException {
        //Method for adding a contact record to the database
        logger.log(Level.INFO, "Entered method for adding a contact record to the database");

        //Create a contact record
        logger.log(Level.INFO, "Creating contact record");
        contact = new Contact();
        contact.setActive(true);

        //Create a contact record in the database
        logger.log(Level.INFO, "Creating contact a record in the database");
        try {
            em.persist(contact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during contact record creation", e);
            throw new EJBException("0-001");
        }

        contactDetails = new ContactDetails();
        contactDetails.setId(contact.getId());

        //Create an email contact record
        logger.log(Level.INFO, "Creating an email contact record");
        emailContactDetails.setContact(contactDetails);
        emailContact = emailContactService.addEmailContact(emailContactDetails);

        //Create a phone contact record
        logger.log(Level.INFO, "Creating a phone contact record");
        phoneContactDetails.setContact(contactDetails);
        phoneContact = phoneContactService.addPhoneContact(phoneContactDetails);

        //Create a postal contact record
        logger.log(Level.INFO, "Creating a postal contact record");
        postalContactDetails.setContact(contactDetails);
        postalContact = postalContactService.addPostalContact(postalContactDetails);

        //Return the contact record created
        logger.log(Level.INFO, "Returning the contact record created");
        return contact;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<ContactDetails> retrieveContacts() throws InvalidStateException {
        //Method for retrieving contact records from the database
        logger.log(Level.INFO, "Entered method for retrieving contact records from the database");

        //Retrieving contact records
        logger.log(Level.INFO, "Retrieving contact records from the database");
        List<Contact> contacts = new ArrayList<>();
        q = em.createNamedQuery("Contact.findAll");
        try {
            contacts = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during retrieval", e);
            throw new InvalidStateException("0-002");
        }

        //Returning the record list
        return convertContactsToContactDetailsList(contacts);
    }

    @Override
    public ContactDetails retrieveContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving contact records from the database
        logger.log(Level.INFO, "Entered method for retrieving contact records from the database");

        //Check contact's unique identifier to be unique
        logger.log(Level.INFO, "Checking if unique identfier is valid");
        if (contactId == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("6-001");
        }

        //Retrieving contact records
        logger.log(Level.INFO, "Retrieving contact records from the database");
        contact = new Contact();
        q = em.createNamedQuery("Contact.findById");
        q.setParameter("id", contactId);
        try {
            contact = (Contact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during retrieval", e);
            throw new InvalidStateException("0-002");
        }

        //Returning the record list
        return convertContactToContactDetails(contact);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">

    @Override
    public Contact editContact(ContactDetails contactDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException {
        //Checking valididty of details
        logger.log(Level.INFO, "Checking validity of details");
        if (contactDetails == null) {
            logger.log(Level.INFO, "Contact details are null");
            throw new InvalidArgumentException("6-002");
        }

        //Create a contact record
        logger.log(Level.INFO, "Creating contact record");
        contact = em.find(Contact.class, contactDetails.getId());
        contact.setActive(contactDetails.getActive());
        contact.setId(contactDetails.getId());

        //Update a contact record in the database
        logger.log(Level.INFO, "Editing contact a record in the database");
        try {
            em.merge(contact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during contact record update", e);
            throw new EJBException("0-001");
        }

        //Create an email contact record
        logger.log(Level.INFO, "Creating an email contact record");

        emailContactDetails.setContact(contactDetails);
        try {
            emailContactService.editEmailContact(emailContactDetails);
        } catch (InvalidStateException ex) {
            logger.log(Level.SEVERE, "An error occurred during email contact record editing", ex);
        }

        //Create a phone contact record
        logger.log(Level.INFO, "Creating a phone contact record");
        phoneContactDetails.setContact(contactDetails);
        try {
            phoneContactService.editPhoneContact(phoneContactDetails);
        } catch (InvalidStateException ex) {
            logger.log(Level.SEVERE, "An error occurred during phone contact record editing", ex);
        }

        //Create a postal contact record
        logger.log(Level.INFO, "Creating a postal contact record");
        postalContactDetails.setContact(contactDetails);
        try {
            postalContactService.editPostalContact(postalContactDetails);
        } catch (InvalidStateException ex) {
            logger.log(Level.SEVERE, "An error occurred during postal contact record editing", ex);
        }

        //Return the contact record created
        logger.log(Level.INFO, "Returning the contact record updated");
        return contact;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a contact record from the database
        logger.log(Level.INFO, "Entered method for removing a contact record from the database");

        //Checking if id is null
        logger.log(Level.INFO, "Checking if unique identifer is null");
        if (contactId == null) {
            logger.log(Level.WARNING, "The contact's unique identifier is null");
            throw new InvalidArgumentException("5-002");
        }

        //Get the contact record to be removed
        logger.log(Level.INFO, "Getting the contact record to be removed");
        contact = em.find(Contact.class, contactId);

        System.out.println("Contact id: " + contact.getId());

        //Remove a contact record from the database
        logger.log(Level.INFO, "Removing a contact record from the database");
        try {
            em.remove(contact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during removal", e);
            throw new InvalidStateException("0-004");
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private ContactDetails convertContactToContactDetails(Contact contact) {
        //Method for converting a contact record to its details

        //Converting contact to contact details
        contactDetails = new ContactDetails();
        contactDetails.setId(contact.getId());
        contactDetails.setActive(contact.getActive());
        contactDetails.setVersion(contact.getVersion());

        //Returning the details
        return contactDetails;
    }

    private List<ContactDetails> convertContactsToContactDetailsList(List<Contact> contacts) {
        //Method for converting contact records list to contact records details list

        //Converting education list to contact details list
        logger.log(Level.INFO, "Converting education list to contact details list");
        List<ContactDetails> details = new ArrayList<>();
        for (Contact c : contacts) {
            details.add(convertContactToContactDetails(c));
        }

        //Returning contact details list
        logger.log(Level.INFO, "Returning contact details list");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(ContactRequests.class.getSimpleName());

}
