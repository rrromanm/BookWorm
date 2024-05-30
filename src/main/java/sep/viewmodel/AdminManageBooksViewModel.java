package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.jdbc.BookDatabaseImplementation;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.List;

/**
 * The AdminManageBooksViewModel class provides the view model for the admin manage books view.
 * It handles the logic for managing books, including updating, deleting, and adding new books,
 * as well as filtering and loading the list of books from the database.
 * This class listens for property changes from the model and updates the view accordingly.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class AdminManageBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    final ListProperty<Book> bookList;
    final SimpleObjectProperty<Book> selectedBook;
    PropertyChangeSupport support;

    /**
     * Constructs an AdminManageBooksViewModel with the specified model.
     * Initializes the book list and sets up a property change listener for the model.
     *
     * @param model The model to interact with for book management operations
     */

    public AdminManageBooksViewModel(Model model){
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
        this.support = new PropertyChangeSupport(this);

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
     * Resets the book list by fetching all books from the model.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getAllBooks());
    }

    /**
     * Loads the list of books from the database and updates the book list property.
     *
     * @throws RemoteException If a remote communication error occurs
     */
    public void loadBooks() throws RemoteException {
        try{
            bookList.setAll(model.getAllBooks());
            support.firePropertyChange("bookList", null, bookList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Shows a filtered list of books based on the specified genre, state, and search text.
     *
     * @param genre The genre to filter by
     * @param state The state to filter by
     * @param search The search query to filter by
     * @throws RemoteException If a remote communication error occurs
     */
    public void showFiltered(String genre, String state,String search) throws RemoteException {
        bookList.setAll(model.filter(genre, state, search));
    }

    /**
     * Sets the selected book in the view model.
     *
     * @param book The book to set as selected
     */
    public void setSelectedBook(Book book) {
        selectedBook.set(book);
    }

    /**
     * Updates the book with the specified details.
     *
     * @param bookID The ID of the book to update
     * @param title The new title of the book
     * @param author The new author of the book
     * @param year The new year of publication
     * @param publisher The new publisher of the book
     * @param isbn The new ISBN of the book
     * @param pageCount The new page count of the book
     * @param genre The new genre of the book
     * @throws SQLException If a database access error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException{
        model.updateBook(bookID, title, author, year, publisher, isbn,pageCount,genre);
        support.firePropertyChange("updateBook", false, true);
    }

    /**
     * Deletes the book with the specified details.
     *
     * @param bookID The ID of the book to delete
     * @param title The title of the book
     * @param author The author of the book
     * @param year The year of publication
     * @param publisher The publisher of the book
     * @param isbn The ISBN of the book
     * @param pageCount The page count of the book
     * @param genre The genre of the book
     * @throws SQLException If a database access error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        model.deleteBook(bookID,title,author,year,publisher,isbn,pageCount,genre);
        support.firePropertyChange("removeBook", false, true);
    }

    /**
     * Creates a new book with the specified details.
     *
     * @param title The title of the new book
     * @param author The author of the new book
     * @param year The year of publication of the new book
     * @param publisher The publisher of the new book
     * @param isbn The ISBN of the new book
     * @param pageCount The page count of the new book
     * @param genre The genre of the new book
     * @throws SQLException If a database access error occurs
     * @throws RemoteException If a remote communication error occurs
     */
    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        model.createBook(title,author,year,publisher,isbn,pageCount,genre);
        support.firePropertyChange("addBook", null, bookList);
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
     * Updates the view based on the changes in the model.
     *
     * @param event The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent event) {
        Platform.runLater(() -> {
            if (event.getPropertyName().equals("BorrowBook"))
            {
                this.support.firePropertyChange("BorrowBook", false, true);
            }
            if (event.getPropertyName().equals("ReturnBook"))
            {
                this.support.firePropertyChange("ReturnBook", false, true);
            }
            if (event.getPropertyName().equals("Wishlist"))
            {
                this.support.firePropertyChange("Wishlist", false, true);
            }
            if (event.getPropertyName().equals("BookDonate"))
            {
                this.support.firePropertyChange("BookDonate", false, true);
            }
            if (event.getPropertyName().equals("createPatron"))
            {
                this.support.firePropertyChange("createPatron", false, true);
            }
            if (event.getPropertyName().equals("DonatedBookApproved"))
            {
                this.support.firePropertyChange("DonatedBookApproved", false,true);
            }
            if (event.getPropertyName().equals("DonatedBookRejected")) {
                this.support.firePropertyChange("DonatedBookRejected", false, true);
            }
            if(event.getPropertyName().equals("removeBook")){
                this.support.firePropertyChange("removeBook", false, true);
            }
            if(event.getPropertyName().equals("updateBook")){
                this.support.firePropertyChange("updateBook", false, true);
            }
            if(event.getPropertyName().equals("createBook")){
                this.support.firePropertyChange("createBook", false, true);
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
