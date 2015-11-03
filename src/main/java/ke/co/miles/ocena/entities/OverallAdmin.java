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
@Table(name = "overall_admin", catalog = "ocena", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OverallAdmin.findAll", query = "SELECT o FROM OverallAdmin o"),
    @NamedQuery(name = "OverallAdmin.findById", query = "SELECT o FROM OverallAdmin o WHERE o.id = :id"),
    @NamedQuery(name = "OverallAdmin.findByUsername", query = "SELECT o FROM OverallAdmin o WHERE o.username = :username"),
    @NamedQuery(name = "OverallAdmin.findByPassword", query = "SELECT o FROM OverallAdmin o WHERE o.password = :password"),
    @NamedQuery(name = "OverallAdmin.findByActive", query = "SELECT o FROM OverallAdmin o WHERE o.active = :active"),
    @NamedQuery(name = "OverallAdmin.findByVersion", query = "SELECT o FROM OverallAdmin o WHERE o.version = :version")})
public class OverallAdmin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    @Size(max = 150)
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "version")
    private Integer version;

    public OverallAdmin() {
    }

    public OverallAdmin(Integer id) {
        this.id = id;
    }

    public OverallAdmin(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OverallAdmin)) {
            return false;
        }
        OverallAdmin other = (OverallAdmin) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "ke.co.miles.ocena.entities.OverallAdmin[ id=" + id + " ]";
    }
    
}
