package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;

/**
 * The AdminManageDonatedBooksViewModel class provides the view model for managing donated books in the admin view.
 * It handles the logic for approving and rejecting donated books, as well as loading the list of donated books from the model.
 * This class listens for property changes from the model and updates the view accordingly.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageDonatedBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    final ListProperty<Book> bookList;

    /**
     * Constructs an AdminManageDonatedBooksViewModel with the specified model.
     * Initializes the book list and sets up a property change listener for the model.
     *
     * @param model The model to interact with for managing donated books
     */
    public AdminManageDonatedBooksViewModel(Model model){
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.addPropertyChangeListener(this);
    }

    /**
     * Binds the provided property to the book list property in the view model.
     *
     * @param property The property to bind to the book list
     * @throws RemoteException If a remote communication error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(bookList);
    }

    /**
     * Approves a donated book with the specified details.
     *
     * @param id The ID of the donated book
     * @param title The title of the book
     * @param author The author of the book
     * @param isbn The ISBN of the book
     * @param year The year of publication
     * @param publisher The publisher of the book
     * @param pageCount The page count of the book
     * @param genreId The genre ID of the book
     * @throws SQLException If a database access error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        model.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
    }

    /**
     * Rejects a donated book with the specified ID.
     *
     * @param bookId The ID of the book to reject
     * @throws SQLException If a database access error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        model.rejectDonatedBook(bookId);
    }

    /**
     * Resets the book list by fetching all donated books from the model.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getDonatedBooks());
    }

    /**
     * Handles property change events from the model.
     * Updates the book list based on changes in donated books.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("BookDonate".equals(evt.getPropertyName())) {
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("DonatedBookApproved".equals(evt.getPropertyName())) {
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("DonatedBookRejected".equals(evt.getPropertyName())) {
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
