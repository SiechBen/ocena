/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.country;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CountryDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface CountryRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addCountry(CountryDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editCountry(CountryDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<CountryDetails> retrieveCountries() throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeCountry(Integer id) throws InvalidArgumentException, InvalidStateException;

}
