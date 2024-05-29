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

/**
 * The ProfileViewModel class provides the view model for managing a patron's profile information,
 * including username, email, first name, last name, phone number, password, patron ID, history of books,
 * and wishlist.
 * It allows patrons to update their profile information, view their borrowing history, and remove books from their wishlist.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class ProfileViewModel implements PropertyChangeListener {
    private final Model model;
    protected final StringProperty username;
    protected final StringProperty email;
    protected final StringProperty first_name;
    protected final StringProperty last_name;
    protected final StringProperty phone_number;
    private final StringProperty error;
    public final StringProperty password;
    protected final StringProperty patronID;
    final ListProperty<Book> historyOfBooksList;
    final ListProperty<Book> wishlistList;
    final PropertyChangeSupport support;

    /**
     * Constructs a ProfileViewModel with the specified model.
     *
     * @param model The model to interact with for managing profile information
     */
    public ProfileViewModel(Model model) {
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

    /**
     * Binds the provided property to the historyOfBooksList property in the view model.
     *
     * @param property The property to bind to the historyOfBooksList
     * @throws RemoteException If a remote error occurs
     */
    public void bindHistoryList(ObjectProperty<ObservableList<Book>> property) throws
            RemoteException {
        property.bindBidirectional(historyOfBooksList);
    }

    /**
     * Binds the provided property to the wishlistList property in the view model.
     *
     * @param property The property to bind to the wishlistList
     * @throws RemoteException If a remote error occurs
     */
    public void bindWishlistList(ObjectProperty<ObservableList<Book>> property) throws
            RemoteException {
        property.bindBidirectional(wishlistList);
    }

    /**
     * Binds the provided property to the username property in the view model.
     *
     * @param property The property to bind to the username
     */
    public void bindUsername(StringProperty property) {
        property.bindBidirectional(username);
    }

    /**
     * Binds the provided property to the email property in the view model.
     *
     * @param property The property to bind to the email
     */
    public void bindEmail(StringProperty property) {
        property.bindBidirectional(email);
    }

    /**
     * Binds the provided property to the first_name property in the view model.
     *
     * @param property The property to bind to the first_name
     */
    public void bindFirstName(StringProperty property) {
        property.bindBidirectional(first_name);
    }

    /**
     * Binds the provided property to the last_name property in the view model.
     *
     * @param property The property to bind to the last_name
     */
    public void bindLastName(StringProperty property) {
        property.bindBidirectional(last_name);
    }

    /**
     * Binds the provided property to the phone_number property in the view model.
     *
     * @param property The property to bind to the phone_number
     */
    public void bindPhoneNumber(StringProperty property) {
        property.bindBidirectional(phone_number);
    }

    /**
     * Binds the provided property to the password property in the view model.
     *
     * @param property The property to bind to the password
     */
    public void bindPassword(StringProperty property) {
        property.bindBidirectional(password);
    }

    /**
     * Binds the provided property to the patronID property in the view model.
     *
     * @param property The property to bind to the patronID
     */
    public void bindUserId(StringProperty property) {
        property.bindBidirectional(patronID);
    }

    /**
     * Fills the profile data with information of the logged-in user.
     */
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

    /**
     * Resets the list of borrowed books for the specified patron.
     *
     * @param patron The patron whose borrowed books are to be reset
     * @throws RemoteException If a remote error occurs
     */
    public void resetHistoryList(Patron patron) throws RemoteException {
        historyOfBooksList.setAll(model.getHistoryOfBooks(patron));
    }

    /**
     * Resets the wishlist for the specified patron.
     *
     * @param patron The patron whose wishlist is to be reset
     * @throws RemoteException If a remote error occurs
     */
    public void resetWishlistList(Patron patron) throws RemoteException {
        wishlistList.setAll(model.getWishlistedBooks(patron));
    }

    /**
     * Retrieves the amount of books read by the specified patron.
     *
     * @param patron The patron whose read book count is to be retrieved
     * @return The number of books read by the patron
     * @throws RemoteException If a remote error occurs
     */
    public int getAmountOfReadBooks(Patron patron) throws RemoteException {
        return model.getAmountOfReadBooks(patron);
    }

    /**
     * Deletes the logged-in patron's account.
     *
     * @throws RemoteException If a remote error occurs
     */
    public void deletePatron() throws RemoteException {
        try {
            model.deletePatron(UserSession.getInstance().getLoggedInUser().getUserID());
        } catch (RemoteException e) {
            throw new RemoteException(e.getMessage());
        }
    }

    /**
     * Updates the username of the logged-in patron.
     *
     * @param newUsername The new username to set
     * @param userID      The ID of the logged-in patron
     */
    public void updateUsername(String newUsername, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updateUsername(loggedInUser.getUserID(), newUsername);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Updates the email of the logged-in patron.
     *
     * @param newEmail The new email address to set
     * @param userID   The ID of the logged-in patron
     */
    public void updateEmail(String newEmail, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updateEmail(loggedInUser.getUserID(), newEmail);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Updates the phone number of the logged-in patron.
     *
     * @param newPhoneNumber The new phone number to set
     * @param userID         The ID of the logged-in patron
     */
    public void updatePhoneNumber(String newPhoneNumber, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updatePhoneNumber(loggedInUser.getUserID(), newPhoneNumber);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Updates the first name of the logged-in patron.
     *
     * @param newFirstName The new first name to set
     * @param userID       The ID of the logged-in patron
     */
    public void updateFirstName(String newFirstName, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updateFirstName(loggedInUser.getUserID(), newFirstName);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Updates the last name of the logged-in patron.
     *
     * @param newLastName The new last name to set
     * @param userID      The ID of the logged-in patron
     */
    public void updateLastName(String newLastName, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updateLastName(loggedInUser.getUserID(), newLastName);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Updates the password of the logged-in patron.
     *
     * @param newPassword The new password to set
     * @param userID      The ID of the logged-in patron
     */
    public void updatePassword(String newPassword, int userID) {
        Patron loggedInUser = UserSession.getInstance().getLoggedInUser();
        if (loggedInUser != null) {
            try {
                model.updatePassword(loggedInUser.getUserID(), newPassword);
            } catch (Exception e) {
                error.set(e.getMessage());
                throw new IllegalStateException(e.getMessage());
            }
        } else {
            throw new IllegalStateException("No user logged in.");
        }
    }

    /**
     * Removes the specified book from the wishlist of the logged-in patron.
     *
     * @param book   The book to remove from the wishlist
     * @param patron The logged-in patron
     * @throws SQLException If a SQL error occurs
     * @throws IOException  If an I/O error occurs
     */
    public void removeFromWishlist(Book book, Patron patron)
            throws SQLException, IOException {
        model.deleteFromWishlist(book, patron);
    }

    /**
     * Adds a property change listener to the support object.
     *
     * @param listener The property change listener to add
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.support.addPropertyChangeListener(listener);
    }

    /**
     * Handles property change events triggered by changes in the model.
     * This method is called when a property value in the model changes,
     * and it updates the view model accordingly.
     *
     * @param event The property change event containing information about the change
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        {
            if (event.getPropertyName().equals("BorrowBook")) {
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (event.getPropertyName().equals("ReturnBook")) {
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getPropertyName().equals("Wishlist")) {
                try {
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getPropertyName().equals("removeBook")) {
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if (event.getPropertyName().equals("updateBook")) {
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }

            }
            if (event.getPropertyName().equals("updatePatron")) {
                this.support.firePropertyChange("updatePatron", false, true);
            }
            if (event.getPropertyName().equals("ExtendBook")) {
                this.support.firePropertyChange("ExtendBook", false, true);
            }
            if (event.getPropertyName().equals("removePatron")) {
                this.support.firePropertyChange("removePatron", false, true);
            }
            if (event.getPropertyName().equals("login")) {
                this.support.firePropertyChange("login", false, true);
                try {
                    resetHistoryList(UserSession.getInstance().getLoggedInUser());
                    resetWishlistList(UserSession.getInstance().getLoggedInUser());
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
