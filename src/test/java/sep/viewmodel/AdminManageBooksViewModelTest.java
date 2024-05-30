package sep.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;
import sep.model.Book;
import sep.model.Event;
import sep.model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminManageBooksViewModelTest {

    private AdminManageBooksViewModel viewModel;
    private Model model;
    private Book book;
    private ArrayList<Book> books;

    @Before
    public void setUp() {
        model = mock(Model.class);
        viewModel = new AdminManageBooksViewModel(model);
        books = new ArrayList<>();
        book = new Book(1,"Title", "Author", 2000, "ISBN", 2000, 300, "Genre");
        books.add(book);
    }

    @Test
    public void testBindList() throws RemoteException {
        ObjectProperty<ObservableList<Book>> property = new SimpleObjectProperty<>(FXCollections.observableArrayList());
        viewModel.bindList(property);
        
        assertSame(property.get(), viewModel.bookList.get());
    }

    @Test
    public void testResetBookList() throws RemoteException {
        when(model.getAllBooks()).thenReturn(books);

        viewModel.resetBookList();

        verify(model).getAllBooks();
        assertEquals(1, viewModel.bookList.size());
        assertEquals(book, viewModel.bookList.get(0));
    }

    @Test
    public void testLoadBooks() throws RemoteException {
        when(model.getAllBooks()).thenReturn(books);

        viewModel.loadBooks();

        verify(model).getAllBooks();
        assertEquals(1, viewModel.bookList.size());
        assertEquals(book, viewModel.bookList.get(0));
    }

    @Test
    public void testShowFiltered() throws RemoteException {
        when(model.filter(anyString(), anyString(), anyString())).thenReturn(books);

        viewModel.showFiltered("genre", "state", "search");

        verify(model).filter("genre", "state", "search");
        assertEquals(1, viewModel.bookList.size());
        assertEquals(book, viewModel.bookList.get(0));
    }

    @Test
    public void testCreateBook() throws SQLException, RemoteException {
        viewModel.createBook("title", "author", "year", "publisher", "isbn", "pageCount", "genre");

        verify(model).createBook("title", "author", "year", "publisher", "isbn", "pageCount", "genre");
    }

    @Test
    public void testUpdateBook() throws SQLException, RemoteException {
        viewModel.updateBook(1, "title", "author", "year", "publisher", "isbn", "pageCount", "genre");

        verify(model).updateBook(1, "title", "author", "year", "publisher", "isbn", "pageCount", "genre");
    }

    @Test
    public void testDeleteBook() throws SQLException, RemoteException {
        viewModel.deleteBook(1, "title", "author", "year", "publisher", "isbn", "pageCount", "genre");

        verify(model).deleteBook(1, "title", "author", "year", "publisher", "isbn", "pageCount", "genre");
    }

    @Test
    public void testSetSelectedBook() {
        viewModel.setSelectedBook(book);

        assertNotNull(viewModel.selectedBook);
        assertSame(book, viewModel.selectedBook.get());
    }

    @Test
    public void testAddPropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        viewModel.addPropertyChangeListener(listener);

        assertTrue(viewModel.support.hasListeners("BorrowBook"));
    }

    @Test
    public void testRemovePropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        viewModel.addPropertyChangeListener(listener);
        viewModel.removePropertyChangeListener(listener);

        assertFalse(viewModel.support.hasListeners("BorrowBook"));
    }
}
