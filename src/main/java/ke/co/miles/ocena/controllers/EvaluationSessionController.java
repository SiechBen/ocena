/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ke.co.miles.ocena.defaults.Controller;
import ke.co.miles.ocena.exceptions.DuplicateStateException;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionAnswerDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
//import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "EvaluationSessionController", urlPatterns = {"/viewEvaluationSessions", "/addEvaluationSession", "/checkEvaluationSession", "/setupEvaluationSession", "/editEvaluationSession", "/closeEvaluationSession"})
public class EvaluationSessionController extends Controller {

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
        String path = request.getServletPath();
        String destination;

        //Define the date format to be used
        DateFormat yearMonthFormat = new SimpleDateFormat("MMM yyyy");
        DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat databaseDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date;
        String dateString;
        List<DegreeDetails> degrees = null;

        boolean adminSession;
        /*       try {
         adminSession = (Boolean) session.getAttribute("mainAdminSession");
         } catch (Exception e) {
         logger.log(Level.INFO, "Main admin session is null");
         logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
         request.getRequestDispatcher("index.jsp").forward(request, response);
         return;
         }

         if (adminSession == false) {*/
        try {
            adminSession = (Boolean) session.getAttribute("subAdminSession");
        } catch (Exception e) {
            logger.log(Level.INFO, "Sub admin session is null");
            logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }
   //     }

