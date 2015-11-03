/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "InstitutionController", urlPatterns = {"/institution", "/addInstitution", "/editInstitution", "/removeInstitution"})
public class InstitutionController extends Controller {

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

            switch (path) {
                case "/institution":
                    //Retrieve the institution
                    logger.log(Level.INFO, "Retrieving the institution");
                    institution = (InstitutionDetails) getServletContext().getAttribute("institution");

                    //View the institution
                    logger.log(Level.INFO, "View the institution");

                    if (institution != null) {
                        path = "/viewInstitution";
                        logger.log(Level.INFO, "Path is: {0}", path);
                        break;
                    }

                    path = "/addInstitution";
                    break;
                case "/addInstitution":

                    //Read in and set institution details 
                    logger.log(Level.INFO, "Reading in and setting the institution details");
                    country = new CountryDetails();
                    country.setId(Integer.parseInt(request.getParameter("country")));

                    institution = new InstitutionDetails();
                    institution.setAbbreviation(request.getParameter("abbreviation"));
                    institution.setName(request.getParameter("institution-name"));
                    institution.setCountry(country);
                    institution.setActive(true);
                    try {
                        institutionService.addInstitution(institution);
                    } catch (Exception e) {
                        logger.log(Level.INFO, "Institution record creation failed", e);
                        return;
                    }
                    try {
                        institution = institutionService.retrieveInstitution();
                        getServletContext().setAttribute("institution", institution);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "Institution record retrieval failed", e);
                        return;
                    }

                    path = "/viewInstitution";

                    break;

                case "/editInstitution":

                    //Read in posted values and filling them in a institution details container
                    logger.log(Level.INFO, "Reading in posted values and filling them in a institution details container");
                    country = new CountryDetails();
                    country.setId(Integer.parseInt(request.getParameter("country")));

                    institution = new InstitutionDetails();
                    institution = (InstitutionDetails) getServletContext().getAttribute("institution");

                    institution.setActive(true);
                    institution.setCountry(country);
                    institution.setId(institution.getId());
                    institution.setName(request.getParameter("name"));
                    institution.setAbbreviation(request.getParameter("abbreviation"));

                    //Send the institution details to the entity manager
                    logger.log(Level.INFO, "Sending the institution details to the entity manager");
                    try {
                        institutionService.editInstitution(institution);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during institution record details submission", e);
                    }

                    //Retrieve the new institution details
                    logger.log(Level.INFO, "Retrieving the new institution details");
                    institution = new InstitutionDetails();
                    try {
                        institution = institutionService.retrieveInstitution();
                        getServletContext().setAttribute("institution", institution);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during retrieval of institutions", e);
                    }

                    //Update the institution table
                    logger.log(Level.INFO, "Updating the institution table");
                    generateTableBody(institution, response);
                    return;

                case "/removeInstitution":
                    //Read in the institution record to be deleted
                    logger.log(Level.INFO, "Reading in the institution record to be deleted");
                    institution = new InstitutionDetails();
                    institution = (InstitutionDetails) getServletContext().getAttribute("institution");

                    //Send the unique identifier to the entity manager
                    logger.log(Level.INFO, "Sending the unique identifier to the entity manager");
                    try {
                        institutionService.removeInstitution(institution.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during institution unique identifier submission", e);
                    }

                    //Retrieve the new institution details
                    logger.log(Level.INFO, "Retrieving the new institution details");
                    institution = new InstitutionDetails();
                    try {
                        institution = institutionService.retrieveInstitution();
                        if (institution.getId() == null) {
                            path = "/addInstitution";
                            logger.log(Level.INFO, "No institution record hence adding one.\nPath is: {0}", path);
                            break;
                        }
                        getServletContext().setAttribute("institution", institution);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.SEVERE, "An error occurred during retrieval of institutions", e);
                    }

                    //Update the institution table
                    logger.log(Level.INFO, "Updating the institution table");
                    generateTableBody(institution, response);
                    return;
            }

            destination = "/WEB-INF/views" + path + ".jsp";
            logger.log(Level.INFO, "Request dispatch to forward to: {0}", destination);
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

    //<editor-fold defaultstate="collapsed" desc="Generate body content">
    private void generateTableBody(InstitutionDetails institution, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();

        out.write("<h1>" + institution.getName() + "</h1>");
        out.write("<table id=\"institution-table\">");
        out.write("<thead>");
        out.write("<tr>");
        out.write("<th colspan=\"3\"></th>");
        out.write("</tr>");
        out.write("</thead>");
        out.write("<tfoot>");
        out.write("<tr colspan=\"3\">");
        out.write("<td>" + institution.getAbbreviation() + " is a world class University</td>");
        out.write("</tr>");
        out.write("</tfoot>");
        out.write("<tbody>");
        out.write("<tr>");
        out.write("<td>Name:</td>");
        out.write("<td>" + institution.getName() + "</td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>Abbreviation:</td>");
        out.write("<td>" + institution.getAbbreviation() + "</td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td>Country:</td>");
        out.write("<td>" + institution.getCountry().getName() + "</td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<td><button onclick=\"editInstitution('" + institution.getName() + "', '" + institution.getAbbreviation() + "', '" + institution.getCountry().getId() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button> Edit institution details</td>");
        out.write("<td><button onclick=\"removeInstitution()\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button> Remove the institution</td>");
        out.write("</tr>");
        out.write("</tbody>");
        out.write("</table>");
    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(InstitutionController.class.getSimpleName());
}
