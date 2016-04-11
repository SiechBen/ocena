/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "faculty_member", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultyMember.findNonStudentsByFacultyId", query = "SELECT f FROM FacultyMember f WHERE f.faculty.id = :facultyId AND NOT f.facultyMemberRole.id = :facultyMemberRoleId"),
    @NamedQuery(name = "FacultyMember.findNonStudentsByDepartmentId", query = "SELECT f FROM FacultyMember f WHERE f.department.id = :departmentId AND NOT f.facultyMemberRole.id = :facultyMemberRoleId"),
    @NamedQuery(name = "FacultyMember.findActiveByFacultyId", query = "SELECT f FROM FacultyMember f WHERE f.active = :active AND f.faculty.id = :facultyId"),
    @NamedQuery(name = "FacultyMember.findActiveByDepartmentId", query = "SELECT f FROM FacultyMember f WHERE f.active = :active AND f.department.id = :departmentId"),
    @NamedQuery(name = "FacultyMember.findByPersonId", query = "SELECT f FROM FacultyMember f WHERE f.person.id = :personId"),
    @NamedQuery(name = "FacultyMember.findAll", query = "SELECT f FROM FacultyMember f"),
    @NamedQuery(name = "FacultyMember.findById", query = "SELECT f FROM FacultyMember f WHERE f.id = :id"),
    @NamedQuery(name = "FacultyMember.findByAdmissionYear", query = "SELECT f FROM FacultyMember f WHERE f.admissionYear = :admissionYear"),
    @NamedQuery(name = "FacultyMember.findByActive", query = "SELECT f FROM FacultyMember f WHERE f.active = :active"),
    @NamedQuery(name = "FacultyMember.findByVersion", query = "SELECT f FROM FacultyMember f WHERE f.version = :version")})
public class FacultyMember implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "admission_year")
    @Temporal(TemporalType.DATE)
    private Date admissionYear;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "department", referencedColumnName = "id")
    @ManyToOne
    private Department department;
    @JoinColumn(name = "faculty", referencedColumnName = "id")
    @ManyToOne
    private Faculty faculty;
    @JoinColumn(name = "person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person person;
    @JoinColumn(name = "faculty_member_role", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private FacultyMemberRole facultyMemberRole;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyMember")
    private List<StudentFeedback> studentFeedbackList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyMember")
    private List<CourseOfSession> courseOfSessionList;

    public FacultyMember() {
    }

    public FacultyMember(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Date admissionYear) {
        this.admissionYear = admissionYear;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public FacultyMemberRole getFacultyMemberRole() {
        return facultyMemberRole;
    }

    public void setFacultyMemberRole(FacultyMemberRole facultyMemberRole) {
        this.facultyMemberRole = facultyMemberRole;
    }

    @XmlTransient
    public List<StudentFeedback> getStudentFeedbackList() {
        return studentFeedbackList;
    }

    public void setStudentFeedbackList(List<StudentFeedback> studentFeedbackList) {
        this.studentFeedbackList = studentFeedbackList;
    }

    @XmlTransient
    public List<CourseOfSession> getCourseOfSessionList() {
        return courseOfSessionList;
    }

    public void setCourseOfSessionList(List<CourseOfSession> courseOfSessionList) {
        this.courseOfSessionList = courseOfSessionList;
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
        if (!(object instanceof FacultyMember)) {
            return false;
        }
        FacultyMember other = (FacultyMember) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.FacultyMember[ id=" + id + " ]";
    }

}
