package sep.viewmodel;

import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;
import sep.model.UserSession;
import sep.viewmodel.MyBooksViewModel;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MyBooksViewModelTest {

    private MyBooksViewModel viewModel;
    private Model modelMock;
    private Patron loggedInUser;
    private Book book;

    @BeforeEach
    void setUp() {
        modelMock = mock(Model.class);
        viewModel = new MyBooksViewModel(modelMock);

        book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        loggedInUser = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        UserSession.getInstance().setLoggedInUser(loggedInUser);
        UserSession.getInstance().getLoggedInUser();
    }

    @Test
    void resetBookList() throws RemoteException {
        // Given
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre"));
        when(modelMock.getBorrowedBooks(loggedInUser)).thenReturn(books);

        // When
        viewModel.resetBookList(loggedInUser);

        // Then
        assertEquals(books, viewModel.borrowedBookList.get());
    }

    @Test
    void returnBook() throws IOException, SQLException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        // When
        viewModel.returnBook(book, loggedInUser);

        // Then
        verify(modelMock).returnBookToDatabase(book, loggedInUser);
    }

    @Test
    void checkBooksToExtend() throws RemoteException {
        // Given
        ArrayList<String> expectedBooks = new ArrayList<>();
        expectedBooks.add("Book Title");
        when(modelMock.getEndingBooks(loggedInUser)).thenReturn(expectedBooks);

        // When
        ArrayList<String> actualBooks = viewModel.checkBooksToExtend(loggedInUser);

        // Then
        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    void extendBook() throws SQLException, RemoteException {
        // Given
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        // When
        viewModel.extendBook(book, loggedInUser);

        // Then
        verify(modelMock).extendBook(book, loggedInUser);
    }

    @Test
    void propertyChange_BorrowBook() throws RemoteException {
        // When
        viewModel.propertyChange(new PropertyChangeEvent(modelMock, "BorrowBook", null, null));

        // Then
        verify(modelMock).getBorrowedBooks(loggedInUser);
    }

    @Test
    void propertyChangeLogin() throws RemoteException {
        // When
        viewModel.propertyChange(new PropertyChangeEvent(modelMock, "login", null, null));

        // Then
        verify(modelMock).getBorrowedBooks(loggedInUser);
    }

    @Test
    void testPropertyChange_BorrowBook_WithRemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(modelMock, "BorrowBook", null, null);
        doThrow(new RemoteException()).when(modelMock).getBorrowedBooks(UserSession.getInstance().getLoggedInUser());

        // When, Then
        assertThrows(RuntimeException.class, () -> viewModel.propertyChange(event));
    }

    @Test
    void testPropertyChange_Login_WithRemoteException() throws RemoteException {
        // Given
        PropertyChangeEvent event = new PropertyChangeEvent(modelMock, "login", null, null);
        doThrow(new RemoteException()).when(modelMock).getBorrowedBooks(UserSession.getInstance().getLoggedInUser());

        // When, Then
        assertThrows(RuntimeException.class, () -> viewModel.propertyChange(event));
    }

    @Test
    void testBindList() throws RemoteException {
        // Given
        ObservableList<Book> observableList = FXCollections.observableArrayList();
        ObjectProperty<ObservableList<Book>> property = new SimpleObjectProperty<>(observableList);

        // When
        viewModel.bindList(property);

        // Then
        assertEquals(viewModel.borrowedBookList.get(), property.get());

        // Verify bidirectional binding
        Book newBook = new Book(2, "New Book Title", "New Author Name", 2025, "New Publisher Name", 1234567890124L, 300, "New Genre");

        // Update the view model's list and check if the property list updates
        viewModel.borrowedBookList.add(newBook);
        assertEquals(viewModel.borrowedBookList.get(), property.get());

        // Update the property list and check if the view model's list updates
        property.get().add(book);
        assertEquals(viewModel.borrowedBookList.get(), property.get());
    }
}
