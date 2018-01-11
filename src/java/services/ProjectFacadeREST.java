/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

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
import entities.Contact;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nicolas
 */
@Stateless
@Path("project")
public class ProjectFacadeREST {

    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    // Creation d'un projet
    @PUT
    @Path("create")
    public Integer create(@QueryParam("nom") String nom) {
        // http://localhost:8080/ProjetJEE/webresources/project/create?nom=testProject
        try {
            Project p = new Project(1, nom);
            tx.begin();
            em.persist(p);
            tx.commit();
            return p.getIdproject();
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Creation d'un projet avec description
    @PUT
    @Path("createWithDesc")
    public Integer create(@QueryParam("code") String code, @QueryParam("description") String desc) {
        try {
            Project p = new Project(1, code, desc);
            tx.begin();
            em.persist(p);
            tx.commit();
            return p.getIdproject();
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("createWithDescStartDate")
    public Integer create(@QueryParam("code") String code, @QueryParam("description") String desc, @QueryParam("startDate") String start) {
        try {
            Project p = new Project(1, code, desc, start);
            tx.begin();
            em.persist(p);
            tx.commit();
            return p.getIdproject();
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Supprime un projet et retourne son id
    @DELETE
    @Path("{id}/delete")
    public String delete(@PathParam("id") Integer id) {
        try {
            tx.begin();
            Query q = em.createQuery("delete from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            q.executeUpdate();
            tx.commit();
            return "OK";
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne les informations d'un projet
    @GET
    @Path("{id}/get")
    @Produces({MediaType.APPLICATION_JSON})
    public Project findProject(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            Project p = (Project) q.getSingleResult();
            return p;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Met a jour le projet
    // A modifier
    @POST
    @Path("{id}/update") 
    public Project updateProject(@PathParam("id") Integer id, @QueryParam("code") String code) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            Project p = (Project) q.getSingleResult();
            return p;
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne tous les projets
    @GET
    @Path("getAll")
    @Produces({MediaType.APPLICATION_JSON})
    public List<Project> getAll() {
        try {
            Query q = em.createQuery("select p from Project p");
            return q.getResultList();
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT 
    @Path("{id}/addContact")
    // retourne 1 si OK sinon 0
    public String addContact(@PathParam("id") Integer id, @QueryParam("idContact") Integer idContact) {
        // on part du principe qu'il ne peut être QUE admin, client ou team
        try {
            tx.begin();
            // récupération du projet en param
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            Query q2 = em.createQuery("select c from Contact c where c.idcontact=:idparam2");
            q.setParameter("idparam", id);
            q2.setParameter("idparam2", idContact);
            Project p = (Project) q.getSingleResult();
            List<Contact> c = q2.getResultList();
            if(c.isEmpty()) {
                return "ID Contact inconnu";
            } else {
                p.setContacts(idContact);
                em.persist(p);
                tx.commit();
                return "OK";
            }
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne la liste des contacts participant à un projet
    @GET
    @Path("{id}/getTeam")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> getTeam(@PathParam("id") Integer idproject) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            Project p = (Project) q.getSingleResult();
            String contacts = p.getContacts().substring(1);
            String contact_temp = contacts.substring(0, contacts.length() - 1);
            String[] list_contact = contact_temp.split(",");
            List<Contact> res = new ArrayList<Contact>();
            for(int i = 0; i < list_contact.length; i++) {
                int id = Integer.parseInt(list_contact[i]);
                q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
                q.setParameter("idparam", id);
                Contact c = (Contact) q.getSingleResult();
                res.add(c);
            }
            return res;
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    } 
}