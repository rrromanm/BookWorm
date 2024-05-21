package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.jdbc.PatronDatabaseImplementation;
import sep.model.Model;
import sep.model.Patron;
import sep.model.UserSession;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

public class AdminManageAccountsViewModel implements PropertyChangeListener {
    private final Model model;
    private final ListProperty<Patron> patronList;
    private final SimpleObjectProperty<Patron> selectedPatron;
    private PropertyChangeSupport support;

    public AdminManageAccountsViewModel(Model model) {
        this.model = model;
        this.patronList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedPatron = new SimpleObjectProperty<>();
        this.support = new PropertyChangeSupport(this);

        model.addPropertyChangeListener(this);
    }
    public void bindList(ObjectProperty<ObservableList<Patron>> property) throws RemoteException {
        property.bindBidirectional(patronList);
    }

    public void bindSelectedBook(ReadOnlyObjectProperty<Patron> property) throws RemoteException {
        selectedPatron.bind(property);
    }

    public void updateUsername(String newUsername,int userID) throws RemoteException {
            try{
                model.updateUsername(userID, newUsername);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void updateEmail(String newEmail,int userID) throws RemoteException{

            try{
                model.updateEmail(userID, newEmail);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
        }

    public void updatePhoneNumber(String newPhoneNumber,int userID) throws RemoteException{

            try{
                model.updatePhoneNumber(userID, newPhoneNumber);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void updateFirstName(String newFirstName,int userID) throws RemoteException{

            try{
                model.updateFirstName(userID, newFirstName);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void updateLastName(String newLastName,int userID) throws RemoteException{

            try{
                model.updateLastName(userID, newLastName);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void updatePassword(int userID,String newPassword) throws RemoteException{
            try{
                model.updatePassword(userID, newPassword);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void updateFees(int userID, int newFees) throws RemoteException{
            try{
                model.updateFees(userID, newFees);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    public void deletePatron(Patron patron){
        //TODO: IMPLEMENT DELETE PATRON
    }
    public void loadPatrons() throws SQLException {
        try{
            List<Patron> patrons = PatronDatabaseImplementation.getInstance().getAllPatrons();
            patronList.setAll(patrons);
            support.firePropertyChange("patronList", null, patronList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }

    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("createPatron".equals(evt.getPropertyName())){
                try {
                    loadPatrons();
                    System.out.println("refreshed patron table");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            if("updatePatron".equals(evt.getPropertyName())){
                try {
                    loadPatrons();
                    System.out.println("refreshed patron table");
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
