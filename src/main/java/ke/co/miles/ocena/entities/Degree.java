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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "degree", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Degree.findByAdmissionIdAndFacultyId", query = "SELECT d FROM Degree d WHERE d.admission.id = :admissionId AND d.faculty.id = :facultyId"),
    @NamedQuery(name = "Degree.findByAdmissionIdAndDepartmentId", query = "SELECT d FROM Degree d WHERE d.admission.id = :admissionId AND d.department.id = :departmentId"),
    @NamedQuery(name = "Degree.findByFacultyId", query = "SELECT d FROM Degree d WHERE d.faculty.id = :facultyId"),
    @NamedQuery(name = "Degree.findByDepartmentId", query = "SELECT d FROM Degree d WHERE d.department.id = :departmentId"),
    @NamedQuery(name = "Degree.findAll", query = "SELECT d FROM Degree d"),
    @NamedQuery(name = "Degree.findById", query = "SELECT d FROM Degree d WHERE d.id = :id"),
    @NamedQuery(name = "Degree.findByName", query = "SELECT d FROM Degree d WHERE d.name = :name"),
    @NamedQuery(name = "Degree.findByActive", query = "SELECT d FROM Degree d WHERE d.active = :active"),
    @NamedQuery(name = "Degree.findByVersion", query = "SELECT d FROM Degree d WHERE d.version = :version")})
public class Degree implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 120)
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "degree")
    private List<EvaluationSession> evaluationSessionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "degree")
    private List<Course> courseList;
    @JoinColumn(name = "admission", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Admission admission;
    @JoinColumn(name = "department", referencedColumnName = "id")
    @ManyToOne
    private Department department;
    @JoinColumn(name = "faculty", referencedColumnName = "id")
    @ManyToOne
    private Faculty faculty;

    public Degree() {
    }

    public Degree(Integer id) {
        this.id = id;
    }

    public Degree(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    public List<EvaluationSession> getEvaluationSessionList() {
        return evaluationSessionList;
    }

    public void setEvaluationSessionList(List<EvaluationSession> evaluationSessionList) {
        this.evaluationSessionList = evaluationSessionList;
    }

    @XmlTransient
    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
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
        if (!(object instanceof Degree)) {
            return false;
        }
        Degree other = (Degree) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Degree[ id=" + id + " ]";
    }
    
}
