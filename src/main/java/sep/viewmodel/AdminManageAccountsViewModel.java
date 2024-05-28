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

/**
 * The AdminManageAccountsViewModel class provides the view model for the admin manage accounts view.
 * It handles the logic for managing patron accounts, including updating user information,
 * deleting patrons, and loading the list of patrons from the database.
 * This class listens for property changes from the model and updates the view accordingly.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageAccountsViewModel implements PropertyChangeListener {
    private final Model model;
    private final ListProperty<Patron> patronList;
    private final SimpleObjectProperty<Patron> selectedPatron;
    private PropertyChangeSupport support;

    /**
     * Constructs an AdminManageAccountsViewModel with the specified model.
     * Initializes the patron list and sets up a property change listener for the model.
     *
     * @param model The model to interact with for patron management operations
     */
    public AdminManageAccountsViewModel(Model model) {
        this.model = model;
        this.patronList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedPatron = new SimpleObjectProperty<>();
        this.support = new PropertyChangeSupport(this);

        model.addPropertyChangeListener(this);
    }

    /**
     * Binds the provided property to the patron list property in the view model.
     *
     * @param property The property to bind to the patron list
     * @throws RemoteException If a remote communication error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Patron>> property) throws RemoteException {
        property.bindBidirectional(patronList);
    }

    /**
     * Binds the provided property to the selected patron property in the view model.
     *
     * @param property The property to bind to the selected patron
     * @throws RemoteException If a remote communication error occurs
     */
    public void bindSelectedBook(ReadOnlyObjectProperty<Patron> property) throws RemoteException {
        selectedPatron.bind(property);
    }

    /**
     * Updates the username of the patron with the specified user ID.
     *
     * @param newUsername The new username
     * @param userID The ID of the user to update
     * @throws RemoteException If a remote communication error occurs
     */

    public void updateUsername(String newUsername,int userID) throws RemoteException {
            try{
                model.updateUsername(userID, newUsername);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Updates the email of the patron with the specified user ID.
     *
     * @param newEmail The new email
     * @param userID The ID of the user to update
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateEmail(String newEmail,int userID) throws RemoteException{

            try{
                model.updateEmail(userID, newEmail);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Updates the phone number of the patron with the specified user ID.
     *
     * @param newPhoneNumber The new phone number
     * @param userID The ID of the user to update
     * @throws RemoteException If a remote communication error occurs
     */

    public void updatePhoneNumber(String newPhoneNumber,int userID) throws RemoteException{

            try{
                model.updatePhoneNumber(userID, newPhoneNumber);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Updates the first name of the patron with the specified user ID.
     *
     * @param newFirstName The new first name
     * @param userID The ID of the user to update
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateFirstName(String newFirstName,int userID) throws RemoteException{

            try{
                model.updateFirstName(userID, newFirstName);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Updates the last name of the patron with the specified user ID.
     *
     * @param newLastName The new last name
     * @param userID The ID of the user to update
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateLastName(String newLastName,int userID) throws RemoteException{

            try{
                model.updateLastName(userID, newLastName);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Updates the password of the patron with the specified user ID.
     *
     * @param userID The ID of the user to update
     * @param newPassword The new password
     * @throws RemoteException If a remote communication error occurs
     */
    public void updatePassword(int userID,String newPassword) throws RemoteException{
            try{
                model.updatePassword(userID, newPassword);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }
    /**
     * Updates the fees of the patron with the specified user ID.
     *
     * @param userID The ID of the user to update
     * @param newFees The new fees amount
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateFees(int userID, int newFees) throws RemoteException{
            try{
                model.updateFees(userID, newFees);
            }catch (Exception e){
                throw new IllegalStateException(e.getMessage());
            }
    }

    /**
     * Deletes the patron with the specified ID and reloads the list of patrons.
     *
     * @param id The ID of the patron to delete
     * @throws RemoteException If a remote communication error occurs
     */
    public void deletePatron(int id) throws RemoteException {
        try {
            model.deletePatron(id);
            loadPatrons();
        } catch (Exception e) {
            throw new RemoteException("Failed to delete patron: " + e.getMessage());
        }
    }

    /**
     * Loads the list of patrons from the database and updates the patron list property.
     *
     * @throws SQLException If a database access error occurs
     */
    public void loadPatrons() throws SQLException {
        try{
            List<Patron> patrons = PatronDatabaseImplementation.getInstance().getAllPatrons();
            patronList.setAll(patrons);
            support.firePropertyChange("patronList", null, patronList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }

    }

    /**
     * Adds a property change listener to this view model.
     *
     * @param listener The listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    /**
     * Removes a property change listener from this view model.
     *
     * @param listener The listener to remove
     */
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Handles property change events from the model.
     * Updates the patron list and notifies listeners of changes to the borrow and return book events.
     *
     * @param evt The property change event
     */
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
            if (evt.getPropertyName().equals("BorrowBook"))
            {
                try {
                    loadPatrons();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (evt.getPropertyName().equals("ReturnBook"))
            {
                try {
                    loadPatrons();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                this.support.firePropertyChange("ReturnBook", false, true);
            }
        });
    }
}
