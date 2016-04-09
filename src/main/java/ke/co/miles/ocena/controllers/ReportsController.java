/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ke.co.miles.ocena.defaults.Controller;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;

/**
 *
 * @author siech
 */
@WebServlet(name = "ReportsController", urlPatterns = {"/downloadAllReports", "/downloadFacultyReports"})
public class ReportsController extends Controller {

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

            String destination, filePath, headerKey, headerValue, mimeType;
            OutputStream outStream = response.getOutputStream();
            String path = request.getServletPath();
            FileInputStream inStream;
            ServletContext context;
            File downloadFile;
            byte[] buffer;
            int bytesRead;

            switch (path) {

                case "/downloadAllReports":
                    //Get the servlet context
                    LOGGER.log(Level.INFO, "Getting the servlet context");
                    context = getServletContext();

                    //Set the file path
                    LOGGER.log(Level.INFO, "Setting the file path");
                    filePath = context.getRealPath("/") + File.separator;

                    try {
                        zipService.zipFolder(filePath + bundle.getString("parent_folder").replaceAll("[\\\\/*<>|\"?]", "-"), filePath + bundle.getString("zip_name").replaceAll("[\\\\/*<>|\"?]", "-") + ".zip");
                    } catch (Exception ex) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString("zip_error"));
                        LOGGER.log(Level.INFO, bundle.getString("zip_error"));

                        path = (String) session.getAttribute("home");
                        LOGGER.log(Level.INFO, "Path is: {0}", path);

