/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Task;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.TaskFacade;

/**
 *
 * @author amles_000
 */
@Named(value = "taskManaged")
@Dependent
public class TaskManaged {
    
private List<Task> tasks;
    /**
     * Creates a new instance of Contact
     */
    public TaskManaged() {
    }
    
    @EJB
    private TaskFacade tasksession;
    
    public List<Task> getTasks() {  
        tasks = (List<Task>) tasksession.findAll();
        return tasks;  
    }  
    
    
}
