/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.access.overalladmin;

import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidLoginException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.OverallAdminDetails;

/**
 *
 * @author siech
 */
@Local
public interface OverallAdminRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addOverallAdmin(OverallAdminDetails details) throws InvalidArgumentException;

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     * @throws InvalidLoginException
     */
    public OverallAdminDetails userExists(String username, String password) throws InvalidArgumentException, InvalidStateException, InvalidLoginException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editOverallAdmin(OverallAdminDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeOverallAdmin(Integer id) throws InvalidArgumentException, InvalidStateException;

}
