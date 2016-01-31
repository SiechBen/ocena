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
import java.util.Locale;
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
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "CollegeController", urlPatterns = {"/addCollege", "/retrieveColleges", "/editCollege", "/removeCollege"})
public class CollegeController extends Controller {

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

        Locale locale = request.getLocale();
        bundle = ResourceBundle.getBundle("text", locale);

        boolean adminSession;
        try {
            adminSession = (Boolean) session.getAttribute("mainAdminSession");
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

            String path = (String) session.getAttribute("home");
            logger.log(Level.INFO, "Path is: {0}", path);
            String destination = "/WEB-INF/views" + path + ".jsp";
            try {
                logger.log(Level.INFO, "Dispatching request to: {0}", destination);
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.log(Level.INFO, "Request dispatch failed");
            }

        } else if (adminSession == true) {
            //Admin session established
            logger.log(Level.INFO, "Admin session established hence responding to the request");

            String path = request.getServletPath();
            String destination;
            List<CollegeDetails> colleges;
            institution = new InstitutionDetails();
            institution = (InstitutionDetails) getServletContext().getAttribute("institution");

            switch (path) {

                case "/addCollege":

                    //Read in posted values and filling them in a college details container
                    logger.log(Level.INFO, "Reading in posted values and filling them in a college details container");
                    college = new CollegeDetails();
                    college.setActive(true);
                    college.setInstitution(institution);
                    college.setName(request.getParameter("name"));
                    college.setAbbreviation(request.getParameter("abbreviation"));

                    //Send the college details to the entity manager
                    logger.log(Level.INFO, "Sending the college details to the entity manager");
                    try {
                        collegeService.addCollege(college);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of colleges
                    logger.log(Level.INFO, "Retrieving the new list of college records");
                    colleges = new ArrayList<>();
                    try {
                        colleges = collegeService.retrieveColleges();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the college records table
                    logger.log(Level.INFO, "Updating the college records table");
                    generateTableBody(colleges, response);
                    return;

                case "/retrieveColleges":
                    //Avail the colleges in the session
                    logger.log(Level.INFO, "Avail the colleges in the session");
                    colleges = new ArrayList<>();
                    try {
                        colleges = collegeService.retrieveColleges();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    session.setAttribute("colleges", colleges);
                    path = "/viewColleges";
                    logger.log(Level.SEVERE, "Path is: {0}", path);

                    break;

                case "/editCollege":

                    //Read in posted values and filling them in a college details container
                    logger.log(Level.INFO, "Reading in posted values and filling them in a college details container");
                    college = new CollegeDetails();
                    college.setActive(true);
                    college.setInstitution(institution);
                    college.setName(request.getParameter("name"));
                    college.setId(Integer.parseInt(request.getParameter("id")));
                    college.setAbbreviation(request.getParameter("abbreviation"));

                    //Send the college details to the entity manager
                    logger.log(Level.INFO, "Sending the college details to the entity manager");
                    try {
                        collegeService.editCollege(college);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of colleges
                    logger.log(Level.INFO, "Retrieving the new list of college records");
                    colleges = new ArrayList<>();
                    try {
                        colleges = collegeService.retrieveColleges();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the college records table
                    logger.log(Level.INFO, "Updating the college records table");
                    generateTableBody(colleges, response);
                    return;

                case "/removeCollege":

                    //Send the unique identifier to the entity manager for the college to be removed
                    logger.log(Level.INFO, "Sending the unique identifier to the entity manager for the college to be removed");
                    try {
                        collegeService.removeCollege(Integer.parseInt(request.getParameter("id")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of colleges
                    logger.log(Level.INFO, "Retrieving the new list of college records");
                    colleges = new ArrayList<>();
                    try {
                        colleges = collegeService.retrieveColleges();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the college records table
                    logger.log(Level.INFO, "Updating the college records table");
                    generateTableBody(colleges, response);
                    return;

            }

            destination = "/WEB-INF/views" + path + ".jsp";
            logger.log(Level.INFO, "Requesting dispatch to forward to: {0}", destination);
            try {
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString("redirection_failed"));
                logger.log(Level.INFO, bundle.getString("redirection_failed"), e);
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
    private void generateTableBody(List<CollegeDetails> colleges, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        short index = 0;
        for (CollegeDetails c : colleges) {
            if (index % 2 == 0) {
                out.write("<tr class=\"even\" >");
            } else {
                out.write("<tr>");
            }
            out.write("<td onclick=\"loadWindow('/Ocena/retrieveFaculties?collegeId=" + c.getId() + "')\">" + ++index + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/retrieveFaculties?collegeId=" + c.getId() + "')\">" + c.getName() + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/retrieveFaculties?collegeId=" + c.getId() + "')\">" + c.getAbbreviation() + "</td>");
            out.write("<td><button onclick=\"editCollege('" + c.getId() + "', '" + c.getName() + "', '" + c.getAbbreviation() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write("<td><button onclick=\"removeCollege('" + c.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(CollegeController.class.getSimpleName());

}
