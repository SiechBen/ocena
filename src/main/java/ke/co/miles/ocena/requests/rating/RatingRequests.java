/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.rating;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.RatingType;
import ke.co.miles.ocena.entities.Rating;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class RatingRequests extends EntityRequests implements RatingRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Short addRating(RatingDetails details) throws InvalidArgumentException {
        //Method for adding a rating record to the database
        LOGGER.log(Level.INFO, "Entered the method for adding a rating record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The rating details are null");
            throw new InvalidArgumentException("error_003_01");
        } else if (details.getRating() == null || details.getRating().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("error_003_02");
        } else if (details.getRating().trim().length() > 60) {
            LOGGER.log(Level.INFO, "The rating is longer than 60 characters");
            throw new InvalidArgumentException("error_003_03");
        } else if (details.getRatingType() == null) {
            LOGGER.log(Level.INFO, "The rating type is null");
            throw new InvalidArgumentException("error_003_04");
        }

        //Checking if the rating is a duplicate
        LOGGER.log(Level.INFO, "Checking if the rating is a duplicate");
        rating = new Rating();
        q = em.createNamedQuery("Rating.findByRating");
        q.setParameter("rating", details.getRating());
        try {
            rating = (Rating) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Rating is available for use");
            rating = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_003_02");
        }
        if (rating != null) {
            LOGGER.log(Level.SEVERE, "Rating is already in use");
            throw new InvalidArgumentException("error_003_05");
        }

        //Creating a container to hold rating record
        LOGGER.log(Level.INFO, "Creating a container to hold rating record");
        rating = new Rating();
        rating.setRating(details.getRating());
        rating.setActive(details.getActive());
        rating.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));

        //Adding a rating record to the database
        LOGGER.log(Level.INFO, "Adding a rating record to the database");
        try {
            em.persist(rating);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Return the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");

        return rating.getId();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<RatingDetails> retrieveRatingsByRatingType(RatingTypeDetail ratingTypeDetail) throws InvalidArgumentException {
        //Method for retrieving rating records by rating type from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving rating records by rating type from the database");

        //Check that rating type is not null
        LOGGER.log(Level.INFO, "Checking that rating type is not null");
        if (ratingTypeDetail == null) {
            LOGGER.log(Level.INFO, "The rating type is null");
            throw new InvalidArgumentException("error_003_04");
        } else if (ratingTypeDetail.getId() == null) {
            LOGGER.log(Level.INFO, "The rating type unique identifier is null");
            throw new InvalidArgumentException("error_003_06");
        }

        //Create a container to hold the rating values
        LOGGER.log(Level.INFO, "Creating a container to hold the rating values");
        List<Rating> ratingValues = new ArrayList<>();
        q = em.createNamedQuery("Rating.findByRatingTypeId");
        q.setParameter("ratingTypeId", ratingTypeDetail.getId());
        try {
            ratingValues = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred during rating records retrieval");
            throw new EJBException("error_000_01");
        }

        //Return the list of rating values
        LOGGER.log(Level.INFO, "Returning the list of rating values");
        return convertRatingsToRatingDetailsList(ratingValues);

    }

    @Override
    public Map<RatingTypeDetail, List<RatingDetails>> retrieveRatings() throws InvalidStateException {
        //Method for retrieving rating records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving rating records from the database");

        //Create a container retrieve rating type records into it from the database
        LOGGER.log(Level.INFO, "Creating a container retrieving rating type records into it from the database");
        List<RatingType> ratingTypes = new ArrayList<>();
        q = em.createNamedQuery("RatingType.findAll");
        try {
            ratingTypes = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during rating records retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Create a map to hold rating type and value records from the database
        LOGGER.log(Level.INFO, "Creating a map to hold rating type and value records from the database");
        Map<RatingTypeDetail, List<RatingDetails>> ratingTypesAndValuesMap = new HashMap<>();
        q = em.createNamedQuery("Rating.findByRatingTypeId");
        try {

            for (RatingType type : ratingTypes) {
                q.setParameter("ratingTypeId", type.getId());
                ratingTypesAndValuesMap.put(RatingTypeDetail.getRatingType(type.getId()),
                        convertRatingsToRatingDetailsList(q.getResultList()));
            }
        } catch (Exception e) {
            throw new InvalidStateException("error_000_01");
        }

        //Return the map of rating types and rating values
        LOGGER.log(Level.INFO, "Returning the map of rating types and rating values");
        return ratingTypesAndValuesMap;
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">

    @Override
    public void editRating(RatingDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a rating record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a rating record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            LOGGER.log(Level.INFO, "The rating details are null");
            throw new InvalidArgumentException("error_003_01");
        } else if (details.getId() == null) {
            LOGGER.log(Level.INFO, "The rating's unique identifier is null");
            throw new InvalidArgumentException("error_003_07");
        } else if (details.getRating() == null || details.getRating().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The rating is null");
            throw new InvalidArgumentException("error_003_02");
        } else if (details.getRating().trim().length() > 60) {
            LOGGER.log(Level.INFO, "The rating is longer than 60 characters");
            throw new InvalidArgumentException("error_003_03");
        } else if (details.getRatingType() == null) {
            LOGGER.log(Level.INFO, "The rating type is null");
            throw new InvalidArgumentException("error_003_04");
        }

        //Checking if the rating is a duplicate
        LOGGER.log(Level.INFO, "Checking if the rating is a duplicate");
        rating = new Rating();
        q = em.createNamedQuery("Rating.findByRating");
        q.setParameter("rating", details.getRating());
        try {
            rating = (Rating) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "Rating is available for use");
            rating = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (rating != null) {
            if (!rating.getId().equals(details.getId())) {
                LOGGER.log(Level.SEVERE, "Rating is already in use");
                throw new InvalidArgumentException("error_003_05");
            }
        }

        //Creating a container to hold rating record
        LOGGER.log(Level.INFO, "Creating a container to hold rating record");
        rating = em.find(Rating.class, details.getId());
        rating.setId(details.getId());
        rating.setRating(details.getRating());
        rating.setActive(details.getActive());
        rating.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));

        //Adding a rating record to the database
        LOGGER.log(Level.INFO, "Adding a rating record to the database");
        try {
            em.persist(rating);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeRating(Short id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a rating record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a rating record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The rating's unique identifier is null");
            throw new InvalidArgumentException("error_003_07");
        }

        //Removing a rating record from the database
        LOGGER.log(Level.INFO, "Removing a rating record from the database");
        rating = em.find(Rating.class, id);
        try {
            em.remove(rating);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">
    private RatingDetails convertRatingToRatingDetails(Rating rating) {
        //Entered method for converting rating to rating details
        LOGGER.log(Level.FINE, "Entered method for converting ratings to rating details");

        //Convert list of rating to rating details
        LOGGER.log(Level.FINE, "Convert list of rating to rating details");
        RatingDetails details = new RatingDetails();
        details.setActive(rating.getActive());
        details.setRating(rating.getRating());
        details.setId(rating.getId());
        details.setRatingType(RatingTypeDetail.getRatingType(rating.getRatingType().getId()));
        details.setVersion(rating.getVersion());

        //Returning converted rating details
        LOGGER.log(Level.FINE, "Returning converted rating details");
        return details;
    }

    private List<RatingDetails> convertRatingsToRatingDetailsList(List<Rating> ratings) {
        //Entered method for converting ratings list to rating details list
        LOGGER.log(Level.FINE, "Entered method for converting ratings list to rating details list");

        //Convert list of ratings to rating details list
        LOGGER.log(Level.FINE, "Convert list of ratings to rating details list");
        List<RatingDetails> ratingDetailsList = new ArrayList<>();
        for (Rating r : ratings) {
            ratingDetailsList.add(convertRatingToRatingDetails(r));
        }

        //Returning converted rating details list
        LOGGER.log(Level.FINE, "Returning converted rating details list");
        return ratingDetailsList;
    }

//</editor-fold>
    private static final Logger LOGGER = Logger.getLogger(RatingRequests.class.getSimpleName());

}
