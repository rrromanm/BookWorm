package sep.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sep.client.ClientInterface;
import sep.model.ModelManager;
import sep.shared.ConnectorInterface;

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

    @Mock
    private ClientInterface client;

    private ModelManager modelManager;

    @BeforeEach
    public void setUp() throws IOException, SQLException {
        MockitoAnnotations.openMocks(this);
        ConnectorInterface connector = mock(ConnectorInterface.class);
        when(client.getAllBooks()).thenReturn(new ArrayList<>());
        modelManager = new ModelManager(connector);
        modelManager = spy(new ModelManager(connector));
    }

    @Test
    public void testGetAllBooks() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(client.getAllBooks()).thenReturn(books);

        ArrayList<Book> result = modelManager.getAllBooks();

        assertEquals(books, result);
        verify(modelManager, times(1)).getAllBooks();
    }

    @Test
    public void testGetBorrowedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);

        ArrayList<Book> books = new ArrayList<>();
        when(client.getBorrowedBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getBorrowedBooks(patron);

        assertEquals(books, result);
        verify(modelManager, times(1)).getBorrowedBooks(patron);
    }

    @Test
    public void testGetHistoryOfBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        ArrayList<Book> books = new ArrayList<>();
        when(client.getHistoryOfBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getHistoryOfBooks(patron);

        assertEquals(books, result);
        verify(modelManager, times(1)).getHistoryOfBooks(patron);
    }

    @Test
    public void testGetWishlistedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        ArrayList<Book> books = new ArrayList<>();
        when(client.getWishlistedBooks(patron)).thenReturn(books);

        ArrayList<Book> result = modelManager.getWishlistedBooks(patron);

        assertEquals(books, result);
        verify(modelManager, times(1)).getWishlistedBooks(patron);
    }

    @Test
    public void testGetDonatedBooks() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(client.getDonatedBooks()).thenReturn(books);

        ArrayList<Book> result = modelManager.getDonatedBooks();

        assertEquals(books, result);
        verify(modelManager, times(1)).getDonatedBooks();
    }

    @Test
    public void testGetAllEvents() throws RemoteException {
        ArrayList<Event> events = new ArrayList<>();
        when(client.getAllEvents()).thenReturn(events);

        ArrayList<Event> result = modelManager.getAllEvents();

        assertEquals(events, result);
        verify(modelManager, times(1)).getAllEvents();
    }

    @Test
    public void testGetAmountOfReadBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(modelManager.getAmountOfReadBooks(patron)).thenReturn(10);

        int result = modelManager.getAmountOfReadBooks(patron);

        assertEquals(10, result);
        verify(modelManager, times(1)).getAmountOfReadBooks(patron);
    }

    @Test
    public void testGetAmountOfBorrowedBooks() throws RemoteException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(modelManager.getAmountOfBorrowedBooks(patron)).thenReturn(5);

        int result = modelManager.getAmountOfBorrowedBooks(patron);

        assertEquals(5, result);
        verify(modelManager, times(1)).getAmountOfBorrowedBooks(patron);
    }

