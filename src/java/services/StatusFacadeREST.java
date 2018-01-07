/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Status;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fleur
 */
@Stateless
@Path("status")
public class StatusFacadeREST {
    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
   
    
    // Creation d'un status
    @GET
    @Path("create")
    public String create(@QueryParam("code") String code, @QueryParam("description") String description) {
        try {
            Status s = new Status(code, description);
            tx.begin();
            em.persist(s);
            tx.commit();
            return s.getCodeStatus();
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne les informations d'un status
    @GET
    @Path("{code}/get")
    // Le resultat est produit en JSON
    @Produces({MediaType.APPLICATION_JSON})
    public Status findStatus(@PathParam("code") String code) {
        try {
            Query q = em.createQuery("select s from Status s where s.code_status=:codeparam");
            q.setParameter("codeparam", code);
            Status s = (Status) q.getSingleResult();
            return s;
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Met a jour le stutus
    @GET
    @Path("{code}/update")
    public String updateStatus(@PathParam("code") String code, @QueryParam("description") String description) {
        try {
            tx.begin();
            Query q = em.createQuery("select s from Status s where s.code_status=:codeparam");
            q.setParameter("codeparam", code);
            Status s = (Status) q.getSingleResult();
            s.setDescription(description);
            tx.commit();
            return s.getCodeStatus();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Supprime un status et retourne son code
    @GET
    @Path("{code}/delete")
    public String delete(@PathParam("code") String code) {
        try {
            tx.begin();
            Query q = em.createQuery("delete from Status s where s.code_status=:codeparam");
            q.setParameter("codeparam", code);
            q.executeUpdate();
            tx.commit();
            return "OK";
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
