/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.phone;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.PhoneContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.PhoneContactDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface PhoneContactRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public PhoneContact addPhoneContact(PhoneContactDetails details) throws InvalidArgumentException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<PhoneContactDetails> retrievePhoneContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editPhoneContact(PhoneContactDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removePhoneContact(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public PhoneContactDetails retrievePhoneContact(Integer contactId) throws InvalidArgumentException, InvalidStateException;

}
