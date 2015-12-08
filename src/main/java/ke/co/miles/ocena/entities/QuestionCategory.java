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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "question_category", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "QuestionCategory.findAll", query = "SELECT q FROM QuestionCategory q"),
    @NamedQuery(name = "QuestionCategory.findById", query = "SELECT q FROM QuestionCategory q WHERE q.id = :id"),
    @NamedQuery(name = "QuestionCategory.findByCategory", query = "SELECT q FROM QuestionCategory q WHERE q.category = :category"),
    @NamedQuery(name = "QuestionCategory.findByActive", query = "SELECT q FROM QuestionCategory q WHERE q.active = :active"),
    @NamedQuery(name = "QuestionCategory.findByVersion", query = "SELECT q FROM QuestionCategory q WHERE q.version = :version")})
public class QuestionCategory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Size(max = 120)
    @Column(name = "category")
    private String category;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionCategory")
    private List<Question> questionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionCategory")
    private List<AssessedEvaluation> assessedEvaluationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "questionCategory")
    private List<EvaluatedQuestion> evaluatedQuestionList;

    public QuestionCategory() {
    }

    public QuestionCategory(Short id) {
        this.id = id;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    @XmlTransient
    public List<AssessedEvaluation> getAssessedEvaluationList() {
        return assessedEvaluationList;
    }

    public void setAssessedEvaluationList(List<AssessedEvaluation> assessedEvaluationList) {
        this.assessedEvaluationList = assessedEvaluationList;
    }

    @XmlTransient
    public List<EvaluatedQuestion> getEvaluatedQuestionList() {
        return evaluatedQuestionList;
    }

    public void setEvaluatedQuestionList(List<EvaluatedQuestion> evaluatedQuestionList) {
        this.evaluatedQuestionList = evaluatedQuestionList;
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
        if (!(object instanceof QuestionCategory)) {
            return false;
        }
        QuestionCategory other = (QuestionCategory) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.QuestionCategory[ id=" + id + " ]";
    }
    
}
