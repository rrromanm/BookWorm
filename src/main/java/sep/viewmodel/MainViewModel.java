package sep.viewmodel;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The MainViewModel class provides the view model for managing the main functionality of the application.
 * It handles borrowing, wishlisting and is used as home window that leads to all features.
 * It interacts with the model to perform operations related to books and patrons.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class MainViewModel implements PropertyChangeListener
{
    private final Model model;
    final ListProperty<Book> bookList;
    final SimpleObjectProperty<Book> selectedBook;

    /**
     * Constructs a MainViewModel with the specified model.
     *
     * @param model The model to interact with for main operations
     */
    public MainViewModel(Model model)
    {
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
        model.addPropertyChangeListener(this);
    }

    /**
     * Binds the provided property to the bookList property in the view model.
     *
     * @param property The property to bind to the bookList
     * @throws RemoteException If a remote error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(bookList);
    }

    /**
     * Binds the provided property to the selectedBook property in the view model.
     *
     * @param property The property to bind to the selectedBook
     * @throws RemoteException If a remote error occurs
     */
    public void bindSelectedBook(ReadOnlyObjectProperty<Book> property) throws RemoteException {
        selectedBook.bind(property);
    }

    /**
     * Resets the book list to display all available books.
     *
     * @throws RemoteException If a remote error occurs
     */
    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getAllBooks());
    }

    /**
     * Displays filtered books based on the provided criteria.
     *
     * @param genre  The genre to filter by
     * @param state  The state of the book to filter by
     * @param search The search keyword to filter by
     * @throws RemoteException If a remote error occurs
     */
    public void showFiltered(String genre, String state,String search) throws RemoteException {
        bookList.setAll(model.filter(genre, state, search));
    }

    /**
     * Allows a patron to borrow a book.
     *
     * @param book   The book to borrow
     * @param patron The patron borrowing the book
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    public void borrowBook(Book book, Patron patron)
        throws IOException, SQLException {
        model.borrowBooks(book, patron);
    }

    /**
     * Allows a patron to wishlist a book.
     *
     * @param book   The book to wishlist
     * @param patron The patron wishing to add the book to their wishlist
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    public void wishlistBook(Book book,Patron patron)
        throws IOException, SQLException{
        model.wishlistBook(book,patron);
    }

    /**
     * Checks if a book is wishlisted by a patron.
     *
     * @param book   The book to check
     * @param patron The patron who wishlisted the book
     * @return True if the book is wishlisted by the patron, otherwise false
     * @throws RemoteException If a remote error occurs
     * @throws SQLException    If a SQL error occurs
     */
    public boolean isWishlisted(Book book,Patron patron) throws RemoteException, SQLException{
        return model.isWishlisted(book,patron);
    }

    /**
     * Retrieves the amount of borrowed books by a patron.
     *
     * @param patron The patron to retrieve the borrowed book count for
     * @return The number of books borrowed by the patron
     * @throws RemoteException If a remote error occurs
     * @throws SQLException    If a SQL error occurs
     */
    public int getAmountOfBorrowedBooks(Patron patron) throws RemoteException, SQLException{
        return model.getAmountOfBorrowedBooks(patron);
    }

    /**
     * Retrieves a list of ending books for a patron.
     *
     * @param patron The patron to retrieve the ending books for
     * @return A list of ending books for the patron
     * @throws RemoteException If a remote error occurs
     * @throws SQLException    If a SQL error occurs
     */
    public ArrayList<String> getEndingBooks(Patron patron) throws RemoteException, SQLException{
        return model.getEndingBooks(patron);
    }

    /**
     * Responds to property change events triggered by the model.
     * This method is called whenever a property change event occurs in the model,
     * allowing the view model to react accordingly by refreshing the book list.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("received in main model " + evt.getPropertyName());
        Platform.runLater(() -> {
            if ("BorrowBook".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                    System.out.println("refreshed table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("ReturnBook".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                    System.out.println("refreshed table");
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
            if("removeBook".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("createBook".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("updateBook".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
