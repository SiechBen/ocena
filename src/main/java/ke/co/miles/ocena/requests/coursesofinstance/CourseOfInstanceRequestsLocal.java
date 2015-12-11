/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.coursesofinstance;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.CourseOfInstance;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface CourseOfInstanceRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public CourseOfInstanceDetails addCourseOfInstance(CourseOfInstanceDetails details) throws InvalidArgumentException;

    /**
     *
     * @param evaluationInstanceId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<CourseOfInstanceDetails> retrieveCoursesOfInstance(Integer evaluationInstanceId) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param evaluationInstanceDetails
     * @param courseOfSessionDetails
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public CourseOfInstanceDetails retrieveCourseOfInstance(EvaluationInstanceDetails evaluationInstanceDetails, CourseOfSessionDetails courseOfSessionDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public CourseOfInstanceDetails retrieveCourseOfInstance(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editCourseOfInstance(CourseOfInstanceDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeCourseOfInstance(Integer id) throws InvalidArgumentException, InvalidStateException;
    
}
