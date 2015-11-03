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
public class EmailContactDetails implements Serializable, Comparable<EmailContactDetails> {

    @Override
    public int compareTo(EmailContactDetails o) {
        return this.getEmailAddress().compareTo(o.getEmailAddress());
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
        if (!(object instanceof EmailContactDetails)) {
            return false;
        }
        EmailContactDetails other = (EmailContactDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "EmailContact[ email address=" + getEmailAddress() + " ]";
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
     * @return the emailAddress
     */
    public String getEmailAddress() {
        return emailAddress;
    }

    /**
     * @param emailAddress the emailAddress to set
     */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /**
     * @return the contact
     */
    public ContactDetails getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(ContactDetails contact) {
        this.contact = contact;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @param version the version to set
     */
    public void setVersion(Integer version) {
        this.version = version;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @return the version
     */
    public Integer getVersion() {
        return version;
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private String emailAddress;
    private ContactDetails contact;

}