        //Check session type
        logger.log(Level.INFO, "Checking session type");
        if (adminSession == false) {
            //Admin session not established
            logger.log(Level.INFO, "Admin session not established hence not responding to the request");
        } else if (adminSession == true) {
            //Admin session established
            logger.log(Level.INFO, "Admin session established hence responding to the request");

            switch (path) {

                case "/addEvaluationSession":

                    //Proceed to page
                    break;

                case "/viewEvaluationSessions":

                    //Proceed to page
                    break;

                case "/checkEvaluationSession":

                    //Retrieve the faculty unique identifier if any
                    logger.log(Level.INFO, "Retrieving the faculty unique identifier if any");
                    faculty = new FacultyDetails();
                    try {
                        faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                        if (faculty.getId() == null) {
                            faculty = null;
                        }
                    } catch (Exception e) {
                        faculty = null;
                    }

                    //Retrieve the department unique identifier if any
                    logger.log(Level.INFO, "Retrieving the department unique identifier if any");
                    department = new DepartmentDetails();
                    try {
                        department.setId(Integer.parseInt(request.getParameter("departmentId")));
                        if (department.getId() == null) {
                            department = null;
                        }
                    } catch (Exception e) {
                        department = null;
                    }

                    if (faculty != null) {
                        try {
                            degrees = degreeService.retrieveFacultyDegrees(faculty.getId());
                        } catch (InvalidArgumentException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the degrees");
                        }
                    } else if (department != null) {
                        try {
                            degrees = degreeService.retrieveDepartmentDegrees(department.getId());
                        } catch (InvalidArgumentException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the degrees");
                        }
                    }

                    //Avail the degrees on session
                    logger.log(Level.INFO, "Availing the degrees on session");
                    session.setAttribute("degrees", degrees);

                    //Retrieve the active evaluation sessions
                    logger.log(Level.INFO, "Retrieving the active evaluation sessions");
                    List<EvaluationSessionDetails> evaluationSessions = new ArrayList<>();
                    try {
                        evaluationSessions = evaluationSessionService.retrieveEvaluationSessions(degrees);
                    } catch (InvalidArgumentException | InvalidStateException ex) {
                        logger.log(Level.INFO, "An error occurred while retrieving the active evaluation session");
                    }

                    if (!evaluationSessions.isEmpty()) {
                        //Generate evaluation session table
                        logger.log(Level.INFO, "Generating evaluation session table");

                        out.write("<thead>");
                        out.write("<tr>");
                        out.write("<th colspan=\"2\"> ACTIVE EVALUATION SESSIONS </th>");
                        out.write("</tr>");
                        out.write("</thead>");
                        out.write("<tfoot>");
                        out.write("<tr>");
                        out.write("<td colspan=\"2\">Evaluation sessions</tr>");
                        out.write("</tr>");
                        out.write("</tfoot>");

                        int count = 0;
                        for (EvaluationSessionDetails es : evaluationSessions) {
                            out.write("<tbody>");
                            if (count++ > 0) {
                                out.write("<tr> <th colspan=\"2\"> </th> </tr>");
                            }
                            out.write("<tr>");
                            out.write("<td> Degree: </td>");
                            out.write("<td>");
                            out.write("<select id=\"course-of-session-degree\">");
                            for (DegreeDetails d : degrees) {
                                out.write("<option value=\"" + d.getId() + "\">" + d.getName() + "</option>");
                            }
                            out.write("</select>");
                            out.write("</td>");
                            out.write("<tr>");
                            out.write("<td> Start date: </td>");
                            try {
                                //Retrieve the date
                                date = databaseDateFormat.parse(databaseDateFormat.format(es.getStartDate()));

                                //Format the date string to MM/dd/yyyy
                                dateString = userDateFormat.format(date);

                                //Display the date
                                out.write("<td> <input type=\"text\" class=\"start-date\" value=\"" + dateString + "\"> </td>");
                            } catch (ParseException ex) {
                                logger.log(Level.INFO, "String is unparseable to date format");
                            }
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td> End date: </td>");
                            out.write("<td> ");
                            try {
                                //Retrieve the date
                                date = databaseDateFormat.parse(databaseDateFormat.format(es.getEndDate()));

                                //Format the date string to MM/dd/yyyy
                                dateString = userDateFormat.format(date);

                                //Display the date
                                out.write("<input type=\"text\" class=\"end-date\" onchange=\"checkDate(); return false;\" value=\"" + dateString + "\">");
                            } catch (ParseException ex) {
                                logger.log(Level.INFO, "String is unparseable to date format");
                            }
                            out.write("<span id=\"information-box\" class=\"alert alert-warning\" hidden></span> ");
                            out.write("</td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td> Academic year: </td>");
                            out.write("<td> <input type=\"text\" id=\"academic-year\" value=\"" + es.getAcademicYear() + "\"> </td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td> Semester: </td>");
                            out.write("<td> <input type=\"text\" id=\"semester\" value=\"" + es.getSemester() + "\"> </td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td> Admission month & year </td>");
                            out.write("<td> <input type=\"date\" class=\"admission-month-year\" value=\"" + es.getAdmissionYear() + "\"/> </td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            out.write("<td colspan=\"2\"></td>");
                            out.write("</tr>");
                            out.write("<tr>");
                            if (faculty != null) {
                                out.write("<td><button id=\"view-courses-of-session-button\" class=\"btn btn-default\" onclick=\"loadWindow('/Ocena/retrieveCoursesOfSession?evaluationSessionId=" + es.getId() + "&degreeId=" + es.getDegree().getId() + "&departmentId=&facultyId=" + faculty.getId() + "')\">Courses of this session</button></td>");
                            } else if (department != null) {
                                out.write("<td><button id=\"view-courses-of-session-button\" class=\"btn btn-default\" onclick=\"loadWindow('/Ocena/retrieveCoursesOfSession?evaluationSessionId=" + es.getId() + "&degreeId=" + es.getDegree().getId() + "&departmentId=" + department.getId() + "&facultyId=')\">Courses of this session</button></td>");
                            }
                            out.write("<td>");
                            out.write("<button id=\"edit-evaluation-session-button\" class=\"btn btn-default\" onclick=\"editEvaluationSession('" + es.getId() + "')\"> Edit this session </button>");
                            out.write("<button id=\"close-evaluation-session-button\" class=\"btn btn-default\" onclick=\"closeEvaluationSession('" + es.getId() + "')\"> Close this session </button>");
                            out.write("</td>");
                            out.write("</tr>");
                            out.write("</tbody>");
                        }

                    } else {
                        out.write("");
                    }

                    return;

                case "/setupEvaluationSession":

                    //Declare evaluation session unique identifier holder
                    Integer evaluationSessionId;

                    //Read in the degree unique identifier if any
                    logger.log(Level.INFO, "Reading in the degree unique identifier if any");
                    degree = new DegreeDetails();
                    try {
                        degree = degreeService.retrieveDegree(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (NumberFormatException | InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "The degree unique identifier is not provided");
                        return;
                    }

                    Date admissionYear;
                    try {
                        admissionYear = yearMonthFormat.parse(request.getParameter("admissionYear"));
                    } catch (ParseException e) {
                        logger.log(Level.INFO, "An error occurred while parsing the admission year and month");
                        return;
                    }
                    Calendar holder = Calendar.getInstance();
                    holder.setTime(admissionYear);

                    Calendar calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.MONTH, holder.get(Calendar.MONTH));
                    calendar.set(Calendar.YEAR, holder.get(Calendar.YEAR));
                    admissionYear = calendar.getTime();

                    //Read in the evaluation session details
                    logger.log(Level.INFO, "Reading in the evaluation session details");
                    evaluationSession = new EvaluationSessionDetails();
                    evaluationSession.setDegree(degree);
                    evaluationSession.setActive(Boolean.TRUE);
                    evaluationSession.setAdmissionYear(admissionYear);
                    evaluationSession.setSemester(request.getParameter("semester"));
                    evaluationSession.setAcademicYear(request.getParameter("academicYear"));
                    try {
                        //Read in date string in the format MM/dd/yyyy and parse it to date
                        date = userDateFormat.parse(request.getParameter("startDate"));

                        //Format the date string to yyyy/MM/dd and parse it to date
                        date = databaseDateFormat.parse(databaseDateFormat.format(date));

                        //Set the start date
                        evaluationSession.setStartDate(date);
                    } catch (ParseException ex) {
                        logger.log(Level.INFO, "String is unparseable to date format");
                        return;
                    }
                    try {
                        //Read in the date string in the format MM/dd/yyyy and parse it to date
                        date = userDateFormat.parse(request.getParameter("endDate"));

                        //Format the date string to yyyy/MM/dd and parse it to date
                        date = databaseDateFormat.parse(databaseDateFormat.format(date));

                        //Set the end date
                        evaluationSession.setEndDate(date);
                    } catch (ParseException ex) {
                        logger.log(Level.INFO, "String is unparseable to date format");
                        return;
                    }

                    //Send the evaluation session details to the entity manager for record update
                    logger.log(Level.INFO, "Sending the evaluation session details to the entity manager for record creation");
                    try {
                        evaluationSession.setId(evaluationSessionService.addEvaluationSession(evaluationSession));
                    } catch (InvalidArgumentException | DuplicateStateException e) {
                        logger.log(Level.INFO, "An error occurred during evaluation session setup");
                    }

                    //Retrieve the evaluation questions for the faculty or department
                    logger.log(Level.INFO, "Retrieving the evaluation questions for the faculty or department");
                    List<QuestionDetails> questions = new ArrayList<>();
                    if (degree.getFaculty() != null) {
                        try {
                            questions = questionService.retrieveQuestionsInFaculty(degree.getFaculty().getId());
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving the questions");
                        }
                    } else if (degree.getDepartment() != null) {
                        try {
                            questions = questionService.retrieveQuestionsInDepartment(degree.getDepartment().getId());
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving the questions");
                        }
                    }

                    //Make copies of the questions for use in this session
                    logger.log(Level.INFO, "Making copies of the questions for use in this session");
                    for (QuestionDetails qd : questions) {
                        //Create and fill a container to hold this question
                        logger.log(Level.INFO, "Creating and filling a container to hold the question : {0}", qd);

                        evaluatedQuestion = new EvaluatedQuestionDetails();
                        evaluatedQuestion.setActive(Boolean.TRUE);
                        evaluatedQuestion.setQuestion(qd.getQuestion());
                        evaluatedQuestion.setRatingType(qd.getRatingType());
                        evaluatedQuestion.setEvaluationSession(evaluationSession);
                        evaluatedQuestion.setMeansOfAnswering(qd.getMeansOfAnswering());
                        evaluatedQuestion.setQuestionCategory(qd.getQuestionCategory());

                        //Record a copy of this question
                        logger.log(Level.INFO, "Recording a copy of this question");
                        try {
                            evaluatedQuestionService.addEvaluatedQuestion(evaluatedQuestion);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while recording a copy of the question");
                        }
                    }

                    //Generate tabe body to display the evaluation session details
                    displayEvaluationSession(response, request, session);

                    return;

                case "/editEvaluationSession":

                    //Read in the unique identifier for the evaluation session
                    logger.log(Level.INFO, "Reading in the unique identifier for the evaluation session");
                    evaluationSessionId = Integer.parseInt(request.getParameter("evaluationSessionId"));

                    //Read in the degree unique identifier if any
                    logger.log(Level.INFO, "Reading in the degree unique identifier if any");
                    degree = new DegreeDetails();
                    try {
                        degree.setId(Integer.parseInt(request.getParameter("degreeId")));
                    } catch (Exception e) {
                        logger.log(Level.INFO, "The degree unique identifier is not provided");
                        return;
                    }

                    try {
                        admissionYear = yearMonthFormat.parse(request.getParameter("admissionYear"));
                    } catch (ParseException e) {
                        logger.log(Level.INFO, "An error occurred while parsing admission month and year");
                        return;
                    }

                    holder = Calendar.getInstance();
                    holder.setTime(admissionYear);

                    calendar = Calendar.getInstance();
                    calendar.clear();
                    calendar.set(Calendar.MONTH, holder.get(Calendar.MONTH));
                    calendar.set(Calendar.YEAR, holder.get(Calendar.YEAR));
                    admissionYear = calendar.getTime();

                    //Read in the evaluation session details
                    logger.log(Level.INFO, "Reading in the evaluation session details");
                    evaluationSession = new EvaluationSessionDetails();
                    evaluationSession.setDegree(degree);
                    evaluationSession.setId(evaluationSessionId);
                    evaluationSession.setActive(Boolean.TRUE);
                    evaluationSession.setAdmissionYear(admissionYear);
                    evaluationSession.setSemester(request.getParameter("semester"));
                    evaluationSession.setAcademicYear(request.getParameter("academicYear"));
                    try {
                        //Read in date string in the format MM/dd/yyyy and parse it to date
                        date = userDateFormat.parse(request.getParameter("startDate"));

                        //Format the date string to yyyy/MM/dd and parse it to date
                        date = databaseDateFormat.parse(databaseDateFormat.format(date));

                        //Set the start date
                        evaluationSession.setStartDate(date);
                    } catch (ParseException ex) {
                        logger.log(Level.INFO, "String is unparseable to date format");
                        return;
                    }
                    try {
                        //Read in the date string in the format MM/dd/yyyy and parse it to date
                        date = userDateFormat.parse(request.getParameter("endDate"));

                        //Format the date string to yyyy/MM/dd and parse it to date
                        date = databaseDateFormat.parse(databaseDateFormat.format(date));

                        //Set the end date
                        evaluationSession.setEndDate(date);
                    } catch (ParseException ex) {
                        logger.log(Level.INFO, "String is unparseable to date format");
                        return;
                    }

                    //Send the evaluation session details to the entity manager for record creation
                    logger.log(Level.INFO, "Sending the evaluation session details to the entity manager for record update");
                    try {
                        evaluationSessionService.editEvaluationSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException | DuplicateStateException e) {
                        logger.log(Level.INFO, "An error occurred during evaluation session update");
                    }

                    //Generate table body to display the evaluation session
                    displayEvaluationSession(response, request, session);

                    return;

                case "/closeEvaluationSession":

                    //Retrieve the evaluation session record
                    logger.log(Level.INFO, "Retrieving the evaluation session record");
                    try {
                        evaluationSession = evaluationSessionService.retrieveEvaluationSession(Integer.parseInt(request.getParameter("evaluationSessionId")));
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred during evaluation session retrieval");
                    }

                    //Mark the evaluation
                    logger.log(Level.INFO, "Marking the evaluation");
                    try {
                        evaluationMarkingService.markEvaluation(evaluationSession);
                    } catch (InvalidArgumentException ex) {
                        logger.log(Level.INFO, "An error occurred while marking the evaluation");
                        return;
                    }

                    //Retrieve question categories
                    logger.log(Level.INFO, "Retrieving question categories");
                    List<QuestionCategoryDetails> questionCategories;
                    try {
                        questionCategories = questionCategoryService.retrieveQuestionCategories();
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while retrieving the question categories");
                        return;
                    }

                    //Retrieve the courses of this degree's evaluation session
                    logger.log(Level.INFO, "Retrieving the courses of this degree's evaluation session");
                    List<CourseOfSessionDetails> courses = new ArrayList<>();
                    try {
                        courses = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while retrieving the courses of session");
                    }

                    DecimalFormat decimalFormat = new DecimalFormat("0.00");

                    //<editor-fold defaultstate="collapsed" desc="Generate evaluation report">
                    for (CourseOfSessionDetails c : courses) {
                        List<AssessedEvaluationDetails> assessedEvaluations = new ArrayList<>();
                        try {
                            assessedEvaluations = assessedEvaluationService.retrieveAssessedEvaluationsByCourse(c);
                        } catch (InvalidArgumentException | InvalidStateException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the assessed evaluation of this course of session");
                        }

                        XWPFDocument evaluationReport = new XWPFDocument();
                        FileOutputStream outStream;
                        String filePath;

                        filePath = "Evaluation reports"
                                + File.separator + evaluationSession.getAcademicYear().replaceAll("[\\\\/*<>|\"?]", "-") + " [academic year]"
                                + File.separator + c.getCourse().getCode().replaceAll("[\\\\/*<>|\"?]", "-") + " [course code]"
                                + File.separator + c.getFacultyMember().getPerson().getReferenceNumber().replaceAll("[\\\\/*<>|\"?]", "-") + " [staff number]"
                                + File.separator + evaluationSession.getSemester().replaceAll("[\\\\/*<>|\"?]", "-") + " [semester]"
                                + File.separator + "Evaluation report.docx";

                        new File(filePath).getParentFile().mkdirs();

                        try {
                            outStream = new FileOutputStream(filePath);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "Evaluation report could not created");
                            continue;
                        }

                        XWPFParagraph paragraph = evaluationReport.createParagraph();
                        XWPFRun run = paragraph.createRun();

                        run.setText("COURSE/LECTURER EVALUATION REPORT");
                        run.addBreak();
                        run.setText("COURSE: " + c.getCourse().getCode().toUpperCase());
                        run.addBreak();
                        run.setText("LECTURER: " + c.getFacultyMember().getPerson().getFirstName().toUpperCase() + " " + c.getFacultyMember().getPerson().getLastName().toUpperCase());
                        run.addBreak();
                        run.setText("REFERENCE NO: " + c.getFacultyMember().getPerson().getReferenceNumber().toUpperCase());
                        run.addBreak();
                        run.setText("SEMESTER: " + evaluationSession.getSemester());
                        run.addBreak();
                        run.setText("ACADEMIC YEAR: " + evaluationSession.getAcademicYear());
                        run.addBreak();

                        paragraph = evaluationReport.createParagraph();
                        run = paragraph.createRun();

                        int e = 64, i;
                        for (QuestionCategoryDetails qc : questionCategories) {

                            run.addBreak();
                            run.setText((char) ++e + ". " + qc.getCategory().toUpperCase());
                            run.addBreak();

                            i = 0;

                            for (AssessedEvaluationDetails ae : assessedEvaluations) {

                                if (ae.getQuestionCategory().equals(qc)) {

                                    if (ae.getEvaluatedQuestion().getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_RATING)) {

                                        try {
                                            if (ae.getRating() != null) {
                                                try {
                                                    run.setText(++i + ". " + ae.getQuestionDescription());
                                                    run.addBreak();
                                                    run.addTab();
                                                    run.setText("Rating: " + decimalFormat.format(Double.parseDouble(ae.getRating())));
                                                    run.addTab();
                                                    run.addTab();
                                                    run.setText("Standard deviation: " + decimalFormat.format((ae.getStandardDeviation())));
                                                    run.addBreak();
                                                    run.addBreak();
                                                } catch (Exception ex) {
                                                    logger.log(Level.INFO, "The rating standard deviation could not be rounded off");
                                                }
                                            }
                                        } catch (Exception ex) {
                                            logger.log(Level.INFO, "The rating is null");
                                        }
                                        try {
                                            if (ae.getPercentageScore() != null) {
                                                try {
                                                    run.setText(++i + ". " + ae.getQuestionDescription());
                                                    run.addBreak();
                                                    run.addTab();
                                                    run.setText("Percentage: " + decimalFormat.format(Double.parseDouble(ae.getPercentageScore().replaceAll(" ", "").replaceAll("%", ""))) + "%");
                                                    run.addBreak();
                                                    run.addBreak();
                                                } catch (Exception ex) {
                                                    logger.log(Level.INFO, "The percentage score could not be rounded off");
                                                }
                                            }
                                        } catch (Exception ex) {
                                            logger.log(Level.INFO, "The percentage score is null");
                                        }

                                    }

                                }

                            }

                        }

                        try {
                            evaluationReport.write(outStream);
                            outStream.close();
                        } catch (IOException ex) {
                            logger.log(Level.INFO, "Evaluation report could not be written to");
                            continue;
                        }
                        logger.log(Level.INFO, "\n\n\033[32;3mReport generated successfully\n");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="Evaluation summary report">
                    for (CourseOfSessionDetails c : courses) {
                        List<AssessedEvaluationDetails> assessedEvaluations = new ArrayList<>();
                        try {
                            assessedEvaluations = assessedEvaluationService.retrieveAssessedEvaluationsByCourse(c);
                        } catch (InvalidArgumentException | InvalidStateException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the assessed evaluation of this course of session");
                        }

                        XWPFDocument evaluationSummaryReport = new XWPFDocument();

                        String filePath = "Evaluation reports"
                                + File.separator + evaluationSession.getAcademicYear().replaceAll("[\\\\/*<>|\"?]", "-") + " [academic year]"
                                + File.separator + c.getCourse().getCode().replaceAll("[\\\\/*<>|\"?]", "-") + " [course code]"
                                + File.separator + c.getFacultyMember().getPerson().getReferenceNumber().replaceAll("[\\\\/*<>|\"?]", "-") + " [staff number]"
                                + File.separator + evaluationSession.getSemester().replaceAll("[\\\\/*<>|\"?]", "-") + " [semester]"
                                + File.separator + "Evaluation summary report.docx";

                        new File(filePath).getParentFile().mkdirs();

                        FileOutputStream outStream;
                        try {
                            outStream = new FileOutputStream(filePath);
                        } catch (IOException ex) {
                            logger.log(Level.INFO, "Evaluation summary report could not be created");
                            continue;
                        }

                        XWPFParagraph paragraph = evaluationSummaryReport.createParagraph();

                        XWPFRun run = paragraph.createRun();

                        run.setText("COURSE/LECTURER EVALUATION SUMMARY REPORT");
                        run.addBreak();
                        run.setText("ACADEMIC YEAR: " + evaluationSession.getAcademicYear());
                        run.addBreak();
                        run.setText("SEMESTER: " + evaluationSession.getSemester());
                        run.addBreak();
                        run.setText("COURSE: " + c.getCourse().getCode().toUpperCase());
                        run.addBreak();
                        run.setText("LECTURER: " + c.getFacultyMember().getPerson().getFirstName().toUpperCase() + " " + c.getFacultyMember().getPerson().getLastName().toUpperCase());
                        run.addBreak();
                        run.setText("REFERENCE NO: " + c.getFacultyMember().getPerson().getReferenceNumber().toUpperCase());
                        run.addBreak();

                        evaluationSummaryReport.createParagraph();

                        XWPFTable evaluationResultsTable = evaluationSummaryReport.createTable(questionCategories.size(), 3);
                        CTTblWidth width = evaluationResultsTable.getCTTbl().addNewTblPr().addNewTblW();
                        width.setType(STTblWidth.DXA);
                        width.setW(BigInteger.valueOf(9072));

                        XWPFTableRow headerRow = evaluationResultsTable.getRow(0);
                        headerRow.getCell(1).setText("Criteria");
                        headerRow.getCell(2).setText("Score/5");

                        XWPFTableRow tableRow;

                        int i = 1, e = 64, totalCount, noOfCategoriesUsed = 0;
                        double totalCategoryScore, averageCategoryScore, totalEvaluationScore = 0,
                                percentageEvaluationScore;

                        for (QuestionCategoryDetails qc : questionCategories) {

                            totalCount = 0;
                            totalCategoryScore = 0;
                            averageCategoryScore = 0;

                            for (AssessedEvaluationDetails ae : assessedEvaluations) {

                                if (ae.getQuestionCategory().equals(qc)) {

                                    if (ae.getEvaluatedQuestion().getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_RATING)) {

                                        try {
                                            if (ae.getRating() != null) {
                                                totalCategoryScore += Double.parseDouble(ae.getRating());
                                                totalCount++;
                                            }
                                        } catch (Exception ex) {
                                            logger.log(Level.INFO, "The rating is null");
                                        }

                                    }

                                }

                            }

                            if (totalCount != 0) {
                                averageCategoryScore = totalCategoryScore / totalCount;
                                noOfCategoriesUsed++;
                            }

                            String category = qc.getCategory().toLowerCase();
                            category = Character.toUpperCase(category.charAt(0)) + category.substring(1);

                            try {
                                averageCategoryScore = Double.parseDouble(decimalFormat.format(averageCategoryScore));
                            } catch (Exception ex) {
                                logger.log(Level.INFO, "Average category score could not be rounded off");
                            }
                            if (averageCategoryScore != 0.0) {
                                tableRow = evaluationResultsTable.getRow(i++);
                                tableRow.getCell(0).setText(String.valueOf((char) ++e));
                                tableRow.getCell(1).setText(category);
                                tableRow.getCell(2).setText(String.valueOf(averageCategoryScore));
                                totalEvaluationScore += averageCategoryScore;
                            }

                        }

                        if (noOfCategoriesUsed > 0) {
                            percentageEvaluationScore = ((totalEvaluationScore / noOfCategoriesUsed) / 5) * 100;
                        } else {
                            return;
                        }
                        try {
                            percentageEvaluationScore = Double.parseDouble(decimalFormat.format(percentageEvaluationScore));
                        } catch (Exception ex) {
                            logger.log(Level.INFO, "Percentage evaluation score could not be rounded off");
                        }

                        XWPFTableRow summaryRow = evaluationResultsTable.getRow(i);
                        summaryRow.getCell(1).setText("Percentage Rating(" + (char) 65 + " to " + (char) e + ")");
                        summaryRow.getCell(2).setText(String.valueOf(percentageEvaluationScore) + "%");

                        evaluationSummaryReport.createParagraph().createRun().addBreak();

                        XWPFTable summaryTable = evaluationSummaryReport.createTable(2, 3);
                        width = summaryTable.getCTTbl().getTblPr().addNewTblW();
                        width.setType(STTblWidth.DXA);
                        width.setW(BigInteger.valueOf(9072));

                        headerRow = summaryTable.getRow(0);
                        headerRow.getCell(0).setText("Course Code");
                        headerRow.getCell(1).setText("Course Name");
                        headerRow.getCell(2).setText("% Overall Score");

                        tableRow = summaryTable.getRow(1);
                        tableRow.getCell(0).setText(c.getCourse().getCode());
                        tableRow.getCell(1).setText(c.getCourse().getTitle());
                        tableRow.getCell(2).setText(String.valueOf(percentageEvaluationScore));

                        try {
                            evaluationSummaryReport.write(outStream);
                            outStream.close();
                        } catch (IOException ex) {
                            logger.log(Level.INFO, "Evaluation summary report could not be written to");
                            continue;
                        }

                        logger.log(Level.INFO, "\n\n\033[32;3mEvaluation summary report generated successfully\n");
                    }
                    //</editor-fold>

