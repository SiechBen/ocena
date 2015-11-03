/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.useraccount;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.UserAccount;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidLoginException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.UserAccountDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface UserAccountRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addUserAccount(UserAccountDetails details) throws InvalidArgumentException;

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<UserAccountDetails> retrieveUserAccounts() throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editUserAccount(UserAccountDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeUserAccount(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidLoginException
     */
    public UserAccount retrieveUserAccount(String username, String password) throws InvalidArgumentException, InvalidLoginException;

    /**
     *
     * @param referenceNumber
     * @return
     * @throws InvalidArgumentException
     * @throws ke.co.miles.ocena.exceptions.InvalidStateException
     */
    public UserAccountDetails retrieveUserAccount(String referenceNumber) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param personId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public UserAccountDetails retrieveUserAccountByPersonId(Integer personId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param userAccount
     * @return
     */
    public UserAccountDetails convertUserAccountToUserAccountDetails(UserAccount userAccount);

}
