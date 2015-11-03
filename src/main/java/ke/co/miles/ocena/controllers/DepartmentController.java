/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "DepartmentController", urlPatterns = {"/addDepartment", "/retrieveDepartments", "/editDepartment", "/removeDepartment", "/departmentDashboard", "/checkDepartment", "/saveEditedDepartment"})
public class DepartmentController extends Controller {

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
        if (adminSession == false) {
            //Admin session not established
            logger.log(Level.INFO, "Admin session not established hence not responding to the request");
        } else if (adminSession == true) {
            //Admin session established
            logger.log(Level.INFO, "Admin session established hence responding to the request");

            String path = request.getServletPath();
            String destination;
            Integer departmentId;
            Random random = new Random();
            List<DepartmentDetails> departments = new ArrayList<>();

            faculty = new FacultyDetails();
            try {
                faculty = facultyService.retrieveFaculty(Integer.parseInt(request.getParameter("facultyId")));
            } catch (InvalidArgumentException | InvalidStateException ex) {
                logger.log(Level.FINE, "An error occurred while while retrieving the faculty record to the database", ex);
            }

            switch (path) {
                case "/departmentDashboard":
                    //Read in the department's unique identifier
                    logger.log(Level.INFO, "Retrieving the department's unique identifier");
                    departmentId = Integer.parseInt(request.getParameter("departmentId"));

                    //Retrieve the department record from the database
                    logger.log(Level.INFO, "Retrieving the department details from the database");
                    department = new DepartmentDetails();
                    try {
                        department = departmentService.retrieveDepartment(departmentId);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the department details");
                    }

                    //Avail the department in the session 
                    logger.log(Level.INFO, "Availing the department in the session");
                    session.setAttribute("department", department);

                    //Avail other required records in the session
                    availOtherRequiredRecords(session, department);

                    path = "/departmentDashboard";
                    logger.log(Level.INFO, "Path is: {0}", path);
                    break;

                case "/checkDepartment":
                    //Read in the faculty's unique identifier
                    logger.log(Level.INFO, "Retrieving the faculty's unique identifier");
                    int facultyId = Integer.parseInt(request.getParameter("facultyId"));

                    //Retrieve the faculty record from the database
                    logger.log(Level.INFO, "Retrieving the faculty details from the database");
                    faculty = new FacultyDetails();
                    try {
                        faculty = facultyService.retrieveFaculty(facultyId);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the faculty details");
                    }

                    //Avail the faculty in the session 
                    logger.log(Level.INFO, "Availing the faculty in the session");
                    session.setAttribute("faculty", faculty);

                    //Retrieve the faculty record from the database
                    logger.log(Level.INFO, "Retrieving the faculty details from the database");
                    departments = new ArrayList<>();
                    try {
                        departments = departmentService.retrieveDepartments(faculty.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the departments in a faculty");
                    }

                    //Checking if there are departments in this faculty
                    if (departments.isEmpty()) {

                        //Avail other required records in the session
                        availOtherRequiredRecords(session, faculty);

                        path = "/facultyDashboard";
                        logger.log(Level.INFO, "Path is {0}", path);
                    } else {
                        //Retrieve the departments from the database
                        logger.log(Level.INFO, "Retrieving departments in {0} from the database", faculty.getName());
                        try {
                            departments = departmentService.retrieveDepartments(faculty.getId());
                        } catch (InvalidArgumentException | InvalidStateException ex) {
                            logger.log(Level.INFO, "An error ocurred while retrieving departments", ex);
                        }

                        //Avail the departments in the session
                        logger.log(Level.INFO, "Availing the list of departments in the session");
                        session.setAttribute("departments", departments);

                        //Set the path to display the departments
                        path = "/viewDepartments";
                        logger.log(Level.INFO, "Displaying the departments \nPath is: {0}", path);
                    }

                    break;

                case "/retrieveDepartments":

                    //Retrieve the list of departments and set the path to where they'll be displayed
                    path = retrieveDepartments(faculty, departments, session);
                    break;

                case "/addDepartment":
                    //Read in the country details
                    country = new CountryDetails();
                    try {
                        country.setId(Integer.parseInt(request.getParameter("country")));
                    } catch (Exception e) {
                        country.setId(new Integer("110"));
                    }

                    //Read in the email contact details
                    emailContact = new EmailContactDetails();
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email"));

                    //Read in the phone contact details
                    phoneContact = new PhoneContactDetails();
                    phoneContact.setActive(true);
                    phoneContact.setMobileNumber(request.getParameter("mobileNumber"));
                    phoneContact.setFixedNumber(request.getParameter("fixedNumber"));

                    //Read in the postal contact details
                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("boxNumber"));
                    postalContact.setPostalCode(request.getParameter("postalCode"));

                    //Read in values for the department record to be made
                    logger.log(Level.INFO, "Reading in values for the department record to be made");
                    department = new DepartmentDetails();
                    department.setActive(true);
                    department.setFaculty(faculty);
                    department.setName(request.getParameter("name"));
                    department.setAbbreviation(request.getParameter("abbreviation"));

                    if (emailContact.getEmailAddress().trim().length() == 0) {
                        emailContact.setEmailAddress((department.getAbbreviation() + "." + faculty.getAbbreviation() + "@uonbi.ac.ke").toLowerCase());
                    }
                    if (phoneContact.getMobileNumber().trim().length() == 0) {
                        phoneContact.setMobileNumber("+254-7" + random.nextInt(1000) + "0" + random.nextInt(1000) + random.nextInt(1000));
                    }

                    //Send the details to the entity manager
                    logger.log(Level.INFO, "Sending the details to the entity manager");
                    try {
                        departmentService.addDepartment(department, emailContact, phoneContact, postalContact);
                    } catch (Exception e) {
                        logger.log(Level.INFO, "An error occurred while adding the department record to the database", e);
                    }

                    //Retrieve the new list of departments
                    logger.log(Level.INFO, "Retrieving departments from the database");
                    try {
                        departments = departmentService.retrieveDepartments(faculty.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error ocurred while retrieving departments", ex);
                    }

                    //Update table body
                    generateTableBody(departments, faculty, response);
                    return;
                case "/editDepartment":
                    //Read in the department's unique identifier
                    logger.log(Level.INFO, "Retrieving the department's unique identifier");
                    departmentId = Integer.parseInt(request.getParameter("departmentId"));

                    //Retrieve the department record from the database
                    logger.log(Level.INFO, "Retrieving the department details from the database");
                    department = new DepartmentDetails();
                    try {
                        department = departmentService.retrieveDepartment(departmentId);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the department details");
                    }

                    //Retrieve the contact record from the database
                    logger.log(Level.INFO, "Retrieving the contact details from the database");
                    contact = new ContactDetails();
                    try {
                        contact = contactService.retrieveContact(department.getContact().getId());
                    } catch (InvalidStateException | InvalidArgumentException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the contact details");
                    }

                    //Retrieve the email contact details
                    logger.log(Level.INFO, "Retrieving the email contact details");
                    emailContact = new EmailContactDetails();
                    try {
                        emailContact = emailContactService.retrieveEmailContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the department email contact");
                    }

                    //Retrieve the phone contact details
                    logger.log(Level.INFO, "Retrieving the phone contact details");
                    phoneContact = new PhoneContactDetails();
                    try {
                        phoneContact = phoneContactService.retrievePhoneContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the department phone contact");
                    }

                    //Retrieve the postal contact details
                    logger.log(Level.INFO, "Retrieving the postal contact details");
                    postalContact = new PostalContactDetails();
                    try {
                        postalContact = postalContactService.retrievePostalContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred when retrieving the department postal contact");
                    }

                    //Avail the department and its contacts in the session
                    logger.log(Level.INFO, "Availing the department and its contacts in the session");
                    session.setAttribute("department", department);
                    session.setAttribute("contact", contact);
                    session.setAttribute("postalContact", postalContact);
                    session.setAttribute("phoneContact", phoneContact);
                    session.setAttribute("emailContact", emailContact);

                    path = "/editDepartment";
                    logger.log(Level.INFO, "Path is: {0}", path);
                    break;

                case "/saveEditedDepartment":

                    //Read in the contact details
                    contact = new ContactDetails();
                    contact.setId(Integer.parseInt(request.getParameter("contact-id")));
                    contact.setActive(true);

                    //Read in values for the department record to be made
                    logger.log(Level.INFO, "Reading in values for the department record to be made");
                    department = new DepartmentDetails();
                    department.setActive(true);
                    department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    department.setFaculty(faculty);
                    department.setContact(contact);
                    department.setName(request.getParameter("department-name"));
                    department.setAbbreviation(request.getParameter("department-abbreviation"));

                    //Read in the email contact details
                    emailContact = new EmailContactDetails();
                    emailContact.setContact(contact);
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email-address"));
                    emailContact.setId(Integer.parseInt(request.getParameter("email-contact-id")));

                    //Read in the phone contact details
                    phoneContact = new PhoneContactDetails();
                    phoneContact.setContact(contact);
                    phoneContact.setActive(true);
                    phoneContact.setId(Integer.parseInt(request.getParameter("phone-contact-id")));
                    phoneContact.setMobileNumber(request.getParameter("mobile-number"));
                    phoneContact.setFixedNumber(request.getParameter("fixed-number"));

                    if (emailContact.getEmailAddress().trim().length() == 0) {
                        emailContact.setEmailAddress((department.getAbbreviation() + "." + faculty.getAbbreviation() + "@uonbi.ac.ke").toLowerCase());
                    }
                    if (phoneContact.getMobileNumber().trim().length() == 0) {
                        phoneContact.setMobileNumber("+254-7" + random.nextInt(1000) + "0" + random.nextInt(1000) + random.nextInt(1000));
                    }

                    //Read in the country details
                    country = new CountryDetails();
                    try {
                        country.setId(Integer.parseInt(request.getParameter("country")));
                    } catch (Exception e) {
                        country.setId(new Integer("110"));
                    }

                    //Read in the postal contact details
                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setContact(contact);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("box-number"));
                    postalContact.setPostalCode(request.getParameter("postal-code"));
                    postalContact.setId(Integer.parseInt(request.getParameter("postal-contact-id")));

                    //Send the details to the entity manager
                    logger.log(Level.INFO, "Sending the details to the entity manager");
                    try {
                        departmentService.editDepartment(department, emailContact, phoneContact, postalContact);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while adding the department record to the database", e);
                    }

                    //Retrieve the list of departments and set the path to where they'll be displayed
                    path = retrieveDepartments(faculty, departments, session);
                    logger.log(Level.INFO, "Displaying the departments \nPath is: {0}", path);

                    break;

                case "/removeDepartment":
                    //Send the unique identifier of the department to be removed to the entity manager
                    logger.log(Level.INFO, "Sending the unique identifier of the department to be removed to the entity manager");
                    try {
                        departmentService.removeDepartment(Integer.parseInt(request.getParameter("id")));
                    } catch (NumberFormatException | InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while removing the department record from the database", e);
                    }

                    //Retrieve the new list of departments
                    logger.log(Level.INFO, "Retrieving departments from the database");
                    try {
                        departments = departmentService.retrieveDepartments(faculty.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error ocurred while retrieving departments", ex);
                    }

                    //Update table body
                    generateTableBody(departments, faculty, response);
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

    //<editor-fold defaultstate="collapsed" desc="Generate table body">
    private void generateTableBody(List<DepartmentDetails> departments, FacultyDetails faculty, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int index = 0;

        for (DepartmentDetails d : departments) {
            if (index % 2 == 0) {
                out.write("<tr class=\"even\">");
            } else {
                out.write("<tr>");
            }
            out.write("<td onclick=\"loadWindow('/Ocena/departmentDashboard?departmentId=" + d.getId() + "&facultyId=" + faculty.getId() + "')\">" + ++index + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/departmentDashboard?departmentId=" + d.getId() + "&facultyId=" + faculty.getId() + "')\">" + d.getName() + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/departmentDashboard?departmentId=" + d.getId() + "&facultyId=" + faculty.getId() + "')\">" + d.getAbbreviation() + "</td>");
            out.write("<td><button onclick=\"loadWindow('/Ocena/editDepartment?departmentId=" + d.getId() + "&facultyId=" + faculty.getId() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write(" <td><button onclick=\"removeDepartment('" + d.getId() + "', '" + faculty.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Retrieve departments">
    private String retrieveDepartments(FacultyDetails f, List<DepartmentDetails> departments, HttpSession session) {
        //Retrieve the departments from the database
        logger.log(Level.INFO, "Retrieving departments in {0} from the database", f.getName());
        try {
            departments = departmentService.retrieveDepartments(f.getId());
        } catch (InvalidArgumentException | InvalidStateException ex) {
            logger.log(Level.INFO, "An error ocurred while retrieving departments", ex);
        }

        //Avail the departments in the session
        logger.log(Level.INFO, "Availing the list of departments in the session");
        session.setAttribute("departments", departments);
        session.setAttribute("faculty", f);

        //Set the path to display the departments
        String path = "/viewDepartments";
        logger.log(Level.INFO, "Displaying the departments \nPath is: {0}", path);
        return path;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Retrieve other required records">
    private void availOtherRequiredRecords(HttpSession session, Object object) {
        List<QuestionDetails> questions = new ArrayList<>();
        List<AdmissionDetails> admissions = new ArrayList<>();
        Map<QuestionDetails, RatingTypeDetail> ratingTypesByQuestionMap;
        List<QuestionCategoryDetails> questionCategories = new ArrayList<>();
        List<MeansOfAnsweringDetail> meansOfAnsweringList = new ArrayList<>();
        Map<QuestionDetails, MeansOfAnsweringDetail> meansOfAnsweringByQuestionMap;
        Map<RatingTypeDetail, List<RatingDetails>> ratingTypeAndValuesMap = new HashMap();
        Map<AdmissionDetails, List<DegreeDetails>> degreesByAdmissionMap = new HashMap<>();
        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = new HashMap<>();

        //Determine the object's identity and cast it to the appropriate class then retrieve evaluation questions about it
        logger.log(Level.INFO, "Determining object's identity");
        if (object instanceof FacultyDetails) {

            logger.log(Level.INFO, "Casting the object to FacultyDetails");
            faculty = (FacultyDetails) object;
            try {
                questions = questionService.retrieveQuestionsInFaculty(faculty.getId());

                //Retrieve the map of questions of a faculty in question categories from the database
                logger.log(Level.INFO, "Retrieving the map of questions of a faculty in question categories from the database");
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfFacultyByQuestionCategories(faculty);

            } catch (InvalidArgumentException | InvalidStateException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving list of questions in a faculty");
            }

            try {
                //Retrieve the map of degrees by faculty from the database
                logger.log(Level.INFO, "Retrieving the map of degrees by faculty from the database");
                degreesByAdmissionMap = degreeService.retrieveDegreesOfFacultyByAdmission(faculty);
            } catch (InvalidArgumentException | InvalidStateException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving list of degrees in a faculty");
            }

        } else if (object instanceof DepartmentDetails) {

            logger.log(Level.INFO, "Casting the object to DepartmentDetails");
            department = (DepartmentDetails) object;
            try {
                questions = questionService.retrieveQuestionsInDepartment(department.getId());

                //Retrieve the map of questions of a department in question categories from the database
                logger.log(Level.INFO, "Retrieving the map of questions of a department in question categories from the database");
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfDepartmentByQuestionCategories(department);
            } catch (InvalidArgumentException | InvalidStateException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving list of questions in a department");
            }

            try {
                //Retrieve the map of degrees by department from the database
                logger.log(Level.INFO, "Retrieving the map of degrees by department from the database");
                degreesByAdmissionMap = degreeService.retrieveDegreesOfDepartmentByAdmission(department);
            } catch (InvalidArgumentException | InvalidStateException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving list of degrees in a department");
            }
        }

        //Avail the map in session of questions in question categories
        logger.log(Level.INFO, "Availing the map of questions in question categories in session");
        session.setAttribute("questionsInQuestionCategoryMap", questionsInQuestionCategoryMap);

        //Avail the map of degrees by admission in session
        logger.log(Level.INFO, "Availing the map of degrees by admission in session");
        session.setAttribute("degreesByAdmissionMap", degreesByAdmissionMap);

        //Add the means of answering to the list
        logger.log(Level.INFO, "Adding the means of answering to the list");
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_RATING);
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_REASONING);
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_LISTING_COMMENTS);

        //Avail the list of means of answering in the session 
        logger.log(Level.INFO, "Availing the list of means of answering in the session");
        session.setAttribute("meansOfAnsweringList", meansOfAnsweringList);

        //Retrieve the list of question category records from the database
        logger.log(Level.INFO, "Retrieving the list of question category records from the database");
        try {
            questionCategories = questionCategoryService.retrieveQuestionCategories();
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred during question category record creation", e);
        }

        //Avail the question categories in session
        logger.log(Level.INFO, "Availing the question categories in session");
        session.setAttribute("questionCategories", questionCategories);

        //Retrieve the list of admissions
        logger.log(Level.INFO, "Retrieving the new list of admission records");
        try {
            admissions = admissionService.retrieveAdmissions();
        } catch (InvalidStateException e) {
            logger.log(Level.SEVERE, "An error occurred during retrieval of admissions", e);
        }

        //Avail the admissions in session
        logger.log(Level.INFO, "Availing the admissions in session");
        session.setAttribute("admissions", admissions);

        //Retrieve the list of rating records from the database
        logger.log(Level.INFO, "Retrieving the list of rating records from the database");
        try {
            ratingTypeAndValuesMap = ratingService.retrieveRatings();
        } catch (InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred during rating record retrieval", e);
        }

        //Avail the map of rating types and values in session
        logger.log(Level.INFO, "Availing the map of rating types and values in session");
        session.setAttribute("ratingTypeAndValuesMap", ratingTypeAndValuesMap);

        //Retrieve the map of rating types by question from the database
        logger.log(Level.INFO, "Retrieving the map of rating types by question from the database");
        try {
            ratingTypesByQuestionMap = questionService.retrieveRatingTypesByQuestion(questions);
        } catch (InvalidArgumentException ex) {
            logger.log(Level.INFO, "An error occurred during rating record retrieval");
            return;
        }

        //Avail the map in session
        logger.log(Level.INFO, "Availing the map in session");
        session.setAttribute("ratingTypesByQuestionMap", ratingTypesByQuestionMap);

        //Retrieve the map of means of answering by question from the database
        logger.log(Level.INFO, "Retrieving the map of means of answering by question from the database");
        try {
            meansOfAnsweringByQuestionMap = questionService.retrieveMeansOfAnsweringByQuestion(questions);
        } catch (InvalidArgumentException ex) {
            logger.log(Level.INFO, "An error occurred during rating record retrieval");
            return;
        }

        //Avail the map in session
        logger.log(Level.INFO, "Availing the map of means of answering by question in session");
        session.setAttribute("meansOfAnsweringByQuestionMap", meansOfAnsweringByQuestionMap);

    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(DepartmentController.class.getSimpleName());

}
