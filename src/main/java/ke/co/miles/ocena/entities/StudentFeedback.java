/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "student_feedback", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "StudentFeedback.findByEvaluationSessionIdAndFacultyMemberId", query = "SELECT s FROM StudentFeedback s WHERE s.evaluationSession.id = :evaluationSessionId AND s.facultyMember.id = :facultyMemberId"),
    @NamedQuery(name = "StudentFeedback.findByEvaluationSessionId", query = "SELECT s FROM StudentFeedback s WHERE s.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "StudentFeedback.findByFacultyMemberId", query = "SELECT s FROM StudentFeedback s WHERE s.facultyMember.id = :facultyMemberId ORDER BY s.id DESC"),
    @NamedQuery(name = "StudentFeedback.findAll", query = "SELECT s FROM StudentFeedback s"),
    @NamedQuery(name = "StudentFeedback.findById", query = "SELECT s FROM StudentFeedback s WHERE s.id = :id"),
    @NamedQuery(name = "StudentFeedback.findByFeedback", query = "SELECT s FROM StudentFeedback s WHERE s.feedback = :feedback"),
    @NamedQuery(name = "StudentFeedback.findByDateCompleted", query = "SELECT s FROM StudentFeedback s WHERE s.dateCompleted = :dateCompleted"),
    @NamedQuery(name = "StudentFeedback.findByActive", query = "SELECT s FROM StudentFeedback s WHERE s.active = :active"),
    @NamedQuery(name = "StudentFeedback.findByVersion", query = "SELECT s FROM StudentFeedback s WHERE s.version = :version")})
public class StudentFeedback implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 400)
    @Column(name = "feedback")
    private String feedback;
    @Column(name = "date_completed")
    @Temporal(TemporalType.DATE)
    private Date dateCompleted;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "evaluation_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationSession evaluationSession;
    @JoinColumn(name = "faculty_member", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyMember facultyMember;

    public StudentFeedback() {
    }

    public StudentFeedback(Integer id) {
        this.id = id;
    }

    public StudentFeedback(Integer id, String feedback) {
        this.id = id;
        this.feedback = feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
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

    public EvaluationSession getEvaluationSession() {
        return evaluationSession;
    }

    public void setEvaluationSession(EvaluationSession evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    public FacultyMember getFacultyMember() {
        return facultyMember;
    }

    public void setFacultyMember(FacultyMember facultyMember) {
        this.facultyMember = facultyMember;
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
        if (!(object instanceof StudentFeedback)) {
            return false;
        }
        StudentFeedback other = (StudentFeedback) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.StudentFeedback[ id=" + id + " ]";
    }
    
}
