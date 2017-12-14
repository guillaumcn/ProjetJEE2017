package entity;

import entity.Contact;
import entity.Project;
import entity.ProjectManagerPK;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-12T16:47:17")
@StaticMetamodel(ProjectManager.class)
public class ProjectManager_ { 

    public static volatile SingularAttribute<ProjectManager, Contact> contact;
    public static volatile SingularAttribute<ProjectManager, ProjectManagerPK> projectManagerPK;
    public static volatile SingularAttribute<ProjectManager, Project> project;
    public static volatile SingularAttribute<ProjectManager, Date> startdate;

}