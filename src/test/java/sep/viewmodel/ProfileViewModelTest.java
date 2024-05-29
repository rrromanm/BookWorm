package sep.viewmodel;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sep.model.Book;
import sep.model.Model;
import sep.model.Patron;
import sep.model.UserSession;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProfileViewModelTest {

    @Mock
    private Model model;

    private ProfileViewModel profileViewModel;
    private Patron loggedInUser;

    @BeforeEach
    void setUp() throws RemoteException {
        MockitoAnnotations.openMocks(this);
        profileViewModel = new ProfileViewModel(model);

        loggedInUser = new Patron(1, "John", "Doe", "johndoe", "password123", "johndoe@example.com", "12345678", 0);
        UserSession.getInstance().setLoggedInUser(loggedInUser);
    }

    @Test
    void testBindHistoryList() throws RemoteException {
        ObservableList<Book> list = FXCollections.observableArrayList();
        profileViewModel.bindHistoryList(new SimpleObjectProperty<>(list));
        assertEquals(profileViewModel.historyOfBooksList, list);
    }

    @Test
    void testBindWishlistList() throws RemoteException {
        ObservableList<Book> list = FXCollections.observableArrayList();
        profileViewModel.bindWishlistList(new SimpleObjectProperty<>(list));
        assertEquals(profileViewModel.wishlistList, list);
    }

    @Test
    void testBindUsername() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindUsername(property);
        profileViewModel.username.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindEmail() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindEmail(property);
        profileViewModel.email.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindFirstName() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindFirstName(property);
        profileViewModel.first_name.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindLastName() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindLastName(property);
        profileViewModel.last_name.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindPhoneNumber() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindPhoneNumber(property);
        profileViewModel.phone_number.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindPassword() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindPassword(property);
        profileViewModel.password.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testBindUserId() {
        // Given
        StringProperty property = new SimpleStringProperty();

        // When
        profileViewModel.bindUserId(property);
        profileViewModel.patronID.set("test");

        // Then
        assertEquals("test", property.get());
    }

    @Test
    void testFillData() {
        profileViewModel.fillData();
        assertEquals("johndoe", profileViewModel.username.get());
        assertEquals("johndoe@example.com", profileViewModel.email.get());
        assertEquals("John", profileViewModel.first_name.get());
        assertEquals("Doe", profileViewModel.last_name.get());
        assertEquals("12345678", profileViewModel.phone_number.get());
        assertEquals("password123", profileViewModel.password.get());
        assertEquals("1", profileViewModel.patronID.get());
    }

    @Test
    void testResetHistoryList() throws RemoteException {
        // Given
        ArrayList<Book> history = new ArrayList<>();
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        history.add(book);
        when(model.getHistoryOfBooks(loggedInUser)).thenReturn(history);

        // When
        profileViewModel.resetHistoryList(loggedInUser);

        // Then
        assertEquals(history, profileViewModel.historyOfBooksList.get());
    }

    @Test
    void testResetWishlistList() throws RemoteException {
        // Given
        ArrayList<Book> wishlist = new ArrayList<>();
        Book book = new Book(2, "Another Book Title", "Another Author Name", 2023, "Another Publisher Name", 1234567890124L, 250, "Genre");
        wishlist.add(book);
        when(model.getWishlistedBooks(loggedInUser)).thenReturn(wishlist);

        // When
        profileViewModel.resetWishlistList(loggedInUser);

        // Then
        assertEquals(wishlist, profileViewModel.wishlistList.get());
    }

    @Test
    void testGetAmountOfReadBooks() throws RemoteException {
        when(model.getAmountOfReadBooks(any(Patron.class))).thenReturn(5);
        int amount = profileViewModel.getAmountOfReadBooks(loggedInUser);
        assertEquals(5, amount);
    }

    @Test
    void testDeletePatron() throws RemoteException {
        profileViewModel.deletePatron();
        verify(model).deletePatron(loggedInUser.getUserID());
    }

    @Test
    void testUpdateUsername() throws RemoteException {
        profileViewModel.updateUsername("newUsername", loggedInUser.getUserID());
        verify(model).updateUsername(loggedInUser.getUserID(), "newUsername");
    }

    @Test
    void testUpdateEmail() throws RemoteException {
        profileViewModel.updateEmail("newEmail@example.com", loggedInUser.getUserID());
        verify(model).updateEmail(loggedInUser.getUserID(), "newEmail@example.com");
    }

    @Test
    void testUpdatePhoneNumber() throws RemoteException {
        profileViewModel.updatePhoneNumber("87654321", loggedInUser.getUserID());
        verify(model).updatePhoneNumber(loggedInUser.getUserID(), "87654321");
    }

    @Test
    void testUpdateFirstName() throws RemoteException {
        profileViewModel.updateFirstName("Jane", loggedInUser.getUserID());
        verify(model).updateFirstName(loggedInUser.getUserID(), "Jane");
    }

    @Test
    void testUpdateLastName() throws RemoteException {
        profileViewModel.updateLastName("Smith", loggedInUser.getUserID());
        verify(model).updateLastName(loggedInUser.getUserID(), "Smith");
    }

    @Test
    void testUpdatePassword() throws RemoteException {
        profileViewModel.updatePassword("newPassword123", loggedInUser.getUserID());
        verify(model).updatePassword(loggedInUser.getUserID(), "newPassword123");
    }

    @Test
    void testRemoveFromWishlist() throws SQLException, IOException, IOException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        profileViewModel.removeFromWishlist(book, loggedInUser);
        verify(model).deleteFromWishlist(book, loggedInUser);
    }

    @Test
    void testPropertyChangeReturnBook() throws RemoteException {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "ReturnBook", false, true);
        profileViewModel.propertyChange(event);
        verify(model).getHistoryOfBooks(loggedInUser);
    }

    @Test
    void testPropertyChangeWishlist() throws RemoteException {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "Wishlist", false, true);
        profileViewModel.propertyChange(event);
        verify(model).getWishlistedBooks(loggedInUser);
    }

    @Test
    void testPropertyChangeUpdateBook() throws RemoteException {
        PropertyChangeEvent event = new PropertyChangeEvent(this, "updateBook", false, true);
        profileViewModel.propertyChange(event);
        verify(model).getHistoryOfBooks(loggedInUser);
        verify(model).getWishlistedBooks(loggedInUser);
    }
    @Test
    void testResetHistoryList_RemoteException() throws RemoteException {
        doThrow(new RemoteException()).when(model).getHistoryOfBooks(any(Patron.class));
        assertThrows(RemoteException.class, () -> profileViewModel.resetHistoryList(loggedInUser));
    }

    @Test
    void testResetWishlistList_RemoteException() throws RemoteException {
        doThrow(new RemoteException()).when(model).getWishlistedBooks(any(Patron.class));
        assertThrows(RemoteException.class, () -> profileViewModel.resetWishlistList(loggedInUser));
    }

    @Test
    void testGetAmountOfReadBooks_RemoteException() throws RemoteException {
        doThrow(new RemoteException()).when(model).getAmountOfReadBooks(any(Patron.class));
        assertThrows(RemoteException.class, () -> profileViewModel.getAmountOfReadBooks(loggedInUser));
    }

    @Test
    void testDeletePatron_RemoteException() throws RemoteException {
        doThrow(new RemoteException()).when(model).deletePatron(anyInt());
        assertThrows(RemoteException.class, () -> profileViewModel.deletePatron());
    }

    @Test
    void testUpdateUsername_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updateUsername(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateUsername("newUsername", loggedInUser.getUserID()));
    }

    @Test
    void testUpdateEmail_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updateEmail(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateEmail("newEmail@example.com", loggedInUser.getUserID()));
    }

    @Test
    void testUpdatePhoneNumber_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updatePhoneNumber(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updatePhoneNumber("87654321", loggedInUser.getUserID()));
    }

    @Test
    void testUpdateFirstName_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updateFirstName(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateFirstName("Jane", loggedInUser.getUserID()));
    }

    @Test
    void testUpdateLastName_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updateLastName(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateLastName("Smith", loggedInUser.getUserID()));
    }

    @Test
    void testUpdatePassword_IllegalStateException() throws RemoteException {
        doThrow(new IllegalStateException()).when(model).updatePassword(anyInt(), anyString());
        assertThrows(IllegalStateException.class, () -> profileViewModel.updatePassword("newPassword123", loggedInUser.getUserID()));
    }

    @Test
    void testRemoveFromWishlist_RemoteException() throws SQLException, IOException {
        Book book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");
        doThrow(new RemoteException()).when(model).deleteFromWishlist(any(Book.class), any(Patron.class));
        assertThrows(RemoteException.class, () -> profileViewModel.removeFromWishlist(book, loggedInUser));
    }
    @Test
    void testUpdateUsername_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateUsername("newUsername", 1));
    }

    @Test
    void testUpdateEmail_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateEmail("newEmail@example.com", 1));
    }

    @Test
    void testUpdatePhoneNumber_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updatePhoneNumber("87654321", 1));
    }

    @Test
    void testUpdateFirstName_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateFirstName("Jane", 1));
    }

    @Test
    void testUpdateLastName_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updateLastName("Smith", 1));
    }

    @Test
    void testUpdatePassword_NoUserLoggedIn() {
        UserSession.getInstance().setLoggedInUser(null);
        assertThrows(IllegalStateException.class, () -> profileViewModel.updatePassword("newPassword123", 1));
    }
    @Test
    public void testAddPropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        profileViewModel.addPropertyChangeListener(listener);

        assertTrue(profileViewModel.support.hasListeners(null));
    }
}
