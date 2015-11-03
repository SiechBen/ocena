/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.studentfeedback;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.StudentFeedback;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.FacultyMemberDetails;
import ke.co.miles.ocena.utilities.StudentFeedbackDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface StudentFeedbackRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Integer addStudentFeedback(StudentFeedbackDetails details) throws InvalidArgumentException;

    /**
     *
     * @param studentFeedbackDetails
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editStudentFeedback(StudentFeedbackDetails studentFeedbackDetails) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeStudentFeedback(Integer id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param studentFeedbackId
     * @return
     * @throws InvalidArgumentException
     */
    public StudentFeedbackDetails retrieveStudentFeedback(Integer studentFeedbackId) throws InvalidArgumentException;

    /**
     *
     * @param studentFeedbackDetails
     * @return
     * @throws InvalidArgumentException
     */
    public StudentFeedback retrieveStudentFeedback(StudentFeedbackDetails studentFeedbackDetails) throws InvalidArgumentException;

    /**
     *
     * @param facultyMember
     * @return
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<StudentFeedbackDetails> retrieveStudentFeedbacks(FacultyMemberDetails facultyMember) throws InvalidArgumentException, InvalidStateException;

}
