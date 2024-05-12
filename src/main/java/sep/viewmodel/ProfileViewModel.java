package sep.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import sep.model.Model;
import sep.model.UserSession;

public class ProfileViewModel {
    private final Model model;
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final StringProperty phone_number;
    private final StringProperty error;
    private final StringProperty password;
    private final StringProperty patronID;
    private UserSession session;



    public ProfileViewModel(Model model)
    {
        this.model = model;
        this.username = new SimpleStringProperty("");
        this.email = new SimpleStringProperty("");
        this.first_name = new SimpleStringProperty("");
        this.last_name = new SimpleStringProperty("");
        this.phone_number = new SimpleStringProperty("");
        this.password = new SimpleStringProperty("");
        this.patronID = new SimpleStringProperty("");
        this.error = new SimpleStringProperty("");
    }

    public void setPatronData(){
        username.set(UserSession.getInstance().getLoggedInUser().getUsername());
        email.set(UserSession.getInstance().getLoggedInUser().getEmail());
        first_name.set(UserSession.getInstance().getLoggedInUser().getFirstName());
        last_name.set(UserSession.getInstance().getLoggedInUser().getLastName());
        phone_number.set(UserSession.getInstance().getLoggedInUser().getPhoneNumber());
        password.set(UserSession.getInstance().getLoggedInUser().getPassword());
        patronID.set(String.valueOf(UserSession.getInstance().getLoggedInUser().getUserID()));
    }

    public void bindUsername(StringProperty property){
        property.bind(username);
    }
    public void bindEmail(StringProperty property){
        property.bind(email);
    }
    public void bindFirstName(StringProperty property){
        property.bind(first_name);
    }
    public void bindLastName(StringProperty property){
        property.bind(last_name);
    }
    public void bindPhoneNumber(StringProperty property){
        property.bind(phone_number);
    }
    public void bindError(StringProperty property){
        property.bind(error);
    }
    public void bindPassword(StringProperty property){
        property.bind(password);
    }
    public void bindPatronID(StringProperty property){
        property.bind(patronID);
    }
}
