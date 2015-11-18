/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.question;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.entities.MeansOfAnswering;
import ke.co.miles.ocena.entities.Question;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.entities.RatingType;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class QuestionRequests extends EntityRequests implements QuestionRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addQuestion(QuestionDetails details) throws InvalidArgumentException {
        //Method for adding a question record to the database
        logger.log(Level.INFO, "Entered the method for adding a question record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_005_01");
        } else if (details.getQuestion() == null || details.getQuestion().trim().length() == 0) {
            logger.log(Level.INFO, "The question is null");
            throw new InvalidArgumentException("error_005_02");
        } else if (details.getQuestion().trim().length() > 200) {
            logger.log(Level.INFO, "The question is longer than 200 characters");
            throw new InvalidArgumentException("error_005_03");
        } else if (details.getMeansOfAnswering() == null) {
            logger.log(Level.INFO, "The means of answering is null");
            throw new InvalidArgumentException("error_005_04");
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The category in which the question falls is null");
            throw new InvalidArgumentException("error_005_05");
        } else if (details.getFaculty() == null && details.getDepartment() == null) {
            logger.log(Level.INFO, "The faculty and department is null");
            throw new InvalidArgumentException("error_005_06");
        }

        //Checking if the question is unique to a faculty or department
        logger.log(Level.INFO, "Checking if the question is unique to a faculty or department");
        String objectName = null;
        if (details.getFaculty() != null) {
            q = em.createNamedQuery("Question.findByQuestionAndFacultyId");
            q.setParameter("question", details.getQuestion());
            q.setParameter("facultyId", details.getFaculty().getId());
            objectName = "faculty";
        } else if (details.getDepartment() != null) {
            q = em.createNamedQuery("Question.findByQuestionAndDepartmentId");
            q.setParameter("question", details.getQuestion());
            q.setParameter("departmentId", details.getDepartment().getId());
            objectName = "department";
        }
        try {
            question = (Question) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Question is available for use in this {0}", objectName);
            question = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (question != null) {
            logger.log(Level.INFO, "Question exists already in the database for this {0}", objectName);
            throw new InvalidArgumentException("error_005_07", null, objectName);
        }

        //Creating a container to hold question record
        logger.log(Level.INFO, "Creating a container to hold question record");
        question = new Question();
        question.setActive(details.getActive());
        question.setQuestion(details.getQuestion());
        question.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        question.setMeansOfAnswering(em.find(MeansOfAnswering.class, details.getMeansOfAnswering().getId()));
        try {
            question.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Faculty is null - question does not belong to a faculty");
        }
        try {
            question.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Department is null - question does not belong to a department");
        }
        try {
            question.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Rating type is null - question is not answered by ratingF");
        }

        //Adding a question record to the database
        logger.log(Level.INFO, "Adding a question record to the database");
        try {
            em.persist(question);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return question.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<QuestionDetails> retrieveQuestionsInFaculty(Integer facultyId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving question of a faculty from the database
        logger.log(Level.INFO, "Entered the method for retrieving question of a faculty from the database");

        if (facultyId == null) {
            throw new InvalidArgumentException("error_005_08");
        }

        //Retrieving question records from the database
        logger.log(Level.INFO, "Retrieving question records from the database");
        q = em.createNamedQuery("Question.findByFacultyId");
        q.setParameter("facultyId", facultyId);
        List<Question> questions = new ArrayList<>();
        try {
            questions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of question records
        logger.log(Level.INFO, "Returning the details list of question records");
        return convertQuestionsToQuestionDetailsList(questions);
    }

    @Override
    public List<QuestionDetails> retrieveQuestionsInDepartment(Integer departmentId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving question of a department from the database
        logger.log(Level.INFO, "Entered the method for retrieving question of a department from the database");

        if (departmentId == null) {
            throw new InvalidArgumentException("error_005_09");
        }

        //Retrieving question records from the database
        logger.log(Level.INFO, "Retrieving question records from the database");
        q = em.createNamedQuery("Question.findByDepartmentId");
        q.setParameter("departmentId", departmentId);
        List<Question> questions = new ArrayList<>();
        try {
            questions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of question records
        logger.log(Level.INFO, "Returning the details list of question records");
        return convertQuestionsToQuestionDetailsList(questions);
    }

    @Override
    public Map<MeansOfAnsweringDetail, List<QuestionDetails>> retrieveQuestionsByMeansOfAnswering() {
        //Method for retrieving a map of questions by means of answering of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions by means of answering of records in the database");

        //Retrieving means of answering records from the database
        logger.log(Level.INFO, "Retrieving the means of answering records from the database");
        q = em.createNamedQuery("MeansOfAnswering.findAll");
        List<MeansOfAnswering> meansOfAnsweringList = new ArrayList<>();
        try {
            meansOfAnsweringList = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve by means of answering from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions by means of answering from the database and putting them in a map");
        Map<MeansOfAnsweringDetail, List<QuestionDetails>> questionsByMeansOfAnsweringMap = new HashMap<>();
        q = em.createNamedQuery("Question.findByMeansOfAnsweringId");
        for (MeansOfAnswering m : meansOfAnsweringList) {
            q.setParameter("meansOfAnsweringId", m.getId());
            questionsByMeansOfAnsweringMap.put(
                    (MeansOfAnsweringDetail.getMeansOfAnswering(m.getId())),
                    convertQuestionsToQuestionDetailsList(q.getResultList())
            );
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions by means of answering");
        return questionsByMeansOfAnsweringMap;
    }

    @Override
    public Map<QuestionCategoryDetails, List<QuestionDetails>> retrieveQuestionsOfFacultyByQuestionCategories(FacultyDetails faculty) throws InvalidArgumentException {
        //Method for retrieving a map of questions in question category of records of a faculty in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions in question category of records records of a faculty in the database");

        if (faculty == null) {
            logger.log(Level.INFO, "The faculty for which the questions are to be retrieved are not provided");
            throw new InvalidArgumentException("error_005_11");
        } else if (faculty.getId() == null) {
            logger.log(Level.INFO, "The unique identifier of the department is not provided");
            throw new InvalidArgumentException("error_005_08");
        }

        //Retrieving question category records from the database
        logger.log(Level.INFO, "Retrieving the question category records from the database");
        q = em.createNamedQuery("QuestionCategory.findAll");
        List<QuestionCategory> questionCategories = new ArrayList<>();
        try {
            questionCategories = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve questions in categories from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions in categories  records of a faculty from the database and putting them in a map");
        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = new HashMap<>();
        q = em.createNamedQuery("Question.findByQuestionCategoryIdAndFacultyId");
        for (QuestionCategory qc : questionCategories) {
            q.setParameter("questionCategoryId", qc.getId());
            q.setParameter("facultyId", faculty.getId());
            questionsInQuestionCategoryMap.put(
                    questionCategoryService.convertQuestionCategoryToQuestionCategoryDetails(qc),
                    convertQuestionsToQuestionDetailsList(q.getResultList()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions in question categories records of a faculty ");
        return questionsInQuestionCategoryMap;
    }

    @Override
    public Map<QuestionCategoryDetails, List<QuestionDetails>> retrieveQuestionsOfDepartmentByQuestionCategories(DepartmentDetails department) throws InvalidArgumentException {
        //Method for retrieving a map of questions in question category of records of a department in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions in question category of records records of a department in the database");

        if (department == null) {
            logger.log(Level.INFO, "The department for which the questions are to be retrieved are not provided");
            throw new InvalidArgumentException("error_005_12");
        } else if (department.getId() == null) {
            logger.log(Level.INFO, "The unique identifier of the department is not provided");
            throw new InvalidArgumentException("error_005_09");
        }

        //Retrieving question category records from the database
        logger.log(Level.INFO, "Retrieving the question category records from the database");
        q = em.createNamedQuery("QuestionCategory.findAll");
        List<QuestionCategory> questionCategories = new ArrayList<>();
        try {
            questionCategories = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve questions in categories from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions in categories  records of a department from the database and putting them in a map");
        Map<QuestionCategoryDetails, List<QuestionDetails>> questionsInQuestionCategoryMap = new HashMap<>();
        q = em.createNamedQuery("Question.findByQuestionCategoryIdAndDepartmentId");
        for (QuestionCategory qc : questionCategories) {
            q.setParameter("questionCategoryId", qc.getId());
            q.setParameter("departmentId", department.getId());
            questionsInQuestionCategoryMap.put(questionCategoryService.convertQuestionCategoryToQuestionCategoryDetails(qc),
                    convertQuestionsToQuestionDetailsList(q.getResultList()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions in question categories records of a department ");
        return questionsInQuestionCategoryMap;
    }

    @Override
    public Map<FacultyDetails, List<QuestionDetails>> retrieveQuestionsByFaculty() {
        //Method for retrieving a map of questions in faculty of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions in faculty of records in the database");

        //Retrieve faculty records from the database
        logger.log(Level.INFO, "Retrieving the faculty records from the database");
        q = em.createNamedQuery("Faculty.findAll");
        List<Faculty> faculties = new ArrayList<>();
        try {
            faculties = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve questions in faculty from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions in faculty from the database and putting them in a map");
        Map<FacultyDetails, List<QuestionDetails>> questionsInFacultyMap = new HashMap<>();
        questionsInFacultyMap.put(null, null);
        try {
            q = em.createNamedQuery("Question.findByFacultyId");
            for (Faculty f : faculties) {
                q.setParameter("facultyId", f.getId());
                questionsInFacultyMap.put(facultyservice.convertFacultyToFacultyDetails(f),
                        convertQuestionsToQuestionDetailsList(q.getResultList()));
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while filling the map");
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions in faculty");
        return questionsInFacultyMap;
    }

    @Override
    public Map<RatingTypeDetail, List<QuestionDetails>> retrieveQuestionsByRatingType() {
        //Method for retrieving a map of questions by rating type of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions by rating type of records in the database");

        //Retrieving rating type records from the database
        logger.log(Level.INFO, "Retrieving the rating type records from the database");
        q = em.createNamedQuery("RatingType.findAll");
        List<RatingType> ratingTypes = new ArrayList<>();
        try {
            ratingTypes = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve by rating type from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions by rating type from the database and putting them in a map");
        Map<RatingTypeDetail, List<QuestionDetails>> questionsByRatingTypeMap = new HashMap<>();
        q = em.createNamedQuery("Question.findByRatingTypeId");
        for (RatingType r : ratingTypes) {
            q.setParameter("ratingTypeId", r.getId());
            questionsByRatingTypeMap.put(RatingTypeDetail.getRatingType(r.getId()),
                    convertQuestionsToQuestionDetailsList(q.getResultList()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions by rating type");
        return questionsByRatingTypeMap;
    }

    @Override
    public Map<DepartmentDetails, List<QuestionDetails>> retrieveQuestionsByDepartment() {
        //Method for retrieving a map of questions in department of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of questions in department of records in the database");

        //Retrieve department records from the database
        logger.log(Level.INFO, "Retrieving the department records from the database");
        q = em.createNamedQuery("Department.findAll");
        List<Department> departments = new ArrayList<>();
        try {
            departments = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve questions in department from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions in department from the database and putting them in a map");
        Map<DepartmentDetails, List<QuestionDetails>> questionsInDepartmentMap = new HashMap<>();
        questionsInDepartmentMap.put(null, null);
        try {
            q = em.createNamedQuery("Question.findByDepartmentId");
            for (Department d : departments) {
                q.setParameter("ratingTypeId", d.getId());
                questionsInDepartmentMap.put(departmentService.convertDepartmentToDepartmentDetails(d),
                        convertQuestionsToQuestionDetailsList(q.getResultList()));
            }
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while filling the map");
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of questions in department");
        return questionsInDepartmentMap;
    }

    @Override
    public Map<QuestionDetails, MeansOfAnsweringDetail> retrieveMeansOfAnsweringByQuestion(List<QuestionDetails> questions) throws InvalidArgumentException {
        //Method for retrieving a map of means of answering by questions of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of means of answering by questions of records in the database");

        if (questions == null) {
            logger.log(Level.INFO, "The questions for which the means of answering are to be retrieved are required");
            throw new InvalidArgumentException("error_005_13");
        }

        //Retrieve map of means of answering by question from the database and putting them in a map");
        logger.log(Level.INFO, "Retrieving map of means of answering by question from the database and putting them in a map");
        Map<QuestionDetails, MeansOfAnsweringDetail> meansOfAnsweringByQuestionMap = new HashMap<>();
        q = em.createNamedQuery("MeansOfAnswering.findById");
        for (QuestionDetails qq : questions) {
            q.setParameter("id", qq.getMeansOfAnswering().getId());
            meansOfAnsweringByQuestionMap.put(qq,
                    MeansOfAnsweringDetail.getMeansOfAnswering(((MeansOfAnswering) q.getSingleResult()).getId()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of means of answering by questions ");
        return meansOfAnsweringByQuestionMap;
    }

    @Override
    public Map<QuestionDetails, RatingTypeDetail> retrieveRatingTypesByQuestion(List<QuestionDetails> questions) throws InvalidArgumentException {
        //Method for retrieving a map of rating types by question of records in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of rating types by question of records in the database");

        if (questions == null) {
            logger.log(Level.INFO, "The questions for which the rating types are to be retrieved are required");
            throw new InvalidArgumentException("error_005_14");
        }

        //Retrieve by rating type from the database and put them in a map
        logger.log(Level.INFO, "Retrieving questions by rating type from the database and putting them in a map");
        Map<QuestionDetails, RatingTypeDetail> ratingTypesByQuestionMap = new HashMap<>();
        q = em.createNamedQuery("RatingType.findById");
        for (QuestionDetails qq : questions) {
            try {
                q.setParameter("id", qq.getRatingType().getId());
                ratingTypesByQuestionMap.put(qq,
                        RatingTypeDetail.getRatingType(((RatingType) q.getSingleResult()).getId()));
            } catch (Exception e) {
                logger.log(Level.SEVERE, "The rating type for this question is null");
            }
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of rating types by question");
        return ratingTypesByQuestionMap;
    }

    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editQuestion(QuestionDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a question record in the database
        logger.log(Level.INFO, "Entered the method for editing a question record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_005_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The question's unique identifier is null");
            throw new InvalidArgumentException("error_005_10");
        } else if (details.getQuestion() == null || details.getQuestion().trim().length() == 0) {
            logger.log(Level.INFO, "The question is null");
            throw new InvalidArgumentException("error_005_02");
        } else if (details.getQuestion().trim().length() > 200) {
            logger.log(Level.INFO, "The question is longer than 200 characters");
            throw new InvalidArgumentException("error_005_03");
        } else if (details.getMeansOfAnswering() == null) {
            logger.log(Level.INFO, "The means of answering is null");
            throw new InvalidArgumentException("error_005_04");
        } else if (details.getQuestionCategory() == null) {
            logger.log(Level.INFO, "The category in which the question falls is null");
            throw new InvalidArgumentException("error_005_05");
        } else if (details.getFaculty() == null && details.getDepartment() == null) {
            logger.log(Level.INFO, "The faculty and department is null");
            throw new InvalidArgumentException("error_005_06");
        }

        //Checking if the question is unique to a faculty or department
        logger.log(Level.INFO, "Checking if the question is unique to a faculty or department");
        String objectName = null;
        if (details.getFaculty() != null) {
            q = em.createNamedQuery("Question.findByQuestionAndFacultyId");
            q.setParameter("question", details.getQuestion());
            q.setParameter("facultyId", details.getFaculty().getId());
            objectName = "faculty";
        } else if (details.getDepartment() != null) {
            q = em.createNamedQuery("Question.findByQuestionAndDepartmentId");
            q.setParameter("question", details.getQuestion());
            q.setParameter("departmentId", details.getDepartment().getId());
            objectName = "department";
        }
        try {
            question = (Question) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Question is available for use in this {0}", objectName);
            question = null;
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (question != null) {
            if (!(question.getId().equals(details.getId()))) {
                logger.log(Level.INFO, "Question exists already in the database for this {0}", objectName);
                throw new InvalidArgumentException("error_005_07", null, objectName);
            }
        }

        //Creating a container to hold question record
        logger.log(Level.INFO, "Creating a container to hold question record");
        question = new Question();
        question.setId(details.getId());
        question.setActive(details.getActive());
        question.setQuestion(details.getQuestion());
        question.setQuestionCategory(em.find(QuestionCategory.class, details.getQuestionCategory().getId()));
        question.setMeansOfAnswering(em.find(MeansOfAnswering.class, details.getMeansOfAnswering().getId()));
        try {
            question.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Faculty is null - question does not belong to a faculty");
        }
        try {
            question.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Department is null - question does not belong to a department");
        }
        try {
            question.setRatingType(em.find(RatingType.class, details.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "Rating type is null - question is not answered by ratingF");
        }

        //Editing a question record in the database
        logger.log(Level.INFO, "Editing a question record in the database");
        try {
            em.merge(question);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new EJBException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeQuestion(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a question record from the database
        logger.log(Level.INFO, "Entered the method for removing a question record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier is null");
            throw new InvalidArgumentException("error_005_10");
        }

        //Removing a question record from the database
        logger.log(Level.INFO, "Removing a question record from the database");
        question = em.find(Question.class, id);
        try {
            em.remove(question);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
    //</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<QuestionDetails> convertQuestionsToQuestionDetailsList(List<Question> questions) {
        //Entered method for converting questions list to question details list
        logger.log(Level.FINE, "Entered method for converting questions list to question details list");

        //Convert list of questions to question details list
        logger.log(Level.FINE, "Convert list of questions to question details list");
        List<QuestionDetails> details = new ArrayList<>();
        for (Question a : questions) {
            details.add(convertQuestionToQuestionDetails(a));
        }

        //Returning converted question details list
        logger.log(Level.FINE, "Returning converted question details list");
        return details;
    }

    private QuestionDetails convertQuestionToQuestionDetails(Question question) {
        //Entered method for converting question to question details
        logger.log(Level.FINE, "Entered method for converting questions to question details");

        //Convert list of question to question details
        logger.log(Level.FINE, "Convert list of question to question details");

        facultyDetails = new FacultyDetails();
        try {
            facultyDetails.setId(question.getFaculty().getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "This question does not belong to a faculty");
        }

        departmentDetails = new DepartmentDetails();
        try {
            departmentDetails.setId(question.getDepartment().getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "This question does not belong to a department");
        }

        questionCategoryDetails = new QuestionCategoryDetails();
        questionCategoryDetails.setId(question.getQuestionCategory().getId());

        QuestionDetails details = new QuestionDetails();
        details.setId(question.getId());
        details.setFaculty(facultyDetails);
        details.setActive(question.getActive());
        details.setDepartment(departmentDetails);
        details.setVersion(question.getVersion());
        details.setQuestion(question.getQuestion());
        details.setQuestionCategory(questionCategoryDetails);
        details.setMeansOfAnswering(MeansOfAnsweringDetail.getMeansOfAnswering(question.getMeansOfAnswering().getId()));
        try {
            details.setRatingType(RatingTypeDetail.getRatingType(question.getRatingType().getId()));
        } catch (Exception e) {
            logger.log(Level.FINE, "The rating type is null");
        }

        //Returning converted question details
        logger.log(Level.FINE, "Returning converted question details");
        return details;
    }
//</editor-fold>

    private static final Logger logger = Logger.getLogger(QuestionRequests.class.getSimpleName());

}
