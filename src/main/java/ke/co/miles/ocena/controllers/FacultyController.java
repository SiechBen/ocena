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
import java.util.Random;
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
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "FacultyController", urlPatterns = {"/addFaculty", "/retrieveFaculties", "/editFaculty", "/removeFaculty", "/saveEditedFaculty"})
public class FacultyController extends Controller {

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
            Random random = new Random();
            List<FacultyDetails> faculties = new ArrayList<>();
            college = new CollegeDetails();
            try {
                college = collegeService.retrieveCollege(Integer.parseInt(request.getParameter("collegeId")));
            } catch (InvalidArgumentException | InvalidStateException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.setContentType("text/html;charset=UTF-8");
                response.getWriter().write(bundle.getString(e.getCode()));
                LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
            }

            switch (path) {

                case "/retrieveFaculties":

                    //Retrieve the list of faculties and set the path to where they'll be displayed
                    path = retrieveFaculties(college, faculties, session, response);
                    break;

                case "/addFaculty":
                    //Read in the country details
                    country = new CountryDetails();
                    try {
                        country.setId(Integer.parseInt(request.getParameter("country")));
                    } catch (NumberFormatException e) {
                        country.setId(new Integer("110"));
                    }

                    //Read in the email contact details
                    emailContact = new EmailContactDetails();
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email"));

                    //Read in the phone contact details
                    phoneContact = new PhoneContactDetails();
                    phoneContact.setActive(true);
                    phoneContact.setMobileNumber(request.getParameter("mobileNumber"));
                    phoneContact.setFixedNumber(request.getParameter("fixedNumber"));

                    //Read in the postal contact details
                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("boxNumber"));
                    postalContact.setPostalCode(request.getParameter("postalCode"));

                    //Read in values for the faculty record to be made
                    LOGGER.log(Level.INFO, "Reading in values for the faculty record to be made");
                    faculty = new FacultyDetails();
                    faculty.setActive(true);
                    faculty.setCollege(college);
                    faculty.setName(request.getParameter("name"));
                    faculty.setAbbreviation(request.getParameter("abbreviation"));

                    if (emailContact.getEmailAddress().trim().length() == 0) {
                        emailContact.setEmailAddress((faculty.getAbbreviation() + "." + college.getAbbreviation() + "@uonbi.ac.ke").toLowerCase());
                    }
                    if (phoneContact.getMobileNumber().trim().length() == 0) {
                        phoneContact.setMobileNumber("+254-7" + random.nextInt(1000) + "0" + random.nextInt(1000) + random.nextInt(1000));
                    }

