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

public class AdminManageDonatedBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ListProperty<Book> bookList;

    public AdminManageDonatedBooksViewModel(Model model){
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        model.addPropertyChangeListener(this);
    }

    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(bookList);
    }

    public void approveDonatedBook(int id, String title, String author, long isbn, int year, String publisher, int pageCount, String genreId) throws SQLException, RemoteException {
        model.approveDonatedBook(id, title, author, isbn, year, publisher, pageCount, genreId);
    }

    public void rejectDonatedBook(int bookId) throws SQLException, RemoteException {
        model.rejectDonatedBook(bookId);
    }

    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getDonatedBooks());
    }

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
