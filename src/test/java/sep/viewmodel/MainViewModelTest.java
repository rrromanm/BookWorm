package sep.viewmodel;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MainViewModelTest {

    private MainViewModel viewModel;
    private Model model;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        viewModel = new MainViewModel(model);
        new JFXPanel();
    }

    @Test
    void bindList() throws RemoteException {
        // Given
        ObservableList<Book> bookObservableList = FXCollections.observableArrayList();
        ObjectProperty<ObservableList<Book>> property = new SimpleObjectProperty<>(bookObservableList);

        // When
        viewModel.bindList(property);

        // Then
        assertEquals(bookObservableList, viewModel.bookList.get());
    }

    @Test
    void bindSelectedBook() throws RemoteException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        ReadOnlyObjectProperty<Book> property = new SimpleObjectProperty<>(book);

        // When
        viewModel.bindSelectedBook(property);

        // Then
        assertEquals(book, viewModel.selectedBook.get());
    }

    @Test
    void resetBookList() throws RemoteException {
        // Given
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre"));
        books.add(new Book(2, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre"));
        when(model.getAllBooks()).thenReturn(books);

        // When
        viewModel.resetBookList();

        // Then
        assertEquals(books, viewModel.bookList.get());
    }

    @Test
    void showFiltered() throws RemoteException {
        // Given
        ArrayList<Book> filteredBooks = new ArrayList<>();
        filteredBooks.add(new  Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre"));
        filteredBooks.add(new Book(2, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre"));
        when(model.filter(anyString(), anyString(), anyString())).thenReturn(filteredBooks);

        // When
        viewModel.showFiltered("genre", "state", "search");

        // Then
        assertEquals(filteredBooks, viewModel.bookList.get());
    }

    @Test
    void borrowBook() throws SQLException, IOException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        // When
        viewModel.borrowBook(book, patron);

        // Then
        verify(model).borrowBooks(book, patron);
    }

    @Test
    void wishlistBook() throws IOException, SQLException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);


        // When
        viewModel.wishlistBook(book, patron);

        // Then
        verify(model).wishlistBook(book, patron);
    }

    @Test
    void isWishlisted() throws RemoteException, SQLException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        when(model.isWishlisted(book, patron)).thenReturn(true);

        // When
        boolean result = viewModel.isWishlisted(book, patron);

        // Then
        assertTrue(result);
    }

    @Test
    void getAmountOfBorrowedBooks() throws RemoteException, SQLException {
        // Given
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        when(model.getAmountOfBorrowedBooks(patron)).thenReturn(5);

        // When
        int result = viewModel.getAmountOfBorrowedBooks(patron);

        // Then
        assertEquals(5, result);
    }

    @Test
    void getEndingBooks() throws RemoteException, SQLException {
        // Given
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        ArrayList<String> endingBooks = new ArrayList<>();
        endingBooks.add("Book 1");
        endingBooks.add("Book 2");
        when(model.getEndingBooks(patron)).thenReturn(endingBooks);

        // When
        ArrayList<String> result = viewModel.getEndingBooks(patron);

        // Then
        assertEquals(endingBooks, result);
    }
    @Test
    void propertyChange_BorrowBook() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "BorrowBook", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_ReturnBook() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "ReturnBook", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_DonatedBookApproved() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "DonatedBookApproved", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_RemoveBook() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "removeBook", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_CreateBook() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "createBook", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_UpdateBook() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "updateBook", null, null);
        when(model.getAllBooks()).thenReturn(new ArrayList<>());

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();
    }

    @Test
    void propertyChange_BorrowBook_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "BorrowBook", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

    @Test
    void propertyChange_ReturnBook_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "ReturnBook", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

    @Test
    void propertyChange_DonatedBookApproved_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "DonatedBookApproved", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

    @Test
    void propertyChange_RemoveBook_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "removeBook", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

    @Test
    void propertyChange_CreateBook_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "createBook", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

    @Test
    void propertyChange_UpdateBook_RemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(model, "updateBook", null, null);
        doThrow(new RemoteException()).when(model).getAllBooks();

        // When
        Platform.runLater(() -> viewModel.propertyChange(event));


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Then
        verify(model).getAllBooks();

    }

}