                    //Send the details to the entity manager
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager");
                    try {
                        facultyService.addFaculty(faculty, emailContact, phoneContact, postalContact);
                    } catch (InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of faculties
                    LOGGER.log(Level.INFO, "Retrieving faculties from the database");
                    try {
                        faculties = facultyService.retrieveFaculties(college.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update table body
                    generateTableBody(faculties, college, response);
                    return;
                case "/editFaculty":
                    //Read in the faculty's unique identifier
                    LOGGER.log(Level.INFO, "Retrieving the faculty's unique identifier");
                    int facultyId = Integer.parseInt(request.getParameter("facultyId"));

                    //Retrieve the faculty record from the database
                    LOGGER.log(Level.INFO, "Retrieving the faculty details from the database");
                    faculty = new FacultyDetails();
                    try {
                        faculty = facultyService.retrieveFaculty(facultyId);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the contact record from the database
                    LOGGER.log(Level.INFO, "Retrieving the contact details from the database");
                    contact = new ContactDetails();
                    try {
                        contact = contactService.retrieveContact(faculty.getContact().getId());
                    } catch (InvalidStateException | InvalidArgumentException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the email contact details
                    LOGGER.log(Level.INFO, "Retrieving the email contact details");
                    emailContact = new EmailContactDetails();
                    try {
                        emailContact = emailContactService.retrieveEmailContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the phone contact details
                    LOGGER.log(Level.INFO, "Retrieving the phone contact details");
                    phoneContact = new PhoneContactDetails();
                    try {
                        phoneContact = phoneContactService.retrievePhoneContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the postal contact details
                    LOGGER.log(Level.INFO, "Retrieving the postal contact details");
                    postalContact = new PostalContactDetails();
                    try {
                        postalContact = postalContactService.retrievePostalContact(contact.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Avail the faculty and its contacts in the session
                    LOGGER.log(Level.INFO, "Availing the faculty and its contacts in the session");
                    session.setAttribute("faculty", faculty);
                    session.setAttribute("contact", contact);
                    session.setAttribute("postalContact", postalContact);
                    session.setAttribute("phoneContact", phoneContact);
                    session.setAttribute("emailContact", emailContact);

                    path = "/editFaculty";
                    LOGGER.log(Level.INFO, "Path is: {0}", path);
                    break;

                case "/saveEditedFaculty":

                    //Read in the contact details
                    contact = new ContactDetails();
                    contact.setId(Integer.parseInt(request.getParameter("contact-id")));
                    contact.setActive(true);

                    //Read in values for the faculty record to be made
                    LOGGER.log(Level.INFO, "Reading in values for the faculty record to be made");
                    faculty = new FacultyDetails();
                    faculty.setActive(true);
                    faculty.setCollege(college);
                    faculty.setContact(contact);
                    faculty.setName(request.getParameter("faculty-name"));
                    faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                    faculty.setAbbreviation(request.getParameter("faculty-abbreviation"));

                    //Read in the email contact details
                    emailContact = new EmailContactDetails();
                    emailContact.setContact(contact);
                    emailContact.setActive(true);
                    emailContact.setEmailAddress(request.getParameter("email-address"));
                    emailContact.setId(Integer.parseInt(request.getParameter("email-contact-id")));

                    //Read in the phone contact details
                    phoneContact = new PhoneContactDetails();
                    phoneContact.setContact(contact);
                    phoneContact.setActive(true);
                    phoneContact.setId(Integer.parseInt(request.getParameter("phone-contact-id")));
                    phoneContact.setMobileNumber(request.getParameter("mobile-number"));
                    phoneContact.setFixedNumber(request.getParameter("fixed-number"));

                    //Read in the country details
                    country = new CountryDetails();
                    try {
                        country.setId(Integer.parseInt(request.getParameter("country")));
                    } catch (NumberFormatException e) {
                        country.setId(new Integer("110"));
                    }

                    //Read in the postal contact details
                    postalContact = new PostalContactDetails();
                    postalContact.setActive(true);
                    postalContact.setCountry(country);
                    postalContact.setContact(contact);
                    postalContact.setTown(request.getParameter("town"));
                    postalContact.setBoxNumber(request.getParameter("box-number"));
                    postalContact.setPostalCode(request.getParameter("postal-code"));
                    postalContact.setId(Integer.parseInt(request.getParameter("postal-contact-id")));

                    if (emailContact.getEmailAddress().trim().length() == 0) {
                        emailContact.setEmailAddress((faculty.getAbbreviation() + "." + college.getAbbreviation() + "@uonbi.ac.ke").toLowerCase());
                    }
                    if (phoneContact.getMobileNumber().trim().length() == 0) {
                        phoneContact.setMobileNumber("+254-7" + random.nextInt(1000) + "0" + random.nextInt(1000) + random.nextInt(1000));
                    }

                    //Send the details to the entity manager
                    LOGGER.log(Level.INFO, "Sending the details to the entity manager");
                    try {
                        facultyService.editFaculty(faculty, emailContact, phoneContact, postalContact);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the list of faculties and set the path to where they'll be displayed
                    path = retrieveFaculties(college, faculties, session, response);
                    LOGGER.log(Level.INFO, "Displaying the faculties \nPath is: {0}", path);

                    break;

                case "/removeFaculty":
                    //Send the unique identifier of the faculty to be removed to the entity manager
                    LOGGER.log(Level.INFO, "Sending the unique identifier of the faculty to be removed to the entity manager");
                    try {
                        facultyService.removeFaculty(Integer.parseInt(request.getParameter("id")));
                    } catch (NumberFormatException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString("invalid_id"));
                        LOGGER.log(Level.INFO, bundle.getString("invalid_id"));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Retrieve the new list of faculties
                    LOGGER.log(Level.INFO, "Retrieving faculties from the database");
                    try {
                        faculties = facultyService.retrieveFaculties(college.getId());
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        response.setContentType("text/html;charset=UTF-8");
                        response.getWriter().write(bundle.getString(e.getCode()));
                        LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
                    }

                    //Update table body
                    generateTableBody(faculties, college, response);
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
    private void generateTableBody(List<FacultyDetails> faculties, CollegeDetails college, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        int index = 0;

        for (FacultyDetails f : faculties) {
            if (index % 2 == 0) {
                out.write("<tr class=\"even\">");
            } else {
                out.write("<tr>");
            }
            out.write("<td onclick=\"loadWindow('/Ocena/checkDepartment?facultyId=" + f.getId() + "&collegeId=" + college.getId() + "')\">" + ++index + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/checkDepartment?facultyId=" + f.getId() + "&collegeId=" + college.getId() + "')\">" + f.getName() + "</td>");
            out.write("<td onclick=\"loadWindow('/Ocena/checkDepartment?facultyId=" + f.getId() + "&collegeId=" + college.getId() + "')\">" + f.getAbbreviation() + "</td>");
            out.write("<td><button onclick=\"loadWindow('/Ocena/editFaculty?facultyId=" + f.getId() + "')\"><span class=\"glyphicon glyphicon-pencil\" aria-hidden=\"true\"></span></button></td>");
            out.write(" <td><button onclick=\"removeFaculty('" + f.getId() + "', '" + college.getId() + "')\"><span class=\"glyphicon glyphicon-trash\" aria-hidden=\"true\"></span></button></td>");
            out.write("</tr>");
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Retrieve faculties">
    private String retrieveFaculties(CollegeDetails c, List<FacultyDetails> faculties, HttpSession session, HttpServletResponse response) throws IOException {
        //Retrieve the faculties from the database
        LOGGER.log(Level.INFO, "Retrieving faculties in {0} from the database", c.getName());
        try {
            faculties = facultyService.retrieveFaculties(c.getId());
        } catch (InvalidArgumentException | InvalidStateException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(bundle.getString(e.getCode()));
            LOGGER.log(Level.INFO, bundle.getString(e.getCode()));
        }

        //Avail the faculties in the session
        LOGGER.log(Level.INFO, "Availing the list of faculties in the session");
        session.setAttribute("faculties", faculties);
        session.setAttribute("college", c);

        //Set the path to display the faculties
        String path = "/viewFaculties";
        LOGGER.log(Level.INFO, "Displaying the faculties \nPath is: {0}", path);
        return path;
    }
    //</editor-fold>

    private static final Logger LOGGER = Logger.getLogger(FacultyController.class.getSimpleName());

}
