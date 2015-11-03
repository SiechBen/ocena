/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.question;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.QuestionDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Local
public interface QuestionRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addQuestion(QuestionDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editQuestion(QuestionDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeQuestion(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @return @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<MeansOfAnsweringDetail, List<QuestionDetails>> retrieveQuestionsByMeansOfAnswering() throws InvalidArgumentException;

    /**
     *
     * @return @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<RatingTypeDetail, List<QuestionDetails>> retrieveQuestionsByRatingType() throws InvalidArgumentException;

    /**
     *
     * @return
     */
    public Map<FacultyDetails, List<QuestionDetails>> retrieveQuestionsByFaculty();

    /**
     *
     * @return
     */
    public Map<DepartmentDetails, List<QuestionDetails>> retrieveQuestionsByDepartment();

    /**
     *
     * @param questions
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<QuestionDetails, RatingTypeDetail> retrieveRatingTypesByQuestion(List<QuestionDetails> questions) throws InvalidArgumentException;

    /**
     *
     * @param questions
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<QuestionDetails, MeansOfAnsweringDetail> retrieveMeansOfAnsweringByQuestion(List<QuestionDetails> questions) throws InvalidArgumentException;

    /**
     *
     * @param departmentId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<QuestionDetails> retrieveQuestionsInDepartment(Integer departmentId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param facultyId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<QuestionDetails> retrieveQuestionsInFaculty(Integer facultyId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param faculty
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<QuestionCategoryDetails, List<QuestionDetails>> retrieveQuestionsOfFacultyByQuestionCategories(FacultyDetails faculty) throws InvalidArgumentException;

    /**
     *
     * @param department
     * @return
     * @throws ke.co.miles.ocena.exceptions.InvalidArgumentException
     */
    public Map<QuestionCategoryDetails, List<QuestionDetails>> retrieveQuestionsOfDepartmentByQuestionCategories(DepartmentDetails department) throws InvalidArgumentException;

}
