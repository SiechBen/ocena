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
public class PhoneContactDetails implements Serializable, Comparable<PhoneContactDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhoneContactDetails)) {
            return false;
        }
        PhoneContactDetails other = (PhoneContactDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "PhoneContact[ mobile number=" + getMobileNumber() + " ]";
    }

    @Override
    public int compareTo(PhoneContactDetails o) {
        return this.getMobileNumber().compareTo(o.getMobileNumber());
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
     * @return the mobileNumber
     */
    public String getMobileNumber() {
        return mobileNumber;
    }

    /**
     * @param mobileNumber the mobileNumber to set
     */
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    /**
     * @return the fixedNumber
     */
    public String getFixedNumber() {
        return fixedNumber;
    }

    /**
     * @param fixedNumber the fixedNumber to set
     */
    public void setFixedNumber(String fixedNumber) {
        this.fixedNumber = fixedNumber;
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

    private Integer id;
    private Boolean active;
    private Integer version;
    private String fixedNumber;
    private String mobileNumber;
    private ContactDetails contact;

}
