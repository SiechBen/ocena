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
public class AssessedEvaluationCommentDetails implements Serializable, Comparable<AssessedEvaluationCommentDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessedEvaluationCommentDetails)) {
            return false;
        }
        AssessedEvaluationCommentDetails other = (AssessedEvaluationCommentDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.AssessedEvaluationCommentDetailss[ Comment=" + getComment() + " ]";
    }

    @Override
    public int compareTo(AssessedEvaluationCommentDetails o) {
        return this.getComment().compareTo(o.getComment());
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
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * @return the assessedEvaluation
     */
    public AssessedEvaluationDetails getAssessedEvaluation() {
        return assessedEvaluation;
    }

    /**
     * @param assessedEvaluation the assessedEvaluation to set
     */
    public void setAssessedEvaluation(AssessedEvaluationDetails assessedEvaluation) {
        this.assessedEvaluation = assessedEvaluation;
    }

    private Integer id;
    private String comment;
    private Boolean active;
    private Integer version;
    private AssessedEvaluationDetails assessedEvaluation;

}
