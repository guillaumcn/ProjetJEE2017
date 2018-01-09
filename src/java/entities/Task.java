/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "task")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Task.findAll", query = "SELECT t FROM Task t")
    , @NamedQuery(name = "Task.findByIdtask", query = "SELECT t FROM Task t WHERE t.idtask = :idtask")
    , @NamedQuery(name = "Task.findByContacts", query = "SELECT t FROM Task t WHERE t.contacts = :contacts")
    , @NamedQuery(name = "Task.findByDateStart", query = "SELECT t FROM Task t WHERE t.dateStart = :dateStart")
    , @NamedQuery(name = "Task.findByDateEnd", query = "SELECT t FROM Task t WHERE t.dateEnd = :dateEnd")
    , @NamedQuery(name = "Task.findByDescription", query = "SELECT t FROM Task t WHERE t.description = :description")
    , @NamedQuery(name = "Task.findByStatus", query = "SELECT t FROM Task t WHERE t.status = :status")})
public class Task implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtask")
    private Integer idtask;
    @Size(max = 255)
    @Column(name = "contacts")
    private String contacts;
    @Column(name = "dateStart")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;
    @Column(name = "dateEnd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    @Size(max = 255)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private int status;
    @JoinColumn(name = "idsprint", referencedColumnName = "idsprint")
    @OneToOne(optional = false)
    private Sprint idsprint;

    public Task() {
    }

    public Task(Integer idtask) {
        this.idtask = idtask;
    }

    /**
     * 
     * @param idSprint
     * @param status 
     * status possibles : 1 - 2 - 3 ou 4 pour "TODO" - "IN PROGRESS" - "DONE" ou "VALIDATED"
     */
    public Task(Sprint idSprint, int status) {
        this.idsprint = idSprint;
        this.status = status;
    }

    public Integer getIdtask() {
        return idtask;
    }

    public void setIdtask(Integer idtask) {
        this.idtask = idtask;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Sprint getIdsprint() {
        return idsprint;
    }

    public void setIdsprint(Sprint idsprint) {
        this.idsprint = idsprint;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtask != null ? idtask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.idtask == null && other.idtask != null) || (this.idtask != null && !this.idtask.equals(other.idtask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Task[ idtask=" + idtask + " ]";
    }
    
}
