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
public enum FacultyMemberRoleDetail implements Serializable {

    MANAGEMENT(new Short("1"), "Management"),
    LECTURER(new Short("2"), "Lecturer"),
    STUDENT(new Short("3"), "Student"),
    OTHER_STAFF(new Short("4"), "Other staff");

    private FacultyMemberRoleDetail(Short id, String facultyMemberRole) {
        this.id = id;
        this.facultyMemberRole = facultyMemberRole;
    }

    public static FacultyMemberRoleDetail getFacultyMemberRoleDetail(Short id) {
        switch (id) {
            case 1:
                return MANAGEMENT;
            case 2:
                return LECTURER;
            case 3:
                return STUDENT;
            case 4:
                return OTHER_STAFF;
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
     * @return the facultyMemberRole
     */
    public String getFacultyMemberRole() {
        return facultyMemberRole;
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

    private final Short id;
    private final String facultyMemberRole;
    private Boolean active;
    private Integer version;

}
