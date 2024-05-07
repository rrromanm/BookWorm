package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.model.Model;

import java.rmi.RemoteException;

public class LoginViewModel
{
    private final Model model;
    private final StringProperty username;
    private final StringProperty password;

    public LoginViewModel(Model model)
    {
        this.model = model;
        this.username = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
    }

    public void login(){
        try {
            model.login(username.get(), password.get());
            reset();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
    public void reset()
    {
        this.username.set("");
        this.password.set("");
    }
}
