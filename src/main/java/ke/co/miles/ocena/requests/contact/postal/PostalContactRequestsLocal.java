/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.contact.postal;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.PostalContact;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface PostalContactRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public PostalContact addPostalContact(PostalContactDetails details) throws InvalidArgumentException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<PostalContactDetails> retrievePostalContacts(Integer contactId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removePostalContact(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editPostalContact(PostalContactDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param contactId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public PostalContactDetails retrievePostalContact(Integer contactId) throws InvalidArgumentException, InvalidStateException;
    
}
