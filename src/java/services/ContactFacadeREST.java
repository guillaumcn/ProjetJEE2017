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
 * @author Guillaume
 */
@Stateless
@Path("contact")
public class ContactFacadeREST {

    private EntityManager em = Persistence.createEntityManagerFactory("ProjetJEEPU").createEntityManager();
    EntityTransaction tx = em.getTransaction();
    
    // Creation d'un contact
    @PUT
    @Path("create")
    public String create(@QueryParam("login") String login, @QueryParam("password") String password) {
        try {
            Contact c = new Contact(1, login, password, Boolean.FALSE);
            tx.begin();
            em.persist(c);
            tx.commit();
            return "OK";
        } catch(Exception e) {
            return "Login déjà utilisé";
        }
    }

    @PUT
    @Path("createWithStatus")
    // exemple de creation (postman en put) : http://localhost:8080/ProjetJEE/webresources/contact/create?login=test&password=test&status=client
    public String createWithStatus(@QueryParam("login") String login, @QueryParam("password") String password, @QueryParam("status") String status) {
        // Status : Client ou Admin
        // Si on met 1 l'id va s'autoincrement
        try {
            if(status.equals("admin")) {
                Contact c = new Contact(1, login, password, Boolean.TRUE);
                tx.begin();
                em.persist(c);
                tx.commit();
                return "OK";
            } else if(status.equals("client")) {
                Contact c = new Contact(1, login, password, Boolean.FALSE);
                tx.begin();
                em.persist(c);
                tx.commit();
                return "OK";
            } else {
                return "Mauvais status 'admin'/'client'";
            }
        } catch (Exception e) {
            return "Login déjà utilisé";
        }
    }
    
    @GET
    @Path("{id}/get")
    // Le resultat est produit en JSON
    // exemple d'url (postman en put) :  http://localhost:8080/ProjetJEE/webresources/contact/1/get
    // @Produces({MediaType.APPLICATION_JSON})
    public Contact findContact(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            Contact c = (Contact) q.getSingleResult();
            return c;
        } catch (Exception e) {
            return new Contact();
        }
    }
    
    @POST
    @Path("{id}/update")
    // http://localhost:8080/ProjetJEE/webresources/contact/1/update?login=toto&password=tata
    public String updateContact(@PathParam("id") Integer id, @QueryParam("login") String login, @QueryParam("password") String password) {
        try {
            tx.begin();
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le contact n'existe pas / ID inconnu";
            } else {
                Contact c = (Contact) q.getSingleResult();
                c.setLogin(login);
                c.setPassword(password);
                tx.commit();
                return "OK";
            }
        } catch (Exception e) {
            return "Login déjà utilisé";
        }
    }
    
    // Modification du login
    @POST
    @Path("{id}/updateLogin")
    public String updateLogin(@PathParam("id") Integer idcontact, @QueryParam("login") String login) {
        try {
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", idcontact);
            if(q.getResultList().isEmpty()) {
                return "Le contact n'existe pas / ID inconnu";
            } else {
                Contact c = (Contact) q.getSingleResult();
                tx.begin();
                c.setLogin(login);
                em.persist(c);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            return "Login déjà utilisé";
        }
    } 
    
    // Modification du password
    @POST
    @Path("{id}/updatePassword")
    public String updatePassword(@PathParam("id") Integer idcontact, @QueryParam("password") String pass) {
        try  {
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", idcontact);
            if(q.getResultList().isEmpty()) {
                return "Le contact n'existe pas / ID inconnu";
            } else {
                Contact c = (Contact) q.getSingleResult();
                tx.begin();
                c.setPassword(pass);
                em.persist(c);
                tx.commit();
                return "OK";
            }
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @DELETE
    @Path("{id}/delete")
    // http://localhost:8080/ProjetJEE/webresources/contact/1/delete
    public String deleteContact(@PathParam("id") Integer id) {
        try {
            Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
            q.setParameter("idparam", id);
            if(q.getResultList().isEmpty()) {
                return "Le contact n'existe pas / ID inconnu";
            } else {
               tx.begin();
                q = em.createQuery("delete from Contact c where c.idcontact=:idparam");
                q.setParameter("idparam", id);
                q.executeUpdate();
                tx.commit();
                return "OK"; 
            }
        } catch (Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
    }
    
    @GET
    @Path("getAll")
    // http://localhost:8080/ProjetJEE/webresources/contact/getAll
    // Le resultat est produit en JSON
    // @Produces({MediaType.APPLICATION_JSON})
    public List<Contact> findAll() {
        try {
            Query q = em.createQuery("select c from Contact c");
            return q.getResultList(); 
        } catch(Exception e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        
    }
    
    // Modification du status du contact
    @POST
    @Path("{id}/updateState")
    // http://localhost:8080/ProjetJEE/webresources/contact/1/updateState?state=admin
    // TODO : renvoie "Unexpected 'E'"
    public String updateState(@PathParam("id") Integer idcontact, @QueryParam("state") String newstate)  {
        Query q = em.createQuery("select c from Contact c where c.idcontact=:idparam");
        q.setParameter("idparam", idcontact);
        if(q.getResultList().isEmpty()) {
            return "Le contact n'existe pas / ID inconnu";
        } else {
            Contact client = (Contact) q.getSingleResult();
            try {
                tx.begin();
                // On verifie quel etat doit changer
                if(newstate.equals("admin")) {
                    if(!client.getAdmin()) {
                        client.setAdmin(Boolean.TRUE);
                        em.persist(client);
                        tx.commit();
                        return "OK";
                    } else {
                        return "deja admin";
                    }
                } else if (newstate.equals("client")) {
                    if(client.getAdmin()) {
                        client.setAdmin(Boolean.FALSE);
                        em.persist(client);
                        tx.commit();
                        return "OK";
                    } else {
                        return "deja client / team";
                    } 
                } else {
                    return "Erreur dans la requete";
                }    

            } catch(Exception e) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }
}
