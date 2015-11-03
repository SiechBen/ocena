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
@Table(name = "faculty_member_role", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "FacultyMemberRole.findAll", query = "SELECT f FROM FacultyMemberRole f"),
    @NamedQuery(name = "FacultyMemberRole.findById", query = "SELECT f FROM FacultyMemberRole f WHERE f.id = :id"),
    @NamedQuery(name = "FacultyMemberRole.findByFacultyMemberRole", query = "SELECT f FROM FacultyMemberRole f WHERE f.facultyMemberRole = :facultyMemberRole"),
    @NamedQuery(name = "FacultyMemberRole.findByActive", query = "SELECT f FROM FacultyMemberRole f WHERE f.active = :active"),
    @NamedQuery(name = "FacultyMemberRole.findByVersion", query = "SELECT f FROM FacultyMemberRole f WHERE f.version = :version")})
public class FacultyMemberRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Short id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "faculty_member_role")
    private String facultyMemberRole;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "facultyMemberRole")
    private List<FacultyMember> facultyMemberList;

    public FacultyMemberRole() {
    }

    public FacultyMemberRole(Short id) {
        this.id = id;
    }

    public FacultyMemberRole(Short id, String facultyMemberRole) {
        this.id = id;
        this.facultyMemberRole = facultyMemberRole;
    }

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getFacultyMemberRole() {
        return facultyMemberRole;
    }

    public void setFacultyMemberRole(String facultyMemberRole) {
        this.facultyMemberRole = facultyMemberRole;
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
    public List<FacultyMember> getFacultyMemberList() {
        return facultyMemberList;
    }

    public void setFacultyMemberList(List<FacultyMember> facultyMemberList) {
        this.facultyMemberList = facultyMemberList;
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
        if (!(object instanceof FacultyMemberRole)) {
            return false;
        }
        FacultyMemberRole other = (FacultyMemberRole) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.FacultyMemberRole[ id=" + id + " ]";
    }
    
}
