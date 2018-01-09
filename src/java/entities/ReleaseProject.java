/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author root
 */
@Entity
@Table(name = "releaseProject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ReleaseProject.findAll", query = "SELECT r FROM ReleaseProject r")
    , @NamedQuery(name = "ReleaseProject.findByIdrelease", query = "SELECT r FROM ReleaseProject r WHERE r.idrelease = :idrelease")
    , @NamedQuery(name = "ReleaseProject.findByDaterelease", query = "SELECT r FROM ReleaseProject r WHERE r.daterelease = :daterelease")
    , @NamedQuery(name = "ReleaseProject.findByVersion", query = "SELECT r FROM ReleaseProject r WHERE r.version = :version")})
public class ReleaseProject implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idrelease")
    private Integer idrelease;
    @Column(name = "daterelease")
    @Temporal(TemporalType.TIMESTAMP)
    private Date daterelease;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "version")
    private String version;
    @JoinColumn(name = "idproject", referencedColumnName = "idproject")
    @OneToOne(optional = false)
    private Project idproject;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrelease")
    private Collection<Sprint> sprintCollection;

    public ReleaseProject() {
    }

    public ReleaseProject(Integer idrelease) {
        this.idrelease = idrelease;
    }

    public ReleaseProject(Integer idrelease, String version) {
        this.idrelease = idrelease;
        this.version = version;
    }
    
    /**
     * 
     * @param idProject
     * @param version 
     * constructeur à utiliser depuis la maj de bd
     * Crée une release depuis un idProject avec une description version 
     */
    public ReleaseProject(Project idProject, String version){
        this.idproject = idProject;
        this.version = version;
    }

    public Integer getIdrelease() {
        return idrelease;
    }

    public void setIdrelease(Integer idrelease) {
        this.idrelease = idrelease;
    }

    public Date getDaterelease() {
        return daterelease;
    }

    public void setDaterelease(Date daterelease) {
        this.daterelease = daterelease;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Project getIdproject() {
        return idproject;
    }

    public void setIdproject(Project idproject) {
        this.idproject = idproject;
    }

    @XmlTransient
    public Collection<Sprint> getSprintCollection() {
        return sprintCollection;
    }

    public void setSprintCollection(Collection<Sprint> sprintCollection) {
        this.sprintCollection = sprintCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrelease != null ? idrelease.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ReleaseProject)) {
            return false;
        }
        ReleaseProject other = (ReleaseProject) object;
        if ((this.idrelease == null && other.idrelease != null) || (this.idrelease != null && !this.idrelease.equals(other.idrelease))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ReleaseProject[ idrelease=" + idrelease + " ]";
    }
    
}
