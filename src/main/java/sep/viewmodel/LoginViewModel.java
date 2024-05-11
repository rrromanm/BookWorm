package sep.viewmodel;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.model.Model;
import sep.model.Patron;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public class LoginViewModel
{
    private final Model model;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty error;


    public LoginViewModel(Model model)
    {
        this.model = model;
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.error = new SimpleStringProperty("");
    }

    public Patron login(){
        try {
            Patron patron = model.login(username.get(),password.get());
            if(patron!=null){
                reset();
                this.error.set("");
                return patron;
            }

        } catch (RemoteException e) {
            this.error.set(e.getMessage());
            e.printStackTrace();
        }
        this.error.set("Username or password is incorrect. Try again");
        return null;
    }
    public void bindUsername(StringProperty property){
        this.username.bindBidirectional(property);
    }
    public void bindPassword(StringProperty property){
        this.password.bindBidirectional(property);
    }
    public void bindError(StringProperty property){this.error.bindBidirectional(property);}
    public void reset()
    {
        this.username.set("");
        this.password.set("");
    }
}
