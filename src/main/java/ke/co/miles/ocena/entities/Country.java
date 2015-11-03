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
@Table(name = "country", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Country.findAll", query = "SELECT c FROM Country c"),
    @NamedQuery(name = "Country.findById", query = "SELECT c FROM Country c WHERE c.id = :id"),
    @NamedQuery(name = "Country.findByIso", query = "SELECT c FROM Country c WHERE c.iso = :iso"),
    @NamedQuery(name = "Country.findByName", query = "SELECT c FROM Country c WHERE c.name = :name"),
    @NamedQuery(name = "Country.findByNiceName", query = "SELECT c FROM Country c WHERE c.niceName = :niceName"),
    @NamedQuery(name = "Country.findByIso3", query = "SELECT c FROM Country c WHERE c.iso3 = :iso3"),
    @NamedQuery(name = "Country.findByNumCode", query = "SELECT c FROM Country c WHERE c.numCode = :numCode"),
    @NamedQuery(name = "Country.findByPhoneCode", query = "SELECT c FROM Country c WHERE c.phoneCode = :phoneCode"),
    @NamedQuery(name = "Country.findByActive", query = "SELECT c FROM Country c WHERE c.active = :active"),
    @NamedQuery(name = "Country.findByVersion", query = "SELECT c FROM Country c WHERE c.version = :version")})
public class Country implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "iso")
    private String iso;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 80)
    @Column(name = "nice_name")
    private String niceName;
    @Size(max = 3)
    @Column(name = "iso3")
    private String iso3;
    @Column(name = "num_code")
    private Short numCode;
    @Basic(optional = false)
    @NotNull
    @Column(name = "phone_code")
    private int phoneCode;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<Institution> institutionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private List<PostalContact> postalContactList;

    public Country() {
    }

    public Country(Integer id) {
        this.id = id;
    }

    public Country(Integer id, String iso, String name, String niceName, int phoneCode) {
        this.id = id;
        this.iso = iso;
        this.name = name;
        this.niceName = niceName;
        this.phoneCode = phoneCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNiceName() {
        return niceName;
    }

    public void setNiceName(String niceName) {
        this.niceName = niceName;
    }

    public String getIso3() {
        return iso3;
    }

    public void setIso3(String iso3) {
        this.iso3 = iso3;
    }

    public Short getNumCode() {
        return numCode;
    }

    public void setNumCode(Short numCode) {
        this.numCode = numCode;
    }

    public int getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode(int phoneCode) {
        this.phoneCode = phoneCode;
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
    public List<Institution> getInstitutionList() {
        return institutionList;
    }

    public void setInstitutionList(List<Institution> institutionList) {
        this.institutionList = institutionList;
    }

    @XmlTransient
    public List<PostalContact> getPostalContactList() {
        return postalContactList;
    }

    public void setPostalContactList(List<PostalContact> postalContactList) {
        this.postalContactList = postalContactList;
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
        if (!(object instanceof Country)) {
            return false;
        }
        Country other = (Country) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.Country[ id=" + id + " ]";
    }
    
}