                        break;
                    }

                    //Read input file from a path
                    LOGGER.log(Level.INFO, "Reading input file from a path");
                    downloadFile = new File(filePath + bundle.getString("zip_name").replaceAll("[\\\\/*<>|\"?]", "-") + ".zip");
                    inStream = new FileInputStream(downloadFile);

                    //Get MIME type of the file
                    LOGGER.log(Level.INFO, "Getting the MIME type of the file");
                    mimeType = context.getMimeType(bundle.getString("zip_name").replaceAll("[\\\\/*<>|\"?]", "-") + ".zip");
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }

                    //Modify the response to convey content type and length
                    LOGGER.log(Level.INFO, "Modifying the response to convey content type and length");
                    response.setContentType(mimeType);
                    response.setContentLength((int) downloadFile.length());

                    //Ensure browser downloads the file rather than opens it
                    LOGGER.log(Level.INFO, "Ensuring the browser downloads the file rather than opens it");
                    headerKey = "Content-Disposition";
                    headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                    response.setHeader(headerKey, headerValue);

                    //Obtain and write to response's output stream
                    LOGGER.log(Level.INFO, "Obtaining and writing to response's output stream");

                    buffer = new byte[4096];
                    while ((bytesRead = inStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, bytesRead);
                    }

                    //Download the file and close the input/output streams
                    LOGGER.log(Level.INFO, "Download the file and close the input/output streams");
                    outStream.flush();
                    inStream.close();
                    outStream.close();

                    LOGGER.log(Level.INFO, "\n\n\033[32;3m Download completed successfully\n\n");

                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    response.setContentType("text/html;charset=UTF-8");
                    response.getWriter().write(bundle.getString("download_success"));

                    path = (String) session.getAttribute("home");
                    LOGGER.log(Level.INFO, "Path is: {0}", path);

                    break;

                case "/downloadFacultyReports":
                    //Get the servlet context
                    LOGGER.log(Level.INFO, "Getting the servlet context");
                    context = getServletContext();

                    //Set the file path
                    LOGGER.log(Level.INFO, "Setting the file path");
                    filePath = context.getRealPath("/") + File.separator + bundle.getString("parent_folder").replaceAll("[\\\\/*<>|\"?]", "-") + File.separator;

                    Map<AdmissionDetails, List<DegreeDetails>> degreesByAdmissionMap;
                    try {
                        degreesByAdmissionMap = (Map<AdmissionDetails, List<DegreeDetails>>) session.getAttribute("degreesByAdmissionMap");
                    } catch (Exception e) {
                        LOGGER.log(Level.INFO, bundle.getString("degrees_unavailable"));

                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString("degrees_unavailable"));

                        path = (String) session.getAttribute("home");
                        LOGGER.log(Level.INFO, "Path is: {0}", path);

                        break;
                    }

                    String sourceFolder;
                    String destinationFolder;

                    for (AdmissionDetails ad : degreesByAdmissionMap.keySet()) {
                        for (DegreeDetails dd : degreesByAdmissionMap.get(ad)) {

                            sourceFolder = filePath + ad.getAdmission().replaceAll("[\\\\/*<>|\"?]", "-") + File.separator + dd.getName().replaceAll("[\\\\/*<>|\"?]", "-");
                            destinationFolder = filePath + ad.getAdmission().replaceAll("[\\\\/*<>|\"?]", "-") + File.separator + dd.getName().replaceAll("[\\\\/*<>|\"?]", "-") + ".zip";

                            try {
                                zipService.zipFolder(sourceFolder, destinationFolder);
                            } catch (Exception ex) {
                                LOGGER.log(Level.INFO, bundle.getString("zip_error"));

                                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                response.setContentType("text/html;charset=UTF-8");
                                response.getWriter().write(bundle.getString("zip_error"));

                                path = (String) session.getAttribute("home");
                                LOGGER.log(Level.INFO, "Path is: {0}", path);

                                break;
                            }

                            //Read input file from a path
                            LOGGER.log(Level.INFO, "Reading input file from a path");
                            downloadFile = new File(destinationFolder);
                            inStream = new FileInputStream(downloadFile);

                            //Get MIME type of the file
                            LOGGER.log(Level.INFO, "Getting the MIME type of the file");
                            mimeType = context.getMimeType(dd.getName().replaceAll("[\\\\/*<>|\"?]", "-") + ".zip");
                            if (mimeType == null) {
                                mimeType = "application/octet-stream";
                            }

                            //Modify the response to convey content type and length
                            LOGGER.log(Level.INFO, "Modifying the response to convey content type and length");
                            response.setContentType(mimeType);
                            response.setContentLength((int) downloadFile.length());

                            //Ensure browser downloads the file rather than opens it
                            LOGGER.log(Level.INFO, "Ensuring the browser downloads the file rather than opens it");
                            headerKey = "Content-Disposition";
                            headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
                            response.setHeader(headerKey, headerValue);

                            //Obtain response's output stream
                            LOGGER.log(Level.INFO, "Obtaining response's output stream");

                            //Write to the output stream
                            LOGGER.log(Level.INFO, "Writing to the output stream");
                            buffer = new byte[4096];
                            while ((bytesRead = inStream.read(buffer)) > 0) {
                                outStream.write(buffer, 0, bytesRead);
                            }

                            //Download the file and close the input/output streams
                            LOGGER.log(Level.INFO, "Download the file and close the input/output streams");
                            outStream.flush();
                            inStream.close();

                        }
                    }

                    outStream.close();

                    LOGGER.log(Level.INFO, "\n\n\033[32;3m Downloads completed successfully\n\n");

                    path = (String) session.getAttribute("home");
                    LOGGER.log(Level.INFO, "Path is: {0}", path);
                    destination = "/WEB-INF/views" + path + ".jsp";
                    try {
                        LOGGER.log(Level.INFO, "Dispatching request to: {0}", destination);
                        request.getRequestDispatcher(destination).forward(request, response);
                    } catch (ServletException | IOException e) {
                        LOGGER.log(Level.INFO, "Request dispatch failed");
                    }
            }

            destination = "/WEB-INF/views" + path + ".jsp";
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

    private static final Logger LOGGER = Logger.getLogger(ReportsController.class.getSimpleName());

}
