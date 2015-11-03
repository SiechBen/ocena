/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.utilities;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Ben Siech
 */
public class UserAccountDetails implements Serializable, Comparable<UserAccountDetails> {

    @Override
    public int compareTo(UserAccountDetails o) {
        return this.getUsername().compareTo(o.getUsername());
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
        if (!(object instanceof UserAccountDetails)) {
            return false;
        }
        UserAccountDetails other = (UserAccountDetails) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "UserAccount[ username=" + getUsername() + " ]";
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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
     * @return the person
     */
    public PersonDetails getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(PersonDetails person) {
        this.person = person;
    }

    /**
     * @return the userGroup
     */
    public UserGroupDetail getUserGroup() {
        return userGroup;
    }

    /**
     * @param userGroup the userGroup to set
     */
    public void setUserGroup(UserGroupDetail userGroup) {
        this.userGroup = userGroup;
    }

    /**
     * @return the activeFrom
     */
    public Date getActiveFrom() {
        return activeFrom;
    }

    /**
     * @param activeFrom the activeFrom to set
     */
    public void setActiveFrom(Date activeFrom) {
        this.activeFrom = activeFrom;
    }

    /**
     * @return the deactivatedOn
     */
    public Date getDeactivatedOn() {
        return deactivatedOn;
    }

    /**
     * @param deactivatedOn the deactivatedOn to set
     */
    public void setDeactivatedOn(Date deactivatedOn) {
        this.deactivatedOn = deactivatedOn;
    }

    private Integer id;
    private Boolean active;
    private Integer version;
    private String username;
    private String password;
    private Date activeFrom;
    private Date deactivatedOn;
    private PersonDetails person;
    private UserGroupDetail userGroup;

}
