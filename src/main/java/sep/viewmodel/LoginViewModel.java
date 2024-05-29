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

/**
 * The LoginViewModel class provides the view model for handling user login functionality.
 * It interacts with the model to perform login operations and manages the login view state.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class LoginViewModel
{
    private final Model model;
    final StringProperty username;
    final StringProperty password;
    final StringProperty error;

    /**
     * Constructs a LoginViewModel with the specified model.
     *
     * @param model The model to interact with for login operations
     */
    public LoginViewModel(Model model)
    {
        this.model = model;
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.error = new SimpleStringProperty("");
    }

    /**
     * Attempts to log in the user using the provided username and password.
     *
     * @return The logged-in patron if successful, null otherwise
     */
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

    /**
     * Binds the provided property to the username property in the view model.
     *
     * @param property The property to bind to the username
     */
    public void bindUsername(StringProperty property){
        this.username.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the password property in the view model.
     *
     * @param property The property to bind to the password
     */
    public void bindPassword(StringProperty property){
        this.password.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the error property in the view model.
     *
     * @param property The property to bind to the error
     */
    public void bindError(StringProperty property){this.error.bindBidirectional(property);}

    /**
     * Resets the username and password properties to empty strings.
     */
    public void reset()
    {
        this.username.set("");
        this.password.set("");
    }
}
