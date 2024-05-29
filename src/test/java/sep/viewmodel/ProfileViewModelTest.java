package sep.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProfileViewModelTest {
    private ProfileViewModel profileViewModel;
    private Model model;
    private Patron patron;
    private Book book;
    private UserSession userSession;

    @BeforeEach
    public void setUp() throws RemoteException {
        model = mock(Model.class);
        userSession = UserSession.getInstance();

        patron = new Patron(1,"Test", "Testovic", "testname", "testword", "email", "phoneNumber", 1);
        userSession.setLoggedInUser(patron);

        book = new Book(1, "Book Title", "Author Name", 2024, "Publisher Name", 1234567890123L, 200, "Genre");

        profileViewModel = new ProfileViewModel(model);
    }

    @Test
    public void testFillData() {
        profileViewModel.fillData();

        assertEquals("testname", profileViewModel.username.get());
        assertEquals("email", profileViewModel.email.get());
        assertEquals("Test", profileViewModel.first_name.get());
        assertEquals("Testovic", profileViewModel.last_name.get());
        assertEquals("phoneNumber", profileViewModel.phone_number.get());
        assertEquals("testword", profileViewModel.password.get());
        assertEquals("1", profileViewModel.patronID.get());
    }

    @Test
    public void testResetHistoryList() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Title 1", "Author 1", 2022, "Publisher 1", 1234567891235L, 100, "Genre 1"));
        when(model.getHistoryOfBooks(patron)).thenReturn(books);

        profileViewModel.resetHistoryList(patron);

        assertEquals(books, profileViewModel.historyOfBooksList.get());
        verify(model, times(1)).getHistoryOfBooks(patron);
    }

    @Test
    public void testResetWishlistList() throws RemoteException {
        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(2, "Title 2", "Author 2", 2023, "Publisher 2", 1234567891236L, 200, "Genre 2"));
        when(model.getWishlistedBooks(patron)).thenReturn(books);

        profileViewModel.resetWishlistList(patron);

        assertEquals(books, profileViewModel.wishlistList.get());
        verify(model, times(1)).getWishlistedBooks(patron);
    }

    @Test
    public void testGetAmountOfReadBooks() throws RemoteException {
        when(model.getAmountOfReadBooks(patron)).thenReturn(5);

        int amount = profileViewModel.getAmountOfReadBooks(patron);

        assertEquals(5, amount);
        verify(model, times(1)).getAmountOfReadBooks(patron);
    }

    @Test
    public void testDeletePatron() throws RemoteException {
        profileViewModel.deletePatron();

        verify(model, times(1)).deletePatron(patron.getUserID());
    }

    @Test
    public void testUpdateUsername() throws RemoteException {
        String newUsername = "newUsername";
        profileViewModel.updateUsername(newUsername, patron.getUserID());

        verify(model, times(1)).updateUsername(patron.getUserID(), newUsername);
    }

    @Test
    public void testUpdateEmail() throws RemoteException {
        String newEmail = "newEmail";
        profileViewModel.updateEmail(newEmail, patron.getUserID());

        verify(model, times(1)).updateEmail(patron.getUserID(), newEmail);
    }

    @Test
    public void testUpdatePhoneNumber() throws RemoteException {
        String newPhoneNumber = "newPhoneNumber";
        profileViewModel.updatePhoneNumber(newPhoneNumber, patron.getUserID());

        verify(model, times(1)).updatePhoneNumber(patron.getUserID(), newPhoneNumber);
    }

    @Test
    public void testUpdateFirstName() throws RemoteException {
        String newFirstName = "newFirstName";
        profileViewModel.updateFirstName(newFirstName, patron.getUserID());

        verify(model, times(1)).updateFirstName(patron.getUserID(), newFirstName);
    }

    @Test
    public void testUpdateLastName() throws RemoteException {
        String newLastName = "newLastName";
        profileViewModel.updateLastName(newLastName, patron.getUserID());

        verify(model, times(1)).updateLastName(patron.getUserID(), newLastName);
    }

    @Test
    public void testUpdatePassword() throws RemoteException {
        String newPassword = "newPassword";
        profileViewModel.updatePassword(newPassword, patron.getUserID());

        verify(model, times(1)).updatePassword(patron.getUserID(), newPassword);
    }

    @Test
    public void testRemoveFromWishlist() throws SQLException, IOException {
        Book book = new Book(3, "Title 3", "Author 3", 2024, "Publisher 3", 1234567891237L, 300, "Genre 3");

        profileViewModel.removeFromWishlist(book, patron);

        verify(model, times(1)).deleteFromWishlist(book, patron);
    }

    @Test
    public void testPropertyChange() throws IOException, SQLException {
        model.addPropertyChangeListener(profileViewModel);
        model.borrowBooks(book, patron);

        PropertyChangeEvent event = new PropertyChangeEvent(this, "BorrowBook", false, true);
        verify(profileViewModel).propertyChange(event);
    }

    @Test
    public void testAddPropertyChangeListener() {
        PropertyChangeListener listener = mock(PropertyChangeListener.class);
        profileViewModel.addPropertyChangeListener(listener);

        assertTrue(profileViewModel.support.hasListeners(null));
    }
}
