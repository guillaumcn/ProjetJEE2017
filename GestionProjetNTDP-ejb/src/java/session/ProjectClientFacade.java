/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.ProjectClient;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author amles_000
 */
@Stateless
public class ProjectClientFacade extends AbstractFacade<ProjectClient> {

    @PersistenceContext(unitName = "GestionProjetNTDP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProjectClientFacade() {
        super(ProjectClient.class);
    }
    
}
