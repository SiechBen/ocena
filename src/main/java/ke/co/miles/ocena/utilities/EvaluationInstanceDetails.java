/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.utilities;

import java.io.Serializable;

/**
 *
 * @author Ben Siech
 */
public class EvaluationInstanceDetails implements Serializable, Comparable<EvaluationInstanceDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluationInstanceDetails)) {
            return false;
        }
        EvaluationInstanceDetails other = (EvaluationInstanceDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluationInstanceDetails[ anonymous person=" + getAnonymousIdentity()+ " ]";
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the anonymousIdentity
     */
    public String getAnonymousIdentity() {
        return anonymousIdentity;
    }

    /**
     * @param anonymousIdentity the anonymousIdentity to set
     */
    public void setAnonymousIdentity(String anonymousIdentity) {
        this.anonymousIdentity = anonymousIdentity;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the evaluationSession
     */
    public EvaluationSessionDetails getEvaluationSession() {
        return evaluationSession;
    }

    /**
     * @param evaluationSession the evaluationSession to set
     */
    public void setEvaluationSession(EvaluationSessionDetails evaluationSession) {
        this.evaluationSession = evaluationSession;
    }

    @Override
    public int compareTo(EvaluationInstanceDetails o) {
        return this.getAnonymousIdentity().compareTo(o.getAnonymousIdentity());
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private String anonymousIdentity;
    private EvaluationSessionDetails evaluationSession;

}
