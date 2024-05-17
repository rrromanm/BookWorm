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
    public void resetHistoryList(Patron patron) throws RemoteException {
        historyOfBooksList.setAll(model.getHistoryOfBooks(patron));
    }
    public void resetWishlistList(Patron patron) throws RemoteException{
        wishlistList.setAll(model.getWishlistedBooks(patron));
    }
    public int getAmountOfReadBooks(Patron patron) throws RemoteException{
        return model.getAmountOfReadBooks(patron);
    }

    public void updateUsername(String newUsername,String oldUsername){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser != null){
            try{
                model.updateUsername(oldUsername, newUsername);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateEmail(String newEmail,String oldEmail){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateEmail(oldEmail, newEmail);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updatePhoneNumber(String newPhoneNumber,String oldPhoneNumber){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updatePhoneNumber(oldPhoneNumber, newPhoneNumber);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateFirstName(String newFirstName,String oldFirstName){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateFirstName(oldFirstName, newFirstName);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updateLastName(String newLastName,String oldLastName){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updateLastName(oldLastName, newLastName);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }

    public void updatePassword(String newPassword,String oldPassword){
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if(loggedInUser!= null){
            try{
                model.updatePassword(oldPassword, newPassword);
            }catch (Exception e){
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        }else{
            throw new IllegalStateException("No user logged in.");
        }
    }
    public void removeFromWishlist(Book book, Patron patron) throws SQLException, RemoteException
    {
        model.deleteFromWishlist(book,patron);
    }

    @Override public void propertyChange(PropertyChangeEvent evt)
    {
        Platform.runLater(() -> {
            if ("Wishlist".equals(evt.getPropertyName())) {
                try {
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
