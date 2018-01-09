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
    @Consumes(MediaType.APPLICATION_JSON)
    public Integer create(@QueryParam("code") String code) {
        try {
            Project p = new Project(1, code);
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
        Query q = em.createQuery("select p from Project p");
        return q.getResultList();
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
            q.setParameter("idparam", id);
            Project p = (Project) q.getSingleResult();
            p.setContacts(idContact);
            
            em.persist(p);
            tx.commit();
            return "OK";
        } catch (Exception e) {
            System.out.println("id project : " + id);
            System.out.println("id contact : " + idContact);
            // url testé : http://localhost:8080/ProjetJEE/webresources/project/1/addContact?idContact=1
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne la liste des contacts participant à un projet
    @GET
    @Path("{id}/getTeam")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> getTeam(@PathParam("id") Integer idproject) {
        Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
        q.setParameter("idparam", idproject);
        Project project = (Project) q.getSingleResult();
        try {
            // Initialisation de la liste de réponse
            List<Contact> res = null;
            // Recuperation des clients
            q = em.createQuery("select c from Contact c, ProjectClient pc where pc.contact.idcontact = c.idcontact and pc.project.idproject=:idparam");
            q.setParameter("idparam", project.getIdproject());
            // Contact c = (Contact) q.getSingleResult();
            List<Contact> temp = q.getResultList();
            res = temp;
            /* for(int i = 0; i < temp.size(); i++) {
                // System.out.println(temp.get(i).toString());
                res.add(temp.get(i));
            } */
            // Recuperation des managers
            q = em.createQuery("select c from Contact c, ProjectManager pm where pm.contact.idcontact = c.idcontact and pm.project.idproject=:idparam");
            q.setParameter("idparam", project.getIdproject());
            temp = q.getResultList();
            res.addAll(temp);
            /* for(int i = 0; i < temp.size() - 1; i++) {
                res.add(temp.get(i));
            } */
            
            // Recuperation des autres
            // q = em.createQuery("select c from Contact c inner join ProjectTeam as pt on c.idcontact = pt.idmember where pm.idproject=:idparam");
            q = em.createQuery("select c from Contact c, ProjectTeam pt where pt.idmember.idcontact = c.idcontact and pt.idproject.idproject=:idparam");
            q.setParameter("idparam", project.getIdproject());
            temp = q.getResultList();
            res.addAll(temp);
            /* for(int i = 0; i < temp.size() - 1; i++) {
                res.add(temp.get(i));
            } */
            return res;
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }

    
    /*public ProjectFacadeREST() {
        super(Project.class);
    }
    @POST
    @Override
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(Project entity) {
        super.create(entity);
    }
    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") Integer id, Project entity) {
        super.edit(entity);
    }
    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }
    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Project find(@PathParam("id") Integer id) {
        return super.find(id);
    }
    @GET
    @Override
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Project> findAll() {
        return super.findAll();
    }
    @GET
    @Path("{from}/{to}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Project> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }
    @GET
    @Path("count")
    @Produces(MediaType.TEXT_PLAIN)
    public String countREST() {
        return String.valueOf(super.count());
    } */
    
}