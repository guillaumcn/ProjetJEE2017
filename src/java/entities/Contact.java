/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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
 * @author root
 */
@Entity
@Table(name = "contact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contact.findAll", query = "SELECT c FROM Contact c")
    , @NamedQuery(name = "Contact.findByIdcontact", query = "SELECT c FROM Contact c WHERE c.idcontact = :idcontact")
    , @NamedQuery(name = "Contact.findByLogin", query = "SELECT c FROM Contact c WHERE c.login = :login")
    , @NamedQuery(name = "Contact.findByPassword", query = "SELECT c FROM Contact c WHERE c.password = :password")
    , @NamedQuery(name = "Contact.findByAdmin", query = "SELECT c FROM Contact c WHERE c.admin = :admin")})
public class Contact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idcontact")
    private Integer idcontact;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "password")
    private String password;
    @Column(name = "admin")
    private Boolean admin;

    public Contact() {
    }

    public Contact(Integer idcontact) {
        this.idcontact = idcontact;
    }

    public Contact(Integer idcontact, String login, String password) {
        this.idcontact = idcontact;
        this.login = login;
        this.password = password;
    }
    
    public Contact(Integer idcontact, String login, String password, Boolean status) {
        this.idcontact = idcontact;
        this.login = login;
        this.password = password;
        this.admin = status;
    }

    public Integer getIdcontact() {
        return idcontact;
    }

    public void setIdcontact(Integer idcontact) {
        this.idcontact = idcontact;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcontact != null ? idcontact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contact)) {
            return false;
        }
        Contact other = (Contact) object;
        if ((this.idcontact == null && other.idcontact != null) || (this.idcontact != null && !this.idcontact.equals(other.idcontact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Contact[ idcontact=" + idcontact + " ]";
    }
    
}
