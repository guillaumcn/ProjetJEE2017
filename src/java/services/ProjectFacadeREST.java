/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Project;
import entities.ReleaseProject;
import entities.Sprint;
import entities.Task;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
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
    public String create(@QueryParam("nom") String nom) {
        // http://localhost:8080/ProjetJEE/webresources/project/create?nom=testProject
        try {
            Project p = new Project(1, nom);
            tx.begin();
            em.persist(p);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            // e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Creation d'un projet avec description
    @PUT
    @Path("createWithDesc")
    public String create(@QueryParam("code") String code, @QueryParam("description") String desc) {
        try {
            Project p = new Project(1, code, desc);
            tx.begin();
            em.persist(p);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT
    @Path("createWithDescStartDate")
    public String create(@QueryParam("code") String code, @QueryParam("description") String desc, @QueryParam("startDate") String start) {
        try {
            Project p = new Project(1, code, desc, start);
            tx.begin();
            em.persist(p);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Supprime un projet et retourne son id
    @DELETE
    @Path("{id}/delete")
    public String delete(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
                tx.begin();
                q = em.createQuery("delete from Project p where p.idproject=:idparam");
                q.setParameter("idparam", id);
                q.executeUpdate();
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne les informations d'un projet
    @GET
    @Path("{id}/get")
    // @Produces({MediaType.APPLICATION_JSON})
    public Project findProject(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                throw new Exception("Le project n'existe pas / ID inconnu");
            } else {
                Project p = (Project) q.getSingleResult();
                return p;
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Met a jour le projet
    // A modifier
    @POST
    @Path("{id}/updateName") 
    public String updateProjectName(@PathParam("id") Integer id, @QueryParam("name") String name) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
                Project p = (Project) q.getSingleResult();
                tx.begin();
                p.setNom(name);
                em.persist(p);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Met a jour la date de debut de projet
    @POST
    @Path("{id}/updateStartDate")
    public String updateStartDate(@PathParam("id") Integer id, @QueryParam("startdate") String date) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
               Project p = (Project) q.getSingleResult();
                if(date.matches("^2[0-9]{3,}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])")) {
                    tx.begin();
                    p.setStartDate(new Date(date));
                    em.persist(p);
                    tx.commit();
                    return "OK";
                } else {
                    return "Format date incorrect : AAAA/MM/JJ"; 
                } 
            }
        } catch(Exception e) {
            // e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Met a jour la date de fin de projet
    @POST
    @Path("{id}/updateEndDate")
    public String updateEndDate(@PathParam("id") Integer id, @QueryParam("enddate") String date) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
               Project p = (Project) q.getSingleResult();
                if(date.matches("^2[0-9]{3,}/(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])")) {
                    tx.begin();
                    p.setEndDate(new Date(date));
                    em.persist(p);
                    tx.commit();
                    return "OK";
                } else {
                    return "Format date incorrect : AAAA/MM/JJ"; 
                } 
            }
        } catch(Exception e) {
            // e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Modifie la description
    @POST
    @Path("{id}/updateDescription")
    public String updateDesc(@PathParam("id") Integer idproject, @QueryParam("desc") String desc) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
                Project p = (Project) q.getSingleResult();
                tx.begin();
                p.setDescription(desc);
                em.persist(p);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Retourne tous les projets
    @GET
    @Path("getAll")
    // @Produces({MediaType.APPLICATION_JSON})
    public List<Project> findAll() {
        try {
            Query q = em.createQuery("select p from Project p");
            return q.getResultList();
        } catch(Exception e) {
            // e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @PUT 
    @Path("{id}/addContact")
    // retourne 1 si OK sinon 0
    public String addContact(@PathParam("id") Integer id, @QueryParam("idContact") Integer idContact) {
        // on part du principe qu'il ne peut être QUE admin, client ou team
        try {
            // récupération du projet en param
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le project n'existe pas / ID inconnu";
            } else {
                Project p = (Project) q.getSingleResult();
                q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
                q.setParameter("idparam", idContact);
                List<Contact> c = q.getResultList();
                if(c.isEmpty()) {
                    return "ID Contact inconnu";
                } else {
                    if(!p.checkContact(idContact)) {
                        tx.begin();
                        p.setContacts(idContact);
                        em.persist(p);
                        tx.commit();
                        return "OK";
                    } else {
                        return "OK";
                    }
                }
            }
            
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Supprime un utilisateur du projet
    @DELETE
    @Path("{id}/removeContact")
    public String removeContact(@PathParam("id") Integer idproject, @QueryParam("idcontact") Integer idcontact) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:id");
            q.setParameter("id", idproject);
            if(q.getResultList().isEmpty()) {
                return "Le projet n'existe pas / ID inconnu";
            } else {
                Project p = (Project) q.getSingleResult();
                q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
                q.setParameter("idparam", idcontact);
                if(q.getResultList().isEmpty()) {
                    return "ID Contact inconnu";
                } else {
                    if(!p.checkContact(idcontact)) {
                        return "Le contact ne fait pas partie du projet";
                    } else {
                        tx.begin();
                        p.removeContact(idcontact);
                        em.persist(p);
                        tx.commit();
                        return "OK";
                    }
                }
            }
        } catch(Exception e) {
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
            if(q.getResultList().isEmpty()) {
                throw new Exception("Le projet n'existe pas / ID inconnu");
            } else {
                Project p = (Project) q.getSingleResult();
                String contacts = p.getContacts().substring(1);
                String contact_temp = contacts.substring(0, contacts.length() - 1);
                String[] list_contact = contact_temp.split(",");
                List<Contact> res = new ArrayList<>();
                for(int i = 0; i < list_contact.length; i++) {
                    int id = Integer.parseInt(list_contact[i]);
                    q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
                    q.setParameter("idparam", id);
                    Contact c = (Contact) q.getSingleResult();
                    res.add(c);
                }
                return res;
            }
            
        } catch(Exception e) {
            // e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Recuperation des release associes au projet
    @GET
    @Path("{id}/getRelease")
    public List<ReleaseProject> getRelease(@PathParam("id") Integer idproject) {
        try  {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            if(q.getResultList().isEmpty()) {
                throw new Exception("Le projet n'existe pas / ID inconnu");
            } else {
                Project p = (Project) q.getSingleResult();
                q = em.createQuery("select r from ReleaseProject r where r.idproject=:idparam");
                q.setParameter("idparam", p);
                return q.getResultList();
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Recupere les sprint associes au projet
    @GET
    @Path("{id}/getSprint")
    public List<Sprint> getSprint(@PathParam("id") Integer idproject) {
        try {
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            if(q.getResultList().isEmpty()) {
                throw new Exception("Le projet n'existe pas / ID inconnu");
            } else {
                Project p = (Project) q.getSingleResult();
                q = em.createQuery("select r from ReleaseProject r where r.idproject=:idparam");
                q.setParameter("idparam", p);
                List<ReleaseProject> r = q.getResultList();
                List<Sprint> s = new ArrayList();
                for(ReleaseProject rp : r) {
                    q = em.createQuery("select s from Sprint s where s.idrelease=:idparam");
                    q.setParameter("idparam", rp);
                    s.addAll(q.getResultList());
                }
                return s;
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    // Recuperation des taches
    @GET
    @Path("{id}/getTasksWithStatus")
    public List<Task> getTasks(@PathParam("id") Integer idproject, @QueryParam("status") String status) {
        try {
            int stat;
            switch(status) {
                case "TO DO":
                    stat = 1;
                    break;
                case "IN PROGRESS":
                    stat = 2;
                    break;
                case "DONE":
                    stat = 3;
                    break;
                case "VALIDATED":
                    stat = 4;
                    break;
                case "ALL":
                    stat = 5;
                    break;
                default:
                    throw new Exception("Mauvais status");
            }
            Query q = em.createQuery("select p from Project p where p.idproject=:idparam");
            q.setParameter("idparam", idproject);
            if(q.getResultList().isEmpty()) {
                throw new Exception("Le projet n'existe pas / ID inconnu");
            } else {
                Project p = (Project) q.getSingleResult();
                q = em.createQuery("select r from ReleaseProject r where r.idproject=:idparam");
                q.setParameter("idparam", p);
                List<ReleaseProject> rp = q.getResultList();
                List<Task> tasks = new ArrayList();
                for(ReleaseProject temp : rp) {
                    q = em.createQuery("select s from Sprint s where s.idrelease=:idparam");
                    q.setParameter("idparam", temp);
                    List<Sprint> s = q.getResultList();
                    for(Sprint temps : s) {
                        if(stat == 5) {
                           q = em.createQuery("select t from Task t where t.idsprint=:sprintparam");
                           q.setParameter("sprintparam", temps);
                           tasks.addAll(q.getResultList());
                        } else {
                           q = em.createQuery("select t from Task t where t.idsprint=:sprint and t.status=:status");
                           q.setParameter("sprint", temps);
                           q.setParameter("status", stat);
                           tasks.addAll(q.getResultList());
                        }
                    }
                }
                return tasks;
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
}