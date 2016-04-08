/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.person;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import ke.co.miles.ocena.entities.Person;
import ke.co.miles.ocena.entities.UserAccount;
import ke.co.miles.ocena.entities.UserGroup;
import ke.co.miles.ocena.exceptions.AlgorithmException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidLoginException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.exceptions.MilesException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;
import ke.co.miles.ocena.utilities.UserAccountDetails;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class PersonRequests extends EntityRequests implements PersonRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addPerson(PersonDetails personDetails, UserAccountDetails userAccountDetails, FacultyMemberDetails facultyMemberDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, AlgorithmException {
        //Method for adding a person record to the database
        logger.log(Level.INFO, "Entered the method for adding a person record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (personDetails == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_029_01");
        } else if (personDetails.getFirstName() == null || personDetails.getFirstName().trim().length() == 0) {
            logger.log(Level.INFO, "The first name is null");
            throw new InvalidArgumentException("error_029_02");
        } else if (personDetails.getFirstName().trim().length() > 20) {
            logger.log(Level.INFO, "The first name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_03");
        } else if (personDetails.getLastName() == null || personDetails.getLastName().trim().length() == 0) {
            logger.log(Level.INFO, "The last name is null");
            throw new InvalidArgumentException("error_029_04");
        } else if (personDetails.getLastName().trim().length() > 20) {
            logger.log(Level.INFO, "The last name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_05");
        } else if (personDetails.getReferenceNumber() == null || personDetails.getReferenceNumber().trim().length() == 0) {
            logger.log(Level.INFO, "The reference number is null");
            throw new InvalidArgumentException("error_029_06");
        } else if (personDetails.getReferenceNumber().trim().length() > 20) {
            logger.log(Level.INFO, "The reference number is longer than 20 characters");
            throw new InvalidArgumentException("error_029_07");
        } else if (personDetails.getNationalIdOrPassport() == null || personDetails.getNationalIdOrPassport().trim().length() == 0) {
            logger.log(Level.INFO, "The national id or passport is null");
            throw new InvalidArgumentException("error_029_08");
        } else if (personDetails.getNationalIdOrPassport().trim().length() > 20) {
            logger.log(Level.INFO, "The national id or passport is longer than 20 characters");
            throw new InvalidArgumentException("error_029_09");
        } else if (facultyMemberDetails == null) {
            logger.log(Level.INFO, "The faculty member details are null");
            throw new InvalidArgumentException("error_028_01");
        }

        //Checking if the reference number is a duplicate
        logger.log(Level.INFO, "Checking if the reference number is a duplicate");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", personDetails.getReferenceNumber());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The reference number is available for use");
            person = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            logger.log(Level.SEVERE, "The reference number is already in use");
            throw new InvalidArgumentException("error_029_10");
        }

        //Checking if the national id or passport number is a duplicate
        logger.log(Level.INFO, "Checking if the national id or passport number is a duplicate");
        q = em.createNamedQuery("Person.findByNationalIdOrPassport");
        q.setParameter("nationalIdOrPassport", personDetails.getNationalIdOrPassport());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The national id or passport number is available for use");
            person = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            logger.log(Level.SEVERE, "The national id or passport number is already in use");
            throw new InvalidArgumentException("error_029_11");
        }

        //Creating a container to hold person record
        logger.log(Level.INFO, "Creating a container to hold person record");
        person = new Person();
        person.setNationalIdOrPassport(personDetails.getNationalIdOrPassport());
        person.setReferenceNumber(personDetails.getReferenceNumber().toUpperCase());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setActive(personDetails.getActive());
        person.setContact(contactService.addContact(emailContactDetails, phoneContactDetails, postalContactDetails));

        //Adding a person record to the database
        logger.log(Level.INFO, "Adding a person record to the database");
        try {
            em.persist(person);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during person record creation", e);
            throw new EJBException("error_000_01");
        }

        //Create a message digest algorithm object for SHA-256 hashing algorithm
        logger.log(Level.INFO, "Creating a message digest algorithm object");
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }

        //Create the person's user account
        logger.log(Level.INFO, "Creating the person's user account");
        userAccount = new UserAccount();
        userAccount.setActive(false);
        userAccount.setActiveFrom(null);
        userAccount.setDeactivatedOn(null);
        userAccount.setUsername(personDetails.getReferenceNumber());
        userAccount.setPerson(em.find(Person.class, person.getId()));
        userAccount.setUserGroup(em.find(UserGroup.class, userAccountDetails.getUserGroup().getId()));
        userAccount.setPassword(accessService.generateSHAPassword(messageDigest, personDetails.getReferenceNumber()));

        //Create the user's account record
        logger.log(Level.INFO, "Creating the user's account record");
        try {
            em.persist(userAccount);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during user account record creation", e);
            throw new EJBException("error_000_01");
        }

        //Create a faculty member record for the person
        logger.log(Level.INFO, "Creating a faculty member record for the person");
        facultyMemberDetails.setPerson(convertPersonToPersonDetails(person));
        facultyMemberService.addFacultyMember(facultyMemberDetails);

        //Return the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return person.getId();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public Map<PersonDetails, UserGroupDetail> retrievePerson(String username, String password) throws MilesException, AlgorithmException {
        //Method for retrieving person record from the database
        logger.log(Level.INFO, "Entered the method for retrieving person records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution unique identifier passed in");
        if (username == null || username.trim().length() == 0) {
            logger.log(Level.INFO, "The username is null");
            throw new InvalidArgumentException("error_001_12");
        } else if (username.trim().length() > 20) {
            logger.log(Level.INFO, "The username is longer than 20 characters");
            throw new InvalidArgumentException("error_001_13");
        } else if (password == null || password.trim().length() == 0) {
            logger.log(Level.INFO, "The password is null");
            throw new InvalidArgumentException("error_029_14");
        } else if (password.trim().length() > 150) {
            logger.log(Level.INFO, "The password is longer than 150 characters");
            throw new InvalidArgumentException("error_029_15");
        }

        //Get the hashed password
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            logger.log(Level.SEVERE, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }
        
        String hashedPassword = accessService.generateSHAPassword(messageDigest, password);

        //Retrieving the user account record from the database
        logger.log(Level.INFO, "Retrieving the matching user account from the database");
        try {
            userAccount = userAccountService.retrieveUserAccount(username, hashedPassword);
        } catch (InvalidArgumentException | InvalidLoginException e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new InvalidLoginException("error_000_01");
        }

        //Creating and valuating a map of person details to their user group
        logger.log(Level.INFO, "Creating and valuating a map of person details to their user group");
        Map<PersonDetails, UserGroupDetail> personUserGroupMap = new HashMap<>();
        personUserGroupMap.put(convertPersonToPersonDetails(userAccount.getPerson()),
                UserGroupDetail.getUserGroupDetail(userAccount.getUserGroup().getId()));

        //Returning the details list of person record
        logger.log(Level.INFO, "Returning the details list of person records");
        return personUserGroupMap;
    }

    @Override
    public List<PersonDetails> retrievePersons(Integer institutionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving person records from the database
        logger.log(Level.INFO, "Entered the method for retrieving person records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the institution unique identifier passed in");
        if (institutionId == null) {
            logger.log(Level.INFO, "The institution to which the person is associated is null");
            throw new InvalidArgumentException("error_029_16");
        }

        //Retrieving person records from the database
        logger.log(Level.INFO, "Retrieving person records from the database");
        q = em.createNamedQuery("Person.findByInstitutionId");
        q.setParameter("institutionId", institutionId);
        List<Person> people = new ArrayList<>();
        try {
            people = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of person records
        logger.log(Level.INFO, "Returning the details list of person records");
        return convertPersonsToPersonDetailsList(people);
    }

    @Override
    public PersonDetails retrievePerson(String referenceNumber) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a person record from the database
        logger.log(Level.INFO, "Entered the method for retrieving a person record from the database");

        //Checking validity of the reference number passed in
        logger.log(Level.INFO, "Checking validity of the reference number passed in");
        if (referenceNumber == null) {
            logger.log(Level.INFO, "The reference number of the person is null");
            throw new InvalidArgumentException("error_029_06");
        }

        //Retrieving person record from the database
        logger.log(Level.INFO, "Retrieving person record from the database");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", referenceNumber);
        person = new Person();
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.SEVERE, "No such record exists", e);
            throw new InvalidStateException("error_029_18");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the person record
        logger.log(Level.INFO, "Returning the details of the person");
        return convertPersonToPersonDetails(person);
    }

    @Override
    public PersonDetails retrievePerson(Integer personId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a person record from the database
        logger.log(Level.INFO, "Entered the method for retrieving a person record from the database");

        //Checking validity of the reference number passed in
        logger.log(Level.INFO, "Checking validity of the reference number passed in");
        if (personId == null) {
            logger.log(Level.INFO, "The institution to which the person is associated is null");
            throw new InvalidArgumentException("error_029_16");
        }

        //Retrieving person record from the database
        logger.log(Level.INFO, "Retrieving person record from the database");
        q = em.createNamedQuery("Person.findById");
        q.setParameter("id", personId);
        person = new Person();
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.SEVERE, "No such record exists", e);
            throw new InvalidStateException("error_029_18");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the person record
        logger.log(Level.INFO, "Returning the details of the person");
        return convertPersonToPersonDetails(person);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editPerson(PersonDetails personDetails, UserAccountDetails userAccountDetails, FacultyMemberDetails facultyMemberDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, InvalidStateException, AlgorithmException {
        //Method for editing a person record in the database
        logger.log(Level.INFO, "Entered the method for editing a person record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (personDetails == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_029_01");
        } else if (personDetails.getId() == null) {
            logger.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_029_17");
        } else if (personDetails.getFirstName() == null || personDetails.getFirstName().trim().length() == 0) {
            logger.log(Level.INFO, "The first name is null");
            throw new InvalidArgumentException("error_029_02");
        } else if (personDetails.getFirstName().trim().length() > 20) {
            logger.log(Level.INFO, "The first name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_03");
        } else if (personDetails.getLastName() == null || personDetails.getLastName().trim().length() == 0) {
            logger.log(Level.INFO, "The last name is null");
            throw new InvalidArgumentException("error_029_04");
        } else if (personDetails.getLastName().trim().length() > 20) {
            logger.log(Level.INFO, "The last name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_05");
        } else if (personDetails.getReferenceNumber() == null || personDetails.getReferenceNumber().trim().length() == 0) {
            logger.log(Level.INFO, "The reference number is null");
            throw new InvalidArgumentException("error_029_06");
        } else if (personDetails.getReferenceNumber().trim().length() > 20) {
            logger.log(Level.INFO, "The reference number is longer than 20 characters");
            throw new InvalidArgumentException("error_029_07");
        } else if (personDetails.getNationalIdOrPassport() == null || personDetails.getNationalIdOrPassport().trim().length() == 0) {
            logger.log(Level.INFO, "The national id or passport is null");
            throw new InvalidArgumentException("error_029_08");
        } else if (personDetails.getNationalIdOrPassport().trim().length() > 20) {
            logger.log(Level.INFO, "The national id or passport is longer than 20 characters");
            throw new InvalidArgumentException("error_029_09");
        } else if (facultyMemberDetails == null) {
            logger.log(Level.INFO, "The faculty member details are null");
            throw new InvalidArgumentException("error_028_01");
        }

        //Checking if the reference number is a duplicate
        logger.log(Level.INFO, "Checking if the reference number is a duplicate");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", personDetails.getReferenceNumber());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The reference number is available for use");
            person = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            if (!person.getId().equals(personDetails.getId())) {
                logger.log(Level.SEVERE, "The reference number is already in use");
                throw new InvalidArgumentException("error_029_10");
            }
        }

        //Checking if the national id or passport number is a duplicate
        logger.log(Level.INFO, "Checking if the national id or passport number is a duplicate");
        q = em.createNamedQuery("Person.findByNationalIdOrPassport");
        q.setParameter("nationalIdOrPassport", personDetails.getNationalIdOrPassport());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "The national id or passport number is available for use");
            person = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_029_02");
        }
        if (person != null) {
            if (!person.getId().equals(personDetails.getId())) {
                logger.log(Level.SEVERE, "The national id or passport number is already in use");
                throw new InvalidArgumentException("error_029_11");
            }
        }

        //Creating a container to hold person record
        logger.log(Level.INFO, "Creating a container to hold person record");
        person = em.find(Person.class, personDetails.getId());
        if (personDetails.getContact() != null) {
            contactDetails = new ContactDetails();
            contactDetails.setId(personDetails.getContact().getId());
            contactDetails.setActive(personDetails.getContact().getActive());
            person.setContact(contactService.editContact(contactDetails, emailContactDetails, phoneContactDetails, postalContactDetails));
        }
        person.setNationalIdOrPassport(personDetails.getNationalIdOrPassport());
        person.setReferenceNumber(personDetails.getReferenceNumber().toUpperCase());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setActive(personDetails.getActive());
        person.setId(personDetails.getId());

        //Editing a person record in the database
        logger.log(Level.INFO, "Editing a person record in the database");
        try {
            em.merge(person);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during person record update", e);
            throw new InvalidStateException("error_000_01");
        }

        //Create a message digest algorithm object for SHA-256 hashing algorithm
        logger.log(Level.INFO, "Creating a message digest algorithm object");
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }

        //Create the person's user account
        logger.log(Level.INFO, "Creating the person's user account");
        q = em.createNamedQuery("UserAccount.findByPersonId");
        q.setParameter("personId", person.getId());
        try {
            userAccount = (UserAccount) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during user account record retrieval");
            throw new EJBException("error_000_01");
        }
        userAccount.setActive(true);
        userAccount.setActiveFrom(null);
        userAccount.setDeactivatedOn(null);
        userAccount.setUsername(personDetails.getReferenceNumber());
        userAccount.setPerson(em.find(Person.class, person.getId()));
        userAccount.setUserGroup(em.find(UserGroup.class, userAccountDetails.getUserGroup().getId()));
        if (userAccount.getPassword().equals(userAccountDetails.getPassword())) {
            userAccount.setPassword(userAccountDetails.getPassword());
        } else {
            userAccount.setPassword(accessService.generateSHAPassword(messageDigest, userAccountDetails.getPassword()));
        }

        //Edit the user's account record
        logger.log(Level.INFO, "Editing the user's account record");
        try {
            userAccountService.editUserAccount(userAccountService.convertUserAccountToUserAccountDetails(userAccount));
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.SEVERE, "An error occurred during user account record update", e);
            throw new EJBException("error_000_01");
        }

        //Edit the faculty member record for the person
        logger.log(Level.INFO, "Editing the faculty member record for the person");
        facultyMemberDetails.setPerson(convertPersonToPersonDetails(person));
        try {
            facultyMemberService.editFacultyMember(facultyMemberDetails);
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.SEVERE, "An error occurred during faculty member record update", e);
            throw new EJBException("error_000_01");
        }

        //Commit the changes
        logger.log(Level.INFO, "Committing the changes");
        try {
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "And error occurred while committing the changes", e);
            throw new EJBException("error_000_01");
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removePerson(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a person record from the database
        logger.log(Level.INFO, "Entered the method for removing a person record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_029_17");
        }

        //Get the person record to be removed
        logger.log(Level.INFO, "Getting the person record to be removed");
        person = em.find(Person.class, id);
        int contactId = person.getContact().getId();

        //Removing a person record from the database
        logger.log(Level.INFO, "Removing a person record from the database");
        try {
            em.remove(person);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

        //Remove the person's contact record
        logger.log(Level.INFO, "Removing the person's contact record");
        contactService.removeContact(contactId);

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<PersonDetails> convertPersonsToPersonDetailsList(List<Person> people) {
        //Entered method for converting people list to person details list
        logger.log(Level.FINE, "Entered method for converting people list to person details list");

        //Convert list of people to person details list
        logger.log(Level.FINE, "Convert list of people to person details list");
        List<PersonDetails> details = new ArrayList<>();
        for (Person a : people) {
            details.add(convertPersonToPersonDetails(a));
        }

        //Returning converted person details list
        logger.log(Level.FINE, "Returning converted person details list");
        return details;
    }

    @Override
    public PersonDetails convertPersonToPersonDetails(Person person) {
        //Entered method for converting person to person details
        logger.log(Level.FINE, "Entered method for converting people to person details");

        //Convert list of person to person details
        logger.log(Level.FINE, "Convert list of person to person details");
        contactDetails = new ContactDetails();
        contactDetails.setId(person.getContact().getId());

        PersonDetails details = new PersonDetails();
        details.setNationalIdOrPassport(person.getNationalIdOrPassport());
        details.setReferenceNumber(person.getReferenceNumber());
        details.setFirstName(person.getFirstName());
        details.setLastName(person.getLastName());
        details.setVersion(person.getVersion());
        details.setActive(person.getActive());
        details.setContact(contactDetails);
        details.setId(person.getId());

        //Returning converted person details
        logger.log(Level.FINE, "Returning converted person details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(PersonRequests.class.getSimpleName());

}
