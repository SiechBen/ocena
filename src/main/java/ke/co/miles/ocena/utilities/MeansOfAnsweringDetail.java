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
public enum MeansOfAnsweringDetail implements Serializable {

    BY_RATING(new Short("1"), "By rating"),
    BY_REASONING(new Short("2"), "By reasoning"),
    BY_LISTING_COMMENTS(new Short("3"), "By listing comments");

    private MeansOfAnsweringDetail(Short id, String meansOfAnswering) {
        this.id = id;
        this.meansOfAnswering = meansOfAnswering;
    }

    public static MeansOfAnsweringDetail getMeansOfAnswering(Short id) {
        switch (id) {
            case 1:
                return BY_RATING;
            case 2:
                return BY_REASONING;
            case 3:
                return BY_LISTING_COMMENTS;
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
     * @return the meansOfAnswering
     */
    public String getMeansOfAnswering() {
        return meansOfAnswering;
    }

    private final Short id;
    private final String meansOfAnswering;

}
