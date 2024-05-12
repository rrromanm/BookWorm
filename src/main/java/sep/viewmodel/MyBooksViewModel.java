package sep.viewmodel;

import dk.via.remote.observer.RemotePropertyChangeEvent;
import dk.via.remote.observer.RemotePropertyChangeListener;
import dk.via.remote.observer.RemotePropertyChangeSupport;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.rmi.RemoteException;

public class MyBooksViewModel implements RemotePropertyChangeListener<Patron>
{
    private final Model model;
    private RemotePropertyChangeSupport<Patron> support;
    private final ListProperty<Book> borrowedBookList;

    public MyBooksViewModel(Model model)
    {
        this.model = model;
        this.support = new RemotePropertyChangeSupport<Patron>();
        model.addPropertyChangeListener(this);
        this.borrowedBookList = new SimpleListProperty<>(FXCollections.observableArrayList());
    }
    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(borrowedBookList);
    }
    public void addPropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
    {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(RemotePropertyChangeListener<Patron> listener)
    {
        support.removePropertyChangeListener(listener);
    }
    public void resetBookList(Patron patron) throws RemoteException {
        borrowedBookList.setAll(model.getBorrowedBooks(patron));
    }

    public void returnBook(Book book, Patron patron) throws RemoteException {
        model.returnBook(book, patron);
    }

    @Override public void propertyChange(RemotePropertyChangeEvent evt) throws RemoteException
    {
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
        });
    }
}
