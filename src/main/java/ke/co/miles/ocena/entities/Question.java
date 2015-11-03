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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "question", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Question.findByQuestionCategoryIdAndFacultyId", query = "SELECT q FROM Question q WHERE q.questionCategory.id = :questionCategoryId AND q.faculty.id = :facultyId"),
    @NamedQuery(name = "Question.findByQuestionCategoryIdAndDepartmentId", query = "SELECT q FROM Question q WHERE q.questionCategory.id = :questionCategoryId AND q.department.id = :departmentId"),
    @NamedQuery(name = "Question.findByQuestionAndFacultyId", query = "SELECT q FROM Question q WHERE q.question = :question AND q.faculty.id = :facultyId"),
    @NamedQuery(name = "Question.findByQuestionAndDepartmentId", query = "SELECT q FROM Question q WHERE q.question = :question AND q.department.id = :departmentId"),
    @NamedQuery(name = "Question.findByQuestionCategoryId", query = "SELECT q FROM Question q WHERE q.questionCategory.id = :questionCategoryId"),
    @NamedQuery(name = "Question.findByMeansOfAnsweringId", query = "SELECT q FROM Question q WHERE q.meansOfAnswering.id = :meansOfAnsweringId"),
    @NamedQuery(name = "Question.findByFacultyId", query = "SELECT q FROM Question q WHERE q.faculty.id = :facultyId"),
    @NamedQuery(name = "Question.findByDepartmentId", query = "SELECT q FROM Question q WHERE q.department.id = :departmentId"),
    @NamedQuery(name = "Question.findAll", query = "SELECT q FROM Question q"),
    @NamedQuery(name = "Question.findById", query = "SELECT q FROM Question q WHERE q.id = :id"),
    @NamedQuery(name = "Question.findByQuestion", query = "SELECT q FROM Question q WHERE q.question = :question"),
    @NamedQuery(name = "Question.findByActive", query = "SELECT q FROM Question q WHERE q.active = :active"),
    @NamedQuery(name = "Question.findByVersion", query = "SELECT q FROM Question q WHERE q.version = :version")})
public class Question implements Serializable {
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
    @JoinColumn(name = "department", referencedColumnName = "id")
    @ManyToOne
    private Department department;
    @JoinColumn(name = "faculty", referencedColumnName = "id")
    @ManyToOne
    private Faculty faculty;
    @JoinColumn(name = "means_of_answering", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private MeansOfAnswering meansOfAnswering;
    @JoinColumn(name = "question_category", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private QuestionCategory questionCategory;
    @JoinColumn(name = "rating_type", referencedColumnName = "id")
    @ManyToOne
    private RatingType ratingType;

    public Question() {
    }

    public Question(Integer id) {
        this.id = id;
    }

    public Question(Integer id, String question) {
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
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
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Question[ id=" + id + " ]";
    }
    
}
