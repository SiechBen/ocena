/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.access.overalladmin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.OverallAdmin;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidLoginException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.OverallAdminDetails;

/**
 *
 * @author siech
 */
@Stateless
public class OverallAdminRequests extends EntityRequests implements OverallAdminRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addOverallAdmin(OverallAdminDetails details) throws InvalidArgumentException {

        if (details == null) {
            logger.log(Level.INFO, "Details are null");
            throw new InvalidArgumentException("error_006_01");
        } else if (details.getUsername() == null || details.getUsername().trim().length() == 0) {
            logger.log(Level.INFO, "The username is null");
            throw new InvalidArgumentException("error_006_02");
        } else if (details.getUsername().length() > 45) {
            logger.log(Level.INFO, "The username is longer than the permissible 45 characters");
            throw new InvalidArgumentException("error_006_03");
        } else if (details.getPassword() == null || details.getPassword().trim().length() == 0) {
            logger.log(Level.INFO, "The password is null");
            throw new InvalidArgumentException("error_006_04");
        } else if (details.getPassword().length() > 150) {
            logger.log(Level.INFO, "The password is longer than the permissible 150 characters");
            throw new InvalidArgumentException("error_006_05");
        }

        overallAdmin = new OverallAdmin();
        q = em.createNamedQuery("OverallAdmin.findByUsername");
        q.setParameter("username", details.getUsername());
        try {
            overallAdmin = (OverallAdmin) q.getSingleResult();
        } catch (NoResultException e) {
            overallAdmin = null;
        }

        if (overallAdmin != null) {
            logger.log(Level.INFO, "An admin with this username already exists");
            throw new InvalidArgumentException("error_006_06");
        }

        overallAdmin = new OverallAdmin();
        overallAdmin.setActive(Boolean.TRUE);
        overallAdmin.setPassword(details.getPassword());
        overallAdmin.setUsername(details.getUsername());

        logger.log(Level.INFO, "Add overall admin record to the database");
        try {
            em.persist(overallAdmin);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record creation");
            throw new EJBException("error_000_01");
        }

        logger.log(Level.INFO, "Returning a unique identifier of the new record created");
        return overallAdmin.getId();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public OverallAdminDetails userExists(String username, String password) throws InvalidArgumentException, InvalidStateException, InvalidLoginException {

        if (username == null || password == null || username.trim().length() == 0 || password.trim().length() == 0) {
            logger.log(Level.INFO, "Username and or password is not provided");
            throw new InvalidArgumentException("error_000_01");
        }

        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.INFO, "The hashing algorithm was not found");
            throw new InvalidArgumentException("error_007_01");
        }

        String encryptedPassword = accessService.generateSHAPassword(messageDigest, password);

        overallAdmin = new OverallAdmin();
        q = em.createNamedQuery("OverallAdmin.findByUsernameAndPassword");
        q.setParameter("username", username);
        q.setParameter("password", encryptedPassword);
        try {
            overallAdmin = (OverallAdmin) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Invalid user login attempt");
            throw new InvalidLoginException("error_006_08");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during login attempt", e);
            throw new InvalidLoginException("error_000_01");
        }

        return convertOverallAdminToOverallAdminDetails(overallAdmin);
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">

    @Override
    public void editOverallAdmin(OverallAdminDetails details) throws InvalidArgumentException, InvalidStateException {

        if (details == null) {
            logger.log(Level.INFO, "Details are null");
            throw new InvalidArgumentException("error_006_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The unique identifier of the overall admin is null");
            throw new InvalidArgumentException("error_006_07");
        } else if (details.getUsername() == null || details.getUsername().trim().length() == 0) {
            logger.log(Level.INFO, "The username is null");
            throw new InvalidArgumentException("error_006_02");
        } else if (details.getUsername().length() > 45) {
            logger.log(Level.INFO, "The username is null");
            throw new InvalidArgumentException("error_006_03");
        } else if (details.getPassword() == null || details.getPassword().trim().length() == 0) {
            logger.log(Level.INFO, "The password is null");
            throw new InvalidArgumentException("error_006_04");
        } else if (details.getPassword().length() > 150) {
            logger.log(Level.INFO, "The password is null");
            throw new InvalidArgumentException("error_006_05");
        }

        overallAdmin = new OverallAdmin();
        q = em.createNamedQuery("OverallAdmin.findByUsername");
        q.setParameter("username", details.getUsername());
        try {
            overallAdmin = (OverallAdmin) q.getSingleResult();
        } catch (NoResultException e) {
            overallAdmin = null;
        }

        if (overallAdmin != null) {
            if (!overallAdmin.getId().equals(details.getId())) {
                logger.log(Level.INFO, "An admin with this username already exists");
                throw new InvalidArgumentException("error_006_06");
            }
        }

        MessageDigest messageDigest;
        //Create a message digest algorithm for SHA-256 hashing algorithm
        logger.log(Level.INFO, "Creating a message digest hashing algorithm object");
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
            throw new InvalidArgumentException("error_007_01");
        }

        overallAdmin = em.find(OverallAdmin.class, details.getId());
        overallAdmin.setId(details.getId());
        overallAdmin.setActive(Boolean.TRUE);
        overallAdmin.setPassword(accessService.generateSHAPassword(messageDigest, details.getPassword()));
        overallAdmin.setUsername(details.getUsername());

        logger.log(Level.INFO, "Edit overall admin record in the database");
        try {
            em.merge(overallAdmin);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record editing");
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">

    @Override
    public void removeOverallAdmin(Integer id) throws InvalidArgumentException, InvalidStateException {

        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the overall admin is required");
            throw new InvalidArgumentException("error_006_07");
        }

        overallAdmin = em.find(OverallAdmin.class, id);
        try {
            em.remove(overallAdmin);
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record removal");
            throw new InvalidStateException("error_000_01");
        }
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private OverallAdminDetails convertOverallAdminToOverallAdminDetails(OverallAdmin overallAdmin) {

        overallAdminDetails = new OverallAdminDetails();

        overallAdminDetails.setId(overallAdmin.getId());
        overallAdminDetails.setActive(overallAdmin.getActive());
        overallAdminDetails.setVersion(overallAdmin.getVersion());
        overallAdminDetails.setUsername(overallAdmin.getUsername());
        overallAdminDetails.setPassword(overallAdmin.getPassword());

        return overallAdminDetails;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(OverallAdminRequests.class.getSimpleName());

}
