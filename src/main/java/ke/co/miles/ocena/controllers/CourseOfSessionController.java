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
import ke.co.miles.ocena.utilities.CourseDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "CourseOfSessionController", urlPatterns = {"/retrieveCoursesOfSession", "/addCourseOfSession", "/editCourseOfSession", "/removeCourseOfSession"})
public class CourseOfSessionController extends Controller {

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
        List<CourseOfSessionDetails> coursesOfSession;

        boolean adminSession;
        try {
            adminSession = (Boolean) session.getAttribute("subAdminSession");
        } catch (Exception e) {
            logger.log(Level.INFO, "Admin session is null");
            logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
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
            institution = new InstitutionDetails();
            institution = (InstitutionDetails) getServletContext().getAttribute("institution");

            switch (path) {

                case "/retrieveCoursesOfSession":

                    //Read in the evaluation session unique identifier
                    logger.log(Level.INFO, "Reading in the evaluation session unique identifier");
                    evaluationSession = new EvaluationSessionDetails();
                    evaluationSession.setId(Integer.parseInt(request.getParameter("evaluationSessionId")));

                    //Avail the evaluation session in the session
                    logger.log(Level.INFO, "Avail the evaluation session in the session");
                    session.setAttribute("evaluationSession", evaluationSession);

                    //Retrieve the courses of session
                    logger.log(Level.INFO, "Retrieving the courses of session");
                    coursesOfSession = new ArrayList<>();
                    try {
                        coursesOfSession = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while retrieving the courses of session");
                    }

                    //Avail the courses of evaluation session in the session
                    logger.log(Level.INFO, "Avail the courses of evaluation session in the session");
                    session.setAttribute("coursesOfSession", coursesOfSession);

                    //Retrieve the map of people by courses of session
                    logger.log(Level.INFO, "Retrieving the map of people by courses of session");
                    Map<CourseOfSessionDetails, PersonDetails> personByCourseOfSessionMap = new HashMap<>();
                    try {
                        personByCourseOfSessionMap = courseOfSessionService.retrievePersonByCourseOfSession(coursesOfSession);
                    } catch (InvalidArgumentException e) {
                        logger.log(Level.INFO, "An error occurred while retrieving the map of people by course of session");
                    }

                    //Avail the map of people by faculty in the session
                    logger.log(Level.INFO, "Avail the map of people by faculty in the session");
                    session.setAttribute("personByCourseOfSessionMap", personByCourseOfSessionMap);

                    //Retrieve the faculty unique identifier if any
                    logger.log(Level.INFO, "Retrieving the faculty unique identifier if any");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (Exception e) {
                        faculty = null;
                        logger.log(Level.INFO, "Faculty unique identifier is not provided");
                    }

                    //Retrieve the department unique identifier if any
                    logger.log(Level.INFO, "Retrieving the department unique identifier if any");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (Exception e) {
                        department = null;
                        logger.log(Level.INFO, "Department unique identifier is not provided");
                    }

                    //Retrieve the faculty members who are not students
                    logger.log(Level.INFO, "Retrieving the faculty members who are not students");
                    List<FacultyMemberDetails> facultyMembers = new ArrayList<>();
                    try {
                        if (faculty != null) {
                            facultyMembers = facultyMemberService.retrieveNonStudentFacultyMembers(faculty);
                        } else if (department != null) {
                            facultyMembers = facultyMemberService.retrieveNonStudentFacultyMembers(department);
                        }
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred while retrieving the active evaluation session");
                    }

                    //Avail the non-student faculty members in the session
                    logger.log(Level.INFO, "Avail the non-student faculty members in the session");
                    session.setAttribute("facultyMembers", facultyMembers);

                    //Retrieve the map of people by faculty member
                    logger.log(Level.INFO, "Retrieving the map of people by faculty member");
                    Map<FacultyMemberDetails, PersonDetails> personByFacultyMemberMap = new HashMap<>();
                    try {
                        personByFacultyMemberMap = courseOfSessionService.retrievePersonByFacultyMember(facultyMembers);
                    } catch (InvalidArgumentException e) {
                        logger.log(Level.INFO, "An error occurred while retrieving the map of people by faculty");
                    }

                    //Avail the map of people by faculty in the session
                    logger.log(Level.INFO, "Avail the map of people by faculty in the session");
                    session.setAttribute("personByFacultyMemberMap", personByFacultyMemberMap);

                    //Read in the evaluation session unique identifier
                    logger.log(Level.INFO, "Reading in the evaluation session unique identifier");
                    degree = new DegreeDetails();
                    degree.setId(Integer.parseInt(request.getParameter("degreeId")));

                    //Retrieve the list of courses from the database
                    logger.log(Level.INFO, "Retrieving the list of courses from the database");
                    List<CourseDetails> courses = new ArrayList<>();
                    try {
                        courses = courseService.retrieveCoursesOfDegree(degree.getId());
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred while retrieving list of courses from the database");
                    }

                    //Avail the courses in the session
                    logger.log(Level.INFO, "Availing the courses in session");
                    session.setAttribute("courses", courses);

                    path = "/viewCoursesOfSession";
                    logger.log(Level.SEVERE, "Path is: {0}", path);

                    break;

                case "/addCourseOfSession":

                    //Read in the evaluation session unique identifier
                    logger.log(Level.INFO, "Reading in the evaluation session unique identifier");
                    evaluationSession = new EvaluationSessionDetails();
                    evaluationSession.setId(Integer.parseInt(request.getParameter("evaluationSessionId")));

                    //Read in the course unique identifier
                    logger.log(Level.INFO, "Reading in the course unique identifier");
                    course = new CourseDetails();
                    course.setId(Integer.parseInt(request.getParameter("courseId")));

                    //Read in the faculty member unique identifier
                    logger.log(Level.INFO, "Reading in the faculty member unique identifier");
                    facultyMember = new FacultyMemberDetails();
                    facultyMember.setId(Integer.parseInt(request.getParameter("facultyMemberId")));

                    //Read in posted values and filling them in a course of session details container
                    logger.log(Level.INFO, "Reading in posted values and filling them in a course of session details container");
                    courseOfSession = new CourseOfSessionDetails();
                    courseOfSession.setActive(true);
                    courseOfSession.setCourse(course);
                    courseOfSession.setFacultyMember(facultyMember);
                    courseOfSession.setEvaluationSession(evaluationSession);

                    //Send the course of session details to the entity manager
                    logger.log(Level.INFO, "Sending the course of session details to the entity manager");
                    try {
                        courseOfSessionService.addCourseOfSession(courseOfSession);
                    } catch (InvalidArgumentException e) {
                        logger.log(Level.SEVERE, "An error occurred during course of session record details submission");
                    }

                    //Retrieve the new list of courses of session
                    logger.log(Level.INFO, "Retrieving the new list of course of session records");
                    coursesOfSession = new ArrayList<>();
                    try {
                        coursesOfSession = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during retrieval of courses of session", e);
                    }

                    //Update the course of session records table
                    logger.log(Level.INFO, "Updating the course of session records table");
                    generateTableBody(coursesOfSession, response, request);

                    return;

                case "/editCourseOfSession":

                    //Read in the evaluation session unique identifier
                    logger.log(Level.INFO, "Reading in the evaluation session unique identifier");
                    evaluationSession = new EvaluationSessionDetails();
                    evaluationSession.setId(Integer.parseInt(request.getParameter("evaluationSessionId")));

                    //Read in the course unique identifier
                    logger.log(Level.INFO, "Reading in the course unique identifier");
                    course = new CourseDetails();
                    course.setId(Integer.parseInt(request.getParameter("courseId")));

                    //Read in the faculty member unique identifier
                    logger.log(Level.INFO, "Reading in the faculty member unique identifier");
                    facultyMember = new FacultyMemberDetails();
                    facultyMember.setId(Integer.parseInt(request.getParameter("facultyMemberId")));

                    //Read in posted values and filling them in a course of session details container
                    logger.log(Level.INFO, "Reading in posted values and filling them in a course of session details container");
                    courseOfSession = new CourseOfSessionDetails();
                    courseOfSession.setActive(true);
                    courseOfSession.setCourse(course);
                    courseOfSession.setFacultyMember(facultyMember);
                    courseOfSession.setEvaluationSession(evaluationSession);
                    courseOfSession.setId(Integer.parseInt(request.getParameter("courseOfSessionId")));

                    //Send the course of session details to the entity manager
                    logger.log(Level.INFO, "Sending the course of session details to the entity manager");
                    try {
                        courseOfSessionService.editCourseOfSession(courseOfSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during course of session record details submission");
                    }

                    //Retrieve the new list of courses of session
                    logger.log(Level.INFO, "Retrieving the new list of course of session records");
                    coursesOfSession = new ArrayList<>();
                    try {
                        coursesOfSession = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during retrieval of courses of session", e);
                    }

                    //Update the course of session records table
                    logger.log(Level.INFO, "Updating the course of session records table");
                    generateTableBody(coursesOfSession, response, request);

                    return;

                case "/removeCourseOfSession":

                    //Send the unique identifier to the entity manager for the courses of session to be removed
                    logger.log(Level.INFO, "Sending the unique identifier to the entity manager for the courses of session to be removed");
                    try {
                        courseOfSessionService.removeCourseOfSession(Integer.parseInt(request.getParameter("courseOfSessionId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during courses of session unique identifier submission", e);
                    }

                    //Retrieve the new list of courses of session
                    logger.log(Level.INFO, "Retrieving the new list of course of session records");
                    coursesOfSession = new ArrayList<>();
                    try {
                        coursesOfSession = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during retrieval of courses of session", e);
                    }

                    //Update the course of session records table
                    logger.log(Level.INFO, "Updating the course of session records table");
                    generateTableBody(coursesOfSession, response, request);

                    return;

            }

            destination = "/WEB-INF/views" + path + ".jsp";
            logger.log(Level.INFO, "Requesting dispatch to forward to: {0}", destination);
            try {
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.log(Level.SEVERE, "Request dispatch failed", e);
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
    private void generateTableBody(List<CourseOfSessionDetails> coursesOfSession, HttpServletResponse response, HttpServletRequest request) throws IOException {
        PrintWriter out = response.getWriter();
        short index = 0;

        //Retrieve the faculty unique identifier if any
        logger.log(Level.INFO, "Retrieving the faculty unique identifier if any");
        faculty = new FacultyDetails();
        try {
            faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
        } catch (Exception e) {
            faculty = null;
        }

        //Retrieve the department unique identifier if any
        logger.log(Level.INFO, "Retrieving the department unique identifier if any");
        department = new DepartmentDetails();
        try {
            department.setId(Integer.parseInt(request.getParameter("departmentId")));
        } catch (Exception e) {
            department = null;
        }

        //Retrieve the map of people by courses of session
        logger.log(Level.INFO, "Retrieving the map of people by courses of session");
        Map<CourseOfSessionDetails, PersonDetails> personByCourseOfSessionMap = new HashMap<>();
        try {
            personByCourseOfSessionMap = courseOfSessionService.retrievePersonByCourseOfSession(coursesOfSession);
        } catch (InvalidArgumentException e) {
            logger.log(Level.INFO, "An error occurred while retrieving the map of people by course of session");
        }

        for (CourseOfSessionDetails c : coursesOfSession) {
            if (index % 2 == 0) {
                out.write("<tr class=\"even\" >");
            } else {
                out.write("<tr>");
            }
            out.write("<td>" + ++index + "</td>");
            out.write("<td>" + c.getCourse().getCode() + " - " + c.getCourse().getTitle() + "</td>");
            out.write("<td>" + personByCourseOfSessionMap.get(c).getReferenceNumber() + " - " + personByCourseOfSessionMap.get(c).getFirstName() + " " + personByCourseOfSessionMap.get(c).getLastName() + "</td>");
            out.write("<td><button onclick=\"editCourseOfSession('" + c.getId() + "', '" + c.getFacultyMember().getId() + "', '" + c.getCourse().getId() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write("<td><button onclick=\"removeCourseOfSession('" + c.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(CourseOfSessionController.class.getSimpleName());

}
