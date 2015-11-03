/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.utilities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ben Siech
 */
public class StudentFeedbackDetails implements Serializable, Comparable<StudentFeedbackDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof StudentFeedbackDetails)) {
            return false;
        }
        StudentFeedbackDetails other = (StudentFeedbackDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.StudentFeedback[ id=" + getId() + " ]";
    }

    @Override
    public int compareTo(StudentFeedbackDetails o) {
        return this.getDateCompleted().compareTo(o.getDateCompleted());
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the feedback
     */
    public String getFeedback() {
        return feedback;
    }

    /**
     * @param feedback the feedback to set
     */
    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the dateCompleted
     */
    public Date getDateCompleted() {
        return dateCompleted;
    }

    /**
     * @param dateCompleted the dateCompleted to set
     */
    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    /**
     * @return the facultyMember
     */
    public FacultyMemberDetails getFacultyMember() {
        return facultyMember;
    }

    /**
     * @param facultyMember the facultyMember to set
     */
    public void setFacultyMember(FacultyMemberDetails facultyMember) {
        this.facultyMember = facultyMember;
    }

    /**
     * @return the evaluationSession
     */
    public EvaluationSessionDetails getEvaluationSession() {
        return evaluationSession;
    }

    /**
     * @param evaluationSession the evaluationSession to set
     */
    public void setEvaluationSession(EvaluationSessionDetails evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    private Integer id;
    private Boolean active;
    private String feedback;
    private Integer version;
    private Date dateCompleted;
    private FacultyMemberDetails facultyMember;
    private EvaluationSessionDetails evaluationSession;

}
