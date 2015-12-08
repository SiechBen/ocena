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
@Table(name = "postal_contact", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PostalContact.findByContactId", query = "SELECT p FROM PostalContact p WHERE p.contact.id = :contactId"),
    @NamedQuery(name = "PostalContact.findAll", query = "SELECT p FROM PostalContact p"),
    @NamedQuery(name = "PostalContact.findById", query = "SELECT p FROM PostalContact p WHERE p.id = :id"),
    @NamedQuery(name = "PostalContact.findByBoxNumber", query = "SELECT p FROM PostalContact p WHERE p.boxNumber = :boxNumber"),
    @NamedQuery(name = "PostalContact.findByPostalCode", query = "SELECT p FROM PostalContact p WHERE p.postalCode = :postalCode"),
    @NamedQuery(name = "PostalContact.findByTown", query = "SELECT p FROM PostalContact p WHERE p.town = :town"),
    @NamedQuery(name = "PostalContact.findByActive", query = "SELECT p FROM PostalContact p WHERE p.active = :active"),
    @NamedQuery(name = "PostalContact.findByVersion", query = "SELECT p FROM PostalContact p WHERE p.version = :version")})
public class PostalContact implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Size(max = 20)
    @Column(name = "box_number")
    private String boxNumber;
    @Size(max = 20)
    @Column(name = "postal_code")
    private String postalCode;
    @Size(max = 100)
    @Column(name = "town")
    private String town;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @JoinColumn(name = "contact", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contact;
    @JoinColumn(name = "country", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Country country;

    public PostalContact() {
    }

    public PostalContact(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBoxNumber() {
        return boxNumber;
    }

    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
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
        if (!(object instanceof PostalContact)) {
            return false;
        }
        PostalContact other = (PostalContact) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.PostalContact[ id=" + id + " ]";
    }
    
}
