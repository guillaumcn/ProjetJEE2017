/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import entity.Contact;
import java.util.List;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import session.ContactFacade;

/**
 *
 * @author amles_000
 */
@Named(value = "contactManaged")
@Dependent
public class ContactManaged {
    
private List<Contact> contacts;
    /**
     * Creates a new instance of Contact
     */
    public ContactManaged() {
    }
    
    @EJB
    private ContactFacade contactsession;
    
    public List<Contact> getContacts() {  
        contacts = (List<Contact>) contactsession.findAll();
        return contacts;  
    }  
    
    
}
