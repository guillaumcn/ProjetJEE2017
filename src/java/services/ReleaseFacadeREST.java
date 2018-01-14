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
    public String create(@QueryParam("idproject") Integer idproject, @QueryParam("daterelease") String daterelease) {
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
                ReleaseProject release = new ReleaseProject(p, new Date(daterelease));
                tx.begin();
                em.persist(release);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("{id}/get")
    // @Produces({MediaType.APPLICATION_JSON})
    // http://localhost:8080/ProjetJEE/webresources/release/1/get
    public ReleaseProject getRelease(@PathParam("id") Integer id) {
        try{
            Query q = em.createQuery("select r from ReleaseProject r where r.idrelease=:idr");
            q.setParameter("idr", id);
            ReleaseProject rp = (ReleaseProject) q.getSingleResult();
            return rp;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    @GET
    @Path("getAll")
    // @Produces({MediaType.APPLICATION_JSON})
    // http://localhost:8080/ProjetJEE/webresources/release/getAll
    public List<ReleaseProject> getAll() {
        try {
            Query q = em.createQuery("select r from ReleaseProject r");
            return q.getResultList();
        } catch(Exception e) {
            return null;
        }
    }
    
    @POST
    @Path("{id}/update")
    // http://localhost:8080/ProjetJEE/webresources/release/1/update?date=2018/02/10
    public String updateRelease(@PathParam("id") Integer idRelease, @QueryParam("date") String newDate){
        System.out.println("updateRelease()");
        try {
            Query q = em.createQuery("select r from ReleaseProject r where r.idrelease=:id");
            q.setParameter("id", idRelease);
            ReleaseProject rp = (ReleaseProject) q.getSingleResult();
            if(rp != null){
                tx.begin();
                rp.setDaterelease(new Date(newDate));
                em.persist(rp);
                tx.commit();
                return "Release mise à jour";
            } else {
                return "La release correspondant à l'id : [" + idRelease + "] n'a pas pu être trouvée";
            }
        } catch (Exception e) {
            return "Erreur lors de l'update";
        }
    }
    
    @DELETE
    @Path("{id}/delete")
    public String delete(@PathParam("id") Integer idrelease) {
        try {
            Query q = em.createQuery("select r from ReleaseProject r where r.idrelease=:idparam");
            q.setParameter("idparam", idrelease);
            if(q.getResultList().isEmpty()) {
                return "La release n'existe pas / ID inconnu";
            } else {
                tx.begin();
                q = em.createQuery("delete from ReleaseProject r where r.idrelease=:idparam");
                q.setParameter("idparam",idrelease);
                q.executeUpdate();
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
}