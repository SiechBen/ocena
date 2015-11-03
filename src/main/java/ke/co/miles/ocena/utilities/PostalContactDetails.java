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
public class PostalContactDetails implements Serializable, Comparable<PostalContactDetails> {

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PostalContactDetails)) {
            return false;
        }
        PostalContactDetails other = (PostalContactDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public int compareTo(PostalContactDetails o) {
        return this.getBoxNumber().compareTo(o.getBoxNumber());
    }

    @Override
    public String toString() {
        return "PostalContact[ box number= " + getBoxNumber() + " ]";
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
     * @return the town
     */
    public String getTown() {
        return town;
    }

    /**
     * @param town the town to set
     */
    public void setTown(String town) {
        this.town = town;
    }

    /**
     * @return the country
     */
    public CountryDetails getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(CountryDetails country) {
        this.country = country;
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
     * @return the boxNumber
     */
    public String getBoxNumber() {
        return boxNumber;
    }

    /**
     * @param boxNumber the boxNumber to set
     */
    public void setBoxNumber(String boxNumber) {
        this.boxNumber = boxNumber;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
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
    private String town;
    private Boolean active;
    private Integer version;
    private String boxNumber;
    private String postalCode;
    private CountryDetails country;
    private ContactDetails contact;

}
