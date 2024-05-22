package sep.viewmodel;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import sep.model.Model;

import java.rmi.RemoteException;

public class CreateAccountViewModel {
    private final Model model;
    private final StringProperty email;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final StringProperty phone_number;
    private final StringProperty repeatPassword;


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
    public void bindEmail(StringProperty property){
        this.email.bindBidirectional(property);
    }

    public void bindUsername(StringProperty property){
        this.username.bindBidirectional(property);
    }

    public void bindPassword(StringProperty property){
        this.password.bindBidirectional(property);
    }

    public void bindRepeatPassword(StringProperty property){
        this.repeatPassword.bindBidirectional(property);
    }

    public void bindFirstName(StringProperty property){
        this.first_name.bindBidirectional(property);
    }

    public void bindLastName(StringProperty property){
        this.last_name.bindBidirectional(property);
    }

    public void bindPhoneNumber(StringProperty property){
        this.phone_number.bindBidirectional(property);
    }

    




    public void reset()
    {
        this.email.set("");
        this.username.set("");
        this.password.set("");
        this.phone_number.set("");
        this.first_name.set("");
        this.last_name.set("");
    }
    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Something went wrong...");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
