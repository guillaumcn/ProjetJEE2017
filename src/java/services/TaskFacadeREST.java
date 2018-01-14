/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Sprint;
import entities.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    // pour tester ca, il faut d'abord créer un sprint pour pouvoir avoir son id
    @PUT
    @Path("create")
    public String create(@QueryParam("idsprint") Integer idsprint, @QueryParam("code_status") String code_status) {
        if(!code_status.equals("TO DO") && !code_status.equals("IN PROGRESS") && !code_status.equals("DONE") && !code_status.equals("VALIDATED")) {
            return "Code_status invalide";
        } else {
            try {
                Query q = em.createQuery("select s from Sprint s where s.idsprint=:idsprint");
                q.setParameter("idsprint", idsprint);
                Sprint s = (Sprint) q.getSingleResult();
                if(s == null) {
                    return "Sprint ID's not correct";
                } else {
                    tx.begin();
                    Task t = null;
                    switch(code_status){
                        case "TO DO":
                            t = new Task(s, 1);
                            break;
                        case "IN PROGRESS":
                            t = new Task(s, 2);
                            break;
                        case "DONE":
                            t = new Task(s, 3);
                            break;
                        case "VALIDATED":
                            t = new Task(s, 4);
                            break;
                        default:
                            return "Mauvais status";
                    }
                    em.persist(t);
                    tx.commit();
                    return "OK";
                }

            } catch(Exception e) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }
    
    // Retourne toutes les taches
    @GET
    @Path("getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Task> getAll() {
        try {
            Query q = em.createQuery("select t from Task t");
            return q.getResultList(); 
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
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
    @DELETE
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
    @POST
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
    
    // A FAIRE - adapter avec la nouvelle bd
    // Change le status d'une tache
    
    /**
     * 
     * @param id
     * @param status
     * @return update le statut d'une task
     */
    @POST
    @Path("{id}/updateTaskStatus")
    public String updateTaskStatus(@PathParam("id") Integer id, @QueryParam("status") String status) {
        try {
            tx.begin();
            // Préparation de la requete pour le changement de status
            int s;
            // On check la valeur passer par l'URL
            switch(status) {
                case "TODO":
                    s = 1;
                    break;
                case "IN PROGRESS":
                    s = 2;
                    break;
                case "DONE":
                    s = 3;
                    break;
                case "VALIDATED":
                    s = 4;
                    break;
                default:
                    return "Mauvais status";
            }
 
            Query q = em.createQuery("select t from Task t where t.idtask=:idparam");
            q.setParameter("idparam", id);
            Task task = (Task) q.getSingleResult();
            task.setStatus(s);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    } 
}