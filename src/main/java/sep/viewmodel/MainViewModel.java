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

public class MainViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ListProperty<Book> bookList;
    private final SimpleObjectProperty<Book> selectedBook;

    public MainViewModel(Model model)
    {
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
        model.addPropertyChangeListener(this);
    }

    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(bookList);
    }

    public void bindSelectedBook(ReadOnlyObjectProperty<Book> property) throws RemoteException {
        selectedBook.bind(property);
    }

    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getAllBooks());
    }

    public void showFiltered(String genre, String state,String search) throws RemoteException {
        bookList.setAll(model.filter(genre, state, search));
    }

    public void borrowBook(Book book, Patron patron)
        throws IOException, SQLException {
        model.borrowBooks(book, patron);
    }
    public void wishlistBook(Book book,Patron patron)
        throws IOException, SQLException{
        model.wishlistBook(book,patron);
    }
    public boolean isWishlisted(Book book,Patron patron) throws RemoteException, SQLException{
        return model.isWishlisted(book,patron);
    }
    public int getAmountOfBorrowedBooks(Patron patron) throws RemoteException, SQLException{
        return model.getAmountOfBorrowedBooks(patron);
    }
    public ArrayList<String> getEndingBooks(Patron patron) throws RemoteException, SQLException{
        return model.getEndingBooks(patron);
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("received in view model " + evt.getPropertyName());
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
