/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "phone_contact", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PhoneContact.findByContactId", query = "SELECT p FROM PhoneContact p WHERE p.contact.id = :contactId"),
    @NamedQuery(name = "PhoneContact.findAll", query = "SELECT p FROM PhoneContact p"),
    @NamedQuery(name = "PhoneContact.findById", query = "SELECT p FROM PhoneContact p WHERE p.id = :id"),
    @NamedQuery(name = "PhoneContact.findByMobileNumber", query = "SELECT p FROM PhoneContact p WHERE p.mobileNumber = :mobileNumber"),
    @NamedQuery(name = "PhoneContact.findByFixedNumber", query = "SELECT p FROM PhoneContact p WHERE p.fixedNumber = :fixedNumber"),
    @NamedQuery(name = "PhoneContact.findByActive", query = "SELECT p FROM PhoneContact p WHERE p.active = :active"),
    @NamedQuery(name = "PhoneContact.findByVersion", query = "SELECT p FROM PhoneContact p WHERE p.version = :version")})
public class PhoneContact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Size(max = 20)
    @Column(name = "fixed_number")
    private String fixedNumber;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "contact", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contact;

    public PhoneContact() {
    }

    public PhoneContact(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFixedNumber() {
        return fixedNumber;
    }

    public void setFixedNumber(String fixedNumber) {
        this.fixedNumber = fixedNumber;
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

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
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
        if (!(object instanceof PhoneContact)) {
            return false;
        }
        PhoneContact other = (PhoneContact) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.PhoneContact[ id=" + id + " ]";
    }
    
}
