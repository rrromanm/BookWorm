package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;
import sep.model.UserSession;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;

public class ProfileViewModel implements PropertyChangeListener
{
    private final Model model;
    private final StringProperty username;
    private final StringProperty email;
    private final StringProperty first_name;
    private final StringProperty last_name;
    private final StringProperty phone_number;
    private final StringProperty error;
    private final StringProperty password;
    private final StringProperty patronID;
    private final ListProperty<Book> historyOfBooksList;
    private final ListProperty<Book> wishlistList;
    private final PropertyChangeSupport support;


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
        this.historyOfBooksList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.wishlistList = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.addPropertyChangeListener(this);
        this.support = new PropertyChangeSupport(this);
    }
    public void bindHistoryList(ObjectProperty<ObservableList<Book>> property) throws
        RemoteException
    {
        property.bindBidirectional(historyOfBooksList);
    }
    public void bindWishlistList(ObjectProperty<ObservableList<Book>> property) throws
        RemoteException
    {
        property.bindBidirectional(wishlistList);
    }

    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    public void bindEmail(StringProperty property) {
        property.bindBidirectional(email);
    }

    public void bindFirstName(StringProperty property) {
        property.bindBidirectional(first_name);
    }

    public void bindLastName(StringProperty property) {
        property.bindBidirectional(last_name);
    }

    public void bindPhoneNumber(StringProperty property) {
        property.bindBidirectional(phone_number);
    }

    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    public void bindUserId(StringProperty property){
        property.bindBidirectional(patronID);
    }

    public void fillData() {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            username.set(loggedInUser.getUsername());
            email.set(loggedInUser.getEmail());
            first_name.set(loggedInUser.getFirstName());
            last_name.set(loggedInUser.getLastName());
            phone_number.set(loggedInUser.getPhoneNumber());
            password.set(loggedInUser.getPassword());
            patronID.set(String.valueOf(loggedInUser.getUserID()));
        }
    }

    public void resetHistoryList(Patron patron) throws RemoteException {
        historyOfBooksList.setAll(model.getHistoryOfBooks(patron));
    }
    public void resetWishlistList(Patron patron) throws RemoteException{
        wishlistList.setAll(model.getWishlistedBooks(patron));
    }
    public int getAmountOfReadBooks(Patron patron) throws RemoteException{
        return model.getAmountOfReadBooks(patron);
    }

    public void updateUsername(String newUsername,int userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser != null){
            try{
                model.updateUsername(userID, newUsername);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateEmail(String newEmail,int userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateEmail(userID, newEmail);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updatePhoneNumber(String newPhoneNumber,int  userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updatePhoneNumber(userID, newPhoneNumber);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateFirstName(String newFirstName,int userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateFirstName(userID, newFirstName);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateLastName(String newLastName,int userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateLastName(userID, newLastName);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updatePassword(String newPassword,int userID){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updatePassword(userID, newPassword);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }
    public void removeFromWishlist(Book book, Patron patron)
        throws SQLException, IOException
    {
        model.deleteFromWishlist(book,patron);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }


    @Override public void propertyChange(PropertyChangeEvent event)
    {
        Platform.runLater(() -> {
            if (event.getPropertyName().equals("BorrowBook"))
            {
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (event.getPropertyName().equals("ReturnBook"))
            {
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getPropertyName().equals("Wishlist"))
            {
                try {
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if(event.getPropertyName().equals("removeBook")){
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if(event.getPropertyName().equals("updateBook")){
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }
            if(event.getPropertyName().equals("updatePatron")){
                this.support.firePropertyChange("updatePatron", false, true);
            }
            if (event.getPropertyName().equals("ExtendBook"))
            {
                this.support.firePropertyChange("ExtendBook", false, true);
            }
        });
    }
}
