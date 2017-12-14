package entity;

import entity.ProjectClient;
import entity.ProjectManager;
import entity.Release;
import entity.Task;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-12T16:47:17")
@StaticMetamodel(Project.class)
public class Project_ { 

    public static volatile CollectionAttribute<Project, Release> releaseCollection;
    public static volatile SingularAttribute<Project, String> code;
    public static volatile CollectionAttribute<Project, Task> taskCollection;
    public static volatile CollectionAttribute<Project, ProjectClient> projectClientCollection;
    public static volatile SingularAttribute<Project, Integer> idproject;
    public static volatile SingularAttribute<Project, String> description;
    public static volatile CollectionAttribute<Project, ProjectManager> projectManagerCollection;
    public static volatile SingularAttribute<Project, Date> startdate;

}