/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact;

import java.util.List;
import javax.ejb.Local;
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
@Local
public interface ContactRequestsLocal {

    /**
     *
     * @param emailContactDetails
     * @param phoneContactDetails
     * @param postalContactDetails
     * @return
     * @throws InvalidArgumentException
     */
    public Contact addContact(EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException;

    /**
     *
     * @return @throws InvalidStateException
     */
    public List<ContactDetails> retrieveContacts() throws InvalidStateException;

    /**
     *
     * @param contactDetails
     * @param emailContactDetails
     * @param phoneContactDetails
     * @param postalContactDetails
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Contact editContact(ContactDetails contactDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public ContactDetails retrieveContact(Integer contactId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param contactId
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeContact(Integer contactId) throws InvalidArgumentException, InvalidStateException;

}
