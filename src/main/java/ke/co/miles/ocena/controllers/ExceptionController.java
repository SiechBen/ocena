/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import ke.co.miles.ocena.defaults.Controller;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "ExceptionController", urlPatterns = {"/test"})
public class ExceptionController extends Controller {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processError(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();
        PrintWriter out = response.getWriter();
        String destination;

        Locale locale = request.getLocale();
        bundle = ResourceBundle.getBundle("text", locale);

        switch (path) {
            //Test if the path can be called
            case "/test":
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString("test_success"));
                logger.log(Level.INFO, bundle.getString("test_success"));
                return;
        }

        destination = "/WEB-INF/views" + path + ".jsp";
        logger.log(Level.INFO, "Request dispatch to forward to: {0}", destination);
        try {
            request.getRequestDispatcher(destination).forward(request, response);
        } catch (ServletException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString("redirection_failed"));
            logger.log(Level.INFO, bundle.getString("redirection_failed"), e);
        }

        //Analyze the servlet error by getting the error details
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        //Give the servlet a name if not named 
        if (servletName == null) {
            servletName = "Unkown";
        }

        String requestUri = (String) request.getAttribute("javax.servlet.error.request-uri");
        //If no uri was requested
        if (requestUri == null) {
            requestUri = "Unkown";
        }

        //Print out the error report
        out.write("<html><head><title>Exception/Error Details</title></head><body>");
        if (statusCode != 500) {
            out.write("<h3> Error details </h3>");
            out.write("<strong> Statuc code</strong>: " + statusCode);
            out.write("<strong> Requested uri</strong>: " + requestUri);
        } else {
            out.write("<h3> Exception details </h3>");
            out.write("<ul> Servlet name: </ul>" + servletName);
            out.write("<li> Exception name: </li>" + throwable.getClass().getName());
            out.write("<li> Requested uri: </li>" + requestUri);
            out.write("<li> Exception message: </li>" + throwable.getMessage());
            out.write("<ul> Error details </ul>");
        }
        out.write("</body></html>");

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
        processError(request, response);
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
        processError(request, response);
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

    private static final Logger logger = Logger.getLogger(ExceptionController.class.getSimpleName());
}
