package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.UserSession;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

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
        });
    }
}