                    //<editor-fold defaultstate="collapsed" desc="Generate comments and reasons dump file">
                    for (CourseOfSessionDetails c : courses) {
                        List<AssessedEvaluationDetails> assessedEvaluations = new ArrayList<>();
                        try {
                            assessedEvaluations = assessedEvaluationService.retrieveAssessedEvaluationsByCourse(c);
                        } catch (InvalidArgumentException | InvalidStateException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the assessed evaluation of this course of session");
                        }

                        XWPFDocument commentsReport = new XWPFDocument();
                        String filePath;
                        try {
                            filePath = "Evaluation reports"
                                    + File.separator + evaluationSession.getAcademicYear().replaceAll("[\\\\/*<>|\"?]", "-") + " [academic year]"
                                    + File.separator + c.getCourse().getCode().replaceAll("[\\\\/*<>|\"?]", "-") + " [course code]"
                                    + File.separator + c.getFacultyMember().getPerson().getReferenceNumber().replaceAll("[\\\\/*<>|\"?]", "-") + " [staff number]"
                                    + File.separator + evaluationSession.getSemester().replaceAll("[\\\\/*<>|\"?]", "-") + " [semester]"
                                    + File.separator + "General comments.docx";
                        } catch (Exception e) {
                            logger.log(Level.INFO, "Comments dump file not created");
                            return;
                        }

                        new File(filePath).getParentFile().mkdirs();

                        FileOutputStream outStream;
                        try {
                            outStream = new FileOutputStream(filePath);
                        } catch (IOException e) {
                            logger.log(Level.INFO, "General comments document could not be created");
                            continue;
                        }

                        XWPFParagraph paragraph = commentsReport.createParagraph();

                        XWPFRun run = paragraph.createRun();

                        run.setText("EVALUATION GENERAL COMMENTS ");
                        run.addBreak();
                        run.setText("COURSE: " + c.getCourse().getCode().toUpperCase());
                        run.addBreak();
                        run.setText("LECTURER: " + c.getFacultyMember().getPerson().getFirstName().toUpperCase() + " " + c.getFacultyMember().getPerson().getLastName().toUpperCase());
                        run.addBreak();
                        run.setText("REFERENCE NO: " + c.getFacultyMember().getPerson().getReferenceNumber().toUpperCase());
                        run.addBreak();
                        run.setText("SEMESTER: " + evaluationSession.getSemester());
                        run.addBreak();
                        run.setText("ACADEMIC YEAR: " + evaluationSession.getAcademicYear());
                        run.addBreak();

                        int f = 64, j, d;
                        QuestionCategoryDetails questionCategoryHolder = new QuestionCategoryDetails();
                        for (QuestionCategoryDetails qc : questionCategories) {

                            j = 0;

                            for (AssessedEvaluationDetails ae : assessedEvaluations) {

                                if (ae.getQuestionCategory().equals(qc)) {

                                    if (ae.getEvaluatedQuestion().getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_LISTING_COMMENTS)) {
                                        if (questionCategoryHolder.getCategory() == null || !qc.getCategory().equals(questionCategoryHolder.getCategory())) {
                                            run.addBreak();
                                            run.setText((char) ++f + ". " + qc.getCategory().toUpperCase());
                                            run.addBreak();
                                            questionCategoryHolder.setCategory(qc.getCategory());
                                        }

                                        run.setText(++j + ". " + ae.getQuestionDescription() + ":");
                                        run.addBreak();
                                        List<EvaluatedQuestionAnswerDetails> evaluatedQuestionAnswers;
                                        try {
                                            evaluatedQuestionAnswers = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswers(ae.getEvaluatedQuestion(), evaluationSession, c);
                                        } catch (InvalidArgumentException | InvalidStateException ex) {
                                            logger.log(Level.INFO, "An error occurred during retrieval");
                                            return;
                                        }

                                        d = 0;
                                        for (EvaluatedQuestionAnswerDetails eqa : evaluatedQuestionAnswers) {
                                            try {
                                                if (eqa.getComment1() != null && eqa.getComment1().trim().length() > 0) {
                                                    run.addTab();
                                                    run.setText(String.valueOf((++d)) + ") " + eqa.getComment1());
                                                    run.addBreak();
                                                }
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                if (eqa.getComment2() != null && eqa.getComment2().trim().length() > 0) {
                                                    run.addTab();
                                                    run.setText(String.valueOf((++d)) + ") " + eqa.getComment2());
                                                    run.addBreak();
                                                }
                                            } catch (Exception ex) {
                                            }
                                            try {
                                                if (eqa.getComment3() != null && eqa.getComment3().trim().length() > 0) {
                                                    run.addTab();
                                                    run.setText(String.valueOf((++d)) + ") " + eqa.getComment3());
                                                    run.addBreak();
                                                }
                                            } catch (Exception ex) {
                                            }
                                        }
                                    } else if (ae.getEvaluatedQuestion().getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_REASONING)) {
                                        if (questionCategoryHolder.getCategory() == null || !qc.getCategory().equals(questionCategoryHolder.getCategory())) {
                                            run.addBreak();
                                            run.setText((char) ++f + ". " + qc.getCategory().toUpperCase());
                                            run.addBreak();
                                            questionCategoryHolder.setCategory(qc.getCategory());
                                        }

                                        run.setText(++j + ". " + ae.getQuestionDescription() + ":");
                                        run.addBreak();
                                        List<EvaluatedQuestionAnswerDetails> evaluatedQuestionAnswers;
                                        try {
                                            evaluatedQuestionAnswers = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswers(ae.getEvaluatedQuestion(), evaluationSession, c);
                                        } catch (InvalidArgumentException | InvalidStateException ex) {
                                            logger.log(Level.INFO, "An error occurred during retrieval");
                                            return;
                                        }

                                        d = 0;
                                        for (EvaluatedQuestionAnswerDetails eqa : evaluatedQuestionAnswers) {
                                            try {
                                                if (eqa.getReasoning() != null && eqa.getReasoning().trim().length() > 0) {
                                                    run.addTab();
                                                    run.setText(String.valueOf((++d)) + ") " + eqa.getReasoning());
                                                    run.addBreak();
                                                }
                                            } catch (Exception ex) {
                                                //We should do something, right?
                                            }

                                        }

                                    }

                                }

                            }

                        }
                        try {
                            commentsReport.write(outStream);
                            outStream.close();
                        } catch (IOException e) {
                            logger.log(Level.INFO, "The evaluation general comments report could not be writtent to");
                            continue;
                        }

                        logger.log(Level.INFO, "\n\n\033[32;3mEvaluation general comments dumped in a comments report successfully\n");
                    }
                    //</editor-fold>

                    //Deactivate the evaluation session
                    logger.log(Level.INFO, "Deactivating the evaluation session");
                    evaluationSession.setActive(Boolean.FALSE);

                    //Send the evaluation session details to the entity manager for record update
                    logger.log(Level.INFO, "Sending the evaluation session details to the entity manager for record update");
                    try {
                        evaluationSessionService.editEvaluationSession(evaluationSession);
                    } catch (InvalidArgumentException | InvalidStateException | DuplicateStateException ex) {
                        logger.log(Level.INFO, "An error occurred during evaluation session update");
                        return;
                    }

                    //Reload evaluation sessions displayed
                    displayEvaluationSession(response, request, session);
                    return;
            }

            destination = "WEB-INF/views" + path + ".jsp";
            try {
                logger.log(Level.INFO, "Dispatching request to: {0}", destination);
                request.getRequestDispatcher(destination).forward(request, response);
            } catch (ServletException | IOException e) {
                logger.log(Level.INFO, "Request dispatch failed");
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

    //<editor-fold defaultstate="collapsed" desc="Display evaluation session details">
    private void displayEvaluationSession(HttpServletResponse response, HttpServletRequest request, HttpSession session) throws IOException {
        //Declare the response writer
        PrintWriter out = response.getWriter();
        //Define the date format to be used
        DateFormat userDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        DateFormat databaseDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
        Date date;
        String dateString;
        List<DegreeDetails> degrees = new ArrayList<>();

        //Retrieve the faculty unique identifier if any
        logger.log(Level.INFO, "Retrieving the faculty unique identifier if any");
        faculty = new FacultyDetails();
        try {
            faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
            if (faculty.getId() == null) {
            logger.log(Level.INFO, "The faculty unique identifier is not available");
                faculty = null;
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "The faculty unique identifier is not available");
            faculty = null;
        }

        //Retrieve the department unique identifier if any
        logger.log(Level.INFO, "Retrieving the department unique identifier if any");
        department = new DepartmentDetails();
        try {
            department.setId(Integer.parseInt(request.getParameter("departmentId")));
            if (department.getId() == null) {
               logger.log(Level.INFO, "The department unique identifier is not available");
             department = null;
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "The department unique identifier is not available");
            department = null;
        }

        if (faculty != null) {
            try {
                degrees = degreeService.retrieveFacultyDegrees(faculty.getId());
            } catch (InvalidArgumentException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving the degrees");
            }
        } else if (department != null) {
            try {
                degrees = degreeService.retrieveDepartmentDegrees(department.getId());
            } catch (InvalidArgumentException ex) {
                logger.log(Level.INFO, "An error occurred while retrieving the degrees");
            }
        }

        //Retrieve the active evaluation sessions
        logger.log(Level.INFO, "Retrieving the active evaluation sessions");
        List<EvaluationSessionDetails> evaluationSessions = new ArrayList<>();
        try {
            evaluationSessions = evaluationSessionService.retrieveEvaluationSessions(degrees);
        } catch (InvalidArgumentException | InvalidStateException ex) {
            logger.log(Level.INFO, "An error occurred while retrieving the active evaluation session");
        }

        if (!evaluationSessions.isEmpty()) {
            //Generate evaluation session table body
            logger.log(Level.INFO, "Generating evaluation session table body");

            out.write("<thead>");
            out.write("<tr>");
            out.write("<th colspan=\"2\"> ACTIVE EVALUATION SESSIONS </th>");
            out.write("</tr>");
            out.write("</thead>");
            out.write("<tfoot>");
            out.write("<tr>");
            out.write("<td colspan=\"2\">Evaluation sessions</tr>");
            out.write("</tr>");
            out.write("</tfoot>");

            int count = 0;
            out.write("<tbody>");
            for (EvaluationSessionDetails es : evaluationSessions) {
                if (count++ > 0) {
                    out.write("<tr> <th colspan=\"2\"> </th> </tr>");
                }
                out.write("<tr>");
                out.write("<td> Degree: </td>");
                out.write("<td>");
                out.write("<select id=\"course-of-session-degree\">");
                for (DegreeDetails d : degrees) {
                    out.write("<option value=\"" + d.getId() + "\">" + d.getName() + "</option>");
                }
                out.write("</select>");
                out.write("</td>");
                out.write("<tr>");
                out.write("<td> Start date: </td>");
                try {
                    //Retrieve the date
                    date = databaseDateFormat.parse(databaseDateFormat.format(es.getStartDate()));

                    //Format the date string to MM/dd/yyyy
                    dateString = userDateFormat.format(date);

                    //Display the date
                    out.write("<td> <input type=\"text\" class=\"start-date\" value=\"" + dateString + "\"> </td>");
                } catch (ParseException ex) {
                    logger.log(Level.INFO, "String is unparseable to date format");
                }
                out.write("</tr>");
                out.write("<tr>");
                out.write("<td> End date: </td>");
                out.write("<td> ");
                try {
                    //Retrieve the date
                    date = databaseDateFormat.parse(databaseDateFormat.format(es.getEndDate()));

                    //Format the date string to MM/dd/yyyy
                    dateString = userDateFormat.format(date);

                    //Display the date
                    out.write("<input type=\"text\" class=\"end-date\" onchange=\"checkDate(); return false;\" value=\"" + dateString + "\">");
                } catch (ParseException ex) {
                    logger.log(Level.INFO, "String is unparseable to date format");
                }
                out.write("<span id=\"information-box\" class=\"alert alert-warning\" hidden></span> ");
                out.write("</td>");
                out.write("</tr>");
                out.write("<tr>");
                out.write("<td> Academic year: </td>");
                out.write("<td> <input type=\"text\" id=\"academic-year\" value=\"" + es.getAcademicYear() + "\"> </td>");
                out.write("</tr>");
                out.write("<tr>");
                out.write("<td> Admission month & year </td>");
                out.write("<td> <input type=\"date\" class=\"admission-month-year\" value=\"" + es.getAdmissionYear() + "\"/> </td>");
                out.write("</tr>");
                out.write("<tr>");
                out.write("<td> Semester: </td>");
                out.write("<td> <input type=\"text\" id=\"semester\" value=\"" + es.getSemester() + "\"> </td>");
                out.write("</tr>");
                out.write("<tr>");
                out.write("<td colspan=\"2\"></td>");
                out.write("</tr>");
                out.write("<tr>");
                if (faculty != null) {
                    out.write("<td>");
                    out.write("<button id=\"view-courses-of-session-button\" class=\"btn btn-default\" onclick=\"loadWindow('/Ocena/retrieveCoursesOfSession?evaluationSessionId=" + es.getId() + "&degreeId=" + es.getDegree().getId() + "&departmentId=&facultyId=" + faculty.getId() + "')\">Courses of this session</button>");
                    out.write("<input type=\"hidden\" name=\"facultyId\" id=\"facultyId\" value=\"" + faculty.getId() + "\">\n");
                    out.write("</td>");
                } else if (department != null) {
                    out.write("<td>");
                    out.write("<button id=\"view-courses-of-session-button\" class=\"btn btn-default\" onclick=\"loadWindow('/Ocena/retrieveCoursesOfSession?evaluationSessionId=" + es.getId() + "&degreeId=" + es.getDegree().getId() + "&departmentId=" + department.getId() + "&facultyId=')\">Courses of this session</button>");
                    out.write("<input type=\"hidden\" name=\"departmentId\" id=\"departmentId\" value=\"" + department.getId() + "\">\n");
                    out.write("</td>");
                }
                out.write("<td>");
                out.write("<button id=\"edit-evaluation-session-button\" class=\"btn btn-default\" onclick=\"editEvaluationSession('" + es.getId() + "')\"> Edit this session </button>");
                out.write("<button id=\"close-evaluation-session-button\" class=\"btn btn-default\" onclick=\"closeEvaluationSession('" + es.getId() + "')\"> Close this session </button>");
                out.write("</td>");
                out.write("</tr>");
            }
            out.write("</tbody>");
        }
    }

    //</editor-fold>
    private static final Logger logger = Logger.getLogger(EvaluationSessionController.class
            .getSimpleName());

}
