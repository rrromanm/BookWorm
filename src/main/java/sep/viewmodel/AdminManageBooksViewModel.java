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

public class AdminManageBooksViewModel implements PropertyChangeListener
{
    private final Model model;
    private final ListProperty<Book> bookList;
    private final SimpleObjectProperty<Book> selectedBook;
    private PropertyChangeSupport support;

    public AdminManageBooksViewModel(Model model){
        this.model = model;
        this.bookList = new SimpleListProperty<>(FXCollections.observableArrayList());
        this.selectedBook = new SimpleObjectProperty<>();
        this.support = new PropertyChangeSupport(this);
        
        model.addPropertyChangeListener(this);
    }

    public void bindList(ObjectProperty<ObservableList<Book>> property) throws RemoteException {
        property.bindBidirectional(bookList);
    }
    public void resetBookList() throws RemoteException {
        bookList.setAll(model.getAllBooks());
    }
    public void loadBooks() throws RemoteException {
        try{
            List<Book> books = BookDatabaseImplementation.getInstance().readBooks();
            bookList.setAll(books);
            support.firePropertyChange("bookList", null, bookList);
        }catch (Exception e){
            throw new IllegalStateException(e.getMessage());
        }
    }
    public void showFiltered(String genre, String state,String search) throws RemoteException {
        bookList.setAll(model.filter(genre, state, search));
    }
    public void setSelectedBook(Book book) {
        selectedBook.set(book);
    }
    public void updateBook(int bookID, String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException{
        model.updateBook(bookID, title, author, year, publisher, isbn,pageCount,genre);
        support.firePropertyChange("updateBook", false, true);
    }
    public void deleteBook(int bookID,String title, String author, String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        model.deleteBook(bookID,title,author,year,publisher,isbn,pageCount,genre);
        support.firePropertyChange("removeBook", false, true);
    }
    public void createBook(String title, String author,String year, String publisher, String isbn, String pageCount, String genre) throws SQLException, RemoteException {
        model.createBook(title,author,year,publisher,isbn,pageCount,genre);
        support.firePropertyChange("addBook", null, bookList);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Platform.runLater(() -> {
            if ("removeBook".equals(evt.getPropertyName())){
                try {
                    loadBooks();
                    System.out.println("refreshed books table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if("addBook".equals(evt.getPropertyName())) {
                try {
                    loadBooks();
                    System.out.println("refreshed books table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
            if ("updateBook".equals(evt.getPropertyName())) {
                try {
                    loadBooks();
                    System.out.println("refreshed books table");
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
