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
public class QuestionDetails implements Serializable, Comparable<QuestionDetails> {

    @Override
    public int compareTo(QuestionDetails o) {
        return this.getQuestion().compareTo(o.getQuestion());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof QuestionDetails)) {
            return false;
        }
        QuestionDetails other = (QuestionDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Question[ question= " + getQuestion() + " ]";
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
     * @return the faculty
     */
    public FacultyDetails getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(FacultyDetails faculty) {
        this.faculty = faculty;
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
     * @return the department
     */
    public DepartmentDetails getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(DepartmentDetails department) {
        this.department = department;
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

    private Integer id;
    private Boolean active;
    private Integer version;
    private String question;
    private FacultyDetails faculty;
    private RatingTypeDetail ratingType;
    private DepartmentDetails department;
    private MeansOfAnsweringDetail meansOfAnswering;
    private QuestionCategoryDetails questionCategory;

}
