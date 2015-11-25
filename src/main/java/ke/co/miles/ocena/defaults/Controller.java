/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.defaults;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import ke.co.miles.ocena.requests.access.AccessRequestsLocal;
import ke.co.miles.ocena.requests.access.overalladmin.OverallAdminRequestsLocal;
import ke.co.miles.ocena.requests.admission.AdmissionRequestsLocal;
import ke.co.miles.ocena.requests.assessedevaluation.AssessedEvaluationCommentRequestsLocal;
import ke.co.miles.ocena.requests.assessedevaluation.AssessedEvaluationRequestsLocal;
import ke.co.miles.ocena.requests.college.CollegeRequestsLocal;
import ke.co.miles.ocena.requests.contact.ContactRequestsLocal;
import ke.co.miles.ocena.requests.contact.email.EmailContactRequestsLocal;
import ke.co.miles.ocena.requests.contact.phone.PhoneContactRequestsLocal;
import ke.co.miles.ocena.requests.contact.postal.PostalContactRequestsLocal;
import ke.co.miles.ocena.requests.country.CountryRequestsLocal;
import ke.co.miles.ocena.requests.course.CourseRequestsLocal;
import ke.co.miles.ocena.requests.coursesofinstance.CourseOfInstanceRequestsLocal;
import ke.co.miles.ocena.requests.coursesofsession.CourseOfSessionRequestsLocal;
import ke.co.miles.ocena.requests.degree.DegreeRequestsLocal;
import ke.co.miles.ocena.requests.department.DepartmentRequestsLocal;
import ke.co.miles.ocena.requests.evaluatedquestion.EvaluatedQuestionRequestsLocal;
import ke.co.miles.ocena.requests.evaluatedquestionanswer.EvaluatedQuestionAnswerRequestsLocal;
import ke.co.miles.ocena.requests.evaluationmarking.EvaluationMarkingRequestsLocal;
import ke.co.miles.ocena.requests.evaluationinstance.EvaluationInstanceRequestsLocal;
import ke.co.miles.ocena.requests.evaluationsession.EvaluationSessionRequestsLocal;
import ke.co.miles.ocena.requests.institution.InstitutionRequestsLocal;
import ke.co.miles.ocena.requests.person.PersonRequestsLocal;
import ke.co.miles.ocena.requests.question.QuestionRequestsLocal;
import ke.co.miles.ocena.requests.questioncategory.QuestionCategoryRequestsLocal;
import ke.co.miles.ocena.requests.faculty.FacultyRequestsLocal;
import ke.co.miles.ocena.requests.facultymember.FacultyMemberRequestsLocal;
import ke.co.miles.ocena.requests.rating.RatingRequestsLocal;
import ke.co.miles.ocena.requests.studentfeedback.StudentFeedbackRequestsLocal;
import ke.co.miles.ocena.requests.useraccount.UserAccountRequestsLocal;
import ke.co.miles.ocena.requests.zip.ZipRequestsLocal;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.AssessedEvaluationCommentDetails;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.CollegeDetails;
import ke.co.miles.ocena.utilities.ContactDetails;
import ke.co.miles.ocena.utilities.CountryDetails;
import ke.co.miles.ocena.utilities.CourseDetails;
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.EmailContactDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionAnswerDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.InstitutionDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.PersonDetails;
import ke.co.miles.ocena.utilities.PhoneContactDetails;
import ke.co.miles.ocena.utilities.PostalContactDetails;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.OverallAdminDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;
import ke.co.miles.ocena.utilities.StudentFeedbackDetails;
import ke.co.miles.ocena.utilities.UserAccountDetails;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
public class Controller extends HttpServlet {

    @PersistenceContext(name = "OcenaPU")
    protected EntityManager em;
    protected Query q;

    @EJB
    protected  ZipRequestsLocal zipService;

    @EJB
    protected OverallAdminRequestsLocal overallAdminService;

    protected OverallAdminDetails overallAdminDetails;

    @EJB
    protected EvaluationInstanceRequestsLocal evaluationInstanceService;

    @EJB
    protected AssessedEvaluationRequestsLocal assessedEvaluationService;

    protected AssessedEvaluationDetails assessedEvaluation;

    @EJB
    protected AssessedEvaluationCommentRequestsLocal assessedEvaluationCommentService;

    protected AssessedEvaluationCommentDetails assessedEvaluationComment;

    protected EvaluationInstanceDetails evaluationInstance;
    @EJB
    protected EvaluatedQuestionAnswerRequestsLocal evaluatedQuestionAnswerService;

    protected EvaluatedQuestionAnswerDetails evaluatedQuestionAnswer;
    @EJB
    protected EvaluatedQuestionRequestsLocal evaluatedQuestionService;

    protected EvaluatedQuestionDetails evaluatedQuestion;

    @EJB
    protected AccessRequestsLocal accessService;

    protected CourseOfSessionDetails courseOfSession;
    @EJB
    protected CourseOfSessionRequestsLocal courseOfSessionService;

    protected CourseDetails course;
    @EJB
    protected CourseRequestsLocal courseService;

    protected DegreeDetails degree;
    @EJB
    protected DegreeRequestsLocal degreeService;

    protected PersonDetails person;
    @EJB
    protected PersonRequestsLocal personService;

    protected RatingDetails rating;
    @EJB
    protected RatingRequestsLocal ratingService;

    protected FacultyDetails faculty;
    @EJB
    protected FacultyRequestsLocal facultyService;

    protected CollegeDetails college;
    @EJB
    protected CollegeRequestsLocal collegeService;

    protected ContactDetails contact;
    @EJB
    protected ContactRequestsLocal contactService;

    protected CountryDetails country;
    @EJB
    protected CountryRequestsLocal countryService;

    protected UserGroupDetail userGroupDetail;

    protected QuestionDetails question;
    @EJB
    protected QuestionRequestsLocal questionService;

    protected AdmissionDetails admission;
    @EJB
    protected AdmissionRequestsLocal admissionService;

    protected RatingTypeDetail ratingType;

    protected DepartmentDetails department;
    @EJB
    protected DepartmentRequestsLocal departmentService;

    protected UserAccountDetails userAccount;
    @EJB
    protected UserAccountRequestsLocal userAccountService;

    protected InstitutionDetails institution;
    @EJB
    protected InstitutionRequestsLocal institutionService;

    protected FacultyMemberDetails facultyMember;
    @EJB
    protected FacultyMemberRequestsLocal facultyMemberService;

    protected PhoneContactDetails phoneContact;
    @EJB
    protected PhoneContactRequestsLocal phoneContactService;

    protected EmailContactDetails emailContact;
    @EJB
    protected EmailContactRequestsLocal emailContactService;

    protected PostalContactDetails postalContact;
    @EJB
    protected PostalContactRequestsLocal postalContactService;

    protected QuestionCategoryDetails questionCategory;
    @EJB
    protected QuestionCategoryRequestsLocal questionCategoryService;

    protected MeansOfAnsweringDetail meansOfAnswering;
    @EJB
    protected EvaluationSessionRequestsLocal evaluationSessionService;

    protected EvaluationSessionDetails evaluationSession;
    @EJB
    protected CourseOfInstanceRequestsLocal courseOfInstanceService;

    protected CourseOfInstanceDetails courseOfInstance;

    @EJB
    protected EvaluationMarkingRequestsLocal evaluationMarkingService;

    protected StudentFeedbackDetails studentFeedback;
    @EJB
    protected StudentFeedbackRequestsLocal studentFeedbackService;

}
