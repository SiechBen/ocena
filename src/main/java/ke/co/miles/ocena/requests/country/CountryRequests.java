/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.country;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Country;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CountryDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class CountryRequests extends EntityRequests implements CountryRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addCountry(CountryDetails details) throws InvalidArgumentException {
        //Method for adding a country record to the database
        logger.log(Level.INFO, "Entered the method for adding a country record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("14-001");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The country name is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getName().trim().length() > 80) {
            logger.log(Level.INFO, "The country name is longer than 80 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getNiceName() == null || details.getNiceName().trim().length() == 0) {
            logger.log(Level.INFO, "The nice name is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getNiceName().trim().length() > 80) {
            logger.log(Level.INFO, "The nice name is longer than 80 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getIso() == null || details.getIso().trim().length() == 0) {
            logger.log(Level.INFO, "The iso is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getIso().trim().length() > 2) {
            logger.log(Level.INFO, "The iso is longer than 2 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getPhoneCode() == null) {
            logger.log(Level.INFO, "The phone code is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getIso3() != null) {
            if (details.getIso3().trim().length() > 3) {
                logger.log(Level.INFO, "The iso3 is longer than 3 characters");
                throw new InvalidArgumentException("14-003");
            }
        }

        //Checking if the country name is a duplicate
        logger.log(Level.INFO, "Checking if the country name is a duplicate");
        q = em.createNamedQuery("Country.findByName");
        q.setParameter("name", details.getName());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Country name is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Country name is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the nice name is a duplicate
        logger.log(Level.INFO, "Checking if the nice name is a duplicate");
        q = em.createNamedQuery("Country.findByNiceName");
        q.setParameter("niceName", details.getNiceName());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Nice name is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Nice name is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the iso is a duplicate
        logger.log(Level.INFO, "Checking if the iso is a duplicate");
        q = em.createNamedQuery("Country.findByIso");
        q.setParameter("iso", details.getIso());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The iso is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "The iso is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the phone code is a duplicate
        logger.log(Level.INFO, "Checking if the phone code is a duplicate");
        q = em.createNamedQuery("Country.findByPhonecode");
        q.setParameter("phoneCode", details.getPhoneCode());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Phone code is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Phone code is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Creating a container to hold country record
        logger.log(Level.INFO, "Creating a container to hold country record");
        country = new Country();
        country.setName(details.getName());
        country.setIso(details.getIso());
        country.setIso3(details.getIso3());
        country.setActive(details.getActive());
        country.setNumCode(details.getNumCode());
        country.setNiceName(details.getNiceName());
        country.setPhoneCode(details.getPhoneCode());

        //Adding a country record to the database
        logger.log(Level.INFO, "Adding a country record to the database");
        try {
            em.persist(country);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("14-001");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return country.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<CountryDetails> retrieveCountries() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving country records from the database
        logger.log(Level.INFO, "Entered the method for retrieving country records from the database");

        //Retrieving country records from the database
        logger.log(Level.INFO, "Retrieving country records from the database");
        q = em.createNamedQuery("Country.findAll");
         List<Country> countries = new ArrayList<>();
        try {
            countries = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("14-002");
        }

        //Returning the details list of country records
        logger.log(Level.INFO, "Returning the details list of country records");
        return convertCountriesToCountryDetailsList(countries);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editCountry(CountryDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a country record in the database
        logger.log(Level.INFO, "Entered the method for editing a country record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("14-001");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The country's unique identifier is null");
            throw new InvalidArgumentException("14-006");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The country name is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getName().trim().length() > 80) {
            logger.log(Level.INFO, "The country name is longer than 80 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getNiceName() == null || details.getNiceName().trim().length() == 0) {
            logger.log(Level.INFO, "The nice name is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getNiceName().trim().length() > 80) {
            logger.log(Level.INFO, "The nice name is longer than 80 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getIso() == null || details.getIso().trim().length() == 0) {
            logger.log(Level.INFO, "The iso is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getIso().trim().length() > 2) {
            logger.log(Level.INFO, "The iso is longer than 2 characters");
            throw new InvalidArgumentException("14-003");
        } else if (details.getPhoneCode() == null) {
            logger.log(Level.INFO, "The phone code is null");
            throw new InvalidArgumentException("14-002");
        } else if (details.getIso3() != null) {
            if (details.getIso3().trim().length() > 3) {
                logger.log(Level.INFO, "The iso3 is longer than 3 characters");
                throw new InvalidArgumentException("14-003");
            }
        }

        //Checking if the country name is a duplicate
        logger.log(Level.INFO, "Checking if the country name is a duplicate");
        q = em.createNamedQuery("Country.findByName");
        q.setParameter("name", details.getName());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Country name is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Country name is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the nice name is a duplicate
        logger.log(Level.INFO, "Checking if the nice name is a duplicate");
        q = em.createNamedQuery("Country.findByNiceName");
        q.setParameter("niceName", details.getNiceName());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Nice name is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Nice name is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the iso is a duplicate
        logger.log(Level.INFO, "Checking if the iso is a duplicate");
        q = em.createNamedQuery("Country.findByIso");
        q.setParameter("iso", details.getIso());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The iso is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "The iso is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Checking if the phone code is a duplicate
        logger.log(Level.INFO, "Checking if the phone code is a duplicate");
        q = em.createNamedQuery("Country.findByPhonecode");
        q.setParameter("phoneCode", details.getPhoneCode());
        try {
            country = (Country) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Phone code is available for use");
            country = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("14-002");
        }
        if (country != null) {
            logger.log(Level.SEVERE, "Phone code is already in use");
            throw new InvalidArgumentException("14-005");
        }

        //Creating a container to hold country record
        logger.log(Level.INFO, "Creating a container to hold country record");
        country = em.find(Country.class, details.getId());
        country.setId(details.getId());
        country.setIso(details.getIso());
        country.setName(details.getName());
        country.setIso3(details.getIso3());
        country.setActive(details.getActive());
        country.setNumCode(details.getNumCode());
        country.setNiceName(details.getNiceName());
        country.setPhoneCode(details.getPhoneCode());

        //Editing a country record in the database
        logger.log(Level.INFO, "Editing a country record in the database");
        try {
            em.merge(country);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("14-003");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeCountry(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a country record from the database
        logger.log(Level.INFO, "Entered the method for removing a country record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("14-006");
        }

        //Removing a country record from the database
        logger.log(Level.INFO, "Removing a country record from the database");
        country = em.find(Country.class, id);
        try {
            em.remove(country);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("14-004");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<CountryDetails> convertCountriesToCountryDetailsList(List<Country> countrys) {
        //Entered method for converting countrys list to country details list
        logger.log(Level.FINE, "Entered method for converting countrys list to country details list");

        //Convert list of countrys to country details list
        logger.log(Level.FINE, "Convert list of countrys to country details list");
        List<CountryDetails> details = new ArrayList<>();
        for (Country a : countrys) {
            details.add(convertCountryToCountryDetails(a));
        }

        //Returning converted country details list
        logger.log(Level.FINE, "Returning converted country details list");
        return details;
    }

    private CountryDetails convertCountryToCountryDetails(Country country) {
        //Entered method for converting country to country details
        logger.log(Level.FINE, "Entered method for converting countrys to country details");

        //Convert list of country to country details
        logger.log(Level.FINE, "Convert list of country to country details");
        CountryDetails details = new CountryDetails();
        details.setId(country.getId());
        details.setIso(country.getIso());
        details.setName(country.getName());
        details.setIso3(country.getIso3());
        details.setActive(country.getActive());
        details.setVersion(country.getVersion());
        details.setNumCode(country.getNumCode());
        details.setNiceName(country.getNiceName());
        details.setPhoneCode(country.getPhoneCode());

        //Returning converted country details
        logger.log(Level.FINE, "Returning converted country details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(CountryRequests.class.getSimpleName());
}
