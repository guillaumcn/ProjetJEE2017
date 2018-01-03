/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Contact;
import java.util.List;
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
 * @author Guillaume
 */
@Stateless
@Path("contact")
public class ContactFacadeREST {

    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();

    @GET
    @Path("create")
    public Integer create(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("status") String status) {
        // Status : Client, Team ou Admin
        // Si on met 1 l'id va s'autoincrement
        try {
            Contact c = new Contact(1, login, password, status);
            tx.begin();
            em.persist(c);
            tx.commit();
            return c.getIdcontact();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("{id}/get")
    // Le resultat est produit en JSON
    @Produces({MediaType.APPLICATION_JSON})
    public Contact findContact(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            Contact c = (Contact) q.getSingleResult();
            return c;
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("{id}/update")
    public Integer updateContact(@PathParam("id") Integer id, @QueryParam("login") String login, @QueryParam("password") String password) {
        try {
            tx.begin();
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            Contact c = (Contact) q.getSingleResult();
            c.setLogin(login);
            c.setPassword(password);
            tx.commit();
            return c.getIdcontact();
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("{id}/delete")
    public String deleteContact(@PathParam("id") Integer id) {
        try {
            tx.begin();
            Query q = em.createQuery("delete from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            q.executeUpdate();
            tx.commit();
            return "OK";
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("getAll")
    // Le resultat est produit en JSON
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contact> findAll() {
        Query q = em.createQuery("select c from Contact c");
        return q.getResultList();
    }
    
}
