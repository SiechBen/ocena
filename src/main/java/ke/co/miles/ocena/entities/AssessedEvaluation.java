/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "assessed_evaluation", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssessedEvaluation.findByEvaluationSessionIdAndQuestionCategoryId", query = "SELECT a FROM AssessedEvaluation a WHERE a.evaluationSession.id = :evaluationSessionId AND a.questionCategory.id = :questionCategoryId"),
    @NamedQuery(name = "AssessedEvaluation.findByEvaluationSessionId", query = "SELECT a FROM AssessedEvaluation a WHERE a.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "AssessedEvaluation.findByCourseOfSessionId", query = "SELECT a FROM AssessedEvaluation a WHERE a.courseOfSession.id = :courseOfSessionId"),
    @NamedQuery(name = "AssessedEvaluation.findAll", query = "SELECT a FROM AssessedEvaluation a"),
    @NamedQuery(name = "AssessedEvaluation.findById", query = "SELECT a FROM AssessedEvaluation a WHERE a.id = :id"),
    @NamedQuery(name = "AssessedEvaluation.findByQuestionDescription", query = "SELECT a FROM AssessedEvaluation a WHERE a.questionDescription = :questionDescription"),
    @NamedQuery(name = "AssessedEvaluation.findByRating", query = "SELECT a FROM AssessedEvaluation a WHERE a.rating = :rating"),
    @NamedQuery(name = "AssessedEvaluation.findByStandardDeviation", query = "SELECT a FROM AssessedEvaluation a WHERE a.standardDeviation = :standardDeviation"),
    @NamedQuery(name = "AssessedEvaluation.findByPercentageScore", query = "SELECT a FROM AssessedEvaluation a WHERE a.percentageScore = :percentageScore"),
    @NamedQuery(name = "AssessedEvaluation.findByVersion", query = "SELECT a FROM AssessedEvaluation a WHERE a.version = :version"),
    @NamedQuery(name = "AssessedEvaluation.findByActive", query = "SELECT a FROM AssessedEvaluation a WHERE a.active = :active")})
public class AssessedEvaluation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 300)
    @Column(name = "question_description")
    private String questionDescription;
    @Size(max = 10)
    @Column(name = "rating")
    private String rating;
    @Max(value = 5)
    @Min(value = 0)
    @Column(name = "standard_deviation")
    private Double standardDeviation;
    @Size(max = 20)
    @Column(name = "percentage_score")
    private String percentageScore;
    @Column(name = "version")
    private Integer version;
    @Column(name = "active")
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "assessedEvaluation")
    private List<AssessedEvaluationComment> assessedEvaluationCommentList;
    @JoinColumn(name = "course_of_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CourseOfSession courseOfSession;
    @JoinColumn(name = "evaluated_question", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluatedQuestion evaluatedQuestion;
    @JoinColumn(name = "evaluation_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationSession evaluationSession;
    @JoinColumn(name = "question_category", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionCategory questionCategory;

    public AssessedEvaluation() {
    }

    public AssessedEvaluation(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestionDescription() {
        return questionDescription;
    }

    public void setQuestionDescription(String questionDescription) {
        this.questionDescription = questionDescription;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public String getPercentageScore() {
        return percentageScore;
    }

    public void setPercentageScore(String percentageScore) {
        this.percentageScore = percentageScore;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @XmlTransient
    public List<AssessedEvaluationComment> getAssessedEvaluationCommentList() {
        return assessedEvaluationCommentList;
    }

    public void setAssessedEvaluationCommentList(List<AssessedEvaluationComment> assessedEvaluationCommentList) {
        this.assessedEvaluationCommentList = assessedEvaluationCommentList;
    }

    public CourseOfSession getCourseOfSession() {
        return courseOfSession;
    }

    public void setCourseOfSession(CourseOfSession courseOfSession) {
        this.courseOfSession = courseOfSession;
    }

    public EvaluatedQuestion getEvaluatedQuestion() {
        return evaluatedQuestion;
    }

    public void setEvaluatedQuestion(EvaluatedQuestion evaluatedQuestion) {
        this.evaluatedQuestion = evaluatedQuestion;
    }

    public EvaluationSession getEvaluationSession() {
        return evaluationSession;
    }

    public void setEvaluationSession(EvaluationSession evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AssessedEvaluation)) {
            return false;
        }
        AssessedEvaluation other = (AssessedEvaluation) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.AssessedEvaluation[ id=" + id + " ]";
    }

}
