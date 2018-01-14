/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.ReleaseProject;
import entities.Sprint;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 *
 * @author Nicolas
 */
@Stateless
@Path("sprint")
public class SprintFacadeREST {
    
    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    // Creation d'un sprint
    @PUT
    @Path("create")
    public String create(@QueryParam("description") String desc, @QueryParam("idrelease") Integer release) {
        try {
            Query q = em.createQuery("select r from ReleaseProject r where r.idrelease=:idparam");
            q.setParameter("idparam", release);
            if(q.getResultList().isEmpty()) {
                return "La release n'existe pas / ID inconnu";
            } else {
                ReleaseProject r = (ReleaseProject) q.getSingleResult();
                tx.begin();
                Sprint s = new Sprint(1, desc, r);
                em.persist(s);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Modification de la release
    @POST
    @Path("{id}/updateRelease")
    public String updateRelease(@PathParam("id") Integer idsprint, @QueryParam("idrelease") Integer idrelease) {
        try {
            Query q = em.createQuery("select s from Sprint s where s.idsprint=:idparam");
            q.setParameter("idparam", idsprint);
            if(q.getResultList().isEmpty()) {
                return "Le sprint n'existe pas / ID inconnu";
            } else {
                q = em.createQuery("select r from ReleaseProject r where r.idrelease=:idparam");
                q.setParameter("idparam", idrelease);
                if(q.getResultList().isEmpty()) {
                    return "La release n'existe pas / ID inconnu";
                } else {
                    tx.begin();
                    ReleaseProject r = (ReleaseProject) q.getSingleResult();
                    Sprint s = (Sprint) q.getSingleResult();
                    s.setIdrelease(r);
                    em.persist(s);
                    tx.commit();
                    return "OK";
                }
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Modification de la description
    @POST
    @Path("{id}/updateDescription")
    public String updateDesc(@PathParam("id") Integer idsprint, @QueryParam("description") String desc) {
        try {
            Query q = em.createQuery("select s from Sprint s where s.idsprint=:idparam");
            q.setParameter("idparam", idsprint);
            if(q.getResultList().isEmpty()) {
                return "Le sprint n'existe pas / ID inconnu";
            } else {
                tx.begin();
                Sprint s = (Sprint) q.getSingleResult();
                s.setDescription(desc);
                em.persist(s);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Suppression d'un sprint
    @DELETE
    @Path("{id}/delete")
    public String delete(@PathParam("id") Integer idsprint) {
        try {
            Query q = em.createQuery("select s from Sprint s where s.idsprint=:idparam");
            q.setParameter("idparam", idsprint);
            if(q.getResultList().isEmpty()) {
                return "Le sprint n'existe pas / ID inconnu";
            } else {
                tx.begin();
                q = em.createQuery("delete from Sprint s where s.idsprint=:idparam");
                q.executeUpdate();
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}
