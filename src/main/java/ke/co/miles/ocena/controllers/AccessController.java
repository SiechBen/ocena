/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ke.co.miles.ocena.defaults.Controller;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidLoginException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "AccessController", urlPatterns = {"/checkLoginInfo", "/login", "/logout", "/home", "/index"})
public class AccessController extends Controller {

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

        PrintWriter out = response.getWriter();

        //Get the user session
        HttpSession session = request.getSession(false);

        //Get the user path
        String path = request.getServletPath();
        String destination;

        switch (path) {

            case "/index":

                //Redirect to index page
                logger.log(Level.INFO, "Path is: {0}", path);
                try {
                    request.getRequestDispatcher("index.jsp").forward(request, response);
                } catch (IOException | ServletException e) {
                    logger.log(Level.SEVERE, "Request dispatch failed", e);
                }

                break;

            case "/checkLoginInfo":

                String username = request.getParameter("username");
                String password = request.getParameter("password");

                if (username == null || username.trim().length() == 0
                        || password == null || password.trim().length() == 0) {

                    return;

                } else {
                    boolean userExists;
                    try {
                        overallAdminDetails = overallAdminService.userExists(username, password);
                        userExists = true;
                    } catch (InvalidArgumentException | InvalidStateException | InvalidLoginException e) {
                        logger.log(Level.INFO, "Invalid login attempt");
                        userExists = false;
                    }
                    if (userExists) {
                        //Admin login
                        out.write("<input type=\"hidden\" id=\"useful-username\" value=\"valid\">");
                        out.write("<input type=\"hidden\" id=\"useful-password\" value=\"valid\">");
                        return;

                    } else {

                        //Retrieve the logged in user
                        Map<PersonDetails, UserGroupDetail> personUserGroupMap;
                        try {
                            personUserGroupMap = personService.retrievePerson(username, password);

                            //Valid login attempt
                            out.write("<input type=\"hidden\" id=\"useful-username\" value=\"valid\">");
                            out.write("<input type=\"hidden\" id=\"useful-password\" value=\"valid\">");

                            return;

                        } catch (InvalidArgumentException | InvalidLoginException | NoSuchAlgorithmException ex) {

                            return;
                        }
                    }
                }

            case "/login":

                //Clear the previous session if any
                if (session != null) {
                    session.invalidate();
                }

                /*if (session == null || ((new Date().getTime() - session.getLastAccessedTime()) > 1800000)) {*/
                //Authentificate the user
                username = request.getParameter("login-username");
                password = request.getParameter("login-password");

                if (username == null || username.trim().length() == 0
                        || password == null || password.trim().length() == 0) {

                    return;

                } else {

                    boolean userExists;
                    try {
                        overallAdminDetails = overallAdminService.userExists(username, password);
                        userExists = true;
                    } catch (InvalidArgumentException | InvalidStateException | InvalidLoginException e) {
                        logger.log(Level.INFO, "Invalid login attempt");
                        userExists = false;
                    }
                    if (userExists) {

                        session = request.getSession(true);
                        session.setAttribute("adminCredentials", overallAdminDetails);
                        session.setAttribute("home", "/adminDashboard");
                        session.setAttribute("subAdminSession", false);
                        session.setAttribute("mainAdminSession", true);
                        session.setAttribute("user", "Admin");

                        //Retrieve the institution and avail it in the session
                        availApplicationAttributes();

                        path = "/adminDashboard";
                        logger.log(Level.SEVERE, "Path is : {0}", path);

                    } else {

                        //Retrieve the logged in user
                        Map<PersonDetails, UserGroupDetail> personUserGroupMap;
                        try {
                            personUserGroupMap = personService.retrievePerson(username, password);
                        } catch (InvalidArgumentException | InvalidLoginException | NoSuchAlgorithmException ex) {

                            return;
                        }

                        person = null;
                        UserGroupDetail userGroup = null;
                        for (Map.Entry<PersonDetails, UserGroupDetail> entryKey : personUserGroupMap.entrySet()) {
                            person = entryKey.getKey();
                            userGroup = entryKey.getValue();
                        }

                        //Create a new session
                        session = request.getSession(true);

                        if (person != null) {
                            //Set the user
                            session.setAttribute("person", person);
                            session.setAttribute("user", person.getFirstName());
                        }

                        if (userGroup != null) {
                            switch (userGroup.getUserGroup()) {
                                case "Admin":
                                    //Set the session type
                                    session.setAttribute("subAdminSession", true);
                                    session.setAttribute("mainAdminSession", false);

                                    path = null;

                                    break;

                                case "Management":
                                    //Set the home window for the management
                                    session.setAttribute("home", "/managementDashboard");
                                    //Set the session type
                                    session.setAttribute("subAdminSession", true);
                                    session.setAttribute("mainAdminSession", false);
                                    //Set the path
                                    path = "/managementDashboard";
                                    break;

                                case "Lecturer":
                                    //Set the home window for the lecturer
                                    session.setAttribute("home", "/lecturerDashboard");
                                    //Set the session type
                                    session.setAttribute("subAdminSession", false);
                                    session.setAttribute("mainAdminSession", false);
                                    //Set the path
                                    path = "/lecturerDashboard";
                                    break;

                                case "Student":
                                    //Set the home window for the student
                                    session.setAttribute("home", "/studentDashboard");
                                    //Set the session type
                                    session.setAttribute("subAdminSession", false);
                                    session.setAttribute("mainAdminSession", false);

                                    //Set the path
                                    path = "/studentDashboard";
                                    break;

                                case "Other staff":
                                    //Set the home window for any other staff member
                                    session.setAttribute("home", "/otherStaffDashboard");
                                    //Set the session type
                                    session.setAttribute("subAdminSession", false);
                                    session.setAttribute("mainAdminSession", false);
                                    //Set the path
                                    path = "/otherStaffDashboard";
                                    break;

                                default:
                                    //Set the home window for any visitors
                                    session.setAttribute("home", "/visitorDashboard");
                                    //Set the session type
                                    session.setAttribute("subAdminSession", false);
                                    session.setAttribute("mainAdminSession", false);
                                    //Set the path
                                    path = "/visitorDashboard";

                            }

                            //Retrieve the corresponding faculty member 
                            logger.log(Level.INFO, "Retrieving the corresponding faculty member");
                            facultyMember = new FacultyMemberDetails();
                            try {
                                facultyMember = facultyMemberService.retrieveFacultyMemberByPerson(person.getId());
                            } catch (InvalidArgumentException | InvalidStateException ex) {
                                logger.log(Level.INFO, "An error occurred during faculty member retrieval");
                            }

                            //Avail the faculty member on session
                            logger.log(Level.INFO, "Availing the faculty member on session");
                            session.setAttribute("facultyMember", facultyMember);

                            //If the student is the member of a faculty
                            if (facultyMember.getFaculty() != null) {

                                //Avail the faculty for which the student is a member in session
                                logger.log(Level.INFO, "Availing the faculty for which the student is a member in session");
                                faculty = new FacultyDetails();
                                try {
                                    faculty = facultyService.retrieveFaculty(facultyMember.getFaculty().getId());
                                    session.setAttribute("faculty", faculty);
                                } catch (InvalidArgumentException | InvalidStateException ex) {
                                    logger.log(Level.INFO, "An error occurred during faculty retrieval");
                                }

                                //Set the path
                                if (path == null) {
                                    path = "/facultyDashboard";

                                    //Set the homepage
                                    session.setAttribute("home", "/facultyDashboard");

                                    //Retrieve records required at the faculty dashboard
                                    logger.log(Level.INFO, "Retrieving the records required at the faculty dashboard");
                                    availOtherRequiredRecords(session, faculty);
                                }

                                //If the student is the member of a department
                            } else if (facultyMember.getDepartment() != null) {

                                //Avail the department for which the student is a member in session
                                logger.log(Level.INFO, "Availing the department for which the student is a member in session");
                                department = new DepartmentDetails();
                                try {
                                    department = departmentService.retrieveDepartment(facultyMember.getDepartment().getId());
                                    session.setAttribute("department", department);
                                } catch (InvalidArgumentException | InvalidStateException ex) {
                                    logger.log(Level.INFO, "An error occurred during department retrieval");
                                }

                                //Set the path
                                if (path == null) {
                                    path = "/departmentDashboard";

                                    //Set the homepage
                                    session.setAttribute("home", "/departmentDashboard");

                                    //Retrieve records required at the department dashboard
                                    logger.log(Level.INFO, "Retrieving the records required at the department dashboard");
                                    availOtherRequiredRecords(session, department);
                                }

                            }

                            //Retrieve admission records and avail them on session
                            logger.log(Level.INFO, "Retrieving admission records and availing them on session");
                            try {
                                session.setAttribute("admissions", admissionService.retrieveAdmissions());
                            } catch (Exception e) {
                                logger.log(Level.INFO, "An error occurred during admissions retrieval");
                            }

                            logger.log(Level.SEVERE, "Path is : {0}", path);

                        }

                        //Retrieve the institution and avail it in the session
                        availApplicationAttributes();

                        break;
                    }
                }

                break;

            case "/home":
                //Change path to appropriate homepage
                logger.log(Level.INFO, "Directing user to home");
                path = (String) session.getAttribute("home");
                logger.log(Level.SEVERE, "Path is : {0}", path);

                break;

            case "/logout":
                //Logout user by invalidating session
                session.setAttribute("subAdminSession", false);
                session.setAttribute("mainAdminSession", false);
                session.invalidate();

                //Change path to index page
                path = "index.jsp";
                logger.log(Level.SEVERE, "Path is : {0}", path);
                //Redirect user to the page
                request.getRequestDispatcher(path).forward(request, response);
                return;
        }

