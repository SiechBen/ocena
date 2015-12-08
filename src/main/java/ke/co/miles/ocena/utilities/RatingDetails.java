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
public class RatingDetails implements Serializable, Comparable<RatingDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatingDetails)) {
            return false;
        }
        RatingDetails other = (RatingDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.getId().equals(other.getId())));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Rating[ rating =" + getRating() + " ]";
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
     * @return the rating
     */
    public String getRating() {
        return rating;
    }

    /**
     * @param rating the rating to set
     */
    public void setRating(String rating) {
        this.rating = rating;
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
     * @return the ratingType
     */
    public RatingTypeDetail getRatingType() {
        return ratingType;
    }

    /**
     * @param ratingType the ratingType to set
     */
    public void setRatingType(RatingTypeDetail ratingType) {
        this.ratingType = ratingType;
    }

    @Override
    public int compareTo(RatingDetails o) {
        return this.getRating().compareTo(o.getRating());
    }

    private Short id;
    private String rating;
    private Boolean active;
    private Integer version;
    private RatingTypeDetail ratingType;

}
