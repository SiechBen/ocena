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
public class AssessedEvaluationDetails implements Serializable, Comparable<AssessedEvaluationDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessedEvaluationDetails)) {
            return false;
        }
        AssessedEvaluationDetails other = (AssessedEvaluationDetails) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.AssessedEvaluation[ id=" + getId() + " ]";
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
     * @return the percentageScore
     */
    public String getPercentageScore() {
        return percentageScore;
    }

    /**
     * @param percentageScore the percentageScore to set
     */
    public void setPercentageScore(String percentageScore) {
        this.percentageScore = percentageScore;
    }

    /**
     * @return the questionDescription
     */
    public String getQuestionDescription() {
        return questionDescription;
    }

    /**
     * @param questionDescription the questionDescription to set
     */
    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    /**
     * @return the questionCategory
     */
    public QuestionCategoryDetails getQuestionCategory() {
        return questionCategory;
    }

    /**
     * @param questionCategory the questionCategory to set
     */
    public void setQuestionCategory(QuestionCategoryDetails questionCategory) {
        this.questionCategory = questionCategory;
    }

    /**
     * @return the evaluatedQuestion
     */
    public EvaluatedQuestionDetails getEvaluatedQuestion() {
        return evaluatedQuestion;
    }

    /**
     * @param evaluatedQuestion the evaluatedQuestion to set
     */
    public void setEvaluatedQuestion(EvaluatedQuestionDetails evaluatedQuestion) {
        this.evaluatedQuestion = evaluatedQuestion;
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

    @Override
    public int compareTo(AssessedEvaluationDetails o) {
        return this.getId().compareTo(o.getId());
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

    private Integer id;
    private String rating;
    private Boolean active;
    private Integer version;
    private String percentageScore;
    private String questionDescription;
    private CourseOfSessionDetails courseOfSession;
    private QuestionCategoryDetails questionCategory;
    private EvaluatedQuestionDetails evaluatedQuestion;
    private EvaluationSessionDetails evaluationSession;

}
