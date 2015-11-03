/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.course;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.CourseDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface CourseRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addCourse(CourseDetails details) throws InvalidArgumentException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editCourse(CourseDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeCourse(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param degreeId
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<CourseDetails> retrieveCoursesOfDegree(Integer degreeId) throws InvalidArgumentException, InvalidStateException;

}
