/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionAnswerDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;
import ke.co.miles.ocena.utilities.StudentFeedbackDetails;

/**
 *
 * @author Ben Siech
 */
@WebServlet(name = "EvaluationController", urlPatterns = {"/evaluationArena", "/updateCourses", "/updateDegrees", "/setCourse", "/retrieveEvaluationQuestions", "/performEvaluation", "/evaluationComplete"})
public class EvaluationController extends Controller {

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
        PrintWriter outWriter = response.getWriter();

        HttpSession session = request.getSession();
        String path = request.getServletPath();
        Integer admissionId;
        List<DegreeDetails> degrees;
        String destination;
        ArrayList<String> urlPatterns = new ArrayList<>();

        boolean adminSession, evaluatorSession = false;
        try {
            adminSession = (boolean) session.getAttribute("mainAdminSession");

            if (adminSession) {
                urlPatterns.clear();
                urlPatterns.add("/updateDegrees");
                urlPatterns.add("/updateCourses");
                urlPatterns.add("/retrieveEvaluationQuestions");
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "Main admin session is null");
            logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        if (adminSession == false) {
            try {
                adminSession = (boolean) session.getAttribute("subAdminSession");

                if (adminSession) {
                    urlPatterns.clear();
                    urlPatterns.add("/updateDegrees");
                    urlPatterns.add("/updateCourses");
                    urlPatterns.add("/retrieveEvaluationQuestions");
                }
            } catch (Exception e) {
                logger.log(Level.INFO, "Sub admin session is null");
                logger.log(Level.INFO, "Requesting dispatch to forward to: index.jsp");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }
        }

        if (adminSession == false) {

            evaluatorSession = (boolean) session.getAttribute("evaluatorSession");
            if (evaluatorSession) {
                urlPatterns.clear();
                urlPatterns.add("/setCourse");
                urlPatterns.add("/updateDegrees");
                urlPatterns.add("/updateCourses");
                urlPatterns.add("/evaluationArena");
                urlPatterns.add("/performEvaluation");
                urlPatterns.add("/evaluationComplete");
                urlPatterns.add("/retrieveEvaluationQuestions");
            }

        }

        //Check session type
        logger.log(Level.INFO, "Checking session type");
        if (adminSession || evaluatorSession) {
            //Admin session not established
            logger.log(Level.INFO, "Responding to the request");

            if (urlPatterns.contains(path)) {
                switch (path) {

                    case "/evaluationArena":

                        //Go to evaluation arena page
                        path = "/evaluationArena";
                        logger.log(Level.INFO, "Path is : {0}", path);

                        break;

                    case "/updateDegrees":

                        //Read in the admission unique identifier
                        logger.log(Level.INFO, "Reading in the admission unique identifier");
                        admissionId = Integer.parseInt(request.getParameter("admissionId"));

                        //Read in the faculty unique identifier
                        logger.log(Level.INFO, "Reading in the faculty unique identifier");
                        faculty = new FacultyDetails();
                        try {
                            faculty = facultyService.retrieveFaculty(Integer.parseInt(request.getParameter("facultyId")));
                        } catch (NumberFormatException | InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "The faculty unique identifier was not passed in");
                            faculty = null;
                        }

                        //Read in the department unique identifier
                        logger.log(Level.INFO, "Reading in the department unique identifier");
                        department = new DepartmentDetails();
                        try {
                            department = departmentService.retrieveDepartment(Integer.parseInt(request.getParameter("departmentId")));
                        } catch (NumberFormatException | InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "The department unique identifier was not passed in");
                            department = null;
                        }

                        //Retrieve the degrees by admission and faculty or department
                        logger.log(Level.INFO, "Retrieving the degrees by admission and faculty or department");
                        degrees = new ArrayList<>();
                        if (faculty != null) {
                            try {
                                degrees = degreeService.retrieveDegreesOfFacultyOrDepartmentAndAdmission(faculty, admissionId);
                            } catch (InvalidStateException ex) {
                                logger.log(Level.INFO, "An error occurred during degree records retrieval");
                                return;
                            }
                        } else if (department != null) {
                            try {
                                degrees = degreeService.retrieveDegreesOfFacultyOrDepartmentAndAdmission(department, admissionId);
                            } catch (InvalidStateException ex) {
                                logger.log(Level.INFO, "An error occurred during degree records retrieval");
                                return;
                            }
                        }

                        //Update the degree options
                        outWriter.write("<option disabled selected> Select degree </option>");
                        for (DegreeDetails d : degrees) {
                            outWriter.write(" <option value=\"" + d.getId() + "\"> " + d.getName() + " </option>\n");
                        }
                        outWriter.flush();
                        outWriter.close();
                        return;

                    case "/updateCourses":
                        //Read in the degree unique identifier
                        logger.log(Level.INFO, "Reading in the degree unique identifier");
                        Integer degreeId = Integer.parseInt(request.getParameter("degreeId"));

                        //Avail the degree unique identifier on session
                        logger.log(Level.INFO, "Availing the degree unique identifier on session");
                        session.setAttribute("degreeId", degreeId);

                        //Read in the evaluation instance from the session
                        logger.log(Level.INFO, "Reading in the evaluation instance from the session");
                        evaluationInstance = new EvaluationInstanceDetails();
                        evaluationInstance = null;
                        try {
                            evaluationInstance = (EvaluationInstanceDetails) session.getAttribute("evaluationInstance");
                        } catch (Exception e) {
                            logger.log(Level.INFO, "The evaluation instance is not set");
                        }

                        //Retrieve the evaluation session
                        logger.log(Level.INFO, "Retrieving the evaluation session from the database");
                        try {
                            evaluationSession = evaluationSessionService.retrieveEvaluationSessionByDegree(degreeId, ((FacultyMemberDetails) session.getAttribute("facultyMember")).getAdmissionYear());
                        } catch (InvalidArgumentException | InvalidStateException ex) {
                            logger.log(Level.INFO, "An error occurred while retrieving the evaluation session");
                        }

                        try {
                            evaluationInstance.getId();
                        } catch (Exception e) {
                            //Create an evaluation instance object and fill it up
                            logger.log(Level.INFO, "Creating an evaluation instance object and filling it");
                            evaluationInstance = new EvaluationInstanceDetails();
                            evaluationInstance.setActive(Boolean.TRUE);
                            evaluationInstance.setEvaluationSession(evaluationSession);
                            try {
                                evaluationInstance.setAnonymousIdentity(accessService.generateAnonymousIdentity(((PersonDetails) session.getAttribute("person")).getReferenceNumber()));
                            } catch (NoSuchAlgorithmException ex) {
                                logger.log(Level.INFO, "An error occurred while generating anonymous identity");
                                return;
                            }

                            //Send the evaluation instance object to the entity manager for record creation
                            logger.log(Level.INFO, "Sending the evaluation instance object to the entity manager for record creation");
                            try {
                                evaluationInstance = evaluationInstanceService.addEvaluationInstance(evaluationInstance);
                            } catch (Exception ex) {
                                logger.log(Level.INFO, "An error occurred during evaluation instance record creation");
                            }

                            //Avail the evaluation instance on session
                            logger.log(Level.INFO, "Availing the evaluation instance on session");
                            session.setAttribute("evaluationInstance", evaluationInstance);
                        }

                        //Retrieve the active evaluation session for this degree
                        logger.log(Level.INFO, "Retrieving the active evaluation session for this degree");
                        facultyMember = new FacultyMemberDetails();
                        try {
                            facultyMember = (FacultyMemberDetails) session.getAttribute("facultyMember");
                        } catch (Exception e) {
                            logger.log(Level.INFO, "No faculty member details retrieved");
                            return;
                        }

                        //Retrieve the active evaluation session for this degree
                        logger.log(Level.INFO, "Retrieving the active evaluation session for this degree");
                        evaluationSession = new EvaluationSessionDetails();
                        try {
                            evaluationSession = evaluationSessionService.retrieveEvaluationSessionByDegree(degreeId, ((FacultyMemberDetails) session.getAttribute("facultyMember")).getAdmissionYear());
                        } catch (InvalidArgumentException | InvalidStateException e) {
                        }

                        //Avail the evaluation session on session
                        logger.log(Level.INFO, "Availing the evaluation session on session");
                        session.setAttribute("evaluationSession", evaluationSession);

                        //Retrieve the courses of this degree's evaluation session
                        logger.log(Level.INFO, "Retrieving the courses of this degree's evaluation session");
                        List<CourseOfSessionDetails> courses = new ArrayList<>();
                        try {
                            courses = courseOfSessionService.retrieveCoursesOfSession(evaluationSession);
                        } catch (InvalidArgumentException | InvalidStateException e) {
                        }

                        //Read in the evaluation instance from the session
                        logger.log(Level.INFO, "Reading in the evaluation instance from the session");
                        try {
                            evaluationInstance = (EvaluationInstanceDetails) session.getAttribute("evaluationInstance");
                        } catch (Exception e) {
                            logger.log(Level.INFO, "The evaluation instance is not set");
                        }

                        //Retrieve the courses of this evaluation instance
                        logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                        List<CourseOfInstanceDetails> instanceCourses = new ArrayList<>();
                        try {
                            instanceCourses = courseOfInstanceService.retrieveCoursesOfInstance(evaluationInstance.getId());
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                        }

                        //Remove evaluated courses
                        if (!instanceCourses.isEmpty()) {
                            for (CourseOfInstanceDetails instanceCourse : instanceCourses) {
                                logger.log(Level.INFO, "Removing this evaluated course {0} from the list courses of session", instanceCourse.getCourseOfSession().getId());
                                courses.remove(instanceCourse.getCourseOfSession());
                            }
                        }

                        //Avail the courses on session
                        logger.log(Level.INFO, "Availing the courses on session");
                        session.setAttribute("courses", courses);

                        //Set a signal that evaluation is about to be completed
                        logger.log(Level.INFO, "Setting a signal that evaluation is about to be completed");
                        if (courses.size() == 1) {
                            session.setAttribute("complete", true);
                        } else {
                            session.setAttribute("complete", false);
                        }

                        //Update the courses options
                        outWriter.write("<option disabled selected> Select course </option>");
                        for (CourseOfSessionDetails c : courses) {
                            outWriter.write(" <option value=\"" + c.getId() + "\"> " + c.getCourse().getTitle() + " (" + c.getCourse().getCode() + ") </option>\n");
                        }

                        outWriter.flush();
                        outWriter.close();
                        return;

                    case "/setCourse":

                        //Read in the course of session unique identifier
                        logger.log(Level.INFO, "Reading in the course of session unique identifier");
                        Integer courseId = Integer.parseInt(request.getParameter("courseId"));

                        //Avail the course of session unique identifier on session
                        logger.log(Level.INFO, "Availing the course of session unique identifier on session");
                        session.setAttribute("courseId", courseId);

                        return;

                    case "/retrieveEvaluationQuestions":

                        //Read in the unique identifier and obtain respective course of session
                        logger.log(Level.INFO, "Reading in the unique identifier and obtaining respective course of session");
                        try {
                            courseOfSession = courseOfSessionService.retrieveCourseOfSession((Integer) session.getAttribute("courseId"));
                        } catch (InvalidArgumentException ex) {
                            logger.log(Level.INFO, "An error occurred while obtaining the course of session");
                        }

                        //Avail the course of session on session
                        logger.log(Level.INFO, "Availing the course of session on session");
                        session.setAttribute("courseOfSession", courseOfSession);

                        //Read in the faculty unique identifier if any
                        logger.log(Level.INFO, "Reading in the faculty unique identifier if any");
                        faculty = new FacultyDetails();
                        try {
                            faculty.setId(Integer.parseInt(request.getParameter("facultyId")));
                        } catch (Exception e) {
                            logger.log(Level.INFO, "The faculty is null");
                            faculty = null;
                        }

                        //Read in the department unique identifier if any
                        logger.log(Level.INFO, "Reading in the department unique identifier if any");
                        department = new DepartmentDetails();
                        try {
                            department.setId(Integer.parseInt(request.getParameter("departmentId")));
                        } catch (Exception e) {
                            logger.log(Level.INFO, "The department is null");
                            department = null;
                        }

                        //Display the evaluation questions
                        logger.log(Level.INFO, "Displaying the evaluation questions");
                        if (faculty != null) {
                            availEvaluationQuestions(session, response, faculty, courseOfSession);
                        } else if (department != null) {
                            availEvaluationQuestions(session, response, department, courseOfSession);
                        }

                        return;

                    case "/performEvaluation":
                        //Read in the map of evaluation questions in question categories from the session
                        logger.log(Level.INFO, "Reading in the map of evaluation questions in question categories from the session");
                        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = (Map<QuestionCategoryDetails, List<QuestionDetails>>) session.getAttribute("questionsInQuestionCategoryMap");

                        //Extract the list of questions from the map
                        logger.log(Level.INFO, "Extract the list of questions from the map");
                        List<QuestionDetails> questions = new ArrayList<>();
                        for (QuestionCategoryDetails qcd : questionsInQuestionCategoryMap.keySet()) {
                            for (QuestionDetails qd : questionsInQuestionCategoryMap.get(qcd)) {
                                questions.add(qd);
                            }
                        }

                        //Read in the evaluation instance from session
                        logger.log(Level.INFO, "Reading in the evaluation instance from session");
                        evaluationInstance = (EvaluationInstanceDetails) session.getAttribute("evaluationInstance");

                        //Read in the course of session
                        logger.log(Level.INFO, "Reading in the course of session");
                        courseOfSession = (CourseOfSessionDetails) session.getAttribute("courseOfSession");

                        //Set the corresponding course of instance
                        logger.log(Level.INFO, "Setting the corresponding course of instance");
                        courseOfInstance = new CourseOfInstanceDetails();
                        courseOfInstance.setCourseOfSession(courseOfSession);
                        courseOfInstance.setActive(Boolean.TRUE);
                        courseOfInstance.setEvaluationInstance(evaluationInstance);
                        try {
                            courseOfInstance = courseOfInstanceService.addCourseOfInstance(courseOfInstance);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while setting the course of instance");

                            //Go back to the evaluation arena
                            path = "/evaluationArena";
                            logger.log(Level.INFO, "Path is : {0}", path);
                            break;
                        }

                        //Read in the evaluation made for each question
                        logger.log(Level.INFO, "Reading in the evaluation made for each question");
                        for (QuestionDetails qd : questions) {

                            //Create an evaluated question object and fill up the question
                            logger.log(Level.INFO, "Creating an evaluated question object and fill up the question");
                            evaluatedQuestion = new EvaluatedQuestionDetails();
                            evaluatedQuestion.setQuestion(qd.getQuestion());

                            //Instantiate a new object for the evaluated question answer
                            logger.log(Level.INFO, "Instantiating a new object for the evaluated question answer");
                            evaluatedQuestionAnswer = new EvaluatedQuestionAnswerDetails();

                            //Fill up object fields with values 
                            logger.log(Level.INFO, "Filling up the object fields with values");
                            evaluatedQuestionAnswer.setActive(true);
                            evaluatedQuestionAnswer.setCourseOfInstance(courseOfInstance);
                            evaluatedQuestionAnswer.setEvaluatedQuestion(evaluatedQuestion);
                            evaluatedQuestionAnswer.setEvaluationInstance(evaluationInstance);
                            if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_RATING) {
                                evaluatedQuestionAnswer.setRating(request.getParameter("evaluation-rating" + qd.getId()));
                            } else if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_REASONING) {
                                evaluatedQuestionAnswer.setReasoning(request.getParameter("evaluation-reasoning" + qd.getId()));
                            } else if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_LISTING_COMMENTS) {
                                evaluatedQuestionAnswer.setComment1(request.getParameter("evaluation-comment-one" + qd.getId()));
                                evaluatedQuestionAnswer.setComment2(request.getParameter("evaluation-comment-two" + qd.getId()));
                                evaluatedQuestionAnswer.setComment3(request.getParameter("evaluation-comment-three" + qd.getId()));
                            }

