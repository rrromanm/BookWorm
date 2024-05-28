package sep.viewmodel;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import sep.model.Model;

import java.rmi.RemoteException;

/**
 * The CreateAccountViewModel class provides the view model for creating a new patron account in the system.
 * It interacts with the model to create a new patron account.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class CreateAccountViewModel {
    private final Model model;
    private final StringProperty email;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final StringProperty phone_number;
    private final StringProperty repeatPassword;

    /**
     * Constructs a CreateAccountViewModel with the specified model.
     *
     * @param model The model to interact with for creating user accounts
     */
    public CreateAccountViewModel(Model model) {
        this.model = model;
        this.email = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.repeatPassword = new SimpleStringProperty("");
        this.first_name = new SimpleStringProperty("");
        this.last_name = new SimpleStringProperty("");
        this.phone_number = new SimpleStringProperty("");
    }

    /**
     * Creates a new patron account with the provided details.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void createPatron() throws RemoteException {
        try{

            if(!password.get().equals(repeatPassword.get())){
                throw new IllegalArgumentException("Passwords must match!");
            }
            model.createPatron(username.get(), password.get(), first_name.get(), last_name.get(), email.get(), phone_number.get(),0);
            reset();
            System.out.println("Patron created!");
        }catch(Exception e){
            throw new RemoteException(e.getMessage());
        }
    }
    /**
     * Binds the provided property to the email property in the view model.
     *
     * @param property The property to bind to the email
     */
    public void bindEmail(StringProperty property){
        this.email.bindBidirectional(property);
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
     * Binds the provided property to the repeatPassword property in the view model.
     *
     * @param property The property to bind to the repeatPassword
     */
    public void bindRepeatPassword(StringProperty property){
        this.repeatPassword.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the first_name property in the view model.
     *
     * @param property The property to bind to the first_name
     */
    public void bindFirstName(StringProperty property){
        this.first_name.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the last_name property in the view model.
     *
     * @param property The property to bind to the last_name
     */
    public void bindLastName(StringProperty property){
        this.last_name.bindBidirectional(property);
    }

    /**
     * Binds the provided property to the phone_number property in the view model.
     *
     * @param property The property to bind to the phone_number
     */
    public void bindPhoneNumber(StringProperty property){
        this.phone_number.bindBidirectional(property);
    }

    /**
     * Resets the fields to empty strings.
     */
    public void reset()
    {
        this.email.set("");
        this.username.set("");
        this.password.set("");
        this.phone_number.set("");
        this.first_name.set("");
        this.last_name.set("");
    }

    /**
     * Displays an alert with the given message.
     *
     * @param message The message to display in the alert
     */
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
