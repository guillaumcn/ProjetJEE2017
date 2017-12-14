/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Project;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.ProjectFacade;

/**
 *
 * @author amles_000
 */
@Named(value = "projectManaged")
@Dependent
public class ProjectManaged {
    
private List<Project> projects;
    /**
     * Creates a new instance of Contact
     */
    public ProjectManaged() {
    }
    
    @EJB
    private ProjectFacade projectsession;
    
    public List<Project> getProjects() {  
        projects = (List<Project>) projectsession.findAll();
        return projects;  
    }  
    
    
}
