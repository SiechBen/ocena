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
import ke.co.miles.ocena.utilities.FacultyMemberRoleDetail;
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
        LOGGER.log(Level.INFO, "Entered the method for adding a person record to the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (personDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_029_01");
        } else if (personDetails.getFirstName() == null || personDetails.getFirstName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The first name is null");
            throw new InvalidArgumentException("error_029_02");
        } else if (personDetails.getFirstName().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The first name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_03");
        } else if (personDetails.getLastName() == null || personDetails.getLastName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The last name is null");
            throw new InvalidArgumentException("error_029_04");
        } else if (personDetails.getLastName().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The last name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_05");
        } else if (personDetails.getReferenceNumber() == null || personDetails.getReferenceNumber().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The reference number is null");
            throw new InvalidArgumentException("error_029_06");
        } else if (personDetails.getReferenceNumber().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The reference number is longer than 20 characters");
            throw new InvalidArgumentException("error_029_07");
        } else if (personDetails.getNationalIdOrPassport() == null || personDetails.getNationalIdOrPassport().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The national id or passport is null");
            throw new InvalidArgumentException("error_029_08");
        } else if (personDetails.getNationalIdOrPassport().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The national id or passport is longer than 20 characters");
            throw new InvalidArgumentException("error_029_09");
        } else if (facultyMemberDetails == null) {
            LOGGER.log(Level.INFO, "The faculty member details are null");
            throw new InvalidArgumentException("error_028_01");
        }

        //Checking if the reference number is a duplicate
        LOGGER.log(Level.INFO, "Checking if the reference number is a duplicate");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", personDetails.getReferenceNumber());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The reference number is available for use");
            person = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            LOGGER.log(Level.SEVERE, "The reference number is already in use");
            throw new InvalidArgumentException("error_029_10");
        }

        //Checking if the national id or passport number is a duplicate
        LOGGER.log(Level.INFO, "Checking if the national id or passport number is a duplicate");
        q = em.createNamedQuery("Person.findByNationalIdOrPassport");
        q.setParameter("nationalIdOrPassport", personDetails.getNationalIdOrPassport());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The national id or passport number is available for use");
            person = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            LOGGER.log(Level.SEVERE, "The national id or passport number is already in use");
            throw new InvalidArgumentException("error_029_11");
        }

        //Creating a container to hold person record
        LOGGER.log(Level.INFO, "Creating a container to hold person record");
        person = new Person();
        person.setNationalIdOrPassport(personDetails.getNationalIdOrPassport());
        person.setReferenceNumber(personDetails.getReferenceNumber().toUpperCase());
        person.setFirstName(personDetails.getFirstName());
        person.setLastName(personDetails.getLastName());
        person.setActive(personDetails.getActive());
        person.setContact(contactService.addContact(emailContactDetails, phoneContactDetails, postalContactDetails));

        //Adding a person record to the database
        LOGGER.log(Level.INFO, "Adding a person record to the database");
        try {
            em.persist(person);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during person record creation", e);
            throw new EJBException("error_000_01");
        }

        //Create a message digest algorithm object for SHA-256 hashing algorithm
        LOGGER.log(Level.INFO, "Creating a message digest algorithm object");
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.INFO, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }

        //Create the person's user account
        LOGGER.log(Level.INFO, "Creating the person's user account");
        userAccount = new UserAccount();
        userAccount.setActive(false);
        userAccount.setActiveFrom(null);
        userAccount.setDeactivatedOn(null);
        userAccount.setUsername(personDetails.getReferenceNumber());
        userAccount.setPerson(em.find(Person.class, person.getId()));
        userAccount.setUserGroup(em.find(UserGroup.class, userAccountDetails.getUserGroup().getId()));
        userAccount.setPassword(accessService.generateSHAPassword(messageDigest, personDetails.getReferenceNumber()));

        //Create the user's account record
        LOGGER.log(Level.INFO, "Creating the user's account record");
        try {
            em.persist(userAccount);
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during user account record creation", e);
            throw new EJBException("error_000_01");
        }

        //Create a faculty member record for the person
        LOGGER.log(Level.INFO, "Creating a faculty member record for the person");
        facultyMemberDetails.setPerson(convertPersonToPersonDetails(person));
        facultyMemberService.addFacultyMember(facultyMemberDetails);

        //Return the unique identifier of the new record added
        LOGGER.log(Level.INFO, "Returning the unique identifier of the new record added");
        return person.getId();
    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public Map<PersonDetails, UserGroupDetail> retrievePerson(String username, String password) throws MilesException, AlgorithmException {
        //Method for retrieving person record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving person records from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the institution unique identifier passed in");
        if (username == null || username.trim().length() == 0) {
            LOGGER.log(Level.INFO, "The username is null");
            throw new InvalidArgumentException("error_001_12");
        } else if (username.trim().length() > 20) {
            LOGGER.log(Level.INFO, "The username is longer than 20 characters");
            throw new InvalidArgumentException("error_001_13");
        } else if (password == null || password.trim().length() == 0) {
            LOGGER.log(Level.INFO, "The password is null");
            throw new InvalidArgumentException("error_029_14");
        } else if (password.trim().length() > 150) {
            LOGGER.log(Level.INFO, "The password is longer than 150 characters");
            throw new InvalidArgumentException("error_029_15");
        }

        //Get the hashed password
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            LOGGER.log(Level.SEVERE, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }

        String hashedPassword = accessService.generateSHAPassword(messageDigest, password);

        //Retrieving the user account record from the database
        LOGGER.log(Level.INFO, "Retrieving the matching user account from the database");
        try {
            userAccount = userAccountService.retrieveUserAccount(username, hashedPassword);
        } catch (InvalidArgumentException | InvalidLoginException e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new InvalidLoginException("error_000_01");
        }

        //Creating and valuating a map of person details to their user group
        LOGGER.log(Level.INFO, "Creating and valuating a map of person details to their user group");
        Map<PersonDetails, UserGroupDetail> personUserGroupMap = new HashMap<>();
        personUserGroupMap.put(convertPersonToPersonDetails(userAccount.getPerson()),
                UserGroupDetail.getUserGroupDetail(userAccount.getUserGroup().getId()));

        //Returning the details list of person record
        LOGGER.log(Level.INFO, "Returning the details list of person records");
        return personUserGroupMap;
    }

    @Override
    public HashMap<PersonDetails, HashMap<UserGroupDetail, FacultyMemberRoleDetail>> retrievePersons() throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving person records from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving person records from the database");

        //Retrieving person records from the database
        LOGGER.log(Level.INFO, "Retrieving person records from the database");
        q = em.createNamedQuery("Person.findAll");
        List<Person> people = new ArrayList<>();
        try {
            people = q.getResultList();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        HashMap<PersonDetails, HashMap<UserGroupDetail, FacultyMemberRoleDetail>> usersMap = new HashMap<>();
        ArrayList<HashMap<String, Enum>> enumMapList;
        HashMap<UserGroupDetail, FacultyMemberRoleDetail> enumMap;
        for (Person p : people) {
            enumMapList = new ArrayList<>();
            enumMap = new HashMap<>();

            //Retrieving this person's user account
            LOGGER.log(Level.FINE, "Retrieving this person's user account from the database");
            q = em.createNamedQuery("UserAccount.findByPersonId");
            q.setParameter("personId", p.getId());
            try {
                userAccountDetails = userAccountService.retrieveUserAccountByPersonId(p.getId());
            } catch (InvalidArgumentException | InvalidStateException e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                return null;
            }

            //Retrieving this person's faculty member record user group
            LOGGER.log(Level.FINE, "Retrieving this person's faculty member role from the database");
            q = em.createNamedQuery("FacultyMember.findByPersonId");
            q.setParameter("personId", p.getId());
            try {
                facultyMemberDetails = facultyMemberService.retrieveFacultyMemberByPerson(p.getId());
            } catch (InvalidArgumentException | InvalidStateException e) {
                LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
                return null;
            }
            enumMap.put(userAccountDetails.getUserGroup(), facultyMemberDetails.getFacultyMemberRole());

            usersMap.put(convertPersonToPersonDetails(p), enumMap);
        }

        //Returning the details list of person records
        LOGGER.log(Level.INFO, "Returning the details map of person records");
        return usersMap;
    }

    @Override
    public PersonDetails retrievePerson(String referenceNumber) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a person record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving a person record from the database");

        //Checking validity of the reference number passed in
        LOGGER.log(Level.INFO, "Checking validity of the reference number passed in");
        if (referenceNumber == null) {
            LOGGER.log(Level.INFO, "The reference number of the person is null");
            throw new InvalidArgumentException("error_029_06");
        }

        //Retrieving person record from the database
        LOGGER.log(Level.INFO, "Retrieving person record from the database");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", referenceNumber);
        person = new Person();
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No such record exists", e);
            throw new InvalidStateException("error_029_18");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the person record
        LOGGER.log(Level.INFO, "Returning the details of the person");
        return convertPersonToPersonDetails(person);
    }

    @Override
    public PersonDetails retrievePerson(Integer personId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a person record from the database
        LOGGER.log(Level.INFO, "Entered the method for retrieving a person record from the database");

        //Checking validity of the reference number passed in
        LOGGER.log(Level.INFO, "Checking validity of the reference number passed in");
        if (personId == null) {
            LOGGER.log(Level.INFO, "The institution to which the person is associated is null");
            throw new InvalidArgumentException("error_029_16");
        }

        //Retrieving person record from the database
        LOGGER.log(Level.INFO, "Retrieving person record from the database");
        q = em.createNamedQuery("Person.findById");
        q.setParameter("id", personId);
        person = new Person();
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.SEVERE, "No such record exists", e);
            throw new InvalidStateException("error_029_18");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details of the person record
        LOGGER.log(Level.INFO, "Returning the details of the person");
        return convertPersonToPersonDetails(person);
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editPerson(PersonDetails personDetails, UserAccountDetails userAccountDetails, FacultyMemberDetails facultyMemberDetails, EmailContactDetails emailContactDetails, PhoneContactDetails phoneContactDetails, PostalContactDetails postalContactDetails) throws InvalidArgumentException, InvalidStateException, AlgorithmException {
        //Method for editing a person record in the database
        LOGGER.log(Level.INFO, "Entered the method for editing a person record in the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the details passed in");
        if (personDetails == null) {
            LOGGER.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_029_01");
        } else if (personDetails.getId() == null) {
            LOGGER.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_029_17");
        } else if (personDetails.getFirstName() == null || personDetails.getFirstName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The first name is null");
            throw new InvalidArgumentException("error_029_02");
        } else if (personDetails.getFirstName().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The first name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_03");
        } else if (personDetails.getLastName() == null || personDetails.getLastName().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The last name is null");
            throw new InvalidArgumentException("error_029_04");
        } else if (personDetails.getLastName().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The last name is longer than 20 characters");
            throw new InvalidArgumentException("error_029_05");
        } else if (personDetails.getReferenceNumber() == null || personDetails.getReferenceNumber().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The reference number is null");
            throw new InvalidArgumentException("error_029_06");
        } else if (personDetails.getReferenceNumber().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The reference number is longer than 20 characters");
            throw new InvalidArgumentException("error_029_07");
        } else if (personDetails.getNationalIdOrPassport() == null || personDetails.getNationalIdOrPassport().trim().length() == 0) {
            LOGGER.log(Level.INFO, "The national id or passport is null");
            throw new InvalidArgumentException("error_029_08");
        } else if (personDetails.getNationalIdOrPassport().trim().length() > 20) {
            LOGGER.log(Level.INFO, "The national id or passport is longer than 20 characters");
            throw new InvalidArgumentException("error_029_09");
        } else if (facultyMemberDetails == null) {
            LOGGER.log(Level.INFO, "The faculty member details are null");
            throw new InvalidArgumentException("error_028_01");
        }

        //Checking if the reference number is a duplicate
        LOGGER.log(Level.INFO, "Checking if the reference number is a duplicate");
        q = em.createNamedQuery("Person.findByReferenceNumber");
        q.setParameter("referenceNumber", personDetails.getReferenceNumber());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The reference number is available for use");
            person = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_000_01");
        }
        if (person != null) {
            if (!person.getId().equals(personDetails.getId())) {
                LOGGER.log(Level.SEVERE, "The reference number is already in use");
                throw new InvalidArgumentException("error_029_10");
            }
        }

        //Checking if the national id or passport number is a duplicate
        LOGGER.log(Level.INFO, "Checking if the national id or passport number is a duplicate");
        q = em.createNamedQuery("Person.findByNationalIdOrPassport");
        q.setParameter("nationalIdOrPassport", personDetails.getNationalIdOrPassport());
        try {
            person = (Person) q.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.log(Level.INFO, "The national id or passport number is available for use");
            person = null;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record retrieval");
            throw new EJBException("error_029_02");
        }
        if (person != null) {
            if (!person.getId().equals(personDetails.getId())) {
                LOGGER.log(Level.SEVERE, "The national id or passport number is already in use");
                throw new InvalidArgumentException("error_029_11");
            }
        }

        //Creating a container to hold person record
        LOGGER.log(Level.INFO, "Creating a container to hold person record");
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
        LOGGER.log(Level.INFO, "Editing a person record in the database");
        try {
            em.merge(person);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during person record update", e);
            throw new InvalidStateException("error_000_01");
        }

        //Create a message digest algorithm object for SHA-256 hashing algorithm
        LOGGER.log(Level.INFO, "Creating a message digest algorithm object");
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.INFO, "An error occurred while finding the hashing algorithm");
            throw new AlgorithmException("error_007_01");
        }

        //Create the person's user account
        LOGGER.log(Level.INFO, "Creating the person's user account");
        q = em.createNamedQuery("UserAccount.findByPersonId");
        q.setParameter("personId", person.getId());
        try {
            userAccount = (UserAccount) q.getSingleResult();
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "An error occurred during user account record retrieval");
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
        LOGGER.log(Level.INFO, "Editing the user's account record");
        try {
            userAccountService.editUserAccount(userAccountService.convertUserAccountToUserAccountDetails(userAccount));
        } catch (InvalidArgumentException | InvalidStateException e) {
            LOGGER.log(Level.SEVERE, "An error occurred during user account record update", e);
            throw new EJBException("error_000_01");
        }

        //Edit the faculty member record for the person
        LOGGER.log(Level.INFO, "Editing the faculty member record for the person");
        facultyMemberDetails.setPerson(convertPersonToPersonDetails(person));
        try {
            facultyMemberService.editFacultyMember(facultyMemberDetails);
        } catch (InvalidArgumentException | InvalidStateException e) {
            LOGGER.log(Level.SEVERE, "An error occurred during faculty member record update", e);
            throw new EJBException("error_000_01");
        }

        //Commit the changes
        LOGGER.log(Level.INFO, "Committing the changes");
        try {
            em.flush();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "And error occurred while committing the changes", e);
            throw new EJBException("error_000_01");
        }
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removePerson(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a person record from the database
        LOGGER.log(Level.INFO, "Entered the method for removing a person record from the database");

        //Checking validity of details
        LOGGER.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            LOGGER.log(Level.INFO, "The person's unique identifier is null");
            throw new InvalidArgumentException("error_029_17");
        }

        //Get the person record to be removed
        LOGGER.log(Level.INFO, "Getting the person record to be removed");
        person = em.find(Person.class, id);
        int contactId = person.getContact().getId();

        //Removing a person record from the database
        LOGGER.log(Level.INFO, "Removing a person record from the database");
        try {
            em.remove(person);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

        //Remove the person's contact record
        LOGGER.log(Level.INFO, "Removing the person's contact record");
        contactService.removeContact(contactId);

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<PersonDetails> convertPersonsToPersonDetailsList(List<Person> people) {
        //Entered method for converting people list to person details list
        LOGGER.log(Level.FINE, "Entered method for converting people list to person details list");

        //Convert list of people to person details list
        LOGGER.log(Level.FINE, "Convert list of people to person details list");
        List<PersonDetails> details = new ArrayList<>();
        for (Person a : people) {
            details.add(convertPersonToPersonDetails(a));
        }

        //Returning converted person details list
        LOGGER.log(Level.FINE, "Returning converted person details list");
        return details;
    }

    @Override
    public PersonDetails convertPersonToPersonDetails(Person person) {
        //Entered method for converting person to person details
        LOGGER.log(Level.FINE, "Entered method for converting people to person details");

        //Convert list of person to person details
        LOGGER.log(Level.FINE, "Convert list of person to person details");
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
        LOGGER.log(Level.FINE, "Returning converted person details");
        return details;
    }
//</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(PersonRequests.class.getSimpleName());

}
