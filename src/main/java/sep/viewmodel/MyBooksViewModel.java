package sep.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;
import sep.model.UserSession;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The MyBooksViewModel class provides the view model for managing a patron's borrowed books.
 * It allows patrons to return books, check for books that are borrowed by him, and extend book borrowing periods.
 *
 * Author: Group 6 (Samuel, Kuba, Maciej, Romans)
 */
public class MyBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    private PropertyChangeListener support;
    final ListProperty<Book> borrowedBookList;

    /**
     * Constructs a MyBooksViewModel with the specified model.
     *
     * @param model The model to interact with for managing borrowed books
     */
    public MyBooksViewModel(Model model)
    {
        this.model = model;
        model.addPropertyChangeListener(this);
        this.borrowedBookList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }

    /**
     * Binds the provided property to the borrowedBookList property in the view model.
     *
     * @param property The property to bind to the borrowedBookList
     * @throws RemoteException If a remote error occurs
     */
    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(borrowedBookList);
    }

    /**
     * Resets the list of borrowed books for the specified patron.
     *
     * @param patron The patron whose borrowed books are to be reset
     * @throws RemoteException If a remote error occurs
     */
    public void resetBookList(Patron patron) throws RemoteException {
        borrowedBookList.setAll(model.getBorrowedBooks(patron));
    }

    /**
     * Allows a patron to return a borrowed book.
     *
     * @param book   The book to return
     * @param patron The patron returning the book
     * @throws IOException  If an I/O error occurs
     * @throws SQLException If a SQL error occurs
     */
    public void returnBook(Book book, Patron patron)
        throws IOException, SQLException {
        model.returnBookToDatabase(book, patron);
    }

    /**
     * Retrieves a list of books that can be extended for the specified patron.
     *
     * @param patron The patron to check for extendable books
     * @return The list of books that can be extended
     * @throws RemoteException If a remote error occurs
     */
    public ArrayList<String> checkBooksToExtend(Patron patron) throws RemoteException {
        return model.getEndingBooks(patron);
    }

    /**
     * Allows a patron to extend the borrowing period for a book.
     *
     * @param book   The book to extend the borrowing period for
     * @param patron The patron extending the book borrowing period
     * @throws SQLException If a SQL error occurs
     * @throws RemoteException If a remote error occurs
     */
    public void extendBook(Book book, Patron patron) throws SQLException, RemoteException {
        model.extendBook(book, patron);
    }

    /**
     * Responds to property change events triggered by the model.
     * This method is called whenever a property change event occurs in the model,
     * allowing the view model to react accordingly by refreshing the borrowed book list.
     *
     * @param evt The property change event
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("BorrowBook".equals(evt.getPropertyName())) {
            try {
                resetBookList(UserSession.getInstance().getLoggedInUser());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        if ("login".equals(evt.getPropertyName())){
            try {
                resetBookList(UserSession.getInstance().getLoggedInUser());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
