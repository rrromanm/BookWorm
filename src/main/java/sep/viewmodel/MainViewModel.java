package sep.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.rmi.RemoteException;
import java.sql.SQLException;

public class MainViewModel {
    private final Model model;
    private final ListProperty<Book> bookList;
    private final SimpleObjectProperty<Book> selectedBook;

    public MainViewModel(Model model)
    {
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
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
}
