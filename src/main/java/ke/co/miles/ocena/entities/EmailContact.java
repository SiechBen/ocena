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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author siech
 */
@Entity
@Table(name = "email_contact", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmailContact.findByContactId", query = "SELECT e FROM EmailContact e WHERE e.contact.id = :contactId"),
    @NamedQuery(name = "EmailContact.findAll", query = "SELECT e FROM EmailContact e"),
    @NamedQuery(name = "EmailContact.findById", query = "SELECT e FROM EmailContact e WHERE e.id = :id"),
    @NamedQuery(name = "EmailContact.findByEmailAddress", query = "SELECT e FROM EmailContact e WHERE e.emailAddress = :emailAddress"),
    @NamedQuery(name = "EmailContact.findByActive", query = "SELECT e FROM EmailContact e WHERE e.active = :active"),
    @NamedQuery(name = "EmailContact.findByVersion", query = "SELECT e FROM EmailContact e WHERE e.version = :version")})
public class EmailContact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 65)
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "contact", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contact;

    public EmailContact() {
    }

    public EmailContact(Integer id) {
        this.id = id;
    }

    public EmailContact(Integer id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
        if (!(object instanceof EmailContact)) {
            return false;
        }
        EmailContact other = (EmailContact) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.EmailContact[ id=" + id + " ]";
    }
    
}
