package sep.viewmodel;

import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.model.Model;

import java.rmi.RemoteException;

public class CreateAccountViewModel {
    private final Model model;
    private final StringProperty email;
    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final LongProperty phone_number;
    private final StringProperty repeatPassword;

    public CreateAccountViewModel(Model model) {
        this.model = model;
        this.email = new SimpleStringProperty("");
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.repeatPassword = new SimpleStringProperty("");
        this.first_name = new SimpleStringProperty("");
        this.last_name = new SimpleStringProperty("");
        this.phone_number = new SimpleLongProperty();
    }

    public void createPatron() throws RemoteException {
        try{
            if(!password.get().equals(repeatPassword.get()))
                throw new Exception("Passwords do not match!");

            model.createPatron(username.get(), password.get(), first_name.get(), last_name.get(), email.get(), phone_number.get());
            reset();
            System.out.println("Patron created!");
        }catch(Exception e){
            throw new IllegalArgumentException("Error creating account");
        }
    }




    public void reset()
    {
        this.email.set("");
        this.username.set("");
        this.password.set("");
        this.phone_number.set(0);
        this.first_name.set("");
        this.last_name.set("");
    }
}
