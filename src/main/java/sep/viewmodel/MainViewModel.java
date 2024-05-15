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
import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ListProperty<Book> bookList;
    private final SimpleObjectProperty<Book> selectedBook;
    private final RemotePropertyChangeSupport<Patron> support;

    public MainViewModel(Model model)
    {
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
        this.support = new RemotePropertyChangeSupport<>();
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

    public void borrowBook(Book book, Patron patron) throws RemoteException, SQLException {
        model.borrowBooks(book, patron);
    }
     public void addPropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
    {
        support.addPropertyChangeListener(listener);
    }

     public void removePropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
    {
        support.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("received in view model " + evt.getPropertyName());
        Platform.runLater(() -> {
            if ("UserLoggedIn".equals(evt.getPropertyName())){
                try
                {
                    support.firePropertyChange("UserLoggedIn", null, (Patron)evt.getNewValue());
                }
                catch (RemoteException e)
                {
                    throw new RuntimeException(e);
                }
            }
            if ("BookBorrowed".equals(evt.getPropertyName())){
                try {
                    resetBookList();
                    System.out.println("refreshed table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            } else if ("borrowedBook".equals(evt.getPropertyName()) || "returnedBook".equals(evt.getPropertyName())) {
                try {
                    resetBookList();
                    support.firePropertyChange("ResetBooks", null, (Patron)evt.getNewValue());
                    System.out.println("refreshed table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
