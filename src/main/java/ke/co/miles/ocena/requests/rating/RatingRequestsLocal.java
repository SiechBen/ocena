/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.rating;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Local
public interface RatingRequestsLocal {

    /**
     *
     * @param details details of the rating record to be added to the database
     * @return the unique identifier of the new record made
     * @throws InvalidArgumentException when the details are null or incorrectly specified
     */
    public Short addRating(RatingDetails details) throws InvalidArgumentException;

    /**
     *
     * @return @throws InvalidStateException when no record exists in the database
     */
    public Map<RatingTypeDetail, List<RatingDetails>> retrieveRatings() throws InvalidStateException;

    /**
     *
     * @param details details of the rating record to be edited in the database
     * @throws InvalidArgumentException when the details are null or incorrectly specified
     * @throws InvalidStateException when no such record exists in the database
     */
    public void editRating(RatingDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id the unique identifier of the record to be removed
     * @throws InvalidArgumentException when the unique identifier is null or incorrectly specified
     * @throws InvalidStateException when no such record exists in the database
     */
    public void removeRating(Short id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param ratingTypeDetail
     * @return
     * @throws InvalidArgumentException
     */
    public List<RatingDetails> retrieveRatingsByRatingType(RatingTypeDetail ratingTypeDetail) throws InvalidArgumentException;

}
