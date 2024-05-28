package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.model.Model;

import java.rmi.RemoteException;

/**
 * The AdminLoginViewModel class provides the view model for the admin login view.
 * It handles the logic for admin login, including binding properties to the view,
 * performing the login action, and resetting the view model state.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */

public class AdminLoginViewModel {
    private final Model model;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty error;

    /**
     * Constructs an AdminLoginViewModel with the specified model.
     *
     * @param model The model to interact with for login operations
     */

    public AdminLoginViewModel(Model model) {
        this.model = model;
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.error = new SimpleStringProperty("");
    }

    /**
     * Attempts to log in as an admin using the provided username and password.
     * If the login is successful, the view model calls reset. Otherwise, an error message is set.
     *
     * @return true if the login is successful, false otherwise
     */

    public boolean login(){
        try{
            if(model.loginAsAdmin(username.get(), password.get())){
                reset();
                return true;
            }
        } catch (RemoteException e){
            error.set(e.getMessage());
            e.printStackTrace();
        }
        error.set("Username or password incorrect. Try again.");
        return false;
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
     * @param property The property to bind to the error message
     */
    public void bindError(StringProperty property){
        this.error.bindBidirectional(property);
    }

    /**
     * Resets the view model to its initial state by clearing the username and password properties.
     */
    public void reset()
    {
        this.username.set("");
        this.password.set("");
    }
}
