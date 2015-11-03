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
public class CountryDetails implements Serializable, Comparable<CountryDetails> {

    @Override
    public int compareTo(CountryDetails o) {
        return this.getNiceName().compareTo(o.getNiceName());
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
        if (!(object instanceof CountryDetails)) {
            return false;
        }
        CountryDetails other = (CountryDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Country[ name=" + getNiceName() + " ]";
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
     * @return the iso
     */
    public String getIso() {
        return iso;
    }

    /**
     * @param iso the iso to set
     */
    public void setIso(String iso) {
        this.iso = iso;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the iso3
     */
    public String getIso3() {
        return iso3;
    }

    /**
     * @param iso3 the iso3 to set
     */
    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    /**
     * @return the numCode
     */
    public Short getNumCode() {
        return numCode;
    }

    /**
     * @param numCode the numCode to set
     */
    public void setNumCode(Short numCode) {
        this.numCode = numCode;
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
     * @return the niceName
     */
    public String getNiceName() {
        return niceName;
    }

    /**
     * @param niceName the niceName to set
     */
    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    /**
     * @return the phoneCode
     */
    public Integer getPhoneCode() {
        return phoneCode;
    }

    /**
     * @param phoneCode the phoneCode to set
     */
    public void setPhoneCode(Integer phoneCode) {
        this.phoneCode = phoneCode;
    }

    private Integer id;
    private String iso;
    private String name;
    private String iso3;
    private Short numCode;
    private Boolean active;
    private Integer version;
    private String niceName;
    private Integer phoneCode;

}
