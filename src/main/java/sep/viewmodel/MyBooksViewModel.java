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
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MyBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    private PropertyChangeListener support;
    private final ListProperty<Book> borrowedBookList;

    public MyBooksViewModel(Model model)
    {
        this.model = model;
        model.addPropertyChangeListener(this);
        this.borrowedBookList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(borrowedBookList);
    }
    public void resetBookList(Patron patron) throws RemoteException {
        borrowedBookList.setAll(model.getBorrowedBooks(patron));
    }

    public void returnBook(Book book, Patron patron) throws RemoteException, SQLException {
        model.returnBookToDatabase(book, patron);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("ResetBooks".equals(evt.getPropertyName())) {
            try {
                resetBookList(UserSession.getInstance().getLoggedInUser());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
