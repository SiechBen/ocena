/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.access;

import java.security.MessageDigest;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.AlgorithmException;
import ke.co.miles.ocena.utilities.AccessCredentials;

/**
 *
 * @author Anthony Kwaje
 */
@Local
public interface AccessRequestsLocal {

    /**
     *
     * @param username
     * @param password
     * @return
     * @throws AlgorithmException
     */
    public AccessCredentials addCredentials(String username, String password) throws AlgorithmException;

    /**
     *
     * @param messageDigest
     * @param password
     * @return
     */
    public String generateSHAPassword(MessageDigest messageDigest, String password);

    /**
     *
     * @param username
     * @return
     * @throws AlgorithmException
     */
    public String generateAnonymousIdentity(String username) throws AlgorithmException;
}
