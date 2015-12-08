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
@Table(name = "assessed_evaluation_comment", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AssessedEvaluationComment.findByAssessedEvaluationId", query = "SELECT a FROM AssessedEvaluationComment a WHERE a.assessedEvaluation.id = :assessedEvaluationId"),
    @NamedQuery(name = "AssessedEvaluationComment.findAll", query = "SELECT a FROM AssessedEvaluationComment a"),
    @NamedQuery(name = "AssessedEvaluationComment.findById", query = "SELECT a FROM AssessedEvaluationComment a WHERE a.id = :id"),
    @NamedQuery(name = "AssessedEvaluationComment.findByComment", query = "SELECT a FROM AssessedEvaluationComment a WHERE a.comment = :comment"),
    @NamedQuery(name = "AssessedEvaluationComment.findByActive", query = "SELECT a FROM AssessedEvaluationComment a WHERE a.active = :active"),
    @NamedQuery(name = "AssessedEvaluationComment.findByVersion", query = "SELECT a FROM AssessedEvaluationComment a WHERE a.version = :version")})
public class AssessedEvaluationComment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 300)
    @Column(name = "comment")
    private String comment;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "assessed_evaluation", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AssessedEvaluation assessedEvaluation;

    public AssessedEvaluationComment() {
    }

    public AssessedEvaluationComment(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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

    public AssessedEvaluation getAssessedEvaluation() {
        return assessedEvaluation;
    }

    public void setAssessedEvaluation(AssessedEvaluation assessedEvaluation) {
        this.assessedEvaluation = assessedEvaluation;
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
        if (!(object instanceof AssessedEvaluationComment)) {
            return false;
        }
        AssessedEvaluationComment other = (AssessedEvaluationComment) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.AssessedEvaluationComment[ id=" + id + " ]";
    }
    
}
