/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import ke.co.miles.ocena.defaults.Controller;
import javax.servlet.http.HttpServletResponse;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "AdmissionController", urlPatterns = {"/addAdmission", "/retrieveAdmissions", "/editAdmission", "/removeAdmission"})
public class AdmissionController extends Controller {

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

            switch (path) {

                case "/addAdmission":

                    //Read in posted values and filling them in an admission details container
                    LOGGER.log(Level.INFO, "Reading in posted values and filling them in an admission details container");
                    admission = new AdmissionDetails();
                    admission.setActive(true);
                    admission.setAdmission(request.getParameter("admission"));

                    //Send the admission details to the entity manager for record creation
                    LOGGER.log(Level.INFO, "Sending the admission details to the entity manager for record creation");
                    try {
                        admissionService.addAdmission(admission);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the admission records table
                    LOGGER.log(Level.INFO, "Updating the admission records table");
                    generateTableBody(response);

                    return;

                case "/retrieveAdmissions":
                    //Retrieve a list admissions from the database
                    LOGGER.log(Level.INFO, "Retrieving a list admissions from the database");
                    List admissions = new ArrayList<>();
                    try {
                        admissions = admissionService.retrieveAdmissions();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail the admissions in the session
                    LOGGER.log(Level.INFO, "Avail the admissions in the session");
                    session.setAttribute("admissions", admissions);
                    path = "/viewAdmissions";
                    LOGGER.log(Level.SEVERE, "Path is: {0}", path);

                    break;

                case "/editAdmission":

                    //Read in posted values and filling them in an admission details container
                    LOGGER.log(Level.INFO, "Reading in posted values and filling them in an admission details container");
                    admission = new AdmissionDetails();
                    admission.setActive(true);
                    admission.setAdmission(request.getParameter("admission"));
                    admission.setId(Integer.parseInt(request.getParameter("admissionId")));

                    //Send the admission details to the entity manager for record update
                    LOGGER.log(Level.INFO, "Sending the admission details to the entity manager for record update");
                    try {
                        admissionService.editAdmission(admission);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the admission records table
                    LOGGER.log(Level.INFO, "Updating the admission records table");
                    generateTableBody(response);
                    return;

                case "/removeAdmission":

                    //Send the unique identifier to the entity manager for the admission to be removed
                    LOGGER.log(Level.INFO, "Sending the unique identifier to the entity manager for the admission to be removed");
                    try {
                        admissionService.removeAdmission(Integer.parseInt(request.getParameter("admissionId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update the admission records table
                    LOGGER.log(Level.INFO, "Updating the admission records table");
                    generateTableBody(response);
                    return;

            }

            destination = "/WEB-INF/views" + path + ".jsp";
            LOGGER.log(Level.INFO, "Requesting dispatch to forward to: {0}", destination);
            try {
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

    //<editor-fold defaultstate="collapsed" desc="Generate table body">
    private void generateTableBody(HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        short index = 0;
        List<AdmissionDetails> admissions = new ArrayList<>();

        //Retrieve the list of admissions
        LOGGER.log(Level.INFO, "Retrieving the new list of admission records");
        try {
            admissions = admissionService.retrieveAdmissions();
        } catch (InvalidStateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
        }

        //Generate table body
        LOGGER.log(Level.INFO, "Returning the table body in the response");
        for (AdmissionDetails a : admissions) {
            out.write("<tr>");
            out.write("<td>" + ++index + "</td>");
            out.write("<td>" + a.getAdmission() + "</td>");
            out.write("<td><button onclick=\"editAdmission('" + a.getId() + "', '" + a.getAdmission() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write("<td><button onclick=\"removeAdmission('" + a.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(AdmissionController.class.getSimpleName());

}
