/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.questioncategory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class QuestionCategoryRequests extends EntityRequests implements QuestionCategoryRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Short addQuestionCategory(QuestionCategoryDetails details) throws InvalidArgumentException {
        //Method for adding a question category record to the database
        logger.log(Level.INFO, "Entered the method for adding a question category record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_004_01");
        } else if (details.getCategory() == null || details.getCategory().trim().length() == 0) {
            logger.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("error_004_02");
        } else if (details.getCategory().trim().length() > 120) {
            logger.log(Level.INFO, "The question category is longer than 120 characters");
            throw new InvalidArgumentException("error_004_03");
        }

        //Check against duplicate entry
        logger.log(Level.INFO, "Checking against duplicate entry");
        questionCategory = new QuestionCategory();
        q = em.createNamedQuery("QuestionCategory.findByCategory");
        q.setParameter("category", details.getCategory());
        try {
            questionCategory = (QuestionCategory) q.getSingleResult();
        } catch (NoResultException e) {
            questionCategory = null;
            logger.log(Level.INFO, "The category is available for use");
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while retrieving category");
            throw new EJBException("error_000_01");
        }
        if (questionCategory != null) {
            logger.log(Level.INFO, "The category is already in use");
            throw new InvalidArgumentException("error_004_04");
        }

        //Creating a container to hold question category record
        logger.log(Level.INFO, "Creating a container to hold question category record");
        questionCategory = new QuestionCategory();
        questionCategory.setActive(details.getActive());
        questionCategory.setCategory(details.getCategory());

        //Adding a question category record to the database
        logger.log(Level.INFO, "Adding a question category record to the database");
        try {
            em.persist(questionCategory);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return questionCategory.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    @Override
    public List<QuestionCategoryDetails> retrieveQuestionCategories() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving question category records from the database
        logger.log(Level.INFO, "Entered the method for retrieving question category records from the database");

        //Retrieving question category records from the database
        logger.log(Level.INFO, "Retrieving question category records from the database");
        q = em.createNamedQuery("QuestionCategory.findAll");
        List<QuestionCategory> questionCategorys = new ArrayList<>();
        try {
            questionCategorys = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of question category records
        logger.log(Level.INFO, "Returning the details list of question category records");
        return convertQuestionCategoriesToQuestionCategoryDetailsList(questionCategorys);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editQuestionCategory(QuestionCategoryDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a question category record in the database
        logger.log(Level.INFO, "Entered the method for editing a question category record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
      if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_004_01");
        } else if(details.getId() == null){
           logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_004_05");
        }else if (details.getCategory() == null || details.getCategory().trim().length() == 0) {
            logger.log(Level.INFO, "The question category is null");
            throw new InvalidArgumentException("error_004_02");
        } else if (details.getCategory().trim().length() > 120) {
            logger.log(Level.INFO, "The question category is longer than 120 characters");
            throw new InvalidArgumentException("error_004_03");
        }

        //Check against duplicate entry
        logger.log(Level.INFO, "Checking against duplicate entry");
        questionCategory = new QuestionCategory();
        q = em.createNamedQuery("QuestionCategory.findByCategory");
        q.setParameter("category", details.getCategory());
        try {
            questionCategory = (QuestionCategory) q.getSingleResult();
        } catch (NoResultException e) {
            questionCategory = null;
            logger.log(Level.INFO, "The category is available for use");
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while retrieving category");
            throw new EJBException("error_000_01");
        }
        if (questionCategory != null) {
            if (!(questionCategory.getId().equals(details.getId()))) {
                logger.log(Level.INFO, "The category is already in use");
                throw new InvalidArgumentException("error_004_04");
            }
        }

        //Creating a container to hold question category record
        logger.log(Level.INFO, "Creating a container to hold question category record");
        questionCategory = em.find(QuestionCategory.class, details.getId());
        questionCategory.setId(details.getId());
        questionCategory.setCategory(details.getCategory());
        questionCategory.setActive(details.getActive());

        //Editing a question category record in the database
        logger.log(Level.INFO, "Editing a question category record in the database");
        try {
            em.merge(questionCategory);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeQuestionCategory(Short id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a question category record from the database
        logger.log(Level.INFO, "Entered the method for removing a question category record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_004_05");
        }

        //Removing a question category record from the database
        logger.log(Level.INFO, "Removing a question category record from the database");
        questionCategory = em.find(QuestionCategory.class, id);
        try {
            em.remove(questionCategory);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<QuestionCategoryDetails> convertQuestionCategoriesToQuestionCategoryDetailsList(List<QuestionCategory> questionCategorys) {
        //Entered method for converting question categorys list to question category details list
        logger.log(Level.FINE, "Entered method for converting question categorys list to question category details list");

        //Convert list of question categorys to question category details list
        logger.log(Level.FINE, "Convert list of question categorys to question category details list");
        List<QuestionCategoryDetails> details = new ArrayList<>();
        for (QuestionCategory m : questionCategorys) {
            details.add(convertQuestionCategoryToQuestionCategoryDetails(m));
        }

        //Returning converted question category details list
        logger.log(Level.FINE, "Returning converted question category details list");
        return details;
    }

    @Override
    public QuestionCategoryDetails convertQuestionCategoryToQuestionCategoryDetails(QuestionCategory questionCategory) {
        //Entered method for converting question category to question category details
        logger.log(Level.FINE, "Entered method for converting question categorys to question category details");

        //Convert list of question category to question category details
        logger.log(Level.FINE, "Convert list of question category to question category details");
        QuestionCategoryDetails details = new QuestionCategoryDetails();
        details.setId(questionCategory.getId());
        details.setActive(questionCategory.getActive());
        details.setCategory(questionCategory.getCategory());
        details.setVersion(questionCategory.getVersion());

        //Returning converted question category details
        logger.log(Level.FINE, "Returning converted question category details");
        return details;
    }
//</editor-fold>
    
    private static final Logger logger = Logger.getLogger(QuestionCategoryRequests.class.getSimpleName());
}
