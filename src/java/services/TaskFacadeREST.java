/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Project;
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
                Task t = new Task(1, idproject, description);
                tx.begin();
                em.persist(t);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
