/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ke.co.miles.ocena.defaults.Controller;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.FacultyMemberRoleDetail;
import ke.co.miles.ocena.utilities.UserAccountDetails;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "PersonController", urlPatterns = {"/createAccountAtMainAdmin", "/createAccountAtSubAdmin", "/addUser", "/adminUserView", "/viewUser", "/retrieveUsers", "/editUser", "/removeUser", "/updateFaculties", "/updateDepartments", "/upgradeUser", "/retrieveUser", "/viewAdminProfile", "/viewUserProfile", "/editUserProfile", "/updateEditFaculties", "/updateEditDepartments", "/updateAdminFaculties", "/updateAdminDepartments", "/validatePassword", "/checkFacultyMemberRole"})
public class PersonController extends Controller {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String path = request.getServletPath();
        PrintWriter out = response.getWriter();
        String destination;
        DateFormat userDateFormat = new SimpleDateFormat("MMM yyyy");

        boolean adminSession;
        try {
            adminSession = (Boolean) session.getAttribute("mainAdminSession");
        } catch (Exception e) {
            logger.log(Level.INFO, "Main admin session is null");
            logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (adminSession == false) {
            try {
                adminSession = (Boolean) session.getAttribute("subAdminSession");
            } catch (Exception e) {
                logger.log(Level.INFO, "Sub admin session is null");
                logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }

        //Check session type
        logger.log(Level.INFO, "Checking session type");
        if (adminSession == true || adminSession == false) {
            //Admin session established
            logger.log(Level.INFO, "Admin session established hence responding to the request");

            switch (path) {

                case "/createAccountAtMainAdmin":

                    //Retrieve and avail the list countries in application scope
                    logger.log(Level.INFO, "Retrieving and availing the list of countries in application scope");
                    try {
                        getServletContext().setAttribute("countries", countryService.retrieveCountries());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during countries retrieval", ex);
                        return;
                    }

                    //Retrieve and avail the list colleges in session scope
                    logger.log(Level.INFO, "Retrieving and availing the list of colleges in session scope");
                    try {
                        session.setAttribute("colleges", collegeService.retrieveColleges());
                    } catch (InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during colleges retrieval", ex);
                        return;
                    }

                    ArrayList<FacultyMemberRoleDetail> memberRoles = new ArrayList<>();
                    memberRoles.addAll(Arrays.asList(FacultyMemberRoleDetail.values()));

                    session.setAttribute("facultyMemberRoles", memberRoles);
                    path = "/addUserAtMainAdmin";
                    logger.log(Level.INFO, "Path is : {0}", path);
                    break;

                case "/createAccountAtSubAdmin":

                    //Retrieve and avail the list countries in application scope
                    logger.log(Level.INFO, "Retrieving and availing the list of countries in application scope");
                    try {
                        getServletContext().setAttribute("countries", countryService.retrieveCountries());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during countries retrieval", ex);
                        return;
                    }

                    //Retrieve and avail the list colleges in session scope
                    logger.log(Level.INFO, "Retrieving and availing the list of colleges in session scope");
                    try {
                        session.setAttribute("colleges", collegeService.retrieveColleges());
                    } catch (InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during colleges retrieval", ex);
                        return;
                    }

                    memberRoles = new ArrayList<>();
                    memberRoles.addAll(Arrays.asList(FacultyMemberRoleDetail.values()));

                    session.setAttribute("facultyMemberRoles", memberRoles);
                    path = "/addUserAtSubAdmin";
                    logger.log(Level.INFO, "Path is : {0}", path);
                    break;

                case "/addUser":

                    //Retrieve and set person details 
                    logger.log(Level.INFO, "Retrieving the person details");
                    country = new CountryDetails();
                    country.setId(Integer.parseInt(request.getParameter("country")));

                    emailContact = new EmailContactDetails();
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email-address"));

                    phoneContact = new PhoneContactDetails();
                    phoneContact.setActive(true);
                    phoneContact.setFixedNumber(request.getParameter("fixed-number"));
                    phoneContact.setMobileNumber(request.getParameter("mobile-number"));

                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("box-number"));
                    postalContact.setPostalCode(request.getParameter("postal-code"));

                    person = new PersonDetails();
                    person.setActive(true);
                    person.setLastName(request.getParameter("last-name"));
                    person.setFirstName(request.getParameter("first-name"));
                    person.setNationalIdOrPassport(request.getParameter("national-id-or-passport"));
                    person.setReferenceNumber(request.getParameter("reference-number"));

                    userAccount = new UserAccountDetails();

                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("campus-faculty")));
                    } catch (Exception e) {
                        logger.log(Level.INFO, "The person is not a member of a faculty");
                    }

                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("campus-department")));
                        faculty = new FacultyDetails();
                    } catch (Exception e) {
                        logger.log(Level.INFO, "The person is not a member of a department");
                    }

                    Date admissionYear;
                    try {
                        String date = request.getParameter("admission-year");
                        if (!date.equals("")) {
                            admissionYear = userDateFormat.parse(date);
                        } else {
                            admissionYear = null;
                        }
                    } catch (ParseException ex) {
                        logger.log(Level.INFO, "An error occurred while parsing the admission month and year", ex);
                        admissionYear = null;
                    }

                    Calendar holder;
                    Calendar calendar;
                    if (admissionYear != null) {
                        holder = Calendar.getInstance();
                        holder.setTime(admissionYear);

                        calendar = Calendar.getInstance();
                        calendar.clear();
                        calendar.set(Calendar.MONTH, holder.get(Calendar.MONTH));
                        calendar.set(Calendar.YEAR, holder.get(Calendar.YEAR));
                        admissionYear = calendar.getTime();
                    }

                    FacultyMemberRoleDetail memberRole;
                    try {
                        memberRole = FacultyMemberRoleDetail.getFacultyMemberRoleDetail(Short.parseShort(request.getParameter("memberRole")));

                        if (memberRole.equals(FacultyMemberRoleDetail.LECTURER)) {
                            userAccount.setUserGroup(UserGroupDetail.LECTURER);
                        } else if (memberRole.equals(FacultyMemberRoleDetail.MANAGEMENT)) {
                            userAccount.setUserGroup(UserGroupDetail.MANAGEMENT);
                        } else if (memberRole.equals(FacultyMemberRoleDetail.OTHER_STAFF)) {
                            userAccount.setUserGroup(UserGroupDetail.OTHER_STAFF);
                        } else if (memberRole.equals(FacultyMemberRoleDetail.STUDENT)) {
                            userAccount.setUserGroup(UserGroupDetail.STUDENT);
                        }
                    } catch (Exception e) {
                        memberRole = FacultyMemberRoleDetail.STUDENT;
                        userAccount.setUserGroup(UserGroupDetail.STUDENT);
                    }

                    facultyMember = new FacultyMemberDetails();
                    facultyMember.setActive(true);
                    facultyMember.setFaculty(faculty);
                    facultyMember.setDepartment(department);
                    facultyMember.setAdmissionYear(admissionYear);
                    facultyMember.setFacultyMemberRole(memberRole);

                    try {
                        personService.addPerson(person, userAccount, facultyMember, emailContact, phoneContact, postalContact);
                    } catch (Exception e) {
                        logger.log(Level.INFO, "Person record creation failed", e);
                    }

                    path = (String) session.getAttribute("home");
                    logger.log(Level.INFO, "Path is : {0}", path);

                    break;

                case "/updateFaculties":
                    //Read in the college unique identifier
                    logger.log(Level.INFO, "Reading in the college unique identifier");
                    Integer collegeId = Integer.parseInt(request.getParameter("collegeId"));

                    //Retrieve the faculties in the college and availing them on session
                    logger.log(Level.INFO, "Retrieving the faculties in the college and availing them on session");
                    List<FacultyDetails> faculties;
                    try {
                        faculties = facultyService.retrieveFaculties(collegeId);
                        session.setAttribute("faculties", faculties);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty records retrieval");
                        return;
                    }

                    //Update the faculties options
                    if (!faculties.isEmpty()) {
                        out.write("<label for=\"campus-faculty\"> School/faculty </label>");
                        out.write("<select name=\"campus-faculty\" id=\"campus-faculty\" onchange=\"updateDepartments()\" required=\"true\">");
                        for (FacultyDetails f : faculties) {
                            out.write(" <option value=\"" + f.getId() + "\"> " + f.getName() + " </option>\n selected");
                        }
                        out.write("</select>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/updateDepartments":
                    //Read in the faculty unique identifier
                    logger.log(Level.INFO, "Reading in the faculty unique identifier");
                    Integer facultyId = Integer.parseInt(request.getParameter("facultyId"));

                    //Retrieve the departments in the faculty and availing them on session
                    logger.log(Level.INFO, "Retrieving the departments in the faculty and availing them on session");
                    List<DepartmentDetails> departments;
                    try {
                        departments = departmentService.retrieveDepartments(facultyId);
                        session.setAttribute("departments", departments);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during department records retrieval");
                        return;
                    }

                    //Update the department options
                    if (!departments.isEmpty()) {
                        out.write("<label for=\"campus-department\">Department </label>");
                        out.write("<select name=\"campus-department\" id=\"campus-department\" required=\"true\">");
                        for (DepartmentDetails d : departments) {
                            out.write(" <option value=\"" + d.getId() + "\"> " + d.getName() + " </option>\n selected");
                        }
                        out.write("</select>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/updateEditFaculties":
                    //Read in the college unique identifier
                    logger.log(Level.INFO, "Reading in the college unique identifier");
                    collegeId = Integer.parseInt(request.getParameter("collegeId"));

                    //Retrieve the faculties in the college and availing them on session
                    logger.log(Level.INFO, "Retrieving the faculties in the college and availing them on session");
                    try {
                        faculties = facultyService.retrieveFaculties(collegeId);
                        session.setAttribute("faculties", faculties);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty records retrieval");
                        return;
                    }

                    //Update the faculties options
                    if (!faculties.isEmpty()) {
                        out.write("<td><label for=\"campus-faculty\"> School/faculty </label></td>");
                        out.write("<td><select name=\"campus-faculty\" id=\"edit-campus-faculty\" onchange=\"updateEditDepartments()\" required=\"true\">");
                        for (FacultyDetails f : faculties) {
                            out.write(" <option value=\"" + f.getId() + "\"> " + f.getName() + " </option>\n selected");
                        }
                        out.write("</select></td>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/updateEditDepartments":
                    //Read in the faculty unique identifier
                    logger.log(Level.INFO, "Reading in the faculty unique identifier");
                    facultyId = Integer.parseInt(request.getParameter("facultyId"));

                    //Retrieve the departments in the faculty and availing them on session
                    logger.log(Level.INFO, "Retrieving the departments in the faculty and availing them on session");
                    try {
                        departments = departmentService.retrieveDepartments(facultyId);
                        session.setAttribute("departments", departments);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during department records retrieval");
                        return;
                    }

                    //Update the department options
                    if (!departments.isEmpty()) {
                        out.write("<td><label for=\"campus-department\">Department </label></td>");
                        out.write("<td><select name=\"campus-department\" id=\"edit-campus-department\" required=\"true\">");
                        for (DepartmentDetails d : departments) {
                            out.write(" <option value=\"" + d.getId() + "\"> " + d.getName() + " </option>\n selected");
                        }
                        out.write("</select></td>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/updateAdminFaculties":
                    //Read in the college unique identifier
                    logger.log(Level.INFO, "Reading in the college unique identifier");
                    collegeId = Integer.parseInt(request.getParameter("collegeId"));

                    //Retrieve the faculties in the college and availing them on session
                    logger.log(Level.INFO, "Retrieving the faculties in the college and availing them on session");
                    try {
                        faculties = facultyService.retrieveFaculties(collegeId);
                        session.setAttribute("faculties", faculties);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty records retrieval");
                        return;
                    }

                    //Update the faculties options
                    if (!faculties.isEmpty()) {
                        out.write("<td><label for=\"campus-faculty\"> School/faculty </label></td>");
                        out.write("<td><select name=\"campus-faculty\" id=\"edit-admin-campus-faculty\" onchange=\"updateAdminDepartments()\" required=\"true\">");
                        for (FacultyDetails f : faculties) {
                            out.write(" <option value=\"" + f.getId() + "\"> " + f.getName() + " </option>\n selected");
                        }
                        out.write("</select></td>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/updateAdminDepartments":
                    //Read in the faculty unique identifier
                    logger.log(Level.INFO, "Reading in the faculty unique identifier");
                    facultyId = Integer.parseInt(request.getParameter("facultyId"));

                    //Retrieve the departments in the faculty and availing them on session
                    logger.log(Level.INFO, "Retrieving the departments in the faculty and availing them on session");
                    try {
                        departments = departmentService.retrieveDepartments(facultyId);
                        session.setAttribute("departments", departments);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during department records retrieval");
                        return;
                    }

                    //Update the department options
                    if (!departments.isEmpty()) {
                        out.write("<td><label for=\"campus-department\">Department </label></td>");
                        out.write("<td><select name=\"campus-department\" id=\"edit-admin-campus-department\" required=\"true\">");
                        for (DepartmentDetails d : departments) {
                            out.write(" <option value=\"" + d.getId() + "\"> " + d.getName() + " </option>\n selected");
                        }
                        out.write("</select></td>");
                    } else {
                        out.write("");
                    }

                    return;

                case "/viewUser":
                    //Retrieve and avail the list colleges in session scope
                    logger.log(Level.INFO, "Retrieving and availing the list of colleges in session scope");
                    try {
                        session.setAttribute("colleges", collegeService.retrieveColleges());
                    } catch (InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during colleges retrieval", ex);
                        return;
                    }

                    //Proceed to the page
                    break;

                case "/adminUserView":
                    //Retrieve and avail the list colleges in session scope
                    logger.log(Level.INFO, "Retrieving and availing the list of colleges in session scope");
                    try {
                        session.setAttribute("colleges", collegeService.retrieveColleges());
                    } catch (InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during colleges retrieval", ex);
                        return;
                    }
                    //Proceed to the page
                    break;

                case "/retrieveUser":
                    //Read in the user's reference number
                    logger.log(Level.INFO, "Reading in the user's reference number");
                    String referenceNumber = request.getParameter("referenceNumber");

                    //Retrieve the matching person
                    logger.log(Level.INFO, "Retrieving the matching person");
                    person = new PersonDetails();
                    try {
                        person = personService.retrievePerson(referenceNumber);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during person record retrieval");
                        return;
                    }

                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    userAccount = new UserAccountDetails();
                    try {
                        userAccount = userAccountService.retrieveUserAccount(referenceNumber);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during user account record retrieval");
                        return;
                    }

                    //Retrieve the corresponding faculty member
                    logger.log(Level.INFO, "Retrieving the corresponding faculty member");
                    facultyMember = new FacultyMemberDetails();
                    try {
                        facultyMember = facultyMemberService.retrieveFacultyMemberByPerson(person.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty member record retrieval");
                        return;
                    }

                    //Display the user details
                    logger.log(Level.INFO, "Displaying the user details");
                    displayUserDetails(response, facultyMember, userAccount, person);

                    return;

                case "/upgradeUser":

                    //Retrieve the matching person
                    logger.log(Level.INFO, "Retrieving the matching person");
                    person = new PersonDetails();
                    try {
                        person = personService.retrievePerson(Integer.parseInt(request.getParameter("personId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during person record retrieval");
                        return;
                    }

                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    userAccount = new UserAccountDetails();
                    try {
                        userAccount = userAccountService.retrieveUserAccount(person.getReferenceNumber());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during user account record retrieval");
                        return;
                    }

                    //Hold the current user account details
                    logger.log(Level.INFO, "Holding the current user account details");
                    UserAccountDetails userAccountHolder = userAccount;

                    //Upgrade the person's user group
                    logger.log(Level.INFO, "Upgrading the person's user group");
                    userAccount.setUserGroup(UserGroupDetail.getUserGroupDetail(Short.parseShort(request.getParameter("userGroup"))));
                    try {
                        userAccountService.editUserAccount(userAccount);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred during user account record update");
                        return;
                    }

                    //Retrieve the corresponding faculty member
                    logger.log(Level.INFO, "Retrieving the corresponding faculty member");
                    facultyMember = new FacultyMemberDetails();
                    try {
                        facultyMember = facultyMemberService.retrieveFacultyMemberByPerson(person.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty member record retrieval");
                        return;
                    }

                    //Upgrade the person's member role
                    logger.log(Level.INFO, "Upgrading the person's member role");
                    facultyMember.setFacultyMemberRole(FacultyMemberRoleDetail.getFacultyMemberRoleDetail(Short.parseShort(request.getParameter("memberRole"))));
                    try {
                        facultyMemberService.editFacultyMember(facultyMember);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty member record update");
                        return;
                    }

                    //Display the user details
                    logger.log(Level.INFO, "Displaying the user details");
                    displayUserDetails(response, facultyMember, userAccount, person);

                    return;

                case "/viewUserProfile":
                case "/viewAdminProfile":

                    //Read in the person's unique identifier
                    logger.log(Level.INFO, "Reading in the person's unique identifier");
                    Integer personId = ((PersonDetails) session.getAttribute("person")).getId();

                    //Retrieve the person
                    logger.log(Level.INFO, "Retrieving the person");
                    person = new PersonDetails();
                    try {
                        person = personService.retrievePerson(personId);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during person record retrieval");
                        return;
                    }

                    //Avail the person in session
                    logger.log(Level.INFO, "Availing the person in session");
                    session.setAttribute("person", person);

                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    userAccount = new UserAccountDetails();
                    try {
                        userAccount = userAccountService.retrieveUserAccount(person.getReferenceNumber());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during user account record retrieval");
                        return;
                    }

                    //Avail the user account in session
                    logger.log(Level.INFO, "Availing the user account in session");
                    session.setAttribute("userAccount", userAccount);

                    //Retrieve the corresponding faculty member
                    logger.log(Level.INFO, "Retrieving the corresponding faculty member");
                    facultyMember = new FacultyMemberDetails();
                    try {
                        facultyMember = facultyMemberService.retrieveFacultyMemberByPerson(person.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty member record retrieval");
                        return;
                    }

                    //Avail the faculty member in session
                    logger.log(Level.INFO, "Availing the faculty member in session");
                    session.setAttribute("facultyMember", facultyMember);

                    //Retrieve the person's contact
                    logger.log(Level.INFO, "Retrieving the person's contact");
                    contact = new ContactDetails();
                    try {
                        contact = contactService.retrieveContact(person.getContact().getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during contact record retrieval");
                        return;
                    }

                    //Avail the contact in session
                    logger.log(Level.INFO, "Availing the contact in session");
                    session.setAttribute("contact", contact);

                    //Retrieve the person's email contact
                    logger.log(Level.INFO, "Retrieving the person's contact");
                    emailContact = new EmailContactDetails();
                    try {
                        emailContact = emailContactService.retrieveEmailContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during email contact record retrieval");
                        return;
                    }

                    //Avail the email contact in session
                    logger.log(Level.INFO, "Availing the email contact in session");
                    session.setAttribute("emailContact", emailContact);

                    //Retrieve the person's phone contact
                    logger.log(Level.INFO, "Retrieving the person's phone contact");
                    phoneContact = new PhoneContactDetails();
                    try {
                        phoneContact = phoneContactService.retrievePhoneContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during phone contact record retrieval");
                        return;
                    }

                    //Avail the phone contact in session
                    logger.log(Level.INFO, "Availing the phone contact in session");
                    session.setAttribute("phoneContact", phoneContact);

                    //Retrieve the person's postal contact
                    logger.log(Level.INFO, "Retrieving the person's postal contact");
                    postalContact = new PostalContactDetails();
                    try {
                        postalContact = postalContactService.retrievePostalContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during postal contact record retrieval");
                        return;
                    }

                    //Avail the postal contact in session
                    logger.log(Level.INFO, "Availing the postal contact in session");
                    session.setAttribute("postalContact", postalContact);

                    //Retrieve and avail the list of colleges in session scope
                    logger.log(Level.INFO, "Retrieving and availing the list of colleges in session scope");
                    try {
                        session.setAttribute("colleges", collegeService.retrieveColleges());
                    } catch (InvalidStateException ex) {
                        logger.log(Level.SEVERE, "An error occurred during colleges retrieval", ex);
                        return;
                    }

                    break;

                case "/editUserProfile":

                    //Read in the contact details
                    contact = new ContactDetails();
                    contact.setId(Integer.parseInt(request.getParameter("contact-id")));
                    contact.setActive(true);

                    //Read in and set person details 
                    logger.log(Level.INFO, "Reading in the person details");
                    person = new PersonDetails();
                    person.setActive(true);
                    person.setContact(contact);
                    person.setLastName(request.getParameter("last-name"));
                    person.setFirstName(request.getParameter("first-name"));
                    person.setId(Integer.parseInt(request.getParameter("person-id")));
                    person.setReferenceNumber(request.getParameter("reference-number"));
                    person.setNationalIdOrPassport(request.getParameter("national-id-or-passport"));

                    //Read in the email contact details
                    logger.log(Level.INFO, "Reading in the person's email contact details");
                    emailContact = new EmailContactDetails();
                    emailContact.setContact(contact);
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email-address"));
                    emailContact.setId(Integer.parseInt(request.getParameter("email-contact-id")));

                    //Read in the phone contact details
                    logger.log(Level.INFO, "Reading in the person's phone contact details");
                    phoneContact = new PhoneContactDetails();
                    phoneContact.setContact(contact);
                    phoneContact.setActive(true);
                    phoneContact.setId(Integer.parseInt(request.getParameter("phone-contact-id")));
                    phoneContact.setMobileNumber(request.getParameter("mobile-number"));
                    phoneContact.setFixedNumber(request.getParameter("fixed-number"));

                    //Read in the country details
                    logger.log(Level.INFO, "Reading in the country details");
                    country = new CountryDetails();
                    try {
                        country.setId(Integer.parseInt(request.getParameter("country")));
                    } catch (Exception e) {
                        country.setId(new Integer("110"));
                    }

                    //Read in the postal contact details
                    logger.log(Level.INFO, "Reading in the person's postal contact details");
                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setContact(contact);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("box-number"));
                    postalContact.setPostalCode(request.getParameter("postal-code"));
                    postalContact.setId(Integer.parseInt(request.getParameter("postal-contact-id")));

                    //Retrieve the corresponding faculty member
                    logger.log(Level.INFO, "Retrieving the corresponding faculty member");
                    facultyMember = new FacultyMemberDetails();
                    try {
                        facultyMember = facultyMemberService.retrieveFacultyMemberByPerson(person.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during faculty member record retrieval");
                        return;
                    }

                    //Read in the department
                    logger.log(Level.INFO, "Read in the department details");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("campus-department")));
                        logger.log(Level.INFO, "Department details are provided");
                        faculty = null;
                    } catch (Exception e) {
                        logger.log(Level.INFO, "Department details are not provided");

                        //Read in the faculty
                        logger.log(Level.INFO, "Read in the faculty details");
                        faculty = new FacultyDetails();
                        try {
                            faculty.setId(Integer.parseInt(request.getParameter("campus-faculty")));
                            logger.log(Level.INFO, "Faculty details are provided");
                            department = null;
                        } catch (Exception ex) {
                            logger.log(Level.INFO, "Faculty details are not provided");
                            try {
                                if (facultyMember.getFaculty().getId() != null) {
                                    faculty.setId(facultyMember.getFaculty().getId());
                                }
                            } catch (Exception exc) {
                                logger.log(Level.INFO, "Faculty details did not exist before");
                            }

                            try {
                                if (facultyMember.getDepartment().getId() != null) {
                                    department.setId(facultyMember.getDepartment().getId());
                                }
                            } catch (Exception exc) {
                                logger.log(Level.INFO, "Department details did not exist before");
                            }
                        }

                    }

                    try {
                        if (faculty.getId() != null) {
                            try {
                                if (department.getId() != null) {
                                    if (facultyMember.getDepartment().getId() != null) {
                                        department.setId(null);
                                        logger.log(Level.INFO, "Department details cleared");
                                    }
                                }
                            } catch (NullPointerException e) {
                                logger.log(Level.INFO, "Department details are not provided, first check abandoned");
                            }
                        }
                    } catch (NullPointerException e) {
                        logger.log(Level.INFO, "Faculty details are not provided, first check abandoned");
                    }

                    try {
                        if (department.getId() != null) {
                            try {
                                if (faculty.getId() != null) {
                                    if (facultyMember.getFaculty().getId() != null) {
                                        faculty.setId(null);
                                        logger.log(Level.INFO, "Faculty details cleared");
                                    }
                                }
                            } catch (NullPointerException e) {
                                logger.log(Level.INFO, "Faculty details are not provided, second check abandoned");
                            }
                        }
                    } catch (NullPointerException e) {
                        logger.log(Level.INFO, "Department details are not provided, second check abandoned");
                    }

                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    userAccount = new UserAccountDetails();
                    try {
                        userAccount = userAccountService.retrieveUserAccountByPersonId(Integer.parseInt(request.getParameter("person-id")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during user account record retrieval");
                        return;
                    }

                    if (userAccount.getUserGroup().equals(UserGroupDetail.STUDENT)) {
                        try {
                            admissionYear = userDateFormat.parse(request.getParameter("admission-year"));
                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while parsing the admission month and year", e);
                            return;
                        }

                        if (admissionYear != null) {
                            holder = Calendar.getInstance();
                            holder.setTime(admissionYear);

                            calendar = Calendar.getInstance();
                            calendar.clear();
                            calendar.set(Calendar.MONTH, holder.get(Calendar.MONTH));
                            calendar.set(Calendar.YEAR, holder.get(Calendar.YEAR));
                            admissionYear = calendar.getTime();
                        }
                    } else {
                        admissionYear = null;
                    }

                    facultyMember.setFaculty(faculty);
                    facultyMember.setDepartment(department);
                    facultyMember.setAdmissionYear(admissionYear);

                    //Create a message digest algorithm for SHA-256 hashing algorithm
                    logger.log(Level.INFO, "Creating a message digest hashing algorithm object");
                    MessageDigest messageDigest;
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
                        break;
                    }

                    //Read in the old entered password
                    logger.log(Level.INFO, "Reading in the old entered password");
                    String oldPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("old-password"));

                    //Check validity of the entered old password
                    logger.log(Level.INFO, "Checking validity of the entered old password");
                    if (oldPassword.equals(userAccount.getPassword())) {
                        //Password is valid
                        logger.log(Level.INFO, "Old password is valid");
                        String newPassword = request.getParameter("new-password");
                        String confirmationPassword = request.getParameter("confirm-password");
                        if (newPassword != null && newPassword.trim().length() > 0) {
                            if (newPassword.equals(confirmationPassword)) {
                                userAccount.setPassword(newPassword);
                            } else {
                                //Password is invalid
                                logger.log(Level.INFO, "Passwords do not match");
                            }
                        } else {
                            //Password is invalid
                            logger.log(Level.INFO, "Old password is unchanged");
                        }
                    } else {
                        //Password is invalid
                        logger.log(Level.INFO, "Old password is invalid");
                    }

                    //Edit the person details
                    logger.log(Level.INFO, "Editing the person details");
                    try {
                        personService.editPerson(person, userAccount, facultyMember, emailContact, phoneContact, postalContact);
                    } catch (InvalidArgumentException | InvalidStateException | NoSuchAlgorithmException e) {
                        logger.log(Level.INFO, "Person record creation failed", e);
                    }

                    path = (String) session.getAttribute("home");
                    logger.log(Level.INFO, "Path is : {0}", path);

                    break;

                case "/validatePassword":
                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    userAccount = new UserAccountDetails();
                    try {
                        userAccount = userAccountService.retrieveUserAccountByPersonId(Integer.parseInt(request.getParameter("personId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during user account record retrieval");
                        return;
                    }

                    //Create a message digest algorithm for SHA-256 hashing algorithm
                    logger.log(Level.INFO, "Creating a message digest hashing algorithm object");
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
                        break;
                    }

                    //Read in the entered password
                    logger.log(Level.INFO, "Reading in the entered password");
                    oldPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("password"));

                    //Check validity of the entered password
                    logger.log(Level.INFO, "Checking validity of the entered password");
                    if (oldPassword.equals(userAccount.getPassword())) {
                        //Password is valid
                        logger.log(Level.INFO, "Old password is valid");
                        out.write("");
                    } else {
                        //Password is invalid
                        logger.log(Level.INFO, "Old password is invalid");
                        out.write("<span class=\"btn btn-warning\">Wrong password entered!</span>");
                    }

                    return;

                case "/checkFacultyMemberRole":

                    logger.log(Level.INFO, "Checking faculty member role");
                    try {
                        memberRole = FacultyMemberRoleDetail.getFacultyMemberRoleDetail(Short.parseShort(request.getParameter("memberRole")));
                    } catch (Exception e) {
                        return;
                    }

                    logger.log(Level.INFO, "Checking if student");
                    if (memberRole.equals(FacultyMemberRoleDetail.STUDENT)) {
                        logger.log(Level.INFO, "Faculty member role is student");
                        out.write("<label for=\"admission-year\">Admission month & year</label>");
                        out.write("<input type=\"date\" name=\"admission-year\" id=\"admission-year\" required=\"true\"/>");
                    } else {
                        logger.log(Level.INFO, "Faculty member role is not student");
                    }

                    logger.log(Level.INFO, "Returning from the method");
                    return;

            }

            destination = "WEB-INF/views" + path + ".jsp";
            try {
                logger.log(Level.INFO, "Dispatching request to: {0}", destination);
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.log(Level.INFO, "Request dispatch failed");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Display user details">
    private void displayUserDetails(HttpServletResponse response, FacultyMemberDetails facultyMember, UserAccountDetails userAccount, PersonDetails person) throws IOException {

        //Retrieve the faculty to which the faculty member belongs if any
        logger.log(Level.INFO, "Retrieving the faculty to which the faculty member belongs if any");
        faculty = new FacultyDetails();
        try {
            if (facultyMember.getFaculty() != null) {
                faculty = facultyService.retrieveFaculty(facultyMember.getFaculty().getId());
            } else {
                logger.log(Level.INFO, "The person does not belong to a faculty");
                faculty = null;
            }
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "The person does not belong to a faculty");
            faculty = null;
        }

        //Retrieve the department to which the faculty member belongs if any
        logger.log(Level.INFO, "Retrieving the department to which the faculty member belongs if any");
        department = new DepartmentDetails();
        try {
            if (facultyMember.getDepartment() != null) {
                department = departmentService.retrieveDepartment(facultyMember.getDepartment().getId());

            } else {
                logger.log(Level.INFO, "The person does not belong to a faculty");
                department = null;
            }
        } catch (InvalidArgumentException | InvalidStateException ex) {
            logger.log(Level.INFO, "The person does not belong to a department");
            department = null;
        }

        PrintWriter out = response.getWriter();

        out.write("<table id=\"upgrade-user-details\">");
        out.write("<thead>");
        out.write("<tr>");
        out.write("<th colspan=\"2\"> DETAILS FOR " + person.getReferenceNumber() + " </th>");
        out.write("</tr>");
        out.write("</thead>");
        out.write("<tfoot>");
        out.write("<tr>");
        out.write("<td colspan=\"2\"> Upgrade the user's role</td>");
        out.write("</tr>");
        out.write("</tfoot>");
        out.write("<tbody>");
        out.write("<input type=\"hidden\" id=\"upgrade-person-identifier\" value=\"" + person.getId() + "\">");
        out.write("<tr>");
        out.write("<td> Name: </td>");
        out.write("<td> <input type='text' id='upgrade-full-name' value='" + person.getFirstName() + " " + person.getLastName() + "' readonly=\"true\"> </td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td> Number: </td>");
        out.write("<td> <input type='text' id='user-number' value='" + person.getReferenceNumber() + "' readonly=\"true\"> </td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td> Member in: </td>");
        if (faculty != null) {
            out.write("<td> <input type='text' id='upgrade-member-institution' value='" + faculty.getName() + " (" + faculty.getAbbreviation() + ")' readonly=\"true\"> </td>");
        } else if (department != null) {
            out.write("<td> <input type='text' id='upgrade-member-institution' value='" + department.getName() + " (" + department.getAbbreviation() + ")' readonly=\"true\"> </td>");
        }
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td> Member role: </td>");
        out.write("<td> ");
        out.write("<select id='upgrade-member-role'>");
        for (FacultyMemberRoleDetail mr : FacultyMemberRoleDetail.values()) {
            if (facultyMember.getFacultyMemberRole() == mr) {
                out.write("<option value='" + mr.getId() + "' selected>" + mr.getFacultyMemberRole() + "</option>");
            } else {
                out.write("<option value='" + mr.getId() + "'>" + mr.getFacultyMemberRole() + "</option>");
            }
        }
        out.write(" </select>");
        out.write(" </td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td> User group: </td>");
        out.write("<td> ");
        out.write("<select id='upgrade-user-group'>");
        for (UserGroupDetail ug : UserGroupDetail.values()) {
            if (userAccount.getUserGroup() == ug) {
                out.write("<option value='" + ug.getId() + "' selected>" + ug.getUserGroup() + "</option>");
            } else {
                out.write("<option value='" + ug.getId() + "'>" + ug.getUserGroup() + "</option>");
            }
        }
        out.write(" </select>");
        out.write(" </td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td colspan=\"2\"> <button class='btn btn-default' onclick=\"upgradeUser(); return false;\"> Upgrade </button> </td>");
        out.write("</tr>");
        out.write("</tbody>");
        out.write("</table>");

    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(PersonController.class.getSimpleName());

}
