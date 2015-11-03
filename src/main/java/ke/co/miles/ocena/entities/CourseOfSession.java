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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "course_of_session", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseOfSession.findByEvaluationSessionId", query = "SELECT c FROM CourseOfSession c WHERE c.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "CourseOfSession.findAll", query = "SELECT c FROM CourseOfSession c"),
    @NamedQuery(name = "CourseOfSession.findById", query = "SELECT c FROM CourseOfSession c WHERE c.id = :id"),
    @NamedQuery(name = "CourseOfSession.findByActive", query = "SELECT c FROM CourseOfSession c WHERE c.active = :active"),
    @NamedQuery(name = "CourseOfSession.findByVersion", query = "SELECT c FROM CourseOfSession c WHERE c.version = :version")})
public class CourseOfSession implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseOfSession")
    private List<CourseOfInstance> courseOfInstanceList;
    @JoinColumn(name = "evaluation_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationSession evaluationSession;
    @JoinColumn(name = "course", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Course course;
    @JoinColumn(name = "faculty_member", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyMember facultyMember;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseOfSession")
    private List<AssessedEvaluation> assessedEvaluationList;

    public CourseOfSession() {
    }

    public CourseOfSession(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public List<CourseOfInstance> getCourseOfInstanceList() {
        return courseOfInstanceList;
    }

    public void setCourseOfInstanceList(List<CourseOfInstance> courseOfInstanceList) {
        this.courseOfInstanceList = courseOfInstanceList;
    }

    public EvaluationSession getEvaluationSession() {
        return evaluationSession;
    }

    public void setEvaluationSession(EvaluationSession evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public FacultyMember getFacultyMember() {
        return facultyMember;
    }

    public void setFacultyMember(FacultyMember facultyMember) {
        this.facultyMember = facultyMember;
    }

    @XmlTransient
    public List<AssessedEvaluation> getAssessedEvaluationList() {
        return assessedEvaluationList;
    }

    public void setAssessedEvaluationList(List<AssessedEvaluation> assessedEvaluationList) {
        this.assessedEvaluationList = assessedEvaluationList;
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
        if (!(object instanceof CourseOfSession)) {
            return false;
        }
        CourseOfSession other = (CourseOfSession) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.CourseOfSession[ id=" + id + " ]";
    }
    
}
