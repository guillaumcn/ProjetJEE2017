package entity;

import entity.ProjectClient;
import entity.ProjectManager;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-12T16:47:17")
@StaticMetamodel(Contact.class)
public class Contact_ { 

    public static volatile SingularAttribute<Contact, String> password;
    public static volatile CollectionAttribute<Contact, ProjectClient> projectClientCollection;
    public static volatile SingularAttribute<Contact, Boolean> isClient;
    public static volatile SingularAttribute<Contact, Integer> idcontact;
    public static volatile SingularAttribute<Contact, Boolean> isAdmin;
    public static volatile CollectionAttribute<Contact, ProjectManager> projectManagerCollection;
    public static volatile SingularAttribute<Contact, String> login;
    public static volatile SingularAttribute<Contact, Boolean> isTeam;

}