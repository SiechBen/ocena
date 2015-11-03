/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Contact;
import ke.co.miles.ocena.entities.PhoneContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class PhoneContactRequests extends EntityRequests implements PhoneContactRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public PhoneContact addPhoneContact(PhoneContactDetails details) throws InvalidArgumentException {
        //Method for adding an phone contact record to the database
        logger.log(Level.INFO, "Entered the method for adding an phone contact record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("11-001");
        } else if (details.getMobileNumber() != null) {
            if (details.getMobileNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The mobile number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getFixedNumber() != null) {
            if (details.getFixedNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The fixed number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact to which the phone contact belongs is null");
            throw new InvalidArgumentException("11-004");
        }

        //Checking if the mobile number is a duplicate
        logger.log(Level.INFO, "Checking if the mobile number is a duplicate");
        q = em.createNamedQuery("PhoneContact.findByMobileNumber");
        q.setParameter("mobileNumber", details.getMobileNumber());
        try {
            phoneContact = (PhoneContact) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Mobile number is available for use");
            phoneContact = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }
        if (phoneContact != null) {
            logger.log(Level.SEVERE, "Mobile number is already in use");
            throw new InvalidArgumentException("11-005");
        }

        //Creating a container to hold phone contact record
        logger.log(Level.INFO, "Creating a container to hold phone contact record");
        phoneContact = new PhoneContact();
        phoneContact.setActive(details.getActive());
        phoneContact.setMobileNumber(details.getMobileNumber());
        phoneContact.setFixedNumber(details.getFixedNumber());
        phoneContact.setContact(em.find(Contact.class, details.getContact().getId()));

        //Adding an phone contact record to the database
        logger.log(Level.INFO, "Adding an phone contact record to the database");
        try {
            em.persist(phoneContact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("11-001");
        }

        //Returning new record added
        logger.log(Level.INFO, "Returning the new record added");
        return phoneContact;

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<PhoneContactDetails> retrievePhoneContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving phone contact records from the database
        logger.log(Level.INFO, "Entered the method for retrieving phone contact records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact which offers the phone contact is null");
            throw new InvalidArgumentException("11-004");
        }

        //Retrieving phone contact records from the database
        logger.log(Level.INFO, "Retrieving phone contact records from the database");
        q = em.createNamedQuery("PhoneContact.findByContactId");
        q.setParameter("contactId", contactId);
        List<PhoneContact> phoneContacts = new ArrayList<>();
        try {
            phoneContacts = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }

        //Returning the details list of phone contact records
        logger.log(Level.INFO, "Returning the details list of phone contact records");
        return convertPhoneContactsToPhoneContactDetailsList(phoneContacts);
    }

    @Override
    public PhoneContactDetails retrievePhoneContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving phone contact records from the database
        logger.log(Level.INFO, "Entered the method for retrieving phone contact records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the contact unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The contact which offers the phone contact is null");
            throw new InvalidArgumentException("11-004");
        }

        //Retrieving phone contact records from the database
        logger.log(Level.INFO, "Retrieving phone contact records from the database");
        q = em.createNamedQuery("PhoneContact.findByContactId");
        q.setParameter("contactId", contactId);
        phoneContact = new PhoneContact();
        try {
            phoneContact = (PhoneContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }

        //Returning the details list of phone contact records
        logger.log(Level.INFO, "Returning the details list of phone contact records");
        return convertPhoneContactToPhoneContactDetails(phoneContact);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editPhoneContact(PhoneContactDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing an phone contact record in the database
        logger.log(Level.INFO, "Entered the method for editing an phone contact record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("11-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The phone contact's unique identifier is null");
            throw new InvalidArgumentException("11-006");
        } else if (details.getMobileNumber() != null) {
            if (details.getMobileNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The mobile number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getFixedNumber() != null) {
            if (details.getFixedNumber().trim().length() > 20) {
                logger.log(Level.INFO, "The fixed number is longer than 20 characters");
                throw new InvalidArgumentException("11-003");
            }
        } else if (details.getContact() == null) {
            logger.log(Level.INFO, "The contact to which the phone contact belongs is null");
            throw new InvalidArgumentException("11-004");
        }

        //Checking if the phone contact is a duplicate
        logger.log(Level.INFO, "Checking if the mobile number is a duplicate");
        q = em.createNamedQuery("PhoneContact.findByMobileNumber");
        q.setParameter("mobileNumber", details.getMobileNumber());
        try {
            phoneContact = (PhoneContact) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Mobile number is available for use");
            phoneContact = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("11-002");
        }
        if (phoneContact != null) {
            if (!(phoneContact.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Mobile number is already in use");
                throw new InvalidArgumentException("11-005");
            }
        }

        //Creating a container to hold phone contact record
        logger.log(Level.INFO, "Creating a container to hold phone contact record");
        phoneContact = em.find(PhoneContact.class, details.getId());
        phoneContact.setMobileNumber(details.getMobileNumber());
        phoneContact.setFixedNumber(details.getFixedNumber());
        phoneContact.setActive(details.getActive());
        phoneContact.setId(details.getId());
        phoneContact.setContact(em.find(Contact.class, details.getContact().getId()));

        //Editing an phone contact record in the database
        logger.log(Level.INFO, "Editing an phone contact record in the database");
        try {
            em.merge(phoneContact);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("11-003");

        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">

    @Override
    public void removePhoneContact(Integer contactId) throws InvalidArgumentException, InvalidStateException {
        //Method for removing an phone contact record from the database
        logger.log(Level.INFO, "Entered the method for removing an phone contact record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (contactId == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("11-006");
        }

        //Get the phone contact record to be removed
        logger.log(Level.INFO, "Getting the phone contact record to be removed");
        q = em.createNamedQuery("PhoneContact.findByContactId");
        q.setParameter("contactId", contactId);
        try {
            phoneContact = (PhoneContact) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during phone record retrieval", e);
        }

        //Removing the phone contact record from the database
        logger.log(Level.INFO, "Removing the phone contact record from the database");
        try {
            em.remove(phoneContact);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("11-004");

        }
    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<PhoneContactDetails> convertPhoneContactsToPhoneContactDetailsList(List<PhoneContact> phoneContacts) {
        //Entered method for converting phone contacts list to phone contact details list
        logger.log(Level.FINE, "Entered method for converting phone contacts list to phone contact details list");

        //Convert list of phone contacts to phone contact details list
        logger.log(Level.FINE, "Convert list of phone contacts to phone contact details list");
        List<PhoneContactDetails> details = new ArrayList<>();
        for (PhoneContact a : phoneContacts) {
            details.add(convertPhoneContactToPhoneContactDetails(a));
        }

        //Returning converted phone contact details list
        logger.log(Level.FINE, "Returning converted phone contact details list");
        return details;
    }

    private PhoneContactDetails convertPhoneContactToPhoneContactDetails(PhoneContact phoneContact) {
        //Entered method for converting phoneContact to phoneContact details
        logger.log(Level.FINE, "Entered method for converting phone contacts to phone contact details");

        //Convert list of phoneContact to phoneContact details
        logger.log(Level.FINE, "Convert list of phone contact to phone contact details");
        contactDetails = new ContactDetails();
        contactDetails.setId(phoneContact.getContact().getId());

        PhoneContactDetails details = new PhoneContactDetails();
        details.setMobileNumber(phoneContact.getMobileNumber());
        details.setFixedNumber(phoneContact.getFixedNumber());
        details.setVersion(phoneContact.getVersion());
        details.setActive(phoneContact.getActive());
        details.setId(phoneContact.getId());
        details.setContact(contactDetails);

        //Returning converted phoneContact details
        logger.log(Level.FINE, "Returning converted phone contact details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(PhoneContactRequests.class.getSimpleName());

}
