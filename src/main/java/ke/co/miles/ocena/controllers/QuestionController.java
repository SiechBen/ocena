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
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
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
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "QuestionController", urlPatterns = {"/addQuestion", "/retrieveQuestions", "/editQuestion", "/removeQuestion"})
public class QuestionController extends Controller {

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

        //Get the user's locale and the associated resource bundle
        Locale locale = request.getLocale();
        bundle = ResourceBundle.getBundle("text", locale);

        boolean adminSession;
        try {
            adminSession = (Boolean) session.getAttribute("mainAdminSession");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Main admin session is null");
            LOGGER.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (adminSession == false) {
            try {
                adminSession = (Boolean) session.getAttribute("subAdminSession");
            } catch (Exception e) {
                LOGGER.log(Level.INFO, "Sub admin session is null");
                LOGGER.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }

        //Check session type
        LOGGER.log(Level.INFO, "Checking session type");
        if (adminSession == false) {
            //Admin session not established
            LOGGER.log(Level.INFO, "Admin session not established hence not responding to the request");

            String path = (String) session.getAttribute("home");
            LOGGER.log(Level.INFO, "Path is: {0}", path);
            String destination = "/WEB-INF/views" + path + ".jsp";
            try {
                LOGGER.log(Level.INFO, "Dispatching request to: {0}", destination);
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                LOGGER.log(Level.INFO, "Request dispatch failed");
            }

        } else if (adminSession == true) {
            //Admin session established
            LOGGER.log(Level.INFO, "Admin session established hence responding to the request");

            String path = request.getServletPath();
            String destination;
            Map<RatingTypeDetail, List<RatingDetails>> ratingTypeAndValuesMap = new HashMap<>();

            switch (path) {
                case "/addQuestion":

                    //Read in the unique identifier of the faculty to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the question belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the question belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a department");
                        department = null;
                    }

                    //Read in the unique identifier of the question category
                    LOGGER.log(Level.INFO, "Reading in the question category id");
                    questionCategory = new QuestionCategoryDetails();
                    try {
                        questionCategory.setId(Short.parseShort(request.getParameter("questionCategoryId")));
                    } catch (NumberFormatException e) {
                        questionCategory = null;
                    }

                    //Read in details for the question
                    LOGGER.log(Level.INFO, "Reading in details for the question");
                    question = new QuestionDetails();
                    question.setActive(true);
                    question.setFaculty(faculty);
                    question.setDepartment(department);
                    question.setQuestionCategory(questionCategory);
                    question.setQuestion(request.getParameter("question"));
                    question.setMeansOfAnswering(MeansOfAnsweringDetail.getMeansOfAnswering(Short.parseShort(request.getParameter("meansOfAnsweringId"))));
                    try {
                        question.setRatingType(RatingTypeDetail.getRatingType(Short.parseShort(request.getParameter("ratingTypeId"))));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.FINE, "Rating type is null");
                    }

                    //Send the details to the entity manager for recording in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for recording in the database");
                    try {
                        questionService.addQuestion(question);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail records in the session and
                    //Display the new list of question records
                    if (question.getFaculty() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, question.getFaculty());
                    } else if (question.getDepartment() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, question.getDepartment());
                    }

                    return;

                case "/editQuestion":

                    //Read in the unique identifier of the faculty to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the question belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the question belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a department");
                        department = null;
                    }

                    //Read in the unique identifier of the question category
                    LOGGER.log(Level.INFO, "Reading in the question category id");
                    questionCategory = new QuestionCategoryDetails();
                    try {
                        questionCategory.setId(Short.parseShort(request.getParameter("questionCategoryId")));
                    } catch (NumberFormatException e) {
                        questionCategory = null;
                    }