        //Use request dispatcher to foward request internally
        destination = "/WEB-INF/views" + path + ".jsp";

        logger.log(Level.INFO,
                "Request dispatch to forward to: {0}", destination);
        try {
            request.getRequestDispatcher(destination).forward(request, response);
        } catch (ServletException | IOException e) {
            logger.log(Level.SEVERE, "Request dispatch failed", e);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Avail application attributes">
    private void availApplicationAttributes() {
        //Retrieve the list of countries
        logger.log(Level.INFO, "Retrieving the list of countries");
        List<CountryDetails> countries;
        try {
            countries = countryService.retrieveCountries();
        } catch (InvalidArgumentException | InvalidStateException ex) {
            logger.log(Level.SEVERE, "An error occurred during countries retrieval", ex);
            return;
        }

        //Avail the countries in the application scope
        logger.log(Level.INFO, "Avail the countries in the application scope");
        if (countries != null) {
            getServletContext().setAttribute("countries", countries);
        }

        //Retrieve the institution
        logger.log(Level.INFO, "Retrieving the institution");
        institution = new InstitutionDetails();
        try {
            institution = institutionService.retrieveInstitution();
        } catch (InvalidArgumentException | InvalidStateException ex) {
            logger.log(Level.SEVERE, "An error occurred during instituion retrieval", ex);
            return;
        }

        //Avail the institution in the application scope
        logger.log(Level.INFO, "Avail the institution in the application scope");
        if (institution.getId() != null) {
            //session.setAttribute("institution", institution);
            getServletContext().setAttribute("institution", institution);
        }

    }
//</editor-fold>

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

    private static final Logger logger = Logger.getLogger(AccessController.class
            .getSimpleName());
}