//    @Test
//    public void testAddPropertyChangeListener() {
//        PropertyChangeListener listener = mock(PropertyChangeListener.class);
//        modelManager.addPropertyChangeListener(listener);
//
//        PropertyChangeSupport support = modelManager.getSupport();
//        assertTrue(Arrays.asList(support.getPropertyChangeListeners()).contains(listener));
//    }
//
//    @Test
//    public void testRemovePropertyChangeListener() {
//        PropertyChangeListener listener = mock(PropertyChangeListener.class);
//        modelManager.addPropertyChangeListener(listener);
//        modelManager.removePropertyChangeListener(listener);
//
//        PropertyChangeSupport support = modelManager.getClass().getPro;
//        assertFalse(Arrays.asList(support.getPropertyChangeListeners()).contains(listener));
//    }

    @Test
    public void testCreatePatron() throws RemoteException, SQLException {
        modelManager.createPatron("user", "Password123?", "first", "last", "email@example.com", "55555555", 0);

        verify(modelManager, times(1)).createPatron("user", "Password123?", "first", "last", "email@example.com", "55555555", 0);
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

        verify(modelManager, times(1)).createEvent("Event Title", "Event Description", "2023-12-31");
    }

    @Test
    public void testCreateEventThrowException() throws RemoteException, SQLException {
        String title = "Test Event";
        String description = "This is a test event";
        String date = "2024-05-30";

        doThrow(new RemoteException("Error occurred while creating event")).when(modelManager).createEvent(title, description, date);

        assertThrows(RemoteException.class, () -> {

            modelManager.createEvent(title, description, date);
        });
    }

    @Test
    public void testDeleteEvent() throws RemoteException {
        modelManager.deleteEvent(1);

        verify(modelManager, times(1)).deleteEvent(1);
    }

    @Test
    public void testDeleteEventThrowException() throws RemoteException, SQLException {
        doThrow(new RemoteException("Error occurred while creating event")).when(modelManager).deleteEvent(1);

        assertThrows(RemoteException.class, () -> {

            modelManager.deleteEvent(1);
        });
    }

    @Test
    public void testUpdateEvent() throws RemoteException {
        modelManager.updateEvent(1, "Updated Title", "Updated Description", "2023-12-31");

        verify(modelManager, times(1)).updateEvent(1, "Updated Title", "Updated Description", "2023-12-31");
    }

    @Test
    public void testUpdateEventThrowException() throws RemoteException {
        String title = "Test Event";
        String description = "This is a test event";
        String date = "2024-05-30";

        doThrow(new RemoteException("Error occurred while creating event")).when(modelManager).updateEvent(1, title, description, date);

        assertThrows(RemoteException.class, () -> {
            modelManager.updateEvent(2, title, description, date);
        });
    }

    @Test
    public void testDeletePatron() throws RemoteException {
        modelManager.deletePatron(1);

        verify(modelManager, times(1)).deletePatron(1);
    }

    @Test
    public void testFilter() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        when(client.filter(anyString(), anyString(), anyString())).thenReturn(books);

        ArrayList<Book> result = modelManager.filter("genre", "state", "search");

        assertEquals(books, result);
        verify(modelManager, times(1)).filter("genre", "state", "search");
    }

    @Test
    public void testBorrowBooks() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(client).borrowBooks(book, patron);

        modelManager.borrowBooks(book, patron);

        verify(modelManager, times(1)).borrowBooks(book, patron);
    }

    @Test
    public void testWishlistBook() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(client).wishlistBook(book, patron);

        modelManager.wishlistBook(book, patron);

        verify(modelManager, times(1)).wishlistBook(book, patron);
    }

    @Test
    public void testExtendBook() throws RemoteException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(client).extendBook(book, patron);

        modelManager.extendBook(book, patron);

        verify(modelManager, times(1)).extendBook(book, patron);
    }

    @Test
    public void testIsWishlisted() throws RemoteException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        when(modelManager.isWishlisted(book, patron)).thenReturn(true);

        boolean result = modelManager.isWishlisted(book, patron);

        assertTrue(result);
        verify(modelManager, times(1)).isWishlisted(book, patron);
    }

    @Test
    public void testReturnBookToDatabase() throws IOException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(client).returnBookToDatabase(book, patron);

        modelManager.returnBookToDatabase(book, patron);

        verify(modelManager, times(1)).returnBookToDatabase(book, patron);
    }

    @Test
    public void testDonateBook() throws SQLException, IOException {
        Patron patron = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        doNothing().when(client).donateBook(anyString(), anyString(), anyLong(), anyInt(), anyString(), anyInt(), anyString(), eq(patron));

        modelManager.donateBook("Title", "Author", 1234567891, 2002, "ISBN", 2002, "PageCount", patron);

        verify(modelManager, times(1)).donateBook("Title", "Author", 1234567891, 2002, "ISBN", 2002, "PageCount", patron);
    }

    @Test
    public void testUpdateBook() throws RemoteException, SQLException {
        doNothing().when(client).updateBook(anyInt(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString());

        modelManager.updateBook(1, "Title", "Author", "Year", "Publisher", "ISBN", "PageCount", "Genre");

        verify(modelManager, times(1)).updateBook(1, "Title", "Author", "Year", "Publisher", "ISBN", "PageCount", "Genre");
    }

    @Test
    public void testDeleteBook() throws RemoteException, SQLException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        modelManager.deleteBook(1, "Book Title", "Author Name", "2024", "Publisher Name", "1234567890123L", "200", "Genre");;

        verify(modelManager, times(1)).deleteBook(1, "Book Title", "Author Name", "2024", "Publisher Name", "1234567890123L", "200", "Genre");
    }
}
