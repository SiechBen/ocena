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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "means_of_answering", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeansOfAnswering.findAll", query = "SELECT m FROM MeansOfAnswering m"),
    @NamedQuery(name = "MeansOfAnswering.findById", query = "SELECT m FROM MeansOfAnswering m WHERE m.id = :id"),
    @NamedQuery(name = "MeansOfAnswering.findByMeansOfAnswering", query = "SELECT m FROM MeansOfAnswering m WHERE m.meansOfAnswering = :meansOfAnswering"),
    @NamedQuery(name = "MeansOfAnswering.findByActive", query = "SELECT m FROM MeansOfAnswering m WHERE m.active = :active"),
    @NamedQuery(name = "MeansOfAnswering.findByVersion", query = "SELECT m FROM MeansOfAnswering m WHERE m.version = :version")})
public class MeansOfAnswering implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "means_of_answering")
    private String meansOfAnswering;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meansOfAnswering")
    private List<Question> questionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "meansOfAnswering")
    private List<EvaluatedQuestion> evaluatedQuestionList;

    public MeansOfAnswering() {
    }

    public MeansOfAnswering(Short id) {
        this.id = id;
    }

    public MeansOfAnswering(Short id, String meansOfAnswering) {
        this.id = id;
        this.meansOfAnswering = meansOfAnswering;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getMeansOfAnswering() {
        return meansOfAnswering;
    }

    public void setMeansOfAnswering(String meansOfAnswering) {
        this.meansOfAnswering = meansOfAnswering;
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
        if (!(object instanceof MeansOfAnswering)) {
            return false;
        }
        MeansOfAnswering other = (MeansOfAnswering) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.MeansOfAnswering[ id=" + id + " ]";
    }
    
}
