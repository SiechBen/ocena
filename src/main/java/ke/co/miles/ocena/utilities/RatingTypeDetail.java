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
public enum RatingTypeDetail implements Serializable {

    STAR(new Short("1"), "star"),
    BOOLEAN(new Short("2"), "boolean"),
    YES_OR_NO(new Short("3"), "yes or no"),
    PERCENTAGE(new Short("4"), "percentage");

    private RatingTypeDetail(Short id, String ratingType) {
        this.id = id;
        this.ratingType = ratingType;
    }

    public static RatingTypeDetail getRatingType(Short id) {
        switch (id) {
            case 1:
                return STAR;
            case 2:
                return BOOLEAN;
            case 3:
                return YES_OR_NO;
            case 4:
                return PERCENTAGE;
            default:
                return null;
        }
    }

    /**
     * @return the id
     */
    public Short getId() {
        return id;
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
    public String getRatingType() {
        return ratingType;
    }
    
      @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Rating[ rating type = " + getRatingType() + " ]";
    }
    

    private final Short id;
    private Boolean active;
    private Integer version;
    private final String ratingType;

}
