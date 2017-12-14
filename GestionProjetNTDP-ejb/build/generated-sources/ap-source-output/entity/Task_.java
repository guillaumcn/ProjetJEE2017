package entity;

import entity.Project;
import entity.Release;
import entity.Status;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T15:38:20")
@StaticMetamodel(Task.class)
public class Task_ { 

    public static volatile CollectionAttribute<Task, Release> releaseCollection;
    public static volatile SingularAttribute<Task, Integer> idtask;
    public static volatile SingularAttribute<Task, String> description;
    public static volatile SingularAttribute<Task, Project> idproject;
    public static volatile SingularAttribute<Task, Status> status;

}