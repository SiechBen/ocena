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
@Table(name = "course_of_instance", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CourseOfInstance.findByCourseOfSessionIdAndEvaluationInstanceId", query = "SELECT c FROM CourseOfInstance c WHERE c.courseOfSession.id = :courseOfSessionId AND c.evaluationInstance.id = :evaluationInstanceId"),
    @NamedQuery(name = "CourseOfInstance.findByEvaluationInstanceIdAndCourseOfSessionId", query = "SELECT c FROM CourseOfInstance c WHERE c.courseOfSession.id = :courseOfSessionId AND c.evaluationInstance.id = :evaluationInstanceId"),
    @NamedQuery(name = "CourseOfInstance.findByEvaluationInstanceId", query = "SELECT c FROM CourseOfInstance c WHERE c.evaluationInstance.id = :evaluationInstanceId"),
    @NamedQuery(name = "CourseOfInstance.findAll", query = "SELECT c FROM CourseOfInstance c"),
    @NamedQuery(name = "CourseOfInstance.findById", query = "SELECT c FROM CourseOfInstance c WHERE c.id = :id"),
    @NamedQuery(name = "CourseOfInstance.findByActive", query = "SELECT c FROM CourseOfInstance c WHERE c.active = :active"),
    @NamedQuery(name = "CourseOfInstance.findByVersion", query = "SELECT c FROM CourseOfInstance c WHERE c.version = :version")})
public class CourseOfInstance implements Serializable {
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
    @JoinColumn(name = "course_of_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private CourseOfSession courseOfSession;
    @JoinColumn(name = "evaluation_instance", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationInstance evaluationInstance;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "courseOfInstance")
    private List<EvaluatedQuestionAnswer> evaluatedQuestionAnswerList;

    public CourseOfInstance() {
    }

    public CourseOfInstance(Integer id) {
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

    public CourseOfSession getCourseOfSession() {
        return courseOfSession;
    }

    public void setCourseOfSession(CourseOfSession courseOfSession) {
        this.courseOfSession = courseOfSession;
    }

    public EvaluationInstance getEvaluationInstance() {
        return evaluationInstance;
    }

    public void setEvaluationInstance(EvaluationInstance evaluationInstance) {
        this.evaluationInstance = evaluationInstance;
    }

    @XmlTransient
    public List<EvaluatedQuestionAnswer> getEvaluatedQuestionAnswerList() {
        return evaluatedQuestionAnswerList;
    }

    public void setEvaluatedQuestionAnswerList(List<EvaluatedQuestionAnswer> evaluatedQuestionAnswerList) {
        this.evaluatedQuestionAnswerList = evaluatedQuestionAnswerList;
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
        if (!(object instanceof CourseOfInstance)) {
            return false;
        }
        CourseOfInstance other = (CourseOfInstance) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.CourseOfInstance[ id=" + id + " ]";
    }
    
}
