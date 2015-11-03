/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.coursesofsession;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.PersonDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface CourseOfSessionRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addCourseOfSession(CourseOfSessionDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editCourseOfSession(CourseOfSessionDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeCourseOfSession(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<CourseOfSessionDetails> retrieveCoursesOfSession(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param facultyMembers
     * @return
     * @throws InvalidArgumentException
     */
    public Map<FacultyMemberDetails, PersonDetails> retrievePersonByFacultyMember(List<FacultyMemberDetails> facultyMembers) throws InvalidArgumentException;

    /**
     *
     * @param courseOfSessionList
     * @return
     * @throws InvalidArgumentException
     */
    public Map<CourseOfSessionDetails, PersonDetails> retrievePersonByCourseOfSession(List<CourseOfSessionDetails> courseOfSessionList) throws InvalidArgumentException;

    /**
     *
     * @param integer
     * @return
     * @throws InvalidArgumentException
     */
    public CourseOfSessionDetails retrieveCourseOfSession(Integer integer) throws InvalidArgumentException;

}
