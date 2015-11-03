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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "contact", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c"),
    @NamedQuery(name = "Contact.findById", query = "SELECT c FROM Contact c WHERE c.id = :id"),
    @NamedQuery(name = "Contact.findByActive", query = "SELECT c FROM Contact c WHERE c.active = :active"),
    @NamedQuery(name = "Contact.findByVersion", query = "SELECT c FROM Contact c WHERE c.version = :version")})
public class Contact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Faculty> facultyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<PostalContact> postalContactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Department> departmentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<PhoneContact> phoneContactList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<Person> personList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contact")
    private List<EmailContact> emailContactList;

    public Contact() {
    }

    public Contact(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
    public List<Faculty> getFacultyList() {
        return facultyList;
    }

    public void setFacultyList(List<Faculty> facultyList) {
        this.facultyList = facultyList;
    }

    @XmlTransient
    public List<PostalContact> getPostalContactList() {
        return postalContactList;
    }

    public void setPostalContactList(List<PostalContact> postalContactList) {
        this.postalContactList = postalContactList;
    }

    @XmlTransient
    public List<Department> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<Department> departmentList) {
        this.departmentList = departmentList;
    }

    @XmlTransient
    public List<PhoneContact> getPhoneContactList() {
        return phoneContactList;
    }

    public void setPhoneContactList(List<PhoneContact> phoneContactList) {
        this.phoneContactList = phoneContactList;
    }

    @XmlTransient
    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
    }

    @XmlTransient
    public List<EmailContact> getEmailContactList() {
        return emailContactList;
    }

    public void setEmailContactList(List<EmailContact> emailContactList) {
        this.emailContactList = emailContactList;
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
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Contact[ id=" + id + " ]";
    }
    
}
