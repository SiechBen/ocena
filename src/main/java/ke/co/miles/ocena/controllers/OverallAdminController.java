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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ke.co.miles.ocena.defaults.Controller;
import ke.co.miles.ocena.entities.OverallAdmin;
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

        boolean adminSession;
        try {
            adminSession = (boolean) session.getAttribute("mainAdminSession");
        } catch (Exception e) {
            logger.log(Level.INFO, "Admin session is null");
            logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //Check session type
        if (!adminSession) {
            //Admin session not established
            logger.log(Level.INFO, "Admin session not established hence not responding to the request");
        } else {
            //Admin session established
            logger.log(Level.INFO, "Admin session established hence responding to the request");

            String path = request.getServletPath();

            String destination;

            switch (path) {
                case "/viewCredentials":
                    logger.log(Level.INFO, "Viewing admin user credentials");

                    break;

                case "/validateOverallAdminPassword":
                    //Retrieve the person's user account
                    logger.log(Level.INFO, "Retrieving the person's user account");
                    overallAdminDetails = new OverallAdminDetails();
                    overallAdminDetails = (OverallAdminDetails) session.getAttribute("adminCredentials");

                    MessageDigest messageDigest;
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
                    String oldOverallAdminPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("password"));

                    //Check validity of the entered password
                    logger.log(Level.INFO, "Checking validity of the entered password");
                    if (oldOverallAdminPassword.equals(overallAdminDetails.getPassword())) {
                        //Password is valid
                        logger.log(Level.INFO, "Old password is valid");
                        out.write("");
                    } else {
                        //Password is invalid
                        logger.log(Level.INFO, "Old password is invalid");
                        out.write("<span class=\"btn btn-warning\">Wrong password entered!</span>");
                    }

                    return;

                case "/addOverallAdmin":
                    break;
                case "/editOverallAdminCredentials":

                    //Create an overall admin details object
                    logger.log(Level.INFO, "Creating an overall admin details object");
                    overallAdminDetails = (OverallAdminDetails) session.getAttribute("adminCredentials");
                    overallAdminDetails.setUsername(request.getParameter("overall-admin-username"));

                    //Create a message digest algorithm for SHA-256 hashing algorithm
                    logger.log(Level.INFO, "Creating a message digest hashing algorithm object");
                    try {
                        messageDigest = MessageDigest.getInstance("SHA-256");
                    } catch (NoSuchAlgorithmException e) {
                        logger.log(Level.INFO, "An error occurred while finding the hashing algorithm");
                        break;
                    }

                    //Read in the old entered password
                    logger.log(Level.INFO, "Reading in the old entered password");
                    oldOverallAdminPassword = accessService.generateSHAPassword(messageDigest, request.getParameter("old-overall-admin-password"));

                    //Check validity of the entered old password
                    logger.log(Level.INFO, "Checking validity of the entered old password");
                    if (oldOverallAdminPassword.equals(overallAdminDetails.getPassword())) {
                        //Password is valid
                        logger.log(Level.INFO, "Old password is valid");
                        String newPassword = request.getParameter("new-overall-admin-password");
                        String confirmationPassword = request.getParameter("confirm-overall-admin-password");
                        if (newPassword != null && newPassword.trim().length() > 0) {
                            if (newPassword.equals(confirmationPassword)) {
                                overallAdminDetails.setPassword(newPassword);
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

                    try {
                        overallAdminService.editOverallAdmin(overallAdminDetails);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "Overall admin credentia;s could not be edited");
                    }

                    path = (String) session.getAttribute("home");
                    break;
                case "/deleteOverallAdmin":
                    break;
            }

            destination = "/WEB-INF/views" + path + ".jsp";
            logger.log(Level.INFO, "Path is {0}", destination);
            try {
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.log(Level.INFO, "Forward dispatch could not be accomplished");
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

    private static final Logger logger = Logger.getLogger(OverallAdminController.class.getSimpleName());
}
