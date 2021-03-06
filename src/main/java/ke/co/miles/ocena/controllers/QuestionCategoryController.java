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
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "QuestionCategoryController", urlPatterns = {"/addQuestionCategory", "/retrieveQuestionCategory", "/editQuestionCategory", "/removeQuestionCategory"})
public class QuestionCategoryController extends Controller {

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
            List<QuestionCategoryDetails> questionCategories = new ArrayList<>();

            switch (path) {
                case "/addQuestionCategory":

                    //Read in details for the question category
                    LOGGER.log(Level.INFO, "Reading in details for the question category");
                    questionCategory = new QuestionCategoryDetails();
                    questionCategory.setActive(true);
                    questionCategory.setCategory(request.getParameter("questionCategory"));

                    //Send the details to the entity manager for recording in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for recording in the database");
                    try {
                        questionCategoryService.addQuestionCategory(questionCategory);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of question category records from the database
                    LOGGER.log(Level.INFO, "Retrieving the new list of question category records from the database");
                    try {
                        questionCategories = questionCategoryService.retrieveQuestionCategories();
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail the question categories session
                    LOGGER.log(Level.INFO, "Availing the question categories in session");
                    session.setAttribute("questionCategories", questionCategories);

                    //Display the new list of question category records
                    generateTableBody(questionCategories, session, response);
                    return;

                case "/editQuestionCategory":

                    //Read in details for the question category
                    LOGGER.log(Level.INFO, "Reading in details for the question category");
                    questionCategory = new QuestionCategoryDetails();
                    questionCategory.setActive(true);
                    questionCategory.setCategory(request.getParameter("questionCategory"));
                    questionCategory.setId(Short.parseShort(request.getParameter("questionCategoryId")));

                    //Send the details to the entity manager for recording in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record update in the database");
                    try {
                        questionCategoryService.editQuestionCategory(questionCategory);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of question category records from the database
                    LOGGER.log(Level.INFO, "Retrieving the new list of question category records from the database");
                    try {
                        questionCategories = questionCategoryService.retrieveQuestionCategories();
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Display the new list of question category records
                    generateTableBody(questionCategories, session, response);

                    return;

                case "/removeQuestionCategory":

                    //Send the details to the entity manager for record removal from the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record removal from the database");
                    try {
                        questionCategoryService.removeQuestionCategory(Short.parseShort(request.getParameter("questionCategoryId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of question category records from the database
                    LOGGER.log(Level.INFO, "Retrieving the new list of question category records from the database");
                    try {
                        questionCategories = questionCategoryService.retrieveQuestionCategories();
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Display the new list of question category records
                    generateTableBody(questionCategories, session, response);

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

    //<editor-fold defaultstate="collapsed" desc="Generate table body">
    private void generateTableBody(List<QuestionCategoryDetails> questionCategories, HttpSession session, HttpServletResponse response) throws IOException {
        int index = 0;
        PrintWriter out = response.getWriter();
        for (QuestionCategoryDetails qc : questionCategories) {
            out.write("<tr>");
            out.write("<td> " + ++index + " </td>");
            out.write("<td> " + qc.getCategory() + " </td>");
            out.write("<td><button onclick=\"editQuestionCategory('" + qc.getId() + "', '" + qc.getCategory() + "')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
            out.write("<td><button onclick=\"removeQuestionCategory('" + qc.getId() + "')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(QuestionCategoryController.class.getSimpleName());

}
