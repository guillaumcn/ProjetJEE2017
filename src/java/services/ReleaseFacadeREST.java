/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Release;
import entities.Project;
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
@Path("release")
public class ReleaseFacadeREST {
    
    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    // Creation d'une release
    @GET
    @Path("create")
    public String create(@QueryParam("idproject") Integer idproject, @QueryParam("daterelease") String daterelease) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            List<Project> p = q.getResultList();
            if(p.isEmpty()) {
                return "Id Project non valide";
            } else {
                Release release = new Release(1, (Project) p.get(0), daterelease, "1");
                tx.begin();
                em.persist(release);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
