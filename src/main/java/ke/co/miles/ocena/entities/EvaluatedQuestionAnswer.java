/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "evaluated_question_answer", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByEvaluationInstanceIdAndEvaluatedQuestionIdAndCourseOfInstanceId", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.evaluationInstance.id = :evaluationInstanceId AND e.evaluatedQuestion.id = :evaluatedQuestionId AND e.courseOfInstance.id = :courseOfInstanceId"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByEvaluatedQuestionIdAndCourseOfInstanceId", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.evaluatedQuestion.id = :evaluatedQuestionId AND e.courseOfInstance.id = :courseOfInstanceId"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findAll", query = "SELECT e FROM EvaluatedQuestionAnswer e"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findById", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.id = :id"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByRating", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.rating = :rating"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByReasoning", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.reasoning = :reasoning"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByComment1", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.comment1 = :comment1"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByComment2", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.comment2 = :comment2"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByComment3", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.comment3 = :comment3"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByActive", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.active = :active"),
    @NamedQuery(name = "EvaluatedQuestionAnswer.findByVersion", query = "SELECT e FROM EvaluatedQuestionAnswer e WHERE e.version = :version")})
public class EvaluatedQuestionAnswer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "rating")
    private String rating;
    @Size(max = 200)
    @Column(name = "reasoning")
    private String reasoning;
    @Size(max = 200)
    @Column(name = "comment1")
    private String comment1;
    @Size(max = 200)
    @Column(name = "comment2")
    private String comment2;
    @Size(max = 200)
    @Column(name = "comment3")
    private String comment3;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "evaluated_question", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluatedQuestion evaluatedQuestion;
    @JoinColumn(name = "course_of_instance", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CourseOfInstance courseOfInstance;
    @JoinColumn(name = "evaluation_instance", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationInstance evaluationInstance;

    public EvaluatedQuestionAnswer() {
    }

    public EvaluatedQuestionAnswer(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReasoning() {
        return reasoning;
    }

    public void setReasoning(String reasoning) {
        this.reasoning = reasoning;
    }

    public String getComment1() {
        return comment1;
    }

    public void setComment1(String comment1) {
        this.comment1 = comment1;
    }

    public String getComment2() {
        return comment2;
    }

    public void setComment2(String comment2) {
        this.comment2 = comment2;
    }

    public String getComment3() {
        return comment3;
    }

    public void setComment3(String comment3) {
        this.comment3 = comment3;
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

    public EvaluatedQuestion getEvaluatedQuestion() {
        return evaluatedQuestion;
    }

    public void setEvaluatedQuestion(EvaluatedQuestion evaluatedQuestion) {
        this.evaluatedQuestion = evaluatedQuestion;
    }

    public CourseOfInstance getCourseOfInstance() {
        return courseOfInstance;
    }

    public void setCourseOfInstance(CourseOfInstance courseOfInstance) {
        this.courseOfInstance = courseOfInstance;
    }

    public EvaluationInstance getEvaluationInstance() {
        return evaluationInstance;
    }

    public void setEvaluationInstance(EvaluationInstance evaluationInstance) {
        this.evaluationInstance = evaluationInstance;
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
        if (!(object instanceof EvaluatedQuestionAnswer)) {
            return false;
        }
        EvaluatedQuestionAnswer other = (EvaluatedQuestionAnswer) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluatedQuestionAnswer[ id=" + id + " ]";
    }
    
}
