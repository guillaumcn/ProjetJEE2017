package entity;

import entity.Contact;
import entity.Project;
import entity.ProjectClientPK;
import entity.Release;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-12T16:47:17")
@StaticMetamodel(ProjectClient.class)
public class ProjectClient_ { 

    public static volatile SingularAttribute<ProjectClient, Release> idrelease;
    public static volatile SingularAttribute<ProjectClient, Contact> contact;
    public static volatile SingularAttribute<ProjectClient, Project> project;
    public static volatile SingularAttribute<ProjectClient, ProjectClientPK> projectClientPK;

}