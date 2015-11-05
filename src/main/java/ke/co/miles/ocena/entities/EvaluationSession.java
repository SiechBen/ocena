/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "evaluation_session", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluationSession.findActiveByDegreeIdAndAdmissionYear", query = "SELECT e FROM EvaluationSession e WHERE e.degree.id = :degreeId AND e.active = :active AND e.admissionYear = :admissionYear"),
    @NamedQuery(name = "EvaluationSession.findByDegreeIdWhereActive", query = "SELECT e FROM EvaluationSession e WHERE e.degree.id = :degreeId AND e.active = :active"),
    @NamedQuery(name = "EvaluationSession.findAll", query = "SELECT e FROM EvaluationSession e"),
    @NamedQuery(name = "EvaluationSession.findById", query = "SELECT e FROM EvaluationSession e WHERE e.id = :id"),
    @NamedQuery(name = "EvaluationSession.findByStartDate", query = "SELECT e FROM EvaluationSession e WHERE e.startDate = :startDate"),
    @NamedQuery(name = "EvaluationSession.findByEndDate", query = "SELECT e FROM EvaluationSession e WHERE e.endDate = :endDate"),
    @NamedQuery(name = "EvaluationSession.findByAcademicYear", query = "SELECT e FROM EvaluationSession e WHERE e.academicYear = :academicYear"),
    @NamedQuery(name = "EvaluationSession.findBySemester", query = "SELECT e FROM EvaluationSession e WHERE e.semester = :semester"),
    @NamedQuery(name = "EvaluationSession.findByAdmissionYear", query = "SELECT e FROM EvaluationSession e WHERE e.admissionYear = :admissionYear"),
    @NamedQuery(name = "EvaluationSession.findByActive", query = "SELECT e FROM EvaluationSession e WHERE e.active = :active"),
    @NamedQuery(name = "EvaluationSession.findByVersion", query = "SELECT e FROM EvaluationSession e WHERE e.version = :version")})
public class EvaluationSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Size(max = 45)
    @Column(name = "academic_year")
    private String academicYear;
    @Size(max = 45)
    @Column(name = "semester")
    private String semester;
    @Basic(optional = false)
    @NotNull
    @Column(name = "admission_year")
    @Temporal(TemporalType.DATE)
    private Date admissionYear;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "degree", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Degree degree;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationSession")
    private List<StudentFeedback> studentFeedbackList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationSession")
    private List<CourseOfSession> courseOfSessionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationSession")
    private List<EvaluationInstance> evaluationInstanceList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationSession")
    private List<AssessedEvaluation> assessedEvaluationList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationSession")
    private List<EvaluatedQuestion> evaluatedQuestionList;

    public EvaluationSession() {
    }

    public EvaluationSession(Integer id) {
        this.id = id;
    }

    public EvaluationSession(Integer id, Date admissionYear) {
        this.id = id;
        this.admissionYear = admissionYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Date admissionYear) {
        this.admissionYear = admissionYear;
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

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    @XmlTransient
    public List<StudentFeedback> getStudentFeedbackList() {
        return studentFeedbackList;
    }

    public void setStudentFeedbackList(List<StudentFeedback> studentFeedbackList) {
        this.studentFeedbackList = studentFeedbackList;
    }

    @XmlTransient
    public List<CourseOfSession> getCourseOfSessionList() {
        return courseOfSessionList;
    }

    public void setCourseOfSessionList(List<CourseOfSession> courseOfSessionList) {
        this.courseOfSessionList = courseOfSessionList;
    }

    @XmlTransient
    public List<EvaluationInstance> getEvaluationInstanceList() {
        return evaluationInstanceList;
    }

    public void setEvaluationInstanceList(List<EvaluationInstance> evaluationInstanceList) {
        this.evaluationInstanceList = evaluationInstanceList;
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
        if (!(object instanceof EvaluationSession)) {
            return false;
        }
        EvaluationSession other = (EvaluationSession) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluationSession[ id=" + id + " ]";
    }
    
}