                            //Send the evaluation question answer to the entity manager for recording
                            logger.log(Level.INFO, "Sending the evaluation question answer to the entity manager for recording");
                            try {
                                evaluatedQuestionAnswerService.addEvaluatedQuestionAnswer(evaluatedQuestionAnswer);
                            } catch (Exception e) {
                                logger.log(Level.INFO, "An error occurred during evaluation question answer record creation");
                            }
                        }

                        String name = ((PersonDetails) session.getAttribute("person")).getFirstName().toLowerCase();
                        String uppedName = "";
                        for (String split : name.split(" ")) {
                            split = Character.toUpperCase(split.charAt(0)) + split.substring(1);
                            uppedName += ((uppedName.equals("") ? "" : " ") + split);
                        }

                        //Set the feedback string for a single completed course evaluation
                        String courseEvaluationInfo = uppedName + ", "
                                + "you have completed the course/lecturer evaluation for " + courseOfSession.getCourse().getTitle() + ". "
                                + "Kindly select the next course to evaluate below.";
                        session.setAttribute("courseEvaluationInfo", courseEvaluationInfo);

                        //Go back to the evaluation arena
                        path = "/evaluationArena";
                        logger.log(Level.INFO, "Path is : {0}", path);

                        //Inform user that evaluation is complete
                        if ((Boolean) session.getAttribute("complete") == true) {

                            courseEvaluationInfo = "";
                            session.setAttribute("courseEvaluationInfo", courseEvaluationInfo);

                            //Retrieve student feedbacks
                            logger.log(Level.INFO, "Retrieving student feedbacks");
                            List<StudentFeedbackDetails> feedbacks = new ArrayList<>();
                            try {
                                feedbacks = studentFeedbackService.retrieveStudentFeedbacks((FacultyMemberDetails) session.getAttribute("facultyMember"));
                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred while retrieving the student feedbacks");
                            }

                            //Define the date format to be used
                            DateFormat dateTimeFormat = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.ENGLISH);
                            DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.DEFAULT, Locale.ENGLISH);
                            String dateString;
                            Date date = new Date();
                            Date dateTime = date;

                            try {
                                //Retrieve the date
                                dateTime = dateTimeFormat.parse(dateTimeFormat.format(dateTime));
                            } catch (ParseException ex) {
                                logger.log(Level.INFO, "An error occurred while parsing the date");
                            }

                            //Format the date string 
                            dateString = dateFormat.format(date);

                            //Generate feedback string
                            String feedback = uppedName + ", "
                                    + "you have completed the course/lecturer evaluation for this evaluation session. "
                                    + "We are happy to inform you that your opinions, comments and reasonings have been received and we are grateful. "
                                    + "You completed your evaluation on " + dateString + ". Thank you very much!";

                            //Create new student feedback
                            logger.log(Level.INFO, "Creating new student feedback");
                            studentFeedback = new StudentFeedbackDetails();
                            studentFeedback.setFeedback(feedback);
                            studentFeedback.setDateCompleted(dateTime);
                            studentFeedback.setActive(Boolean.TRUE);
                            studentFeedback.setEvaluationSession((EvaluationSessionDetails) session.getAttribute("evaluationSession"));
                            studentFeedback.setFacultyMember((FacultyMemberDetails) session.getAttribute("facultyMember"));

                            //Send the new student feedback to the entity manager for recording
                            try {
                                studentFeedbackService.addStudentFeedback(studentFeedback);
                            } catch (InvalidArgumentException ex) {
                                logger.log(Level.INFO, "Sending the new student feedback to the entity manager for recording");
                                return;
                            }

                            //Set feedback header message
                            String message = "Feedback for evaluation completed at ";

                            //Avail the feedbacks on session
                            logger.log(Level.INFO, "Availing the feedbacks on session");
                            session.setAttribute("currentMessage", message);
                            session.setAttribute("currentFeedback", studentFeedback);
                            if (feedbacks.isEmpty()) {
                                message = "";
                            }
                            session.setAttribute("otherMessage", message);
                            session.setAttribute("feedbacks", feedbacks);

                            //Redirect to evaluation complete page
                            path = "/evaluationComplete";
                            logger.log(Level.INFO, "Evaluation completed. Path is : {0}", path);
                        }

                        break;

                    case "/evaluationComplete":

                        //Retrieve student feedbacks
                        logger.log(Level.INFO, "Retrieving student feedbacks");
                        List<StudentFeedbackDetails> feedbacks = new ArrayList<>();
                        try {
                            feedbacks = studentFeedbackService.retrieveStudentFeedbacks((FacultyMemberDetails) session.getAttribute("facultyMember"));
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving the student feedbacks");
                        }

                        //Prepare the latest evaluation feedback for display
                        logger.log(Level.INFO, "Preparing the latest evaluation feedback for display");
                        String message = "";
                        studentFeedback = new StudentFeedbackDetails();
                        if (!feedbacks.isEmpty()) {
                            studentFeedback = feedbacks.get(0);
                            feedbacks.remove(0);
                            message = "Feedback for evaluation completed at ";
                        }

                        //Avail the feedbacks on session
                        logger.log(Level.INFO, "Availing the feedbacks on session");
                        session.setAttribute("currentFeedback", studentFeedback);
                        session.setAttribute("currentMessage", message);
                        if (feedbacks.isEmpty()) {
                            message = "";
                        }

                        session.setAttribute("otherMessage", message);
                        session.setAttribute("feedbacks", feedbacks);

                        //Redirect to evaluation complete page
                        path = "/evaluationComplete";
                        logger.log(Level.INFO, "Evaluation completed. Path is : {0}", path);
                        break;

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

    //<editor-fold defaultstate="collapsed" desc="Avail evaluation questions">
    private void availEvaluationQuestions(HttpSession session, HttpServletResponse response, Object object, CourseOfSessionDetails courseOfSession) throws IOException {
        List<RatingDetails> ratings = new ArrayList<>();
        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = new HashMap<>();

        //Determine the object's identity and cast it to the appropriate class then retrieve evaluation questions about it
        logger.log(Level.INFO, "Determining object's identity");
        if (object instanceof FacultyDetails) {

            logger.log(Level.INFO, "Casting the object to FacultyDetails");
            faculty = (FacultyDetails) object;

            //Retrieve the map of questions of a faculty in question categories from the database
            logger.log(Level.INFO, "Retrieving the map of questions of a faculty in question categories from the database");
            try {
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfFacultyByQuestionCategories(faculty);
            } catch (InvalidArgumentException ex) {
                logger.log(Level.INFO, "An error occurred during rating record retrieval");
                return;
            }

        } else if (object instanceof DepartmentDetails) {

            logger.log(Level.INFO, "Casting the object to DepartmentDetails");
            department = (DepartmentDetails) object;

            //Retrieve the map of questions of a department in question categories from the database
            logger.log(Level.INFO, "Retrieving the map of questions of a department in question categories from the database");
            try {
                questionsInQuestionCategoryMap = questionService.retrieveQuestionsOfDepartmentByQuestionCategories(department);
            } catch (InvalidArgumentException ex) {
                logger.log(Level.INFO, "An error occurred during rating record retrieval");
                return;
            }

        }

        //Avail map of evaluation questions 
        logger.log(Level.INFO, "Availing the map of evaluation questions on session");
        session.setAttribute("questionsInQuestionCategoryMap", questionsInQuestionCategoryMap);

        //Retrieve the lecturer/tutor for the course of session to be evaluated
        logger.log(Level.INFO, "Retrieve the lecturer/tutor for the course of session to be evaluated");
        facultyMember = new FacultyMemberDetails();
        try {
            facultyMember = facultyMemberService.retrieveSpecificFacultyMember(courseOfSession.getFacultyMember().getId());
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred while retrieving the lecturer/tutor");
        }

        //Display the evaluation questions
        logger.log(Level.INFO, "Displaying the evaluation questions");
        PrintWriter out = response.getWriter();
        int questionIndex = 0, categoryIndex = 0;
        out.write("<h1> Perform evaluation of " + courseOfSession.getCourse().getTitle() + " by " + facultyMember.getPerson().getFirstName() + "&nbsp;" + facultyMember.getPerson().getLastName() + " </h1>");
        out.write("<div id=\"rating-labels\">");
        out.write("<label class=\"info\"> N/A = Not Applicable </label>");
        out.write("<label class=\"info\"> 1 = Poor </label>");
        out.write("<label class=\"info\"> 2 = Reasonable </label>");
        out.write("<label class=\"info\"> 3 = Satisfactory </label>");
        out.write("<label class=\"info\"> 4 = Good </label>");
        out.write("<label class=\"info\"> 5 = Very good </label>");
        out.write("</div>");
        out.write("<div>");
        out.write("<form action=\"/Ocena/performEvaluation\" method=\"POST\">");
        out.write("<table id=\"perform-evaluation-question-table\" class=\"table table-responsive table-hover parent-table\">");
        out.write("<thead>");
        out.write("<tr>");
        out.write(" <th>&nbsp;</th>");
        out.write("<th>&nbsp;</th>");
        out.write("<th colspan=\"7\"> Evaluation question </th>");
        out.write("</tr>");
        out.write("</thead>");
        out.write("<tfoot>");
        out.write("<tr>");
        out.write("<td colspan=\"9\"><input type=\"submit\" value=\"Submit evaluation\" class=\"btn btn-default\"></td>");
        out.write("</tr>");
        out.write("<tr>");
        out.write("<br>");
        out.write("</tr>");
        out.write("</tfoot>");
        out.write("<tbody>");
        for (QuestionCategoryDetails qc : questionsInQuestionCategoryMap.keySet()) {
            out.write("<tr>");
            out.write("<td> <strong>" + String.valueOf((char) ('A' + categoryIndex++)) + ". </strong> </td>");
            out.write("<td colspan=\"8\"> <strong> " + qc.getCategory() + " </strong> </td>");
            out.write("</tr>");
            for (QuestionDetails qd : questionsInQuestionCategoryMap.get(qc)) {
                out.write("<tr>");
                out.write(" <td>&nbsp;</td>");
                out.write("<td> " + ++questionIndex + ". </td>");
                if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_RATING) {
                    out.write("<td class=\"evaluation-question-holder\"> " + qd.getQuestion() + " </td>");
                    if (qd.getRatingType() == RatingTypeDetail.BOOLEAN) {
                        try {
                            ratings = ratingService.retrieveRatingsByRatingType(RatingTypeDetail.BOOLEAN);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "an error occurred while retrieving rating records");
                        }

                    } else if (qd.getRatingType() == RatingTypeDetail.PERCENTAGE) {
                        try {
                            ratings = ratingService.retrieveRatingsByRatingType(RatingTypeDetail.PERCENTAGE);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "an error occurred while retrieving rating records");
                        }

                    } else if (qd.getRatingType() == RatingTypeDetail.STAR) {
                        try {
                            ratings = ratingService.retrieveRatingsByRatingType(RatingTypeDetail.STAR);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "an error occurred while retrieving rating records");
                        }

                    } else if (qd.getRatingType() == RatingTypeDetail.YES_OR_NO) {
                        try {
                            ratings = ratingService.retrieveRatingsByRatingType(RatingTypeDetail.YES_OR_NO);
                        } catch (Exception e) {
                            logger.log(Level.INFO, "an error occurred while retrieving rating records");
                        }
                    }

                    for (RatingDetails r : ratings) {
                        out.write("<td class=\"evaluation-rating-holder\">");
                        out.write("<input type=\"radio\" name=\"evaluation-rating" + qd.getId() + "\" value=\"" + r.getRating() + "\" required=\"true\">" + r.getRating());
                        out.write("</td>");
                    }

                    out.write("</tr>");

                } else if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_REASONING) {
                    out.write("<td colspan=\"7\"> " + qd.getQuestion() + " </td>");
                    out.write("</tr>");
                    out.write("<tr>");
                    out.write("<td colspan=\"2\"> &nbsp; </td>");
                    out.write("<td colspan=\"7\">");
                    out.write("<textarea name=\"evaluation-reasoning" + qd.getId() + "\" cols=\"150\"></textarea>");
                    out.write("</td>");
                    out.write("</tr>");

                } else if (qd.getMeansOfAnswering() == MeansOfAnsweringDetail.BY_LISTING_COMMENTS) {
                    out.write("<td colspan=\"7\"> " + qd.getQuestion() + " </td>");
                    out.write("</tr>");
                    out.write("<tr>");
                    out.write("<td colspan=\"2\"> &nbsp; </td>");
                    out.write("<td colspan=\"7\">");
                    out.write("(i) &nbsp; <input type=\"text\" name=\"evaluation-comment-one" + qd.getId() + "\" size=\"149\">");
                    out.write("</td>");
                    out.write("</tr>");
                    out.write("<tr>");
                    out.write("<td colspan=\"2\"> &nbsp; </td>");
                    out.write("<td colspan=\"7\">");
                    out.write("(ii) &nbsp;<input type=\"text\" name=\"evaluation-comment-two" + qd.getId() + "\" size=\"149\">");
                    out.write("</td>");
                    out.write("</tr>");
                    out.write("<tr>");
                    out.write("<td colspan=\"2\"> &nbsp; </td>");
                    out.write("<td colspan=\"7\">");
                    out.write("(iii) <input type=\"text\" name=\"evaluation-comment-three" + qd.getId() + "\" size=\"149\">");
                    out.write("</td>");
                    out.write("</tr>");

                }

            }
        }
        out.write("</tbody>");
        out.write("</table>");
        out.write("</div>");
        out.write("</form>");

    }
    //</editor-fold>

    private static final Logger logger = Logger.getLogger(EvaluationController.class.getSimpleName());

}
