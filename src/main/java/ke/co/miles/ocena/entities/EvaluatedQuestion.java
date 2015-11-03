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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "evaluated_question", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluatedQuestion.findByQuestionAndEvaluationSessionId", query = "SELECT e FROM EvaluatedQuestion e WHERE e.question = :specificQuestion AND e.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "EvaluatedQuestion.findByEvaluationSessionId", query = "SELECT e FROM EvaluatedQuestion e WHERE e.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "EvaluatedQuestion.findAll", query = "SELECT e FROM EvaluatedQuestion e"),
    @NamedQuery(name = "EvaluatedQuestion.findById", query = "SELECT e FROM EvaluatedQuestion e WHERE e.id = :id"),
    @NamedQuery(name = "EvaluatedQuestion.findByQuestion", query = "SELECT e FROM EvaluatedQuestion e WHERE e.question = :question"),
    @NamedQuery(name = "EvaluatedQuestion.findByActive", query = "SELECT e FROM EvaluatedQuestion e WHERE e.active = :active"),
    @NamedQuery(name = "EvaluatedQuestion.findByVersion", query = "SELECT e FROM EvaluatedQuestion e WHERE e.version = :version")})
public class EvaluatedQuestion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "question")
    private String question;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluatedQuestion")
    private List<EvaluatedQuestionAnswer> evaluatedQuestionAnswerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluatedQuestion")
    private List<AssessedEvaluation> assessedEvaluationList;
    @JoinColumn(name = "evaluation_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationSession evaluationSession;
    @JoinColumn(name = "means_of_answering", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MeansOfAnswering meansOfAnswering;
    @JoinColumn(name = "question_category", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionCategory questionCategory;
    @JoinColumn(name = "rating_type", referencedColumnName = "id")
    @ManyToOne
    private RatingType ratingType;

    public EvaluatedQuestion() {
    }

    public EvaluatedQuestion(Integer id) {
        this.id = id;
    }

    public EvaluatedQuestion(Integer id, String question) {
        this.id = id;
        this.question = question;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @XmlTransient
    public List<EvaluatedQuestionAnswer> getEvaluatedQuestionAnswerList() {
        return evaluatedQuestionAnswerList;
    }

    public void setEvaluatedQuestionAnswerList(List<EvaluatedQuestionAnswer> evaluatedQuestionAnswerList) {
        this.evaluatedQuestionAnswerList = evaluatedQuestionAnswerList;
    }

    @XmlTransient
    public List<AssessedEvaluation> getAssessedEvaluationList() {
        return assessedEvaluationList;
    }

    public void setAssessedEvaluationList(List<AssessedEvaluation> assessedEvaluationList) {
        this.assessedEvaluationList = assessedEvaluationList;
    }

    public EvaluationSession getEvaluationSession() {
        return evaluationSession;
    }

    public void setEvaluationSession(EvaluationSession evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    public MeansOfAnswering getMeansOfAnswering() {
        return meansOfAnswering;
    }

    public void setMeansOfAnswering(MeansOfAnswering meansOfAnswering) {
        this.meansOfAnswering = meansOfAnswering;
    }

    public QuestionCategory getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(QuestionCategory questionCategory) {
        this.questionCategory = questionCategory;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
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
        if (!(object instanceof EvaluatedQuestion)) {
            return false;
        }
        EvaluatedQuestion other = (EvaluatedQuestion) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluatedQuestion[ id=" + id + " ]";
    }
    
}
