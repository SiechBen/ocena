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
public class CourseOfInstanceDetails implements Serializable, Comparable<CourseOfInstanceDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CourseOfInstanceDetails)) {
            return false;
        }
        CourseOfInstanceDetails other = (CourseOfInstanceDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.CourseOfInstance[ id=" + getId() + " ]";
    }

    @Override
    public int compareTo(CourseOfInstanceDetails o) {
        return this.getId().compareTo(o.getId());
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

    /**
     * @return the courseOfSession
     */
    public CourseOfSessionDetails getCourseOfSession() {
        return courseOfSession;
    }

    /**
     * @param courseOfSession the courseOfSession to set
     */
    public void setCourseOfSession(CourseOfSessionDetails courseOfSession) {
        this.courseOfSession = courseOfSession;
    }

    /**
     * @return the evaluationInstance
     */
    public EvaluationInstanceDetails getEvaluationInstance() {
        return evaluationInstance;
    }

    /**
     * @param evaluationInstance the evaluationInstance to set
     */
    public void setEvaluationInstance(EvaluationInstanceDetails evaluationInstance) {
        this.evaluationInstance = evaluationInstance;
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private CourseOfSessionDetails courseOfSession;
    private EvaluationInstanceDetails evaluationInstance;

}
