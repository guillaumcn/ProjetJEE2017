package entity;

import entity.Task;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-12-14T15:38:20")
@StaticMetamodel(Status.class)
public class Status_ { 

    public static volatile CollectionAttribute<Status, Task> taskCollection;
    public static volatile SingularAttribute<Status, String> description;
    public static volatile SingularAttribute<Status, String> codeStatus;

}