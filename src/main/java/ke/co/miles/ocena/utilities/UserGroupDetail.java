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
public enum UserGroupDetail implements Serializable {

    MANAGEMENT(new Short("1"), "Management"),
    LECTURER(new Short("2"), "Lecturer"),
    STUDENT(new Short("3"), "Student"),
    OTHER_STAFF(new Short("4"), "Other staff"),
    ADMIN(new Short("5"), "Admin");

    private UserGroupDetail(Short id, String userGroup) {
        this.id = id;
        this.userGroup = userGroup;
    }

    public static UserGroupDetail getUserGroupDetail(Short id) {
        switch (id) {
            case 1:
                return MANAGEMENT;
            case 2:
                return LECTURER;
            case 3:
                return STUDENT;
            case 4:
                return OTHER_STAFF;
            case 5:
                return ADMIN;
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
     * @return the role
     */
    public String getUserGroup() {
        return userGroup;
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

    @Override
    public String toString() {
        return userGroup;
    }

    private final Short id;
    private Boolean active;
    private Integer version;
    private final String userGroup;

}
