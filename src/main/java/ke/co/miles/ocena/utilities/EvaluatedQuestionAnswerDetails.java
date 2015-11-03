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
public class EvaluatedQuestionAnswerDetails implements Serializable, Comparable<EvaluatedQuestionAnswerDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluatedQuestionAnswerDetails)) {
            return false;
        }
        EvaluatedQuestionAnswerDetails other = (EvaluatedQuestionAnswerDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluatedQuestionAnswerDetails[ =" + getId() + " ]";
    }

    @Override
    public int compareTo(EvaluatedQuestionAnswerDetails o) {
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
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
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
     * @return the comment1
     */
    public String getComment1() {
        return comment1;
    }

    /**
     * @param comment1 the comment1 to set
     */
    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    /**
     * @return the comment2
     */
    public String getComment2() {
        return comment2;
    }

    /**
     * @param comment2 the comment2 to set
     */
    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    /**
     * @return the comment3
     */
    public String getComment3() {
        return comment3;
    }

    /**
     * @param comment3 the comment3 to set
     */
    public void setComment3(String comment3) {
        this.comment3 = comment3;
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
     * @return the reasoning
     */
    public String getReasoning() {
        return reasoning;
    }

    /**
     * @param reasoning the reasoning to set
     */
    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    /**
     * @param evaluatedQuestion the evaluatedQuestion to set
     */
    public void setEvaluatedQuestion(EvaluatedQuestionDetails evaluatedQuestion) {
        this.evaluatedQuestion = evaluatedQuestion;
    }

    /**
     * @param evaluationInstance the evaluationInstance to set
     */
    public void setEvaluationInstance(EvaluationInstanceDetails evaluationInstance) {
        this.evaluationInstance = evaluationInstance;
    }

    /**
     * @return the evaluatedQuestion
     */
    public EvaluatedQuestionDetails getEvaluatedQuestion() {
        return evaluatedQuestion;
    }

    /**
     * @return the evaluationInstance
     */
    public EvaluationInstanceDetails getEvaluationInstance() {
        return evaluationInstance;
    }

    /**
     * @return the courseOfInstance
     */
    public CourseOfInstanceDetails getCourseOfInstance() {
        return courseOfInstance;
    }

    /**
     * @param courseOfInstance the courseOfInstance to set
     */
    public void setCourseOfInstance(CourseOfInstanceDetails courseOfInstance) {
        this.courseOfInstance = courseOfInstance;
    }

    private Integer id;
    private String rating;
    private Boolean active;
    private String comment1;
    private String comment2;
    private String comment3;
    private Integer version;
    private String reasoning;
    private CourseOfInstanceDetails courseOfInstance;
    private EvaluatedQuestionDetails evaluatedQuestion;
    private EvaluationInstanceDetails evaluationInstance;

}
