/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author root
 */
@Entity
@Table(name = "sprint")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sprint.findAll", query = "SELECT s FROM Sprint s")
    , @NamedQuery(name = "Sprint.findByIdsprint", query = "SELECT s FROM Sprint s WHERE s.idsprint = :idsprint")
    , @NamedQuery(name = "Sprint.findByDescription", query = "SELECT s FROM Sprint s WHERE s.description = :description")})
public class Sprint implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idsprint")
    private Integer idsprint;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "idrelease", referencedColumnName = "idrelease")
    @ManyToOne(cascade=REMOVE, optional = false)
    private ReleaseProject idrelease;

    public Sprint() {
    }

    public Sprint(Integer idsprint) {
        this.idsprint = idsprint;
    }
    
    public Sprint(Integer idsprint, String descrition, ReleaseProject release) {
        this.idsprint = idsprint;
        this.description = descrition;
        this.idrelease = release;
    }

    public Integer getIdsprint() {
        return idsprint;
    }

    public void setIdsprint(Integer idsprint) {
        this.idsprint = idsprint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ReleaseProject getIdrelease() {
        return idrelease;
    }

    public void setIdrelease(ReleaseProject idrelease) {
        this.idrelease = idrelease;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsprint != null ? idsprint.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sprint)) {
            return false;
        }
        Sprint other = (Sprint) object;
        if ((this.idsprint == null && other.idsprint != null) || (this.idsprint != null && !this.idsprint.equals(other.idsprint))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Sprint[ idsprint=" + idsprint + " ]";
    }
    
}
