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
import ke.co.miles.ocena.entities.Admission;
import ke.co.miles.ocena.entities.AssessedEvaluation;
import ke.co.miles.ocena.entities.AssessedEvaluationComment;
import ke.co.miles.ocena.entities.College;
import ke.co.miles.ocena.entities.Contact;
import ke.co.miles.ocena.entities.Country;
import ke.co.miles.ocena.entities.Course;
import ke.co.miles.ocena.entities.CourseOfInstance;
import ke.co.miles.ocena.entities.CourseOfSession;
import ke.co.miles.ocena.entities.Degree;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.entities.EmailContact;
import ke.co.miles.ocena.entities.EvaluatedQuestion;
import ke.co.miles.ocena.entities.EvaluatedQuestionAnswer;
import ke.co.miles.ocena.entities.EvaluationInstance;
import ke.co.miles.ocena.entities.EvaluationSession;
import ke.co.miles.ocena.entities.Institution;
import ke.co.miles.ocena.entities.MeansOfAnswering;
import ke.co.miles.ocena.entities.Person;
import ke.co.miles.ocena.entities.PhoneContact;
import ke.co.miles.ocena.entities.PostalContact;
import ke.co.miles.ocena.entities.Question;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.entities.FacultyMember;
import ke.co.miles.ocena.entities.FacultyMemberRole;
import ke.co.miles.ocena.entities.Rating;
import ke.co.miles.ocena.entities.RatingType;
import ke.co.miles.ocena.entities.StudentFeedback;
import ke.co.miles.ocena.entities.UserAccount;
import ke.co.miles.ocena.entities.UserGroup;
import ke.co.miles.ocena.requests.access.AccessRequestsLocal;
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
import ke.co.miles.ocena.utilities.AccessCredentials;
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
import ke.co.miles.ocena.utilities.FacultyMemberRoleDetail;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.RatingDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;
import ke.co.miles.ocena.utilities.StudentFeedbackDetails;
import ke.co.miles.ocena.utilities.UserAccountDetails;
import ke.co.miles.ocena.utilities.UserGroupDetail;

/**
 *
 * @author Ben Siech
 */
public class EntityRequests {

    @PersistenceContext(name = "OcenaPU")
    protected EntityManager em;
    protected Query q;

    @EJB
    protected EvaluationMarkingRequestsLocal evaluationMarkingService;

    @EJB
    protected StudentFeedbackRequestsLocal studentFeedbackService;

    @EJB
    protected CourseOfInstanceRequestsLocal courseOfInstanceService;

    @EJB
    protected EvaluationInstanceRequestsLocal evaluationInstanceService;

    @EJB
    protected EvaluationSessionRequestsLocal evaluationSessionService;

    @EJB
    protected CourseOfSessionRequestsLocal courseOfSessionService;

    @EJB
    protected EvaluatedQuestionRequestsLocal evaluatedQuestionService;

    @EJB
    protected EvaluatedQuestionAnswerRequestsLocal evaluatedQuestionAnswerService;

    @EJB
    protected AssessedEvaluationCommentRequestsLocal assessedEvaluationCommentService;

    @EJB
    protected AssessedEvaluationRequestsLocal assessedEvaluationService;

    @EJB
    protected CourseRequestsLocal courseService;

    @EJB
    protected AccessRequestsLocal accessService;

    @EJB
    protected DegreeRequestsLocal degreeService;

    @EJB
    protected PersonRequestsLocal personService;

    @EJB
    protected RatingRequestsLocal ratingService;

    @EJB
    protected FacultyRequestsLocal facultyservice;

    @EJB
    protected CollegeRequestsLocal collegeService;

    @EJB
    protected ContactRequestsLocal contactService;

    @EJB
    protected CountryRequestsLocal countryService;

    @EJB
    protected QuestionRequestsLocal questionService;

    @EJB
    protected AdmissionRequestsLocal admissionService;

    @EJB
    protected DepartmentRequestsLocal departmentService;

    @EJB
    protected UserAccountRequestsLocal userAccountService;

    @EJB
    protected InstitutionRequestsLocal institutionService;

    @EJB
    protected FacultyMemberRequestsLocal facultyMemberService;

    @EJB
    protected PhoneContactRequestsLocal phoneContactService;

    @EJB
    protected EmailContactRequestsLocal emailContactService;

    @EJB
    protected PostalContactRequestsLocal postalContactService;

    @EJB
    protected QuestionCategoryRequestsLocal questionCategoryService;

    protected AssessedEvaluationComment assessedEvaluationComment;
    protected AssessedEvaluationCommentDetails assessedEvaluationCommentDetails;

    protected AssessedEvaluation assessedEvaluation;
    protected AssessedEvaluationDetails assessedEvaluationDetails;

    protected FacultyMemberRole facultyMemberRole;
    protected FacultyMemberRoleDetail facultyMemberRoleDetail;

    protected Course course;
    protected CourseDetails courseDetails;

    protected Degree degree;
    protected DegreeDetails degreeDetails;

    protected Person person;
    protected PersonDetails personDetails;

    protected Rating rating;
    protected RatingDetails ratingDetails;

    protected Faculty faculty;
    protected FacultyDetails facultyDetails;

    protected College college;
    protected CollegeDetails collegeDetails;

    protected Contact contact;
    protected ContactDetails contactDetails;

    protected Country country;
    protected CountryDetails countryDetails;

    protected UserGroup userGroup;
    protected UserGroupDetail userGroupDetail;

    protected Question question;
    protected QuestionDetails questionDetails;

    protected RatingType ratingType;
    protected RatingTypeDetail ratingTypeDetail;

    protected Admission admission;
    protected AdmissionDetails admissionDetails;

    protected AccessCredentials accessCredentials;

    protected Department department;
    protected DepartmentDetails departmentDetails;

    protected UserAccount userAccount;
    protected UserAccountDetails userAccountDetails;

    protected Institution institution;
    protected InstitutionDetails institutionDetails;

    protected FacultyMember facultyMember;
    protected FacultyMemberDetails facultyMemberDetails;

    protected PhoneContact phoneContact;
    protected PhoneContactDetails phoneContactDetails;

    protected EmailContact emailContact;
    protected EmailContactDetails emailContactDetails;

    protected PostalContact postalContact;
    protected PostalContactDetails postalContactDetails;

    protected QuestionCategory questionCategory;
    protected QuestionCategoryDetails questionCategoryDetails;

    protected MeansOfAnswering meansOfAnswering;
    protected MeansOfAnsweringDetail meansOfAnsweringDetails;

    protected EvaluatedQuestion evaluatedQuestion;
    protected EvaluatedQuestionDetails evaluatedQuestionDetails;

    protected EvaluatedQuestionAnswer evaluatedQuestionAnswer;
    protected EvaluatedQuestionAnswerDetails evaluatedQuestionAnswerDetails;

    protected EvaluationSession evaluationSession;
    protected EvaluationSessionDetails evaluationSessionDetails;

    protected EvaluationInstance evaluationInstance;
    protected EvaluationInstanceDetails evaluationInstanceDetails;

    protected CourseOfSession courseOfSession;
    protected CourseOfSessionDetails courseOfSessionDetails;

    protected CourseOfInstance courseOfInstance;
    protected CourseOfInstanceDetails courseOfInstanceDetails;

    protected StudentFeedback studentFeedback;
    protected StudentFeedbackDetails studentFeedbackDetails;

}
