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
import entities.ProjectManager;
import entities.ProjectClient;
import entities.ProjectTeam;
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
    @GET
    @Path("create")
    public Integer create(@QueryParam("code") String code) {
        try {
            Project p = new Project(1, code);
            tx.begin();
            em.persist(p);
            tx.commit();
            return p.getIdproject();
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Creation d'un projet avec description
    @GET
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
    
    @GET
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
    @GET
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
    @GET
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
    
    @GET 
    @Path("{id}/addContact")
    @Produces({MediaType.APPLICATION_JSON})
    // retourne 1 si OK sinon 0
    public Integer addContact(@PathParam("id") Integer id, @QueryParam("idContact") Integer idContact){
        // on part du principe qu'il ne peut être QUE admin, client ou team
        try {
            // récupération du projet en param
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            Project p = (Project) q.getSingleResult();
            
            // récupération du type de contact depuis son id en param
            Query q2 = em.createQuery("select c from Contact c where c.idcontact=:idContact");
            q2.setParameter("idContact", idContact);
            Contact res = (Contact) q2.getSingleResult();
            
            // création d'un objet en fonction du type de contact récupéré au dessus
            
            // création d'un objet project_manager
            if(res.getIsAdmin()){
                ProjectManager pm = new ProjectManager(p.getIdproject(), res.getIdcontact());
                return 1;
            }
            
            // création d'un objet project_client
            else if(res.getIsClient()){
                ProjectClient pc = new ProjectClient(p.getIdproject(), res.getIdcontact());
                return 1;
            }
            
            // création d'un objet project_team
            else if(res.getIsTeam()){
                ProjectTeam pt = new ProjectTeam(1, p, res);
                return 1;
            }
            
            // si erreur, on renvoie 0
            else {
                return 0;
            }
            
        } catch (Exception e) {
            System.out.println("id project : " + id);
            System.out.println("id contact : " + idContact);
            // url testé : http://localhost:8080/ProjetJEE/webresources/project/1/addContact?idContact=1
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