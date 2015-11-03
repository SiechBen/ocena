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
public class EvaluatedQuestionDetails implements Serializable, Comparable<EvaluatedQuestionDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluatedQuestionDetails)) {
            return false;
        }
        EvaluatedQuestionDetails other = (EvaluatedQuestionDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluatedQuestionDetails[ id=" + getId() + " ]";
    }

    @Override
    public int compareTo(EvaluatedQuestionDetails o) {
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
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * @param question the question to set
     */
    public void setQuestion(String question) {
        this.question = question;
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
     * @return the ratingType
     */
    public RatingTypeDetail getRatingType() {
        return ratingType;
    }

    /**
     * @param ratingType the ratingType to set
     */
    public void setRatingType(RatingTypeDetail ratingType) {
        this.ratingType = ratingType;
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
     * @return the meansOfAnswering
     */
    public MeansOfAnsweringDetail getMeansOfAnswering() {
        return meansOfAnswering;
    }

    /**
     * @param meansOfAnswering the meansOfAnswering to set
     */
    public void setMeansOfAnswering(MeansOfAnsweringDetail meansOfAnswering) {
        this.meansOfAnswering = meansOfAnswering;
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
    private String question;
    private Integer version;
    private RatingTypeDetail ratingType;
    private MeansOfAnsweringDetail meansOfAnswering;
    private QuestionCategoryDetails questionCategory;
    private EvaluationSessionDetails evaluationSession;

}
