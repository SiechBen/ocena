/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.email;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.EmailContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.EmailContactDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface EmailContactRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public EmailContact addEmailContact(EmailContactDetails details) throws InvalidArgumentException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<EmailContactDetails> retrieveEmailContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editEmailContact(EmailContactDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeEmailContact(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public EmailContactDetails retrieveEmailContact(Integer contactId) throws InvalidArgumentException, InvalidStateException;

}
