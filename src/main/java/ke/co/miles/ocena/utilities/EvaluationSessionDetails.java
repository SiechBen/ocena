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
public class EvaluationSessionDetails implements Serializable, Comparable<EvaluationSessionDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EvaluationSessionDetails)) {
            return false;
        }
        EvaluationSessionDetails other = (EvaluationSessionDetails) object;
        return !((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EvaluationSession[ start date=" + getStartDate() + " ]";
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
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
     * @return the semester
     */
    public String getSemester() {
        return semester;
    }

    /**
     * @param semester the semester to set
     */
    public void setSemester(String semester) {
        this.semester = semester;
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
     * @return the academicYear
     */
    public String getAcademicYear() {
        return academicYear;
    }

    /**
     * @param academicYear the academicYear to set
     */
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    /**
     * @return the degree
     */
    public DegreeDetails getDegree() {
        return degree;
    }

    /**
     * @param degree the degree to set
     */
    public void setDegree(DegreeDetails degree) {
        this.degree = degree;
    }

    @Override
    public int compareTo(EvaluationSessionDetails o) {
        return this.getStartDate().compareTo(o.getStartDate());
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
    private Date endDate;
    private Date startDate;
    private Boolean active;
    private String semester;
    private Integer version;
    private Date admissionYear;
    private String academicYear;
    private DegreeDetails degree;

}
