/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.CourseDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "CourseController", urlPatterns = {"/addCourse", "/retrieveCourses", "/editCourse", "/removeCourse"})
public class CourseController extends Controller {

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

            switch (path) {
                case "/addCourse":

                    //Retrieve the specific degree record
                    logger.log(Level.INFO, "Reading the specific degree record");
                    degree = new DegreeDetails();
                    try {
                        degree = degreeService.retrieveDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during record retrieval");
                    }

                    //Read in details for the course
                    logger.log(Level.INFO, "Reading in details for the course");
                    course = new CourseDetails();
                    course.setActive(true);
                    course.setDegree(degree);
                    course.setCode(request.getParameter("courseCode"));
                    course.setTitle(request.getParameter("courseTitle"));

                    //Send the details to the entity manager for recording in the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for recording in the database");
                    try {
                        courseService.addCourse(course);
                    } catch (InvalidArgumentException e) {
                        logger.log(Level.INFO, "An error occurred during course record creation");
                    }

                    //Avail records in the session and
                    //Display the new list of course records
                    generateTableBodyAndAvailOtherRecords(session, response, degree);

                    return;

                case "/retrieveCourses":

                    //Retrieve the specific degree record
                    logger.log(Level.INFO, "Reading the specific degree record");
                    degree = new DegreeDetails();
                    try {
                        degree = degreeService.retrieveDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during record retrieval");
                    }

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

                    //Avail the degree in the session
                    logger.log(Level.INFO, "Availing the degree in session");
                    session.setAttribute("degree", degree);

                    //Set path to view courses
                    path = "/viewCourses";
                    logger.log(Level.INFO, "Path is: {0}", path);

                    break;

                case "/editCourse":

                    //Retrieve the specific degree record
                    logger.log(Level.INFO, "Reading the specific degree record");
                    degree = new DegreeDetails();
                    try {
                        degree = degreeService.retrieveDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during record retrieval");
                    }

                    //Read in details for the course
                    logger.log(Level.INFO, "Reading in details for the course");
                    course = new CourseDetails();
                    course.setActive(true);
                    course.setDegree(degree);
                    course.setCode((request.getParameter("courseCode")));
                    course.setTitle(request.getParameter("courseTitle"));
                    course.setId(Integer.parseInt(request.getParameter("courseId")));

                    //Send the details to the entity manager for record update in the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for record update in the database");
                    try {
                        courseService.editCourse(course);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred during course record update");
                    }

                    //Avail records in the session and
                    //Display the new list of course records
                    generateTableBodyAndAvailOtherRecords(session, response, degree);
                    return;

                case "/removeCourse":

                    //Retrieve the specific degree record
                    logger.log(Level.INFO, "Reading the specific degree record");
                    degree = new DegreeDetails();
                    try {
                        degree = degreeService.retrieveDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during record retrieval");
                    }

                    //Send the details to the entity manager for record removal from the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for record removal from the database");
                    try {
                        courseService.removeCourse(Integer.parseInt(request.getParameter("courseId")));
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred during course record removal");
                    }

                    //Avail other required records in the session and
                    //Display the new list of course records
                    generateTableBodyAndAvailOtherRecords(session, response, degree);
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

    //<editor-fold defaultstate="collapsed" desc="Retrieve other required records and generate table body">
    private void generateTableBodyAndAvailOtherRecords(HttpSession session, HttpServletResponse response, DegreeDetails degree) throws IOException {

        //<editor-fold defaultstate="collapsed" desc="Avail other records in session"> 
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

        //Avail the degree in the session
        logger.log(Level.INFO, "Availing the degree in session");
        session.setAttribute("degree", degree);

        //<editor-fold defaultstate="collapsed" desc="Generate table body">
        int index = 0;
        PrintWriter out = response.getWriter();
        for (CourseDetails c : courses) {
            out.write("<tr>");
            out.write("<td> " + ++index + "</td>");
            out.write("<td> " + c.getTitle() + "</td>");
            out.write("<td> " + c.getCode() + "</td>");
            out.write("<td> <button onclick=\"editCourse('" + c.getId() + "','" + c.getTitle() + "','" + c.getCode() + "','" + degree.getId() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write("<td> <button onclick=\"removeCourse('" + c.getId() + "','" + degree.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
        //</editor-fold>
    }
//</editor-fold>   

    private static final Logger logger = Logger.getLogger(CourseController.class.getSimpleName());

}
