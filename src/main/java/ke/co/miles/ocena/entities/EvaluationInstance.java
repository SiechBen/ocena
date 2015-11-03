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
@Table(name = "evaluation_instance", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EvaluationInstance.findByAnonymousIdentityAndEvaluationSessionId", query = "SELECT e FROM EvaluationInstance e WHERE e.anonymousIdentity = :anonymousIdentity AND e.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "EvaluationInstance.findByEvaluationSessionId", query = "SELECT e FROM EvaluationInstance e WHERE e.evaluationSession.id = :evaluationSessionId"),
    @NamedQuery(name = "EvaluationInstance.findAll", query = "SELECT e FROM EvaluationInstance e"),
    @NamedQuery(name = "EvaluationInstance.findById", query = "SELECT e FROM EvaluationInstance e WHERE e.id = :id"),
    @NamedQuery(name = "EvaluationInstance.findByAnonymousIdentity", query = "SELECT e FROM EvaluationInstance e WHERE e.anonymousIdentity = :anonymousIdentity"),
    @NamedQuery(name = "EvaluationInstance.findByActive", query = "SELECT e FROM EvaluationInstance e WHERE e.active = :active"),
    @NamedQuery(name = "EvaluationInstance.findByVersion", query = "SELECT e FROM EvaluationInstance e WHERE e.version = :version")})
public class EvaluationInstance implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "anonymous_identity")
    private String anonymousIdentity;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationInstance")
    private List<CourseOfInstance> courseOfInstanceList;
    @JoinColumn(name = "evaluation_session", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private EvaluationSession evaluationSession;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evaluationInstance")
    private List<EvaluatedQuestionAnswer> evaluatedQuestionAnswerList;

    public EvaluationInstance() {
    }

    public EvaluationInstance(Integer id) {
        this.id = id;
    }

    public EvaluationInstance(Integer id, String anonymousIdentity) {
        this.id = id;
        this.anonymousIdentity = anonymousIdentity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnonymousIdentity() {
        return anonymousIdentity;
    }

    public void setAnonymousIdentity(String anonymousIdentity) {
        this.anonymousIdentity = anonymousIdentity;
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
        if (!(object instanceof EvaluationInstance)) {
            return false;
        }
        EvaluationInstance other = (EvaluationInstance) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluationInstance[ id=" + id + " ]";
    }
    
}
