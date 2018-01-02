/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Project;
import entities.Status;
import entities.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author Nicolas
 */
@Stateless
@Path("task")
public class TaskFacadeREST {
    
    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    // Creation d'une tache
    @GET
    @Path("create")
    public String create(@QueryParam("idproject") Integer idproject, @QueryParam("description") String description) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            List index = q.getResultList();
            if(index.isEmpty()) {
                return "Project ID's not correct";
            } else {
                Query q2 = em.createQuery("select s from Status s where s.codeStatus = \"TODO\"");
                Object status = q2.getSingleResult();
                Task t = new Task(1, (Project) index.get(0), description, (Status) status);
                tx.begin();
                em.persist(t);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne toutes les taches
    @GET
    @Path("getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Task> getAll() {
        Query q = em.createQuery("select t from Task t");
        return q.getResultList();
    }
    
    // Retourne les informations d'une tache
    @GET
    @Path("{id}/get")
    @Produces({MediaType.APPLICATION_JSON})
    public Task getTask(@PathParam("id") Integer id) {
        Query q = em.createQuery("select t from Task t where t.idtask=:idtask");
        q.setParameter("idtask", id);
        return (Task) q.getSingleResult();
    }
    
    // Supprime une tache
    @GET
    @Path("{id}/delete")
    public String delete(@PathParam("id") Integer id) {
        try {
            tx.begin();
            Query q = em.createQuery("delete from Task t where t.idtask=:idparam");
            q.setParameter("idparam", id);
            q.executeUpdate();
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Change la description d'une tache
    @GET
    @Path("{id}/update")
    public String updateDesc(@PathParam("id") Integer id, @QueryParam("description") String description) {
        try {
            tx.begin();
            Query q = em.createQuery("select t from Task t where t.idtask=:idparam");
            q.setParameter("idparam", id);
            Task t = (Task) q.getSingleResult();
            t.setDescription(description);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Change le status d'une tache
    @GET
    @Path("{id}/updateStatus")
    public String updateStatus(@PathParam("id") Integer id, @QueryParam("status") String status) {
        try {
            tx.begin();
            // Préparation de la requete pour le changement de status
            Query statusUpdate = em.createQuery("select s from Status s where s.codeStatus=:status");
            String s;
            // On check la valeur passer par l'URL
            switch(status) {
                case "TODO":
                    s = "TODO";
                    break;
                case "IN PROGRESS":
                    s = "IN PROGRESS";
                    break;
                case "DONE":
                    s = "DONE";
                    break;
                case "VALIDATED":
                    s = "VALIDATED";
                    break;
                default:
                    return "Mauvais status";
            }
            statusUpdate.setParameter("status", s);
            Status newStatus = (Status) statusUpdate.getSingleResult();
            Query q = em.createQuery("select t from Task t where t.idtask=:idparam");
            q.setParameter("idparam", id);
            Task task = (Task) q.getSingleResult();
            task.setStatus(newStatus);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
