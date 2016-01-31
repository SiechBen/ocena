/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
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
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "RatingController", urlPatterns = {"/addRating", "/editRating", "/removeRating"})
public class RatingController extends Controller {

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
            Map<RatingTypeDetail, List<RatingDetails>> ratingTypeAndValuesMap = new HashMap<>();

            switch (path) {
                case "/addRating":

                    //Read in details for the rating
                    logger.log(Level.INFO, "Reading in details for the rating");
                    rating = new RatingDetails();
                    rating.setActive(true);
                    rating.setRatingType(RatingTypeDetail.getRatingType(Short.parseShort(request.getParameter("ratingType"))));
                    if (rating.getRatingType().equals(RatingTypeDetail.PERCENTAGE)) {
                        rating.setRating(request.getParameter("ratingValue") + "%");
                    } else {
                        rating.setRating(request.getParameter("ratingValue"));
                    }

                    //Send the details to the entity manager for recording in the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for recording in the database");
                    try {
                        ratingService.addRating(rating);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of rating records from the database
                    logger.log(Level.INFO, "Retrieving the new list of rating records from the database");
                    try {
                        ratingTypeAndValuesMap = ratingService.retrieveRatings();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail the ratings session
                    logger.log(Level.INFO, "Availing the ratings in session");
                    session.setAttribute("ratingTypeAndValuesMap", ratingTypeAndValuesMap);

                    //Display the new list of rating records
                    generateTableBody(ratingTypeAndValuesMap, session, response);
                    return;

                case "/editRating":

                    //Read in details for the rating
                    logger.log(Level.INFO, "Reading in details for the rating");
                    rating = new RatingDetails();
                    rating.setActive(true);
                    rating.setId(Short.parseShort(request.getParameter("ratingId")));
                    rating.setRatingType(RatingTypeDetail.getRatingType(Short.parseShort(request.getParameter("ratingType"))));
                    if (rating.getRatingType().equals(RatingTypeDetail.PERCENTAGE)) {
                        rating.setRating(request.getParameter("ratingValue") + "%");
                    } else {
                        rating.setRating(request.getParameter("ratingValue"));
                    }

                    //Send the details to the entity manager for recording in the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for record update in the database");
                    try {
                        ratingService.editRating(rating);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of rating records from the database
                    logger.log(Level.INFO, "Retrieving the new list of rating records from the database");
                    try {
                        ratingTypeAndValuesMap = ratingService.retrieveRatings();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Display the new list of rating records
                    generateTableBody(ratingTypeAndValuesMap, session, response);
                    return;

                case "/removeRating":

                    //Send the details to the entity manager for record removal from the database
                    logger.log(Level.INFO, "Sending the details to the entity manager for record removal from the database");
                    try {
                        ratingService.removeRating(Short.parseShort(request.getParameter("ratingId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of rating records from the database
                    logger.log(Level.INFO, "Retrieving the new list of rating records from the database");
                    try {
                        ratingTypeAndValuesMap = ratingService.retrieveRatings();
                    } catch (InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        logger.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Display the new list of rating records
                    generateTableBody(ratingTypeAndValuesMap, session, response);

                    return;
            }
            destination = "WEB-INF/views" + path + ".jsp";
            try {
                logger.log(Level.INFO, "Dispatching request to: {0}", destination);
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
    private void generateTableBody(Map<RatingTypeDetail, List<RatingDetails>> ratingTypeAndValuesMap, HttpSession session, HttpServletResponse response) throws IOException {
        int index;
        PrintWriter out = response.getWriter();
        for (RatingTypeDetail type : ratingTypeAndValuesMap.keySet()) {
            index = 0;
            out.write("<tr>");
            out.write("<td> &nbsp; </td>");
            out.write("<td colspan=\"4\"> <strong> " + type.getRatingType() + " rating </strong> </td>");
            out.write("</tr>");
            for (RatingDetails r : ratingTypeAndValuesMap.get(type)) {
                out.write("<tr>");
                out.write("<td> &nbsp; </td>");
                out.write("<td> " + ++index + " </td>");
                out.write("<td> " + r.getRating() + " </td>");
                out.write("<td><button onclick=\"editRatingValue('" + r.getId() + "', '" + r.getRating() + "', '" + type.getId() + "')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                out.write("<td><button onclick=\"removeRatingValue('" + r.getId() + "')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                out.write("</tr>");
            }
        }
    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(RatingController.class.getSimpleName());

}
