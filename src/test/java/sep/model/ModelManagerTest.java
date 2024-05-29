package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import sep.client.Client;
import sep.client.ClientInterface;
import sep.model.ModelManager;
import sep.server.Connector;
import sep.shared.ConnectorInterface;
import sep.viewmodel.MainViewModel;
import sep.viewmodel.MyBooksViewModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ModelManagerTest {

    private ModelManager modelManager;
    private Model model;
    private ConnectorInterface connector;
    private Patron loggedInUser;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        connector = mock(ConnectorInterface.class);
        modelManager = new ModelManager(connector);
        model = mock(Model.class);

        loggedInUser = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        UserSession.getInstance().setLoggedInUser(loggedInUser);
        UserSession.getInstance().getLoggedInUser();
    }

    @Test
    public void testGetAllBooks() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(connector.getAllBooks()).thenReturn(books);

        ArrayList<Book> result = modelManager.getAllBooks();

        assertEquals(books, result);
        verify(connector, times(1)).getAllBooks();
    }

    @Test
    public void testGetBorrowedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        ArrayList<Book> books = new ArrayList<>();
        when(connector.getBorrowedBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getBorrowedBooks(patron);

        assertEquals(books, result);
        verify(connector, times(1)).getBorrowedBooks(patron);
    }

    @Test
    public void testGetHistoryOfBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        ArrayList<Book> books = new ArrayList<>();
        when(connector.getHistoryOfBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getHistoryOfBooks(patron);

        assertEquals(books, result);
        verify(connector, times(1)).getHistoryOfBooks(patron);
    }

    @Test
    public void testGetWishlistedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        ArrayList<Book> books = new ArrayList<>();
        when(connector.getWishlistedBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getWishlistedBooks(patron);

        assertEquals(books, result);
        verify(connector, times(1)).getWishlistedBooks(patron);
    }

    @Test
    public void testGetDonatedBooks() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(connector.getDonatedBooks()).thenReturn(books);

        ArrayList<Book> result = modelManager.getDonatedBooks();

        assertEquals(books, result);
        verify(connector, times(1)).getDonatedBooks();
    }

    @Test
    public void testGetAllEvents() throws RemoteException {
        ArrayList<Event> events = new ArrayList<>();
        when(connector.getAllEvents()).thenReturn(events);

        ArrayList<Event> result = modelManager.getAllEvents();

        assertEquals(events, result);
        verify(connector, times(1)).getAllEvents();
    }

    @Test
    public void testGetAmountOfReadBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(connector.getAmountOfReadBooks(patron)).thenReturn(10);

        int result = modelManager.getAmountOfReadBooks(patron);

        assertEquals(10, result);
        verify(connector, times(1)).getAmountOfReadBooks(patron);
    }

    @Test
    public void testGetAmountOfBorrowedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(connector.getAmountOfBorrowedBooks(patron)).thenReturn(5);

        int result = modelManager.getAmountOfBorrowedBooks(patron);

        assertEquals(5, result);
        verify(connector, times(1)).getAmountOfBorrowedBooks(patron);
    }

    @Test
    public void testCreatePatron() throws RemoteException, SQLException {
        modelManager.createPatron("user", "Password123?", "first", "last", "email@example.com", "55555555", 0);

        verify(connector, times(1)).createPatron("user", "Password123?", "first", "last", "email@example.com", "55555555", 0);
    }

    @Test
    void createPatronThrowException() throws RemoteException, SQLException {

        assertThrows(RemoteException.class, () -> {
            modelManager.createPatron("user", "Password123", "first", "last", "email@example.com", "55555555", 0);
        });
    }

    @Test
    public void testCreateEvent() throws RemoteException {
        modelManager.createEvent("Event Title", "Event Description", "2023-12-31");

        verify(connector, times(1)).createEvent("Event Title", "Event Description", "2023-12-31");
    }

    @Test
    public void testCreateEventThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).createEvent(anyString(), anyString(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.createEvent("title", "description", "2024-05-30");
        });
    }

    @Test
    public void testDeleteEvent() throws RemoteException {
        modelManager.deleteEvent(1);

        verify(connector, times(1)).deleteEvent(1);
    }

    @Test
    public void testDeleteEventThrowException() throws RemoteException, SQLException {
        doThrow(new RemoteException("Error occurred while creating event")).when(connector).deleteEvent(1);

        assertThrows(RemoteException.class, () -> {

            modelManager.deleteEvent(1);
        });
    }

    @Test
    public void testUpdateEvent() throws RemoteException, SQLException {
        modelManager.updateEvent(1, "Updated Title", "Updated Description", "2023-12-31");

        verify(connector, times(1)).updateEvent(1, "Updated Title", "Updated Description", "2023-12-31");
    }

    @Test
    public void testUpdateEventThrowException() throws RemoteException, SQLException {
        String title = "Test Event";
        String description = "This is a test event";
        String date = "2024-05-30";

        doThrow(new RemoteException("Error occurred while creating event")).when(connector).updateEvent(2, title, description, date);

        assertThrows(RemoteException.class, () -> {
            modelManager.updateEvent(2, title, description, date);
        });
    }

    @Test
    public void testDeletePatron() throws RemoteException {
        modelManager.deletePatron(1);

        verify(connector, times(1)).deletePatron(1);
    }

    @Test
    public void testDeletePatronThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).deletePatron(anyInt());

        assertThrows(RemoteException.class, () -> {
            modelManager.deletePatron(anyInt());
        });
    }

    @Test
    public void testFilter() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(connector.filter(anyString(), anyString(), anyString())).thenReturn(books);

        ArrayList<Book> result = modelManager.filter("genre", "state", "search");

        assertEquals(books, result);
        verify(connector, times(1)).filter("genre", "state", "search");
    }

    @Test
    public void testBorrowBooks() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(connector).borrowBooks(book, patron);

        modelManager.borrowBooks(book, patron);

        verify(connector, times(1)).borrowBooks(book, patron);
    }

    @Test
    public void testWishlistBook() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(connector).wishlistBook(book, patron);

        modelManager.wishlistBook(book, patron);

        verify(connector, times(1)).wishlistBook(book, patron);
    }

    @Test
    public void testRemoveFromWishlish() throws SQLException, IOException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(connector).deleteFromWishlist(book, patron);

        modelManager.deleteFromWishlist(book, patron);

        verify(connector, times(1)).deleteFromWishlist(book, patron);
    }

    @Test
    public void testGetEndingBooks() throws IOException, SQLException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        ArrayList<String> books = new ArrayList<>();

        when(connector.getEndingBooks(patron)).thenReturn(books);

        ArrayList<String> result = modelManager.getEndingBooks(patron);

        assertEquals(books, result);
        verify(connector, times(1)).getEndingBooks(patron);
    }

    @Test
    public void testApproveDonateBook() throws IOException, SQLException {
        doNothing().when(connector).approveDonatedBook(1,"Book Title", "Author Name", 1234567898923L,2024 , "Publisher Name", 200, "Genre");

        modelManager.approveDonatedBook(1,"Book Title", "Author Name", 1234567898923L,2024 , "Publisher Name", 200, "Genre");

        verify(connector, times(1)).approveDonatedBook(1,"Book Title", "Author Name", 1234567898923L,2024 , "Publisher Name", 200, "Genre");
    }

    @Test
    public void testRejectDonateBook() throws IOException, SQLException {
        doNothing().when(connector).rejectDonatedBook(1);
        modelManager.rejectDonatedBook(1);

        verify(connector, times(1)).rejectDonatedBook(1);
    }

    @Test
    public void testLogin() throws RemoteException {
        String username = "johndoe";
        String password = "password123";
        Patron expectedPatron = new Patron(1, "John", "Doe", username, password, "johndoe@example.com", "12345678", 0);

        when(connector.login(username, password)).thenReturn(expectedPatron);

        Patron result = modelManager.login(username, password);

        assertEquals(expectedPatron, result);
        verify(connector, times(1)).login(username, password);
    }

    @Test
    public void testLoginThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).login(anyString(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.login(anyString(), anyString());
        });
    }


    @Test
    public void testLoginAsAdmin() throws RemoteException {
        String username = "admin";
        String password = "admin123";

        when(connector.loginAsAdmin(username, password)).thenReturn(true);

        boolean result = modelManager.loginAsAdmin(username, password);

        assertTrue(result);
        verify(connector, times(1)).loginAsAdmin(username, password);
    }

    @Test
    public void testLoginAsAdminThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).loginAsAdmin(anyString(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.loginAsAdmin(anyString(), anyString());
        });
    }

    @Test
    public void testUpdateUsername() throws RemoteException {
        int userID = 1;
        String newUsername = "newUsername";

        doNothing().when(connector).updateUsername(userID, newUsername);

        modelManager.updateUsername(userID, newUsername);

        verify(connector, times(1)).updateUsername(userID, newUsername);
    }

    @Test
    public void testUpdateUsernameThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateUsername(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateUsername(1, "newUsername");
        });

        verify(connector, times(1)).updateUsername(anyInt(), anyString());
    }

    @Test
    public void testUpdateEmail() throws RemoteException {
        int userID = 1;
        String newEmail = "newEmail@example.com";

        doNothing().when(connector).updateEmail(userID, newEmail);

        modelManager.updateEmail(userID, newEmail);

        verify(connector, times(1)).updateEmail(userID, newEmail);
    }

    @Test
    public void testUpdateEmailThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateEmail(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateEmail(anyInt(), anyString());
        });
    }

    @Test
    public void testExtendBook() throws RemoteException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(connector).extendBook(book, patron);

        modelManager.extendBook(book, patron);

        verify(connector, times(1)).extendBook(book, patron);
    }

    @Test
    public void testUpdatePhoneNumber() throws RemoteException {
        int userID = 1;
        String newPhoneNumber = "87654321";

        doNothing().when(connector).updatePhoneNumber(userID, newPhoneNumber);

        modelManager.updatePhoneNumber(userID, newPhoneNumber);

        verify(connector, times(1)).updatePhoneNumber(userID, newPhoneNumber);
    }

    @Test
    public void testUpdatePhoneNumberThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updatePhoneNumber(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updatePhoneNumber(anyInt(), anyString());
        });
    }

    @Test
    public void testUpdateFirstName() throws RemoteException {
        int userID = 1;
        String newFirstName = "NewFirstName";

        doNothing().when(connector).updateFirstName(userID, newFirstName);

        modelManager.updateFirstName(userID, newFirstName);

        verify(connector, times(1)).updateFirstName(userID, newFirstName);
    }

    @Test
    public void testUpdateFirstNameThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateFirstName(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateFirstName(anyInt(), anyString());
        });
    }

    @Test
    public void testUpdateLastName() throws RemoteException {
        int userID = 1;
        String newLastName = "NewLastName";

        doNothing().when(connector).updateLastName(userID, newLastName);

        modelManager.updateLastName(userID, newLastName);

        verify(connector, times(1)).updateLastName(userID, newLastName);
    }

    @Test
    public void testUpdateLastNameThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateLastName(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateLastName(anyInt(), anyString());
        });
    }

    @Test
    public void testUpdatePassword() throws RemoteException {
        int userID = 1;
        String newPassword = "newPassword123";

        doNothing().when(connector).updatePassword(userID, newPassword);

        modelManager.updatePassword(userID, newPassword);

        verify(connector, times(1)).updatePassword(userID, newPassword);
    }

    @Test
    public void testUpdatePasswordThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updatePassword(anyInt(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updatePassword(anyInt(), anyString());
        });
    }

    @Test
    public void testUpdateFees() throws RemoteException {
        int userID = 1;
        int newFees = 50;

        doNothing().when(connector).updateFees(userID, newFees);

        modelManager.updateFees(userID, newFees);

        verify(connector, times(1)).updateFees(userID, newFees);
    }

    @Test
    public void testUpdateFeesThrowsException() throws RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateFees(anyInt(), anyInt());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateFees(anyInt(), anyInt());
        });
    }

    @Test
    public void testCreateBook() throws RemoteException, SQLException {
        String title = "Book Title";
        String author = "Author Name";
        String year = "2024";
        String publisher = "Publisher Name";
        String isbn = "1234567890123";
        String pageCount = "200";
        String genre = "Genre";

        doNothing().when(connector).createBook(title, author, year, publisher, isbn, pageCount, genre);

        modelManager.createBook(title, author, year, publisher, isbn, pageCount, genre);

        verify(connector, times(1)).createBook(title, author, year, publisher, isbn, pageCount, genre);
    }

    @Test
    public void testCreateBookThrowsException() throws RemoteException, SQLException {
        doThrow(new RemoteException("Error occurred")).when(connector).createBook(anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.createBook(anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        });
    }

    @Test
    public void testIsWishlisted() throws RemoteException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(connector.isWishlisted(book, patron)).thenReturn(true);

        boolean result = modelManager.isWishlisted(book, patron);

        assertTrue(result);
        verify(connector, times(1)).isWishlisted(book, patron);
    }

    @Test
    public void testReturnBookToDatabase() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(connector).returnBookToDatabase(book, patron);

        modelManager.returnBookToDatabase(book, patron);

        verify(connector, times(1)).returnBookToDatabase(book, patron);
    }

    @Test
    public void testDonateBook() throws SQLException, IOException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        modelManager.donateBook("Title", "Author", 1234567891, 2002, "ISBN", 2002, "PageCount", patron);

        verify(connector, times(1)).donateBook("Title", "Author", 1234567891, 2002, "ISBN", 2002, "PageCount", patron);
    }

    @Test
    public void testUpdateBook() throws RemoteException, SQLException {
        doNothing().when(connector).updateBook(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        modelManager.updateBook(1, "Title", "Author", "Year", "Publisher", "ISBN", "PageCount", "Genre");

        verify(connector, times(1)).updateBook(1, "Title", "Author", "Year", "Publisher", "ISBN", "PageCount", "Genre");
    }

    @Test
    public void testUpdateBookThrowsException() throws SQLException, RemoteException {
        doThrow(new RemoteException("Error occurred")).when(connector).updateBook(anyInt(),anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        assertThrows(RemoteException.class, () -> {
            modelManager.updateBook(anyInt(),anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        });
    }

    @Test
    public void testDeleteBook() throws RemoteException, SQLException {
        modelManager.deleteBook(1, "Book Title", "Author Name", "2024", "Publisher Name", "1234567890123L", "200", "Genre");;

        verify(connector, times(1)).deleteBook(1, "Book Title", "Author Name", "2024", "Publisher Name", "1234567890123L", "200", "Genre");
    }

    @Test
    public void testDeleteBookThrowsException() throws RemoteException, SQLException {
        doThrow(new RemoteException("Error occurred")).when(connector).deleteBook(anyInt(),anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        assertThrows(IllegalArgumentException.class, () -> {
            modelManager.deleteBook(anyInt(),anyString(),anyString(), anyString(), anyString(), anyString(), anyString(), anyString());
        });
    }

    @Test
    public void testAddPropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        modelManager.addPropertyChangeListener(listener);

        assertTrue(modelManager.support.hasListeners(null));
    }

    @Test
    public void testRemovePropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        modelManager.removePropertyChangeListener(listener);

        assertFalse(modelManager.support.hasListeners(null));
    }
}