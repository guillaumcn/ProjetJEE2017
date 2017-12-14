package entity;

import entity.Project;
import entity.ProjectClient;
import entity.Task;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T15:38:20")
@StaticMetamodel(Release.class)
public class Release_ { 

    public static volatile CollectionAttribute<Release, Task> taskCollection;
    public static volatile SingularAttribute<Release, Integer> idrelease;
    public static volatile CollectionAttribute<Release, ProjectClient> projectClientCollection;
    public static volatile SingularAttribute<Release, Project> idproject;
    public static volatile SingularAttribute<Release, Date> daterelease;
    public static volatile SingularAttribute<Release, String> version;

}