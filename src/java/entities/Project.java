/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "project")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Project.findAll", query = "SELECT p FROM Project p")
    , @NamedQuery(name = "Project.findByIdproject", query = "SELECT p FROM Project p WHERE p.idproject = :idproject")
    , @NamedQuery(name = "Project.findByNom", query = "SELECT p FROM Project p WHERE p.nom = :nom")
    , @NamedQuery(name = "Project.findByDescription", query = "SELECT p FROM Project p WHERE p.description = :description")
    , @NamedQuery(name = "Project.findByContacts", query = "SELECT p FROM Project p WHERE p.contacts = :contacts")
    , @NamedQuery(name = "Project.findByStartDate", query = "SELECT p FROM Project p WHERE p.startDate = :startDate")
    , @NamedQuery(name = "Project.findByEndDate", query = "SELECT p FROM Project p WHERE p.endDate = :endDate")})
public class Project implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idproject")
    private Integer idproject;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "nom")
    private String nom;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Size(max = 255)
    @Column(name = "contacts")
    private String contacts;
    @Column(name = "startDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idproject")
    private ReleaseProject releaseProject;

    public Project() {
    }

    public Project(Integer idproject) {
        this.idproject = idproject;
        this.contacts = "[]";
    }

    public Project(Integer idproject, String nom) {
        this.idproject = idproject;
        this.nom = nom;
        this.contacts = "[]";
    }
    
    public Project(Integer idproject, String nom, String description) {
        this.idproject = idproject;
        this.nom = nom;
        this.description = description;
        this.contacts = "[]";
    }
    
    public Project(Integer idproject, String nom, String description, String startDate) {
        this.idproject = idproject;
        this.nom = nom;
        this.description = description;
        this.startDate = new Date(startDate);
        this.contacts = "[]";
    }

    public Integer getIdproject() {
        return idproject;
    }

    public void setIdproject(Integer idproject) {
        this.idproject = idproject;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(Integer contacts) {
        String temp = this.contacts.substring(1);
        String temp2 = temp.substring(0, temp.length() - 1);
        temp2 += contacts + ",";
        this.contacts = "[" + temp2 + "]";
    }
    
    // Méthode vérifiant si l'ID Contact fait déjà partie de la liste
    public boolean checkContact(Integer contact) {
        String temp = this.contacts.substring(1);
        String temp2 = temp.substring(0, temp.length() - 1);
        String[] ids = temp2.split(",");
        
        for(int i = 0; i < ids.length; i++) {
            if(contact == Integer.parseInt(ids[i])) {
                return true;
            }
        }
        
        return false;
    }
    
    // Retire un utilisateur de la liste
    public void removeContact(Integer contact) {
        String temp = this.contacts.substring(1);
        String temp2 = temp.substring(0, temp.length() - 1);
        String[] ids = temp2.split(",");
        String contacts = "[";
        for(int i = 0; i < ids.length; i++) {
            if(contact != Integer.parseInt(ids[i])) {
                contacts += ids[i] + ",";
            }
        }
        contacts += "]";
        this.contacts = contacts;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public ReleaseProject getReleaseProject() {
        return releaseProject;
    }

    public void setReleaseProject(ReleaseProject releaseProject) {
        this.releaseProject = releaseProject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idproject != null ? idproject.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Project)) {
            return false;
        }
        Project other = (Project) object;
        if ((this.idproject == null && other.idproject != null) || (this.idproject != null && !this.idproject.equals(other.idproject))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Project[ idproject=" + idproject + " ]";
    }
    
}
