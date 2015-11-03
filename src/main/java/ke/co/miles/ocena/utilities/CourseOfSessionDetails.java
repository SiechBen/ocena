/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.utilities;

import java.io.Serializable;

/**
 *
 * @author Ben Siech
 */
public class CourseOfSessionDetails implements Serializable, Comparable<CourseOfSessionDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseOfSessionDetails)) {
            return false;
        }
        CourseOfSessionDetails other = (CourseOfSessionDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.CourseOfSessionDetails[ id=" + getId() + " ]";
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

    @Override
    public int compareTo(CourseOfSessionDetails o) {
        return this.getId().compareTo(o.getId());
    }

    /**
     * @param course the course to set
     */
    public void setCourse(CourseDetails course) {
        this.course = course;
    }

    /**
     * @param facultyMember the facultyMember to set
     */
    public void setFacultyMember(FacultyMemberDetails facultyMember) {
        this.facultyMember = facultyMember;
    }

    /**
     * @param evaluationSession the evaluationSession to set
     */
    public void setEvaluationSession(EvaluationSessionDetails evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    /**
     * @return the course
     */
    public CourseDetails getCourse() {
        return course;
    }

    /**
     * @return the facultyMember
     */
    public FacultyMemberDetails getFacultyMember() {
        return facultyMember;
    }

    /**
     * @return the evaluationSession
     */
    public EvaluationSessionDetails getEvaluationSession() {
        return evaluationSession;
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private CourseDetails course;
    private FacultyMemberDetails facultyMember;
    private EvaluationSessionDetails evaluationSession;

}
