/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.utilities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ben Siech
 */
public class FacultyMemberDetails implements Serializable, Comparable<FacultyDetails> {

    @Override
    public int compareTo(FacultyDetails o) {
        return this.getId().compareTo(o.getId());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FacultyMemberDetails)) {
            return false;
        }
        FacultyMemberDetails other = (FacultyMemberDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "FacultyMember[ id=" + getId() + " ]";
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
     * @return the person
     */
    public PersonDetails getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(PersonDetails person) {
        this.person = person;
    }

    /**
     * @return the faculty
     */
    public FacultyDetails getFaculty() {
        return faculty;
    }

    /**
     * @param faculty the faculty to set
     */
    public void setFaculty(FacultyDetails faculty) {
        this.faculty = faculty;
    }

    /**
     * @return the facultyMemberRole
     */
    public FacultyMemberRoleDetail getFacultyMemberRole() {
        return facultyMemberRole;
    }

    /**
     * @param facultyMemberRole
     */
    public void setFacultyMemberRole(FacultyMemberRoleDetail facultyMemberRole) {
        this.facultyMemberRole = facultyMemberRole;
    }

    /**
     * @return the department
     */
    public DepartmentDetails getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(DepartmentDetails department) {
        this.department = department;
    }

    /**
     * @return the admissionYear
     */
    public Date getAdmissionYear() {
        return admissionYear;
    }

    /**
     * @param admissionYear the admissionYear to set
     */
    public void setAdmissionYear(Date admissionYear) {
        this.admissionYear = admissionYear;
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private Date admissionYear;
    private PersonDetails person;
    private FacultyDetails faculty;
    private DepartmentDetails department;
    private FacultyMemberRoleDetail facultyMemberRole;

}
