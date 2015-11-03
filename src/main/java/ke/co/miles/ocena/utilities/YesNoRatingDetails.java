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
public class YesNoRatingDetails implements Serializable, Comparable<YesNoRatingDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof YesNoRatingDetails)) {
            return false;
        }
        YesNoRatingDetails other = (YesNoRatingDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "YesNoRating[ rating=" + getYesNoRating() + " ]";
    }

    @Override
    public int compareTo(YesNoRatingDetails o) {
        return this.getYesNoRating().compareTo(yesNoRating);
    }

    /**
     * @return the id
     */
    public Short getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Short id) {
        this.id = id;
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
     * @return the yesNoRating
     */
    public String getYesNoRating() {
        return yesNoRating;
    }

    /**
     * @param yesNoRating the yesNoRating to set
     */
    public void setYesNoRating(String yesNoRating) {
        this.yesNoRating = yesNoRating;
    }

    private Short id;
    private Boolean active;
    private Integer version;
    private String yesNoRating;

}
