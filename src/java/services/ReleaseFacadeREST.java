/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.ReleaseProject;
import entities.Project;
import java.util.Date;
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
    @PUT
    @Path("create")
    // http://localhost:8080/ProjetJEE/webresources/release/create?idproject=1&daterelease=2018/01/03
    public String create(@QueryParam("idproject") Integer idproject, @QueryParam("daterelease") Date daterelease) {
        System.out.println("idproject entré : " + idproject + " - daterelease : " + daterelease);
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            Project p = (Project) q.getSingleResult();
            System.out.println("Projet trouvé : " + p);
            if(p == null) {
                System.out.println("p : " + p);
                return "Id Project non valide";
            } else {
                ReleaseProject release = new ReleaseProject(p, daterelease);
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
    
    @GET
    @Path("{id}/get")
    // @Produces({MediaType.APPLICATION_JSON})
    // http://localhost:8080/ProjetJEE/webresources/release/1/get
    public ReleaseProject getRelease(@PathParam("id") Integer id) {
        System.out.println("id oui : " + id);
        try{
            Query q = em.createQuery("select r from ReleaseProject r where r.idrelease=:idr");
            q.setParameter("idr", id);
            System.out.println("release trouvée " + q);
            return (ReleaseProject) q.getSingleResult();
        } catch(Exception e) {
            System.out.println("caca");
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
   }
    
    

