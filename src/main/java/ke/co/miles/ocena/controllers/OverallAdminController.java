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
import ke.co.miles.ocena.exceptions.AlgorithmException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.OverallAdminDetails;

/**
 *
 * @author siech
 */
@WebServlet(name = "OverallAdminController", urlPatterns = {"/addOverallAdmin", "/editOverallAdminCredentials", "/deleteOverallAdmin", "/viewCredentials", "/validateOverallAdminPassword"})
public class OverallAdminController extends Controller {

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
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();

        //Get the user's locale and the associated resource bundle
        Locale locale = request.getLocale();
        bundle = ResourceBundle.getBundle("text", locale);

        boolean adminSession;
        try {
            adminSession = (boolean) session.getAttribute("mainAdminSession");
        } catch (Exception e) {
            LOGGER.log(Level.INFO, "Admin session is null");
            LOGGER.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //Check session type
        if (!adminSession) {
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

        } else {
            //Admin session established
            LOGGER.log(Level.INFO, "Admin session established hence responding to the request");

            String path = request.getServletPath();

            String destination;

            switch (path) {
                case "/viewCredentials":
                    LOGGER.log(Level.INFO, "Viewing admin user credentials");

                    break;

                case "/validateOverallAdminPassword":
                    //Retrieve the person's user account
                    LOGGER.log(Level.INFO, "Retrieving the person's user account");
                    overallAdminDetails = new OverallAdminDetails();
                    overallAdminDetails = (OverallAdminDetails) session.getAttribute("adminCredentials");

                    MessageDigest messageDigest;
                    //Create a message digest algorithm for SHA-256 hashing algorithm
                    LOGGER.log(Level.INFO, "Creating a message digest hashing algorithm object");
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString("error_007_01"));
                        LOGGER.log(Level.INFO, bundle.getString("error_007_01"));
                        break;
                    }

                    //Read in the entered password
                    LOGGER.log(Level.INFO, "Reading in the entered password");
                    String oldOverallAdminPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("password"));

                    //Check validity of the entered password
                    LOGGER.log(Level.INFO, "Checking validity of the entered password");
                    if (oldOverallAdminPassword.equals(overallAdminDetails.getPassword())) {
                        //Password is valid
                        LOGGER.log(Level.INFO, "Old password is valid");
                        out.write("");
                    } else {
                        //Password is invalid
                        LOGGER.log(Level.INFO, "Old password is invalid");
                        out.write("<span class=\"btn btn-warning\">Wrong password entered!</span>");
                    }

                    return;

                case "/addOverallAdmin":
                    break;
                case "/editOverallAdminCredentials":

                    //Create an overall admin details object
                    LOGGER.log(Level.INFO, "Creating an overall admin details object");
                    overallAdminDetails = (OverallAdminDetails) session.getAttribute("adminCredentials");
                    overallAdminDetails.setUsername(request.getParameter("overall-admin-username"));

                    //Create a message digest algorithm for SHA-256 hashing algorithm
                    LOGGER.log(Level.INFO, "Creating a message digest hashing algorithm object");
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString("error_007_01"));
                        LOGGER.log(Level.INFO, bundle.getString("error_007_01"));
                        break;
                    }

                    //Read in the old entered password
                    LOGGER.log(Level.INFO, "Reading in the old entered password");
                    oldOverallAdminPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("old-overall-admin-password"));

                    //Check validity of the entered old password
                    LOGGER.log(Level.INFO, "Checking validity of the entered old password");
                    if (oldOverallAdminPassword.equals(overallAdminDetails.getPassword())) {
                        //Password is valid
                        LOGGER.log(Level.INFO, "Old password is valid");
                        String newPassword = request.getParameter("new-overall-admin-password");
                        String confirmationPassword = request.getParameter("confirm-overall-admin-password");
                        if (newPassword != null && newPassword.trim().length() > 0) {
                            if (newPassword.equals(confirmationPassword)) {
                                overallAdminDetails.setPassword(newPassword);
                            } else {
                                //Password is invalid
                                LOGGER.log(Level.INFO, "Passwords do not match");
                            }
                        } else {
                            //Password is invalid
                            LOGGER.log(Level.INFO, "Old password is unchanged");
                        }
                    } else {
                        //Password is invalid
                        LOGGER.log(Level.INFO, "Old password is invalid");
                    }

                    try {
                        overallAdminService.editOverallAdmin(overallAdminDetails);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                        return;
                    }

                    path = (String) session.getAttribute("home");
                    break;
                case "/deleteOverallAdmin":
                    break;
            }

            destination = "/WEB-INF/views" + path + ".jsp";
            LOGGER.log(Level.INFO, "Path is {0}", destination);
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

    private static final Logger LOGGER = Logger.getLogger(OverallAdminController.class.getSimpleName());
}
