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
@Table(name = "rating_type", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RatingType.findAll", query = "SELECT r FROM RatingType r"),
    @NamedQuery(name = "RatingType.findById", query = "SELECT r FROM RatingType r WHERE r.id = :id"),
    @NamedQuery(name = "RatingType.findByRatingType", query = "SELECT r FROM RatingType r WHERE r.ratingType = :ratingType"),
    @NamedQuery(name = "RatingType.findByActive", query = "SELECT r FROM RatingType r WHERE r.active = :active"),
    @NamedQuery(name = "RatingType.findByVersion", query = "SELECT r FROM RatingType r WHERE r.version = :version")})
public class RatingType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "rating_type")
    private String ratingType;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ratingType")
    private List<Rating> ratingList;
    @OneToMany(mappedBy = "ratingType")
    private List<Question> questionList;
    @OneToMany(mappedBy = "ratingType")
    private List<EvaluatedQuestion> evaluatedQuestionList;

    public RatingType() {
    }

    public RatingType(Short id) {
        this.id = id;
    }

    public RatingType(Short id, String ratingType) {
        this.id = id;
        this.ratingType = ratingType;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getRatingType() {
        return ratingType;
    }

    public void setRatingType(String ratingType) {
        this.ratingType = ratingType;
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
    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
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
        if (!(object instanceof RatingType)) {
            return false;
        }
        RatingType other = (RatingType) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.RatingType[ id=" + id + " ]";
    }
    
}
