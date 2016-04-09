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
import java.util.Locale;
import java.util.Map;
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
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "DegreeController", urlPatterns = {"/addDegree", "/retrieveDegrees", "/editDegree", "/removeDegree"})
public class DegreeController extends Controller {

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
        String path = request.getServletPath();
        String destination;

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
        if (adminSession == true || adminSession == false) {
            //Admin session not established
            LOGGER.log(Level.INFO, "Responding to the request");

            switch (path) {
                case "/addDegree":

                    //Read in the unique identifier of the faculty to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the degree belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the degree belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a department");
                        department = null;
                    }

                    //Read in the unique identifier of the admission to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the admission to which the degree belongs");
                    admission = new AdmissionDetails();
                    admission.setId(Integer.parseInt(request.getParameter("admissionId")));

                    //Read in details for the degree
                    LOGGER.log(Level.INFO, "Reading in details for the degree");
                    degree = new DegreeDetails();
                    degree.setActive(true);
                    degree.setFaculty(faculty);
                    degree.setAdmission(admission);
                    degree.setDepartment(department);
                    degree.setName(request.getParameter("degreeName"));

                    //Send the details to the entity manager for recording in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for recording in the database");
                    try {
                        degreeService.addDegree(degree);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail records in the session and
                    //Display the new list of degree records
                    if (degree.getFaculty() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, degree.getFaculty());
                    } else if (degree.getDepartment() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, degree.getDepartment());
                    }

                    return;

                case "/editDegree":

                    //Read in the unique identifier of the faculty to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the degree belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the degree belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a department");
                        department = null;
                    }

                    //Read in the unique identifier of the admission to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the admission to which the degree belongs");
                    admission = new AdmissionDetails();
                    admission.setId(Integer.parseInt(request.getParameter("admissionId")));

                    //Read in details for the degree
                    LOGGER.log(Level.INFO, "Reading in details for the degree");
                    degree = new DegreeDetails();
                    degree.setActive(true);
                    degree.setFaculty(faculty);
                    degree.setAdmission(admission);
                    degree.setDepartment(department);
                    degree.setName((request.getParameter("degreeName")));
                    degree.setId(Integer.parseInt(request.getParameter("degreeId")));

                    //Send the details to the entity manager for record update in the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record update in the database");
                    try {
                        degreeService.editDegree(degree);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail records in the session and
                    //Display the new list of degree records
                    if (degree.getFaculty() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, degree.getFaculty());
                    } else if (degree.getDepartment() != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, degree.getDepartment());
                    }
                    return;

                case "/removeDegree":

                    //Read in the unique identifier of the faculty to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the faculty to which the degree belongs");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a faculty");
                        faculty = null;
                    }

                    //Read in the unique identifier of the department to which the degree belongs
                    LOGGER.log(Level.INFO, "Reading in the unique identifier of the department to which the degree belongs");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                    } catch (NumberFormatException e) {
                        LOGGER.log(Level.INFO, "The degree does not belong to a department");
                        department = null;
                    }

                    //Send the details to the entity manager for record removal from the database
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager for record removal from the database");
                    try {
                        degreeService.removeDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail other required records in the session and
                    //Display the new list of degree records
                    if (faculty != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, faculty);
                    } else if (department != null) {
                        generateTableBodyAndAvailOtherRecords(session, response, department);
                    }
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

    //<editor-fold defaultstate="collapsed" desc="Retrieve other required records and generate table body">
    private void generateTableBodyAndAvailOtherRecords(HttpSession session, HttpServletResponse response, Object object) throws IOException {

        //<editor-fold defaultstate="collapsed" desc="Avail other records in session">       
        Map<AdmissionDetails, List<DegreeDetails>> degreesByAdmissionMap = new HashMap<>();

        //Determine the object's identity and cast it to the appropriate class then retrieve evaluation degrees about it
        LOGGER.log(Level.INFO, "Determining object's identity");
        if (object instanceof FacultyDetails) {

            //Cast the object to FacultyDetails
            LOGGER.log(Level.INFO, "Casting the object to FacultyDetails");
            faculty = (FacultyDetails) object;
            department = null;
            try {
                //Retrieve the map of degrees by faculty from the database
                LOGGER.log(Level.INFO, "Retrieving the map of degrees by faculty from the database");
                degreesByAdmissionMap = degreeService.retrieveDegreesOfFacultyByAdmission(faculty);
            } catch (InvalidArgumentException | InvalidStateException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString(e.getCode()));
                LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            }

        } else if (object instanceof DepartmentDetails) {

            //Cast the object to DepartmentDetails
            LOGGER.log(Level.INFO, "Casting the object to DepartmentDetails");
            department = (DepartmentDetails) object;
            faculty = null;
            try {
                //Retrieve the map of degrees by department from the database
                LOGGER.log(Level.INFO, "Retrieving the map of degrees by department from the database");
                degreesByAdmissionMap = degreeService.retrieveDegreesOfDepartmentByAdmission(department);
            } catch (InvalidArgumentException | InvalidStateException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString(e.getCode()));
                LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            }
        }

        //Avail the degrees in the session
        LOGGER.log(Level.INFO, "Availing the degrees in session");
        session.setAttribute("degreesByAdmissionMap", degreesByAdmissionMap);

        //<editor-fold defaultstate="collapsed" desc="Generate table body">
        int index;
        PrintWriter out = response.getWriter();
        for (AdmissionDetails ad : degreesByAdmissionMap.keySet()) {

            index = 0;
            out.write("<tr>");
            out.write("<td> &nbsp; </td>");
            out.write("<td colspan=\"4\"> <strong> " + ad.getAdmission() + " </strong> </td>");
            out.write("</tr>");

            for (DegreeDetails dd : degreesByAdmissionMap.get(ad)) {
                out.write("<tr>");
                out.write("<td onclick=\"loadWindow('/Ocena/retrieveCourses?degreeId=" + dd.getId() + "')\"> &nbsp; </td>");
                out.write("<td onclick=\"loadWindow('/Ocena/retrieveCourses?degreeId=" + dd.getId() + "')\"> " + ++index + " </td>");
                out.write("<td onclick=\"loadWindow('/Ocena/retrieveCourses?degreeId=" + dd.getId() + "')\"> " + dd.getName() + " </td>");
                if (faculty != null) {
                    out.write("<td><button onclick=\"editDegree('" + dd.getName() + "', '" + dd.getId() + "', '" + ad.getId() + "', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                    out.write("<td><button onclick=\"removeDegree('" + dd.getId() + "', '" + faculty.getId() + "', '')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                } else if (department != null) {
                    out.write("<td><button onclick=\"editDegree('" + dd.getName() + "', '" + dd.getId() + "', '" + ad.getId() + "', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-pencil\"></span></button> </td>");
                    out.write("<td><button onclick=\"removeDegree('" + dd.getId() + "', '', '" + department.getId() + "')\"> <span class=\"glyphicon glyphicon-trash\"></span></button> </td>");
                }
                out.write("</tr>");
            }
        }
        //</editor-fold>
    }
//</editor-fold>   

    private static final Logger LOGGER = Logger.getLogger(DegreeController.class.getSimpleName());

}