                    //Read in details for the question
                    LOGGER.log(Level.INFO, "Reading in details for the question");
                    question = new QuestionDetails();
                    question.setActive(true);
                    question.setFaculty(faculty);
                    question.setDepartment(department);
                    question.setQuestionCategory(questionCategory);
                    question.setQuestion(request.getParameter("question"));
                    question.setId(Integer.parseInt(request.getParameter("questionId")));
                    question.setMeansOfAnswering(MeansOfAnsweringDetail.getMeansOfAnswering(Short.parseShort(request.getParameter("meansOfAnsweringId"))));
                    try {
                        question.setRatingType(RatingTypeDetail.getRatingType(Short.parseShort(request.getParameter("ratingTypeId"))));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.FINE, "Rating type is null");
                    }

                    //Send the details to the entity manager for record update in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record update in the database");
                    try {
                        questionService.editQuestion(question);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail records in the session and
                    //Display the new list of question records
                    if (question.getFaculty() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, question.getFaculty());
                    } else if (question.getDepartment() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, question.getDepartment());
                    }
                    return;

                case "/removeQuestion":

                    //Read in the unique identifier of the faculty to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the question belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the question belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the question belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The question does not belong to a department");
                        department = null;
                    }

                    //Send the details to the entity manager for record removal from the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record removal from the database");
                    try {
                        questionService.removeQuestion(Integer.parseInt(request.getParameter("questionId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        Logger.getLogger(QuestionController.class.getName()).log(Level.SEVERE, null, e);
                    }

                    //Avail other required records in the session and
                    //Display the new list of question records
                    if (faculty != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, faculty);
                    } else if (department != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, department);
                    }
                    return;
            }
            destination = "WEB-INF/views" + path + ".jsp";
            try {
                LOGGER.log(Level.INFO, "Dispatching request to: {0}", destination);
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString("redirection_failed"));
                LOGGER.log(Level.INFO, bundle.getString("redirection_failed"), e);
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

    //<editor-fold defaultstate="collapsed" desc="Retrieve other required records and generate table body">
    private void generateTableBodyAndAvailOtherRecords(HttpSession session, HttpServletResponse response, Object object) throws IOException {

        //<editor-fold defaultstate="collapsed" desc="Avail other records in session">       
        List<QuestionDetails> questions = new ArrayList<>();
        Map<QuestionDetails, RatingTypeDetail> ratingTypesByQuestionMap;
        List<QuestionCategoryDetails> questionCategories = new ArrayList<>();
        List<MeansOfAnsweringDetail> meansOfAnsweringList = new ArrayList<>();
        Map<QuestionDetails, MeansOfAnsweringDetail> meansOfAnsweringByQuestionMap;
        Map<RatingTypeDetail, List<RatingDetails>> ratingTypeAndValuesMap = new HashMap();
        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = new HashMap<>();

        //Determine the object's identity and cast it to the appropriate class then retrieve evaluation questions about it
        LOGGER.log(Level.INFO, "Determining object's identity");
        if (object instanceof FacultyDetails) {

            LOGGER.log(Level.INFO, "Casting the object to FacultyDetails");
            faculty = (FacultyDetails) object;
            department = null;
            try {
                questions = questionService.retrieveQuestionsInFaculty(faculty.getId());

                //Retrieve the map of questions of a faculty in question categories from the database
                LOGGER.log(Level.INFO, "Retrieving the map of questions of a faculty in question categories from the database");
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfFacultyByQuestionCategories(faculty);
            } catch (InvalidArgumentException | InvalidStateException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString(e.getCode()));
                LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            }

        } else if (object instanceof DepartmentDetails) {

            LOGGER.log(Level.INFO, "Casting the object to DepartmentDetails");
            department = (DepartmentDetails) object;
            faculty = null;
            try {
                questions = questionService.retrieveQuestionsInDepartment(department.getId());

                //Retrieve the map of questions of a department in question categories from the database
                LOGGER.log(Level.INFO, "Retrieving the map of questions of a department in question categories from the database");
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfDepartmentByQuestionCategories(department);
            } catch (InvalidArgumentException | InvalidStateException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString(e.getCode()));
                LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            }
        }

        //Avail the questions in the session
        LOGGER.log(Level.INFO, "Availing the questions in session");
        session.setAttribute("questionsInQuestionCategoryMap", questionsInQuestionCategoryMap);

        //Add the means of answering to the list
        LOGGER.log(Level.INFO, "Adding the means of answering to the list");
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_RATING);
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_REASONING);
        meansOfAnsweringList.add(MeansOfAnsweringDetail.BY_LISTING_COMMENTS);

        //Avail the list of means of answering in the session 
        LOGGER.log(Level.INFO, "Availing the list of means of answering in the session");
        session.setAttribute("meansOfAnsweringList", meansOfAnsweringList);

        //Retrieve the list of question category records from the database
        LOGGER.log(Level.INFO, "Retrieving the list of question category records from the database");
        try {
            questionCategories = questionCategoryService.retrieveQuestionCategories();
        } catch (InvalidArgumentException | InvalidStateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
        }

        //Avail the question categories in session
        LOGGER.log(Level.INFO, "Availing the question categories in session");
        session.setAttribute("questionCategories", questionCategories);

        //Retrieve the list of rating records from the database
        LOGGER.log(Level.INFO, "Retrieving the list of rating records from the database");
        try {
            ratingTypeAndValuesMap = ratingService.retrieveRatings();
        } catch (InvalidStateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
        }

        //Avail the map of rating types and values in session
        LOGGER.log(Level.INFO, "Availing the map of rating types and values in session");
        session.setAttribute("ratingTypeAndValuesMap", ratingTypeAndValuesMap);

        //Avail the map in session
        LOGGER.log(Level.INFO, "Availing the map of questions in question categories in session");
        session.setAttribute("questionsInQuestionCategoryMap", questionsInQuestionCategoryMap);

        //Retrieve the map of rating types by question from the database
        LOGGER.log(Level.INFO, "Retrieving the map of rating types by question from the database");
        try {
            ratingTypesByQuestionMap = questionService.retrieveRatingTypesByQuestion(questions);
        } catch (InvalidArgumentException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            return;
        }

        //Avail the map in session
        LOGGER.log(Level.INFO, "Availing the map in session");
        session.setAttribute("ratingTypesByQuestionMap", ratingTypesByQuestionMap);

        //Retrieve the map of means of answering by question from the database
        LOGGER.log(Level.INFO, "Retrieving the map of means of answering by question from the database");
        try {
            meansOfAnsweringByQuestionMap = questionService.retrieveMeansOfAnsweringByQuestion(questions);
        } catch (InvalidArgumentException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            return;
        }

        //Avail the map in session
        LOGGER.log(Level.INFO, "Availing the map of means of answering by question in session");
        session.setAttribute("meansOfAnsweringByQuestionMap", meansOfAnsweringByQuestionMap);
        //</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="Generate table body">
        int index;
        PrintWriter out = response.getWriter();
        for (QuestionCategoryDetails c : questionsInQuestionCategoryMap.keySet()) {

            index = 0;
            out.write("<tr>");
            out.write("<td> &nbsp; </td>");
            out.write("<td colspan=\"6\"> <strong> " + c.getCategory() + " </strong> </td>");
            out.write("</tr>");

            for (QuestionDetails qd : questionsInQuestionCategoryMap.get(c)) {
                out.write("<tr>");
                out.write("<td> &nbsp; </td>");
                out.write("<td> " + ++index + " </td>");
                out.write("<td> " + qd.getQuestion() + " </td>");
                out.write("<td> " + meansOfAnsweringByQuestionMap.get(qd).getMeansOfAnswering() + " </td>");
                if (qd.getRatingType() == null) {
                    out.write("<td> N/A </td>");
                    if (faculty != null) {
                        out.write("<td><button onclick=\"editQuestion('" + qd.getQuestion() + "', '" + qd.getId() + "', '" + c.getId() + "', '" + meansOfAnsweringByQuestionMap.get(qd).getId() + "', '', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                        out.write("<td><button onclick=\"removeQuestion('" + qd.getId() + "', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                    } else if (department != null) {
                        out.write("<td><button onclick=\"editQuestion('" + qd.getQuestion() + "', '" + qd.getId() + "', '" + c.getId() + "', '" + meansOfAnsweringByQuestionMap.get(qd).getId() + "', '', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                        out.write("<td><button onclick=\"removeQuestion('" + qd.getId() + "', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                    }
                } else {
                    out.write("<td> " + ratingTypesByQuestionMap.get(qd).getRatingType() + " </td>");
                    if (faculty != null) {
                        out.write("<td><button onclick=\"editQuestion('" + qd.getQuestion() + "', '" + qd.getId() + "', '" + c.getId() + "', '" + meansOfAnsweringByQuestionMap.get(qd).getId() + "', '" + ratingTypesByQuestionMap.get(qd).getId() + "', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                        out.write("<td><button onclick=\"removeQuestion('" + qd.getId() + "', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                    } else if (department != null) {
                        out.write("<td><button onclick=\"editQuestion('" + qd.getQuestion() + "', '" + qd.getId() + "', '" + c.getId() + "', '" + meansOfAnsweringByQuestionMap.get(qd).getId() + "', '" + ratingTypesByQuestionMap.get(qd).getId() + "', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                        out.write("<td><button onclick=\"removeQuestion('" + qd.getId() + "', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                    }
                }
                out.write("</tr>");
            }
        }
        //</editor-fold>
    }
    //</editor-fold>   

    private static final Logger LOGGER = Logger.getLogger(QuestionController.class.getSimpleName());

}
